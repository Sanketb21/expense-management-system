package com.splitwiseClone.service;

import com.splitwiseClone.model.User;

//defines the contract for UserService and whichever class implements it should must provide implementation for the methods mentioned
public interface UserService {
	User registerUser(String name, String email, String password);
}
