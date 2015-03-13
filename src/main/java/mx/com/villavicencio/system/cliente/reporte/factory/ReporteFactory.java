package mx.com.villavicencio.system.cliente.reporte.factory;

import mx.com.villavicencio.system.cliente.reporte.reporte.ReporteClientes;

/**
 *
 * @author Gabriel J
 */

public class ReporteFactory {

    public static final ReporteClientes newInstance(){
        return new ReporteClientes();
    }
}
