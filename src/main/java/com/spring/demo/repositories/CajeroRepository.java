package com.spring.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.demo.entities.Cajero;

public interface CajeroRepository extends JpaRepository<Cajero, Long> {
}
