package com.spring.demo.dto;

import lombok.Data;

@Data
public class ProductoFacturaDTO {
    private String referencia;
    private Integer cantidad;
    private Double descuento;
}