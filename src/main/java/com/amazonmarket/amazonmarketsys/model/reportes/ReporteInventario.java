package com.amazonmarket.amazonmarketsys.model.reportes;

import java.time.LocalDate;
import com.amazonmarket.amazonmarketsys.model.catalogo.Producto;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ReporteInventario {

    Producto producto;

    String nombreProducto;

    String numeroLote;

    int cantidadActual;

    LocalDate fechaVencimiento;

    long diasParaVencer;
}