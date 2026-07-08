
package com.amazonmarket.amazonmarketsys.model.ventas;

import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

public class PagoVentaTest {
    @Test
    void pagoNuevoDebeSerEfectivoPorDefecto() {
        PagoVenta pago = new PagoVenta();

        assertEquals(MetodoPago.EFECTIVO, pago.getMetodoPago());
        assertNotNull(pago.getFechaPago());
    }

    @Test
    void debeRegistrarPagoConYape() {
        PagoVenta pago = new PagoVenta();
        pago.setMetodoPago(MetodoPago.YAPE);
        pago.setMonto(new BigDecimal("35.50"));
        pago.setReferencia("Operacion 123456");

        assertEquals(MetodoPago.YAPE, pago.getMetodoPago());
        assertEquals(0, pago.getMonto().compareTo(new BigDecimal("35.50")));
        assertEquals("Operacion 123456", pago.getReferencia());
    }
}
