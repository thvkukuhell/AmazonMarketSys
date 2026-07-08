
package com.amazonmarket.amazonmarketsys.model.caja;

import java.math.BigDecimal;
import javax.persistence.*;
import javax.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.openxava.annotations.*;

@Entity
@Getter
@Setter
@View(members =
    "Datos de caja { nombre; descripcion; estado; } " +
    "Montos { saldoInicial; saldoActual; }"
)
@Tab(properties = "nombre, estado, saldoInicial, saldoActual")
public class Caja {
    public enum EstadoCaja {
        ABIERTA, 
        CERRADA
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Hidden
    private Long id;
    
    @NotBlank(message = "El nombre de la caja es obligatorio")
    @Size(max = 60)
    @Column(nullable = false, unique = true, length = 60)
    private String nombre;

    @Size(max = 150)
    @Column(length = 150)
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoCaja estado = EstadoCaja.CERRADA;

    @DecimalMin(value = "0.00")
    @Digits(integer = 10, fraction = 2)
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal saldoInicial = BigDecimal.ZERO;

    @DecimalMin(value = "0.00")
    @Digits(integer = 10, fraction = 2)
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal saldoActual = BigDecimal.ZERO;

    public boolean estaAbierta() {
        return EstadoCaja.ABIERTA.equals(estado);
    }
}
