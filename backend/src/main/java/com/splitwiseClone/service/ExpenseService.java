package com.splitwiseClone.service;

import java.math.BigDecimal;
import java.util.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.splitwiseClone.model.Expense;

public interface ExpenseService {
	Expense createExpense(Expense expense);
	List<Expense> getExpensesByGroupId(long groupId);
	Page<Expense> getExpensesByGroupId(long groupId, Pageable pageable);
	Map<Long, BigDecimal> calculateBalancesByGroup(long groupId);
	List<com.splitwiseClone.dto.SettleTransactionResponse> calculateSettleUp(long groupId);
}
