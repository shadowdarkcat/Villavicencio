package mx.com.villavicencio.system.liquidacion.detalle.dao;

import java.util.Collection;
import mx.com.villavicencio.generics.spring.dao.GenericDao;
import mx.com.villavicencio.system.liquidacion.detalle.dto.DtoDetalleLiquidacion;

/**
 *
 * @author Gabriel J
 */
public interface DetalleLiquidacionDao extends GenericDao<DtoDetalleLiquidacion> {

    Collection<DtoDetalleLiquidacion> findAll(DtoDetalleLiquidacion object);
}
