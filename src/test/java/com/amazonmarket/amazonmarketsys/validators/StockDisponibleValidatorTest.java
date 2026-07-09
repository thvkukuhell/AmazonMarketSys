
package com.amazonmarket.amazonmarketsys.validators;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class StockDisponibleValidatorTest {
    @Test
    void debeValidarCantidadPositiva() {
        assertTrue(StockDisponibleValidator.esCantidadValida(1));
        assertFalse(StockDisponibleValidator.esCantidadValida(0));
        assertFalse(StockDisponibleValidator.esCantidadValida(-1));
    }

    @Test
    void debeIndicarSiHaySuficienteStock() {
        assertTrue(StockDisponibleValidator.haySuficienteStock(10, 5));
        assertTrue(StockDisponibleValidator.haySuficienteStock(10, 10));
        assertFalse(StockDisponibleValidator.haySuficienteStock(10, 11));
    }

    @Test
    void noDebeHaberStockSuficienteSiLaCantidadSolicitadaNoEsValida() {
        assertFalse(StockDisponibleValidator.haySuficienteStock(10, 0));
        assertFalse(StockDisponibleValidator.haySuficienteStock(10, -5));
    }
}
