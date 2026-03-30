package com.ecommerce.backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.backend.entity.Order;
import com.ecommerce.backend.entity.User;
import com.ecommerce.backend.repository.UserRepository;
import com.ecommerce.backend.service.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

	private final OrderService orderService;
	private final UserRepository userRepository;

	public OrderController(OrderService orderService, UserRepository userRepository) {
		this.orderService = orderService;
		this.userRepository = userRepository;
	}

	@PostMapping("/checkout")
	public ResponseEntity<Order> checkout(@RequestParam String address) throws Exception {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

		Order order = orderService.placeOrder(user, address);
		return ResponseEntity.ok(order);
	}

	@GetMapping("/my-orders")
	public ResponseEntity<List<Order>> getMyOrders() {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

		return ResponseEntity.ok(orderService.getUserOrders(user));
	}
}