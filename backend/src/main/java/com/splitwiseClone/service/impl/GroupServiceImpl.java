package com.splitwiseClone.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.splitwiseClone.model.Group;
import com.splitwiseClone.model.User;
import com.splitwiseClone.repository.GroupRepository;
import com.splitwiseClone.repository.UserRepository;
import com.splitwiseClone.service.GroupService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class GroupServiceImpl implements GroupService{
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    public GroupServiceImpl(GroupRepository groupRepository, UserRepository userRepository) {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
    }
    
    @Override
    public Group createGroup(String name) {
        Group newGroup = new Group();
        newGroup.setName(name);
        return groupRepository.save(newGroup);
    }

    @Override
    public Group addMember(long groupId, long userId) {
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new EntityNotFoundException("Group not found: " + groupId));
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found: " + userId));
        Set<User> members = group.getMembers();
        if (members == null) members = new HashSet<>();
        
        // Check if user is already a member
        if (members.contains(user)) {
            throw new IllegalArgumentException("User is already a member of this group");
        }
        
        members.add(user);
        group.setMembers(members);
        return groupRepository.save(group);
    }

    @Override
    public Group removeMember(long groupId, long userId) {
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new EntityNotFoundException("Group not found: " + groupId));
        Set<User> members = group.getMembers();
        if (members != null) {
            members.removeIf(u -> u.getId() == userId);
            group.setMembers(members);
        }
        return groupRepository.save(group);
    }

    @Override
    public Group getById(long groupId) {
        return groupRepository.findById(groupId).orElseThrow(() -> new EntityNotFoundException("Group not found: " + groupId));
    }

    @Override
    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }
}
