
package com.amazonmarket.amazonmarketsys.model.caja;

import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

public class MovimientoCajaTest {
    @Test
    void movimientoNuevoDebeSerIngresoPorDefecto() {
        MovimientoCaja movimiento = new MovimientoCaja();

        assertEquals(MovimientoCaja.TipoMovimientoCaja.INGRESO, movimiento.getTipoMovimiento());
        assertNotNull(movimiento.getFechaMovimiento());
    }

    @Test
    void debeRegistrarEgreso() {
        MovimientoCaja movimiento = new MovimientoCaja();
        movimiento.setTipoMovimiento(MovimientoCaja.TipoMovimientoCaja.EGRESO);
        movimiento.setMonto(new BigDecimal("20.00"));
        movimiento.setDescripcion("Compra de bolsas");
        movimiento.setResponsable("Cajero");

        assertEquals(MovimientoCaja.TipoMovimientoCaja.EGRESO, movimiento.getTipoMovimiento());
        assertEquals(0, movimiento.getMonto().compareTo(new BigDecimal("20.00")));
        assertEquals("Compra de bolsas", movimiento.getDescripcion());
        assertEquals("Cajero", movimiento.getResponsable());
    }
}
