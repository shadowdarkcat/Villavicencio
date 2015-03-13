package mx.com.villavicencio.system.usuario.factory;

import mx.com.villavicencio.system.usuario.dto.DtoUsuario;
import mx.com.villavicencio.utils.NumberUtils;
import mx.com.villavicencio.utils.StringUtils;

/**
 *
 * @author Gabriel J
 */
public class UsuarioFactory {

    public static DtoUsuario newInstance() {
        return new DtoUsuario();
    }

    public static DtoUsuario newInstance(Integer id) {
        DtoUsuario usuario = newInstance();
        usuario.setIdUsuario(id);
        return usuario;
    }

    public static DtoUsuario newInstance(String id) {
        if (!StringUtils.isReallyEmptyOrNull(id)) {
            return newInstance(NumberUtils.stringToNumber(id));
        } else {
            return null;
        }
    }
}
