package com.splitwiseClone.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class GroupMemberRequest {
    @NotNull
    @Min(1)
    private Long userId;

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
}


