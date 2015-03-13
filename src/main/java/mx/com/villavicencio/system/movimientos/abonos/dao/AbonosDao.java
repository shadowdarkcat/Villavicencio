package mx.com.villavicencio.system.movimientos.abonos.dao;

import mx.com.villavicencio.generics.spring.dao.GenericDao;
import mx.com.villavicencio.system.movimientos.abonos.dto.DtoAbonos;

/**
 *
 * @author Gabriel J
 */
public interface AbonosDao extends GenericDao<DtoAbonos> {

    DtoAbonos insert(DtoAbonos object);
}
