package com.amazonmarket.amazonmarketsys.actions;

import org.openxava.actions.*;
import com.amazonmarket.amazonmarketsys.model.compras.*;
import com.amazonmarket.amazonmarketsys.service.*;

public class RegistrarCompraAction extends ViewBaseAction {
    
    public void execute() throws Exception {
        Compra compra = (Compra) getView().getEntity();
        
        if (compra.getProveedor() == null) {
            addError("compra_proveedor_obligatorio");
            return;
        }
        if (compra.getDetalles() == null || compra.getDetalles().isEmpty()) {
            addError("compra_detalles_obligatorios");
            return;
        }
        
        try {
            CompraService service = new CompraService();
            service.registrarCompra(compra);
            getView().setValue("subtotal", compra.getSubtotal());
            getView().setValue("igv", compra.getIgv());
            getView().setValue("total", compra.getTotal());
            addMessage("compra_registrada_correctamente");
        } catch (IllegalArgumentException ex) {
            addError("compra_error_registrar", ex.getMessage());
        }
    }
}
