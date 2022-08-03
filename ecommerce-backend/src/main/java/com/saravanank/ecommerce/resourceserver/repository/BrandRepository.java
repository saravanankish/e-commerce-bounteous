package com.saravanank.ecommerce.resourceserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.saravanank.ecommerce.resourceserver.model.Brand;

public interface BrandRepository extends JpaRepository<Brand, Long> {

}
