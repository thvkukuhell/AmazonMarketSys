
package com.amazonmarket.amazonmarketsys.model.promociones;

import java.math.BigDecimal;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.openxava.annotations.*;

@Entity
@Getter
@Setter
@View(members =
    "Datos { nombre; descripcion; tipoPromocion; activa; } " +
    "Condiciones { montoMinimo; porcentajeDescuento; fechaInicio; fechaFin; }"
)
@Tab(properties = "nombre, tipoPromocion, montoMinimo, porcentajeDescuento, fechaInicio, fechaFin, activa")
public class Promocion {
    public enum TipoPromocion {
        POR_MONTO,
        CLIENTE_FRECUENTE,
        PRODUCTO_POR_VENCER,
        COMPRA_MAYORISTA
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Hidden
    private Long id;

    @NotBlank(message = "El nombre de la promoción es obligatorio")
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String nombre;

    @Size(max = 250)
    @Column(length = 250)
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 40)
    private TipoPromocion tipoPromocion = TipoPromocion.POR_MONTO;

    @DecimalMin(value = "0.00")
    @Digits(integer = 10, fraction = 2)
    private BigDecimal montoMinimo = BigDecimal.ZERO;

    @DecimalMin(value = "0.00")
    @DecimalMax(value = "100.00")
    @Digits(integer = 3, fraction = 2)
    private BigDecimal porcentajeDescuento = BigDecimal.ZERO;

    private LocalDate fechaInicio = LocalDate.now();

    private LocalDate fechaFin;

    private boolean activa = true;

    public boolean estaVigente() {
        LocalDate hoy = LocalDate.now();

        boolean inicioValido = fechaInicio == null || !hoy.isBefore(fechaInicio);
        boolean finValido = fechaFin == null || !hoy.isAfter(fechaFin);

        return activa && inicioValido && finValido;
    }
}
