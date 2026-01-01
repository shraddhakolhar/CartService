package com.scaler.cartservice.repository;

import com.scaler.cartservice.entity.CartItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItemEntity, Long> {

    // Fetch all cart items for a user
    List<CartItemEntity> findByUserEmail(String userEmail);

    // Fetch a specific product in user's cart
    Optional<CartItemEntity> findByUserEmailAndProductId(
            String userEmail,
            Long productId
    );

    // Delete all cart items for a user (used after checkout)
    @Modifying
    @Transactional
    void deleteByUserEmail(String userEmail);
}
