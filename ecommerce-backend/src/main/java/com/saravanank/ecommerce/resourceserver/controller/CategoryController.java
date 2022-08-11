package com.saravanank.ecommerce.resourceserver.controller;

import java.security.Principal;
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
import org.springframework.web.bind.annotation.RestController;

import com.saravanank.ecommerce.resourceserver.model.Category;
import com.saravanank.ecommerce.resourceserver.service.CategoryService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/v1/category")
public class CategoryController {
	
	private static final Logger logger = Logger.getLogger(CartController.class);

	@Autowired
	private CategoryService categoryService;

	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@ApiOperation(value = "Add category", notes = "Only user with admin access can use this endpoint")
	public ResponseEntity<Category> addCategory(@RequestBody Category subCategory, Principal principal) {
		logger.info("POST request to /api/v1/category");
		return new ResponseEntity<Category>(categoryService.addSubCategory(subCategory, principal.getName()), HttpStatus.CREATED);
	}

	@PostMapping("/all")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@ApiOperation(value = "Add many categories", notes = "Only user with admin access can use this endpoint")
	public ResponseEntity<List<Category>> addCategories(@RequestBody List<Category> subCategories, Principal principal) {
		logger.info("POST request to /api/v1/category/all");
		return new ResponseEntity<List<Category>>(categoryService.addSubCategories(subCategories, principal.getName()), HttpStatus.CREATED);
	}

	@GetMapping("/{categoryId}")
	@ApiOperation(value = "Get category by id", notes = "All users can use this endpoint")
	public ResponseEntity<Category> getCategoryById(@PathVariable("categoryId") long categoryId) {
		logger.info("GET request to /api/v1/category/" + categoryId);
		return new ResponseEntity<Category>(categoryService.getCategoryById(categoryId), HttpStatus.OK);
	}

	@GetMapping("/all")
	@ApiOperation(value = "Get all categories", notes = "All users can use this endpoint")
	public ResponseEntity<List<Category>> getAllCategories() {
		logger.info("GET request to /api/v1/category/all");
		return new ResponseEntity<List<Category>>(categoryService.getAllCategories(), HttpStatus.OK);
	}
	
	@GetMapping("/subcategory")
	@ApiOperation(value = "Get all categories", notes = "All users can use this endpoint")
	public ResponseEntity<List<String>> getAllSubCategories() {
		logger.info("GET request to /api/v1/category/all");
		return new ResponseEntity<List<String>>(categoryService.getAllSubCategories(), HttpStatus.OK);
	}

	@PutMapping("/{categoryId}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@ApiOperation(value = "Update a category", notes = "Only user with admin access can use this endpoint")
	public ResponseEntity<Category> updateCategory(@PathVariable("categoryId") long categoryId,
			@RequestBody Category category, Principal principal) {
		logger.info("PUT request to /api/v1/category/" + categoryId);
		return new ResponseEntity<Category>(categoryService.updateCategory(categoryId, category, principal.getName()), HttpStatus.CREATED);
	}

	@DeleteMapping("/{categoryId}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@ApiOperation(value = "Delete a category", notes = "Only user with admin access can use this endpoint")
	public ResponseEntity<String> deleteCategory(@PathVariable("categoryId") long categoryId) {
		logger.info("DELETE request to /api/v1/category/" + categoryId);
		categoryService.deleteCategroy(categoryId);
		return new ResponseEntity<String>("Deleted successfully", HttpStatus.CREATED);
	}

}
