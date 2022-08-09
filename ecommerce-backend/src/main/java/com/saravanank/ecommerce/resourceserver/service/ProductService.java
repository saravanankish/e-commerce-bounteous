package com.saravanank.ecommerce.resourceserver.service;

import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.saravanank.ecommerce.resourceserver.model.Product;
import com.saravanank.ecommerce.resourceserver.model.ProductResponseModel;
import com.saravanank.ecommerce.resourceserver.repository.ProductRepository;

@Service
public class ProductService {

	private static final Logger logger = Logger.getLogger(ProductService.class);
	
	@Autowired
	private ProductRepository productRepo;
	
	public Product getProductById(long productId) {
		Optional<Product> product = productRepo.findById(productId);
		if(product.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
		return product.get();
	}
	
	public Product addProduct(Product product) {
		productRepo.save(product);
		logger.info("Added product with productId=" + product.getProductId());
		return product;
	}
	
	public Product updateProduct(Product product, long productId) {
		Optional<Product> productInDb = productRepo.findById(productId);
		if(productInDb.isEmpty()) {
			logger.warn("Product with productId=" + productId + " not found");
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
		}
		Product productData = productInDb.get();
		if(product.getName() != null) productData.setName(product.getName());
		if(product.getDescription() != null) productData.setDescription(product.getDescription());
		if(product.getPrice() != 0) productData.setPrice(product.getPrice());
		if(product.getQuantity() != null) productData.setQuantity(product.getQuantity());
		if(product.getRating() != 0) productData.setRating(product.getRating());
		if(product.getThumbnail() != null) productData.setThumbnail(product.getThumbnail());
		if(product.getImages() != null) productData.setImages(product.getImages());
		if(product.getBrand() != null) productData.setBrand(product.getBrand());
		if(product.getCategory() != null) productData.setCategory(product.getCategory());
		productRepo.save(productData);
		logger.info("Updated product with productId=" + product.getProductId());
		return productData;
	}
	
	public List<Product> addProducts(List<Product> products) {
		logger.info("Added " + products.size() + " product(s)");
		return (List<Product>) this.productRepo.saveAll(products);
	}
	
	public ProductResponseModel getAllProducts(Integer page, Integer limit, String search) {
		PageRequest pageReq = PageRequest.of(page, limit);
		ProductResponseModel productResponse = new ProductResponseModel();
		Page<Product> products;
		if(search == null) {			
			products = productRepo.findAll(pageReq);
		} else {
			 products = productRepo.findByNameContainingOrDescriptionContaining(search, search, pageReq);
		}
		productResponse.setProducts(products.toList());
		productResponse.setTotal(products.getTotalElements());
		productResponse.setTotalPages(products.getTotalPages());
		productResponse.setCurrentPage(products.getNumber());
		productResponse.setLimit(limit);
		logger.info("Returned products");
		return productResponse;
	}
	
	public void deleteProduct(long productId) {
		boolean productPresent = productRepo.existsById(productId);
		if(!productPresent) {
			logger.warn("Product with productId=" + productId + " not found");
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
		}
		logger.info("Deleted product with productId=" + productId);
		productRepo.deleteById(productId);
	}
	
}
