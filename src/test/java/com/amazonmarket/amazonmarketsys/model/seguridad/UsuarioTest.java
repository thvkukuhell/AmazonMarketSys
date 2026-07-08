package com.amazonmarket.amazonmarketsys.model.seguridad;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class UsuarioTest {

    @Test
    void usuarioNuevoDebePoderAcceder() {
        Usuario usuario = new Usuario();

        assertEquals(Usuario.EstadoUsuario.ACTIVO, usuario.getEstado());
        assertTrue(usuario.puedeAcceder());
    }

    @Test
    void debeObtenerNombreCompleto() {
        Usuario usuario = new Usuario();
        usuario.setNombres("Carlos");
        usuario.setApellidos("Tirabanti");

        assertEquals("Carlos Tirabanti", usuario.getNombreCompleto());
    }

    @Test
    void debeValidarRolesYPermisosDelUsuario() {
        Permiso permiso = new Permiso();
        permiso.setCodigo("SEGURIDAD_USUARIO_CREAR");

        Rol rol = new Rol();
        rol.setCodigo("ADMIN");
        rol.setNombre("Administrador");
        rol.agregarPermiso(permiso);

        Usuario usuario = new Usuario();
        usuario.agregarRol(rol);

        assertTrue(usuario.tieneRol("admin"));
        assertTrue(usuario.tienePermiso("seguridad_usuario_crear"));
        assertFalse(usuario.tienePermiso("COMPRA_ANULAR"));
    }

    @Test
    void debeBloquearYDesactivarAcceso() {
        Usuario usuario = new Usuario();

        usuario.bloquear();
        assertFalse(usuario.puedeAcceder());
        assertEquals(Usuario.EstadoUsuario.BLOQUEADO, usuario.getEstado());

        usuario.desactivar();
        assertFalse(usuario.puedeAcceder());
        assertEquals(Usuario.EstadoUsuario.INACTIVO, usuario.getEstado());

        usuario.desbloquear();
        assertTrue(usuario.puedeAcceder());
        assertEquals(Usuario.EstadoUsuario.ACTIVO, usuario.getEstado());
    }
}