package com.saravanank.ecommerce.resourceserver.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.saravanank.ecommerce.resourceserver.model.Product;

public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {

	public Page<Product> findByNameContainingOrDescriptionContaining(String query, String query1, PageRequest request);
	
	@Query(value = "SELECT COUNT(*) FROM product where name LIKE '%?1%' OR description LIKE '%?1%'", nativeQuery = true)
	public int countOfQueryResult(String query);
	
}
