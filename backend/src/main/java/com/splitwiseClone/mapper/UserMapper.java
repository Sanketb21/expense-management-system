package com.splitwiseClone.mapper;

import com.splitwiseClone.dto.UserResponse;
import com.splitwiseClone.model.User;

public class UserMapper {
    public static UserResponse toResponse(User user) {
        UserResponse r = new UserResponse();
        r.setId(user.getId());
        r.setName(user.getName());
        r.setEmail(user.getEmail());
        return r;
    }
}


