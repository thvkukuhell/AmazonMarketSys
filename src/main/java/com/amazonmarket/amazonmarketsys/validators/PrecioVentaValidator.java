
package com.amazonmarket.amazonmarketsys.validators;

import java.math.BigDecimal;

public class PrecioVentaValidator {
    private PrecioVentaValidator() {
    }
    
    public static boolean esPrecioValido(BigDecimal precio) {
        return precio != null && precio.compareTo(BigDecimal.ZERO) > 0;
    }
    
    public static boolean esDescuentoValido(BigDecimal descuento, BigDecimal importe) {
        if (descuento == null || importe == null) {
            return false;
        }
        
        return descuento.compareTo(BigDecimal.ZERO) >= 0
               && descuento.compareTo(importe) <= 0;
    }
}
