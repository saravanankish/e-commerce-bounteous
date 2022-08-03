package com.saravanank.ecommerce.resourceserver.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "product_quantity")
public class ProductQuantityMapper {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@OneToOne(cascade =  CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinColumn(name = "product_quantity")
	private Product product;
	
	private int quantity;

	public long getProductId() {
		return product.getProductId();
	}
 }
