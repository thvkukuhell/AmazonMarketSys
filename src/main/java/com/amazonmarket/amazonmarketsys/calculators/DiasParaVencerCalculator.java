
package com.amazonmarket.amazonmarketsys.calculators;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class DiasParaVencerCalculator {
    private DiasParaVencerCalculator() {
    }

    public static long calcularDiasParaVencer(LocalDate fechaVencimiento) {
        if (fechaVencimiento == null) {
            throw new IllegalArgumentException("La fecha de vencimiento no puede ser nula");
        }

        return ChronoUnit.DAYS.between(LocalDate.now(), fechaVencimiento);
    }

    public static boolean estaVencido(LocalDate fechaVencimiento) {
        return fechaVencimiento != null && fechaVencimiento.isBefore(LocalDate.now());
    }

    public static boolean estaProximoAVencer(LocalDate fechaVencimiento, int diasUmbral) {
        if (fechaVencimiento == null) {
            return false;
        }

        long dias = calcularDiasParaVencer(fechaVencimiento);
        return dias >= 0 && dias <= diasUmbral;
    }
}
