package com.saravanank.ecommerce.resourceserver.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userId;
	private String name;
	private String email;
	private String username;
	private String password;
	private String role;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "cart")
	private Cart cart;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id")
	private List<Address> addresses;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "delivery_address")
	private Address deliveryAddress;
	
	@OneToMany(cascade =  CascadeType.ALL)
	@JoinColumn(name = "user")
	private List<Order> orders;
}
