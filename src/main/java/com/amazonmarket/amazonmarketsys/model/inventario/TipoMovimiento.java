
package com.amazonmarket.amazonmarketsys.model.inventario;

public enum TipoMovimiento {
    ENTRADA_COMPRA,
    SALIDA_VENTA,
    AJUSTE_POSITIVO,
    AJUSTE_NEGATIVO,
    MERMA,
    DEVOLUCION;

    public boolean esIncremento() {
        return this == ENTRADA_COMPRA || this == AJUSTE_POSITIVO || this == DEVOLUCION;
    }
}
