package mx.com.villavicencio.system.venta.detalle.dao;

import java.util.Collection;
import mx.com.villavicencio.generics.spring.dao.GenericDao;
import mx.com.villavicencio.system.venta.detalle.dto.DtoDetalleNotaVenta;
/**
 *
 * @author Gabriel J
 */
public interface DetalleNotaVentaDao extends GenericDao<DtoDetalleNotaVenta>{

    Collection<DtoDetalleNotaVenta> findAll(DtoDetalleNotaVenta object);
}
