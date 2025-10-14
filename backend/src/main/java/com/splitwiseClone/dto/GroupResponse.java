package com.splitwiseClone.dto;

import java.time.ZonedDateTime;
import java.util.List;

public class GroupResponse {
    private Long id;
    private String name;
    private ZonedDateTime createdAt;
    private List<UserResponse> members;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public ZonedDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(ZonedDateTime createdAt) { this.createdAt = createdAt; }
    public List<UserResponse> getMembers() { return members; }
    public void setMembers(List<UserResponse> members) { this.members = members; }
}


