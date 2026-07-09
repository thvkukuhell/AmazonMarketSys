package com.amazonmarket.amazonmarketsys.pruebas;

import org.openxava.tests.*;

public class ProveedorTest extends ModuleTestBase {
    
    public ProveedorTest(String testName) {
        super(testName, "amazonmarketsys", "Proveedor");
    }
    
    public void testCrearProveedorBasico() throws Exception {
        login("admin", "admin");
        execute("CRUD.new");
        setValue("ruc", "20123456789");
        setValue("razonSocial", "Distribuidora Lima SAC");
        setValue("nombreComercial", "Distribuidora Lima");
        setValue("contacto", "Ana Torres");
        setValue("telefono", "987654321");
        setValue("correo", "compras@distribuidoralima.pe");
        setValue("direccion", "Av. Principal 123");
        setValue("estado", "ACTIVO");
        assertValue("ruc", "20123456789");
        assertValue("razonSocial", "Distribuidora Lima SAC");
        assertValue("estado", "ACTIVO");
        execute("CRUD.save");
        assertNoErrors();
    }

}
