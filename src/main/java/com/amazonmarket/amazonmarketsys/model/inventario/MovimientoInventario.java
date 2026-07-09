
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
    "producto; lote; tipoMovimiento; cantidad; " +
    "fechaMovimiento; documentoReferencia; observacion"
)
@Tab(properties = "producto.nombre, tipoMovimiento, cantidad, fechaMovimiento, documentoReferencia")
public class MovimientoInventario {
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
    @Column(nullable = false, length = 30)
    private TipoMovimiento tipoMovimiento = TipoMovimiento.ENTRADA_COMPRA;

    @Min(value = 1, message = "La cantidad debe ser mayor a cero")
    @Column(nullable = false)
    private int cantidad = 1;

    @ReadOnly
    private LocalDateTime fechaMovimiento = LocalDateTime.now();

    @Size(max = 60)
    @Column(length = 60)
    private String documentoReferencia;

    @Size(max = 200)
    @Column(length = 200)
    private String observacion;

    public boolean esIncremento() {
        return tipoMovimiento != null && tipoMovimiento.esIncremento();
    }
}
