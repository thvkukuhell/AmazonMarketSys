
package com.amazonmarket.amazonmarketsys.actions;

import java.math.BigDecimal;
import org.openxava.actions.ViewBaseAction;

public class AplicarDescuentoAction extends ViewBaseAction {
     @Override
    public void execute() throws Exception {
        BigDecimal subtotal = (BigDecimal) getView().getValue("subtotal");
        BigDecimal porcentaje = (BigDecimal) getView().getValue("porcentajeDescuentoManual");

        if (subtotal == null) subtotal = BigDecimal.ZERO;
        if (porcentaje == null) porcentaje = BigDecimal.ZERO;

        if (porcentaje.compareTo(BigDecimal.ZERO) < 0 || porcentaje.compareTo(BigDecimal.valueOf(100)) > 0) {
            addError("El porcentaje de descuento debe estar entre 0 y 100");
            return;
        }

        BigDecimal descuento = subtotal.multiply(porcentaje).divide(BigDecimal.valueOf(100));
        BigDecimal total = subtotal.subtract(descuento);

        getView().setValue("descuentoTotal", descuento);
        getView().setValue("total", total);

        addMessage("Descuento aplicado correctamente");
    }
}
