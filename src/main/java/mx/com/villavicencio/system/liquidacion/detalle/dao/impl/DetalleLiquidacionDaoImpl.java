package mx.com.villavicencio.system.liquidacion.detalle.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Collection;
import mx.com.villavicencio.commons.exception.ApplicationException;
import mx.com.villavicencio.commons.jdbc.Jdbc;
import mx.com.villavicencio.commons.messages.ApplicationMessages;
import mx.com.villavicencio.properties.PropertiesBean;
import mx.com.villavicencio.properties.Property;
import mx.com.villavicencio.system.liquidacion.detalle.dao.DetalleLiquidacionDao;
import mx.com.villavicencio.system.liquidacion.detalle.dao.sql.procedure.Procedure;
import mx.com.villavicencio.system.liquidacion.detalle.dao.sql.sql.SqlDetalleLiquidacion;
import mx.com.villavicencio.system.liquidacion.detalle.dao.sql.view.View;
import mx.com.villavicencio.system.liquidacion.detalle.dto.DtoDetalleLiquidacion;
import mx.com.villavicencio.system.liquidacion.detalle.factory.DetalleLiquidacionFactory;
import mx.com.villavicencio.system.liquidacion.liquidacion.dao.sql.sql.SqlLiquidacion;
import mx.com.villavicencio.system.productos.productos.dao.sql.sql.SqlProducto;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 *
 * @author Gabriel J
 */
public class DetalleLiquidacionDaoImpl extends JdbcDaoSupport implements DetalleLiquidacionDao {

    @Override
    public Collection<DtoDetalleLiquidacion> findAll() {
        ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO)
                + "\n" + DetalleLiquidacionDaoImpl.class.getSimpleName());
        throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
    }

    @Override
    public Collection<DtoDetalleLiquidacion> findAll(DtoDetalleLiquidacion object) {
        Object[] args = {object.getIdLiquidacion()};
        try {
            return getJdbcTemplate().query(View.VIEW_DETALLE_LIQUIDACION, args, new DetalleLiquidacionRowMapper());
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND)
                    + "\n" + DetalleLiquidacionDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND));
        }
    }

    @Override
    public DtoDetalleLiquidacion findById(DtoDetalleLiquidacion object) {
        ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND)
                + "\n" + DetalleLiquidacionDaoImpl.class.getSimpleName());
        throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND));
    }

    @Override
    public void ingresar(DtoDetalleLiquidacion object) {
        try {
            SimpleJdbcInsert simpleJdbcInsert = Jdbc.getSimpleJdbcInsert(getJdbcTemplate(), "vw_DetalleLiquidacion");
            simpleJdbcInsert.execute(getProcedureParameter(object));
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_INSERT)
                    + "\n" + DetalleLiquidacionDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_INSERT));
        }
    }

    @Override
    public void modificar(DtoDetalleLiquidacion object) {
        ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO)
                + "\n" + DetalleLiquidacionDaoImpl.class.getSimpleName());
        throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
    }

    @Override
    public void eliminar(DtoDetalleLiquidacion object) {
        ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO)
                + "\n" + DetalleLiquidacionDaoImpl.class.getSimpleName());
        throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
    }

    private SqlParameterSource getProcedureParameter(DtoDetalleLiquidacion object) {
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue(SqlDetalleLiquidacion.OPCION, object.getOpcion(), Types.INTEGER);
        source.addValue(SqlDetalleLiquidacion.ID_DETALLE_LIQUIDACION, object.getIdDetalleLiquidacion(), Types.INTEGER);
        source.addValue(SqlProducto.NOMBRE_PRODUCTO, object.getNombreProducto(), Types.VARCHAR);
        source.addValue(SqlDetalleLiquidacion.PIEZAS_SALIDA, object.getPiezasSalida(), Types.INTEGER);
        source.addValue(SqlDetalleLiquidacion.KILOS_SALIDA, object.getKilosSalida(), Types.DECIMAL);
        source.addValue(SqlDetalleLiquidacion.PIEZAS_DEVOLUCION, object.getPiezasDevolucion(), Types.INTEGER);
        source.addValue(SqlDetalleLiquidacion.KILOS_DEVOLUCION, object.getKilosDevolucion(), Types.DECIMAL);
        source.addValue(SqlDetalleLiquidacion.KILOS_TOTAL, object.getKilosTotal(), Types.DECIMAL);
        source.addValue(SqlProducto.COSTO_UNITARIO, object.getCostoUnitario(), Types.DECIMAL);
        source.addValue(SqlDetalleLiquidacion.SUBTOTAL, object.getSubtotal(), Types.DECIMAL);
        source.addValue(SqlDetalleLiquidacion.OBSERVACIONES, object.getObservaciones(), Types.VARCHAR);
        source.addValue(SqlLiquidacion.ID_LIQUIDACION, object.getIdLiquidacion(), Types.INTEGER);
        return source;
    }

    private static class DetalleLiquidacionRowMapper implements RowMapper<DtoDetalleLiquidacion> {

        @Override
        public DtoDetalleLiquidacion mapRow(ResultSet rs, int i) throws SQLException {
            DtoDetalleLiquidacion detalle = DetalleLiquidacionFactory.newInstance();
            detalle.setIdDetalleLiquidacion(rs.getInt(SqlDetalleLiquidacion.ID_DETALLE_LIQUIDACION));
            detalle.setNombreProducto(rs.getString(SqlProducto.NOMBRE_PRODUCTO));
            detalle.setPiezasSalida(rs.getInt(SqlDetalleLiquidacion.PIEZAS_SALIDA));
            detalle.setKilosSalida(rs.getBigDecimal(SqlDetalleLiquidacion.KILOS_SALIDA));
            detalle.setPiezasDevolucion(rs.getInt(SqlDetalleLiquidacion.PIEZAS_DEVOLUCION));
            detalle.setKilosDevolucion(rs.getBigDecimal(SqlDetalleLiquidacion.KILOS_DEVOLUCION));
            detalle.setKilosTotal(rs.getBigDecimal(SqlDetalleLiquidacion.KILOS_TOTAL));
            detalle.setCostoUnitario(rs.getBigDecimal(SqlProducto.COSTO_UNITARIO));
            detalle.setSubtotal(rs.getBigDecimal(SqlDetalleLiquidacion.SUBTOTAL));
            detalle.setObservaciones(rs.getString(SqlDetalleLiquidacion.OBSERVACIONES));
            return detalle;
        }
    }
}
