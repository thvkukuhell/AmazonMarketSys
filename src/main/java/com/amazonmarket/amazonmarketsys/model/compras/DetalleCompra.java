package com.amazonmarket.amazonmarketsys.model.compras;

import java.math.*;
import javax.persistence.*;
import org.openxava.annotations.*;
import com.amazonmarket.amazonmarketsys.model.catalogo.*;
import lombok.*;

@Entity
@Getter @Setter
public class DetalleCompra {

    @Id
    @Hidden
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    int id;

    @ManyToOne(fetch=FetchType.LAZY)
    @Hidden
    Compra compra;

    @Column(length=30)
    @ReadOnly
    String codigoDetalle;

    @ManyToOne(fetch=FetchType.LAZY)
    @DescriptionsList(descriptionProperties="codigo, nombre")
    @Required
    Producto producto;
    
    @Column(length=30)
    @ReadOnly
    String codigoProducto;
    
    @Column(length=100)
    @ReadOnly
    String nombreProducto;
    
    int cantidad;
    
    @Money
    BigDecimal precioUnitario = BigDecimal.ZERO;
    
    @Money
    BigDecimal subtotal = BigDecimal.ZERO;

    @PrePersist
    @PreUpdate
    void antesDeGuardar() {
        sincronizarDatosProducto();
        calcularSubtotal();
    }

    public void generarCodigoDetalleAutomatico(String codigoCompra, int numeroDetalle) {
        if (codigoDetalle != null && !codigoDetalle.trim().isEmpty()) {
            return;
        }

        String prefijo = codigoCompra == null || codigoCompra.trim().isEmpty()
            ? "DET"
            : codigoCompra + "-DET";
        codigoDetalle = prefijo + "-" + String.format("%03d", numeroDetalle);
    }

    public void sincronizarDatosProducto() {
        if (producto == null) {
            return;
        }

        codigoProducto = producto.getCodigo();
        nombreProducto = producto.getNombre();

        if ((precioUnitario == null || precioUnitario.signum() == 0) && producto.getPrecioCompra() != null) {
            precioUnitario = producto.getPrecioCompra();
        }
    }
    
    public void calcularSubtotal() {
        if (cantidad <= 0 || precioUnitario == null) {
            subtotal = BigDecimal.ZERO;
        } else {
            subtotal = precioUnitario.multiply(new BigDecimal(cantidad));
        }
    }
}
