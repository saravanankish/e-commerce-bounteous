package com.saravanank.ecommerce.resourceserver.controller;

import java.security.Principal;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saravanank.ecommerce.resourceserver.model.Cart;
import com.saravanank.ecommerce.resourceserver.model.ProductQuantityMapper;
import com.saravanank.ecommerce.resourceserver.service.CartService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/v1/cart")
public class CartController {
	
	private static final Logger logger = Logger.getLogger(CartController.class);

	@Autowired
	private CartService cartService;

	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
	@ApiOperation(value = "Get cart of user", notes = "All users can use this endpoint, returns cart of current authenticated user")
	public ResponseEntity<Cart> getUserCart(Principal principal) {
		logger.info("GET request to /api/v1/cart");
		return new ResponseEntity<Cart>(cartService.getUserCart(principal.getName()), HttpStatus.OK);
	}

	@PostMapping("/add")
	@PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
	@ApiOperation(value = "Add items to cart", notes = "Only user with customer access can use this endpoint, user can add items to cart using this endpoint")
	public ResponseEntity<Cart> addToCart(Principal principal, @RequestBody ProductQuantityMapper productQty) {
		logger.info("POST request to /api/v1/cart/add");
		return new ResponseEntity<Cart>(cartService.addProductsToCart(principal.getName(), productQty),
				HttpStatus.CREATED);
	}

}
