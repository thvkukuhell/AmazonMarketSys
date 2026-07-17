
package com.amazonmarket.amazonmarketsys.model.inventario;

import com.amazonmarket.amazonmarketsys.model.catalogo.Producto;
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
    "producto; numeroLote; " +
    "Fechas { fechaIngreso; fechaVencimiento; } " +
    "Cantidades { cantidadInicial; cantidadActual; costoUnitario; } " +
    "activo"
)
@Tab(properties = "producto.nombre, numeroLote, fechaVencimiento, cantidadActual, activo")
public class LoteProducto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Hidden
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @DescriptionsList(descriptionProperties = "nombre")
    private Producto producto;

    @NotBlank(message = "El numero de lote es obligatorio")
    @Size(max = 40)
    @Column(nullable = false, length = 40)
    private String numeroLote;

    @ReadOnly
    private LocalDate fechaIngreso = LocalDate.now();

    private LocalDate fechaVencimiento;

    @Min(value = 0, message = "La cantidad inicial no puede ser negativa")
    @Column(nullable = false)
    private int cantidadInicial = 0;

    @Min(value = 0, message = "La cantidad actual no puede ser negativa")
    @Column(nullable = false)
    private int cantidadActual = 0;

    @DecimalMin(value = "0.00", message = "El costo unitario no puede ser negativo")
    @Digits(integer = 10, fraction = 2)
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal costoUnitario = BigDecimal.ZERO;

    private boolean activo = true;

    public boolean tieneStock() {
        return cantidadActual > 0;
    }

    public void descontar(int cantidad) {
        if (cantidad < 0) {
            throw new IllegalArgumentException("La cantidad a descontar no puede ser negativa");
        }

        if (cantidad > cantidadActual) {
            throw new IllegalStateException("No hay suficiente stock en el lote");
        }

        cantidadActual -= cantidad;
    }

    public void incrementar(int cantidad) {
        if (cantidad < 0) {
            throw new IllegalArgumentException("La cantidad a incrementar no puede ser negativa");
        }

        cantidadActual += cantidad;
    }

    @AssertTrue(message = "La fecha de vencimiento debe ser posterior a la fecha de ingreso")
    private boolean isFechaVencimientoValida() {
        return com.amazonmarket.amazonmarketsys.validators.FechaVencimientoValidator.esFechaVencimientoValida(fechaIngreso, fechaVencimiento);
    }

    @AssertTrue(message = "La fecha de vencimiento es obligatoria para productos perecibles")
    private boolean isFechaVencimientoRequerida() {
        boolean perecible = producto != null && producto.isPerecible();
        return com.amazonmarket.amazonmarketsys.validators.FechaVencimientoValidator.esObligatoriaPorProducto(perecible, fechaVencimiento);
    }
}
