package com.example.besthack_atomdev.repository;

import com.example.besthack_atomdev.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByClientId(long clientId);
}
