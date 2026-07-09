
package com.amazonmarket.amazonmarketsys.model.inventario;

import static org.junit.jupiter.api.Assertions.*;
import com.amazonmarket.amazonmarketsys.model.catalogo.Producto;
import org.junit.jupiter.api.Test;

public class MermaTest {
    @Test
    void mermaNuevaDebeTenerMotivoOtroPorDefecto() {
        Merma merma = new Merma();

        assertEquals(Merma.MotivoMerma.OTRO, merma.getMotivo());
        assertEquals(1, merma.getCantidad());
    }

    @Test
    void debeRegistrarMermaPorVencimiento() {
        Producto producto = new Producto();
        producto.setNombre("Yogurt");

        Merma merma = new Merma();
        merma.setProducto(producto);
        merma.setMotivo(Merma.MotivoMerma.VENCIMIENTO);
        merma.setCantidad(5);
        merma.setResponsable("Hellen");

        assertEquals("Yogurt", merma.getProducto().getNombre());
        assertEquals(Merma.MotivoMerma.VENCIMIENTO, merma.getMotivo());
        assertEquals(5, merma.getCantidad());
        assertEquals("Hellen", merma.getResponsable());
    }
}
