package com.greenmart.app.domain.dtos;

import java.util.List;
import java.util.UUID;

import lombok.Data;

@Data
public class CartDto {
    private UUID id;
    private UUID userId;
    private List<CartItemDto> cartItems;
}
