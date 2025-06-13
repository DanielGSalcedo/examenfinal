package com.spring.demo.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.demo.entities.Cliente;
import com.spring.demo.entities.TipoDocumento;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
	 Optional<Cliente> findByNombreAndTipoDocumento(String nombre, TipoDocumento tipoDocumento);

}
