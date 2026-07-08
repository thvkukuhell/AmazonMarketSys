
package com.amazonmarket.amazonmarketsys.service;

import static org.junit.jupiter.api.Assertions.*;

import com.amazonmarket.amazonmarketsys.model.inventario.LoteProducto;
import org.junit.jupiter.api.Test;

public class InventarioServiceTest {
    @Test
    void debeRegistrarIngresoDeStock() {
        LoteProducto lote = new LoteProducto();
        lote.setCantidadActual(10);

        InventarioService service = new InventarioService();
        service.registrarIngreso(lote, 5);

        assertEquals(15, lote.getCantidadActual());
    }

    @Test
    void debeRegistrarSalidaDeStock() {
        LoteProducto lote = new LoteProducto();
        lote.setCantidadActual(10);

        InventarioService service = new InventarioService();
        service.registrarSalida(lote, 4);

        assertEquals(6, lote.getCantidadActual());
    }

    @Test
    void noDebeRegistrarSalidaSiNoHayStockSuficiente() {
        LoteProducto lote = new LoteProducto();
        lote.setCantidadActual(3);

        InventarioService service = new InventarioService();

        assertThrows(IllegalStateException.class, () -> service.registrarSalida(lote, 10));
    }

    @Test
    void debeRegistrarMermaComoUnaSalida() {
        LoteProducto lote = new LoteProducto();
        lote.setCantidadActual(10);

        InventarioService service = new InventarioService();
        service.registrarMerma(lote, 2);

        assertEquals(8, lote.getCantidadActual());
    }

    @Test
    void noDebeAceptarLoteNulo() {
        InventarioService service = new InventarioService();

        assertThrows(IllegalArgumentException.class, () -> service.registrarIngreso(null, 5));
        assertThrows(IllegalArgumentException.class, () -> service.registrarSalida(null, 5));
    }

    @Test
    void noDebeAceptarCantidadInvalidaEnIngreso() {
        LoteProducto lote = new LoteProducto();
        InventarioService service = new InventarioService();

        assertThrows(IllegalArgumentException.class, () -> service.registrarIngreso(lote, 0));
    }
}
