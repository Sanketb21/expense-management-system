package com.splitwiseClone.dto;

import java.math.BigDecimal;

public class SplitRequest {
    private Long userId;
    private BigDecimal amount;       // for EXACT
    private BigDecimal contribution; // percent for PERCENT

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public BigDecimal getContribution() { return contribution; }
    public void setContribution(BigDecimal contribution) { this.contribution = contribution; }
}




