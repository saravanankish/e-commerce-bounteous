package com.saravanank.ecommerce.resourceserver.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.saravanank.ecommerce.resourceserver.model.Order;
import com.saravanank.ecommerce.resourceserver.model.User;
import com.saravanank.ecommerce.resourceserver.repository.OrderRepository;
import com.saravanank.ecommerce.resourceserver.repository.UserRepository;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	public List<Order> getUserOrders(String username) {
		User userInDb = userRepo.findByUsername(username);
		if(userInDb == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
		return userInDb.getOrders();
	}
	
	public List<Order> getUserOrders(long userId) {
		Optional<User> userInDb = userRepo.findById(userId);
		if(userInDb.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
		return userInDb.get().getOrders();
	}
	
	public List<Order> getAllOrders() {
		return orderRepo.findAll();
	}
	
	public Order cancelOrder(long orderId) {
		Optional<Order> orderInDb = orderRepo.findById(orderId);
		if(orderInDb.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
		Order orderData = orderInDb.get();
		orderData.setOrderStatus("CANCELLED");
		orderRepo.saveAndFlush(orderData);
		return orderData;
	}
	
	public Order updateOrder(Order order, long orderId) {
		Optional<Order> orderInDb = orderRepo.findById(orderId);
		if(orderInDb.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
		Order orderData = orderInDb.get();
		if(order.getValue() != 0) orderData.setValue(order.getValue());
		if(order.getOrderDate() != null) orderData.setOrderDate(order.getOrderDate());
		if(order.getOrderStatus() != null) orderData.setOrderStatus(order.getOrderStatus());
		if(order.getProducts() != null) orderData.setProducts(order.getProducts());
		orderRepo.saveAndFlush(orderData);
		return orderData;
	}
	
	public Order addOrder(Order order) {
		order.setOrderDate(new java.sql.Date(new Date().getTime()));
		orderRepo.saveAndFlush(order);
		return order;
	}
	
}
