
package com.amazonmarket.amazonmarketsys.model.catalogo;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class CategoriaTest {
    @Test
    void categoriaNuevaDebeEstarActiva() {
        Categoria categoria = new Categoria();

        assertTrue(categoria.isActivo());
    }

    @Test
    void debeAsignarNombreYDescripcion() {
        Categoria categoria = new Categoria();
        categoria.setNombre("Lacteos");
        categoria.setDescripcion("Productos lacteos y derivados");

        assertEquals("Lacteos", categoria.getNombre());
        assertEquals("Productos lacteos y derivados", categoria.getDescripcion());
    }
}
