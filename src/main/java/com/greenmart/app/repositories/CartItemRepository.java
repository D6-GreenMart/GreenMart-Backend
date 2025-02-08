package com.greenmart.app.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.greenmart.app.domain.entities.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, UUID> {
}
