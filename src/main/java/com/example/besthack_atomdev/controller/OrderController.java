package com.example.besthack_atomdev.controller;
import com.example.besthack_atomdev.dto.OrderResponse;
import com.example.besthack_atomdev.model.User;
import com.example.besthack_atomdev.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.example.besthack_atomdev.dto.CreateOrderRequest;
import com.example.besthack_atomdev.model.Order;
import com.example.besthack_atomdev.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    // Получить все заказы
    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        List<OrderResponse> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    // Получить заказ по ID
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable long id) {
        return orderService.getOrderById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Создать новый заказ
    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody CreateOrderRequest request) {
        // Извлечение clientId из токена авторизации
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName(); // Предполагается, что username в токене - это email

        // Поиск пользователя по email
        User user = userService.findUserByEmail(email);
        if (user == null) {
            throw new IllegalArgumentException("Пользователь с email " + email + " не найден");
        }

        // Создание заказа
        Order createdOrder = orderService.createOrder(user.getId(), request);
        return ResponseEntity.status(201).body(createdOrder);
    }

    // Удалить заказ
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable long id) {
        boolean isDeleted = orderService.deleteOrder(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
