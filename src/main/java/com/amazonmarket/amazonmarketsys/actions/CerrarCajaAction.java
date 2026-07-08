
package com.amazonmarket.amazonmarketsys.actions;

import com.amazonmarket.amazonmarketsys.model.caja.Caja;
import org.openxava.actions.ViewBaseAction;

public class CerrarCajaAction extends ViewBaseAction {
    @Override
    public void execute() throws Exception {
        getView().setValue("estado", Caja.EstadoCaja.CERRADA);
        addMessage("Caja cerrada correctamente");
    }
}
