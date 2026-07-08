
package com.amazonmarket.amazonmarketsys.service;

import com.amazonmarket.amazonmarketsys.model.caja.Caja;
import java.math.BigDecimal;

public class CajaService {
    public void abrirCaja(Caja caja, BigDecimal montoInicial) {
        if (caja == null) {
            throw new IllegalArgumentException("La caja no puede ser nula");
        }

        if (montoInicial == null || montoInicial.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("El monto inicial no puede ser negativo");
        }

        caja.setEstado(Caja.EstadoCaja.ABIERTA);
        caja.setSaldoInicial(montoInicial);
        caja.setSaldoActual(montoInicial);
    }

    public void cerrarCaja(Caja caja) {
        if (caja == null) {
            throw new IllegalArgumentException("La caja no puede ser nula");
        }

        caja.setEstado(Caja.EstadoCaja.CERRADA);
    }

    public void registrarIngreso(Caja caja, BigDecimal monto) {
        validarMovimiento(caja, monto);
        caja.setSaldoActual(caja.getSaldoActual().add(monto));
    }

    public void registrarEgreso(Caja caja, BigDecimal monto) {
        validarMovimiento(caja, monto);

        if (caja.getSaldoActual().compareTo(monto) < 0) {
            throw new IllegalArgumentException("No hay saldo suficiente en caja");
        }

        caja.setSaldoActual(caja.getSaldoActual().subtract(monto));
    }

    private void validarMovimiento(Caja caja, BigDecimal monto) {
        if (caja == null) {
            throw new IllegalArgumentException("La caja no puede ser nula");
        }

        if (!caja.estaAbierta()) {
            throw new IllegalStateException("La caja debe estar abierta");
        }

        if (monto == null || monto.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor a cero");
        }
    }
}
