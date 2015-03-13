package mx.com.villavicencio.system.menu.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Collection;
import java.util.Iterator;
import mx.com.villavicencio.commons.exception.ApplicationException;
import mx.com.villavicencio.commons.jdbc.Jdbc;
import mx.com.villavicencio.commons.messages.ApplicationMessages;
import mx.com.villavicencio.generics.types.GenericTypes;
import mx.com.villavicencio.properties.PropertiesBean;
import mx.com.villavicencio.properties.Property;
import mx.com.villavicencio.system.menu.dao.MenuDao;
import mx.com.villavicencio.system.menu.dao.sql.sql.SqlMenu;
import mx.com.villavicencio.system.menu.dao.sql.procedure.Procedure;
import mx.com.villavicencio.system.menu.dto.MenuItem;
import mx.com.villavicencio.system.usuario.dto.DtoUsuario;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 *
 * @author Gabriel J
 */
public class MenuDaoImpl extends JdbcDaoSupport implements MenuDao {

    @Override
    public Collection<MenuItem> getMenu(DtoUsuario user) {
        Collection<MenuItem> menu = null;

        try {
            Object[] params = {0};
            menu = getJdbcTemplate().query(SqlMenu.MENU, params, new MenuRowMapper());
            for (MenuItem item : menu) {
                item.setChildItems(getChildItems(item));
            }
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_MENU)
                    + "\n" + MenuDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_MENU), ex);
        }
        return menu;
    }

    @Override
    public Collection<MenuItem> getMenuPrivilegios(DtoUsuario user) {
        Collection<MenuItem> menu = null;
        Collection<Integer[]> privilegiosIds;
        try {
            Object[] params = {user.getIdUsuario()};
            privilegiosIds = getJdbcTemplate().query(SqlMenu.PRIVILEGIOS, params, new MenuPrivilegioRowMapper());
            menu = getMenu(user);
            menu = filtrarMenuUsuarios(menu, privilegiosIds);

        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_MENU_PRIVILEGIOS)
                    + "\n" + MenuDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_MENU_PRIVILEGIOS), ex);
        }
        return menu;
    }

    @Override
    public Collection<MenuItem> getMenuAlmacen(DtoUsuario user) {
        Collection<MenuItem> menu = null;

        try {
            Object[] params = {0};
            menu = getJdbcTemplate().query(SqlMenu.MENU_ALMACEN, params, new MenuRowMapper());
            for (MenuItem item : menu) {
                item.setChildItems(getChildItemsAlmacen(item));
            }
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_MENU)
                    + "\n" + MenuDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_MENU), ex);
        }
        return menu;
    }

    @Override
    public Collection<MenuItem> getMenuPrivilegiosAlmacen(DtoUsuario user) {
        Collection<MenuItem> menu = null;
        Collection<Integer[]> privilegiosIds;
        try {
            Object[] params = {user.getIdUsuario()};
            privilegiosIds = getJdbcTemplate().query(SqlMenu.PRIVILEGIOS_ALMACEN, params, new MenuPrivilegioRowMapper());
            menu = getMenuAlmacen(user);
            menu = filtrarMenuUsuarios(menu, privilegiosIds);

        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_MENU_PRIVILEGIOS)
                    + "\n" + MenuDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_MENU_PRIVILEGIOS), ex);
        }
        return menu;
    }

    @Override
    public void insertMultiplesPrivilegios(DtoUsuario usuario, Collection<MenuItem> nuevoMenu) {
        deletePrivilegios(usuario);
        usuario.setOpcion(GenericTypes.INSERT);
        for (MenuItem menuItem : nuevoMenu) {
            insertPrivilegio(usuario, menuItem);
        }
    }

    @Override
    public void insertPrivilegio(DtoUsuario usuario, MenuItem menuItem) {
        try {
            SimpleJdbcCall simpleJdbcCall = Jdbc.getSimpleJdbcCall(getJdbcTemplate(), Procedure.PROCEDURE_PRIVILEGIOS);
            simpleJdbcCall.execute(getProcedureParameter(menuItem, usuario));
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_PRIVILEGIOS)
                    + "\n" + MenuDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_PRIVILEGIOS), ex);
        }
    }

    @Override
    public void deletePrivilegios(DtoUsuario usuario) {
        try {
            SimpleJdbcCall simpleJdbcCall = Jdbc.getSimpleJdbcCall(getJdbcTemplate(), Procedure.PROCEDURE_PRIVILEGIOS);
            simpleJdbcCall.execute(getProcedureParameter(usuario));
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_DELETE_PRIVILEGIOS)
                    + "\n" + MenuDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_DELETE_PRIVILEGIOS), ex);
        }
    }

    @Override
    public void insertMultiplesPrivilegiosAlmacen(DtoUsuario usuario, Collection<MenuItem> nuevoMenu) {
        deletePrivilegiosAlmacen(usuario);
        usuario.setOpcion(GenericTypes.INSERT);
        for (MenuItem menuItem : nuevoMenu) {
            insertPrivilegioAlmacen(usuario, menuItem);
        }
    }

    @Override
    public void insertPrivilegioAlmacen(DtoUsuario usuario, MenuItem menuItem) {
        try {
            SimpleJdbcCall simpleJdbcCall = Jdbc.getSimpleJdbcCall(getJdbcTemplate(), Procedure.PROCEDURE_PRIVILEGIOS_ALMACEN);
            simpleJdbcCall.execute(getProcedureParameter(menuItem, usuario));
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_PRIVILEGIOS)
                    + "\n" + MenuDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_PRIVILEGIOS), ex);
        }
    }

    @Override
    public void deletePrivilegiosAlmacen(DtoUsuario usuario) {
        try {
            SimpleJdbcCall simpleJdbcCall = Jdbc.getSimpleJdbcCall(getJdbcTemplate(), Procedure.PROCEDURE_PRIVILEGIOS_ALMACEN);
            simpleJdbcCall.execute(getProcedureParameter(usuario));
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_DELETE_PRIVILEGIOS)
                    + "\n" + MenuDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_DELETE_PRIVILEGIOS), ex);
        }
    }

    private Collection<MenuItem> getChildItems(MenuItem item) {
        Collection<MenuItem> menu = null;

        try {
            Object[] params = {item.getId()};
            menu = getJdbcTemplate().query(SqlMenu.MENU, params, new MenuRowMapper());
            for (MenuItem itemS : menu) {
                itemS.setParentItem(item);
                itemS.setChildItems(getChildItems(itemS));
            }
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_ROW_MENU)
                    + "\n" + MenuDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_ROW_MENU), ex);
        }
        return menu;
    }

    private Collection<MenuItem> getChildItemsAlmacen(MenuItem item) {
        Collection<MenuItem> menu = null;

        try {
            Object[] params = {item.getId()};
            menu = getJdbcTemplate().query(SqlMenu.MENU_ALMACEN, params, new MenuRowMapper());
            for (MenuItem itemS : menu) {
                itemS.setParentItem(item);
                itemS.setChildItems(getChildItemsAlmacen(itemS));
            }
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_ROW_MENU)
                    + "\n" + MenuDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_ROW_MENU), ex);
        }
        return menu;
    }

    private Collection<MenuItem> filtrarMenuUsuarios(Collection<MenuItem> menu, Collection<Integer[]> privilegiosIds) {
        Iterator<MenuItem> recorrerMenux = menu.iterator();
        while (recorrerMenux.hasNext()) {
            MenuItem menuItem = recorrerMenux.next();

            boolean bandera = true;
            for (Integer[] recorridoId : privilegiosIds) {
                if (recorridoId[0].intValue() == menuItem.getId() && bandera) {
                    bandera = false;
                }
            }
            if (bandera) {
                recorrerMenux.remove();
            } else {
                if (menuItem.getChildItems() != null && menuItem.getChildItems().size() > 0) {
                    menuItem.setChildItems(filtrarMenuUsuarios(menuItem.getChildItems(), privilegiosIds));
                }
            }
        }
        return menu;
    }

    private MapSqlParameterSource getProcedureParameter(MenuItem object, DtoUsuario object1) {
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue(SqlMenu.OPCION, object1.getOpcion(), Types.INTEGER);
        source.addValue(SqlMenu.ID_MENU, object.getId(), Types.INTEGER);
        source.addValue(SqlMenu.ID_USUARIO, object1.getIdUsuario(), Types.INTEGER);
        return source;
    }

    private MapSqlParameterSource getProcedureParameter(DtoUsuario object) {
        object.setOpcion(GenericTypes.MODIFY);
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue(SqlMenu.OPCION, object.getOpcion(), Types.INTEGER);
        source.addValue(SqlMenu.ID_MENU, GenericTypes.ZERO, Types.INTEGER);
        source.addValue(SqlMenu.ID_USUARIO, object.getIdUsuario(), Types.INTEGER);
        return source;
    }

    static class MenuRowMapper implements RowMapper<MenuItem> {

        @Override
        public MenuItem mapRow(ResultSet rs, int i) throws SQLException {
            MenuItem menuItem = new MenuItem();
            menuItem.setId(rs.getInt(SqlMenu.ID));
            menuItem.setLink(rs.getString(SqlMenu.LINK));
            menuItem.setLeyenda(rs.getString(SqlMenu.TEXTO));
            menuItem.setToolTip(rs.getString(SqlMenu.DESCRIPCION));
            menuItem.setTarget(rs.getString(SqlMenu.TARGET));
            return menuItem;
        }

    }

    static class MenuPrivilegioRowMapper implements RowMapper<Integer[]> {

        @Override
        public Integer[] mapRow(ResultSet rs, int i) throws SQLException {
            Integer[] privilegios = new Integer[2];
            privilegios[0] = rs.getInt(SqlMenu.ID_MENU);
            privilegios[1] = rs.getInt(SqlMenu.ID_USUARIO);
            return privilegios;
        }

    }

}
