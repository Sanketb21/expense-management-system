package com.splitwiseClone.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.splitwiseClone.exceptions.InvalidCredentialsException;
import com.splitwiseClone.exceptions.UserAlreadyExistsException;
import com.splitwiseClone.exceptions.UserNotFoundException;
import com.splitwiseClone.model.User;
import com.splitwiseClone.repository.UserRepository;
import com.splitwiseClone.service.UserService;

@Service //tells springboot that this will be a service component and it will create a single instance of this class and manage it
public class UserServiceImpl implements UserService{
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	
	//constructor injection for getting instance of UserRepository & PasswordEncoder
	public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	@Override
	public User registerUser(String name, String email, String password) {
		//check if a user with same email address already exists or not
		if(userRepository.findByEmail(email) != null) {
			throw new UserAlreadyExistsException("User with email " + email + " already exists.");
		}
		
		//hash the password before saving the user
		String hashedPassword = passwordEncoder.encode(password);
		
		//create a new user with provided details and hashed password
		User newUser = new User(name, email, hashedPassword);
		
		return userRepository.save(newUser);
	}
	
	public User loginUser(String email, String password) {
		//find user by email
		User user = userRepository.findByEmail(email);
		
		//if user is not found, throw exception
		if(user == null) {
			throw new UserNotFoundException("User with email " + email + " not found.");
		}
		
		//compare password with stored hashed password
		if(!passwordEncoder.matches(password, user.getPassword())) {
			throw new InvalidCredentialsException("Invalid email or password.");
		}
		
		//if credentials are valid then return user
		return user;
	}
}
