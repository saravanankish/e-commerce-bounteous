package com.saravanank.ecommerce.resourceserver.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.saravanank.ecommerce.resourceserver.model.Product;
import com.saravanank.ecommerce.resourceserver.model.ProductResponseModel;
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
		return (List<Product>) this.productRepo.saveAll(products);
	}
	
	public ProductResponseModel getAllProducts(Integer page, Integer limit) {
		PageRequest pageReq = PageRequest.of(page, limit);
		ProductResponseModel productResponse = new ProductResponseModel();
		productResponse.setProducts(this.productRepo.findAll(pageReq).toList());
		productResponse.setTotal(productRepo.count());
		productResponse.setCurrentPage(page);
		productResponse.setLimit(limit);
		productResponse.setTotalPages(productRepo.count() / limit);
		return productResponse;
	}
	
	public void deleteProduct(long productId) {
		boolean productPresent = productRepo.existsById(productId);
		if(!productPresent) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
		productRepo.deleteById(productId);
	}
}
