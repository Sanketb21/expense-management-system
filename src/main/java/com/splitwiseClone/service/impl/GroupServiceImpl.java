package com.splitwiseClone.service.impl;

import org.springframework.stereotype.Service;

import com.splitwiseClone.model.Group;
import com.splitwiseClone.repository.GroupRepository;
import com.splitwiseClone.service.GroupService;

@Service
public class GroupServiceImpl implements GroupService{
	private final GroupRepository groupRepository;

	public GroupServiceImpl(GroupRepository groupRepository) {
		this.groupRepository = groupRepository;
	}
	
	@Override
	public Group createGroup(String name) {
		Group newGroup = new Group();
		newGroup.setName(name);
		return groupRepository.save(newGroup);
	}
	
}
