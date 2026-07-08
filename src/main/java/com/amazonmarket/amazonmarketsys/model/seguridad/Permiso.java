package com.amazonmarket.amazonmarketsys.model.seguridad;

import com.amazonmarket.amazonmarketsys.model.base.EntidadAuditable;
import javax.persistence.*;
import javax.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.openxava.annotations.*;

@Entity
@Getter
@Setter
@View(members =
    "Datos del permiso { codigo; nombre; modulo; accion; nivelRiesgo; activo; } " +
    "Descripcion { descripcion; } " +
    "Auditoria { fechaCreacion; usuarioCreacion; fechaModificacion; usuarioModificacion; }"
)
@Tab(properties = "codigo, nombre, modulo, accion, nivelRiesgo, activo")
public class Permiso extends EntidadAuditable {

    public enum NivelRiesgo {
        BAJO,
        MEDIO,
        ALTO
    }

    @NotBlank(message = "El codigo del permiso es obligatorio")
    @Size(max = 40)
    @Column(nullable = false, unique = true, length = 40)
    private String codigo;

    @NotBlank(message = "El nombre del permiso es obligatorio")
    @Size(max = 80)
    @Column(nullable = false, length = 80)
    private String nombre;

    @NotBlank(message = "El modulo es obligatorio")
    @Size(max = 60)
    @Column(nullable = false, length = 60)
    private String modulo;

    @NotBlank(message = "La accion es obligatoria")
    @Size(max = 40)
    @Column(nullable = false, length = 40)
    private String accion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private NivelRiesgo nivelRiesgo = NivelRiesgo.BAJO;

    @Size(max = 200)
    @Column(length = 200)
    private String descripcion;

    public boolean perteneceAlModulo(String moduloBuscado) {
        return moduloBuscado != null
                && modulo != null
                && modulo.equalsIgnoreCase(moduloBuscado.trim());
    }

    public boolean esCritico() {
        return NivelRiesgo.ALTO.equals(nivelRiesgo);
    }

    public String getDescripcionCorta() {
        return codigo + " - " + nombre;
    }
}