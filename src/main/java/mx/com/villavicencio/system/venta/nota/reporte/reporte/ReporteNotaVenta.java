package mx.com.villavicencio.system.venta.nota.reporte.reporte;

import java.util.ArrayList;
import java.util.Collection;
import javax.servlet.http.HttpServletResponse;
import mx.com.villavicencio.commons.exception.ApplicationException;
import mx.com.villavicencio.generics.report.GenericReport;
import mx.com.villavicencio.properties.PropertiesBean;
import mx.com.villavicencio.properties.Property;
import mx.com.villavicencio.system.usuario.dto.DtoUsuario;
import mx.com.villavicencio.system.venta.nota.dto.DtoNotaVenta;
import mx.com.villavicencio.system.venta.nota.factory.NotaVentaFactory;

/**
 *
 * @author Gabriel J
 */

public class ReporteNotaVenta extends GenericReport<DtoUsuario, DtoNotaVenta>{

    @Override
    public void generarReporte(DtoUsuario user, DtoNotaVenta object, String rutaServer, String rutaReporte, String formato, HttpServletResponse response) {
        if ((user != null) && (user.getIdUsuario() != 0)) {
            destino = "notaVenta";
            Collection<DtoNotaVenta> collection = new ArrayList<>();
            collection.add(object);
            createReporte(collection, rutaServer, rutaReporte, formato, response);
        } else {
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public void generarReporte(DtoUsuario user, Collection<DtoNotaVenta> object, String rutaServer, String rutaReporte, String formato, HttpServletResponse response) {
        if ((user != null) && (user.getIdUsuario() != 0)) {
            destino = "notaVenta";
            master = "master";
            DtoNotaVenta notaVenta = NotaVentaFactory.newInstance();
            notaVenta.setNotasVenta(object);
            Collection<DtoNotaVenta> collection = new ArrayList<>();
            collection.add(notaVenta);
            createReporte(collection, rutaServer, rutaReporte, formato, response);
        } else {
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

}
