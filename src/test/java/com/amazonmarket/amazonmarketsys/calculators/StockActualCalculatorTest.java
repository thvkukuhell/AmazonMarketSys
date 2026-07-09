
package com.amazonmarket.amazonmarketsys.calculators;

import static org.junit.jupiter.api.Assertions.*;
import com.amazonmarket.amazonmarketsys.model.inventario.LoteProducto;
import com.amazonmarket.amazonmarketsys.model.inventario.MovimientoInventario;
import com.amazonmarket.amazonmarketsys.model.inventario.TipoMovimiento;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

public class StockActualCalculatorTest {
    @Test
    void debeSumarStockDeLotesActivos() {
        LoteProducto l1 = new LoteProducto();
        l1.setCantidadActual(10);
        l1.setActivo(true);

        LoteProducto l2 = new LoteProducto();
        l2.setCantidadActual(5);
        l2.setActivo(true);

        LoteProducto inactivo = new LoteProducto();
        inactivo.setCantidadActual(100);
        inactivo.setActivo(false);

        List<LoteProducto> lotes = new ArrayList<>();
        lotes.add(l1);
        lotes.add(l2);
        lotes.add(inactivo);

        assertEquals(15, StockActualCalculator.calcularStockPorLotes(lotes));
    }

    @Test
    void debeRetornarCeroSiNoHayLotes() {
        assertEquals(0, StockActualCalculator.calcularStockPorLotes(null));
        assertEquals(0, StockActualCalculator.calcularStockPorLotes(new ArrayList<>()));
    }

    @Test
    void debeCalcularStockAplicandoMovimientos() {
        MovimientoInventario entrada = new MovimientoInventario();
        entrada.setTipoMovimiento(TipoMovimiento.ENTRADA_COMPRA);
        entrada.setCantidad(20);

        MovimientoInventario salida = new MovimientoInventario();
        salida.setTipoMovimiento(TipoMovimiento.SALIDA_VENTA);
        salida.setCantidad(8);

        List<MovimientoInventario> movimientos = new ArrayList<>();
        movimientos.add(entrada);
        movimientos.add(salida);

        assertEquals(12, StockActualCalculator.calcularStockPorMovimientos(0, movimientos));
    }

    @Test
    void debeIndicarSiEstaBajoStockMinimo() {
        assertTrue(StockActualCalculator.estaBajoStockMinimo(2, 5));
        assertFalse(StockActualCalculator.estaBajoStockMinimo(10, 5));
    }
}
