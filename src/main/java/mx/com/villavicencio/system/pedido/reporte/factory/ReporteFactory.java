package mx.com.villavicencio.system.pedido.reporte.factory;

import mx.com.villavicencio.system.pedido.reporte.reporte.ReportePedidos;

/**
 *
 * @author Gabriel J
 */
public class ReporteFactory {

    public static ReportePedidos newInstance() {
        return new ReportePedidos();
    }
}
