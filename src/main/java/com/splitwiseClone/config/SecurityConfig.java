package com.splitwiseClone.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
	
	@Bean //bcryptpassword encoder for hashing passwords
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	//this mean configures security filter chain
	//disabling csrf protection for our api and allowing public access to /users/register endpoint
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		http
			.csrf(csrf -> csrf.disable()) //disabling csrf for api
			.authorizeHttpRequests(authorize -> authorize
					.requestMatchers("/users/**", "/groups/create", "/expenses/create", "/expenses/{groupId}").permitAll() //allowing public access to register endpoint
					.anyRequest().authenticated() //all other requests require authentication
					);
		return http.build();
	}
}
