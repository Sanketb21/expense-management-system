package com.splitwiseClone.service;

import java.util.List;
import com.splitwiseClone.model.Group;

public interface GroupService {
	Group createGroup(String name);
    Group addMember(long groupId, long userId);
    Group removeMember(long groupId, long userId);
    Group getById(long groupId);
    List<Group> getAllGroups();
}
