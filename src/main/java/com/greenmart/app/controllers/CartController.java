package com.greenmart.app.controllers;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.greenmart.app.domain.dtos.CartDto;
import com.greenmart.app.domain.dtos.UpdateCartItemRequestDto;
import com.greenmart.app.domain.entities.User;
import com.greenmart.app.services.CartService;
import com.greenmart.app.services.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<CartDto> getCart(@AuthenticationPrincipal UserDetails userDetails) {
    	User user = userService.getUserByEmail(userDetails.getUsername());
        return ResponseEntity.ok(cartService.getCart(user.getId()));
    }

    @PostMapping("/add/{productId}")
    public ResponseEntity<CartDto> addItemToCart(@AuthenticationPrincipal UserDetails userDetails, @PathVariable UUID productId, @RequestParam int quantity) {
    	User user = userService.getUserByEmail(userDetails.getUsername());
    	return ResponseEntity.ok(cartService.addItemToCart(user.getId(), productId, quantity));
    }

    @PutMapping("/update/{cartItemId}")
    public ResponseEntity<CartDto> updateCartItem(@AuthenticationPrincipal UserDetails userDetails, @PathVariable UUID cartItemId, @Valid @RequestBody UpdateCartItemRequestDto updateRequest) {
    	User user = userService.getUserByEmail(userDetails.getUsername());
    	return ResponseEntity.ok(cartService.updateCartItem(user.getId(), cartItemId, updateRequest));
    }

    @DeleteMapping("/remove/{cartItemId}")
    public ResponseEntity<Void> removeItem(@AuthenticationPrincipal UserDetails userDetails, @PathVariable UUID cartItemId) {
    	User user = userService.getUserByEmail(userDetails.getUsername());
    	cartService.removeItemFromCart(user.getId(), cartItemId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/clear")
    public ResponseEntity<Void> clearCart(@AuthenticationPrincipal UserDetails userDetails) {
    	User user = userService.getUserByEmail(userDetails.getUsername());
    	cartService.clearCart(user.getId());
        return ResponseEntity.noContent().build();
    }
}
