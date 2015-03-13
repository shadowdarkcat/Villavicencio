package mx.com.villavicencio.generics.report;

import java.util.Collection;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Gabriel J
 */
interface GenericReportBo<U, D> {

    void generarReporte(U user, D object, String rutaServer, String rutaReporte, String formato, HttpServletResponse response);

    void generarReporte(U user, Collection<D> object, String rutaServer, String rutaReporte, String formato, HttpServletResponse response);
}
