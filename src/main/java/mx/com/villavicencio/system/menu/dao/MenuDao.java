package mx.com.villavicencio.system.menu.dao;

import java.util.Collection;
import mx.com.villavicencio.system.menu.dto.MenuItem;
import mx.com.villavicencio.system.usuario.dto.DtoUsuario;

/**
 *
 * @author Gabriel J
 */
public interface MenuDao {

    Collection<MenuItem> getMenu(DtoUsuario user);

    Collection<MenuItem> getMenuPrivilegios(DtoUsuario user);

    Collection<MenuItem> getMenuAlmacen(DtoUsuario user);

    Collection<MenuItem> getMenuPrivilegiosAlmacen(DtoUsuario user);

    void insertMultiplesPrivilegios(DtoUsuario usuario, Collection<MenuItem> nuevoMenu);

    void insertPrivilegio(DtoUsuario usuario, MenuItem menuItem);

    void deletePrivilegios(DtoUsuario usuario);

    void insertMultiplesPrivilegiosAlmacen(DtoUsuario usuario, Collection<MenuItem> nuevoMenu);

    void insertPrivilegioAlmacen(DtoUsuario usuario, MenuItem menuItem);

    void deletePrivilegiosAlmacen(DtoUsuario usuario);
}
