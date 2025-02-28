package com.example.besthack_atomdev.repository;

import com.example.besthack_atomdev.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}