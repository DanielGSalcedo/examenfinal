package com.spring.demo.services;

import java.time.LocalDateTime;

import com.spring.demo.dto.FacturaDTO;
import com.spring.demo.dto.MedioPagoDTO;
import com.spring.demo.dto.ProductoFacturaDTO;
import com.spring.demo.entities.Cajero;
import com.spring.demo.entities.Cliente;
import com.spring.demo.entities.Compra;
import com.spring.demo.entities.DetallesCompra;
import com.spring.demo.entities.Pago;
import com.spring.demo.entities.Producto;
import com.spring.demo.entities.Tienda;
import com.spring.demo.entities.TipoDocumento;
import com.spring.demo.entities.TipoPago;
import com.spring.demo.entities.Vendedor;
import com.spring.demo.repositories.CajeroRepository;
import com.spring.demo.repositories.ClienteRepository;
import com.spring.demo.repositories.CompraRepository;
import com.spring.demo.repositories.DetallesCompraRepository;
import com.spring.demo.repositories.PagoRepository;
import com.spring.demo.repositories.ProductoRepository;
import com.spring.demo.repositories.TiendaRepository;
import com.spring.demo.repositories.TipoDocumentoRepository;
import com.spring.demo.repositories.TipoPagoRepository;
import com.spring.demo.repositories.VendedorRepository;

import jakarta.transaction.Transactional;

public class FacturaService {
	

    private final ClienteRepository clienteRepository;
    private final TipoDocumentoRepository tipoDocumentoRepository;
    private final ProductoRepository productoRepository;
    private final CompraRepository compraRepository;
    private final DetallesCompraRepository detallesCompraRepository;
    private final TipoPagoRepository tipoPagoRepository;
    private final PagoRepository pagoRepository;
    private final VendedorRepository vendedorRepository;
    private final CajeroRepository cajeroRepository;
    private final TiendaRepository tiendaRepository;

    @Transactional
    public Compra procesarFactura(String tiendaUuid, FacturaDTO facturaDTO) {
        // 1. Buscar tienda
        Tienda tienda = tiendaRepository.findByUuid(tiendaUuid)
            .orElseThrow(() -> new RuntimeException("Tienda no encontrada"));

        // 2. Buscar o crear tipo de documento
        TipoDocumento tipoDoc = tipoDocumentoRepository.findByNombre(facturaDTO.getCliente().getTipo_documento())
        	    .orElseGet(() -> tipoDocumentoRepository.save(
        	        TipoDocumento.builder()
        	            .nombre(facturaDTO.getCliente().getTipo_documento())
        	            .build()
        	    ));

        // 3. Buscar o crear cliente
        Cliente cliente = clienteRepository.findByNombreAndTipoDocumento(
        	    facturaDTO.getCliente().getNombre(), tipoDoc
        	).orElseGet(() -> clienteRepository.save(
        	    Cliente.builder	()
        	        .nombre(facturaDTO.getCliente().getNombre())
        	        .documento(facturaDTO.getCliente().getDocumento())
        	        .tipoDocumento(tipoDoc)
        	        .build()
        	));
        // 4. Buscar vendedor
        Vendedor vendedor = vendedorRepository.findByDocumento(facturaDTO.getVendedor().getDocumento())
            .orElseThrow(() -> new RuntimeException("Vendedor no encontrado"));

        // 5. Buscar cajero
	        Cajero cajero = cajeroRepository.findByToken(facturaDTO.getCajero().getToken())
	            .orElseThrow(() -> new RuntimeException("Cajero no encontrado"));

        // 6. Crear la compra	
        Compra compra = new Compra();
        compra.setCliente(cliente);
        compra.setTienda(tienda);
        compra.setVendedor(vendedor);
        compra.setCajero(cajero);
        compra.setFecha(LocalDateTime.now());
        compra.setImpuestos(facturaDTO.getImpuesto());
        compra = compraRepository.save(compra);

        double totalCompra = 0.0;

        // 7. Procesar productos
        for (ProductoFacturaDTO prodDto : facturaDTO.getProductos()) {
            Producto producto = productoRepository.findByNombre(prodDto.getReferencia())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado: " + prodDto.getReferencia()));

            double precio = producto.getPrecio() * prodDto.getCantidad();
            double descuento = prodDto.getDescuento();
            double finalPrecio = precio - (precio * descuento / 100);

            DetallesCompra detalle = new DetallesCompra();
            detalle.setCompra(compra);
            detalle.setProducto(producto);
            detalle.setCantidad(prodDto.getCantidad());
            detalle.setPrecio(producto.getPrecio());
            detalle.setDescuento(descuento);
            detallesCompraRepository.save(detalle);

            totalCompra += finalPrecio;
        }

        // 8. Procesar pagos
        for (MedioPagoDTO medio : facturaDTO.getMedios_pago()) {
            TipoPago tipoPago = tipoPagoRepository.findByNombre(medio.getTipo_pago())
                .orElseGet(() -> tipoPagoRepository.save(new TipoPago(null, medio.getTipo_pago())));

            Pago pago = new Pago();
            pago.setCompra(compra);
            pago.setTipoPago(tipoPago);
            pago.setTarjetaTipo(medio.getTipo_tarjeta());
            pago.setCuotas(medio.getCuotas() != null ? medio.getCuotas() : 1);
            pago.setValor(medio.getValor());
            pagoRepository.save(pago);
        }

        compra.setTotal(totalCompra + facturaDTO.getImpuesto());
        return compraRepository.save(compra);
    }
}
