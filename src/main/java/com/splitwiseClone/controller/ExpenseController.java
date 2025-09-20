package com.splitwiseClone.controller;

import java.util.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.splitwiseClone.model.Expense;
import com.splitwiseClone.service.ExpenseService;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {
	private final ExpenseService expenseService;

	public ExpenseController(ExpenseService expenseService) {
		this.expenseService = expenseService;
	}
	
	@PostMapping("/create")
	public ResponseEntity<Expense> createExpense(@RequestBody Expense expense){
		// we will pass the expense object directly to the service which will then handle the splits
		// the service will set the expense for each split internally
		Expense createdExpense = expenseService.createExpense(expense, expense.getSplits());
		return new ResponseEntity<>(createdExpense, HttpStatus.CREATED);
	}	
	
	@GetMapping("/{groupId}")
	public ResponseEntity<List<Expense>> getExpensesByGroupId(@PathVariable long groupId){
		List<Expense> expenses = expenseService.getExpensesByGroupId(groupId);
		return new ResponseEntity<>(expenses, HttpStatus.OK);
	}
	
	
}
