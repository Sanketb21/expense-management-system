package com.splitwiseClone.dto;

import java.math.BigDecimal;
import java.util.List;

import com.splitwiseClone.model.SplitType;

public class ExpenseCreateRequest {
    private String description;
    private BigDecimal amount;
    private Long paidById;
    private Long groupId;
    private SplitType splitType; // EQUAL, EXACT, PERCENT
    private List<SplitRequest> splits;

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public Long getPaidById() { return paidById; }
    public void setPaidById(Long paidById) { this.paidById = paidById; }

    public Long getGroupId() { return groupId; }
    public void setGroupId(Long groupId) { this.groupId = groupId; }

    public SplitType getSplitType() { return splitType; }
    public void setSplitType(SplitType splitType) { this.splitType = splitType; }

    public List<SplitRequest> getSplits() { return splits; }
    public void setSplits(List<SplitRequest> splits) { this.splits = splits; }
}




