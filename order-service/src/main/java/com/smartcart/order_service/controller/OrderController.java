package com.smartcart.order_service.controller;

import com.smartcart.order_service.entity.Order;
import com.smartcart.order_service.service.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // Place Order
    @PostMapping
    public Order placeOrder(@RequestBody Order order) {

        return orderService.placeOrder(order);
    }

    // Get All Orders
    @GetMapping
    public List<Order> getAllOrders() {

        return orderService.getAllOrders();
    }

    // Get Order By ID
    @GetMapping("/{id}")
    public Order getOrderById(@PathVariable Long id) {

        return orderService.getOrderById(id);
    }

    // Get Orders By User ID
    @GetMapping("/user/{userId}")
    public List<Order> getOrdersByUserId(
            @PathVariable Long userId
    ) {

        return orderService.getOrdersByUserId(userId);
    }

    // Delete Order
    @DeleteMapping("/{id}")
    public String deleteOrder(@PathVariable Long id) {

        orderService.deleteOrder(id);

        return "Order deleted successfully";
    }
}