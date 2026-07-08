
package com.amazonmarket.amazonmarketsys.calculators;

import static org.junit.jupiter.api.Assertions.*;
import com.amazonmarket.amazonmarketsys.model.ventas.DetalleVenta;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

public class TotalVentaCalculatorTest {
    @Test
    void debeCalcularSubtotalDeDetalles() {
        DetalleVenta d1 = new DetalleVenta();
        d1.setCantidad(2);
        d1.setPrecioUnitario(new BigDecimal("10.00"));
        d1.setDescuento(BigDecimal.ZERO);
        d1.calcularSubtotal();

        DetalleVenta d2 = new DetalleVenta();
        d2.setCantidad(1);
        d2.setPrecioUnitario(new BigDecimal("5.00"));
        d2.setDescuento(BigDecimal.ZERO);
        d2.calcularSubtotal();

        List<DetalleVenta> detalles = new ArrayList<>();
        detalles.add(d1);
        detalles.add(d2);

        BigDecimal subtotal = TotalVentaCalculator.calcularSubtotal(detalles);

        assertEquals(0, subtotal.compareTo(new BigDecimal("25.00")));
    }

    @Test
    void debeCalcularDescuento() {
        BigDecimal descuento = TotalVentaCalculator.calcularDescuento(
                new BigDecimal("100.00"),
                new BigDecimal("10.00")
        );

        assertEquals(0, descuento.compareTo(new BigDecimal("10.0000")));
    }

    @Test
    void totalNoDebeSerNegativo() {
        BigDecimal total = TotalVentaCalculator.calcularTotal(
                new BigDecimal("10.00"),
                new BigDecimal("50.00")
        );

        assertEquals(0, total.compareTo(BigDecimal.ZERO));
    }
}
