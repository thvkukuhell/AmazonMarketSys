package com.amazonmarket.amazonmarketsys.pruebas;

import java.math.*;
import static org.junit.Assert.*;
import org.junit.*;
import com.amazonmarket.amazonmarketsys.model.catalogo.*;
import com.amazonmarket.amazonmarketsys.model.compras.*;

public class DetalleCompraTest {
    
    @Test
    public void testCalcularSubtotalDetalleCompra() {
        DetalleCompra detalle = new DetalleCompra();
        Producto producto = new Producto();
        producto.setCodigo("PROD-001");
        producto.setNombre("Arroz extra");
        detalle.setProducto(producto);
        detalle.setCantidad(3);
        detalle.setPrecioUnitario(new BigDecimal("8.50"));
        
        detalle.calcularSubtotal();
        
        assertEquals(3, detalle.getCantidad());
        assertEquals(new BigDecimal("8.50"), detalle.getPrecioUnitario());
        assertEquals(new BigDecimal("25.50"), detalle.getSubtotal());
    }

    @Test
    public void testSincronizarDatosProducto() {
        Producto producto = new Producto();
        producto.setCodigo("PROD-001");
        producto.setNombre("Arroz extra");
        producto.setPrecioCompra(new BigDecimal("7.50"));

        DetalleCompra detalle = new DetalleCompra();
        detalle.setProducto(producto);
        detalle.sincronizarDatosProducto();

        assertEquals("PROD-001", detalle.getCodigoProducto());
        assertEquals("Arroz extra", detalle.getNombreProducto());
        assertEquals(new BigDecimal("7.50"), detalle.getPrecioUnitario());
    }

    @Test
    public void testSincronizarProductoNoSobrescribePrecioIngresado() {
        Producto producto = new Producto();
        producto.setCodigo("PROD-002");
        producto.setNombre("Aceite vegetal");
        producto.setPrecioCompra(new BigDecimal("9.00"));

        DetalleCompra detalle = new DetalleCompra();
        detalle.setProducto(producto);
        detalle.setPrecioUnitario(new BigDecimal("8.75"));
        detalle.sincronizarDatosProducto();

        assertEquals(new BigDecimal("8.75"), detalle.getPrecioUnitario());
    }
}
