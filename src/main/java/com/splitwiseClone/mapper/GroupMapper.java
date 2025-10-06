package com.splitwiseClone.mapper;

import com.splitwiseClone.dto.GroupResponse;
import com.splitwiseClone.model.Group;

public class GroupMapper {
    public static GroupResponse toResponse(Group g) {
        GroupResponse r = new GroupResponse();
        r.setId(g.getId());
        r.setName(g.getName());
        r.setCreatedAt(g.getCreatedAt());
        return r;
    }
}


