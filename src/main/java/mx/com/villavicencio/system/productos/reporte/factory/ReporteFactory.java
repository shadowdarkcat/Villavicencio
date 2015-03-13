package mx.com.villavicencio.system.productos.reporte.factory;

import mx.com.villavicencio.system.productos.reporte.reporte.ReporteProductos;

/**
 *
 * @author Gabriel J
 */
public class ReporteFactory {

    public static final ReporteProductos newInstance() {
        return new ReporteProductos();
    }
}
