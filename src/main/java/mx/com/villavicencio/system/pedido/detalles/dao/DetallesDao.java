package mx.com.villavicencio.system.pedido.detalles.dao;

import mx.com.villavicencio.generics.spring.dao.GenericDao;
import mx.com.villavicencio.system.pedido.detalles.dto.DtoDetallePedido;

/**
 *
 * @author Gabriel J
 */

public interface DetallesDao extends GenericDao<DtoDetallePedido>{
    
    DtoDetallePedido insert(DtoDetallePedido object);
}
