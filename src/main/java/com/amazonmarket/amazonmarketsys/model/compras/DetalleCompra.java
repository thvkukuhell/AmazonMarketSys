package com.amazonmarket.amazonmarketsys.model.compras;

import java.math.*;
import javax.persistence.*;
import org.openxava.annotations.*;
import lombok.*;

@Embeddable
@Getter @Setter
public class DetalleCompra {
    
    @Column(length=30)
    @Required
    String codigoProducto;
    
    @Column(length=100)
    @Required
    String nombreProducto;
    
    int cantidad;
    
    @Money
    BigDecimal precioUnitario = BigDecimal.ZERO;
    
    @Money
    BigDecimal subtotal = BigDecimal.ZERO;
    
    public void calcularSubtotal() {
        if (cantidad <= 0 || precioUnitario == null) {
            subtotal = BigDecimal.ZERO;
        } else {
            subtotal = precioUnitario.multiply(new BigDecimal(cantidad));
        }
    }
}
