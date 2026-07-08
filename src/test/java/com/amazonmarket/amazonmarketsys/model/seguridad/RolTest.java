package com.amazonmarket.amazonmarketsys.model.seguridad;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class RolTest {

    @Test
    void debeAgregarYValidarPermiso() {
        Permiso permiso = new Permiso();
        permiso.setCodigo("INVENTARIO_VER");
        permiso.setNombre("Ver inventario");
        permiso.setModulo("Inventario");
        permiso.setAccion("Ver");

        Rol rol = new Rol();
        rol.setCodigo("ALMACENERO");
        rol.setNombre("Almacenero");
        rol.agregarPermiso(permiso);

        assertEquals(1, rol.getCantidadPermisos());
        assertTrue(rol.tienePermiso("inventario_ver"));
        assertFalse(rol.tienePermiso("VENTA_ANULAR"));
    }

    @Test
    void debeQuitarPermiso() {
        Permiso permiso = new Permiso();
        permiso.setCodigo("CAJA_CERRAR");

        Rol rol = new Rol();
        rol.agregarPermiso(permiso);
        rol.quitarPermiso(permiso);

        assertEquals(0, rol.getCantidadPermisos());
        assertFalse(rol.tienePermiso("CAJA_CERRAR"));
    }
}