package com.saravanank.ecommerce.resourceserver.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.saravanank.ecommerce.resourceserver.model.Category;
import com.saravanank.ecommerce.resourceserver.repository.CategoryRepository;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepo;
	
	
	public Category addSubCategory(Category subCat) {
		return categoryRepo.save(subCat);
	}
	
	public List<Category> addSubCategories(List<Category> subCategories) {
		return categoryRepo.saveAll(subCategories);
	}
	
	public List<Category> getAllCategories() {
		return categoryRepo.findAll();
	}
	
	public Category updateCategory(long categoryId, Category category) {
		Optional<Category> categoryInDb = categoryRepo.findById(categoryId);
		if(categoryInDb.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found");
		Category categoryData = categoryInDb.get();
		if(category.getName() != null) categoryData.setName(category.getName());
		if(category.getSubCategory() != null) categoryData.setSubCategory(category.getSubCategory());
		categoryRepo.saveAndFlush(categoryData);
		return categoryData;
	}
	
	public void deleteCategroy(long categoryId) {
		boolean categoryPresent = categoryRepo.existsById(categoryId);
		if(!categoryPresent) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found");
		categoryRepo.deleteById(categoryId);
	}
	
	public Category getCategoryById(long categoryId) {
		Optional<Category> categoryInDb = categoryRepo.findById(categoryId);
		if(categoryInDb.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found");
		return categoryInDb.get();
	}
}
