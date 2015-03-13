package mx.com.villavicencio.system.venta.devoluciones.devoluciones.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import mx.com.villavicencio.commons.exception.ApplicationException;
import mx.com.villavicencio.commons.messages.ApplicationMessages;
import mx.com.villavicencio.properties.PropertiesBean;
import mx.com.villavicencio.properties.Property;
import mx.com.villavicencio.system.venta.devoluciones.devoluciones.dao.DevolucionesDao;
import mx.com.villavicencio.system.venta.devoluciones.devoluciones.dao.sql.sql.SqlDevoluciones;
import mx.com.villavicencio.system.venta.devoluciones.devoluciones.dao.sql.view.View;
import mx.com.villavicencio.system.venta.devoluciones.devoluciones.dto.DtoDevoluciones;
import mx.com.villavicencio.system.venta.devoluciones.devoluciones.factory.DevolucionesFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 *
 * @author Gabriel J
 */
public class DevolucionesDaoImpl extends JdbcDaoSupport implements DevolucionesDao {

    @Override
    public Collection<DtoDevoluciones> findAll() {
        ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO)
                + "\n" + DevolucionesDaoImpl.class.getSimpleName());
        throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
    }

    @Override
    public DtoDevoluciones findById(DtoDevoluciones object) {
        Object[] args = {object.getIdDevoluciones()};
        try {
            return getJdbcTemplate().queryForObject(View.VIEW_DEVOLUCIONES, args, new DevolucionesRowMapper());
        } catch (EmptyResultDataAccessException ex) {
            return DevolucionesFactory.newInstance();
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND)
                    + "\n" + DevolucionesDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND));
        }
    }

    @Override
    public void ingresar(DtoDevoluciones object) {
        ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO)
                + "\n" + DevolucionesDaoImpl.class.getSimpleName());
        throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
    }

    @Override
    public void modificar(DtoDevoluciones object) {
        ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO)
                + "\n" + DevolucionesDaoImpl.class.getSimpleName());
        throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
    }

    @Override
    public void eliminar(DtoDevoluciones object) {
        ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO)
                + "\n" + DevolucionesDaoImpl.class.getSimpleName());
        throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
    }

    private static class DevolucionesRowMapper implements RowMapper<DtoDevoluciones> {

        @Override
        public DtoDevoluciones mapRow(ResultSet rs, int i) throws SQLException {
            DtoDevoluciones devoluciones = DevolucionesFactory.newInstance();
            devoluciones.setIdDevoluciones(rs.getInt(SqlDevoluciones.ID_DEVOLUCIONES));
            devoluciones.setFechaDevolucion(rs.getDate(SqlDevoluciones.FECHA_DEVOLUCION));
            devoluciones.setObservaciones(rs.getString(SqlDevoluciones.OBSERVACIONES));
            return devoluciones;
        }
    }
}
