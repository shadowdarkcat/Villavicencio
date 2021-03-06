package mx.com.villavicencio.system.venta.nota.bo;

import java.util.Collection;
import mx.com.villavicencio.generics.spring.bo.GenericBo;
import mx.com.villavicencio.system.pedido.pedido.dto.DtoPedido;
import mx.com.villavicencio.system.usuario.dto.DtoUsuario;
import mx.com.villavicencio.system.venta.nota.dto.DtoNotaVenta;

/**
 *
 * @author Gabriel J
 */
public interface NotaVentaBo extends GenericBo<DtoUsuario, DtoNotaVenta> {

    DtoNotaVenta findByIdPedido(DtoUsuario user, DtoPedido object);

    DtoNotaVenta findNotaVentaById(DtoUsuario user, DtoNotaVenta object);

    Collection<DtoNotaVenta> findAllReport(DtoUsuario user);

    DtoNotaVenta findNotaVentaReportById(DtoUsuario user, DtoNotaVenta object);
}
