package com.saravanank.ecommerce.resourceserver.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "address")
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String doorNo;
	private String street;
	private String area;
	private String city;
	private String state;
	private int pincode;
	private String landmark;
	private String label;
	private boolean isDeliveryAddress = false;
	
	@Enumerated(EnumType.STRING)
	private DeliveryTimingType deliveryTimeType;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "contact_id")
	private MobileNumber phoneNumber;
	
}
