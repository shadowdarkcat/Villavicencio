package mx.com.villavicencio.system.venta.devoluciones.detalle.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import mx.com.villavicencio.commons.exception.ApplicationException;
import mx.com.villavicencio.commons.messages.ApplicationMessages;
import mx.com.villavicencio.properties.PropertiesBean;
import mx.com.villavicencio.properties.Property;
import mx.com.villavicencio.system.venta.devoluciones.detalle.dao.DetalleDevolucionesDao;
import mx.com.villavicencio.system.venta.devoluciones.detalle.dao.sql.sql.SqlDetalleDevoluciones;
import mx.com.villavicencio.system.venta.devoluciones.detalle.dao.sql.view.View;
import mx.com.villavicencio.system.venta.devoluciones.detalle.dto.DtoDetalleDevoluciones;
import mx.com.villavicencio.system.venta.devoluciones.detalle.factory.DetalleDevolucionesFactory;
import mx.com.villavicencio.system.venta.devoluciones.devoluciones.dao.sql.sql.SqlDevoluciones;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 *
 * @author Gabriel J
 */
public class DetalleDevolucionesDaoImpl extends JdbcDaoSupport implements DetalleDevolucionesDao {

    @Override
    public Collection<DtoDetalleDevoluciones> findAll() {
        ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO)
                + "\n" + DetalleDevolucionesDaoImpl.class.getSimpleName());
        throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));

    }

    @Override
    public Collection<DtoDetalleDevoluciones> findAll(DtoDetalleDevoluciones object) {
        Object[] args = {object.getIdDevoluciones()};
        try {
            return getJdbcTemplate().query(View.VIEW_DETALLE_DEVOLUCION, args, new DetalleDevolucionesRowMapper());
        } catch (EmptyResultDataAccessException ex) {
            return new ArrayList<>();
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND)
                    + "\n" + DetalleDevolucionesDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND));
        }

    }

    @Override
    public DtoDetalleDevoluciones findById(DtoDetalleDevoluciones object) {
        ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO)
                + "\n" + DetalleDevolucionesDaoImpl.class.getSimpleName());
        throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
    }

    @Override
    public void ingresar(DtoDetalleDevoluciones object) {
        ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO)
                + "\n" + DetalleDevolucionesDaoImpl.class.getSimpleName());
        throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
    }

    @Override
    public void modificar(DtoDetalleDevoluciones object) {
        ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO)
                + "\n" + DetalleDevolucionesDaoImpl.class.getSimpleName());
        throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
    }

    @Override
    public void eliminar(DtoDetalleDevoluciones object) {
        ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO)
                + "\n" + DetalleDevolucionesDaoImpl.class.getSimpleName());
        throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
    }

    private static class DetalleDevolucionesRowMapper implements RowMapper<DtoDetalleDevoluciones> {

        @Override
        public DtoDetalleDevoluciones mapRow(ResultSet rs, int i) throws SQLException {
            DtoDetalleDevoluciones detalleDevoluciones = DetalleDevolucionesFactory.newInstance();
            detalleDevoluciones.setIdDevoluciones(rs.getInt(SqlDevoluciones.ID_DEVOLUCIONES));
            detalleDevoluciones.setNombreProducto(rs.getString(SqlDetalleDevoluciones.NOMBRE_PRODUCTO));
            detalleDevoluciones.setCantidadPiezas(rs.getInt(SqlDetalleDevoluciones.CANTIDAD));
            detalleDevoluciones.setCantidadKilos(rs.getBigDecimal(SqlDetalleDevoluciones.PESO_KILOS));
            detalleDevoluciones.setCosto(rs.getBigDecimal(SqlDetalleDevoluciones.COSTO_UNITARIO));
            detalleDevoluciones.setSubtotal(rs.getBigDecimal(SqlDetalleDevoluciones.SUBTOTAL));
            return detalleDevoluciones;
        }
    }
}
