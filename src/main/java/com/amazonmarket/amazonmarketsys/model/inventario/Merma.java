
package com.amazonmarket.amazonmarketsys.model.inventario;

import com.amazonmarket.amazonmarketsys.model.catalogo.Producto;
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
    "producto; lote; motivo; cantidad; " +
    "fechaRegistro; responsable; observacion"
)
@Tab(properties = "producto.nombre, motivo, cantidad, fechaRegistro, responsable")
public class Merma {
    public enum MotivoMerma {
        VENCIMIENTO,
        DANIO,
        ROBO,
        ERROR_CONTEO,
        OTRO
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Hidden
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @DescriptionsList(descriptionProperties = "nombre")
    private Producto producto;

    @ManyToOne(fetch = FetchType.LAZY)
    @DescriptionsList(descriptionProperties = "numeroLote")
    private LoteProducto lote;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private MotivoMerma motivo = MotivoMerma.OTRO;

    @Min(value = 1, message = "La cantidad debe ser mayor a cero")
    @Column(nullable = false)
    private int cantidad = 1;

    @ReadOnly
    private LocalDateTime fechaRegistro = LocalDateTime.now();

    @NotBlank(message = "Debe ingresar el responsable del registro")
    @Size(max = 80)
    @Column(nullable = false, length = 80)
    private String responsable;

    @Size(max = 200)
    @Column(length = 200)
    private String observacion;
}
