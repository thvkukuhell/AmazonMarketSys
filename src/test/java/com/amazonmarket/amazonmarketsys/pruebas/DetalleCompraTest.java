package com.amazonmarket.amazonmarketsys.pruebas;

import java.math.*;
import static org.junit.Assert.*;
import org.junit.*;
import com.amazonmarket.amazonmarketsys.model.compras.*;

public class DetalleCompraTest {
    
    @Test
    public void testCalcularSubtotalDetalleCompra() {
        DetalleCompra detalle = new DetalleCompra();
        detalle.setNombreProducto("Arroz extra");
        detalle.setCantidad(3);
        detalle.setPrecioUnitario(new BigDecimal("8.50"));
        
        detalle.calcularSubtotal();
        
        assertEquals(3, detalle.getCantidad());
        assertEquals(new BigDecimal("8.50"), detalle.getPrecioUnitario());
        assertEquals(new BigDecimal("25.50"), detalle.getSubtotal());
    }

    @Test
    public void testGenerarCodigoAutomaticoDetalleCompra() {
        DetalleCompra detalle = new DetalleCompra();

        detalle.generarCodigoAutomatico("COMP-ABC12345", 1);

        assertEquals("COMP-ABC12345-DET-001", detalle.getCodigoProducto());
    }
}
