
package com.amazonmarket.amazonmarketsys.model.ventas;

import com.amazonmarket.amazonmarketsys.model.caja.Caja;
import com.amazonmarket.amazonmarketsys.model.personas.Cliente;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import javax.persistence.*;
import javax.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.openxava.annotations.*;
import java.math.RoundingMode;

@Entity 
@Getter
@Setter
@View(members = 
        "Datos de venta { codigoVenta; fechaVenta; cliente; caja; comprobante; estado; } " +
        "Importes { subtotal; porcentajeDescuentoManual; descuentoTotal; total; } " +
        "Detalle de productos { detalles; } " +
        "Pagos { pagos; } " +
        "Observacion { observacion; }"
)
@Tab(properties = "codigoVenta, fechaVenta, cliente.nombres, comprobante, subtotal, descuentoTotal, total, estado")
public class Venta {
    public enum EstadoVenta {
        REGISTRADA,
        ANULADA
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Hidden
    private Long id;

    @ReadOnly
    public String getCodigoVenta() {
        return id == null ? null : String.format("VENT-%06d", id);
    }

    @ReadOnly
    private LocalDateTime fechaVenta = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @DescriptionsList(descriptionProperties = "dni, nombres, apellidos")
    private Cliente cliente;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @DescriptionsList(descriptionProperties = "nombre")
    private Caja caja;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private Comprobante comprobante = Comprobante.BOLETA_ELECTRONICA;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoVenta estado = EstadoVenta.REGISTRADA;

    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, orphanRemoval = true)
    @ListProperties("producto.nombre, cantidad, precioUnitario, descuento, subtotal")
    private Collection<DetalleVenta> detalles = new ArrayList<>();

    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, orphanRemoval = true)
    @ListProperties("metodoPago, monto, referencia, fechaPago")
    private Collection<PagoVenta> pagos = new ArrayList<>();

    @ReadOnly
    @Digits(integer = 10, fraction = 2)
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal subtotal = BigDecimal.ZERO;

    @DecimalMin(value = "0.00", message = "El porcentaje de descuento no puede ser negativo")
    @DecimalMax(value = "100.00", message = "El descuento no puede superar el 100%")
    @Digits(integer = 3, fraction = 2)
    private BigDecimal porcentajeDescuentoManual = BigDecimal.ZERO;

    @ReadOnly
    @Digits(integer = 10, fraction = 2)
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal descuentoTotal = BigDecimal.ZERO;

    @ReadOnly
    @Digits(integer = 10, fraction = 2)
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal total = BigDecimal.ZERO;

    @Size(max = 250)
    @Column(length = 250)
    private String observacion;

    public void prepararDetalles() {
        if (detalles == null) {
            return;
        }
        for (DetalleVenta detalle : detalles) {
            if (detalle != null) {
                detalle.setVenta(this);
                detalle.sincronizarDatosProducto();
                detalle.calcularSubtotal();
            }
        }
    }

    @PrePersist
    @PreUpdate
    public void antesDeGuardar() {
        validarEstadoCaja();
        calcularTotales();
    }

    public void validarEstadoCaja() {
        if (!com.amazonmarket.amazonmarketsys.validators.CajaAbiertaValidator.cajaEsAbierta(caja)) {
            throw new IllegalStateException("La caja debe estar abierta para registrar una venta");
        }
    }

    public void calcularTotales() {
        prepararDetalles();

        subtotal = detalles.stream()
                .map(DetalleVenta::getSubtotal)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        descuentoTotal = subtotal
                .multiply(porcentajeDescuentoManual)
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

        total = subtotal.subtract(descuentoTotal);

        if (total.compareTo(BigDecimal.ZERO) < 0) {
            total = BigDecimal.ZERO;
        }
    }

    public void anular() {
        this.estado = EstadoVenta.ANULADA;
    }
}
