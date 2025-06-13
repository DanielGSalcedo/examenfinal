package com.spring.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.demo.entities.TipoDocumento;

public interface TipoDocumentoRepository extends JpaRepository<TipoDocumento, Long> {
}
