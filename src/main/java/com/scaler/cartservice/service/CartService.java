package com.scaler.cartservice.service;

import com.scaler.cartservice.dto.*;
import com.scaler.cartservice.entity.CartItemEntity;
import com.scaler.cartservice.repository.CartItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class CartService {

    private final CartItemRepository cartItemRepository;
    private final RestTemplate restTemplate;

    public CartService(
            CartItemRepository cartItemRepository,
            RestTemplate restTemplate
    ) {
        this.cartItemRepository = cartItemRepository;
        this.restTemplate = restTemplate;
    }

    // =========================
    // ADD TO CART
    // =========================
    @Transactional
    public void addToCart(String userEmail, AddToCartRequest request) {

        CartItemEntity item = cartItemRepository
                .findByUserEmailAndProductId(userEmail, request.getProductId())
                .orElseGet(() -> {

                    ProductDto product = restTemplate.getForObject(
                            "http://PRODUCT-SERVICE/products/" + request.getProductId(),
                            ProductDto.class
                    );

                    if (product == null) {
                        throw new RuntimeException("Product not found");
                    }

                    return CartItemEntity.builder()
                            .userEmail(userEmail)
                            .productId(request.getProductId())
                            .quantity(0)
                            .unitPrice(product.price())
                            .totalPrice(0.0)
                            .build();
                });

        item.setQuantity(item.getQuantity() + request.getQuantity());
        cartItemRepository.save(item);
    }

    // =========================
    // UPDATE CART
    // =========================
    @Transactional
    public void updateCart(String userEmail, UpdateCartRequest request) {

        CartItemEntity item = cartItemRepository
                .findByUserEmailAndProductId(userEmail, request.getProductId())
                .orElseThrow(() -> new RuntimeException("Item not found in cart"));

        if (request.getQuantity() == 0) {
            cartItemRepository.delete(item);
        } else {
            item.setQuantity(request.getQuantity());
            cartItemRepository.save(item);
        }
    }

    // =========================
    // VIEW CART
    // =========================
    @Transactional(readOnly = true)
    public CartResponse viewCart(String userEmail) {

        List<CartItemEntity> items =
                cartItemRepository.findByUserEmail(userEmail);

        List<CartItemResponse> responses =
                items.stream().map(item -> {

                    String productName = "UNKNOWN";

                    try {
                        ProductDto product = restTemplate.getForObject(
                                "http://PRODUCT-SERVICE/products/" + item.getProductId(),
                                ProductDto.class
                        );
                        if (product != null && product.title() != null) {
                            productName = product.title();
                        }
                    } catch (Exception ignored) {}

                    return CartItemResponse.builder()
                            .productId(item.getProductId())
                            .productName(productName)
                            .price(item.getUnitPrice())
                            .quantity(item.getQuantity())
                            .itemTotal(item.getTotalPrice())
                            .build();

                }).toList();

        double cartTotal = responses.stream()
                .mapToDouble(CartItemResponse::getItemTotal)
                .sum();

        return CartResponse.builder()
                .items(responses)
                .cartTotal(cartTotal)
                .build();
    }

    // =========================
    // CLEAR CART
    // =========================
    @Transactional
    public void clearCart(String userEmail) {
        cartItemRepository.deleteByUserEmail(userEmail);
    }
}
