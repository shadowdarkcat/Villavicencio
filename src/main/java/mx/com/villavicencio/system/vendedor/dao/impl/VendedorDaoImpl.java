package mx.com.villavicencio.system.vendedor.dao.impl;

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
import mx.com.villavicencio.system.vendedor.dao.VendedorDao;
import mx.com.villavicencio.system.vendedor.dao.sql.sql.SqlVendedor;
import mx.com.villavicencio.system.vendedor.dao.sql.function.Function;
import mx.com.villavicencio.system.vendedor.dao.sql.procedure.Procedure;
import mx.com.villavicencio.system.vendedor.dao.sql.view.View;
import mx.com.villavicencio.system.vendedor.dto.DtoVendedor;
import mx.com.villavicencio.system.vendedor.factory.VendedorFactory;
import mx.com.villavicencio.utils.StringUtils;
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
public class VendedorDaoImpl extends JdbcDaoSupport implements VendedorDao {

    @Override
    public Boolean verifyExistVendedor(DtoVendedor object) {
        try {
            SimpleJdbcCall simpleJdbcCall = Jdbc.getSimpleJdbcCallFunction(getJdbcTemplate(), Function.VERIFY_EXIST_VENDEDOR);
            SqlParameterSource sqlParameterSource = getParameterFunction(object);
            Integer executeFunction = simpleJdbcCall.executeFunction(Integer.class, sqlParameterSource);
            return executeFunction != 0;
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_VERIFICAR)
                    + VendedorDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_VERIFICAR), ex);
        }
    }

    @Override
    public Collection<DtoVendedor> findAll() {
        try {
            return getJdbcTemplate().query(View.VENDEDORES, new VendedoresRowMapper());
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND)
                    + VendedorDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND), ex);
        }
    }

    @Override
    public DtoVendedor findById(DtoVendedor object) {
        Object[] args = {object.getIdVendedor()};
        try {
            return getJdbcTemplate().queryForObject(View.VENDEDOR, args, new VendedoresRowMapper());
        } catch (EmptyResultDataAccessException ex) {
            return object;
        } catch (IncorrectResultSizeDataAccessException ex) {
            return object;
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND)
                    + VendedorDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND), ex);
        }
    }

    @Override
    public void ingresar(DtoVendedor object) {
        ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO)
                + VendedorDaoImpl.class.getSimpleName());
        throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
    }

    @Override
    public DtoVendedor insert(DtoVendedor object) {
        try {
            SimpleJdbcCall simpleJdbcCall = Jdbc.getSimpleJdbcCall(getJdbcTemplate(), Procedure.PROCEDURE_VENDEDOR);
            simpleJdbcCall.execute(getParameterProcedure(object));
            simpleJdbcCall = Jdbc.getSimpleJdbcCallFunction(getJdbcTemplate(), Function.LAST_INDEX_VENDEDOR);
            Integer executeFunction = simpleJdbcCall.executeFunction(Integer.class, GenericTypes.NONE);
            object.setIdVendedor(executeFunction);
            return object;
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_INSERT)
                    + VendedorDaoImpl.class
                    .getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_INSERT));
        } catch (Exception ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_INSERT)
                    + VendedorDaoImpl.class
                    .getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_INSERT));
        }
    }

    @Override
    public void modificar(DtoVendedor object) {
        try {
            SimpleJdbcCall simpleJdbcCall = Jdbc.getSimpleJdbcCall(getJdbcTemplate(), Procedure.PROCEDURE_VENDEDOR);
            simpleJdbcCall.execute(getParameterProcedure(object));

        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_MODIFY)
                    + VendedorDaoImpl.class
                    .getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_MODIFY), ex);
        }
    }

    @Override
    public void eliminar(DtoVendedor object) {
        try {
            SimpleJdbcCall simpleJdbcCall = Jdbc.getSimpleJdbcCall(getJdbcTemplate(), Procedure.PROCEDURE_VENDEDOR);
            simpleJdbcCall.execute(getParameterProcedure(object));

        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_DELETE)
                    + VendedorDaoImpl.class
                    .getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_DELETE), ex);
        }
    }

    @Override
    public Collection<DtoVendedor> getDataCp(DtoVendedor object) {
        Object[] args = null;
        if ((!StringUtils.isReallyEmptyOrNull(object.getColonia())) && (object.getColonia() != "null")) {
            if (StringUtils.isReallyEmptyOrNull(object.getDelegacion())) {
                object.setDelegacion("");
            }
            if (StringUtils.isReallyEmptyOrNull(object.getMunicipio())) {
                object.setMunicipio("");
            }
            args = new Object[]{object.getCp(), object.getColonia(), object.getDelegacion(), object.getMunicipio(), object.getEstado()};
        }
        try {
            if (args != null) {
                return getJdbcTemplate().query(View.CP, args, new CodigoPostalRowMapper());
            } else {
                return getJdbcTemplate().query(View.CPS, new CodigoPostalRowMapper());

            }
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND)
                    + VendedorDaoImpl.class
                    .getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND), ex);
        }
    }

    private SqlParameterSource getParameterFunction(DtoVendedor object) {
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue(SqlVendedor.NOMBRE + "S", object.getNombre(), Types.VARCHAR);
        source.addValue(SqlVendedor.APELLIDO_PATERNO + "S", object.getApellidoPaterno(), Types.VARCHAR);
        source.addValue(SqlVendedor.APELLIDO_MATERNO + "S", object.getApellidoMaterno(), Types.VARCHAR);
        return source;
    }

    private SqlParameterSource getParameterProcedure(DtoVendedor object) {
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue(SqlVendedor.OPCION, object.getOpcion(), Types.INTEGER);
        source.addValue(SqlVendedor.ID_VENDEDOR, object.getIdVendedor(), Types.INTEGER);
        source.addValue(SqlVendedor.NOMBRE, object.getNombre(), Types.VARCHAR);
        source.addValue(SqlVendedor.APELLIDO_PATERNO, object.getApellidoPaterno(), Types.VARCHAR);
        source.addValue(SqlVendedor.APELLIDO_MATERNO, object.getApellidoMaterno(), Types.VARCHAR);
        source.addValue(SqlVendedor.RFC, object.getRfc(), Types.VARCHAR);
        source.addValue(SqlVendedor.CALLE, object.getCalle(), Types.VARCHAR);
        source.addValue(SqlVendedor.CP, object.getCp(), Types.VARCHAR);
        source.addValue(SqlVendedor.COLONIA, object.getColonia(), Types.VARCHAR);
        source.addValue(SqlVendedor.DELEGAGION, object.getDelegacion(), Types.VARCHAR);
        source.addValue(SqlVendedor.MUNICIPIO, object.getMunicipio(), Types.VARCHAR);
        source.addValue(SqlVendedor.ESTADO, object.getEstado(), Types.VARCHAR);
        source.addValue(SqlVendedor.CIUDAD, object.getCiudad(), Types.VARCHAR);
        source.addValue(SqlVendedor.NO_EXTERIOR, object.getNoExterior(), Types.VARCHAR);
        source.addValue(SqlVendedor.NO_INTERIOR, object.getNoInterior(), Types.VARCHAR);
        source.addValue(SqlVendedor.TELEFONO1, object.getTelefono1(), Types.VARCHAR);
        source.addValue(SqlVendedor.TELEFONO2, object.getTelefono2(), Types.VARCHAR);
        source.addValue(SqlVendedor.TELEFONO3, object.getTelefono3(), Types.VARCHAR);
        source.addValue(SqlVendedor.CORREO1, object.getCorreo1(), Types.VARCHAR);
        source.addValue(SqlVendedor.CORREO2, object.getCorreo2(), Types.VARCHAR);
        source.addValue(SqlVendedor.CORREO3, object.getCorreo3(), Types.VARCHAR);
        source.addValue(SqlVendedor.COMISION, object.getComision(), Types.DECIMAL);
        source.addValue(SqlVendedor.VISIBLE, object.getVisible(), Types.BOOLEAN);
        source.addValue(SqlVendedor.EXTERNO, object.getExterno(), Types.BOOLEAN);
        return source;

    }

    private static class VendedoresRowMapper implements RowMapper<DtoVendedor> {

        @Override
        public DtoVendedor mapRow(ResultSet rs, int i) throws SQLException {

            DtoVendedor vendedor = VendedorFactory.newInstance();

            vendedor.setIdVendedor(rs.getInt(SqlVendedor.ID_VENDEDOR));
            vendedor.setNombre(rs.getString(SqlVendedor.NOMBRE));
            vendedor.setApellidoPaterno(rs.getString(SqlVendedor.APELLIDO_PATERNO));
            vendedor.setApellidoMaterno(rs.getString(SqlVendedor.APELLIDO_MATERNO));
            vendedor.setRfc(rs.getString(SqlVendedor.RFC));
            vendedor.setCalle(rs.getString(SqlVendedor.CALLE));
            vendedor.setCp(rs.getString(SqlVendedor.CP));
            vendedor.setColonia(rs.getString(SqlVendedor.COLONIA));
            vendedor.setDelegacion(rs.getString(SqlVendedor.DELEGAGION));
            vendedor.setMunicipio(rs.getString(SqlVendedor.MUNICIPIO));
            vendedor.setEstado(rs.getString(SqlVendedor.ESTADO));
            vendedor.setCiudad(rs.getString(SqlVendedor.CIUDAD));
            vendedor.setNoExterior(rs.getString(SqlVendedor.NO_EXTERIOR));
            vendedor.setNoInterior(rs.getString(SqlVendedor.NO_INTERIOR));
            vendedor.setTelefono1(rs.getString(SqlVendedor.TELEFONO1));
            vendedor.setTelefono2(rs.getString(SqlVendedor.TELEFONO2));
            vendedor.setTelefono3(rs.getString(SqlVendedor.TELEFONO3));
            vendedor.setCorreo1(rs.getString(SqlVendedor.CORREO1));
            vendedor.setCorreo2(rs.getString(SqlVendedor.CORREO2));
            vendedor.setCorreo3(rs.getString(SqlVendedor.CORREO3));
            vendedor.setComision(rs.getBigDecimal(SqlVendedor.COMISION));
            vendedor.setVisible(rs.getBoolean(SqlVendedor.VISIBLE));
            vendedor.setExterno(rs.getBoolean(SqlVendedor.EXTERNO));
            return vendedor;
        }
    }

    private static class CodigoPostalRowMapper implements RowMapper<DtoVendedor> {

        @Override
        public DtoVendedor mapRow(ResultSet rs, int i) throws SQLException {

            DtoVendedor vendedor = VendedorFactory.newInstance();
            vendedor.setCp(rs.getString(SqlVendedor.CP));
            vendedor.setColonia(rs.getString(SqlVendedor.COLONIA));
            vendedor.setDelegacion(rs.getString(SqlVendedor.DELEGAGION));
            vendedor.setMunicipio(rs.getString(SqlVendedor.MUNICIPIO));
            vendedor.setEstado(rs.getString(SqlVendedor.ESTADO));
            vendedor.setCiudad(rs.getString(SqlVendedor.CIUDAD));
            return vendedor;

        }
    }
}
