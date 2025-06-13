package com.spring.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.demo.entities.Compra;

public interface CompraRepository extends JpaRepository<Compra, Long> {
}
