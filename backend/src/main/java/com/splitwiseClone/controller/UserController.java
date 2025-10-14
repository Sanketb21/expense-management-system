package com.splitwiseClone.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;

import com.splitwiseClone.model.User;
import com.splitwiseClone.service.UserService;
import com.splitwiseClone.dto.UserRegisterRequest;
import com.splitwiseClone.dto.UserLoginRequest;
import com.splitwiseClone.dto.UserResponse;
import com.splitwiseClone.mapper.UserMapper;
import com.splitwiseClone.dto.AuthResponse;
import com.splitwiseClone.config.JwtUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/users")
@Tag(name = "Users", description = "User registration and login")
public class UserController {
    private final UserService userService;
    private final JwtUtil jwtUtil;

    //inject services here to use their methods
    public UserController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }
	
	//handles http POST request to the /users/register endpoint
	//@RequestBody maps the json data from request body to a user object
    @PostMapping("/register")
    @Operation(summary = "Register a new user")
    public ResponseEntity<UserResponse> registerUser(@Validated @RequestBody UserRegisterRequest req){
        User registeredUser = userService.registerUser(
                req.getName(),
                req.getEmail(),
                req.getPassword());
        return new ResponseEntity<>(UserMapper.toResponse(registeredUser), HttpStatus.CREATED);
    }
	
    @PostMapping("/login")
    @Operation(summary = "Login a user")
    public ResponseEntity<AuthResponse> loginUser(@Validated @RequestBody UserLoginRequest req){
        User loggedInUser = userService.loginUser(req.getEmail(), req.getPassword());
        String token = jwtUtil.generateToken(loggedInUser.getEmail());
        return new ResponseEntity<>(new AuthResponse(token), HttpStatus.OK);
    }
	
	
}
