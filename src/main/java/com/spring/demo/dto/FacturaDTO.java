package com.spring.demo.dto;

import java.util.List;

import lombok.Data;

@Data
public class FacturaDTO {
    private Double impuesto;
    private ClienteDTO cliente;
    private List<ProductoFacturaDTO> productos;
    private List<MedioPagoDTO> medios_pago;
    private VendedorDTO vendedor;
    private CajeroDTO cajero;
}