package com.saravanank.ecommerce.resourceserver.controller;

import java.security.Principal;

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

@RestController
@RequestMapping("/v1/cart")
public class CartController {
	
	@Autowired
	private CartService cartService;

	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
	public ResponseEntity<Cart> getUserCart(Principal principal) {
		return new ResponseEntity<Cart>(cartService.getUserCart(principal.getName()), HttpStatus.OK);
	}
	
	@PostMapping("/add")
	@PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
	public ResponseEntity<Cart> addToCart(Principal principal, @RequestBody ProductQuantityMapper productQty) {
		return new ResponseEntity<Cart>(cartService.addProductsToCart(principal.getName(), productQty), HttpStatus.CREATED);
	}
}
