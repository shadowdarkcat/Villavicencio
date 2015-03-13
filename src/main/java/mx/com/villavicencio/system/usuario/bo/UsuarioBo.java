package mx.com.villavicencio.system.usuario.bo;

import java.util.Collection;
import mx.com.villavicencio.system.usuario.dto.DtoUsuario;

/**
 *
 * @author Gabriel J
 */
public interface UsuarioBo {

    Collection<DtoUsuario> findAll(DtoUsuario user);

    DtoUsuario validar(DtoUsuario user);

    String getMenu(DtoUsuario user);

    Boolean verifyExistUsuario(DtoUsuario user, DtoUsuario object);

    DtoUsuario insert(DtoUsuario user, DtoUsuario object);

    void modificar(DtoUsuario user, DtoUsuario object);
    
    DtoUsuario findById(DtoUsuario user, DtoUsuario object);
}
