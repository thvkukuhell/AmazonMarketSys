
package com.amazonmarket.amazonmarketsys.model.catalogo;

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
    "Datos generales { codigo; nombre; descripcion; categoria; marca; unidadMedida; } " +
    "Precios { precioCompra; precioVenta; } " +
    "Existencias { stockMinimo; perecible; activo; }"
)
@Tab(properties = "codigo, nombre, categoria.nombre, marca.nombre, precioVenta, stockMinimo, activo")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Hidden
    private Long id;

    @ReadOnly
    @Size(max = 30)
    @Column(unique = true, length = 30)
    private String codigo;

    @NotBlank(message = "El nombre del producto es obligatorio")
    @Size(max = 120)
    @Column(nullable = false, length = 120)
    private String nombre;

    @Size(max = 250)
    @Column(length = 250)
    private String descripcion;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @DescriptionsList(descriptionProperties = "nombre")
    private Categoria categoria;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @DescriptionsList(descriptionProperties = "nombre")
    private Marca marca;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @DescriptionsList(descriptionProperties = "nombre, simbolo")
    private UnidadMedida unidadMedida;

    @DecimalMin(value = "0.00", message = "El precio de compra no puede ser negativo")
    @Digits(integer = 10, fraction = 2)
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal precioCompra = BigDecimal.ZERO;

    @DecimalMin(value = "0.00", message = "El precio de venta no puede ser negativo")
    @Digits(integer = 10, fraction = 2)
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal precioVenta = BigDecimal.ZERO;

    @Min(value = 0, message = "El stock minimo no puede ser negativo")
    @Column(nullable = false)
    private int stockMinimo = 0;

    @Column(nullable = false)
    private boolean perecible = false;

    private boolean activo = true;

    @PrePersist
    @PreUpdate
    public void antesDeGuardar() {
        if (codigo == null || codigo.trim().isEmpty()) {
            codigo = "PROD-" + java.util.UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        }
    }

    public boolean tieneMargenValido() {
        return precioVenta != null
                && precioCompra != null
                && precioVenta.compareTo(precioCompra) >= 0;
    }

    @AssertTrue(message = "El precio de venta no puede ser menor que el precio de compra")
    public boolean isMargenValido() {
        return tieneMargenValido();
    }
}
