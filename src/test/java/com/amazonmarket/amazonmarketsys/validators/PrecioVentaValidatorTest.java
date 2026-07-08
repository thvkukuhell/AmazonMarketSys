
package com.amazonmarket.amazonmarketsys.validators;

import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

public class PrecioVentaValidatorTest {
    @Test
    void precioMayorACeroDebeSerValido() {
        assertTrue(PrecioVentaValidator.esPrecioValido(new BigDecimal("2.50")));
    }

    @Test
    void precioCeroDebeSerInvalido() {
        assertFalse(PrecioVentaValidator.esPrecioValido(BigDecimal.ZERO));
    }

    @Test
    void descuentoNoDebeSuperarImporte() {
        assertTrue(PrecioVentaValidator.esDescuentoValido(
                new BigDecimal("5.00"),
                new BigDecimal("20.00")
        ));

        assertFalse(PrecioVentaValidator.esDescuentoValido(
                new BigDecimal("25.00"),
                new BigDecimal("20.00")
        ));
    }
}
