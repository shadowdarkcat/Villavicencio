package mx.com.villavicencio.system.vendedor.bo;

import java.util.Collection;
import mx.com.villavicencio.generics.spring.bo.GenericBo;
import mx.com.villavicencio.system.usuario.dto.DtoUsuario;
import mx.com.villavicencio.system.vendedor.dto.DtoVendedor;

/**
 *
 * @author Gabriel J
 */
public interface VendedorBo extends GenericBo<DtoUsuario, DtoVendedor> {

    Boolean verifyExistVendedor(DtoUsuario user, DtoVendedor object);
    
    Collection<DtoVendedor>getDataCp(DtoUsuario user, DtoVendedor object);
}
