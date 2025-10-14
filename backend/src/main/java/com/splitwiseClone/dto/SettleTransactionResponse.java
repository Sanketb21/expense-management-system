package com.splitwiseClone.dto;

import java.math.BigDecimal;

public class SettleTransactionResponse {
    private Long fromUserId;
    private Long toUserId;
    private BigDecimal amount;

    public SettleTransactionResponse() {}

    public SettleTransactionResponse(Long fromUserId, Long toUserId, BigDecimal amount) {
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.amount = amount;
    }

    public Long getFromUserId() { return fromUserId; }
    public void setFromUserId(Long fromUserId) { this.fromUserId = fromUserId; }

    public Long getToUserId() { return toUserId; }
    public void setToUserId(Long toUserId) { this.toUserId = toUserId; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
}


