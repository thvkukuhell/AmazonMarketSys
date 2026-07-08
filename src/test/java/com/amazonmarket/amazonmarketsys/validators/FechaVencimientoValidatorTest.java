
package com.amazonmarket.amazonmarketsys.validators;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

public class FechaVencimientoValidatorTest {
    @Test
    void debeSerValidaSiNoTieneFechaDeVencimiento() {
        assertTrue(FechaVencimientoValidator.esFechaVencimientoValida(LocalDate.now(), null));
    }

    @Test
    void debeSerValidaSiVenceDespuesDeIngresar() {
        LocalDate ingreso = LocalDate.now();
        LocalDate vencimiento = ingreso.plusDays(30);

        assertTrue(FechaVencimientoValidator.esFechaVencimientoValida(ingreso, vencimiento));
    }

    @Test
    void noDebeSerValidaSiVenceAntesOElMismoDiaDeIngreso() {
        LocalDate ingreso = LocalDate.now();

        assertFalse(FechaVencimientoValidator.esFechaVencimientoValida(ingreso, ingreso));
        assertFalse(FechaVencimientoValidator.esFechaVencimientoValida(ingreso, ingreso.minusDays(1)));
    }

    @Test
    void debeExigirFechaDeVencimientoSoloParaProductosPerecibles() {
        assertTrue(FechaVencimientoValidator.esObligatoriaPorProducto(false, null));
        assertFalse(FechaVencimientoValidator.esObligatoriaPorProducto(true, null));
        assertTrue(FechaVencimientoValidator.esObligatoriaPorProducto(true, LocalDate.now().plusDays(5)));
    }
}
