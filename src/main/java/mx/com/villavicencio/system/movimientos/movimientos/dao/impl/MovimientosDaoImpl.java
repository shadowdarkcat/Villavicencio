package mx.com.villavicencio.system.movimientos.movimientos.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Collection;
import mx.com.villavicencio.commons.exception.ApplicationException;
import mx.com.villavicencio.commons.jdbc.Jdbc;
import mx.com.villavicencio.commons.messages.ApplicationMessages;
import mx.com.villavicencio.properties.PropertiesBean;
import mx.com.villavicencio.properties.Property;
import mx.com.villavicencio.system.credito.credito.dao.sql.sql.SqlCredito;
import mx.com.villavicencio.system.credito.credito.factory.CreditoFactory;
import mx.com.villavicencio.system.movimientos.abonos.dao.sql.sql.SqlAbonos;
import mx.com.villavicencio.system.movimientos.abonos.factory.AbonosFactory;
import mx.com.villavicencio.system.movimientos.cargos.dao.sql.sql.SqlCargos;
import mx.com.villavicencio.system.movimientos.cargos.factory.CargosFactory;
import mx.com.villavicencio.system.movimientos.movimientos.dao.MovimientosDao;
import mx.com.villavicencio.system.movimientos.movimientos.dao.sql.procedure.Procedure;
import mx.com.villavicencio.system.movimientos.movimientos.dao.sql.sql.SqlMovimientos;
import mx.com.villavicencio.system.movimientos.movimientos.dao.sql.view.View;
import mx.com.villavicencio.system.movimientos.movimientos.dto.DtoMovimientos;
import mx.com.villavicencio.system.movimientos.movimientos.factory.MovimientosFactory;
import mx.com.villavicencio.system.venta.devoluciones.devoluciones.dao.sql.sql.SqlDevoluciones;
import mx.com.villavicencio.system.venta.devoluciones.devoluciones.factory.DevolucionesFactory;
import mx.com.villavicencio.system.venta.nota.dao.sql.sql.SqlNotaVenta;
import mx.com.villavicencio.system.venta.nota.factory.NotaVentaFactory;
import mx.com.villavicencio.utils.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 *
 * @author Gabriel J
 */
public class MovimientosDaoImpl extends JdbcDaoSupport implements MovimientosDao {

    @Override
    public Collection<DtoMovimientos> findAll() {
        ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO) + "\n" + MovimientosDaoImpl.class.getSimpleName());
        throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
    }

    @Override
    public Collection<DtoMovimientos> findAll(DtoMovimientos object) {
        Object[] args = {object.getNotaVenta().getIdNotaVenta()};
        try {
            return getJdbcTemplate().query(View.VIEW_MOVIMIENTOS, args, new MovimientosRowMapper());
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND)
                    + "\n" + MovimientosDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND));
        }
    }

    @Override
    public DtoMovimientos findById(DtoMovimientos object) {
        Object[] args = {object.getNotaVenta().getIdNotaVenta()};
        try {
            if (!StringUtils.isReallyEmptyOrNull(StringUtils.objectToString(args[0]))) {
                return getJdbcTemplate().queryForObject(View.VIEW_MOVIMIENTO, args, new MovimientosRowMapper());
            }
            return MovimientosFactory.newInstance();
        } catch (IncorrectResultSizeDataAccessException ex) {
            return MovimientosFactory.newInstance();
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND)
                    + "\n" + MovimientosDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND));
        }
    }

    @Override
    public void ingresar(DtoMovimientos object) {
       try {
            SimpleJdbcCall simpleJdbcCall = Jdbc.getSimpleJdbcCall(getJdbcTemplate(), Procedure.PROCEDURE_MOVIMIENTOS);
            simpleJdbcCall.execute(getParameterProcedure(object));
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_INSERT)
                    + "\n" + MovimientosDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_INSERT));
        }
    }

    @Override
    public void modificar(DtoMovimientos object) {
        ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO) + "\n" + MovimientosDaoImpl.class.getSimpleName());
        throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
    }

    @Override
    public void eliminar(DtoMovimientos object) {
        ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO) + "\n" + MovimientosDaoImpl.class.getSimpleName());
        throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
    }

     private SqlParameterSource getParameterProcedure(DtoMovimientos object) {
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue(SqlMovimientos.OPCION, object.getOpcion(), Types.INTEGER);
        source.addValue(SqlCargos.ID_CARGOS, object.getCargos().getIdCargos(), Types.INTEGER);
        source.addValue(SqlAbonos.ID_ABONOS, object.getAbonos().getIdAbonos(), Types.INTEGER);
        source.addValue(SqlCredito.ID_CREDITO, object.getCredito().getIdCredito(), Types.INTEGER);
        source.addValue(SqlNotaVenta.ID_NOTA_VENTA, object.getNotaVenta().getIdNotaVenta(), Types.INTEGER);
        source.addValue(SqlDevoluciones.ID_DEVOLUCIONES, object.getDevoluciones().getIdDevoluciones(), Types.INTEGER);
        return source;
    }
     
    private static class MovimientosRowMapper implements RowMapper<DtoMovimientos> {

        @Override
        public DtoMovimientos mapRow(ResultSet rs, int i) throws SQLException {
            DtoMovimientos movimientos = MovimientosFactory.newInstance();
            movimientos.setCargos(
                    CargosFactory.newInstance(
                            rs.getInt(SqlCargos.ID_CARGOS)
                    )
            );

            movimientos.setAbonos(
                    AbonosFactory.newInstance(
                            rs.getInt(SqlAbonos.ID_ABONOS)
                    )
            );

            movimientos.setCredito(
                    CreditoFactory.newInstance(rs.getInt(SqlCredito.ID_CREDITO))
            );

            movimientos.setNotaVenta(
                    NotaVentaFactory.newInstance(rs.getInt(SqlNotaVenta.ID_NOTA_VENTA))
            );

            movimientos.setDevoluciones(
                    DevolucionesFactory.newInstance(rs.getInt(SqlDevoluciones.ID_DEVOLUCIONES))
            );

            return movimientos;
        }
    }
}
