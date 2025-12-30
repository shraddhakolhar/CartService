package com.scaler.cartservice.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CartResponse {

    private List<CartItemResponse> items;
    private double cartTotal;
}
