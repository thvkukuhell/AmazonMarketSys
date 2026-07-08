package com.amazonmarket.amazonmarketsys.model.base;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class EntidadAuditableTest {

    static class EntidadAuditablePrueba extends EntidadAuditable {
    }

    @Test
    void debeRegistrarAuditoriaAntesDeCrear() {
        EntidadAuditablePrueba entidad = new EntidadAuditablePrueba();
        entidad.registrarCreacion("admin");

        entidad.antesDeCrear();

        assertNotNull(entidad.getFechaCreacion());
        assertNotNull(entidad.getFechaModificacion());
        assertEquals("admin", entidad.getUsuarioCreacion());
        assertEquals("admin", entidad.getUsuarioModificacion());
    }

    @Test
    void debeRegistrarModificacion() {
        EntidadAuditablePrueba entidad = new EntidadAuditablePrueba();
        entidad.antesDeCrear();

        entidad.registrarModificacion("supervisor");
        entidad.antesDeActualizar();

        assertEquals("supervisor", entidad.getUsuarioModificacion());
        assertTrue(entidad.resumenAuditoria().contains("supervisor"));
    }
}