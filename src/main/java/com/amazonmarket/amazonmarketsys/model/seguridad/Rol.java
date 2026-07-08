package com.amazonmarket.amazonmarketsys.model.seguridad;

import com.amazonmarket.amazonmarketsys.model.base.EntidadAuditable;
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
    "Datos del rol { codigo; nombre; descripcion; rolSistema; activo; } " +
    "Permisos asignados { permisos; } " +
    "Auditoria { fechaCreacion; usuarioCreacion; fechaModificacion; usuarioModificacion; }"
)
@Tab(properties = "codigo, nombre, rolSistema, activo")
public class Rol extends EntidadAuditable {

    @NotBlank(message = "El codigo del rol es obligatorio")
    @Size(max = 40)
    @Column(nullable = false, unique = true, length = 40)
    private String codigo;

    @NotBlank(message = "El nombre del rol es obligatorio")
    @Size(max = 80)
    @Column(nullable = false, length = 80)
    private String nombre;

    @Size(max = 200)
    @Column(length = 200)
    private String descripcion;

    @Column(nullable = false)
    private boolean rolSistema = false;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "seguridad_rol_permiso",
        joinColumns = @JoinColumn(name = "rol_id"),
        inverseJoinColumns = @JoinColumn(name = "permiso_id")
    )
    @ListProperties("codigo, nombre, modulo, accion, nivelRiesgo, activo")
    private Collection<Permiso> permisos = new LinkedHashSet<>();

    public void agregarPermiso(Permiso permiso) {
        if (permiso != null) {
            asegurarPermisos();
            permisos.add(permiso);
        }
    }

    public void quitarPermiso(Permiso permiso) {
        if (permiso != null && permisos != null) {
            permisos.remove(permiso);
        }
    }

    public boolean tienePermiso(String codigoPermiso) {
        if (codigoPermiso == null || codigoPermiso.isBlank()) {
            return false;
        }

        return permisos != null && permisos.stream()
                .filter(Objects::nonNull)
                .anyMatch(permiso -> codigoPermiso.trim().equalsIgnoreCase(permiso.getCodigo()));
    }

    public int getCantidadPermisos() {
        return permisos == null ? 0 : permisos.size();
    }

    public String getDescripcionCorta() {
        return codigo + " - " + nombre;
    }

    private void asegurarPermisos() {
        if (permisos == null) {
            permisos = new LinkedHashSet<>();
        }
    }
}