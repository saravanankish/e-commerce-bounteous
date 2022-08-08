package com.saravanank.ecommerce.resourceserver.service;

import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.saravanank.ecommerce.resourceserver.model.Category;
import com.saravanank.ecommerce.resourceserver.repository.CategoryRepository;

@Service
public class CategoryService {
	
	private static final Logger logger = Logger.getLogger(CategoryService.class);

	@Autowired
	private CategoryRepository categoryRepo;
	
	
	public Category addSubCategory(Category subCat) {
		categoryRepo.saveAndFlush(subCat);
		logger.info("Added category with category_id=" + subCat.getId());
		return subCat;
	}
	
	public List<Category> addSubCategories(List<Category> subCategories) {
		logger.info("Added " + subCategories.size() + " category(ies)");
		return categoryRepo.saveAll(subCategories);
	}
	
	public List<Category> getAllCategories() {
		logger.info("Returned all categories");
		return categoryRepo.findAll();
	}
	
	public Category updateCategory(long categoryId, Category category) {
		Optional<Category> categoryInDb = categoryRepo.findById(categoryId);
		if(categoryInDb.isEmpty()) {
			logger.warn("Category with id=" + categoryId + " not found");
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found");
		}
		Category categoryData = categoryInDb.get();
		if(category.getName() != null) categoryData.setName(category.getName());
		if(category.getSubCategory() != null) categoryData.setSubCategory(category.getSubCategory());
		categoryRepo.saveAndFlush(categoryData);
		logger.info("Updated category with id=" + categoryId);
		return categoryData;
	}
	
	public void deleteCategroy(long categoryId) {
		boolean categoryPresent = categoryRepo.existsById(categoryId);
		if(!categoryPresent) {
			logger.warn("Category with id=" + categoryId + " not found");
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found");
		}
		logger.info("Deleted category with id=" + categoryId);
		categoryRepo.deleteById(categoryId);
	}
	
	public Category getCategoryById(long categoryId) {
		Optional<Category> categoryInDb = categoryRepo.findById(categoryId);
		if(categoryInDb.isEmpty()) {
			logger.warn("Category with id=" + categoryId + " not found");
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found");
		}
		logger.info("Returned category with id=" + categoryId);
		return categoryInDb.get();
	}
	
}
