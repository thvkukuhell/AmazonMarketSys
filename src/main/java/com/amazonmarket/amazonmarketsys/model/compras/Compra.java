package com.amazonmarket.amazonmarketsys.model.compras;

import java.math.*;
import java.time.*;
import java.util.*;
import javax.persistence.*;
import org.openxava.annotations.*;
import com.amazonmarket.amazonmarketsys.model.personas.*;
import lombok.*;

@Entity
@Getter @Setter
public class Compra {
    
    @Id
    @Hidden
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    int id;

    @Column(length=30)
    @ReadOnly
    String codigoCompra;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @Required
    Proveedor proveedor;
    
    @Required
    LocalDate fechaCompra = LocalDate.now();
    
    @Column(length=20)
    String tipoDocumento;
    
    @Column(length=30)
    @Required
    String numeroDocumento;
    
    @Column(length=20)
    @Required
    String tipoPago = "CONTADO";
    
    @Column(length=20)
    @Required
    String estadoCompra = "REGISTRADA";
    
    @Money
    BigDecimal subtotal = BigDecimal.ZERO;
    
    @Money
    BigDecimal igv = BigDecimal.ZERO;
    
    @Money
    BigDecimal total = BigDecimal.ZERO;
    
    @Column(length=255)
    String observacion;
    
    @OneToMany(mappedBy="compra", cascade=CascadeType.ALL, orphanRemoval=true)
    @ListProperties("codigoDetalle, producto.nombre, codigoProducto, nombreProducto, cantidad, precioUnitario, subtotal")
    Collection<DetalleCompra> detalles = new ArrayList<DetalleCompra>();

    @PrePersist
    @PreUpdate
    void antesDeGuardar() {
        generarCodigosAutomaticos();
        calcularTotales();
    }

    public void generarCodigosAutomaticos() {
        if (codigoCompra == null || codigoCompra.trim().isEmpty()) {
            codigoCompra = "COMP-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        }

        if (detalles == null) {
            return;
        }

        int numeroDetalle = 1;
        for (DetalleCompra detalle: detalles) {
            if (detalle != null) {
                detalle.setCompra(this);
                detalle.generarCodigoDetalleAutomatico(codigoCompra, numeroDetalle);
                detalle.sincronizarDatosProducto();
                numeroDetalle++;
            }
        }
    }
    
    public void calcularTotales() {
        subtotal = BigDecimal.ZERO;
        
        if (detalles != null) {
            for (DetalleCompra detalle: detalles) {
                if (detalle != null) {
                    detalle.calcularSubtotal();
                    if (detalle.getSubtotal() != null) {
                        subtotal = subtotal.add(detalle.getSubtotal());
                    }
                }
            }
        }
        
        igv = subtotal.multiply(new BigDecimal("0.18")).setScale(2, RoundingMode.HALF_UP);
        total = subtotal.add(igv).setScale(2, RoundingMode.HALF_UP);
    }
}
