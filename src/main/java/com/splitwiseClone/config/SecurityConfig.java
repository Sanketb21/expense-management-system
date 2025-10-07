package com.splitwiseClone.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Bean //bcryptpassword encoder for hashing passwords
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	//this mean configures security filter chain
	//disabling csrf protection for our api and allowing public access to /users/register endpoint
	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers(HttpMethod.POST, "/users/register", "/users/login").permitAll()
                                .requestMatchers(HttpMethod.POST, "/groups/create").permitAll()
                                .requestMatchers(HttpMethod.POST, "/groups/{groupId}/members").permitAll()
                                .requestMatchers(HttpMethod.DELETE, "/groups/{groupId}/members/{userId}").permitAll()
                                .requestMatchers(HttpMethod.GET, "/groups/{groupId}").permitAll()
                                .requestMatchers(HttpMethod.POST, "/expenses/create").permitAll()
                                .requestMatchers(HttpMethod.POST, "/expenses/add_split").permitAll()
                                .requestMatchers(HttpMethod.GET, "/users").permitAll()
                                .requestMatchers(HttpMethod.GET, "/groups").permitAll()
                                .requestMatchers(HttpMethod.GET, "/expenses/{groupId}").permitAll()
                                .requestMatchers(HttpMethod.GET, "/expenses/{groupId}/balances").permitAll()
                                .requestMatchers(HttpMethod.GET, "/expenses/{groupId}/settle-up").permitAll()
                                .requestMatchers("/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**").permitAll()
                                .anyRequest().authenticated()
                )
                .csrf(csrf -> csrf.disable())
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}
