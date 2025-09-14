package com.splitwiseClone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.splitwiseClone.model.User;

@Repository //tells springboot that interface is a spring data repository
public interface UserRepository extends JpaRepository<User, Long>{
	User findByEmail(String email);
}
