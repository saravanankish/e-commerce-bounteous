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
import org.springframework.web.bind.annotation.RestController;

import com.saravanank.ecommerce.resourceserver.model.Category;
import com.saravanank.ecommerce.resourceserver.service.CategoryService;

@RestController
@RequestMapping("/v1/category")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<Category> addCategory(@RequestBody Category subCategory) {
		return new ResponseEntity<Category>(categoryService.addSubCategory(subCategory), HttpStatus.CREATED);
	}

	@PostMapping("/all")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<List<Category>> addCategories(@RequestBody List<Category> subCategories) {
		return new ResponseEntity<List<Category>>(categoryService.addSubCategories(subCategories), HttpStatus.CREATED);
	}

	@GetMapping("/{categoryId}")
	public ResponseEntity<Category> getCategoryById(@PathVariable("categoryId") long categoryId) {
		return new ResponseEntity<Category>(categoryService.getCategoryById(categoryId), HttpStatus.OK);
	}

	@GetMapping("/all")
	public ResponseEntity<List<Category>> getAllCategories() {
		return new ResponseEntity<List<Category>>(categoryService.getAllCategories(), HttpStatus.OK);
	}

	@PutMapping("/{categoryId}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<Category> updateCategory(@PathVariable("categoryId") long categoryId,
			@RequestBody Category category) {
		return new ResponseEntity<Category>(categoryService.updateCategory(categoryId, category), HttpStatus.CREATED);
	}

	@DeleteMapping("/{categoryId}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<String> deleteCategory(@PathVariable("categoryId") long categoryId) {
		categoryService.deleteCategroy(categoryId);
		return new ResponseEntity<String>("Deleted successfully", HttpStatus.CREATED);
	}

}
