package com.ecommerce.backend.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ecommerce.backend.entity.CartItem;
import com.ecommerce.backend.entity.User;

public interface CartRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByUser(User user);
    CartItem findByUserAndProductId(User user, Long productId);
}