package com.amazonmarket.amazonmarketsys.model.base;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class EntidadBaseTest {

    static class EntidadPrueba extends EntidadBase {
    }

    @Test
    void entidadNuevaDebeEstarActiva() {
        EntidadPrueba entidad = new EntidadPrueba();

        assertTrue(entidad.estaActivo());
    }

    @Test
    void debeActivarYDesactivarEntidad() {
        EntidadPrueba entidad = new EntidadPrueba();

        entidad.desactivar();
        assertFalse(entidad.estaActivo());

        entidad.activar();
        assertTrue(entidad.estaActivo());
    }
}