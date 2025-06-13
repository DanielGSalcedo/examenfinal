package com.spring.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.demo.entities.TipoPago;

public interface TipoPagoRepository extends JpaRepository<TipoPago, Long> {
}
