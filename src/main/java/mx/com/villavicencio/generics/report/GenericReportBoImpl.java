package mx.com.villavicencio.generics.report;

import java.util.Collection;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Gabriel J
 */
abstract class GenericReportBoImpl<U, D> implements GenericReportBo<U, D> {

    protected abstract void createReporte(Collection collection, String rutaServer, String rutaReporte, String formato, HttpServletResponse response);
}
