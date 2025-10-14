package com.splitwiseClone.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;

import com.splitwiseClone.model.Group;
import com.splitwiseClone.service.GroupService;
import com.splitwiseClone.dto.GroupCreateRequest;
import com.splitwiseClone.dto.GroupResponse;
import com.splitwiseClone.mapper.GroupMapper;
import com.splitwiseClone.dto.GroupMemberRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/groups")
@Tag(name = "Groups", description = "Group creation and management")
public class GroupController {
	private final GroupService groupService;

	public GroupController(GroupService groupService) {
		this.groupService = groupService;
	}
	
    @PostMapping("/create")
    @Operation(summary = "Create a group")
    public ResponseEntity<GroupResponse> createGroup(@Validated @RequestBody GroupCreateRequest req){
        Group newGroup = groupService.createGroup(req.getName());
        return new ResponseEntity<>(GroupMapper.toResponse(newGroup), HttpStatus.CREATED);
    }

    @PostMapping("/{groupId}/members")
    @Operation(summary = "Add member to group")
    public ResponseEntity<GroupResponse> addMember(@PathVariable long groupId, @Validated @RequestBody GroupMemberRequest req){
        Group updated = groupService.addMember(groupId, req.getUserId());
        return ResponseEntity.ok(GroupMapper.toResponse(updated));
    }

    @DeleteMapping("/{groupId}/members/{userId}")
    @Operation(summary = "Remove member from group")
    public ResponseEntity<GroupResponse> removeMember(@PathVariable long groupId, @PathVariable long userId){
        Group updated = groupService.removeMember(groupId, userId);
        return ResponseEntity.ok(GroupMapper.toResponse(updated));
    }

    @GetMapping("/{groupId}")
    @Operation(summary = "Get group by id")
    public ResponseEntity<GroupResponse> getGroup(@PathVariable long groupId){
        Group g = groupService.getById(groupId);
        return ResponseEntity.ok(GroupMapper.toResponse(g));
    }

    @GetMapping
    @Operation(summary = "Get all groups")
    public ResponseEntity<List<GroupResponse>> getAllGroups(){
        List<Group> groups = groupService.getAllGroups();
        List<GroupResponse> response = groups.stream()
            .map(GroupMapper::toResponse)
            .collect(java.util.stream.Collectors.toList());
        return ResponseEntity.ok(response);
    }
}
