package com.splitwiseClone.controller;

import java.math.BigDecimal;
import java.util.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.splitwiseClone.model.Expense;
import com.splitwiseClone.service.ExpenseService;
import com.splitwiseClone.dto.ExpenseCreateRequest;
import com.splitwiseClone.dto.ExpenseResponse;
import com.splitwiseClone.mapper.ExpenseMapper;
import com.splitwiseClone.dto.BalanceResponse;
import com.splitwiseClone.dto.SettleTransactionResponse;
import com.splitwiseClone.dto.PagedResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/expenses")
@Tag(name = "Expenses", description = "Create and query expenses and balances")
public class ExpenseController {
	private final ExpenseService expenseService;

	public ExpenseController(ExpenseService expenseService) {
		this.expenseService = expenseService;
	}
	
    @PostMapping("/create")
    @Operation(summary = "Create an expense", description = "Creates an expense with split strategy and returns the created expense")
    public ResponseEntity<ExpenseResponse> createExpense(@Validated @RequestBody ExpenseCreateRequest request){
		Expense expense = ExpenseMapper.toEntity(request);
		Expense createdExpense = expenseService.createExpense(expense);
		return ResponseEntity.ok(ExpenseMapper.toResponse(createdExpense));
	}	
	
    @GetMapping("/{groupId}")
    @Operation(summary = "List expenses for a group")
    public ResponseEntity<List<ExpenseResponse>> getExpensesByGroupId(@PathVariable long groupId){
        List<Expense> expenses = expenseService.getExpensesByGroupId(groupId);
        List<ExpenseResponse> response = new ArrayList<>();
        for(Expense e: expenses){
            response.add(ExpenseMapper.toResponse(e));
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{groupId}/paginated")
    @Operation(summary = "List expenses for a group with pagination", description = "Supports page, size, and sort parameters")
    public ResponseEntity<PagedResponse<ExpenseResponse>> getExpensesByGroupIdPaginated(
            @PathVariable long groupId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {
        
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
            Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        
        Page<Expense> expensePage = expenseService.getExpensesByGroupId(groupId, pageable);
        
        List<ExpenseResponse> content = expensePage.getContent().stream()
            .map(ExpenseMapper::toResponse)
            .collect(java.util.stream.Collectors.toList());
        
        PagedResponse<ExpenseResponse> response = new PagedResponse<>(
            content,
            expensePage.getNumber(),
            expensePage.getSize(),
            expensePage.getTotalElements(),
            expensePage.getTotalPages(),
            expensePage.isFirst(),
            expensePage.isLast()
        );
        
        return ResponseEntity.ok(response);
    }
	
    @GetMapping("/{groupId}/balances")
    @Operation(summary = "Compute balances for a group", description = "Returns net balance per user for the group")
    public ResponseEntity<List<BalanceResponse>> calculateBalances(@PathVariable long groupId){
        Map<Long, BigDecimal> balances = expenseService.calculateBalancesByGroup(groupId);
        List<BalanceResponse> response = new ArrayList<>();
        for (Map.Entry<Long, BigDecimal> e : balances.entrySet()) {
            response.add(new BalanceResponse(e.getKey(), e.getValue()));
        }
        return ResponseEntity.ok(response);
	}

    @GetMapping("/{groupId}/settle-up")
    @Operation(summary = "Compute minimal settle-up transactions", description = "Returns who should pay whom and how much to settle balances")
	public ResponseEntity<List<SettleTransactionResponse>> settleUp(@PathVariable long groupId){
		List<SettleTransactionResponse> txns = expenseService.calculateSettleUp(groupId);
		return ResponseEntity.ok(txns);
	}
	
}
