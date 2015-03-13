package mx.com.villavicencio.system.venta.nota.dao;

import mx.com.villavicencio.generics.spring.dao.GenericDao;
import mx.com.villavicencio.system.pedido.pedido.dto.DtoPedido;
import mx.com.villavicencio.system.venta.nota.dto.DtoNotaVenta;

/**
 *
 * @author Gabriel J
 */
public interface NotaVentaDao extends GenericDao<DtoNotaVenta>{

    DtoNotaVenta findByIdPedido(DtoPedido object);
}
