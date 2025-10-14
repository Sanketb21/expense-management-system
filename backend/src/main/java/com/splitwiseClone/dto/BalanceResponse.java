package com.splitwiseClone.dto;

import java.math.BigDecimal;

public class BalanceResponse {
    private Long userId;
    private BigDecimal balance; // positive => owed to user; negative => user owes

    public BalanceResponse() {}
    public BalanceResponse(Long userId, BigDecimal balance) {
        this.userId = userId;
        this.balance = balance;
    }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public BigDecimal getBalance() { return balance; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }
}


