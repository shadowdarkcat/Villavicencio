package mx.com.villavicencio.system.pedido.datos.dao;

import java.util.Collection;
import mx.com.villavicencio.generics.spring.dao.GenericDao;
import mx.com.villavicencio.system.pedido.datos.dto.DtoDatosPedido;

/**
 *
 * @author Gabriel J
 */
public interface DatosPedidoDao extends GenericDao<DtoDatosPedido> {

    Collection<DtoDatosPedido> findDatosById(DtoDatosPedido object);
}
