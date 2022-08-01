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
@NoArgsConstructor
@AllArgsConstructor
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int productId;
	private String name;
	private String description;
	private double price;
	private int quantity;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "thumbnail")
	private Image thumbnail;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "image_url")
	private List<Image> images;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "brand")
	private Brand brand;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "category")
	private SubCategory category;
	
	
}
