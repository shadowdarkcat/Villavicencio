package mx.com.villavicencio.system.productos.reporte.reporte;

import java.util.Collection;
import javax.servlet.http.HttpServletResponse;
import mx.com.villavicencio.commons.exception.ApplicationException;
import mx.com.villavicencio.commons.messages.ApplicationMessages;
import mx.com.villavicencio.generics.report.GenericReport;
import mx.com.villavicencio.properties.PropertiesBean;
import mx.com.villavicencio.properties.Property;
import mx.com.villavicencio.system.productos.productos.dto.DtoProducto;
import mx.com.villavicencio.system.usuario.dto.DtoUsuario;

/**
 *
 * @author Gabriel J
 */
public class ReporteProductos extends GenericReport<DtoUsuario, DtoProducto> {
    
    @Override
    public void generarReporte(DtoUsuario user, DtoProducto object, String rutaServer, String rutaReporte, String formato, HttpServletResponse response) {
        if ((user != null) && (user.getIdUsuario() != 0)) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
        } else {
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }
    
    @Override
    public void generarReporte(DtoUsuario user, Collection<DtoProducto> object, String rutaServer, String rutaReporte, String formato, HttpServletResponse response) {
        if ((user != null) && (user.getIdUsuario() != 0)) {
            destino = "productos";
            createReporte(object, rutaServer, rutaReporte, formato, response);
        } else {
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }
}
