
package com.amazonmarket.amazonmarketsys.model.inventario;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class TipoMovimientoTest {
    @Test
    void entradaCompraAjustePositivoYDevolucionDebenSerIncremento() {
        assertTrue(TipoMovimiento.ENTRADA_COMPRA.esIncremento());
        assertTrue(TipoMovimiento.AJUSTE_POSITIVO.esIncremento());
        assertTrue(TipoMovimiento.DEVOLUCION.esIncremento());
    }

    @Test
    void salidaVentaAjusteNegativoYMermaNoDebenSerIncremento() {
        assertFalse(TipoMovimiento.SALIDA_VENTA.esIncremento());
        assertFalse(TipoMovimiento.AJUSTE_NEGATIVO.esIncremento());
        assertFalse(TipoMovimiento.MERMA.esIncremento());
    }
}
