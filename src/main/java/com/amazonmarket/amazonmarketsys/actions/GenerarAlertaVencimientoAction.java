
package com.amazonmarket.amazonmarketsys.actions;

import com.amazonmarket.amazonmarketsys.calculators.DiasParaVencerCalculator;
import java.time.LocalDate;
import org.openxava.actions.ViewBaseAction;

public class GenerarAlertaVencimientoAction extends ViewBaseAction {
    private static final int DIAS_ALERTA = 7;

    @Override
    public void execute() throws Exception {
        LocalDate fechaVencimiento = (LocalDate) getView().getValue("fechaVencimiento");

        if (fechaVencimiento == null) {
            addMessage("Este lote no tiene fecha de vencimiento registrada");
            return;
        }

        if (DiasParaVencerCalculator.estaVencido(fechaVencimiento)) {
            addError("El lote ya esta vencido");
            return;
        }

        if (DiasParaVencerCalculator.estaProximoAVencer(fechaVencimiento, DIAS_ALERTA)) {
            long dias = DiasParaVencerCalculator.calcularDiasParaVencer(fechaVencimiento);
            addMessage("Atencion: el lote vence en " + dias + " dia(s)");
        } else {
            addMessage("El lote no esta proximo a vencer");
        }
    }
}
