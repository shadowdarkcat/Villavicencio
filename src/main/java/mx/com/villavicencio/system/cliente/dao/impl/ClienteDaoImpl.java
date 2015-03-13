package mx.com.villavicencio.system.cliente.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import mx.com.villavicencio.commons.exception.ApplicationException;
import mx.com.villavicencio.commons.jdbc.Jdbc;
import mx.com.villavicencio.commons.messages.ApplicationMessages;
import mx.com.villavicencio.generics.types.GenericTypes;
import mx.com.villavicencio.properties.PropertiesBean;
import mx.com.villavicencio.properties.Property;
import mx.com.villavicencio.system.cliente.dao.ClienteDao;
import mx.com.villavicencio.system.cliente.dao.sql.sql.SqlCliente;
import mx.com.villavicencio.system.cliente.dao.sql.function.Function;
import mx.com.villavicencio.system.cliente.dao.sql.procedure.Procedure;
import mx.com.villavicencio.system.cliente.dao.sql.view.View;
import mx.com.villavicencio.system.cliente.dto.DtoCliente;
import mx.com.villavicencio.system.cliente.factory.ClienteFactory;
import mx.com.villavicencio.system.credito.credito.dto.DtoCredito;
import mx.com.villavicencio.system.credito.credito.factory.CreditoFactory;
import mx.com.villavicencio.system.usuario.dto.DtoUsuario;
import mx.com.villavicencio.system.vendedor.dao.sql.sql.SqlVendedor;
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
public class ClienteDaoImpl extends JdbcDaoSupport implements ClienteDao {

