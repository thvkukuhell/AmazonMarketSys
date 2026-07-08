
package com.amazonmarket.amazonmarketsys.actions;

import java.math.BigDecimal;
import org.openxava.actions.ViewBaseAction;

public class CalcularTotalVentaAction extends ViewBaseAction {
    @Override
    public void execute() throws Exception {
        BigDecimal subtotal = (BigDecimal) getView().getValue("subtotal");
        BigDecimal porcentaje = (BigDecimal) getView().getValue("porcentajeDescuentoManual");

        if (subtotal == null) subtotal = BigDecimal.ZERO;
        if (porcentaje == null) porcentaje = BigDecimal.ZERO;

        BigDecimal descuento = subtotal.multiply(porcentaje).divide(BigDecimal.valueOf(100));
        BigDecimal total = subtotal.subtract(descuento);

        if (total.compareTo(BigDecimal.ZERO) < 0) {
            total = BigDecimal.ZERO;
        }

        getView().setValue("descuentoTotal", descuento);
        getView().setValue("total", total);

        addMessage("Total de venta calculado correctamente");
    }
}
