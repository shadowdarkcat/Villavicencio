package mx.com.villavicencio.system.vendedor.reporte.reporte;

import java.util.ArrayList;
import java.util.Collection;
import javax.servlet.http.HttpServletResponse;
import mx.com.villavicencio.commons.exception.ApplicationException;
import mx.com.villavicencio.generics.report.GenericReport;
import mx.com.villavicencio.properties.PropertiesBean;
import mx.com.villavicencio.properties.Property;
import mx.com.villavicencio.system.usuario.dto.DtoUsuario;
import mx.com.villavicencio.system.vendedor.dto.DtoVendedor;
import mx.com.villavicencio.system.vendedor.factory.VendedorFactory;

/**
 *
 * @author Gabriel J
 */
public class ReporteVendedores extends GenericReport<DtoUsuario, DtoVendedor> {

    @Override
    public void generarReporte(DtoUsuario user, DtoVendedor object, String rutaServer, String rutaReporte, String formato, HttpServletResponse response) {
        if ((user != null) && (user.getIdUsuario() != 0)) {
            destino = "vendedores";
            Collection<DtoVendedor> collection = new ArrayList<>();
            collection.add(object);
            createReporte(collection, rutaServer, rutaReporte, formato, response);
        } else {
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public void generarReporte(DtoUsuario user, Collection<DtoVendedor> object, String rutaServer, String rutaReporte, String formato, HttpServletResponse response) {
        if ((user != null) && (user.getIdUsuario() != 0)) {
            destino = "vendedores";
            master = "master";
            DtoVendedor vendedor = VendedorFactory.newInstance();
            vendedor.setVendedores(object);
            Collection<DtoVendedor> collection = new ArrayList<>();
            collection.add(vendedor);
            createReporte(collection, rutaServer, rutaReporte, formato, response);
        } else {
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }
}
