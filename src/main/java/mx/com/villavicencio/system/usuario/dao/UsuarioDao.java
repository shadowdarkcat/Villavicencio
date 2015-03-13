package mx.com.villavicencio.system.usuario.dao;

import mx.com.villavicencio.generics.spring.dao.GenericDao;
import mx.com.villavicencio.system.usuario.dto.DtoUsuario;

/**
 *
 * @author Gabriel J
 */
public interface UsuarioDao extends GenericDao<DtoUsuario> {

    DtoUsuario validateUsuario(DtoUsuario user);

    Boolean verifyExistUsuario(DtoUsuario user);

    DtoUsuario insert(DtoUsuario user);
}
