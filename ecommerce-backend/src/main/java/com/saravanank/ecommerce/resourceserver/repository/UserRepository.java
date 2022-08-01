package com.saravanank.ecommerce.resourceserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.saravanank.ecommerce.resourceserver.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	public User findByUsername(String username);
	
}
