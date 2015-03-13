package mx.com.villavicencio.system.vendedor.reporte.factory;

import mx.com.villavicencio.system.vendedor.reporte.reporte.ReporteVendedores;

/**
 *
 * @author Gabriel J
 */
public class ReporteFactory {

    public static final ReporteVendedores newInstance() {
        return new ReporteVendedores();
    }
}
