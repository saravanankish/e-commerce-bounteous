package com.saravanank.ecommerce.resourceserver.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long productId;
	private String name;
	private String description;
	private float price;
	private int quantity;
	private float rating;
	private String thumbnail;
	
	@ElementCollection
	private List<String> images;
	
	@OneToOne(cascade = CascadeType.MERGE)
	private Brand brand;
	
	private String category;
	
}
