package com.saravanank.ecommerce.resourceserver.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.saravanank.ecommerce.resourceserver.model.Cart;
import com.saravanank.ecommerce.resourceserver.model.Product;
import com.saravanank.ecommerce.resourceserver.model.ProductQuantityMapper;
import com.saravanank.ecommerce.resourceserver.model.User;
import com.saravanank.ecommerce.resourceserver.repository.ProductRepository;
import com.saravanank.ecommerce.resourceserver.repository.UserRepository;

@Service
public class CartService {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private ProductRepository productRepo;

	public Cart getUserCart(String username) {
		User user = userRepo.findByUsername(username);
		if (user == null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
		return user.getCart();
	}

	public Cart addProductsToCart(String username, ProductQuantityMapper product) {
		User user = userRepo.findByUsername(username);
		if (user == null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
		Optional<Product> productFromDatabase = productRepo.findById(product.getProductId());
		if (productFromDatabase.isEmpty())
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product not found");
		Cart userCart = user.getCart();
		if(userCart.getProducts() == null) {
			userCart.setProducts(new ArrayList<ProductQuantityMapper>());
		}
		List<ProductQuantityMapper> qMapper = userCart.getProducts().stream()
				.filter(p -> p.getProductId() == product.getProductId()).collect(Collectors.toList());
		if (qMapper.size() != 0) {
			if (product.getQuantity() == 0) {
				userCart.setProducts(userCart.getProducts().stream()
						.filter(prod -> prod.getProductId() != product.getProductId()).toList());
			} else {
				userCart.getProducts().stream().forEach(prod -> {
					if (prod.getProductId() == product.getProductId()) {
						prod.setQuantity(product.getQuantity());
						return;
					}
				});
			}
		} else {
			userCart.getProducts().add(product);
		}
		user.setCart(userCart);
		userRepo.saveAndFlush(user);
		return user.getCart();
	}
	
	public Cart saveCart(String username, Cart cart ) {
		User user = userRepo.findByUsername(username);
		user.setCart(cart);
		userRepo.saveAndFlush(user);
		return user.getCart();
	}
}
