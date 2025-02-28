package com.example.besthack_atomdev.controller;

import com.example.besthack_atomdev.dto.OrderRequest;
import com.example.besthack_atomdev.model.Customer;
import com.example.besthack_atomdev.model.Order;
import com.example.besthack_atomdev.model.Product;
import com.example.besthack_atomdev.repository.CustomerRepository;
import com.example.besthack_atomdev.repository.OrderRepository;
import com.example.besthack_atomdev.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    // Получить список всех заказов
    @GetMapping
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // Получить заказ по id
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        return optionalOrder.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Создать новый заказ через DTO (без передачи id заказа, только id покупателя и товаров)
    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody OrderRequest orderRequest) {
        // Получаем покупателя по id
        Optional<Customer> customerOptional = customerRepository.findById(orderRequest.getCustomerId());
        if (!customerOptional.isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        Customer customer = customerOptional.get();

        // Получаем список товаров по переданным id
        List<Product> products = productRepository.findAllById(orderRequest.getProductIds());
        if (products.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        // Формируем новый заказ
        Order order = new Order();
        order.setCustomer(customer);
        order.setProducts(products);

        Order savedOrder = orderRepository.save(order);
        return ResponseEntity.ok(savedOrder);
    }

    // Обновить существующий заказ через DTO (передаем id заказа в path, а в теле – customerId и список productIds)
    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @RequestBody OrderRequest orderRequest) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (!optionalOrder.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Order order = optionalOrder.get();

        // Обновляем покупателя заказа
        Optional<Customer> customerOptional = customerRepository.findById(orderRequest.getCustomerId());
        if (!customerOptional.isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        Customer customer = customerOptional.get();
        order.setCustomer(customer);

        // Обновляем список товаров заказа
        List<Product> products = productRepository.findAllById(orderRequest.getProductIds());
        if (products.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        order.setProducts(products);

        Order updatedOrder = orderRepository.save(order);
        return ResponseEntity.ok(updatedOrder);
    }

    // Удалить заказ по id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        if (!orderRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        orderRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
