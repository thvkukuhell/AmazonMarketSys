
package com.amazonmarket.amazonmarketsys.model.catalogo;

import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

public class ProductoTest {
    @Test
    void productoNuevoDebeEstarActivoYNoPerecible() {
        Producto producto = new Producto();

        assertTrue(producto.isActivo());
        assertFalse(producto.isPerecible());
        assertEquals(0, producto.getStockMinimo());
    }

    @Test
    void debeAsignarDatosBasicos() {
        Producto producto = new Producto();
        producto.setCodigo("P001");
        producto.setNombre("Leche Entera 1L");

        Categoria categoria = new Categoria();
        categoria.setNombre("Lacteos");
        producto.setCategoria(categoria);

        Marca marca = new Marca();
        marca.setNombre("Gloria");
        producto.setMarca(marca);

        UnidadMedida unidad = new UnidadMedida();
        unidad.setNombre("Unidad");
        unidad.setSimbolo("und");
        producto.setUnidadMedida(unidad);

        assertEquals("P001", producto.getCodigo());
        assertEquals("Leche Entera 1L", producto.getNombre());
        assertEquals("Lacteos", producto.getCategoria().getNombre());
        assertEquals("Gloria", producto.getMarca().getNombre());
        assertEquals("und", producto.getUnidadMedida().getSimbolo());
    }

    @Test
    void debeTenerMargenValidoSiPrecioVentaMayorOIgualAPrecioCompra() {
        Producto producto = new Producto();
        producto.setPrecioCompra(new BigDecimal("5.00"));
        producto.setPrecioVenta(new BigDecimal("8.00"));

        assertTrue(producto.tieneMargenValido());
    }

    @Test
    void noDebeTenerMargenValidoSiPrecioVentaMenorAPrecioCompra() {
        Producto producto = new Producto();
        producto.setPrecioCompra(new BigDecimal("8.00"));
        producto.setPrecioVenta(new BigDecimal("5.00"));

        assertFalse(producto.tieneMargenValido());
    }
}
