
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
    "caja; fechaCierre; responsable; " +
    "Montos { totalVentas; totalIngresos; totalEgresos; montoFinal; diferencia; } " +
    "observacion"
)
@Tab(properties = "caja.nombre, fechaCierre, totalVentas, montoFinal, diferencia, responsable")
public class CierreCaja {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Hidden
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @DescriptionsList(descriptionProperties = "nombre")
    private Caja caja;

    @ReadOnly
    private LocalDateTime fechaCierre = LocalDateTime.now();

    @DecimalMin(value = "0.00")
    @Digits(integer = 10, fraction = 2)
    @ReadOnly
    private BigDecimal totalVentas = BigDecimal.ZERO;

    @DecimalMin(value = "0.00")
    @Digits(integer = 10, fraction = 2)
    @ReadOnly
    private BigDecimal totalIngresos = BigDecimal.ZERO;

    @DecimalMin(value = "0.00")
    @Digits(integer = 10, fraction = 2)
    @ReadOnly
    private BigDecimal totalEgresos = BigDecimal.ZERO;

    @DecimalMin(value = "0.00")
    @Digits(integer = 10, fraction = 2)
    private BigDecimal montoFinal = BigDecimal.ZERO;

    @ReadOnly
    @Digits(integer = 10, fraction = 2)
    private BigDecimal diferencia = BigDecimal.ZERO;

    @NotBlank(message = "Debe ingresar el responsable del cierre")
    @Size(max = 80)
    @Column(nullable = false, length = 80)
    private String responsable;

    @Size(max = 250)
    @Column(length = 250)
    private String observacion;

    @PrePersist
    @PreUpdate
    public void calcularDiferencia() {
        BigDecimal esperado = totalVentas.add(totalIngresos).subtract(totalEgresos);
        diferencia = montoFinal.subtract(esperado);
    }
}
