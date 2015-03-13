package mx.com.villavicencio.system.liquidacion.liquidacion.dao;

import mx.com.villavicencio.generics.spring.dao.GenericDao;
import mx.com.villavicencio.system.liquidacion.liquidacion.dto.DtoLiquidacion;

/**
 *
 * @author Gabriel J
 */
public interface LiquidacionDao extends GenericDao<DtoLiquidacion>{

    DtoLiquidacion insert(DtoLiquidacion object);
}
