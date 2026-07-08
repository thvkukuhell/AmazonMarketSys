
package com.amazonmarket.amazonmarketsys.calculators;

import com.amazonmarket.amazonmarketsys.model.ventas.DetalleVenta;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Objects;

public class TotalVentaCalculator {
    private TotalVentaCalculator() {
    }
    
    public static BigDecimal calcularSubtotal(Collection<DetalleVenta> detalles) {
        if (detalles == null || detalles.isEmpty()) {
            return BigDecimal.ZERO;
        }
        
        return detalles.stream()
                .map(DetalleVenta::getSubtotal)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    
    public static BigDecimal calcularDescuento(BigDecimal subtotal, BigDecimal porcentaje) {
        if (subtotal == null || porcentaje == null) {
            return BigDecimal.ZERO;
        }

        return subtotal.multiply(porcentaje).divide(BigDecimal.valueOf(100));
    }
    
    public static BigDecimal calcularTotal(BigDecimal subtotal, BigDecimal descuento) {
        if (subtotal == null) subtotal = BigDecimal.ZERO;
        if (descuento == null) descuento = BigDecimal.ZERO;

        BigDecimal total = subtotal.subtract(descuento);
        return total.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : total;
    }
}
