package com.saravanank.ecommerce.resourceserver.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.saravanank.ecommerce.resourceserver.model.Product;

public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {

}
