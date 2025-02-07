package com.greenmart.app.controllers;

import com.greenmart.app.domain.dtos.CartDto;
import com.greenmart.app.domain.dtos.UpdateCartItemRequestDto;
import com.greenmart.app.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping("/{userId}")
    public ResponseEntity<CartDto> getCart(@PathVariable UUID userId) {
        return ResponseEntity.ok(cartService.getCart(userId));
    }

    @PostMapping("/{userId}/add/{productId}")
    public ResponseEntity<CartDto> addItemToCart(@PathVariable UUID userId, @PathVariable UUID productId, @RequestParam int quantity) {
        return ResponseEntity.ok(cartService.addItemToCart(userId, productId, quantity));
    }

    @PutMapping("/{userId}/update/{cartItemId}")
    public ResponseEntity<CartDto> updateCartItem(@PathVariable UUID userId, @PathVariable UUID cartItemId, @Valid @RequestBody UpdateCartItemRequestDto updateRequest) {
        return ResponseEntity.ok(cartService.updateCartItem(userId, cartItemId, updateRequest));
    }

    @DeleteMapping("/{userId}/remove/{cartItemId}")
    public ResponseEntity<Void> removeItem(@PathVariable UUID userId, @PathVariable UUID cartItemId) {
        cartService.removeItemFromCart(userId, cartItemId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{userId}/clear")
    public ResponseEntity<Void> clearCart(@PathVariable UUID userId) {
        cartService.clearCart(userId);
        return ResponseEntity.noContent().build();
    }
}
