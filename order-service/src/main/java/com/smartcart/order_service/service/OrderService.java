package com.smartcart.order_service.service;

import com.smartcart.order_service.client.ProductClient;
import com.smartcart.order_service.dto.ProductDto;
import com.smartcart.order_service.entity.Order;
import com.smartcart.order_service.exception.ResourceNotFoundException;
import com.smartcart.order_service.repository.OrderRepository;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    private final ProductClient productClient;

    // Constructor Injection
    public OrderService(OrderRepository orderRepository,
                        ProductClient productClient) {

        this.orderRepository = orderRepository;
        this.productClient = productClient;
    }

    // PLACE ORDER

    @CircuitBreaker(
            name = "productService",
            fallbackMethod = "fallbackOrder"
    )

    public Order placeOrder(Order order) {

        // FETCH PRODUCT FROM PRODUCT SERVICE
        ProductDto product =
                productClient.getProductById(
                        order.getProductId());

        // IF PRODUCT NOT FOUND
        if (product == null) {

            throw new ResourceNotFoundException(
                    "Product not found with ID : "
                            + order.getProductId()
            );
        }

        // CALCULATE TOTAL AMOUNT
        double totalAmount =
                product.getPrice()
                        * order.getQuantity();

        // SET TOTAL AMOUNT
        order.setTotalAmount(totalAmount);

        // SET ORDER DATE & TIME
        order.setOrderDate(LocalDateTime.now());

        // SAVE ORDER
        return orderRepository.save(order);
    }

    // FALLBACK METHOD

    public Order fallbackOrder(
            Order order,
            Exception ex
    ) {

        Order fallbackOrder = new Order();

        fallbackOrder.setUserId(order.getUserId());

        fallbackOrder.setProductId(order.getProductId());

        fallbackOrder.setQuantity(order.getQuantity());

        fallbackOrder.setTotalAmount(0);

        fallbackOrder.setOrderDate(LocalDateTime.now());

        return fallbackOrder;
    }

    // GET ALL ORDERS
    public List<Order> getAllOrders() {

        return orderRepository.findAll();
    }

    // GET ORDER BY ID
    public Order getOrderById(Long id) {

        return orderRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Order not found with ID : " + id
                        ));
    }

    // GET ORDERS BY USER ID
    public List<Order> getOrdersByUserId(Long userId) {

        return orderRepository.findByUserId(userId);
    }

    // DELETE ORDER
    public void deleteOrder(Long id) {

        // FIND ORDER FIRST
        Order order = orderRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Order not found with ID : " + id
                        ));

        // DELETE ORDER
        orderRepository.delete(order);
    }
}