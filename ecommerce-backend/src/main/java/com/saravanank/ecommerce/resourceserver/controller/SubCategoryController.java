package com.saravanank.ecommerce.resourceserver.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saravanank.ecommerce.resourceserver.model.Category;
import com.saravanank.ecommerce.resourceserver.service.CategoryService;

@RestController
@RequestMapping("/v1/category")
public class SubCategoryController {

	@Autowired
	private CategoryService subCatService;
	
	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<Category> addSubCategory(@RequestBody Category subCategory) {
		return new ResponseEntity<Category>(subCatService.addSubCategory(subCategory), HttpStatus.CREATED);
	}
	
	@PostMapping("/all")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<List<Category>> addSubCategories(@RequestBody List<Category> subCategories) {
		return new ResponseEntity<List<Category>>(subCatService.addSubCategories(subCategories), HttpStatus.CREATED);
	}
}
