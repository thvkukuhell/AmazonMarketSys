
package com.amazonmarket.amazonmarketsys.model.ventas;

import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

public class DetalleVentaTest {
    @Test
    void debeCalcularSubtotalSinDescuento() {
        DetalleVenta detalle = new DetalleVenta();
        detalle.setCantidad(3);
        detalle.setPrecioUnitario(new BigDecimal("2.50"));
        detalle.setDescuento(BigDecimal.ZERO);

        detalle.calcularSubtotal();

        assertEquals(0, detalle.getSubtotal().compareTo(new BigDecimal("7.50")));
    }

    @Test
    void debeCalcularSubtotalConDescuento() {
        DetalleVenta detalle = new DetalleVenta();
        detalle.setCantidad(4);
        detalle.setPrecioUnitario(new BigDecimal("5.00"));
        detalle.setDescuento(new BigDecimal("3.00"));

        detalle.calcularSubtotal();

        assertEquals(0, detalle.getSubtotal().compareTo(new BigDecimal("17.00")));
    }

    @Test
    void subtotalNoDebeSerNegativo() {
        DetalleVenta detalle = new DetalleVenta();
        detalle.setCantidad(1);
        detalle.setPrecioUnitario(new BigDecimal("2.00"));
        detalle.setDescuento(new BigDecimal("10.00"));

        detalle.calcularSubtotal();

        assertEquals(0, detalle.getSubtotal().compareTo(BigDecimal.ZERO));
    }
}
