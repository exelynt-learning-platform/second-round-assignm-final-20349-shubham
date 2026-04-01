package com.ecommerce.backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.backend.entity.CartItem;
import com.ecommerce.backend.entity.User;
import com.ecommerce.backend.repository.UserRepository;
import com.ecommerce.backend.service.CartService;

@RestController
@RequestMapping("/api/cart")
public class CartController {

	private final CartService cartService;
	private final UserRepository userRepository;

	public CartController(CartService cartService, UserRepository userRepository) {
		this.cartService = cartService;
		this.userRepository = userRepository;
	}

	private User getAuthenticatedUser() {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		return userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
	}

	@PostMapping("/add")
	public ResponseEntity<CartItem> addToCart(@RequestParam Long productId, @RequestParam Integer quantity) {
		User user = getAuthenticatedUser();
		CartItem cartItem = cartService.addToCart(user, productId, quantity);
		return ResponseEntity.ok(cartItem);
	}

	@GetMapping
	public ResponseEntity<List<CartItem>> getCart() {
		User user = getAuthenticatedUser();
		return ResponseEntity.ok(cartService.getCartItems(user));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> removeFromCart(@PathVariable Long id) {
		getAuthenticatedUser();
		cartService.removeCartItem(id);
		return ResponseEntity.ok("Item removed from cart successfully");
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<CartItem> updateCartItem(@PathVariable Long id, @RequestParam Integer quantity) {
		getAuthenticatedUser(); // Security Validation
		CartItem updatedItem = cartService.updateCartItemQuantity(id, quantity);
		return ResponseEntity.ok(updatedItem);
	}
}