package com.splitwiseClone.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.splitwiseClone.model.Expense;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long>{
	@Query("SELECT e FROM Expense e LEFT JOIN FETCH e.splits s LEFT JOIN FETCH e.paidBy p LEFT JOIN FETCH e.group g WHERE e.group.id = :groupId")
	List<Expense> findByGroupId(long groupId);
}
