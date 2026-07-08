
package com.amazonmarket.amazonmarketsys.model.promociones;

import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

public class PromocionTest {
    @Test
    void promocionNuevaDebeEstarActiva() {
        Promocion promocion = new Promocion();

        assertTrue(promocion.isActiva());
        assertEquals(Promocion.TipoPromocion.POR_MONTO, promocion.getTipoPromocion());
    }

    @Test
    void debeIndicarPromocionVigente() {
        Promocion promocion = new Promocion();
        promocion.setActiva(true);
        promocion.setFechaInicio(LocalDate.now().minusDays(1));
        promocion.setFechaFin(LocalDate.now().plusDays(5));

        assertTrue(promocion.estaVigente());
    }

    @Test
    void debeIndicarPromocionVencida() {
        Promocion promocion = new Promocion();
        promocion.setActiva(true);
        promocion.setFechaInicio(LocalDate.now().minusDays(10));
        promocion.setFechaFin(LocalDate.now().minusDays(1));

        assertFalse(promocion.estaVigente());
    }

    @Test
    void debeRegistrarDescuentoPorMonto() {
        Promocion promocion = new Promocion();
        promocion.setMontoMinimo(new BigDecimal("100.00"));
        promocion.setPorcentajeDescuento(new BigDecimal("5.00"));

        assertEquals(0, promocion.getMontoMinimo().compareTo(new BigDecimal("100.00")));
        assertEquals(0, promocion.getPorcentajeDescuento().compareTo(new BigDecimal("5.00")));
    }
}
