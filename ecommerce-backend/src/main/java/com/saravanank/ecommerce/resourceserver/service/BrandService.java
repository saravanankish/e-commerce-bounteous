package com.saravanank.ecommerce.resourceserver.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.saravanank.ecommerce.resourceserver.model.Brand;
import com.saravanank.ecommerce.resourceserver.repository.BrandRepository;

@Service
public class BrandService {

	@Autowired
	private BrandRepository brandRepo;
	
	public Brand addBrand(Brand brand) {
		return brandRepo.save(brand);
	}
	
	public List<Brand> addBrands(List<Brand> brands) {
		return brandRepo.saveAll(brands);
	}
	
	public List<Brand> getAllBrands() {
		return brandRepo.findAll();
	}
	
	public Brand getBrandById(long brandId) {
		Optional<Brand> brandInDb = brandRepo.findById(brandId);
		if(brandInDb.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Brand not found");
		return brandInDb.get();
	}
	
	public Brand updateBrand(Brand brand, long brandId) {
		Optional<Brand> brandInDb = brandRepo.findById(brandId);
		if(brandInDb.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Brand not found");
		Brand brandData = brandInDb.get();
		if(brand.getName() != null) brandData.setName(brand.getName());
		brandRepo.saveAndFlush(brandData);
		return brandData;
	}
	
	public void deleteBrand(long brandId) {
		boolean brandPresent = brandRepo.existsById(brandId);
		if(!brandPresent) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Brand not found");
		brandRepo.deleteById(brandId);
	}
	
}
