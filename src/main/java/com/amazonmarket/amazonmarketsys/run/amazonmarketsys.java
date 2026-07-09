package com.amazonmarket.amazonmarketsys.run;

import org.openxava.util.*;

/**
 * Ejecuta esta clase para arrancar la aplicación.
 */
public class amazonmarketsys {

    public static void main(String[] args) throws Exception {
        DBServer.start("amazonmarketsys-db"); // Para usar tu propia base de datos comenta esta linea y configura src/main/webapp/META-INF/context.xml
        AppServer.run("amazonmarketsys"); // Usa AppServer.run("") para funcionar en el contexto raiz
    }

}
