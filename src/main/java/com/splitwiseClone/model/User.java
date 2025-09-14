package com.splitwiseClone.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity //tells springboot that this class is a jpa entity and should be mapped to a database table named as same as class name
@Table(name = "users") //explicitly set the table name as users to avoid conflicts with reserved sql keyword user
public class User {
	@Id //tells that this is a primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY)//tells that this primary key should be auto generated
	private long id;
	private String name;
	@Column(unique = true) //tells that no 2 users can have same value
	private String email;
	private String password;
	
	public User() {
	}

	public User(String name, String email, String password) {
		this.name = name;
		this.email = email;
		this.password = password;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
	
	
}
