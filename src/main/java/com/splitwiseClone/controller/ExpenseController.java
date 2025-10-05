package com.splitwiseClone.controller;

import java.math.BigDecimal;
import java.util.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.splitwiseClone.model.Expense;
import com.splitwiseClone.service.ExpenseService;
import com.splitwiseClone.dto.ExpenseCreateRequest;
import com.splitwiseClone.dto.ExpenseResponse;
import com.splitwiseClone.mapper.ExpenseMapper;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {
	private final ExpenseService expenseService;

	public ExpenseController(ExpenseService expenseService) {
		this.expenseService = expenseService;
	}
	
	@PostMapping("/create")
	public ResponseEntity<ExpenseResponse> createExpense(@RequestBody ExpenseCreateRequest request){
		Expense expense = ExpenseMapper.toEntity(request);
		Expense createdExpense = expenseService.createExpense(expense);
		return ResponseEntity.ok(ExpenseMapper.toResponse(createdExpense));
	}	
	
	@GetMapping("/{groupId}")
	public ResponseEntity<List<ExpenseResponse>> getExpensesByGroupId(@PathVariable long groupId){
		List<Expense> expenses = expenseService.getExpensesByGroupId(groupId);
		List<ExpenseResponse> response = new ArrayList<>();
		for(Expense e: expenses){
			response.add(ExpenseMapper.toResponse(e));
		}
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/{groupId}/balances")
	public ResponseEntity<Map<Long, BigDecimal>> calculateBalances(@PathVariable long groupId){
		Map<Long, BigDecimal> balances = expenseService.calculateBalancesByGroup(groupId);
		return ResponseEntity.ok(balances);
	}
	
}
