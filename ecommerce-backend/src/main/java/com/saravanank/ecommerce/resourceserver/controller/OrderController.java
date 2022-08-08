package com.saravanank.ecommerce.resourceserver.controller;

import java.security.Principal;
import java.util.List;

import org.apache.log4j.Logger;
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

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/v1/order")
public class OrderController {
	
	private static final Logger logger = Logger.getLogger(OrderController.class);

	@Autowired
	private OrderService orderService;

	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
	@ApiOperation(value = "Get order of current user", notes = "Only user with customer access can use this endpoint, returns orders of current authenticated user")
	public ResponseEntity<List<Order>> getOrdersOfUser(Principal principal) {
		logger.info("GET request to /api/v1/order");
		return new ResponseEntity<List<Order>>(orderService.getUserOrders(principal.getName()), HttpStatus.OK);
	}

	@GetMapping("/{customerId}")
	@ApiOperation(value = "Get orders of customer", notes = "All users can use this endpoint")
	public ResponseEntity<List<Order>> getOrderOfCustomer(@PathVariable("customerId") long customerId) {
		logger.info("GET request to /api/v1/order/" + customerId);
		return new ResponseEntity<List<Order>>(orderService.getUserOrders(customerId), HttpStatus.OK);
	}

	@PostMapping("/cancel/{orderId}") 
	@ApiOperation(value = "Cancel an order", notes = "All users can use this endpoint")
	public ResponseEntity<Order> cancelOrder(@PathVariable("orderId") long orderId) {
		logger.info("POST request to /api/v1/order/cancel" + orderId);
		return new ResponseEntity<Order>(HttpStatus.CREATED);
	}
	
	@GetMapping("/all")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_SUPPORT')")
	@ApiOperation(value = "Get all orders", notes = "Only user with admin or support access can use this endpoint")
	public ResponseEntity<List<Order>> getAllOrders() {
		logger.info("GET request to /api/v1/order/all");
		return new ResponseEntity<List<Order>>(orderService.getAllOrders(), HttpStatus.OK);
	}
	
	@PutMapping("/{orderId}")
	@ApiOperation(value = "Update an order", notes = "All users can use this endpoint")
	public ResponseEntity<Order> updateOrder(@PathVariable("orderId") long orderId, @RequestBody Order order) {
		logger.info("PUT request to /api/v1/order/" + orderId);
		return new ResponseEntity<Order>(HttpStatus.CREATED);
	}
	
	@PostMapping("/add") 
	@ApiOperation(value = "Add an order", notes = "All users can use this endpoint")
	public ResponseEntity<Order> addOrder(@RequestBody Order order) {
		logger.info("POST request to /api/v1/order/add");
		return new ResponseEntity<Order>(HttpStatus.CREATED);				
	}
	
}
