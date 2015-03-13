package mx.com.villavicencio.system.cliente.reporte.reporte;

import java.util.ArrayList;
import java.util.Collection;
import javax.servlet.http.HttpServletResponse;
import mx.com.villavicencio.commons.exception.ApplicationException;
import mx.com.villavicencio.generics.report.GenericReport;
import mx.com.villavicencio.properties.PropertiesBean;
import mx.com.villavicencio.properties.Property;
import mx.com.villavicencio.system.cliente.dto.DtoCliente;
import mx.com.villavicencio.system.cliente.factory.ClienteFactory;
import mx.com.villavicencio.system.usuario.dto.DtoUsuario;

/**
 *
 * @author Gabriel J
 */
public class ReporteClientes extends GenericReport<DtoUsuario, DtoCliente> {

    @Override
    public void generarReporte(DtoUsuario user, DtoCliente object, String rutaServer, String rutaReporte, String formato, HttpServletResponse response) {
        if ((user != null) && (user.getIdUsuario() != 0)) {
            destino = "clientes";
            Collection<DtoCliente> collection = new ArrayList<>();
            collection.add(object);
            createReporte(collection, rutaServer, rutaReporte, formato, response);
        } else {
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public void generarReporte(DtoUsuario user, Collection<DtoCliente> object, String rutaServer, String rutaReporte, String formato, HttpServletResponse response) {
        if ((user != null) && (user.getIdUsuario() != 0)) {
            destino = "clientes";
            master = "master";
            DtoCliente cliente = ClienteFactory.newInstance();
            cliente.setClientes(object);
            Collection<DtoCliente> collection = new ArrayList<>();
            collection.add(cliente);
            createReporte(collection, rutaServer, rutaReporte, formato, response);
        } else {
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }
}
