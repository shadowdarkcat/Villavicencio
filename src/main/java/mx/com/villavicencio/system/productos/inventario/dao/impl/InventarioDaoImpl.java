package mx.com.villavicencio.system.productos.inventario.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import mx.com.villavicencio.commons.exception.ApplicationException;
import mx.com.villavicencio.commons.messages.ApplicationMessages;
import mx.com.villavicencio.properties.PropertiesBean;
import mx.com.villavicencio.properties.Property;
import mx.com.villavicencio.system.productos.inventario.dao.InventarioDao;
import mx.com.villavicencio.system.productos.inventario.dao.sql.sql.SqlInventario;
import mx.com.villavicencio.system.productos.inventario.dao.sql.view.View;
import mx.com.villavicencio.system.productos.inventario.dto.DtoInventario;
import mx.com.villavicencio.system.productos.inventario.factory.InventarioFactory;
import mx.com.villavicencio.system.productos.productos.dao.sql.sql.SqlProducto;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 *
 * @author Gabriel J
 */
public class InventarioDaoImpl extends JdbcDaoSupport implements InventarioDao {

    @Override
    public Collection<DtoInventario> findAll() {
        ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO)
                + "\n" + InventarioDaoImpl.class.getSimpleName());
        throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
    }

    @Override
    public DtoInventario findById(DtoInventario object) {
        Object[] args = {object.getNombreProducto()};
        try {
            return getJdbcTemplate().queryForObject(View.VIEW_INVENTARIO, args, new InventarioRowMapper());
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND)
                    + "\n" + InventarioDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND));
        }
    }

    @Override
    public void ingresar(DtoInventario object) {
        ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO)
                + "\n" + InventarioDaoImpl.class.getSimpleName());
        throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
    }

    @Override
    public void modificar(DtoInventario object) {
        ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO)
                + "\n" + InventarioDaoImpl.class.getSimpleName());
        throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
    }

    @Override
    public void eliminar(DtoInventario object) {
        ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO)
                + "\n" + InventarioDaoImpl.class.getSimpleName());
        throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
    }

    private static class InventarioRowMapper implements RowMapper<DtoInventario> {

        @Override
        public DtoInventario mapRow(ResultSet rs, int i) throws SQLException {
            DtoInventario inventario = InventarioFactory.newInstance();
            inventario.setIdInventario(rs.getInt(SqlInventario.ID_INVENTARIO));
            inventario.setNombreProducto(rs.getString(SqlProducto.NOMBRE_PRODUCTO));
            inventario.setCantidadExistencia(rs.getInt(SqlInventario.CANTIDAD_EXISTENCIA));
            return inventario;
        }
    }
}
