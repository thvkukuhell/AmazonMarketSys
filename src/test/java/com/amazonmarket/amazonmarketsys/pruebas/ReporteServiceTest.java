package com.amazonmarket.amazonmarketsys.pruebas;

import java.math.*;
import java.util.*;
import static org.junit.Assert.*;
import org.junit.*;
import com.amazonmarket.amazonmarketsys.model.compras.*;
import com.amazonmarket.amazonmarketsys.model.personas.*;
import com.amazonmarket.amazonmarketsys.service.*;

public class ReporteServiceTest {
    
    @Test
    public void testTotalComprado() {
        ReporteService service = new ReporteService();
        List<Compra> compras = comprasDeEjemplo();
        
        assertEquals(new BigDecimal("150.00"), service.totalComprado(compras));
    }
    
    @Test
    public void testContarComprasRegistradas() {
        ReporteService service = new ReporteService();
        List<Compra> compras = comprasDeEjemplo();
        
        assertEquals(2, service.contarComprasRegistradas(compras));
    }
    
    @Test
    public void testContarComprasAnuladas() {
        ReporteService service = new ReporteService();
        List<Compra> compras = comprasDeEjemplo();
        
        assertEquals(1, service.contarComprasAnuladas(compras));
    }
    
    @Test
    public void testContarProveedoresActivos() {
        ReporteService service = new ReporteService();
        List<Proveedor> proveedores = proveedoresDeEjemplo();
        
        assertEquals(2, service.contarProveedoresActivos(proveedores));
    }
    
    private List<Compra> comprasDeEjemplo() {
        List<Compra> compras = new ArrayList<Compra>();
        compras.add(nuevaCompra("REGISTRADA", "100.00"));
        compras.add(nuevaCompra("ANULADA", "50.00"));
        compras.add(nuevaCompra("REGISTRADA", "50.00"));
        return compras;
    }
    
    private Compra nuevaCompra(String estado, String total) {
        Compra compra = new Compra();
        compra.setEstadoCompra(estado);
        compra.setTotal(new BigDecimal(total));
        return compra;
    }
    
    private List<Proveedor> proveedoresDeEjemplo() {
        List<Proveedor> proveedores = new ArrayList<Proveedor>();
        proveedores.add(nuevoProveedor("ACTIVO"));
        proveedores.add(nuevoProveedor("INACTIVO"));
        proveedores.add(nuevoProveedor("ACTIVO"));
        return proveedores;
    }
    
    private Proveedor nuevoProveedor(String estado) {
        Proveedor proveedor = new Proveedor();
        proveedor.setEstado(estado);
        return proveedor;
    }
}
