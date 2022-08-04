package com.saravanank.ecommerce.resourceserver.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseModel {

	private List<Product> products;
	private long total;
	private int currentPage;
	private int limit;
	private long totalPages;
	
}
