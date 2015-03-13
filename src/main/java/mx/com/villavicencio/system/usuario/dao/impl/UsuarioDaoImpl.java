package mx.com.villavicencio.system.usuario.dao.impl;

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
import mx.com.villavicencio.system.usuario.dao.UsuarioDao;
import mx.com.villavicencio.system.usuario.dao.sql.sql.SqlUsuario;
import mx.com.villavicencio.system.usuario.dao.sql.function.Function;
import mx.com.villavicencio.system.usuario.dao.sql.procedure.Procedure;
import mx.com.villavicencio.system.usuario.dto.DtoUsuario;
import mx.com.villavicencio.system.usuario.factory.UsuarioFactory;
import mx.com.villavicencio.system.vendedor.dao.sql.sql.SqlVendedor;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 *
 * @author Gabriel J
 */
public class UsuarioDaoImpl extends JdbcDaoSupport implements UsuarioDao {

    @Override
    public Boolean verifyExistUsuario(DtoUsuario usuario) {
        try {
            SimpleJdbcCall simpleJdbcCall = Jdbc.getSimpleJdbcCallFunction(getJdbcTemplate(), Function.VERIFY_EXIST_USUARIO);
            SqlParameterSource parameterFunction = getParameterFunction(usuario);
            Integer executeFunction = simpleJdbcCall.executeFunction(Integer.class, parameterFunction);
            return executeFunction != 0;
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_VERIFICAR)
                    + UsuarioDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_VERIFICAR), ex);
        }
    } 
    
    @Override
    public DtoUsuario validateUsuario(DtoUsuario user) {
        DtoUsuario users = null;
        Object[] args = {user.getNombreUsuario(), user.getPassword()};
        try {
            users = getJdbcTemplate().queryForObject(SqlUsuario.USUARIO, args, new UserRowMapper());
        } catch (EmptyResultDataAccessException ex) {
            return users;
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND)
                    + "\n" + UsuarioDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND), ex);
        }
        return users;
    }

    @Override
    public Collection<DtoUsuario> findAll() {
        try {
            return getJdbcTemplate().query(SqlUsuario.USUARIOS, new UserRowMapper());
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND)
                    + "\n" + UsuarioDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND), ex);
        }
    }

    @Override
    public DtoUsuario findById(DtoUsuario object) {
        DtoUsuario users = null;
        Object[] args = {object.getIdUsuario()};
        try {
            users = getJdbcTemplate().queryForObject(SqlUsuario.USUARIO_BY_ID, args, new UserRowMapper());
        } catch (EmptyResultDataAccessException ex) {
            users = UsuarioFactory.newInstance();
            users.setIdUsuario(object.getIdUsuario());
            users.setNombreUsuario("Usuario Eliminado");
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND)
                    + "\n" + UsuarioDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND), ex);
        }
        return users;
    }

    @Override
    public void ingresar(DtoUsuario object) {
         ApplicationMessages.warnMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO)
                + UsuarioDaoImpl.class.getSimpleName());
        throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
    }

    @Override
    public DtoUsuario insert(DtoUsuario user) {
        try {
            SimpleJdbcCall simpleJdbcCall = Jdbc.getSimpleJdbcCall(getJdbcTemplate(), Procedure.PROCEDURE_USUARIOS);
            simpleJdbcCall.execute(getParameterProcedure(user));
            simpleJdbcCall = Jdbc.getSimpleJdbcCallFunction(getJdbcTemplate(), Function.LAST_INDEX_USUARIO);
            Integer executeFunction = simpleJdbcCall.executeFunction(Integer.class, GenericTypes.NONE);
            user.setIdUsuario(executeFunction);
            return user;
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_INSERT)
                    + "\n" + UsuarioDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_INSERT));
        }
    }
    
    @Override
    public void modificar(DtoUsuario object) {
        try {
            SimpleJdbcCall simpleJdbcCall = Jdbc.getSimpleJdbcCall(getJdbcTemplate(), Procedure.PROCEDURE_USUARIOS);
            simpleJdbcCall.execute(getParameterProcedure(object));
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_MODIFY)
                    + "\n" + UsuarioDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_MODIFY), ex);
        }
    }

    @Override
    public void eliminar(DtoUsuario object) {
        ApplicationMessages.warnMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO)
                + UsuarioDaoImpl.class.getSimpleName());
        throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
    }

    private SqlParameterSource getParameterFunction(DtoUsuario object) {
        MapSqlParameterSource source = new MapSqlParameterSource();
                source.addValue(SqlUsuario.NOMBRE_USUARIO + "S", object.getNombreUsuario(), Types.VARCHAR);
        return source;
    }
    
    private MapSqlParameterSource getParameterProcedure(DtoUsuario object) {
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue(SqlUsuario.OPCION, object.getOpcion(), Types.INTEGER);
        source.addValue(SqlUsuario.ID_USUARIO, object.getIdUsuario(), Types.INTEGER);
        source.addValue(SqlUsuario.NOMBRE_USUARIO, object.getNombreUsuario(), Types.VARCHAR);
        source.addValue(SqlUsuario.NOMBRE_COMPLETO, object.getNombreCompleto(), Types.VARCHAR);
        source.addValue(SqlUsuario.PASSWORD, object.getPassword(), Types.VARCHAR);
        source.addValue(SqlUsuario.NO_TELEFONO, object.getNoTelefono(), Types.VARCHAR);
        source.addValue(SqlUsuario.PUESTO, object.getPuesto(), Types.VARCHAR);
        source.addValue(SqlVendedor.ID_VENDEDOR, object.getIdVendedor(), Types.INTEGER);
        return source;
    }
    
    private static class UserRowMapper implements RowMapper<DtoUsuario> {

        @Override
        public DtoUsuario mapRow(ResultSet rs, int i) throws SQLException {
            DtoUsuario usuarios = UsuarioFactory.newInstance();
            usuarios.setIdUsuario(rs.getInt(SqlUsuario.ID_USUARIO));
            usuarios.setNombreUsuario(rs.getString(SqlUsuario.NOMBRE_USUARIO));
            usuarios.setPassword(rs.getString(SqlUsuario.PASSWORD));
            usuarios.setNombreCompleto(rs.getString(SqlUsuario.NOMBRE_COMPLETO));
            usuarios.setNoTelefono(rs.getString(SqlUsuario.NO_TELEFONO));
            usuarios.setPuesto(rs.getString(SqlUsuario.PUESTO));
            usuarios.setIdVendedor(rs.getInt(SqlVendedor.ID_VENDEDOR));
            return usuarios;
        }
    }
}
