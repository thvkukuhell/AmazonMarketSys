
package com.amazonmarket.amazonmarketsys.model.ventas;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class MetodoPagoTest {
    @Test
    void debeExistirMetodoPagoYape() {
        assertEquals(MetodoPago.YAPE, MetodoPago.valueOf("YAPE"));
    }

    @Test
    void debeExistirMetodoPagoPlin() {
        assertEquals(MetodoPago.PLIN, MetodoPago.valueOf("PLIN"));
    }

    @Test
    void debeExistirMetodoPagoTarjeta() {
        assertEquals(MetodoPago.TARJETA, MetodoPago.valueOf("TARJETA"));
    }
}
