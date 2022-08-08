package com.saravanank.ecommerce.resourceserver.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.saravanank.ecommerce.resourceserver.model.Product;
import com.saravanank.ecommerce.resourceserver.model.ProductResponseModel;
import com.saravanank.ecommerce.resourceserver.service.ProductService;

@RestController
@RequestMapping("/v1/products")
public class ProductController {

	@Autowired
	private ProductService prodService;

	@GetMapping
	public ResponseEntity<ProductResponseModel> getAllProducts(@RequestParam(required = false, name = "limit") Integer limit,
			@RequestParam(required = false, name = "page") Integer page) {
		if(page == null ) page = 0;
		if(limit == null) limit = 12;
		return new ResponseEntity<ProductResponseModel>(prodService.getAllProducts(page, limit), HttpStatus.OK);
	}

	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<Product> addProduct(@RequestBody Product product) {
		return new ResponseEntity<Product>(prodService.addProduct(product), HttpStatus.CREATED);
	}

	@PostMapping("/all")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<List<Product>> addProducts(@RequestBody List<Product> products) {
		return new ResponseEntity<List<Product>>(prodService.addProducts(products), HttpStatus.CREATED);
	}

	@PutMapping
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<Product> updateProduct(@RequestBody Product product) {
		return new ResponseEntity<Product>(prodService.updateProduct(product), HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{productId}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<String> deleteProduct(@PathVariable("productId") long productId) {
		prodService.deleteProduct(productId);
		return new ResponseEntity<String>("Deleted successfully", HttpStatus.NO_CONTENT);
	}
}
