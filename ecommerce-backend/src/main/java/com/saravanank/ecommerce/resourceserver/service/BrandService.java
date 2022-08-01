package com.saravanank.ecommerce.resourceserver.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	
}
