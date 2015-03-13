package mx.com.villavicencio.system.productos.productos.dao.impl;

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
import mx.com.villavicencio.system.productos.productos.dao.ProductoDao;
import mx.com.villavicencio.system.productos.productos.dao.sql.sql.SqlProducto;
import mx.com.villavicencio.system.productos.productos.dao.sql.function.Function;
import mx.com.villavicencio.system.productos.productos.dao.sql.procedure.Procedure;
import mx.com.villavicencio.system.productos.productos.dao.sql.view.View;
import mx.com.villavicencio.system.productos.productos.dto.DtoProducto;
import mx.com.villavicencio.system.productos.productos.factory.ProductoFactory;
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
public class ProductoDaoImpl extends JdbcDaoSupport implements ProductoDao {

    @Override
    public Collection<DtoProducto> findAll() {
        try {
            return getJdbcTemplate().query(SqlProducto.PRODUCTOS, new ProductoRowMapper());
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND)
                    + ProductoDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND), ex);
        }
    }

    @Override
    public DtoProducto findById(DtoProducto object) {
        Object[] args = {object.getIdProducto()};
        try {
            return getJdbcTemplate().queryForObject(SqlProducto.PRODUCTO, args, new ProductoRowMapper());
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND)
                    + ProductoDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND), ex);
        }
    }

    @Override
    public void ingresar(DtoProducto object) {
        try {
            SimpleJdbcCall simpleJdbcCall = Jdbc.getSimpleJdbcCall(getJdbcTemplate(), Procedure.PROCEDURE_PRODUCTO);
            simpleJdbcCall.execute(getParameterProcedureEstablecido(object));
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_INSERT)
                    + ProductoDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_INSERT));
        }
    }

    @Override
    public DtoProducto insertPersonalizado(DtoProducto personalizado) {
        try {
            SimpleJdbcCall simpleJdbcCall = Jdbc.getSimpleJdbcCall(getJdbcTemplate(), Procedure.PROCEDURE_PERSONALIZADO);
            simpleJdbcCall.execute(getParameterProcedurePersonalizado(personalizado));
            simpleJdbcCall = Jdbc.getSimpleJdbcCallFunction(getJdbcTemplate(), Function.LAST_INDEX_PERSONALIZADO);
            Integer executeFunction = simpleJdbcCall.executeFunction(Integer.class, GenericTypes.NONE);
            personalizado.setIdProducto(executeFunction);
            return personalizado;
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_INSERT)
                    + ProductoDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_INSERT));
        }
    }

    @Override
    public void modificar(DtoProducto object) {
        try {
            SimpleJdbcCall simpleJdbcCall = Jdbc.getSimpleJdbcCall(getJdbcTemplate(), Procedure.PROCEDURE_PRODUCTO);
            simpleJdbcCall.execute(getParameterProcedureEstablecido(object));
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_MODIFY)
                    + ProductoDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_MODIFY), ex);
        }
    }

    @Override
    public void eliminar(DtoProducto object) {
        try {
            SimpleJdbcCall simpleJdbcCall = Jdbc.getSimpleJdbcCall(getJdbcTemplate(), Procedure.PROCEDURE_PRODUCTO);
            simpleJdbcCall.execute(getParameterProcedureEstablecido(object));
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_DELETE)
                    + ProductoDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_DELETE), ex);
        }
    }

    @Override
    public Boolean verifyExist(DtoProducto object) {
        try {
            SimpleJdbcCall simpleJdbcCall = Jdbc.getSimpleJdbcCallFunction(getJdbcTemplate(), Function.VERIFY_EXIST_PRODUCTO);
            SqlParameterSource parameterFunction = getParameterFunction(object);
            Integer executeFunction = simpleJdbcCall.executeFunction(Integer.class, parameterFunction);
            return executeFunction != 0;
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_VERIFICAR)
                    + ProductoDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_VERIFICAR), ex);
        } catch (Exception ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_VERIFICAR)
                    + ProductoDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_VERIFICAR), ex);
        }
    }

    @Override
    public Collection<DtoProducto> findDatosProductoEstablecidoVendedorById(Integer id) {
        Object[] args = {id};
        try {
            return getJdbcTemplate().query(View.DATOS_PRODUCTOS_VENDEDOR_ESTABLECIDO, args, new ProductoRowMapper());
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND)
                    + ProductoDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND), ex);
        }
    }

    @Override
    public Collection<DtoProducto> findDatosProductoPersonalizadoVendedorById(Integer id) {
        Object[] args = {id};
        try {
            return getJdbcTemplate().query(View.DATOS_PRODUCTOS_VENDEDOR_PERSONALIZADOS, args, new ProductoPersonalizadoRowMapper());
        } catch (EmptyResultDataAccessException ex) {
            return null;
        } catch (IncorrectResultSizeDataAccessException ex) {
            return null;
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND)
                    + ProductoDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND), ex);
        }
    }

    @Override
    public DtoProducto findProductoPersonalizadoVendedorById(DtoProducto producto, Integer id) {
        Object[] args = {id, producto.getIdProducto()};
        try {
            return getJdbcTemplate().queryForObject(View.DATOS_PRODUCTOS_VENDEDOR_PERSONALIZADO, args, new ProductoPersonalizadoRowMapper());
        } catch (EmptyResultDataAccessException ex) {
            return null;
        } catch (IncorrectResultSizeDataAccessException ex) {
            return null;
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND)
                    + ProductoDaoImpl.class
                    .getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND), ex);
        }
    }

    @Override
    public Collection<DtoProducto> findDatosProductoEstablecidoClienteById(Integer id) {
        Object[] args = {id};
        try {
            return getJdbcTemplate().query(View.DATOS_PRODUCTOS_CLIENTE_ESTABLECIDO, args, new ProductoRowMapper());
        } catch (EmptyResultDataAccessException ex) {
            return null;
        } catch (IncorrectResultSizeDataAccessException ex) {
            return null;
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND)
                    + ProductoDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND), ex);
        }
    }

    @Override
    public Collection<DtoProducto> findDatosProductoPersonalizadoClienteById(Integer id) {
        Object[] args = {id};
        try {
            return getJdbcTemplate().query(View.DATOS_PRODUCTOS_CLIENTE_PERSONALIZADO, args, new ProductoPersonalizadoRowMapper());
        } catch (EmptyResultDataAccessException ex) {
            return null;
        } catch (IncorrectResultSizeDataAccessException ex) {
            return null;
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND)
                    + ProductoDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND), ex);
        }
    }

    @Override
    public DtoProducto findProductoPersonalizadoClienteById(DtoProducto producto, Integer id) {
         Object[] args = {id, producto.getIdProducto()};
        try {
            return getJdbcTemplate().queryForObject(View.DATOS_PRODUCTOS_CLIENTES_PERSONALIZADOS, args, new ProductoPersonalizadoRowMapper());
        } catch (EmptyResultDataAccessException ex) {
            return null;
        } catch (IncorrectResultSizeDataAccessException ex) {
            return null;
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND)
                    + ProductoDaoImpl.class
                    .getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND), ex);
        }
    }

    private SqlParameterSource getParameterFunction(DtoProducto object) {
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue(SqlProducto.NOMBRE_PRODUCTO + "S", object.getNombreProducto(), Types.VARCHAR);
        return source;
    }

    private SqlParameterSource getParameterProcedureEstablecido(DtoProducto object) {
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue(SqlProducto.OPCION, object.getOpcion(), Types.INTEGER);
        source.addValue(SqlProducto.ID_PRODUCTOS, object.getIdProducto(), Types.INTEGER);
        source.addValue(SqlProducto.CLAVE_PRODUCTO, object.getClaveProducto(), Types.VARCHAR);
        source.addValue(SqlProducto.NOMBRE_PRODUCTO, object.getNombreProducto(), Types.VARCHAR);
        source.addValue(SqlProducto.PESO, object.getPeso(), Types.VARCHAR);
        source.addValue(SqlProducto.PESO_MINIMO, object.getPesoMinimo(), Types.DECIMAL);
        source.addValue(SqlProducto.PESO_MAXIMO, object.getPesoMaximo(), Types.DECIMAL);
        source.addValue(SqlProducto.COSTO_UNITARIO, object.getCostoUnitario(), Types.DECIMAL);
        source.addValue(SqlProducto.IMAGEN_PRODUCTO, object.getImagenProducto(), Types.VARCHAR);
        source.addValue(SqlProducto.VISIBLE, object.getVisible(), Types.BOOLEAN);
        return source;
    }

    private SqlParameterSource getParameterProcedurePersonalizado(DtoProducto object) {
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue(SqlProducto.OPCION, object.getOpcion(), Types.INTEGER);
        source.addValue(SqlProducto.ID_PRODUCTOS_PERSONALIZADOS, object.getIdProducto(), Types.INTEGER);
        source.addValue(SqlProducto.CLAVE_PRODUCTO, object.getClaveProducto(), Types.VARCHAR);
        source.addValue(SqlProducto.NOMBRE_PRODUCTO, object.getNombreProducto(), Types.VARCHAR);
        source.addValue(SqlProducto.PESO, object.getPeso(), Types.VARCHAR);
        source.addValue(SqlProducto.PESO_MINIMO, object.getPesoMinimo(), Types.DECIMAL);
        source.addValue(SqlProducto.PESO_MAXIMO, object.getPesoMaximo(), Types.DECIMAL);
        source.addValue(SqlProducto.COSTO_UNITARIO, object.getCostoUnitario(), Types.DECIMAL);
        source.addValue(SqlProducto.IMAGEN_PRODUCTO, object.getImagenProducto(), Types.VARCHAR);
        source.addValue(SqlProducto.VISIBLE, object.getVisible(), Types.BOOLEAN);
        return source;

    }

    private static class ProductoRowMapper implements RowMapper<DtoProducto> {

        @Override
        public DtoProducto mapRow(ResultSet rs, int i) {
            DtoProducto producto = ProductoFactory.newInstance();
            try {
                producto.setIdProducto(rs.getInt(SqlProducto.ID_PRODUCTOS));
                producto.setClaveProducto(rs.getString(SqlProducto.CLAVE_PRODUCTO));
                producto.setNombreProducto(rs.getString(SqlProducto.NOMBRE_PRODUCTO));
                producto.setPeso(rs.getString(SqlProducto.PESO));
                producto.setPesoMinimo(rs.getBigDecimal(SqlProducto.PESO_MINIMO));
                producto.setPesoMaximo(rs.getBigDecimal(SqlProducto.PESO_MAXIMO));
                producto.setCostoUnitario(rs.getBigDecimal(SqlProducto.COSTO_UNITARIO));
                producto.setImagenProducto(rs.getString(SqlProducto.IMAGEN_PRODUCTO));
                producto.setVisible(rs.getBoolean(SqlProducto.VISIBLE));
                producto.setComision(rs.getBigDecimal(SqlProducto.COMISION));
            } catch (SQLException ex) {
                return producto;
            }
            return producto;
        }
    }

    private static class ProductoPersonalizadoRowMapper implements RowMapper<DtoProducto> {

        @Override
        public DtoProducto mapRow(ResultSet rs, int i) {
            DtoProducto producto = ProductoFactory.newInstance();
            try {
                producto.setIdProducto(rs.getInt(SqlProducto.ID_PRODUCTOS_PERSONALIZADOS));
                producto.setClaveProducto(rs.getString(SqlProducto.CLAVE_PRODUCTO));
                producto.setNombreProducto(rs.getString(SqlProducto.NOMBRE_PRODUCTO));
                producto.setPeso(rs.getString(SqlProducto.PESO));
                producto.setPesoMinimo(rs.getBigDecimal(SqlProducto.PESO_MINIMO));
                producto.setPesoMaximo(rs.getBigDecimal(SqlProducto.PESO_MAXIMO));
                producto.setCostoUnitario(rs.getBigDecimal(SqlProducto.COSTO_UNITARIO));
                producto.setImagenProducto(rs.getString(SqlProducto.IMAGEN_PRODUCTO));
                producto.setComision(rs.getBigDecimal(SqlProducto.COMISION));
            } catch (SQLException ex) {
                return producto;
            }
            return producto;
        }
    }
}
