package com.splitwiseClone.service;

import com.splitwiseClone.model.Group;

public interface GroupService {
	Group createGroup(String name);
    Group addMember(long groupId, long userId);
    Group removeMember(long groupId, long userId);
    Group getById(long groupId);
}
