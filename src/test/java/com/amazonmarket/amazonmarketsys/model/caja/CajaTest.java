
package com.amazonmarket.amazonmarketsys.model.caja;

import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

public class CajaTest {
    @Test
    void cajaNuevaDebeEstarCerrada() {
        Caja caja = new Caja();

        assertEquals(Caja.EstadoCaja.CERRADA, caja.getEstado());
        assertFalse(caja.estaAbierta());
    }

    @Test
    void debeIndicarCajaAbierta() {
        Caja caja = new Caja();
        caja.setEstado(Caja.EstadoCaja.ABIERTA);

        assertTrue(caja.estaAbierta());
    }

    @Test
    void debeRegistrarSaldoInicialYActual() {
        Caja caja = new Caja();
        caja.setSaldoInicial(new BigDecimal("100.00"));
        caja.setSaldoActual(new BigDecimal("250.00"));

        assertEquals(0, caja.getSaldoInicial().compareTo(new BigDecimal("100.00")));
        assertEquals(0, caja.getSaldoActual().compareTo(new BigDecimal("250.00")));
    }
}
