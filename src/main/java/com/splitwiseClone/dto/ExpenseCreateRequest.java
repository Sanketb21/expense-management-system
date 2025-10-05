package com.splitwiseClone.dto;

import java.math.BigDecimal;
import java.util.List;

import com.splitwiseClone.model.SplitType;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class ExpenseCreateRequest {
    @NotBlank
    private String description;

    @NotNull
    @Positive
    private BigDecimal amount;

    @NotNull
    @Min(1)
    private Long paidById;

    @NotNull
    @Min(1)
    private Long groupId;

    @NotNull
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




