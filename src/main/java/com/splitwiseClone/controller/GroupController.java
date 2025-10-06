package com.splitwiseClone.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;

import com.splitwiseClone.model.Group;
import com.splitwiseClone.service.GroupService;
import com.splitwiseClone.dto.GroupCreateRequest;
import com.splitwiseClone.dto.GroupResponse;
import com.splitwiseClone.mapper.GroupMapper;

@RestController
@RequestMapping("/groups")
public class GroupController {
	private final GroupService groupService;

	public GroupController(GroupService groupService) {
		this.groupService = groupService;
	}
	
    @PostMapping("/create")
    public ResponseEntity<GroupResponse> createGroup(@Validated @RequestBody GroupCreateRequest req){
        Group newGroup = groupService.createGroup(req.getName());
        return new ResponseEntity<>(GroupMapper.toResponse(newGroup), HttpStatus.CREATED);
    }
}
