package mx.com.villavicencio.system.vendedor.dao;

import java.util.Collection;
import mx.com.villavicencio.generics.spring.dao.GenericDao;
import mx.com.villavicencio.system.vendedor.dto.DtoVendedor;

/**
 *
 * @author Gabriel J
 */
public interface VendedorDao extends GenericDao<DtoVendedor> {

    Boolean verifyExistVendedor(DtoVendedor object);

    Collection<DtoVendedor> getDataCp(DtoVendedor object);
    
    DtoVendedor insert (DtoVendedor object);
}
