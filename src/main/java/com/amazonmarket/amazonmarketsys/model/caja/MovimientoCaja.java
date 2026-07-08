
package com.amazonmarket.amazonmarketsys.model.caja;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.persistence.*;
import javax.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.openxava.annotations.*;

@Entity
@Getter
@Setter
@View(members =
    "caja; fechaMovimiento; tipoMovimiento; monto; descripcion; responsable"
)
@Tab(properties = "caja.nombre, fechaMovimiento, tipoMovimiento, monto, responsable")
public class MovimientoCaja {
    public enum TipoMovimientoCaja {
        INGRESO,
        EGRESO
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Hidden
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @DescriptionsList(descriptionProperties = "nombre")
    private Caja caja;

    @ReadOnly
    private LocalDateTime fechaMovimiento = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TipoMovimientoCaja tipoMovimiento = TipoMovimientoCaja.INGRESO;

    @DecimalMin(value = "0.10", message = "El monto debe ser mayor a cero")
    @Digits(integer = 10, fraction = 2)
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal monto = BigDecimal.ZERO;

    @NotBlank(message = "Debe ingresar una descripción")
    @Size(max = 180)
    @Column(nullable = false, length = 180)
    private String descripcion;

    @NotBlank(message = "Debe ingresar el responsable")
    @Size(max = 80)
    @Column(nullable = false, length = 80)
    private String responsable;
}
