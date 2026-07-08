package com.amazonmarket.amazonmarketsys.model.seguridad;

import com.amazonmarket.amazonmarketsys.model.base.EntidadAuditable;
import java.time.LocalDateTime;
import java.util.*;
import javax.persistence.*;
import javax.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.openxava.annotations.*;

@Entity
@Getter
@Setter
@View(members =
    "Datos personales { dni; nombres; apellidos; email; telefono; } " +
    "Credenciales { usuario; clave; estado; ultimoAcceso; activo; } " +
    "Roles asignados { roles; } " +
    "Auditoria { fechaCreacion; usuarioCreacion; fechaModificacion; usuarioModificacion; }"
)
@Tab(properties = "usuario, nombres, apellidos, email, estado, activo")
public class Usuario extends EntidadAuditable {

    public enum EstadoUsuario {
        ACTIVO,
        INACTIVO,
        BLOQUEADO
    }

    @Pattern(regexp = "\\d{8}|", message = "El DNI debe tener 8 digitos")
    @Column(length = 8, unique = true)
    private String dni;

    @NotBlank(message = "Los nombres son obligatorios")
    @Size(max = 80)
    @Column(nullable = false, length = 80)
    private String nombres;

    @NotBlank(message = "Los apellidos son obligatorios")
    @Size(max = 80)
    @Column(nullable = false, length = 80)
    private String apellidos;

    @NotBlank(message = "El usuario es obligatorio")
    @Size(min = 4, max = 40)
    @Column(nullable = false, unique = true, length = 40)
    private String usuario;

    @Stereotype("PASSWORD")
    @NotBlank(message = "La clave es obligatoria")
    @Size(min = 6, max = 120)
    @Column(nullable = false, length = 120)
    private String clave;

    @Email(message = "El correo no tiene un formato valido")
    @Stereotype("EMAIL")
    @Column(length = 120)
    private String email;

    @Pattern(regexp = "\\d{9}|", message = "El telefono debe tener 9 digitos")
    @Column(length = 9)
    private String telefono;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoUsuario estado = EstadoUsuario.ACTIVO;

    @ReadOnly
    private LocalDateTime ultimoAcceso;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "seguridad_usuario_rol",
        joinColumns = @JoinColumn(name = "usuario_id"),
        inverseJoinColumns = @JoinColumn(name = "rol_id")
    )
    @ListProperties("codigo, nombre, rolSistema, activo")
    private Collection<Rol> roles = new LinkedHashSet<>();

    public String getNombreCompleto() {
        String nombre = nombres == null ? "" : nombres.trim();
        String apellido = apellidos == null ? "" : apellidos.trim();

        return (nombre + " " + apellido).trim();
    }

    public void agregarRol(Rol rol) {
        if (rol != null) {
            asegurarRoles();
            roles.add(rol);
        }
    }

    public void quitarRol(Rol rol) {
        if (rol != null && roles != null) {
            roles.remove(rol);
        }
    }

    public boolean tieneRol(String codigoRol) {
        if (codigoRol == null || codigoRol.isBlank()) {
            return false;
        }

        return roles != null && roles.stream()
                .filter(Objects::nonNull)
                .anyMatch(rol -> codigoRol.trim().equalsIgnoreCase(rol.getCodigo()));
    }

    public boolean tienePermiso(String codigoPermiso) {
        if (codigoPermiso == null || codigoPermiso.isBlank()) {
            return false;
        }

        return roles != null && roles.stream()
                .filter(Objects::nonNull)
                .anyMatch(rol -> rol.tienePermiso(codigoPermiso));
    }

    public boolean puedeAcceder() {
        return estaActivo() && EstadoUsuario.ACTIVO.equals(estado);
    }

    public void registrarAcceso() {
        ultimoAcceso = LocalDateTime.now();
    }

    public void bloquear() {
        estado = EstadoUsuario.BLOQUEADO;
    }

    public void desbloquear() {
        estado = EstadoUsuario.ACTIVO;
        activar();
    }

    @Override
    public void activar() {
        super.activar();
        estado = EstadoUsuario.ACTIVO;
    }

    @Override
    public void desactivar() {
        super.desactivar();
        estado = EstadoUsuario.INACTIVO;
    }

    private void asegurarRoles() {
        if (roles == null) {
            roles = new LinkedHashSet<>();
        }
    }
}