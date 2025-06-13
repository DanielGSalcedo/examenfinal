package com.spring.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.demo.entities.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
}
