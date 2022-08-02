package com.saravanank.ecommerce.resourceserver.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saravanank.ecommerce.resourceserver.model.Brand;
import com.saravanank.ecommerce.resourceserver.service.BrandService;

@RestController
@RequestMapping("/v1/brand")
public class BrandController {

	@Autowired
	private BrandService brandService;
	
	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<Brand> addBrand(@RequestBody Brand brand ){
		return new ResponseEntity<Brand>(brandService.addBrand(brand), HttpStatus.CREATED);
	}
	
	@PostMapping("/all")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<List<Brand>> addBrands(@RequestBody List<Brand> brands) {
		return new ResponseEntity<List<Brand>>(brandService.addBrands(brands), HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<List<Brand>> getAllBrand() {
		return new ResponseEntity<List<Brand>>(brandService.getAllBrands(), HttpStatus.OK);
	}
	
}
