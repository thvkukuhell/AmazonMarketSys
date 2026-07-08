
package com.amazonmarket.amazonmarketsys.model.personas;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ClienteTest {
    @Test
    void debeRetornarNombreCompleto() {
        Cliente cliente = new Cliente();
        cliente.setNombres("Carlos");
        cliente.setApellidos("Tirabanti");

        assertEquals("Carlos Tirabanti", cliente.getNombreCompleto());
    }

    @Test
    void clienteNuevoDebeEstarActivoPorDefecto() {
        Cliente cliente = new Cliente();

        assertTrue(cliente.isActivo());
        assertFalse(cliente.isFrecuente());
        assertEquals(0, cliente.getPuntos());
        assertNotNull(cliente.getFechaRegistro());
    }

    @Test
    void debePermitirMarcarClienteFrecuente() {
        Cliente cliente = new Cliente();
        cliente.setFrecuente(true);
        cliente.setPuntos(50);

        assertTrue(cliente.isFrecuente());
        assertEquals(50, cliente.getPuntos());
    }
}
