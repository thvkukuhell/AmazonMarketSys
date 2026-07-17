
package com.amazonmarket.amazonmarketsys.model.ventas;

import com.amazonmarket.amazonmarketsys.model.catalogo.Producto;
import java.math.BigDecimal;
import javax.persistence.*;
import javax.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.openxava.annotations.*;

@Entity
@Getter
@Setter
@View(members = "producto; " + "cantidad, precioUnitario; "+ "descuento, subtotal")
@Tab(properties = "producto.nombre, cantidad, precioUnitario, descuento, subtotal")
public class DetalleVenta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Hidden
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @DescriptionsList(descriptionProperties = "id, fechaVenta, total")
    private Venta venta;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @DescriptionsList(descriptionProperties = "codigo, nombre")
    private Producto producto;

    @Min(value = 1, message = "La cantidad debe ser mayor a cero")
    @Column(nullable = false)
    private int cantidad = 1;

    @DecimalMin(value = "0.10", message = "El precio unitario debe ser mayor a cero")
    @Digits(integer = 10, fraction = 2)
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal precioUnitario = BigDecimal.ZERO;

    @DecimalMin(value = "0.00", message = "El descuento no puede ser negativo")
    @Digits(integer = 10, fraction = 2)
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal descuento = BigDecimal.ZERO;

    @ReadOnly
    @Digits(integer = 10, fraction = 2)
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal subtotal = BigDecimal.ZERO;

    public void sincronizarDatosProducto() {
        if (producto == null) {
            return;
        }
        if ((precioUnitario == null || precioUnitario.compareTo(BigDecimal.ZERO) == 0) && producto.getPrecioVenta() != null) {
            precioUnitario = producto.getPrecioVenta();
        }
    }

    @PrePersist
    @PreUpdate
    public void antesDeGuardar() {
        sincronizarDatosProducto();
        calcularSubtotal();
    }

    public void calcularSubtotal() {
        if (precioUnitario == null) {
            precioUnitario = BigDecimal.ZERO;
        }
        BigDecimal importe = precioUnitario.multiply(BigDecimal.valueOf(cantidad));
        subtotal = importe.subtract(descuento);

        if (subtotal.compareTo(BigDecimal.ZERO) < 0) {
            subtotal = BigDecimal.ZERO;
        }
    }

    @AssertTrue(message = "El descuento no puede ser mayor que el importe total del detalle")
    private boolean isDescuentoValido() {
        if (descuento == null || precioUnitario == null) return true;
        BigDecimal totalDetalle = precioUnitario.multiply(BigDecimal.valueOf(cantidad));
        return com.amazonmarket.amazonmarketsys.validators.PrecioVentaValidator.esDescuentoValido(descuento, totalDetalle);
    }
}
