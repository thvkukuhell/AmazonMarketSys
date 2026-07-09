package com.amazonmarket.amazonmarketsys.pruebas;

import org.openxava.tests.*;

public class CompraTest extends ModuleTestBase {
    
    public CompraTest(String testName) {
        super(testName, "amazonmarketsys", "Compra");
    }
    
    public void testModuloCompraAbre() throws Exception {
        login("admin", "admin");
        assertNoErrors();
    }
    
    public void testValoresInicialesCompra() throws Exception {
        login("admin", "admin");
        execute("CRUD.new");
        assertValue("tipoPago", "CONTADO");
        assertValue("estadoCompra", "REGISTRADA");
    }
    
    public void testRegistrarCompraSinProveedorMuestraError() throws Exception {
        login("admin", "admin");
        execute("CRUD.new");
        setValue("tipoDocumento", "FACTURA");
        setValue("numeroDocumento", "F001-000001");
        setValue("tipoPago", "CONTADO");
        execute("Compra.registrarCompra");
        assertError("Debe seleccionar un proveedor.");
    }
}
