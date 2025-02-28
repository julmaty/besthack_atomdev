package com.example.besthack_atomdev.repository;

import com.example.besthack_atomdev.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
