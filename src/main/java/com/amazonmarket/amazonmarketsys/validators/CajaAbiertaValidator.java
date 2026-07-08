
package com.amazonmarket.amazonmarketsys.validators;

import com.amazonmarket.amazonmarketsys.model.caja.Caja;

public class CajaAbiertaValidator {
    private CajaAbiertaValidator() {
    }
    
    public static boolean cajaEsAbierta(Caja caja) {
        return caja != null && caja.estaAbierta();
    }
}
