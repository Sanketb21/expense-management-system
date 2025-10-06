package com.splitwiseClone.service;

import java.math.BigDecimal;
import java.util.*;

import com.splitwiseClone.model.Expense;

public interface ExpenseService {
	Expense createExpense(Expense expense);
	List<Expense> getExpensesByGroupId(long groupId);
	Map<Long, BigDecimal> calculateBalancesByGroup(long groupId);
	List<com.splitwiseClone.dto.SettleTransactionResponse> calculateSettleUp(long groupId);
}
