package com.scaler.cartservice.controller;

import com.scaler.cartservice.dto.AddToCartRequest;
import com.scaler.cartservice.dto.CartResponse;
import com.scaler.cartservice.dto.UpdateCartRequest;
import com.scaler.cartservice.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addToCart(
            @RequestBody AddToCartRequest request,
            Authentication authentication
    ) {
        cartService.addToCart(authentication.getName(), request);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update")
    public ResponseEntity<Void> updateCart(
            @RequestBody UpdateCartRequest request,
            Authentication authentication
    ) {
        cartService.updateCart(authentication.getName(), request);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<CartResponse> viewCart(Authentication authentication) {
        return ResponseEntity.ok(
                cartService.viewCart(authentication.getName())
        );
    }

    // 🔥 REQUIRED BY ORDER SERVICE
    @DeleteMapping("/delete")
    public ResponseEntity<Void> clearCart(Authentication authentication) {
        cartService.clearCart(authentication.getName());
        return ResponseEntity.noContent().build();
    }
}
