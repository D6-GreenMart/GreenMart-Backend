package com.greenmart.app.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.greenmart.app.domain.entities.CartItem;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, UUID> {

}
