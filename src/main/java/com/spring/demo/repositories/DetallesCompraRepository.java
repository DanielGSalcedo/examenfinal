package com.spring.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.demo.entities.DetallesCompra;

public interface DetallesCompraRepository extends JpaRepository<DetallesCompra, Long> {
}

