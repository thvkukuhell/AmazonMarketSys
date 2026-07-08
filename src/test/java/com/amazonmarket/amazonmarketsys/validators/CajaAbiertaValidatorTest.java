package com.amazonmarket.amazonmarketsys.validators;

import static org.junit.jupiter.api.Assertions.*;
import com.amazonmarket.amazonmarketsys.model.caja.Caja;
import org.junit.jupiter.api.Test;

public class CajaAbiertaValidatorTest {

    @Test
    void debeRetornarTrueSiCajaEstaAbierta() {
        Caja caja = new Caja();
        caja.setEstado(Caja.EstadoCaja.ABIERTA);

        assertTrue(CajaAbiertaValidator.cajaEsAbierta(caja));
    }

    @Test
    void debeRetornarFalseSiCajaEstaCerrada() {
        Caja caja = new Caja();
        caja.setEstado(Caja.EstadoCaja.CERRADA);

        assertFalse(CajaAbiertaValidator.cajaEsAbierta(caja));
    }

    @Test
    void debeRetornarFalseSiCajaEsNula() {
        assertFalse(CajaAbiertaValidator.cajaEsAbierta(null));
    }
}