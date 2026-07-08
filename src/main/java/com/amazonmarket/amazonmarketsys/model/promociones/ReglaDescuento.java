
package com.amazonmarket.amazonmarketsys.model.promociones;

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
    "promocion; descripcion; " +
    "Condiciones { montoMinimo; porcentajeDescuento; requiereClienteFrecuente; diasAntesVencimiento; }"
)
@Tab(properties = "promocion.nombre, montoMinimo, porcentajeDescuento, requiereClienteFrecuente, diasAntesVencimiento")
public class ReglaDescuento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Hidden
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @DescriptionsList(descriptionProperties = "nombre")
    private Promocion promocion;

    @Size(max = 200)
    @Column(length = 200)
    private String descripcion;

    @DecimalMin(value = "0.00")
    @Digits(integer = 10, fraction = 2)
    private BigDecimal montoMinimo = BigDecimal.ZERO;

    @DecimalMin(value = "0.00")
    @DecimalMax(value = "100.00")
    @Digits(integer = 3, fraction = 2)
    private BigDecimal porcentajeDescuento = BigDecimal.ZERO;

    private boolean requiereClienteFrecuente = false;

    @Min(value = 0)
    private int diasAntesVencimiento = 0;
}
