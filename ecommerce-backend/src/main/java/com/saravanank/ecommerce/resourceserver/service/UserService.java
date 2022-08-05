package com.saravanank.ecommerce.resourceserver.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.saravanank.ecommerce.resourceserver.model.Cart;
import com.saravanank.ecommerce.resourceserver.model.User;
import com.saravanank.ecommerce.resourceserver.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("No user found");
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), true,
				true, true, true, getAuthorities(Arrays.asList(user.getRole())));
	}

	private Collection<? extends GrantedAuthority> getAuthorities(List<String> roles) {
		// TODO Auto-generated method stub
		Set<GrantedAuthority> authorities = new HashSet<>();
		for (String role : roles) {
			authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
		}
		return authorities;
	}

	public User addUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		if (user.getRole().equals("CUSTOMER"))
			user.setCart(new Cart());
		return userRepo.save(user);
	}

	public User getUserByUsername(String username) {
		return userRepo.findByUsername(username);
	}

	public List<User> getCustomers() {
		return userRepo.findByRole("CUSTOMER");
	}

	public User getUserById(long id) {
		Optional<User> user = userRepo.findById(id);
		if (user.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
		return user.get();
	}

	public User updateUser(User user, long customerId) {
		if (customerId == 0)
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User id should be present");
		Optional<User> userData = userRepo.findById(customerId);
		if (userData.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
		User userInDb = userData.get();
		if(user.getName() != null) userInDb.setName(user.getName());
		if(user.getEmail() != null) userInDb.setEmail(user.getEmail());
		if(user.getUsername() != null) userInDb.setUsername(user.getUsername());
		if(user.getRole() != null) userInDb.setRole(user.getRole()); 
		userRepo.saveAndFlush(userInDb);
		return userInDb;
	}
	
	public void deleteUser(long id) {
		Optional<User> user = userRepo.findById(id);
		if (user.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
		User userData = user.get();
		userData.setAccountActive(false);
		userRepo.save(userData);
	}
}
