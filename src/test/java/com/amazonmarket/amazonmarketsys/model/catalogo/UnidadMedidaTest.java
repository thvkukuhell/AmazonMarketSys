
package com.amazonmarket.amazonmarketsys.model.catalogo;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class UnidadMedidaTest {
    @Test
    void unidadMedidaNuevaDebeEstarActiva() {
        UnidadMedida unidad = new UnidadMedida();

        assertTrue(unidad.isActivo());
    }

    @Test
    void debeAsignarNombreYSimbolo() {
        UnidadMedida unidad = new UnidadMedida();
        unidad.setNombre("Kilogramo");
        unidad.setSimbolo("kg");

        assertEquals("Kilogramo", unidad.getNombre());
        assertEquals("kg", unidad.getSimbolo());
    }
}
