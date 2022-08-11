package com.saravanank.ecommerce.resourceserver.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
	private Date expectedDeliveryDate;
	private Date deliveryDate;
	private Date cancelDate;
	private String cancelReason;
	private Date modifiedDate;
	private float taxPercentage;
	private float totalValue;
	private boolean isClosed = false;
	
	@Enumerated(EnumType.STRING)
	private PaymentType paymentType;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id")
	private User user;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "placed_by")
	private User placedBy;
	
	@Enumerated(EnumType.STRING)
	private OrderStatus orderStatus;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "order_id")
	private List<ProductQuantityMapper> products;

}
