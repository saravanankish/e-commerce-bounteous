package com.saravanank.ecommerce.resourceserver.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saravanank.ecommerce.resourceserver.model.Category;
import com.saravanank.ecommerce.resourceserver.repository.CategoryRepository;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository subCategoryRepo;
	
	
	public Category addSubCategory(Category subCat) {
		return subCategoryRepo.save(subCat);
	}
	
	public List<Category> addSubCategories(List<Category> subCategories) {
		return subCategoryRepo.saveAll(subCategories);
	}
	
}
