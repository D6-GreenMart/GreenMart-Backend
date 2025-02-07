package com.greenmart.app.services.impl;

import java.util.ArrayList;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.greenmart.app.domain.dtos.CartDto;
import com.greenmart.app.domain.dtos.UpdateCartItemRequestDto;
import com.greenmart.app.domain.entities.Cart;
import com.greenmart.app.domain.entities.CartItem;
import com.greenmart.app.domain.entities.Product;
import com.greenmart.app.domain.entities.User;
import com.greenmart.app.mappers.CartMapper;
import com.greenmart.app.repositories.CartItemRepository;
import com.greenmart.app.repositories.CartRepository;
import com.greenmart.app.repositories.ProductRepository;
import com.greenmart.app.repositories.UserRepository;
import com.greenmart.app.services.CartService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CartMapper cartMapper;

    @Override
    @Transactional
    public CartDto getCart(UUID userId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseGet(() -> createNewCart(userId));
        return cartMapper.toDto(cart);
    }

    public CartDto addItemToCart(UUID userId, UUID productId, int quantity) {
        Cart cart = cartRepository.findByUserId(userId)  // Fetch by userId instead
            .orElseGet(() -> createNewCart(userId));  // If no cart exists, create one

        if (cart.getCartItems() == null) {
            cart.setCartItems(new ArrayList<>());
        }

        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        // Check if item is already in cart
        CartItem existingItem = cart.getCartItems().stream()
            .filter(item -> item.getProduct().getId().equals(productId))
            .findFirst()
            .orElse(null);

        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
        } else {
            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setProduct(product);
            newItem.setQuantity(quantity);
            cart.getCartItems().add(newItem);
        }

        return cartMapper.toDto(cartRepository.save(cart));
    }

    // Method to create a new cart if none exists
    private Cart createNewCart(UUID userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Cart newCart = new Cart();
        newCart.setUser(user);
        newCart.setCartItems(new ArrayList<>());
        return cartRepository.save(newCart);
    }



    @Override
    @Transactional
    public CartDto updateCartItem(UUID userId, UUID cartItemId, UpdateCartItemRequestDto updateRequest) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        if (updateRequest.getQuantity() <= 0) {
            cartItemRepository.deleteById(cartItemId);  // 🔥 Ensure item is deleted
            cart.getCartItems().removeIf(item -> item.getId().equals(cartItemId));  // Remove from cart
        } else {
            cartItem.setQuantity(updateRequest.getQuantity());
        }

        return cartMapper.toDto(cartRepository.save(cart));
    }

    @Override
    @Transactional
    public void removeItemFromCart(UUID userId, UUID cartItemId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        cartItemRepository.deleteById(cartItemId);  // 🔥 Delete from database
        cart.getCartItems().removeIf(item -> item.getId().equals(cartItemId));  // Remove from list

        cartRepository.save(cart);
    }

    @Override
    @Transactional
    public void clearCart(UUID userId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        cartItemRepository.deleteAll(cart.getCartItems());  // 🔥 Delete all items from DB
        cart.getCartItems().clear();  // Clear list

        cartRepository.save(cart);
    }

}
