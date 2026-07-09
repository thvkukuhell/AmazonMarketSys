
package com.amazonmarket.amazonmarketsys.service;

import com.amazonmarket.amazonmarketsys.model.inventario.LoteProducto;
import com.amazonmarket.amazonmarketsys.validators.StockDisponibleValidator;

public class InventarioService {

    public void registrarIngreso(LoteProducto lote, int cantidad) {
        validarLote(lote);

        if (!StockDisponibleValidator.esCantidadValida(cantidad)) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a cero");
        }

        lote.incrementar(cantidad);
    }

    public void registrarSalida(LoteProducto lote, int cantidad) {
        validarLote(lote);

        if (!StockDisponibleValidator.haySuficienteStock(lote.getCantidadActual(), cantidad)) {
            throw new IllegalStateException("No hay stock suficiente en el lote");
        }

        lote.descontar(cantidad);
    }

    public void registrarMerma(LoteProducto lote, int cantidad) {
        registrarSalida(lote, cantidad);
    }

    private void validarLote(LoteProducto lote) {
        if (lote == null) {
            throw new IllegalArgumentException("El lote no puede ser nulo");
        }
    }
}
