
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
    "Datos de la unidad de medida { nombre; simbolo; activo; }"
)
@Tab(properties = "nombre, simbolo, activo")
public class UnidadMedida {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Hidden
    private Long id;

    @NotBlank(message = "El nombre de la unidad de medida es obligatorio")
    @Size(max = 40)
    @Column(nullable = false, unique = true, length = 40)
    private String nombre;

    @NotBlank(message = "El simbolo es obligatorio")
    @Size(max = 10)
    @Column(nullable = false, length = 10)
    private String simbolo;

    private boolean activo = true;
}
