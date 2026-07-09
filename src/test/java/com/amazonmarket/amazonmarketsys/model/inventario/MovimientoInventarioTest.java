
package com.amazonmarket.amazonmarketsys.model.inventario;

import static org.junit.jupiter.api.Assertions.*;
import com.amazonmarket.amazonmarketsys.model.catalogo.Producto;
import org.junit.jupiter.api.Test;

public class MovimientoInventarioTest {
    @Test
    void movimientoNuevoDebeSerEntradaDeCompraPorDefecto() {
        MovimientoInventario movimiento = new MovimientoInventario();

        assertEquals(TipoMovimiento.ENTRADA_COMPRA, movimiento.getTipoMovimiento());
        assertTrue(movimiento.esIncremento());
    }

    @Test
    void debeIndicarQueUnaSalidaDeVentaNoEsIncremento() {
        MovimientoInventario movimiento = new MovimientoInventario();
        movimiento.setTipoMovimiento(TipoMovimiento.SALIDA_VENTA);

        assertFalse(movimiento.esIncremento());
    }

    @Test
    void debeAsociarProductoYCantidad() {
        Producto producto = new Producto();
        producto.setNombre("Arroz");

        MovimientoInventario movimiento = new MovimientoInventario();
        movimiento.setProducto(producto);
        movimiento.setCantidad(20);
        movimiento.setDocumentoReferencia("COMPRA-0001");

        assertEquals("Arroz", movimiento.getProducto().getNombre());
        assertEquals(20, movimiento.getCantidad());
        assertEquals("COMPRA-0001", movimiento.getDocumentoReferencia());
    }
}
