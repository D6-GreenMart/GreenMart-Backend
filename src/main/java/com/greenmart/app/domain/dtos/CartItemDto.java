package com.greenmart.app.domain.dtos;

import lombok.Data;
import java.util.UUID;

@Data
public class CartItemDto {
    private UUID id;
    private UUID productId;
    private String productName;
    private int quantity;
}
