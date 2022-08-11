package com.saravanank.ecommerce.resourceserver.controller;

import java.security.Principal;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saravanank.ecommerce.resourceserver.model.User;
import com.saravanank.ecommerce.resourceserver.service.UserService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/v1/user")
public class UserController {
	
	private static final Logger logger = Logger.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@GetMapping
	@ApiOperation(value = "Get user data", notes = "All users can use this endpoint, returns user data of current authenticated user")
	public ResponseEntity<User> getUserData(Principal principal) {
		logger.info("GET request to /api/v1/user");
		return new ResponseEntity<User>(userService.getUserByUsername(principal.getName()), HttpStatus.OK);
	}

	@GetMapping("/{userId}")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_CUSTOMER')")
	@ApiOperation(value = "Get user by id", notes = "Only user with admin or customer access can use this endpoint")
	public ResponseEntity<User> getUser(@PathVariable("userId") long userId) {
		logger.info("GET request to /api/v1/user/" + userId);
		return new ResponseEntity<User>(userService.getUserById(userId), HttpStatus.OK);
	}

	@PostMapping("/register")
	@ApiOperation(value = "Register customer", notes = "This is an open endpoint to register user")
	public User registerUser(@RequestBody User user) {
		logger.info("POST request to /api/v1/user/register");
		return userService.addUser(user);
	}

	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public User addUser(@RequestBody User user) {
		logger.info("POST request to /api/v1/user");
		return userService.addUser(user);
	}

	@GetMapping("/customer")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_SUPPORT')")
	@ApiOperation(value = "Get all customer", notes = "Only user with admin or support access can use this endpoint")
	public ResponseEntity<List<User>> getAllCustomers() {
		logger.info("GET request to /api/v1/user/customer");
		return new ResponseEntity<List<User>>(userService.getCustomers(), HttpStatus.OK);
	}

	@PutMapping("/{customerId}")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_CUSTOMER')")
	@ApiOperation(value = "Get customer by id", notes = "Only user with admin or support access can use this endpoint")
	public ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable("customerId") long customerId, Principal principal) {
		logger.info("GET request to /api/v1/user/" + customerId);
		return new ResponseEntity<User>(userService.updateUser(user, customerId, principal.getName()), HttpStatus.CREATED);
	}

	@DeleteMapping("/{userId}")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_CUSTOMER')")
	@ApiOperation(value = "Delete a user", notes = "Only user with admin or customer access can use this endpoint")
	public ResponseEntity<Void> deleteUser(@PathVariable("userId") long userId) {
		logger.info("DELETE request to /api/v1/user/" + userId);
		userService.deleteUser(userId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
