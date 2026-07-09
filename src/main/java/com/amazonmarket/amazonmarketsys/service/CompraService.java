package com.amazonmarket.amazonmarketsys.service;

import com.amazonmarket.amazonmarketsys.model.compras.*;
import org.openxava.jpa.*;

public class CompraService {
    
    public void validarCompra(Compra compra) {
        if (compra == null) {
            throw new IllegalArgumentException("La compra es obligatoria");
        }
        if (compra.getProveedor() == null) {
            throw new IllegalArgumentException("Debe seleccionar un proveedor");
        }
        if (compra.getFechaCompra() == null) {
            throw new IllegalArgumentException("La fecha de compra es obligatoria");
        }
        if (compra.getNumeroDocumento() == null || compra.getNumeroDocumento().trim().isEmpty()) {
            throw new IllegalArgumentException("El numero de documento es obligatorio");
        }
        if (compra.getDetalles() == null || compra.getDetalles().isEmpty()) {
            throw new IllegalArgumentException("Debe ingresar al menos un detalle");
        }
        
        for (DetalleCompra detalle: compra.getDetalles()) {
            if (detalle == null) {
                throw new IllegalArgumentException("El detalle de compra no puede estar vacio");
            }
            if (detalle.getProducto() == null) {
                throw new IllegalArgumentException("Debe seleccionar un producto");
            }
            if (detalle.getCantidad() <= 0) {
                throw new IllegalArgumentException("La cantidad debe ser mayor que cero");
            }
            if (detalle.getPrecioUnitario() == null || detalle.getPrecioUnitario().signum() < 0) {
                throw new IllegalArgumentException("El precio unitario debe ser mayor o igual que cero");
            }
        }
    }
    
    public void calcularTotales(Compra compra) {
        if (compra != null) {
            compra.calcularTotales();
        }
    }

    public void generarCodigosAutomaticos(Compra compra) {
        if (compra != null) {
            compra.generarCodigosAutomaticos();
        }
    }
    
    public void registrarCompra(Compra compra) {
        generarCodigosAutomaticos(compra);
        validarCompra(compra);
        calcularTotales(compra);
        compra.setEstadoCompra("REGISTRADA");
        
        if (compra.getId() == 0) {
            XPersistence.getManager().persist(compra);
        } else {
            XPersistence.getManager().merge(compra);
        }
    }
    
    public void anularCompra(Compra compra) {
        if (compra == null) {
            throw new IllegalArgumentException("La compra es obligatoria");
        }
        compra.setEstadoCompra("ANULADA");
        XPersistence.getManager().merge(compra);
    }
}
