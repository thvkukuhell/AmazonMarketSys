package com.amazonmarket.amazonmarketsys.model.compras;

import java.math.*;
import javax.persistence.*;
import org.openxava.annotations.*;
import com.amazonmarket.amazonmarketsys.model.catalogo.*;
import lombok.*;

@Entity
@Getter @Setter
@View(members =
    "Producto { producto; codigoProducto; nombreProducto; } " +
    "Datos { codigoDetalle; cantidad; precioUnitario; subtotal; }"
)
public class DetalleCompra {

    @Id
    @Hidden
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    int id;

    @ManyToOne(fetch=FetchType.LAZY)
    Compra compra;

    @ManyToOne(fetch=FetchType.LAZY)
    @DescriptionsList(descriptionProperties="codigo, nombre")
    @Required
    Producto producto;
    
    int cantidad;
    
    @Money
    BigDecimal precioUnitario = BigDecimal.ZERO;
    
    @Money
    @ReadOnly
    BigDecimal subtotal = BigDecimal.ZERO;

    @PrePersist
    @PreUpdate
    public void antesDeGuardar() {
        sincronizarDatosProducto();
        calcularSubtotal();
    }

    @ReadOnly
    public String getCodigoDetalle() {
        return id == 0 ? null : String.format("DET-%06d", id);
    }

    @ReadOnly
    public String getCodigoProducto() {
        return producto == null ? null : producto.getCodigo();
    }

    @ReadOnly
    public String getNombreProducto() {
        return producto == null ? null : producto.getNombre();
    }

    public void sincronizarDatosProducto() {
        if (producto == null) {
            return;
        }

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
