package com.greenmart.app.services;

import java.util.UUID;

import com.greenmart.app.domain.dtos.CartDto;
import com.greenmart.app.domain.dtos.UpdateCartItemRequestDto;

public interface CartService {
    CartDto getCart(UUID userId);
    CartDto addItemToCart(UUID userId, UUID productId, int quantity);
    CartDto updateCartItem(UUID userId, UUID cartItemId, UpdateCartItemRequestDto updateRequest);
    void removeItemFromCart(UUID userId, UUID cartItemId);
    void clearCart(UUID userId);
}
