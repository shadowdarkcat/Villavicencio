package mx.com.villavicencio.system.liquidacion.liquidacion.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Collection;
import mx.com.villavicencio.commons.exception.ApplicationException;
import mx.com.villavicencio.commons.jdbc.Jdbc;
import mx.com.villavicencio.commons.messages.ApplicationMessages;
import mx.com.villavicencio.generics.types.GenericTypes;
import mx.com.villavicencio.properties.PropertiesBean;
import mx.com.villavicencio.properties.Property;
import mx.com.villavicencio.system.liquidacion.liquidacion.dao.LiquidacionDao;
import mx.com.villavicencio.system.liquidacion.liquidacion.dao.sql.function.Function;
import mx.com.villavicencio.system.liquidacion.liquidacion.dao.sql.procedure.Procedure;
import mx.com.villavicencio.system.liquidacion.liquidacion.dao.sql.sql.SqlLiquidacion;
import mx.com.villavicencio.system.liquidacion.liquidacion.dao.sql.view.View;
import mx.com.villavicencio.system.liquidacion.liquidacion.dto.DtoLiquidacion;
import mx.com.villavicencio.system.liquidacion.liquidacion.factory.LiquidacionFactory;
import mx.com.villavicencio.system.venta.nota.dao.sql.sql.SqlNotaVenta;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 *
 * @author Gabriel J
 */
public class LiquidacionDaoImpl extends JdbcDaoSupport implements LiquidacionDao {

    @Override
    public Collection<DtoLiquidacion> findAll() {
        try {
            return getJdbcTemplate().query(View.VIEW_LIQUIDACIONES, new LiquidacionRowMapper());
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND)
                    + "\n" + LiquidacionDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND));
        }
    }

    @Override
    public DtoLiquidacion findById(DtoLiquidacion object) {
        Object[] args = {object.getIdLiquidacion()};
        try {
            return getJdbcTemplate().queryForObject(View.VIEW_LIQUIDACION, args, new LiquidacionRowMapper());
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND)
                    + "\n" + LiquidacionDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND));
        }
    }

    @Override
    public DtoLiquidacion insert(DtoLiquidacion object) {
        try {
            SimpleJdbcInsert simpleJdbcInsert = Jdbc.getSimpleJdbcInsert(getJdbcTemplate(), "vw_liquidacion");
            simpleJdbcInsert.execute(getParameterProcedure(object));
            SimpleJdbcCall simpleJdbcCall = Jdbc.getSimpleJdbcCallFunction(getJdbcTemplate(), Function.LAST_INDEX_LIQUIDACION);
            Integer executeFunction = simpleJdbcCall.executeFunction(Integer.class, GenericTypes.NONE);
            object.setIdLiquidacion(executeFunction);
            return object;
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_INSERT)
                    + "\n" + LiquidacionDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_INSERT));
        }
    }

    @Override
    public void ingresar(DtoLiquidacion object) {
        ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO)
                + "\n" + LiquidacionDaoImpl.class.getSimpleName());
        throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
    }

    @Override
    public void modificar(DtoLiquidacion object) {
        ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO)
                + "\n" + LiquidacionDaoImpl.class.getSimpleName());
        throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
    }

    @Override
    public void eliminar(DtoLiquidacion object) {
        ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO)
                + "\n" + LiquidacionDaoImpl.class.getSimpleName());
        throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
    }

    private MapSqlParameterSource getParameterProcedure(DtoLiquidacion object) {
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue(SqlLiquidacion.OPCION, object.getOpcion(), Types.INTEGER);
        source.addValue(SqlLiquidacion.ID_LIQUIDACION, object.getIdLiquidacion(), Types.INTEGER);
        source.addValue(SqlNotaVenta.FOLIO, object.getFolio(), Types.INTEGER);
        source.addValue(SqlLiquidacion.FECHA, object.getFecha(), Types.DATE);
        source.addValue(SqlLiquidacion.NOMBRE_VENDEDOR, object.getNombreVendedor(), Types.VARCHAR);
        source.addValue(SqlLiquidacion.OBSERVACIONES, object.getObservaciones(), Types.VARCHAR);
        return source;
    }

    private static class LiquidacionRowMapper implements RowMapper<DtoLiquidacion> {

        @Override
        public DtoLiquidacion mapRow(ResultSet rs, int i) throws SQLException {
            DtoLiquidacion liquidacion = LiquidacionFactory.newInstance();
            liquidacion.setIdLiquidacion(rs.getInt(SqlLiquidacion.ID_LIQUIDACION));
            String format = String.format("%04d", rs.getInt(SqlNotaVenta.FOLIO));
            liquidacion.setFolio(format);
            liquidacion.setFecha(rs.getDate(SqlLiquidacion.FECHA));
            liquidacion.setNombreVendedor(rs.getString(SqlLiquidacion.NOMBRE_VENDEDOR));
            liquidacion.setObservaciones(rs.getString(SqlLiquidacion.OBSERVACIONES));
            return liquidacion;
        }
    }
}
