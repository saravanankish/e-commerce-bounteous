package com.saravanank.ecommerce.resourceserver.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saravanank.ecommerce.resourceserver.model.Order;
import com.saravanank.ecommerce.resourceserver.service.OrderService;

@RestController
@RequestMapping("/v1/order")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
	public ResponseEntity<List<Order>> getOrdersOfUser(Principal principal) {
		return new ResponseEntity<List<Order>>(orderService.getUserOrders(principal.getName()), HttpStatus.OK);
	}

	@GetMapping("/{customerId}")
	public ResponseEntity<List<Order>> getOrderOfCustomer(@PathVariable("customerId") long customerId) {
		return new ResponseEntity<List<Order>>(orderService.getUserOrders(customerId), HttpStatus.OK);
	}

	@PostMapping("/cancel/{orderId}") 
	public ResponseEntity<Order> cancelOrder(@PathVariable("orderId") long orderId) {
		return new ResponseEntity<Order>(HttpStatus.CREATED);
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<Order>> getAllOrders() {
		return new ResponseEntity<List<Order>>(orderService.getAllOrders(), HttpStatus.OK);
	}
	
	@PutMapping("/{orderId}")
	public ResponseEntity<Order> updateOrder(@PathVariable("orderId") long orderId, @RequestBody Order order) {
		return new ResponseEntity<Order>(HttpStatus.CREATED);
	}
	
	@PostMapping("/add") 
	public ResponseEntity<Order> addOrder(@RequestBody Order order) {
		return new ResponseEntity<Order>(HttpStatus.CREATED);				
	}
	
}
