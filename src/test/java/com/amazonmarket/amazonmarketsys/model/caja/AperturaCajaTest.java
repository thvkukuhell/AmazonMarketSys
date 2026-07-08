
package com.amazonmarket.amazonmarketsys.model.caja;

import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

public class AperturaCajaTest {
    @Test
    void debeCrearAperturaConFechaActual() {
        AperturaCaja apertura = new AperturaCaja();

        assertNotNull(apertura.getFechaApertura());
    }

    @Test
    void debeRegistrarMontoInicialYResponsable() {
        AperturaCaja apertura = new AperturaCaja();
        apertura.setMontoInicial(new BigDecimal("150.00"));
        apertura.setResponsable("Administrador");

        assertEquals(0, apertura.getMontoInicial().compareTo(new BigDecimal("150.00")));
        assertEquals("Administrador", apertura.getResponsable());
    }
}
