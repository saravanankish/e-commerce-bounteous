package com.saravanank.ecommerce.resourceserver.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.saravanank.ecommerce.resourceserver.model.User;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {

	public User findByUsername(String username);
	
	public List<User> findByRole(String role);
	
}
