package com.splitwiseClone.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.splitwiseClone.dto.GroupResponse;
import com.splitwiseClone.dto.UserResponse;
import com.splitwiseClone.model.Group;

public class GroupMapper {
    public static GroupResponse toResponse(Group g) {
        GroupResponse r = new GroupResponse();
        r.setId(g.getId());
        r.setName(g.getName());
        r.setCreatedAt(g.getCreatedAt());
        
        if (g.getMembers() != null) {
            List<UserResponse> members = g.getMembers().stream()
                .map(UserMapper::toResponse)
                .collect(Collectors.toList());
            r.setMembers(members);
        } else {
            r.setMembers(new ArrayList<>());
        }
        
        return r;
    }
}


