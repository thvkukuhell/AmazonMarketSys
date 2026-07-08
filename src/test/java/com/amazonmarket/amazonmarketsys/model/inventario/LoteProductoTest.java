
package com.amazonmarket.amazonmarketsys.model.inventario;

import static org.junit.jupiter.api.Assertions.*;
import com.amazonmarket.amazonmarketsys.model.catalogo.Producto;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

public class LoteProductoTest {
    @Test
    void loteNuevoDebeEstarActivoYConFechaDeIngresoHoy() {
        LoteProducto lote = new LoteProducto();

        assertTrue(lote.isActivo());
        assertEquals(LocalDate.now(), lote.getFechaIngreso());
    }

    @Test
    void debeIndicarSiTieneStock() {
        LoteProducto lote = new LoteProducto();
        lote.setCantidadActual(10);

        assertTrue(lote.tieneStock());

        lote.setCantidadActual(0);
        assertFalse(lote.tieneStock());
    }

    @Test
    void debeDescontarCantidadDelLote() {
        LoteProducto lote = new LoteProducto();
        lote.setCantidadActual(10);

        lote.descontar(4);

        assertEquals(6, lote.getCantidadActual());
    }

    @Test
    void noDebeDescontarMasCantidadQueLaDisponible() {
        LoteProducto lote = new LoteProducto();
        lote.setCantidadActual(5);

        assertThrows(IllegalStateException.class, () -> lote.descontar(10));
    }

    @Test
    void debeIncrementarCantidadDelLote() {
        LoteProducto lote = new LoteProducto();
        lote.setCantidadActual(5);

        lote.incrementar(3);

        assertEquals(8, lote.getCantidadActual());
    }

    @Test
    void debeAsociarProductoYCosto() {
        Producto producto = new Producto();
        producto.setNombre("Yogurt");

        LoteProducto lote = new LoteProducto();
        lote.setProducto(producto);
        lote.setNumeroLote("L-2026-001");
        lote.setCostoUnitario(new BigDecimal("3.50"));

        assertEquals("Yogurt", lote.getProducto().getNombre());
        assertEquals("L-2026-001", lote.getNumeroLote());
        assertEquals(0, lote.getCostoUnitario().compareTo(new BigDecimal("3.50")));
    }
}
