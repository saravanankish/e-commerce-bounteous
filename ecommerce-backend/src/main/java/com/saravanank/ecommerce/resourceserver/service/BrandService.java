package com.saravanank.ecommerce.resourceserver.service;

import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.saravanank.ecommerce.resourceserver.model.Brand;
import com.saravanank.ecommerce.resourceserver.repository.BrandRepository;

@Service
public class BrandService {
	
	private static final Logger logger = Logger.getLogger(BrandService.class);

	@Autowired
	private BrandRepository brandRepo;
	
	public Brand addBrand(Brand brand) {
		brandRepo.saveAndFlush(brand);
		logger.info("Added brand with id=" + brand.getId());
		return brandRepo.save(brand);
	}
	
	public List<Brand> addBrands(List<Brand> brands) {
		logger.info("Added " + brands.size() + " brand(s)");
		return brandRepo.saveAll(brands);
	}
	
	public List<Brand> getAllBrands() {
		logger.info("Returned all brands");
		return brandRepo.findAll();
	}
	
	public Brand getBrandById(long brandId) {
		Optional<Brand> brandInDb = brandRepo.findById(brandId);
		if(brandInDb.isEmpty())  {
			logger.warn("Brand with id=" + brandId + " not found");
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Brand not found");
		}
		logger.info("Returned brand with id=" + brandId);
		return brandInDb.get();
	}
	
	public Brand updateBrand(Brand brand, long brandId) {
		Optional<Brand> brandInDb = brandRepo.findById(brandId);
		if(brandInDb.isEmpty()) {
			logger.warn("Brand with id=" + brandId + " not found");
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Brand not found");
		}
		Brand brandData = brandInDb.get();
		if(brand.getName() != null) brandData.setName(brand.getName());
		logger.info("Updated brand with id=" + brandId);
		brandRepo.saveAndFlush(brandData);
		return brandData;
	}
	
	public void deleteBrand(long brandId) {
		boolean brandPresent = brandRepo.existsById(brandId);
		if(!brandPresent) {
			logger.warn("Brand with id=" + brandId + " not found");
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Brand not found");
		}
		logger.info("Deleted brand with id=" + brandId);
		brandRepo.deleteById(brandId);
	}
	
}
