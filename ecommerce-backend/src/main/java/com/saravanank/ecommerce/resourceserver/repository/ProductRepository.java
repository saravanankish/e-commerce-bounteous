package com.saravanank.ecommerce.resourceserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.saravanank.ecommerce.resourceserver.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

}
