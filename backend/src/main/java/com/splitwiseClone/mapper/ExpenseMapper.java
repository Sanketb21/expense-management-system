package com.splitwiseClone.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.splitwiseClone.dto.ExpenseCreateRequest;
import com.splitwiseClone.dto.ExpenseResponse;
import com.splitwiseClone.dto.SplitRequest;
import com.splitwiseClone.dto.SplitResponse;
import com.splitwiseClone.model.Expense;
import com.splitwiseClone.model.Group;
import com.splitwiseClone.model.Split;
import com.splitwiseClone.model.User;

public class ExpenseMapper {

    public static Expense toEntity(ExpenseCreateRequest req) {
        Expense e = new Expense();
        e.setDescription(req.getDescription());
        e.setAmount(req.getAmount());

        User paidBy = new User();
        paidBy.setId(req.getPaidById());
        e.setPaidBy(paidBy);

        Group group = new Group();
        group.setId(req.getGroupId());
        e.setGroup(group);

        e.setSplitType(req.getSplitType());

        if (req.getSplits() != null && !req.getSplits().isEmpty()) {
            List<Split> splits = new ArrayList<>();
            for (SplitRequest sr : req.getSplits()) {
                Split s = new Split();
                User u = new User();
                u.setId(sr.getUserId());
                s.setUser(u);
                s.setAmount(sr.getAmount());
                s.setContribution(sr.getContribution());
                splits.add(s);
            }
            e.setSplits(splits);
        }
        return e;
    }

    public static ExpenseResponse toResponse(Expense expense) {
        ExpenseResponse r = new ExpenseResponse();
        r.setId(expense.getId());
        r.setDescription(expense.getDescription());
        r.setAmount(expense.getAmount());
        r.setCreatedAt(expense.getCreatedAt());
        r.setPaidById(expense.getPaidBy() != null ? expense.getPaidBy().getId() : null);
        r.setGroupId(expense.getGroup() != null ? expense.getGroup().getId() : null);
        r.setSplitType(expense.getSplitType());

        if (expense.getSplits() != null) {
            List<SplitResponse> splits = expense.getSplits().stream().map(s -> {
                SplitResponse sr = new SplitResponse();
                sr.setUserId(s.getUser() != null ? s.getUser().getId() : null);
                sr.setAmount(s.getAmount());
                sr.setContribution(s.getContribution());
                return sr;
            }).collect(Collectors.toList());
            r.setSplits(splits);
        }
        return r;
    }
}




