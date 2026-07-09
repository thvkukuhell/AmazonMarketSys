
package com.amazonmarket.amazonmarketsys.calculators;

import com.amazonmarket.amazonmarketsys.model.inventario.LoteProducto;
import com.amazonmarket.amazonmarketsys.model.inventario.MovimientoInventario;
import java.util.Collection;
import java.util.Objects;

public class StockActualCalculator {
    private StockActualCalculator() {
    }

    public static int calcularStockPorLotes(Collection<LoteProducto> lotes) {
        if (lotes == null || lotes.isEmpty()) {
            return 0;
        }

        return lotes.stream()
                .filter(Objects::nonNull)
                .filter(LoteProducto::isActivo)
                .mapToInt(LoteProducto::getCantidadActual)
                .sum();
    }

    public static int calcularStockPorMovimientos(int stockInicial, Collection<MovimientoInventario> movimientos) {
        if (movimientos == null || movimientos.isEmpty()) {
            return stockInicial;
        }

        int stock = stockInicial;

        for (MovimientoInventario movimiento : movimientos) {
            if (movimiento == null) {
                continue;
            }

            stock += movimiento.esIncremento() ? movimiento.getCantidad() : -movimiento.getCantidad();
        }

        return stock;
    }

    public static boolean estaBajoStockMinimo(int stockActual, int stockMinimo) {
        return stockActual < stockMinimo;
    }
}
