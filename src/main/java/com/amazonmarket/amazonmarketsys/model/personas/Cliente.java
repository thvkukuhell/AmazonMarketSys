
package com.amazonmarket.amazonmarketsys.model.personas;

import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.openxava.annotations.*;

@Entity
@Getter
@Setter
@View(members = "Datos personales { dni; nombres; apellidos; telefono; email; direccion; }" +
                "Fidelizacion { frecuente; puntos; fechaRegistro; activo; }"
)
@Tab(properties = "dni, nombres, apellidos, telefono, frecuente, puntos, activo")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Hidden
    private Long id;
    
    @NotBlank(message = "El DNI es obligatorio")
    @Pattern(regexp = "\\d{8}", message = "El DNI debe tener 8 dígitos")
    @Column(nullable = false, unique = true, length = 8)
    private String dni;
    
    @NotBlank(message = "Los nombres son obligatorios")
    @Size(max = 80)
    @Column(nullable = false, length = 80)
    private String nombres;
    
    @NotBlank(message = "Los apellidos son obligatorios")
    @Size(max = 80)
    @Column(nullable = false, length = 80)
    private String apellidos;

    @Pattern(regexp = "\\d{9}|", message = "El teléfono debe tener 9 dígitos")
    @Column(length = 9)
    private String telefono;

    @Email(message = "El correo no tiene un formato válido")
    @Stereotype("EMAIL")
    @Column(length = 120)
    private String email;

    @Size(max = 150)
    @Column(length = 150)
    private String direccion;

    private boolean frecuente = false;

    @Min(value = 0, message = "Los puntos no pueden ser negativos")
    private int puntos = 0;

    @ReadOnly
    private LocalDate fechaRegistro = LocalDate.now();

    private boolean activo = true;

    public String getNombreCompleto() {
        return nombres + " " + apellidos;
    }
}
