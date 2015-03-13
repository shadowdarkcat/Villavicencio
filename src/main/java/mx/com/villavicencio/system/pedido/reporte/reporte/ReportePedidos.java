package mx.com.villavicencio.system.pedido.reporte.reporte;

import java.util.ArrayList;
import java.util.Collection;
import javax.servlet.http.HttpServletResponse;
import mx.com.villavicencio.commons.exception.ApplicationException;
import mx.com.villavicencio.generics.report.GenericReport;
import mx.com.villavicencio.properties.PropertiesBean;
import mx.com.villavicencio.properties.Property;
import mx.com.villavicencio.system.pedido.pedido.dto.DtoPedido;
import mx.com.villavicencio.system.pedido.pedido.factory.PedidoFactory;
import mx.com.villavicencio.system.usuario.dto.DtoUsuario;

/**
 *
 * @author Gabriel J
 */
public class ReportePedidos extends GenericReport<DtoUsuario, DtoPedido> {

    @Override
    public void generarReporte(DtoUsuario user, DtoPedido object, String rutaServer, String rutaReporte, String formato, HttpServletResponse response) {
        if ((user != null) && (user.getIdUsuario() != 0)) {
            destino = "pedidos";
            Collection<DtoPedido> collection = new ArrayList<>();
            collection.add(object);
            createReporte(collection, rutaServer, rutaReporte, formato, response);
        } else {
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public void generarReporte(DtoUsuario user, Collection<DtoPedido> object, String rutaServer, String rutaReporte, String formato, HttpServletResponse response) {
        if ((user != null) && (user.getIdUsuario() != 0)) {
            destino = "pedidos";
            master = "master";
            DtoPedido pedido = PedidoFactory.newInstance();
            pedido.setPedidos(object);
            Collection<DtoPedido> collection = new ArrayList<>();
            collection.add(pedido);
            createReporte(collection, rutaServer, rutaReporte, formato, response);
        } else {
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }
}
