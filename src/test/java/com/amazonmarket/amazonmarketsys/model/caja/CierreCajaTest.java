
package com.amazonmarket.amazonmarketsys.model.caja;

import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

public class CierreCajaTest {
    @Test
    void debeCrearCierreConFechaActual() {
        CierreCaja cierre = new CierreCaja();

        assertNotNull(cierre.getFechaCierre());
    }

    @Test
    void debeCalcularDiferenciaDeCaja() {
        CierreCaja cierre = new CierreCaja();
        cierre.setTotalVentas(new BigDecimal("500.00"));
        cierre.setTotalIngresos(new BigDecimal("50.00"));
        cierre.setTotalEgresos(new BigDecimal("30.00"));
        cierre.setMontoFinal(new BigDecimal("525.00"));

        cierre.calcularDiferencia();

        assertEquals(0, cierre.getDiferencia().compareTo(new BigDecimal("5.00")));
    }
}
