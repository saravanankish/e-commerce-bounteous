package com.saravanank.ecommerce.resourceserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saravanank.ecommerce.resourceserver.model.SubCategory;
import com.saravanank.ecommerce.resourceserver.repository.SubCategoryRepository;

@Service
public class SubCategoryService {

	@Autowired
	private SubCategoryRepository subCategoryRepo;
	
	public SubCategory addSubCategory(SubCategory subCat) {
		return subCategoryRepo.save(subCat);
	}
	
}
