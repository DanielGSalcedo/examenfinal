package com.spring.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.demo.entities.TipoProducto;

public interface TipoProductoRepository extends JpaRepository<TipoProducto, Long> {
}
