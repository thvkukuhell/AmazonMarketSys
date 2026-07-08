
package com.amazonmarket.amazonmarketsys.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.amazonmarket.amazonmarketsys.model.ventas.DetalleVenta;
import com.amazonmarket.amazonmarketsys.model.ventas.Venta;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

public class VentaServiceTest {
    @Test
    void debeRecalcularVentaCorrectamente() {
        Venta venta = new Venta();

        DetalleVenta detalle = new DetalleVenta();
        detalle.setCantidad(2);
        detalle.setPrecioUnitario(new BigDecimal("10.00"));
        detalle.setDescuento(BigDecimal.ZERO);
        detalle.calcularSubtotal();

        venta.getDetalles().add(detalle);
        venta.setPorcentajeDescuentoManual(new BigDecimal("10.00"));

        VentaService service = new VentaService();
        service.recalcularVenta(venta);

        assertEquals(0, venta.getSubtotal().compareTo(new BigDecimal("20.00")));
        assertEquals(0, venta.getDescuentoTotal().compareTo(new BigDecimal("2.0000")));
        assertEquals(0, venta.getTotal().compareTo(new BigDecimal("18.0000")));
    }

    @Test
    void debeLanzarErrorSiVentaEsNulaAlRecalcular() {
        VentaService service = new VentaService();

        assertThrows(IllegalArgumentException.class, () -> {
            service.recalcularVenta(null);
        });
    }

    @Test
    void debeAnularVentaUsandoMockito() {
        Venta ventaMock = mock(Venta.class);

        VentaService service = new VentaService();
        service.anularVenta(ventaMock);

        verify(ventaMock).anular();
    }
}
