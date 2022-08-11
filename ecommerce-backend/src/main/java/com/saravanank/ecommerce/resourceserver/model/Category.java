package com.saravanank.ecommerce.resourceserver.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.Data;

@Entity
@Data
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String name;
	
	@ElementCollection
	private List<String> subCategory;
	
	private Date creationDate = new Date();
	private Date modifiedDate = new Date();
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "modified_by")
	private User modifiedBy;
}
