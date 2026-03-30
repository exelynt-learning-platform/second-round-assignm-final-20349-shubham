package com.ecommerce.backend.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ecommerce.backend.entity.CartItem;
import com.ecommerce.backend.entity.Order;
import com.ecommerce.backend.entity.User;
import com.ecommerce.backend.repository.CartRepository;
import com.ecommerce.backend.repository.OrderRepository;

@Service
public class OrderService {

    @Value("${stripe.api.key}")
    private String stripeSecretKey;

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;

    public OrderService(OrderRepository orderRepository, CartRepository cartRepository) {
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
    }

    public Order placeOrder(User user, String address) throws Exception {
        List<CartItem> cartItems = cartRepository.findByUser(user);
        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        BigDecimal total = cartItems.stream()
                .map(item -> item.getProduct().getPrice().multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        /* // FUTURE STRIPE INTEGRATION LOGIC
        // Stripe.apiKey = stripeSecretKey;
        // PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
        //      .setAmount(total.multiply(new BigDecimal(100)).longValue()) 
        //      .setCurrency("usd").build();
        // PaymentIntent intent = PaymentIntent.create(params);
        */

        Order order = new Order();
        order.setUser(user);
        order.setProducts(cartItems.stream().map(CartItem::getProduct).collect(Collectors.toList()));
        order.setTotalPrice(total);
        order.setShippingAddress(address);
        order.setPaymentStatus("SUCCESS (MOCK)"); 

        orderRepository.save(order);
        cartRepository.deleteAll(cartItems);

        return order;
    }

    public List<Order> getUserOrders(User user) {
        return orderRepository.findByUser(user);
    }
}