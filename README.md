# SmartCart – E-Commerce Microservices Backend

SmartCart is a Spring Boot based microservices e-commerce backend system developed as part of the Spring Boot Assignment.

This project was developed by:

- Saharsh Arun
- Adrika Mandal 

Group: Group 3

---

# Project Overview

SmartCart demonstrates enterprise-level backend concepts using Spring Boot Microservices Architecture.

The project implements:
- JWT Authentication
- Spring Security
- API Gateway
- Feign Client
- Circuit Breaker using Resilience4j
- REST APIs
- MySQL Integration
- Swagger & Postman Testing

---

# Microservices Used

## User Service
Handles:
- User Registration
- User Login
- JWT Authentication
- Spring Security

---

## Product Service
Handles:
- Add Product
- Update Product
- Delete Product
- View Products

---

## Order Service
Handles:
- Place Orders
- View Orders
- Feign Client Communication
- Circuit Breaker & Fallback Logic

---

## API Gateway
Acts as:
- Single Entry Point
- Request Router
- Centralized API Access

---

# Tech Stack

- Java 17
- Spring Boot
- Spring Security
- Spring Data JPA
- Hibernate
- MySQL
- Maven
- OpenFeign
- Resilience4j
- Swagger
- Postman

---

# Architecture

Client → API Gateway → Microservices → MySQL

---

# Features Implemented

- JWT Authentication
- Password Encryption
- CRUD APIs
- Feign Client Communication
- Circuit Breaker
- Global Exception Handling
- Swagger Documentation
- Postman API Testing

---

# SQL Scripts

## Create Databases

```sql
CREATE DATABASE smartcart_users;

CREATE DATABASE smartcart_products;

CREATE DATABASE smartcart_orders;
```

---

## Users Table

```sql
CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(100) UNIQUE,
    password VARCHAR(255),
    role VARCHAR(50)
);
```

---

## Products Table

```sql
CREATE TABLE products (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(200),
    description VARCHAR(500),
    price DECIMAL(10,2),
    quantity INT
);
```

---

## Orders Table

```sql
CREATE TABLE orders (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT,
    product_id BIGINT,
    quantity INT,
    total_amount DECIMAL(10,2)
);
```

---

# Running the Project

Start services in the following order:

1. api-gateway
2. user-service
3. product-service
4. order-service

---

# API Testing

Swagger UI:
- http://localhost:8080/swagger-ui/index.html

---

# Postman Collection

Download the Postman Collection here:

[SmartCart Postman Collection](./New%20Collection.postman_collection.json)

---

# Important Concepts Used

- Microservices Architecture
- Spring Security
- JWT Authentication
- Dependency Injection
- JPA/Hibernate
- Feign Client
- Circuit Breaker
- Resilience4j
- REST APIs

---

