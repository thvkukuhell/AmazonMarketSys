
package com.amazonmarket.amazonmarketsys.model.catalogo;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class MarcaTest {
    @Test
    void marcaNuevaDebeEstarActiva() {
        Marca marca = new Marca();

        assertTrue(marca.isActivo());
    }

    @Test
    void debeAsignarNombreYDescripcion() {
        Marca marca = new Marca();
        marca.setNombre("Gloria");
        marca.setDescripcion("Marca peruana de lacteos");

        assertEquals("Gloria", marca.getNombre());
        assertEquals("Marca peruana de lacteos", marca.getDescripcion());
    }
}
