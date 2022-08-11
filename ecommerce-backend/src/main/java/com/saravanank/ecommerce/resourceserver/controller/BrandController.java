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

import com.saravanank.ecommerce.resourceserver.model.Brand;
import com.saravanank.ecommerce.resourceserver.service.BrandService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/v1/brand")
public class BrandController {

	private static final Logger logger = Logger.getLogger(BrandController.class);

	@Autowired
	private BrandService brandService;

	@PostMapping
	@ApiOperation(value = "Add a brand", notes = "Only user with admin access can use this endpoint")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<Brand> addBrand(@RequestBody Brand brand, Principal principal) {
		logger.info("POST request to /api/v1/brand");
		return new ResponseEntity<Brand>(brandService.addBrand(brand, principal.getName()), HttpStatus.CREATED);
	}

	@PostMapping("/all")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@ApiOperation(value = "Add many brand", notes = "Only user with admin access can use this endpoint")
	public ResponseEntity<List<Brand>> addBrands(@RequestBody List<Brand> brands, Principal principal) {
		logger.info("POST request to /api/v1/brand/all");
		return new ResponseEntity<List<Brand>>(brandService.addBrands(brands, principal.getName()), HttpStatus.CREATED);
	}

	@GetMapping("/{brandId}")
	@ApiOperation(value = "Get brand by id", notes = "All users can use this endpoint")
	public ResponseEntity<Brand> getBrandById(@PathVariable("brandId") long brandId) {
		logger.info("GET request to /api/v1/brand/" + brandId);
		return new ResponseEntity<Brand>(brandService.getBrandById(brandId), HttpStatus.OK);
	}

	@GetMapping
	@ApiOperation(value = "Get all brands", notes = "All users can use this endpoint", produces = "application/json")
	public ResponseEntity<List<Brand>> getAllBrand() {
		logger.info("GET request to /api/v1/brand");
		return new ResponseEntity<List<Brand>>(brandService.getAllBrands(), HttpStatus.OK);
	}

	@PutMapping("/{brandId}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@ApiOperation(value = "Update a brand", notes = "Only user with admin access can use this endpoint")
	public ResponseEntity<Brand> updateBrand(@PathVariable("brandId") long brandId, @RequestBody Brand brand,
			Principal principal) {
		logger.info("PUT request to /api/v1/brand/" + brandId);
		return new ResponseEntity<Brand>(brandService.updateBrand(brand, brandId, principal.getName()),
				HttpStatus.CREATED);
	}

	@DeleteMapping("/{brandId}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@ApiOperation(value = "Delete a brand", notes = "Only user with admin access can use this endpoint")
	public ResponseEntity<String> deleteBrand(@PathVariable("brandId") long brandId) {
		logger.info("DELETE request to /api/v1/brand/" + brandId);
		brandService.deleteBrand(brandId);
		return new ResponseEntity<String>("Deleted successfully", HttpStatus.NO_CONTENT);
	}

}
