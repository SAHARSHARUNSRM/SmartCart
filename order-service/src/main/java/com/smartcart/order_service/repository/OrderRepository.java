package com.smartcart.order_service.repository;

import com.smartcart.order_service.entity.Order;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository
        extends JpaRepository<Order, Long> {

    // Find all orders by user
    List<Order> findByUserId(Long userId);
}