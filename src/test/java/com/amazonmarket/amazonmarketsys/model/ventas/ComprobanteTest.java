
package com.amazonmarket.amazonmarketsys.model.ventas;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ComprobanteTest {
    @Test
    void debeExistirBoletaElectronica() {
        assertEquals(Comprobante.BOLETA_ELECTRONICA, Comprobante.valueOf("BOLETA_ELECTRONICA"));
    }

    @Test
    void debeExistirFacturaElectronica() {
        assertEquals(Comprobante.FACTURA_ELECTRONICA, Comprobante.valueOf("FACTURA_ELECTRONICA"));
    }

    @Test
    void debeExistirTicketInterno() {
        assertEquals(Comprobante.TICKET_INTERNO, Comprobante.valueOf("TICKET_INTERNO"));
    }
}
