
package com.amazonmarket.amazonmarketsys.model.ventas;

import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

public class VentaTest {
     @Test
    void ventaNuevaDebeEstarRegistrada() {
        Venta venta = new Venta();

        assertEquals(Venta.EstadoVenta.REGISTRADA, venta.getEstado());
        assertEquals(Comprobante.BOLETA_ELECTRONICA, venta.getComprobante());
        assertNotNull(venta.getFechaVenta());
        assertEquals(0, venta.getTotal().compareTo(BigDecimal.ZERO));
    }

    @Test
    void debeAnularVenta() {
        Venta venta = new Venta();

        venta.anular();

        assertEquals(Venta.EstadoVenta.ANULADA, venta.getEstado());
    }

    @Test
    void debeCalcularTotalesConDescuentoManual() {
        Venta venta = new Venta();

        DetalleVenta detalle1 = new DetalleVenta();
        detalle1.setCantidad(2);
        detalle1.setPrecioUnitario(new BigDecimal("10.00"));
        detalle1.setDescuento(BigDecimal.ZERO);
        detalle1.calcularSubtotal();

        DetalleVenta detalle2 = new DetalleVenta();
        detalle2.setCantidad(1);
        detalle2.setPrecioUnitario(new BigDecimal("5.00"));
        detalle2.setDescuento(BigDecimal.ZERO);
        detalle2.calcularSubtotal();

        venta.getDetalles().add(detalle1);
        venta.getDetalles().add(detalle2);
        venta.setPorcentajeDescuentoManual(new BigDecimal("10.00"));

        venta.calcularTotales();

        assertEquals(0, venta.getSubtotal().compareTo(new BigDecimal("25.00")));
        assertEquals(0, venta.getDescuentoTotal().compareTo(new BigDecimal("2.5000")));
        assertEquals(0, venta.getTotal().compareTo(new BigDecimal("22.5000")));
    }
}
