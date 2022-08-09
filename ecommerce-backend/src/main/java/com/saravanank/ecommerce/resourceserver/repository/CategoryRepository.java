package com.saravanank.ecommerce.resourceserver.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.saravanank.ecommerce.resourceserver.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

	@Query(nativeQuery = true, value = "SELECT sub_category FROM category_sub_category")
	public List<String> findAllSubcategories();
	
}
