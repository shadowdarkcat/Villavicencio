package mx.com.villavicencio.system.menu.bo;

import java.util.Collection;
import mx.com.villavicencio.system.menu.dto.MenuItem;
import mx.com.villavicencio.system.usuario.dto.DtoUsuario;

/**
 *
 * @author Gabriel J
 */
public interface MenuBo {

    Collection<MenuItem> getMenu(DtoUsuario user);

    Collection<MenuItem> getMenuPrivilegios(DtoUsuario user);

    String getConvercionMenu(DtoUsuario user);

    void insertMultiplesPrivilegios(DtoUsuario user, Collection<MenuItem> nuevoMenu, DtoUsuario object);

    void insertPrivilegio(DtoUsuario user, MenuItem menuItem);

    void deletePrivilegios(DtoUsuario user);
}
