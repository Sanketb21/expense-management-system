package com.splitwiseClone.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.splitwiseClone.model.User;
import com.splitwiseClone.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
	private final UserService userService;

	//inject userService here to use its methods
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	//handles http POST request to the /users/register endpoint
	//@RequestBody maps the json data from request body to a user object
	@PostMapping("/register")
	public ResponseEntity<User> registerUser(@RequestBody User user){
		User registeredUser = userService.registerUser(
				user.getName(),
				user.getEmail(),
				user.getPassword());
		//if user is registered successfully, then return the user object with a 201 created status code
		return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
	}
	
	
}