    @Override
    public Collection<DtoCliente> findAll() {
        try {
            return getJdbcTemplate().query(View.CLIENTES, new ClientesRowMapper());
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND)
                    + ClienteDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND), ex);
        }
    }

    @Override
    public DtoCliente findById(DtoCliente object) {
        Object[] args = {object.getIdCliente()};
        try {
            return getJdbcTemplate().queryForObject(View.CLIENTE, args, new ClientesRowMapper());
        } catch (EmptyResultDataAccessException ex) {
            return object;
        } catch (IncorrectResultSizeDataAccessException ex) {
            return object;
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND)
                    + ClienteDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND), ex);
        }
    }

    @Override
    public Collection<DtoCliente> findCollectionById(DtoCliente object) {
        Object[] args = {object.getIdCliente()};
        try {
            return getJdbcTemplate().query(View.CLIENTE_VENDEDOR, args, new ClientesRowMapper());
        } catch (EmptyResultDataAccessException ex) {
            return new ArrayList<>();
        } catch (IncorrectResultSizeDataAccessException ex) {
            return new ArrayList<>();
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND)
                    + ClienteDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND), ex);
        }
    }

    @Override
    public void ingresar(DtoCliente object) {
        ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO)
                + ClienteDaoImpl.class.getSimpleName());
        throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
    }

    @Override
    public DtoCliente insert(DtoCliente object) {
        try {
            SimpleJdbcCall simpleJdbcCall = Jdbc.getSimpleJdbcCall(getJdbcTemplate(), Procedure.PROCEDURE_CLIENTE);
            simpleJdbcCall.execute(getParametersProcedure(object));
            simpleJdbcCall = Jdbc.getSimpleJdbcCallFunction(getJdbcTemplate(), Function.LAST_INDEX_CLIENTE);
            Integer executeFunction = simpleJdbcCall.executeFunction(Integer.class, GenericTypes.NONE);
            object.setIdCliente(executeFunction);
            return object;
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_INSERT)
                    + ClienteDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_INSERT));
        }
    }

    @Override
    public void modificar(DtoCliente object) {
        try {
            SimpleJdbcCall simpleJdbcCall = Jdbc.getSimpleJdbcCall(getJdbcTemplate(), Procedure.PROCEDURE_CLIENTE);
            simpleJdbcCall.execute(getParametersProcedure(object));
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_MODIFY)
                    + ClienteDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_MODIFY), ex);
        }
    }

    @Override
    public void eliminar(DtoCliente object) {
        try {
            SimpleJdbcCall simpleJdbcCall = Jdbc.getSimpleJdbcCall(getJdbcTemplate(), Procedure.PROCEDURE_CLIENTE);
            simpleJdbcCall.execute(getParametersProcedure(object));
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_DELETE)
                    + ClienteDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_DELETE), ex);
        }
    }

    @Override
    public Boolean verifyExistCliente(DtoCliente object) {
        try {
            SimpleJdbcCall simpleJdbcCall = Jdbc.getSimpleJdbcCallFunction(getJdbcTemplate(), Function.VERIFY_EXIST_CLIENTE);
            Integer executeFunction = simpleJdbcCall.executeFunction(Integer.class, getParameterFunction(object));
            return executeFunction != 0;
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND)
                    + ClienteDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND), ex);
        }
    }

    @Override
    public Collection<DtoCliente> getDataCp(DtoCliente object) {
        Object[] args = null;
        if ((!StringUtils.isReallyEmptyOrNull(object.getColonia())) && (object.getColonia() != "null")) {
            if (StringUtils.isReallyEmptyOrNull(object.getDelegacion())) {
                object.setDelegacion("");
            }
            if (StringUtils.isReallyEmptyOrNull(object.getMunicipio())) {
                object.setMunicipio("");
            }
            args = new Object[]{object.getCodigoPostal(), object.getColonia(), object.getDelegacion(), object.getMunicipio(), object.getEstado()};
        }
        try {
            if (args != null) {
                return getJdbcTemplate().query(View.CP, args, new CodigoPostalRowMapper());
            } else {
                return getJdbcTemplate().query(View.CPS, new CodigoPostalRowMapper());

            }
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND)
                    + ClienteDaoImpl.class
                    .getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND), ex);
        }
    }

    private SqlParameterSource getParametersProcedure(DtoCliente object) {
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue(SqlCliente.OPCION, object.getOpcion(), Types.INTEGER);
        source.addValue(SqlCliente.ID_CLIENTE, object.getIdCliente(), Types.INTEGER);
        source.addValue(SqlCliente.EMPRESA, object.getEmpresa(), Types.VARCHAR);
        source.addValue(SqlCliente.RAZON_SOCIAL, object.getRazonSocial(), Types.VARCHAR);
        source.addValue(SqlCliente.RFC, object.getRfc(), Types.VARCHAR);
        source.addValue(SqlCliente.CORREO_EMPRESA, object.getCorreoEmpresa(), Types.VARCHAR);
        source.addValue(SqlCliente.SITIO_WEB, object.getSitioWeb(), Types.VARCHAR);
        source.addValue(SqlCliente.CALLE, object.getCalle(), Types.VARCHAR);
        source.addValue(SqlCliente.CP, object.getCodigoPostal(), Types.VARCHAR);
        source.addValue(SqlCliente.COLONIA, object.getColonia(), Types.VARCHAR);
        source.addValue(SqlCliente.DELEGACION, object.getDelegacion(), Types.VARCHAR);
        source.addValue(SqlCliente.MUNICIPIO, object.getMunicipio(), Types.VARCHAR);
        source.addValue(SqlCliente.ESTADO, object.getEstado(), Types.VARCHAR);
        source.addValue(SqlCliente.CIUDAD, object.getCiudad(), Types.VARCHAR);
        source.addValue(SqlCliente.NO_EXTERIOR, object.getNoExterior(), Types.VARCHAR);
        source.addValue(SqlCliente.NO_INTERIOR, object.getNoInterior(), Types.VARCHAR);
        source.addValue(SqlCliente.NOMBRE, object.getNombre(), Types.VARCHAR);
        source.addValue(SqlCliente.APELLIDO_PATERNO, object.getApellidoPaterno(), Types.VARCHAR);
        source.addValue(SqlCliente.APELLIDO_MATERNO, object.getApellidoMaterno(), Types.VARCHAR);
        source.addValue(SqlCliente.TELEFONO1, object.getTelefono1(), Types.VARCHAR);
        source.addValue(SqlCliente.TELEFONO2, object.getTelefono2(), Types.VARCHAR);
        source.addValue(SqlCliente.TELEFONO3, object.getTelefono3(), Types.VARCHAR);
        source.addValue(SqlCliente.CORREO1, object.getCorreo1(), Types.VARCHAR);
        source.addValue(SqlCliente.CORREO2, object.getCorreo2(), Types.VARCHAR);
        source.addValue(SqlCliente.CORREO3, object.getCorreo3(), Types.VARCHAR);
        source.addValue(SqlCliente.TIPO_CREDITO, object.getCredito().getTipoCredito(), Types.VARCHAR);
        source.addValue(SqlCliente.CANTIDAD_MONETARIA, object.getCredito().getCantidadMonetaria(), Types.DECIMAL);
        source.addValue(SqlCliente.CONTACTO1, object.getContacto1(), Types.VARCHAR);
        source.addValue(SqlCliente.CONTACTO2, object.getContacto2(), Types.VARCHAR);
        source.addValue(SqlCliente.CONTACTO3, object.getContacto3(), Types.VARCHAR);
        source.addValue(SqlCliente.VISIBLE, object.getVisible(), Types.BOOLEAN);
        source.addValue(SqlVendedor.ID_VENDEDOR, object.getVendedor().getIdVendedor(), Types.INTEGER);

        return source;
    }

    private SqlParameterSource getParameterFunction(DtoCliente object) {
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue(SqlCliente.NOMBRE + "S", object.getNombre(), Types.VARCHAR);
        source.addValue(SqlCliente.APELLIDO_PATERNO + "S", object.getApellidoPaterno(), Types.VARCHAR);
        source.addValue(SqlCliente.APELLIDO_MATERNO + "S", object.getApellidoMaterno(), Types.VARCHAR);
        return source;
    }

    private static class ClientesRowMapper implements RowMapper<DtoCliente> {

        @Override
        public DtoCliente mapRow(ResultSet rs, int i) throws SQLException {
            DtoCliente cliente = ClienteFactory.newInstance();
            DtoCredito credito = CreditoFactory.newInstance();
            DtoVendedor vendedor = VendedorFactory.newInstance();

            cliente.setIdCliente(rs.getInt(SqlCliente.ID_CLIENTE));
            cliente.setEmpresa(rs.getString(SqlCliente.EMPRESA));
            cliente.setRazonSocial(rs.getString(SqlCliente.RAZON_SOCIAL));
            cliente.setRfc(rs.getString(SqlCliente.RFC));
            cliente.setCorreoEmpresa(rs.getString(SqlCliente.CORREO_EMPRESA));
            cliente.setSitioWeb(rs.getString(SqlCliente.SITIO_WEB));
            cliente.setCalle(rs.getString(SqlCliente.CALLE));
            cliente.setCodigoPostal(rs.getString(SqlCliente.CP));
            cliente.setColonia(rs.getString(SqlCliente.COLONIA));
            cliente.setDelegacion(rs.getString(SqlCliente.DELEGACION));
            cliente.setMunicipio(rs.getString(SqlCliente.MUNICIPIO));
            cliente.setEstado(rs.getString(SqlCliente.ESTADO));
            cliente.setCiudad(rs.getString(SqlCliente.CIUDAD));
            cliente.setNoExterior(rs.getString(SqlCliente.NO_EXTERIOR));
            cliente.setNoInterior(rs.getString(SqlCliente.NO_INTERIOR));
            cliente.setNombre(rs.getString(SqlCliente.NOMBRE));
            cliente.setApellidoPaterno(rs.getString(SqlCliente.APELLIDO_PATERNO));
            cliente.setApellidoMaterno(rs.getString(SqlCliente.APELLIDO_MATERNO));
            cliente.setTelefono1(rs.getString(SqlCliente.TELEFONO1));
            cliente.setTelefono2(rs.getString(SqlCliente.TELEFONO2));
            cliente.setTelefono3(rs.getString(SqlCliente.TELEFONO3));
            cliente.setCorreo1(rs.getString(SqlCliente.CORREO1));
            cliente.setCorreo2(rs.getString(SqlCliente.CORREO2));
            cliente.setCorreo3(rs.getString(SqlCliente.CORREO3));
            cliente.setContacto1(rs.getString(SqlCliente.CONTACTO1));
            cliente.setContacto2(rs.getString(SqlCliente.CONTACTO2));
            cliente.setContacto3(rs.getString(SqlCliente.CONTACTO3));
            credito.setTipoCredito(rs.getString(SqlCliente.TIPO_CREDITO));
            credito.setCantidadMonetaria(rs.getBigDecimal(SqlCliente.CANTIDAD_MONETARIA));
            cliente.setVisible(rs.getBoolean(SqlCliente.VISIBLE));
            cliente.setCredito(credito);
            vendedor.setIdVendedor(rs.getInt(SqlVendedor.ID_VENDEDOR));
            cliente.setVendedor(vendedor);
            return cliente;
        }
    }

    private static class CodigoPostalRowMapper implements RowMapper<DtoCliente> {

        @Override
        public DtoCliente mapRow(ResultSet rs, int i) throws SQLException {

            DtoCliente cliente = ClienteFactory.newInstance();
            cliente.setCodigoPostal(rs.getString(SqlCliente.CP));
            cliente.setColonia(rs.getString(SqlCliente.COLONIA));
            cliente.setDelegacion(rs.getString(SqlCliente.DELEGACION));
            cliente.setMunicipio(rs.getString(SqlCliente.MUNICIPIO));
            cliente.setEstado(rs.getString(SqlCliente.ESTADO));
            cliente.setCiudad(rs.getString(SqlCliente.CIUDAD));
            return cliente;

        }
    }

}
