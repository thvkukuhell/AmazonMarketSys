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
    "producto; " +
    "Conteo { cantidadSistema; cantidadFisica; diferencia; } " +
    "fechaConteo; responsable; observacion"
)
@Tab(properties =
    "producto.codigo, producto.nombre, cantidadSistema, " +
    "cantidadFisica, diferencia, fechaConteo, responsable"
)
public class InventarioFisico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Hidden
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @DescriptionsList(descriptionProperties = "codigo, nombre")
    private Producto producto;

    @Min(value = 0, message = "La cantidad del sistema no puede ser negativa")
    @Column(nullable = false)
    private int cantidadSistema;

    @Min(value = 0, message = "La cantidad física no puede ser negativa")
    @Column(nullable = false)
    private int cantidadFisica;

    @ReadOnly
    @Column(nullable = false)
    private int diferencia;

    @ReadOnly
    @Column(nullable = false)
    private LocalDateTime fechaConteo = LocalDateTime.now();

    @NotBlank(message = "Debe ingresar el responsable del conteo")
    @Size(max = 80)
    @Column(nullable = false, length = 80)
    private String responsable;

    @Size(max = 200)
    @Column(length = 200)
    private String observacion;

    @PrePersist
    @PreUpdate
    public void calcularDiferencia() {
        diferencia = cantidadFisica - cantidadSistema;

        if (fechaConteo == null) {
            fechaConteo = LocalDateTime.now();
        }
    }
}