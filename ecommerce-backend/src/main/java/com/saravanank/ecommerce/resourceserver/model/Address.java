package com.saravanank.ecommerce.resourceserver.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String doorNo;
	private String street;
	private String area;
	private String city;
	private String state;
	private int pincode;
	private String landmark;
	
}
