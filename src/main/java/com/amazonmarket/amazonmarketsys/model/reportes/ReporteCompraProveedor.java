package com.amazonmarket.amazonmarketsys.model.reportes;

import java.math.*;
import java.time.*;
import com.amazonmarket.amazonmarketsys.model.personas.*;
import lombok.*;

@Getter @Setter
public class ReporteCompraProveedor {
    
    Proveedor proveedor;
    
    BigDecimal totalComprado = BigDecimal.ZERO;
    
    int cantidadCompras;
    
    LocalDate fechaInicio;
    
    LocalDate fechaFin;
}
