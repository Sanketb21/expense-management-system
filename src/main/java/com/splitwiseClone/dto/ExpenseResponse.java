package com.splitwiseClone.dto;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

import com.splitwiseClone.model.SplitType;

public class ExpenseResponse {
    private Long id;
    private String description;
    private BigDecimal amount;
    private ZonedDateTime createdAt;
    private Long paidById;
    private Long groupId;
    private SplitType splitType;
    private List<SplitResponse> splits;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public ZonedDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(ZonedDateTime createdAt) { this.createdAt = createdAt; }

    public Long getPaidById() { return paidById; }
    public void setPaidById(Long paidById) { this.paidById = paidById; }

    public Long getGroupId() { return groupId; }
    public void setGroupId(Long groupId) { this.groupId = groupId; }

    public SplitType getSplitType() { return splitType; }
    public void setSplitType(SplitType splitType) { this.splitType = splitType; }

    public List<SplitResponse> getSplits() { return splits; }
    public void setSplits(List<SplitResponse> splits) { this.splits = splits; }
}




