
package com.amazonmarket.amazonmarketsys.model.ventas;

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
@View(members = "venta; " + "metodoPago, monto; " + "referencia; fechaPago")
@Tab(properties = "venta.id, metodoPago, monto, referencia, fechaPago")
public class PagoVenta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Hidden
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @DescriptionsList(descriptionProperties = "id, fechaVenta, total")
    private Venta venta;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private MetodoPago metodoPago = MetodoPago.EFECTIVO;
    
    @DecimalMin(value = "0.10", message = "El monto pagado debe ser mayor a cero")
    @Digits(integer = 10, fraction = 2)
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal monto = BigDecimal.ZERO;
    
    @Size(max = 80)
    @Column(length = 80)
    private String referencia;
    
    @ReadOnly
    private LocalDateTime fechaPago = LocalDateTime.now();
    
}
