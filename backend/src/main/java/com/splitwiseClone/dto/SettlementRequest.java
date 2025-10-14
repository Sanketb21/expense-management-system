package com.splitwiseClone.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class SettlementRequest {
    @NotNull(message = "Group ID is required")
    @Min(value = 1, message = "Group ID must be positive")
    private Long groupId;

    @NotNull(message = "From user ID is required")
    @Min(value = 1, message = "From user ID must be positive")
    private Long fromUserId;

    @NotNull(message = "To user ID is required")
    @Min(value = 1, message = "To user ID must be positive")
    private Long toUserId;

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be positive")
    private BigDecimal amount;

    public Long getGroupId() { return groupId; }
    public void setGroupId(Long groupId) { this.groupId = groupId; }
    public Long getFromUserId() { return fromUserId; }
    public void setFromUserId(Long fromUserId) { this.fromUserId = fromUserId; }
    public Long getToUserId() { return toUserId; }
    public void setToUserId(Long toUserId) { this.toUserId = toUserId; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
}


