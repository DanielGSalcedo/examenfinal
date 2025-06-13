package com.spring.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.demo.entities.Pago;

public interface PagoRepository extends JpaRepository<Pago, Long> {
}
