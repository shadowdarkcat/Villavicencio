package mx.com.villavicencio.system.movimientos.movimientos.dao;

import java.util.Collection;
import mx.com.villavicencio.generics.spring.dao.GenericDao;
import mx.com.villavicencio.system.movimientos.movimientos.dto.DtoMovimientos;

/**
 *
 * @author Gabriel J
 */
public interface MovimientosDao extends GenericDao<DtoMovimientos>{

    Collection<DtoMovimientos> findAll(DtoMovimientos object);
}
