package com.scaler.cartservice.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CartItemResponse {

    private Long productId;
    private String productName;
    private double price;
    private int quantity;
    private double itemTotal;
}
