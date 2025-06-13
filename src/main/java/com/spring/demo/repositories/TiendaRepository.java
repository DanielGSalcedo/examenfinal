package com.spring.demo.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.demo.entities.Tienda;

public interface TiendaRepository extends JpaRepository<Tienda, Long> {
	Optional<Tienda> findByUuid(String uuid);
}
