
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
    "caja; " +
    "fechaApertura; montoInicial; responsable; observacion"
)
@Tab(properties = "caja.nombre, fechaApertura, montoInicial, responsable")
public class AperturaCaja {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Hidden
    private Long id;
    
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @DescriptionsList(descriptionProperties = "nombre")
    private Caja caja;
    
    @ReadOnly
    private LocalDateTime fechaApertura = LocalDateTime.now();
    
    @DecimalMin(value = "0.00", message = "El monto inicial no puede ser negativo")
    @Digits(integer = 10, fraction = 2)
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal montoInicial = BigDecimal.ZERO;
    
    @NotBlank(message = "Debe ingresar el responsable de apertura")
    @Size(max = 80)
    @Column(nullable = false, length = 80)
    private String responsable;
    
    @Size(max = 250)
    @Column(length = 250)
    private String observacion;
}
