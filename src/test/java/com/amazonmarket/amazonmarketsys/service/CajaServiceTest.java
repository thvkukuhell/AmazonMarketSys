
package com.amazonmarket.amazonmarketsys.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.amazonmarket.amazonmarketsys.model.caja.Caja;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

public class CajaServiceTest {
    @Test
    void debeAbrirCajaCorrectamente() {
        Caja caja = new Caja();
        CajaService service = new CajaService();

        service.abrirCaja(caja, new BigDecimal("100.00"));

        assertEquals(Caja.EstadoCaja.ABIERTA, caja.getEstado());
        assertEquals(0, caja.getSaldoInicial().compareTo(new BigDecimal("100.00")));
        assertEquals(0, caja.getSaldoActual().compareTo(new BigDecimal("100.00")));
    }

    @Test
    void debeCerrarCajaCorrectamente() {
        Caja caja = new Caja();
        caja.setEstado(Caja.EstadoCaja.ABIERTA);

        CajaService service = new CajaService();
        service.cerrarCaja(caja);

        assertEquals(Caja.EstadoCaja.CERRADA, caja.getEstado());
    }

    @Test
    void debeRegistrarIngresoEnCajaAbierta() {
        Caja caja = new Caja();
        caja.setEstado(Caja.EstadoCaja.ABIERTA);
        caja.setSaldoActual(new BigDecimal("100.00"));

        CajaService service = new CajaService();
        service.registrarIngreso(caja, new BigDecimal("50.00"));

        assertEquals(0, caja.getSaldoActual().compareTo(new BigDecimal("150.00")));
    }

    @Test
    void debeRegistrarEgresoEnCajaAbierta() {
        Caja caja = new Caja();
        caja.setEstado(Caja.EstadoCaja.ABIERTA);
        caja.setSaldoActual(new BigDecimal("100.00"));

        CajaService service = new CajaService();
        service.registrarEgreso(caja, new BigDecimal("30.00"));

        assertEquals(0, caja.getSaldoActual().compareTo(new BigDecimal("70.00")));
    }

    @Test
    void noDebeRegistrarMovimientoSiCajaEstaCerrada() {
        Caja caja = new Caja();
        caja.setEstado(Caja.EstadoCaja.CERRADA);
        caja.setSaldoActual(new BigDecimal("100.00"));

        CajaService service = new CajaService();

        assertThrows(IllegalStateException.class, () -> {
            service.registrarIngreso(caja, new BigDecimal("50.00"));
        });
    }

    @Test
    void debeUsarMockitoParaVerificarAperturaDeCaja() {
        Caja cajaMock = mock(Caja.class);
        CajaService service = new CajaService();

        service.abrirCaja(cajaMock, new BigDecimal("80.00"));

        verify(cajaMock).setEstado(Caja.EstadoCaja.ABIERTA);
        verify(cajaMock).setSaldoInicial(new BigDecimal("80.00"));
        verify(cajaMock).setSaldoActual(new BigDecimal("80.00"));
    }
}
