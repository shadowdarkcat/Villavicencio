package mx.com.villavicencio.system.movimientos.abonos.dao.impl;

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
import mx.com.villavicencio.system.movimientos.abonos.dao.AbonosDao;
import mx.com.villavicencio.system.movimientos.abonos.dao.sql.function.Function;
import mx.com.villavicencio.system.movimientos.abonos.dao.sql.procedure.Procedure;
import mx.com.villavicencio.system.movimientos.abonos.dao.sql.sql.SqlAbonos;
import mx.com.villavicencio.system.movimientos.abonos.dao.sql.view.View;
import mx.com.villavicencio.system.movimientos.abonos.dto.DtoAbonos;
import mx.com.villavicencio.system.movimientos.abonos.factory.AbonosFactory;
import mx.com.villavicencio.system.movimientos.bancos.dao.sql.sql.SqlBancos;
import mx.com.villavicencio.system.movimientos.bancos.factory.BancosFactory;
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
public class AbonosDaoImpl extends JdbcDaoSupport implements AbonosDao {

    @Override
    public Collection<DtoAbonos> findAll() {
        ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO)
                + "\n" + AbonosDaoImpl.class.getSimpleName());
        throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
    }

    @Override
    public DtoAbonos findById(DtoAbonos object) {
        DtoAbonos abonos;
        Object[] args = {object.getIdAbonos()};
        try {
            return getJdbcTemplate().queryForObject(View.VIEW_ABONOS, args, new AbonosRowMapper());
        } catch (IncorrectResultSizeDataAccessException ex) {
            abonos = AbonosFactory.newInstance();
            abonos.setBancos(BancosFactory.newInstance());
            return abonos;
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND)
                    + "\n" + AbonosDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND));
        }
    }

    @Override
    public void ingresar(DtoAbonos object) {
        ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO)
                + "\n" + AbonosDaoImpl.class.getSimpleName());
        throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
    }

    @Override
    public DtoAbonos insert(DtoAbonos object) {
        SimpleJdbcCall simpleJdbcCall = Jdbc.getSimpleJdbcCall(getJdbcTemplate(), Procedure.PROCEDURE_ABONOS);
        simpleJdbcCall.execute(getParameterProcedure(object));
        simpleJdbcCall = Jdbc.getSimpleJdbcCallFunction(getJdbcTemplate(), Function.LAST_INDEX_ABONO);
        Integer executeFunction = simpleJdbcCall.executeFunction(Integer.class, GenericTypes.NONE);
        object.setIdAbonos(executeFunction);
        return object;
    }

    @Override
    public void modificar(DtoAbonos object) {
        ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO)
                + "\n" + AbonosDaoImpl.class.getSimpleName());
        throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
    }

    @Override
    public void eliminar(DtoAbonos object) {
        ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO)
                + "\n" + AbonosDaoImpl.class.getSimpleName());
        throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
    }

    private SqlParameterSource getParameterProcedure(DtoAbonos object) {
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue(SqlAbonos.OPCION, object.getOpcion(), Types.INTEGER);
        source.addValue(SqlAbonos.ID_ABONOS, object.getIdAbonos(), Types.INTEGER);
        source.addValue(SqlAbonos.FECHA_CAPTURA, object.getFechaCaptura(), Types.DATE);
        source.addValue(SqlAbonos.ABONO, object.getAbono(), Types.DECIMAL);
        source.addValue(SqlAbonos.ABONO_ANTERIOR, object.getAbono(), Types.DECIMAL);
        source.addValue(SqlAbonos.TIPO_PAGO, object.getTipoPago(), Types.VARCHAR);
        source.addValue(SqlBancos.ID_BANCOS, object.getBancos().getIdBancos(), Types.INTEGER);
        return source;
    }

    private static class AbonosRowMapper implements RowMapper<DtoAbonos> {

        @Override
        public DtoAbonos mapRow(ResultSet rs, int i) throws SQLException {
            DtoAbonos abonos = AbonosFactory.newInstance();
            abonos.setIdAbonos(rs.getInt(SqlAbonos.ID_ABONOS));
            abonos.setFechaCaptura(rs.getDate(SqlAbonos.FECHA_CAPTURA));
            abonos.setAbono(rs.getBigDecimal(SqlAbonos.ABONO));
            abonos.setAbonoAnterior(rs.getBigDecimal(SqlAbonos.ABONO_ANTERIOR));
            abonos.setTipoPago(rs.getString(SqlAbonos.TIPO_PAGO));
            abonos.setBancos(
                    BancosFactory.newInstance(rs.getInt(SqlBancos.ID_BANCOS))
            );
            return abonos;
        }
    }
}
