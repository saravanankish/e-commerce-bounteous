package com.saravanank.authorizationserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.saravanank.authorizationserver.model.User;


@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

	public User findByUsername(String username);
	
}
