package com.amazonmarket.amazonmarketsys.model.personas;

import javax.persistence.*;
import org.openxava.annotations.*;
import lombok.*;

@Entity
@Getter
@Setter
@View(members =
    "Datos generales { codigoProveedor; ruc; razonSocial; nombreComercial; estado; } " +
    "Contacto { contacto; telefono; correo; direccion; } " +
    "Observacion { observacion; }"
)
@Tab(properties = "ruc, razonSocial, nombreComercial, telefono, estado")
public class Proveedor {

    @Id
    @Hidden
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(length = 11)
    @Required
    String ruc;

    @Column(length = 100)
    @Required
    String razonSocial;

    @Column(length = 100)
    String nombreComercial;

    @Column(length = 80)
    String contacto;

    @Column(length = 20)
    String telefono;

    @Column(length = 80)
    String correo;

    @Column(length = 150)
    String direccion;

    @Column(length = 20)
    @Required
    String estado = "ACTIVO";

    @Column(length = 255)
    String observacion;

    @ReadOnly
    public String getCodigoProveedor() {
        return id == 0 ? null : String.format("PROV-%06d", id);
    }
}
