package com.spring.demo.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.demo.entities.Vendedor;

public interface VendedorRepository extends JpaRepository<Vendedor, Long> {
	Optional<Vendedor> findByDocumento(String documento);
}
