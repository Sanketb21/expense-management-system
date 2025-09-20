package com.splitwiseClone.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.splitwiseClone.model.Expense;
import com.splitwiseClone.model.Split;
import com.splitwiseClone.repository.ExpenseRepository;
import com.splitwiseClone.repository.SplitRepository;
import com.splitwiseClone.service.ExpenseService;

@Service
public class ExpenseServiceImpl implements ExpenseService{
	private final ExpenseRepository expenseRepository;
	private final SplitRepository splitRepository;
	
	public ExpenseServiceImpl(ExpenseRepository expenseRepository, SplitRepository splitRepository) {
		this.expenseRepository = expenseRepository;
		this.splitRepository = splitRepository;
	}
	
	@Override
	public Expense createExpense(Expense expense, List<Split> splits) {
		//save the expense first to get its generated id
		Expense savedExpense = expenseRepository.save(expense);
		//set the expense for each split and then save the splits
		for(Split split : splits) {
			split.setExpense(savedExpense);
			splitRepository.save(split);
		}
		//return the saved expense
		return savedExpense;
	}
	
	@Override
	public List<Expense> getExpensesByGroupId(long groupId){
		return expenseRepository.findByGroupId(groupId);
	}
	
	
}
