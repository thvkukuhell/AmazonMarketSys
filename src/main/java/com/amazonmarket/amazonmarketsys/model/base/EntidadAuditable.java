package com.amazonmarket.amazonmarketsys.model.base;

import java.time.LocalDateTime;
import javax.persistence.*;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.openxava.annotations.ReadOnly;

@MappedSuperclass
@Getter
@Setter
public abstract class EntidadAuditable extends EntidadBase {

    @ReadOnly
    @Column(nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @ReadOnly
    @Column(nullable = false)
    private LocalDateTime fechaModificacion;

    @Size(max = 60)
    @Column(length = 60, updatable = false)
    private String usuarioCreacion;

    @Size(max = 60)
    @Column(length = 60)
    private String usuarioModificacion;

    @PrePersist
    public void antesDeCrear() {
        LocalDateTime ahora = LocalDateTime.now();

        if (fechaCreacion == null) {
            fechaCreacion = ahora;
        }

        fechaModificacion = ahora;

        if (usuarioCreacion == null || usuarioCreacion.isBlank()) {
            usuarioCreacion = "sistema";
        }

        if (usuarioModificacion == null || usuarioModificacion.isBlank()) {
            usuarioModificacion = usuarioCreacion;
        }
    }

    @PreUpdate
    public void antesDeActualizar() {
        fechaModificacion = LocalDateTime.now();

        if (usuarioModificacion == null || usuarioModificacion.isBlank()) {
            usuarioModificacion = "sistema";
        }
    }

    public void registrarCreacion(String usuario) {
        if (usuario != null && !usuario.isBlank()) {
            usuarioCreacion = usuario.trim();
            usuarioModificacion = usuario.trim();
        }
    }

    public void registrarModificacion(String usuario) {
        if (usuario != null && !usuario.isBlank()) {
            usuarioModificacion = usuario.trim();
        }
    }

    public String resumenAuditoria() {
        String creador = usuarioCreacion == null ? "sistema" : usuarioCreacion;
        String modificador = usuarioModificacion == null ? creador : usuarioModificacion;

        return "Creado por " + creador + " | Modificado por " + modificador;
    }
}