
package com.amazonmarket.amazonmarketsys.model.catalogo;

import javax.persistence.*;
import javax.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.openxava.annotations.*;

@Entity
@Getter
@Setter
@View(members =
    "Datos de la marca { nombre; descripcion; activo; }"
)
@Tab(properties = "nombre, descripcion, activo")
public class Marca {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Hidden
    private Long id;

    @NotBlank(message = "El nombre de la marca es obligatorio")
    @Size(max = 60)
    @Column(nullable = false, unique = true, length = 60)
    private String nombre;

    @Size(max = 200)
    @Column(length = 200)
    private String descripcion;

    private boolean activo = true;
}
