
package com.amazonmarket.amazonmarketsys.model.promociones;

import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

public class ReglaDescuentoTest {
    @Test
    void debeRegistrarReglaPorClienteFrecuente() {
        ReglaDescuento regla = new ReglaDescuento();
        regla.setRequiereClienteFrecuente(true);
        regla.setPorcentajeDescuento(new BigDecimal("8.00"));

        assertTrue(regla.isRequiereClienteFrecuente());
        assertEquals(0, regla.getPorcentajeDescuento().compareTo(new BigDecimal("8.00")));
    }

    @Test
    void debeRegistrarReglaPorProductoPorVencer() {
        ReglaDescuento regla = new ReglaDescuento();
        regla.setDiasAntesVencimiento(7);
        regla.setPorcentajeDescuento(new BigDecimal("10.00"));

        assertEquals(7, regla.getDiasAntesVencimiento());
        assertEquals(0, regla.getPorcentajeDescuento().compareTo(new BigDecimal("10.00")));
    }
}
