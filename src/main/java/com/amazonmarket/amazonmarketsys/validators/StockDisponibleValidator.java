
package com.amazonmarket.amazonmarketsys.validators;

public class StockDisponibleValidator {
    private StockDisponibleValidator() {
    }

    public static boolean esCantidadValida(int cantidad) {
        return cantidad > 0;
    }

    public static boolean haySuficienteStock(int stockActual, int cantidadSolicitada) {
        return esCantidadValida(cantidadSolicitada) && stockActual >= cantidadSolicitada;
    }
}
