package mx.com.villavicencio.system.credito.credito.dao.impl;

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
import mx.com.villavicencio.system.credito.credito.dao.CreditoDao;
import mx.com.villavicencio.system.credito.credito.dao.sql.sql.SqlCredito;
import mx.com.villavicencio.system.credito.credito.dao.sql.function.Function;
import mx.com.villavicencio.system.credito.credito.dao.sql.procedure.Procedure;
import mx.com.villavicencio.system.credito.credito.dao.sql.view.View;
import mx.com.villavicencio.system.credito.credito.dto.DtoCredito;
import mx.com.villavicencio.system.credito.credito.factory.CreditoFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
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
public class CreditoDaoImpl extends JdbcDaoSupport implements CreditoDao {

    @Override
    public Collection<DtoCredito> findAll() {
        ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO)
                + CreditoDaoImpl.class.getSimpleName());
        throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
    }

    @Override
    public DtoCredito findById(DtoCredito object) {
        Object[] args = {object.getIdCredito()};
        try {
            return getJdbcTemplate().queryForObject(View.CREDITO, args, new CreditoRowMapper());
        } catch (EmptyResultDataAccessException ex) {
            return CreditoFactory.newInstance();
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND)
                    + CreditoDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND));
        }
    }

    @Override
    public DtoCredito findCreditoByIdVendedor(DtoCredito object, Integer id) {
        Object[] args = {id};
        try {
            return getJdbcTemplate().queryForObject(View.DATOS_CREDITO, args, new DatosCreditoRowMapper());
        } catch (EmptyResultDataAccessException ex) {
            return CreditoFactory.newInstance();
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND)
                    + CreditoDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND));
        }
    }

    @Override
    public DtoCredito findCreditoByIdCliente(DtoCredito object, Integer id) {
        Object[] args = {id};
        try {
            return getJdbcTemplate().queryForObject(View.DATOS_CREDITO_CLIENTE, args, new DatosCreditoRowMapper());
        } catch (EmptyResultDataAccessException ex) {
            return CreditoFactory.newInstance();
        } catch (IncorrectResultSizeDataAccessException ex) {
            return CreditoFactory.newInstance();
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND)
                    + CreditoDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND));
        }
    }

    @Override
    public void ingresar(DtoCredito object) {
        ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO)
                + CreditoDaoImpl.class.getSimpleName());
        throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
    }

    @Override
    public DtoCredito insert(DtoCredito object) {
        try {
            SimpleJdbcCall simpleJdbcCall = Jdbc.getSimpleJdbcCall(getJdbcTemplate(), Procedure.PROCEDURE_CREDITO);
            simpleJdbcCall.execute(getParameterProcedure(object));
            simpleJdbcCall = Jdbc.getSimpleJdbcCallFunction(getJdbcTemplate(), Function.LAST_ID_CREDITO);
            Integer executeFunction = simpleJdbcCall.executeFunction(Integer.class, GenericTypes.NONE);
            object.setIdCredito(executeFunction);
            return object;
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_INSERT)
                    + CreditoDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_INSERT));
        }
    }

    @Override
    public void modificar(DtoCredito object) {
        try {
            SimpleJdbcCall simpleJdbcCall = Jdbc.getSimpleJdbcCall(getJdbcTemplate(), Procedure.PROCEDURE_CREDITO);
            simpleJdbcCall.execute(getParameterProcedure(object));
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_MODIFY)
                    + CreditoDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_MODIFY));
        }
    }

    @Override
    public void eliminar(DtoCredito object) {
        ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO)
                + CreditoDaoImpl.class.getSimpleName());
        throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
    }

    private SqlParameterSource getParameterProcedure(DtoCredito object) {
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue(SqlCredito.OPCION, object.getOpcion(), Types.INTEGER);
        source.addValue(SqlCredito.ID_CREDITO, object.getIdCredito(), Types.INTEGER);
        source.addValue(SqlCredito.TIPO_CREDITO, object.getTipoCredito(), Types.VARCHAR);
        source.addValue(SqlCredito.FECHA_REGISTRO, object.getFechaRegistro(), Types.DATE);
        source.addValue(SqlCredito.FECHA_PAGO, object.getFechaPago(), Types.DATE);
        source.addValue(SqlCredito.FOLIO_NOTA, object.getFolioNota(), Types.VARCHAR);
        source.addValue(SqlCredito.CANTIDAD_MONETARIA, object.getCantidadMonetaria(), Types.DECIMAL);
        source.addValue(SqlCredito.ESTATUS_CREDITO, object.getEstatusCredito(), Types.VARCHAR);
        return source;
    }

    private static class CreditoRowMapper implements RowMapper<DtoCredito> {

        @Override
        public DtoCredito mapRow(ResultSet rs, int i) throws SQLException {
            DtoCredito credito = CreditoFactory.newInstance();
            credito.setIdCredito(rs.getInt(SqlCredito.ID_CREDITO));
            credito.setTipoCredito(rs.getString(SqlCredito.TIPO_CREDITO));
            credito.setFechaRegistro(rs.getDate(SqlCredito.FECHA_REGISTRO));
            credito.setFechaPago(rs.getDate(SqlCredito.FECHA_PAGO));
            credito.setFolioNota(rs.getString(SqlCredito.FOLIO_NOTA));
            credito.setCantidadMonetaria(rs.getBigDecimal(SqlCredito.CANTIDAD_MONETARIA));
            credito.setEstatusCredito(rs.getString(SqlCredito.ESTATUS_CREDITO));
            return credito;
        }
    }

    private static class DatosCreditoRowMapper implements RowMapper<DtoCredito> {

        @Override
        public DtoCredito mapRow(ResultSet rs, int i) throws SQLException {
            DtoCredito credito = CreditoFactory.newInstance();
            credito.setIdCredito(rs.getInt(SqlCredito.ID_CREDITO));
            return credito;
        }
    }
}
