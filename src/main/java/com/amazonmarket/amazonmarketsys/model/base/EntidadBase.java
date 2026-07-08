package com.amazonmarket.amazonmarketsys.model.base;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.openxava.annotations.Hidden;

@MappedSuperclass
@Getter
@Setter
public abstract class EntidadBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Hidden
    private Long id;

    @Column(nullable = false)
    private boolean activo = true;

    public void activar() {
        this.activo = true;
    }

    public void desactivar() {
        this.activo = false;
    }

    public boolean estaActivo() {
        return activo;
    }
}