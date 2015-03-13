package mx.com.villavicencio.system.pedido.pedido.dao;

import mx.com.villavicencio.generics.spring.dao.GenericDao;
import mx.com.villavicencio.system.pedido.pedido.dto.DtoPedido;

/**
 *
 * @author Gabriel J
 */
public interface PedidoDao extends GenericDao<DtoPedido> {

    DtoPedido insert(DtoPedido object);

    String createFolio();

    void deleteProducto(DtoPedido object);
}
