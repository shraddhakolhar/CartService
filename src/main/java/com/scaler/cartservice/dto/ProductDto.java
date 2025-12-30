package com.scaler.cartservice.dto;

public record ProductDto(
        Long id,
        String title,
        double price
) {}
