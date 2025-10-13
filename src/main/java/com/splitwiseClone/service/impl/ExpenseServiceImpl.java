package com.splitwiseClone.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.splitwiseClone.model.Expense;
import com.splitwiseClone.model.Group;
import com.splitwiseClone.model.Split;
import com.splitwiseClone.model.User;
import com.splitwiseClone.model.SplitType;
import com.splitwiseClone.repository.ExpenseRepository;
import com.splitwiseClone.repository.GroupRepository;
import com.splitwiseClone.repository.UserRepository;
import com.splitwiseClone.repository.SettlementRepository;
import com.splitwiseClone.service.ExpenseService;
import com.splitwiseClone.dto.SettleTransactionResponse;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ExpenseServiceImpl implements ExpenseService{
	private final ExpenseRepository expenseRepository;
	private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final SettlementRepository settlementRepository;
	
    public ExpenseServiceImpl(ExpenseRepository expenseRepository, UserRepository userRepository, GroupRepository groupRepository, SettlementRepository settlementRepository) {
		this.expenseRepository = expenseRepository;
		this.userRepository = userRepository;
		this.groupRepository = groupRepository;
        this.settlementRepository = settlementRepository;
	}
	
	@Override
	public Expense createExpense(Expense expense) {
		// Fetch the actual paidBy user and group entities from the database using their IDs
		User paidBy = userRepository.findById(expense.getPaidBy().getId())
				.orElseThrow(() -> new EntityNotFoundException("Paid by user not found with id: " + expense.getPaidBy().getId()));
		Group group = groupRepository.findById(expense.getGroup().getId())
				.orElseThrow(() -> new EntityNotFoundException("Group not found with id: " + expense.getGroup().getId()));
		
		expense.setPaidBy(paidBy);
		expense.setGroup(group);

		// Normalize splits according to split type
		applySplitStrategy(expense);
		
		// Save the expense, and thanks to CascadeType.ALL, the splits will be saved automatically
		return expenseRepository.save(expense);
	}

	private void applySplitStrategy(Expense expense) {
		final SplitType splitType = expense.getSplitType() == null ? SplitType.EXACT : expense.getSplitType();
		final BigDecimal totalAmount = expense.getAmount().setScale(2, RoundingMode.HALF_EVEN);

		// Load and attach users for provided splits (also validates users exist)
		List<Split> providedSplits = expense.getSplits() == null ? new ArrayList<>() : expense.getSplits();
		for (Split split : providedSplits) {
			split.setExpense(expense);
			User splitUser = userRepository.findById(split.getUser().getId())
					.orElseThrow(() -> new EntityNotFoundException("Split user not found with id: " + split.getUser().getId()));
			split.setUser(splitUser);
		}

		switch (splitType) {
			case EQUAL -> applyEqualSplit(expense, providedSplits, totalAmount);
			case PERCENT -> applyPercentSplit(expense, providedSplits, totalAmount);
			case EXACT -> applyExactSplit(expense, providedSplits, totalAmount);
			default -> throw new IllegalArgumentException("Unsupported split type: " + splitType);
		}
	}

	private void applyEqualSplit(Expense expense, List<Split> providedSplits, BigDecimal totalAmount) {
		List<Split> targets = new ArrayList<>();
		if (!providedSplits.isEmpty()) {
			targets = providedSplits;
		} else if (expense.getGroup() != null && expense.getGroup().getMembers() != null && !expense.getGroup().getMembers().isEmpty()) {
			for (User member : expense.getGroup().getMembers()) {
				Split split = new Split();
				split.setExpense(expense);
				split.setUser(member);
				targets.add(split);
			}
		} else {
			throw new IllegalArgumentException("No participants provided for EQUAL split");
		}

		int n = targets.size();
		if (n <= 0) throw new IllegalArgumentException("Participants required for EQUAL split");

		BigDecimal equal = totalAmount.divide(BigDecimal.valueOf(n), 2, RoundingMode.HALF_EVEN);
		BigDecimal runningSum = BigDecimal.ZERO;
		for (int i = 0; i < n; i++) {
			Split split = targets.get(i);
			BigDecimal amount = (i == n - 1) ? totalAmount.subtract(runningSum) : equal;
			split.setAmount(amount);
			runningSum = runningSum.add(amount);
		}

		expense.setSplits(targets);
	}

	private void applyExactSplit(Expense expense, List<Split> providedSplits, BigDecimal totalAmount) {
		if (providedSplits.isEmpty()) throw new IllegalArgumentException("EXACT split requires splits with amounts");
		BigDecimal sum = BigDecimal.ZERO;
		for (Split split : providedSplits) {
			if (split.getAmount() == null) throw new IllegalArgumentException("Each split must include amount for EXACT split");
			if (split.getAmount().compareTo(BigDecimal.ZERO) < 0) throw new IllegalArgumentException("Split amount cannot be negative");
			split.setAmount(split.getAmount().setScale(2, RoundingMode.HALF_EVEN));
			sum = sum.add(split.getAmount());
		}
		if (sum.compareTo(totalAmount) != 0) throw new IllegalArgumentException("Sum of split amounts (" + sum + ") must equal total amount (" + totalAmount + ")");
		expense.setSplits(providedSplits);
	}

	private void applyPercentSplit(Expense expense, List<Split> providedSplits, BigDecimal totalAmount) {
		if (providedSplits.isEmpty()) throw new IllegalArgumentException("PERCENT split requires splits with contribution percents");
		BigDecimal percentSum = BigDecimal.ZERO;
		for (Split split : providedSplits) {
			if (split.getContribution() == null) throw new IllegalArgumentException("Each split must include contribution percent for PERCENT split");
			if (split.getContribution().compareTo(BigDecimal.ZERO) < 0) throw new IllegalArgumentException("Contribution percent cannot be negative");
			percentSum = percentSum.add(split.getContribution());
		}
		if (percentSum.compareTo(BigDecimal.valueOf(100)) != 0) throw new IllegalArgumentException("Total percent must equal 100");

		BigDecimal runningSum = BigDecimal.ZERO;
		for (int i = 0; i < providedSplits.size(); i++) {
			Split split = providedSplits.get(i);
			BigDecimal fraction = split.getContribution().divide(BigDecimal.valueOf(100), 6, RoundingMode.HALF_EVEN);
			BigDecimal amount = totalAmount.multiply(fraction).setScale(2, RoundingMode.HALF_EVEN);
			// Adjust last amount to fix rounding drift
			if (i == providedSplits.size() - 1) {
				amount = totalAmount.subtract(runningSum).setScale(2, RoundingMode.HALF_EVEN);
			}
			split.setAmount(amount);
			runningSum = runningSum.add(amount);
		}

		expense.setSplits(providedSplits);
	}
	
	@Override
	public List<Expense> getExpensesByGroupId(long groupId){
		return expenseRepository.findByGroupId(groupId);
	}
	
	@Override
	public Page<Expense> getExpensesByGroupId(long groupId, Pageable pageable){
		return expenseRepository.findByGroupId(groupId, pageable);
	}
	
	@Override
	public Map<Long, BigDecimal> calculateBalancesByGroup(long groupId){
		Map<Long, BigDecimal> balances = new HashMap<>();
		List<Expense> expenses = expenseRepository.findByGroupId(groupId);
		
		for(Expense expense : expenses) {
			long paidByUserId = expense.getPaidBy().getId();
			BigDecimal expenseAmount = expense.getAmount();
			
			//credit the user who paid
			balances.put(paidByUserId, balances.getOrDefault(paidByUserId, BigDecimal.ZERO).add(expenseAmount));
			
			//debit the users in split
			for(Split split : expense.getSplits()) {
				long splitUserId = split.getUser().getId();
				BigDecimal splitAmount = split.getAmount();
				balances.put(splitUserId, balances.getOrDefault(splitUserId, BigDecimal.ZERO).subtract(splitAmount));
			}	
		}
        // apply settlements: fromUser paid amount to toUser => reduce debt/increase payer balance, reduce receiver credit
        settlementRepository.findByGroupId(groupId).forEach(s -> {
            long fromId = s.getFromUser() != null ? s.getFromUser().getId() : 0L;
            long toId = s.getToUser() != null ? s.getToUser().getId() : 0L;
            BigDecimal amt = s.getAmount();
            if (fromId != 0L && amt != null) {
                balances.put(fromId, balances.getOrDefault(fromId, BigDecimal.ZERO).add(amt));
            }
            if (toId != 0L && amt != null) {
                balances.put(toId, balances.getOrDefault(toId, BigDecimal.ZERO).subtract(amt));
            }
        });
		return balances;
	}

	@Override
	public List<SettleTransactionResponse> calculateSettleUp(long groupId) {
		Map<Long, BigDecimal> balances = calculateBalancesByGroup(groupId);
		List<Map.Entry<Long, BigDecimal>> debtors = new ArrayList<>(); // negative balances
		List<Map.Entry<Long, BigDecimal>> creditors = new ArrayList<>(); // positive balances
		for (Map.Entry<Long, BigDecimal> e : balances.entrySet()) {
			int cmp = e.getValue().compareTo(BigDecimal.ZERO);
			if (cmp < 0) debtors.add(e);
			else if (cmp > 0) creditors.add(e);
		}

		// Sort for deterministic behavior (largest absolute first)
		debtors.sort((a,b) -> b.getValue().abs().compareTo(a.getValue().abs()));
		creditors.sort((a,b) -> b.getValue().compareTo(a.getValue()));

		List<SettleTransactionResponse> result = new ArrayList<>();
		int i = 0, j = 0;
		while (i < debtors.size() && j < creditors.size()) {
			Map.Entry<Long, BigDecimal> d = debtors.get(i);
			Map.Entry<Long, BigDecimal> c = creditors.get(j);
			BigDecimal owe = d.getValue().abs();
			BigDecimal receive = c.getValue();
			BigDecimal amount = owe.min(receive);
			if (amount.compareTo(BigDecimal.ZERO) > 0) {
				result.add(new SettleTransactionResponse(d.getKey(), c.getKey(), amount));
				// update balances
				debtors.set(i, Map.entry(d.getKey(), d.getValue().add(amount))); // less negative
				creditors.set(j, Map.entry(c.getKey(), c.getValue().subtract(amount)));
			}
			if (debtors.get(i).getValue().compareTo(BigDecimal.ZERO) == 0) i++;
			if (creditors.get(j).getValue().compareTo(BigDecimal.ZERO) == 0) j++;
		}
		return result;
	}
	
	
}
