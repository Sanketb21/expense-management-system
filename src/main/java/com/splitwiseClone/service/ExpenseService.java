package com.splitwiseClone.service;

import java.util.List;

import com.splitwiseClone.model.Expense;
import com.splitwiseClone.model.Split;

public interface ExpenseService {
	Expense createExpense(Expense expense, List<Split> splits);
	List<Expense> getExpensesByGroupId(long groupId);
}
