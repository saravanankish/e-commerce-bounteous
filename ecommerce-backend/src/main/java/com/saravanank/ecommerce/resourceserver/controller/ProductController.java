package com.saravanank.ecommerce.resourceserver.controller;

import java.util.List;

import org.apache.log4j.Logger;
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

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/v1/products")
public class ProductController {

	private static final Logger logger = Logger.getLogger(ProductController.class);

	@Autowired
	private ProductService prodService;

	@GetMapping("/{productId}")
	@ApiOperation(value = "Get product by id", notes = "This is an open endpoint")
	public ResponseEntity<Product> getProductById(@PathVariable("productId") long productId) {
		logger.info("GET request to /api/v1/products/" + productId);
		return new ResponseEntity<Product>(prodService.getProductById(productId), HttpStatus.OK);
	}

	@GetMapping
	@ApiOperation(value = "Get all products", notes = "This is an open endpoint")
	public ResponseEntity<ProductResponseModel> getAllProducts(
			@RequestParam(required = false, name = "limit") Integer limit,
			@RequestParam(required = false, name = "page") Integer page,
			@RequestParam(required = false, name = "search") String search) {
		logger.info("GET request to /api/v1/products?page=" + page + "&limit=" + limit);
		if (page == null)
			page = 0;
		if (limit == null)
			limit = 12;
		if (limit > 100)
			limit = 100;
		return new ResponseEntity<ProductResponseModel>(prodService.getAllProducts(page, limit, search), HttpStatus.OK);
	}

	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@ApiOperation(value = "Add a product", notes = "Only user with admin access can use this endpoint")
	public ResponseEntity<Product> addProduct(@RequestBody Product product) {
		logger.info("POST request to /api/v1/products");
		return new ResponseEntity<Product>(prodService.addProduct(product), HttpStatus.CREATED);
	}

	@PostMapping("/all")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@ApiOperation(value = "Add many products", notes = "Only user with admin access can use this endpoint")
	public ResponseEntity<List<Product>> addProducts(@RequestBody List<Product> products) {
		logger.info("POST request to /api/v1/products/all");
		return new ResponseEntity<List<Product>>(prodService.addProducts(products), HttpStatus.CREATED);
	}

	@PutMapping("/{productId}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@ApiOperation(value = "Update a product", notes = "Only user with admin access can use this endpoint")
	public ResponseEntity<Product> updateProduct(@RequestBody Product product,
			@PathVariable("productId") long productId) {
		logger.info("PUT request to /api/v1/products");
		return new ResponseEntity<Product>(prodService.updateProduct(product, productId), HttpStatus.CREATED);
	}

	@DeleteMapping("/{productId}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@ApiOperation(value = "Delete a product", notes = "Only user with admin access can use this endpoint")
	public ResponseEntity<String> deleteProduct(@PathVariable("productId") long productId) {
		logger.info("DELET request to /api/v1/products/" + productId);
		prodService.deleteProduct(productId);
		return new ResponseEntity<String>("Deleted successfully", HttpStatus.NO_CONTENT);
	}

}
