
package com.amazonmarket.amazonmarketsys.calculators;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

public class DiasParaVencerCalculatorTest {
    @Test
    void debeCalcularDiasParaVencer() {
        LocalDate fechaVencimiento = LocalDate.now().plusDays(10);

        assertEquals(10, DiasParaVencerCalculator.calcularDiasParaVencer(fechaVencimiento));
    }

    @Test
    void debeLanzarExcepcionSiFechaEsNula() {
        assertThrows(IllegalArgumentException.class,
                () -> DiasParaVencerCalculator.calcularDiasParaVencer(null));
    }

    @Test
    void debeIndicarSiEstaVencido() {
        assertTrue(DiasParaVencerCalculator.estaVencido(LocalDate.now().minusDays(1)));
        assertFalse(DiasParaVencerCalculator.estaVencido(LocalDate.now().plusDays(1)));
        assertFalse(DiasParaVencerCalculator.estaVencido(null));
    }

    @Test
    void debeIndicarSiEstaProximoAVencer() {
        LocalDate enTresDias = LocalDate.now().plusDays(3);
        LocalDate enVeinteDias = LocalDate.now().plusDays(20);

        assertTrue(DiasParaVencerCalculator.estaProximoAVencer(enTresDias, 7));
        assertFalse(DiasParaVencerCalculator.estaProximoAVencer(enVeinteDias, 7));
        assertFalse(DiasParaVencerCalculator.estaProximoAVencer(null, 7));
    }
}
