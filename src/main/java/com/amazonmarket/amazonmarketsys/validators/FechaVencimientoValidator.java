
package com.amazonmarket.amazonmarketsys.validators;

import java.time.LocalDate;

public class FechaVencimientoValidator {
    private FechaVencimientoValidator() {
    }

    public static boolean esFechaVencimientoValida(LocalDate fechaIngreso, LocalDate fechaVencimiento) {
        if (fechaVencimiento == null) {
            return true;
        }

        if (fechaIngreso == null) {
            return false;
        }

        return fechaVencimiento.isAfter(fechaIngreso);
    }

    public static boolean esObligatoriaPorProducto(boolean productoPerecible, LocalDate fechaVencimiento) {
        return !productoPerecible || fechaVencimiento != null;
    }
}
