package com.greenmart.app.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.greenmart.app.domain.entities.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {

}
