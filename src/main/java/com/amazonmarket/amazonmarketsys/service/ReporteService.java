package com.amazonmarket.amazonmarketsys.service;

import java.math.*;
import java.time.*;
import java.util.*;
import org.openxava.jpa.*;
import com.amazonmarket.amazonmarketsys.model.compras.*;
import com.amazonmarket.amazonmarketsys.model.personas.*;

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
}
