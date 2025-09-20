package com.splitwiseClone.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.splitwiseClone.model.Group;
import com.splitwiseClone.service.GroupService;

@RestController
@RequestMapping("/groups")
public class GroupController {
	private final GroupService groupService;

	public GroupController(GroupService groupService) {
		this.groupService = groupService;
	}
	
	@PostMapping("/create")
	public ResponseEntity<Group> createGroup(@RequestBody Group group){
		Group newGroup = groupService.createGroup(group.getName());
		return new ResponseEntity<>(newGroup, HttpStatus.CREATED);
	}
}
