package com.spring.demo.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.demo.entities.Cajero;

public interface CajeroRepository extends JpaRepository<Cajero, Long> {
	Optional<Cajero> findByToken(String token);

}