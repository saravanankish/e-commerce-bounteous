package com.saravanank.ecommerce.resourceserver.model;

import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "orders")
public class Order {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long orderId;

	private float value;
	
	private Date orderDate;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user")
	private User user;
	
	private String orderStatus;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "products")
	private List<ProductQuantityMapper> products;

}
