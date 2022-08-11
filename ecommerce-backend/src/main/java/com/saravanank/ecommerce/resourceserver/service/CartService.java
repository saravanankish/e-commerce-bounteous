package com.saravanank.ecommerce.resourceserver.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.saravanank.ecommerce.resourceserver.model.Cart;
import com.saravanank.ecommerce.resourceserver.model.Product;
import com.saravanank.ecommerce.resourceserver.model.ProductQuantityMapper;
import com.saravanank.ecommerce.resourceserver.model.User;
import com.saravanank.ecommerce.resourceserver.repository.CartRepository;
import com.saravanank.ecommerce.resourceserver.repository.ProductRepository;
import com.saravanank.ecommerce.resourceserver.repository.UserRepository;

@Service
public class CartService {
	
	private static final Logger logger = Logger.getLogger(CartService.class);

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private CartRepository cartRepo;

	@Autowired
	private ProductRepository productRepo;

	public Cart getUserCart(String username) {
		User user = userRepo.findByUsername(username);
		if (user == null) {
			logger.warn("Cart of user with username=" + username + " not found");
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
		}
		logger.warn("Returned cart of user with username=" + username);
//		return user.getCart();
		return null;
	}

	public Cart addProductsToCart(String username, ProductQuantityMapper product) {
		User user = userRepo.findByUsername(username);
		if (user == null) {
			logger.warn("Cart of user with username=" + username + " not found");
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");			
		}
		Optional<Product> productFromDatabase = productRepo.findById(product.getProductId());
		if (productFromDatabase.isEmpty()) {
			logger.warn("Product with product_id=" + product.getProductId() + " not found");			
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product not found");
		}
		Cart userCart =  null; //user.getCart();
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
		logger.warn("Product added to user cart of user with username=" + username);
//		user.setCart(userCart);
		userRepo.save(user);
//		return user.getCart();
		return null;
	}
	
	public Cart addCartToUser(User user) {
		if(user.getUserId() == 0) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User id not present");
		}
		logger.info("Added cart to user with id=" + user.getUserId());
		Cart userCart = new Cart();
		userCart.setUser(user);
		cartRepo.save(userCart);
		return userCart;
	}
	
}
