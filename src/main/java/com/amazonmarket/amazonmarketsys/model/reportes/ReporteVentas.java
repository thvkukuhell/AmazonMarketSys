package com.amazonmarket.amazonmarketsys.model.reportes;

import java.math.BigDecimal;
import java.time.LocalDate;
import com.amazonmarket.amazonmarketsys.model.catalogo.Producto;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ReporteVentas {

    Producto producto;

    String nombreProducto;

    int cantidadVendida;

    BigDecimal totalVendido = BigDecimal.ZERO;

    LocalDate fechaInicio;

    LocalDate fechaFin;
}