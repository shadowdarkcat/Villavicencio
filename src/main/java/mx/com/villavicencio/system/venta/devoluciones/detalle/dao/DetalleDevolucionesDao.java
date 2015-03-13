package mx.com.villavicencio.system.venta.devoluciones.detalle.dao;

import java.util.Collection;
import mx.com.villavicencio.generics.spring.dao.GenericDao;
import mx.com.villavicencio.system.venta.devoluciones.detalle.dto.DtoDetalleDevoluciones;

/**
 *
 * @author Gabriel J
 */
public interface DetalleDevolucionesDao extends GenericDao<DtoDetalleDevoluciones> {

    Collection<DtoDetalleDevoluciones> findAll(DtoDetalleDevoluciones object);
}
