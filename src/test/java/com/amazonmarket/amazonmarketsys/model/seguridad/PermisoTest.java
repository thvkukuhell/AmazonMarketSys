package com.amazonmarket.amazonmarketsys.model.seguridad;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class PermisoTest {

    @Test
    void debeIdentificarModuloDelPermiso() {
        Permiso permiso = new Permiso();
        permiso.setCodigo("VENTA_CREAR");
        permiso.setNombre("Crear venta");
        permiso.setModulo("Ventas");
        permiso.setAccion("Crear");

        assertTrue(permiso.perteneceAlModulo("ventas"));
        assertFalse(permiso.perteneceAlModulo("inventario"));
    }

    @Test
    void permisoAltoDebeSerCritico() {
        Permiso permiso = new Permiso();
        permiso.setNivelRiesgo(Permiso.NivelRiesgo.ALTO);

        assertTrue(permiso.esCritico());
    }
}