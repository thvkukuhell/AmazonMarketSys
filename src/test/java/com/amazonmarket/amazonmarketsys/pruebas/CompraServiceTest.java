package com.amazonmarket.amazonmarketsys.pruebas;

import java.math.*;
import java.time.*;
import java.util.*;
import static org.junit.Assert.*;
import org.junit.*;
import com.amazonmarket.amazonmarketsys.model.compras.*;
import com.amazonmarket.amazonmarketsys.model.personas.*;
import com.amazonmarket.amazonmarketsys.service.*;

public class CompraServiceTest {
    
    @Test
    public void testValidarCompraCorrecta() {
        Compra compra = nuevaCompraBasica();
        
        CompraService service = new CompraService();
        service.validarCompra(compra);
    }
    
    @Test
    public void testCalcularTotalesCompra() {
        Compra compra = nuevaCompraBasica();
        
        CompraService service = new CompraService();
        service.calcularTotales(compra);
        
        assertEquals(new BigDecimal("20.00"), compra.getSubtotal().setScale(2));
        assertEquals(new BigDecimal("3.60"), compra.getIgv());
        assertEquals(new BigDecimal("23.60"), compra.getTotal());
    }

    @Test
    public void testGenerarCodigosAutomaticosCompraYDetalle() {
        Compra compra = nuevaCompraBasica();
        DetalleCompra detalle = compra.getDetalles().iterator().next();

        assertNull(compra.getCodigoCompra());
        assertNull(detalle.getCodigoProducto());

        CompraService service = new CompraService();
        service.generarCodigosAutomaticos(compra);

        assertNotNull(compra.getCodigoCompra());
        assertTrue(compra.getCodigoCompra().startsWith("COMP-"));
        assertNotNull(detalle.getCodigoProducto());
        assertTrue(detalle.getCodigoProducto().startsWith(compra.getCodigoCompra() + "-DET-"));
    }
    
    @Test
    public void testValidarCompraSinProveedor() {
        Compra compra = nuevaCompraBasica();
        compra.setProveedor(null);
        
        CompraService service = new CompraService();
        
        try {
            service.validarCompra(compra);
            fail("Debe validar que exista proveedor");
        } catch (IllegalArgumentException ex) {
            assertTrue(ex.getMessage().contains("proveedor"));
        }
    }
    
    @Test
    public void testValidarCompraSinDetalles() {
        Compra compra = nuevaCompraBasica();
        compra.getDetalles().clear();
        
        CompraService service = new CompraService();
        
        try {
            service.validarCompra(compra);
            fail("Debe validar que existan detalles");
        } catch (IllegalArgumentException ex) {
            assertTrue(ex.getMessage().contains("detalle"));
        }
    }
    
    @Test
    public void testValidarDetalleConCantidadCero() {
        Compra compra = nuevaCompraBasica();
        compra.getDetalles().iterator().next().setCantidad(0);
        
        CompraService service = new CompraService();
        
        try {
            service.validarCompra(compra);
            fail("Debe validar que la cantidad sea mayor que cero");
        } catch (IllegalArgumentException ex) {
            assertTrue(ex.getMessage().contains("cantidad"));
        }
    }
    
    private Compra nuevaCompraBasica() {
        Proveedor proveedor = new Proveedor();
        proveedor.setRuc("20123456789");
        proveedor.setRazonSocial("Distribuidora Lima SAC");
        proveedor.setEstado("ACTIVO");
        
        DetalleCompra detalle = new DetalleCompra();
        detalle.setNombreProducto("Arroz extra");
        detalle.setCantidad(2);
        detalle.setPrecioUnitario(new BigDecimal("10.00"));
        
        Compra compra = new Compra();
        compra.setProveedor(proveedor);
        compra.setFechaCompra(LocalDate.now());
        compra.setNumeroDocumento("F001-000001");
        compra.setDetalles(new ArrayList<DetalleCompra>());
        compra.getDetalles().add(detalle);
        
        return compra;
    }
}
