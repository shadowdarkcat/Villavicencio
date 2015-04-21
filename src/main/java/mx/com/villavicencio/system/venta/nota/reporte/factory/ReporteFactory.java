package mx.com.villavicencio.system.venta.nota.reporte.factory;

import mx.com.villavicencio.system.venta.nota.reporte.reporte.ReporteNotaVenta;

/**
 *
 * @author Gabriel J
 */
public class ReporteFactory {

    public static ReporteNotaVenta newInstance() {
        return new ReporteNotaVenta();
    }
}
