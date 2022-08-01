package com.saravanank.ecommerce.resourceserver.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saravanank.ecommerce.resourceserver.model.Product;
import com.saravanank.ecommerce.resourceserver.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepo;
	
	public Product addProduct(Product product) {
		return this.productRepo.save(product);
	}
	
	public Product updateProduct(Product product) {
		return this.productRepo.save(product);
	}
	
	public List<Product> addProducts(List<Product> products) {
		return this.productRepo.saveAll(products);
	}
	
	public List<Product> getAllProducts() {
		return this.productRepo.findAll();
	}
	
}
