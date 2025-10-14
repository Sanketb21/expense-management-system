package com.splitwiseClone.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class GroupMemberRequest {
    @NotNull(message = "User ID is required")
    @Min(value = 1, message = "User ID must be positive")
    private Long userId;

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
}


