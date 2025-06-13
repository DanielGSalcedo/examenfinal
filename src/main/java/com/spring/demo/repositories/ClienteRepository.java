package com.spring.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.demo.entities.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
