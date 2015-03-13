package mx.com.villavicencio.system.cliente.dao;

import java.util.Collection;
import mx.com.villavicencio.generics.spring.dao.GenericDao;
import mx.com.villavicencio.system.cliente.dto.DtoCliente;

/**
 *
 * @author Gabriel J
 */
public interface ClienteDao extends GenericDao<DtoCliente> {

    Boolean verifyExistCliente(DtoCliente object);

    Collection<DtoCliente> getDataCp(DtoCliente object);

    DtoCliente insert(DtoCliente object);

    Collection<DtoCliente> findCollectionById(DtoCliente object);
}
