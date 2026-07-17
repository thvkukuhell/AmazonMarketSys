package com.amazonmarket.amazonmarketsys.service;

import java.math.*;
import java.time.*;
import java.util.*;
import org.openxava.jpa.*;
import com.amazonmarket.amazonmarketsys.model.compras.*;
import com.amazonmarket.amazonmarketsys.model.personas.*;
import java.time.temporal.ChronoUnit;
import com.amazonmarket.amazonmarketsys.model.catalogo.Producto;
import com.amazonmarket.amazonmarketsys.model.inventario.LoteProducto;
import com.amazonmarket.amazonmarketsys.model.reportes.ReporteVentas;
import com.amazonmarket.amazonmarketsys.model.reportes.ReporteInventario;

public class ReporteService {
    
    public BigDecimal totalComprado() {
        BigDecimal total = (BigDecimal) XPersistence.getManager()
            .createQuery("select sum(c.total) from Compra c where c.estadoCompra = 'REGISTRADA'")
            .getSingleResult();
        
        if (total == null) {
            return BigDecimal.ZERO;
        }
        return total;
        
    }
    
    public BigDecimal totalComprado(List<Compra> compras) {
        BigDecimal total = BigDecimal.ZERO;
        
        if (compras == null) {
            return total;
        }
        
        for (Compra compra: compras) {
            if (compra != null && "REGISTRADA".equals(compra.getEstadoCompra()) && compra.getTotal() != null) {
                total = total.add(compra.getTotal());
            }
        }
        
        return total;
    }
    
    public List<Compra> comprasPorProveedor(Proveedor proveedor) {
        if (proveedor == null) {
            return new ArrayList<Compra>();
        }
        
        return XPersistence.getManager()
            .createQuery("from Compra c where c.proveedor = :proveedor order by c.fechaCompra desc", Compra.class)
            .setParameter("proveedor", proveedor)
            .getResultList();
    }
    
    public List<Compra> comprasPorPeriodo(LocalDate desde, LocalDate hasta) {
        if (desde == null || hasta == null) {
            return new ArrayList<Compra>();
        }
        
        return XPersistence.getManager()
            .createQuery("from Compra c where c.fechaCompra between :desde and :hasta order by c.fechaCompra desc", Compra.class)
            .setParameter("desde", desde)
            .setParameter("hasta", hasta)
            .getResultList();
    }
    
    public List<Proveedor> proveedoresActivos() {
        return XPersistence.getManager()
            .createQuery("from Proveedor p where p.estado = 'ACTIVO' order by p.razonSocial", Proveedor.class)
            .getResultList();
    }
    
    public List<Compra> comprasRegistradas() {
        return comprasPorEstado("REGISTRADA");
    }
    
    public List<Compra> comprasAnuladas() {
        return comprasPorEstado("ANULADA");
    }
    
    public int contarComprasRegistradas(List<Compra> compras) {
        return contarComprasPorEstado(compras, "REGISTRADA");
    }
    
    public int contarComprasAnuladas(List<Compra> compras) {
        return contarComprasPorEstado(compras, "ANULADA");
    }
    
    public int contarProveedoresActivos(List<Proveedor> proveedores) {
        int cantidad = 0;
        
        if (proveedores == null) {
            return cantidad;
        }
        
        for (Proveedor proveedor: proveedores) {
            if (proveedor != null && "ACTIVO".equals(proveedor.getEstado())) {
                cantidad++;
            }
        }
        
        return cantidad;
    }
    
    private List<Compra> comprasPorEstado(String estado) {
        return XPersistence.getManager()
            .createQuery("from Compra c where c.estadoCompra = :estado order by c.fechaCompra desc", Compra.class)
            .setParameter("estado", estado)
            .getResultList();
    }
    
    private int contarComprasPorEstado(List<Compra> compras, String estado) {
        int cantidad = 0;
        
        if (compras == null) {
            return cantidad;
        }
        
        for (Compra compra: compras) {
            if (compra != null && estado.equals(compra.getEstadoCompra())) {
                cantidad++;
            }
        }
        
        return cantidad;
    }
    
    public BigDecimal totalVendido() {
        BigDecimal total = (BigDecimal) XPersistence.getManager()
            .createQuery("select sum(d.subtotal) from DetalleVenta d where d.venta.estado = 'REGISTRADA'")
            .getSingleResult();

        if (total == null) {
            return BigDecimal.ZERO;
        }
        return total;
    }

    public List<ReporteVentas> productosMasVendidos() {
        List<Object[]> filas = XPersistence.getManager()
            .createQuery("select dv.producto.id, sum(dv.cantidad), sum(dv.subtotal) " +
                "from DetalleVenta dv where dv.venta.estado = 'REGISTRADA' " +
                "group by dv.producto.id order by sum(dv.subtotal) desc")
            .getResultList();

        List<ReporteVentas> resultado = new ArrayList<ReporteVentas>();

        for (Object[] fila : filas) {
            Long productoId = (Long) fila[0];
            Long cantidad = (Long) fila[1];
            BigDecimal total = (BigDecimal) fila[2];

            Producto producto = XPersistence.getManager().find(Producto.class, productoId);
            if (producto == null) continue;

            ReporteVentas reporte = new ReporteVentas();
            reporte.setProducto(producto);
            reporte.setNombreProducto(producto.getNombre());
            reporte.setCantidadVendida(cantidad != null ? cantidad.intValue() : 0);
            reporte.setTotalVendido(total != null ? total : BigDecimal.ZERO);
            resultado.add(reporte);
        }

        return resultado;
    }

    public List<ReporteInventario> productosProximosAVencer(int diasLimite) {
        LocalDate hoy = LocalDate.now();
        LocalDate limite = hoy.plusDays(diasLimite);

        List<LoteProducto> lotes = XPersistence.getManager()
            .createQuery("from LoteProducto l where l.activo = true and l.cantidadActual > 0 " +
                "and l.fechaVencimiento between :hoy and :limite order by l.fechaVencimiento asc", LoteProducto.class)
            .setParameter("hoy", hoy)
            .setParameter("limite", limite)
            .getResultList();

        List<ReporteInventario> resultado = new ArrayList<ReporteInventario>();

        for (LoteProducto lote : lotes) {
            ReporteInventario reporte = new ReporteInventario();
            reporte.setProducto(lote.getProducto());
            reporte.setNombreProducto(lote.getProducto().getNombre());
            reporte.setNumeroLote(lote.getNumeroLote());
            reporte.setCantidadActual(lote.getCantidadActual());
            reporte.setFechaVencimiento(lote.getFechaVencimiento());
            reporte.setDiasParaVencer(ChronoUnit.DAYS.between(hoy, lote.getFechaVencimiento()));
            resultado.add(reporte);
        }

        return resultado;
    }
}
