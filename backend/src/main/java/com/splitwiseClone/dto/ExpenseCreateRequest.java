package com.splitwiseClone.dto;

import java.math.BigDecimal;
import java.util.List;

import com.splitwiseClone.model.SplitType;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class ExpenseCreateRequest {
    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be positive")
    private BigDecimal amount;

    @NotNull(message = "Paid by user ID is required")
    @Min(value = 1, message = "Paid by user ID must be positive")
    private Long paidById;

    @NotNull(message = "Group ID is required")
    @Min(value = 1, message = "Group ID must be positive")
    private Long groupId;

    @NotNull(message = "Split type is required")
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




