package mx.com.villavicencio.system.venta.detalle.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import mx.com.villavicencio.commons.exception.ApplicationException;
import mx.com.villavicencio.commons.messages.ApplicationMessages;
import mx.com.villavicencio.properties.PropertiesBean;
import mx.com.villavicencio.properties.Property;
import mx.com.villavicencio.system.productos.productos.dao.sql.sql.SqlProducto;
import mx.com.villavicencio.system.venta.detalle.dao.DetalleNotaVentaDao;
import mx.com.villavicencio.system.venta.detalle.dao.sql.sql.SqlDetalleNotaVenta;
import mx.com.villavicencio.system.venta.detalle.dao.sql.view.View;
import mx.com.villavicencio.system.venta.detalle.dto.DtoDetalleNotaVenta;
import mx.com.villavicencio.system.venta.detalle.factory.DetalleNotaVentaFactory;
import mx.com.villavicencio.system.venta.nota.dao.sql.sql.SqlNotaVenta;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 *
 * @author Gabriel J
 */
public class DetalleNotaVentaDaoImpl extends JdbcDaoSupport implements DetalleNotaVentaDao {

    @Override
    public Collection<DtoDetalleNotaVenta> findAll() {
        ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO)
                + "\n" + DetalleNotaVentaDaoImpl.class.getSimpleName());
        throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
    }

    @Override
    public Collection<DtoDetalleNotaVenta> findAll(DtoDetalleNotaVenta object) {
        Object[] args = {object.getIdNotaVenta()};
        try {
            return getJdbcTemplate().query(View.VIEW_DETALLE_NOTA_VENTA, args, new DetalleNotaVentaRowMapper());
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND)
                    + "\n" + DetalleNotaVentaDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND));
        }
    }

    @Override
    public DtoDetalleNotaVenta findById(DtoDetalleNotaVenta object) {
        ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO)
                + "\n" + DetalleNotaVentaDaoImpl.class.getSimpleName());
        throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
    }

    @Override
    public void ingresar(DtoDetalleNotaVenta object) {
       ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO)
                + "\n" + DetalleNotaVentaDaoImpl.class.getSimpleName());
        throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
    }

    @Override
    public void modificar(DtoDetalleNotaVenta object) {
        ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO)
                + "\n" + DetalleNotaVentaDaoImpl.class.getSimpleName());
        throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
    }

    @Override
    public void eliminar(DtoDetalleNotaVenta object) {
        ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO)
                + "\n" + DetalleNotaVentaDaoImpl.class.getSimpleName());
        throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
    }  

    private static class DetalleNotaVentaRowMapper implements RowMapper<DtoDetalleNotaVenta> {

        @Override
        public DtoDetalleNotaVenta mapRow(ResultSet rs, int i) throws SQLException {
            DtoDetalleNotaVenta detalleNotaVenta = DetalleNotaVentaFactory.newInstance();
            detalleNotaVenta.setIdNotaVenta(rs.getInt(SqlNotaVenta.ID_NOTA_VENTA));
            detalleNotaVenta.setCantidadPiezas(rs.getInt(SqlDetalleNotaVenta.CANTIDAD));
            detalleNotaVenta.setNombreProducto(rs.getString(SqlProducto.NOMBRE_PRODUCTO));
            detalleNotaVenta.setCantidadKilos(rs.getBigDecimal(SqlDetalleNotaVenta.CANTIDAD_KILOS));
            detalleNotaVenta.setCostoUnitario(rs.getBigDecimal(SqlProducto.COSTO_UNITARIO));
            detalleNotaVenta.setSubTotal(rs.getBigDecimal(SqlDetalleNotaVenta.SUBTOTAL));
            return detalleNotaVenta;
        }
    }
}
