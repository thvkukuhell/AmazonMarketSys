package com.amazonmarket.amazonmarketsys.service;

import com.amazonmarket.amazonmarketsys.calculators.TotalVentaCalculator;
import com.amazonmarket.amazonmarketsys.model.ventas.Venta;
import java.math.BigDecimal;

public class VentaService {

    public void recalcularVenta(Venta venta) {
        if (venta == null) {
            throw new IllegalArgumentException("La venta no puede ser nula");
        }

        BigDecimal subtotal = TotalVentaCalculator.calcularSubtotal(venta.getDetalles());
        BigDecimal descuento = TotalVentaCalculator.calcularDescuento(
                subtotal,
                venta.getPorcentajeDescuentoManual()
        );
        BigDecimal total = TotalVentaCalculator.calcularTotal(subtotal, descuento);

        venta.setSubtotal(subtotal);
        venta.setDescuentoTotal(descuento);
        venta.setTotal(total);
    }

    public void anularVenta(Venta venta) {
        if (venta == null) {
            throw new IllegalArgumentException("La venta no puede ser nula");
        }

        venta.anular();
    }
}