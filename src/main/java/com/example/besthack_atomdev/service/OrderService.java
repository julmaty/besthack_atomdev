package com.example.besthack_atomdev.service;

import com.example.besthack_atomdev.model.Order;
import com.example.besthack_atomdev.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    // Получить все заказы
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // Получить заказ по ID
    public Optional<Order> getOrderById(long id) {
        return orderRepository.findById(id);
    }

    // Создать новый заказ
    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    // Обновить заказ
    public Order updateOrder(long id, Order updatedOrder) {
        Optional<Order> existingOrder = orderRepository.findById(id);
        if (existingOrder.isPresent()) {
            updatedOrder.setId(id); // Сохраняем ID
            return orderRepository.save(updatedOrder);
        }
        return null; // Заказ не найден
    }

    // Удалить заказ
    public boolean deleteOrder(long id) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isPresent()) {
            orderRepository.deleteById(id);
            return true;
        }
        return false; // Заказ не найден
    }
}
