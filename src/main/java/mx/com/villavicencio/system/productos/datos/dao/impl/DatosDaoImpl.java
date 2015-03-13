package mx.com.villavicencio.system.productos.datos.dao.impl;

import java.sql.Types;
import mx.com.villavicencio.commons.exception.ApplicationException;
import mx.com.villavicencio.commons.jdbc.Jdbc;
import mx.com.villavicencio.commons.messages.ApplicationMessages;
import mx.com.villavicencio.properties.PropertiesBean;
import mx.com.villavicencio.properties.Property;
import mx.com.villavicencio.system.cliente.dao.sql.sql.SqlCliente;
import mx.com.villavicencio.system.cliente.dto.DtoCliente;
import mx.com.villavicencio.system.productos.productos.dao.sql.sql.SqlProducto;
import mx.com.villavicencio.system.productos.datos.dao.DatosDao;
import mx.com.villavicencio.system.productos.datos.dao.sql.function.Function;
import mx.com.villavicencio.system.productos.datos.dao.sql.procedure.Procedure;
import mx.com.villavicencio.system.productos.productos.dto.DtoProducto;
import mx.com.villavicencio.system.vendedor.dao.sql.sql.SqlVendedor;
import mx.com.villavicencio.system.vendedor.dto.DtoVendedor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 *
 * @author Gabriel J
 */
public class DatosDaoImpl extends JdbcDaoSupport implements DatosDao {

    @Override
    public void productoEstablecidoVendedor(DtoVendedor vendedor, DtoProducto producto) {
        try {
            SimpleJdbcCall simpleJdbcCall = Jdbc.getSimpleJdbcCall(getJdbcTemplate(), Procedure.PROCEDURE_ESTABLECIDO_VENDEDOR);
            simpleJdbcCall.execute(getParameterProcedureEstablecidoVendedor(vendedor, producto));
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_INSERT)
                    + DatosDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_INSERT));
        }
    }

    @Override
    public void productoPersonalizadoVendedor(DtoVendedor vendedor, DtoProducto producto) {
        try {
            SimpleJdbcCall simpleJdbcCall = Jdbc.getSimpleJdbcCall(getJdbcTemplate(), Procedure.PROCEDURE_PERSONALIZADO_VENDEDOR);
            simpleJdbcCall.execute(getParameterProcedurePersonalizadoVendedor(vendedor, producto));
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_INSERT)
                    + DatosDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_INSERT));
        }
    }

    @Override
    public void productoEstablecidoCliente(DtoCliente cliente, DtoProducto producto) {        
        try {
            SimpleJdbcCall simpleJdbcCall = Jdbc.getSimpleJdbcCall(getJdbcTemplate(), Procedure.PROCEDURE_ESTABLECIDO_CLIENTE);
            simpleJdbcCall.execute(getParameterProcedureEstablecidoCliente(cliente, producto));
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_INSERT)
                    + DatosDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_INSERT));
        }
    }

    @Override
    public void productoPersonalizadoCliente(DtoCliente cliente, DtoProducto producto) {
        try {
            SimpleJdbcCall simpleJdbcCall = Jdbc.getSimpleJdbcCall(getJdbcTemplate(), Procedure.PROCEDURE_PERSONALIZADO_CLIENTE);
            simpleJdbcCall.execute(getParameterProcedurePersonalizadoCliente(cliente, producto));
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_INSERT)
                    + DatosDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_INSERT));
        }
    }

    private SqlParameterSource getParameterProcedureEstablecidoVendedor(DtoVendedor vendedor, DtoProducto producto) {
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue(SqlVendedor.OPCION, vendedor.getOpcion(), Types.INTEGER);
        source.addValue(SqlVendedor.ID_VENDEDOR, vendedor.getIdVendedor(), Types.INTEGER);
        source.addValue(SqlProducto.ID_PRODUCTOS, producto.getIdProducto(), Types.INTEGER);
        source.addValue(SqlProducto.COMISION, producto.getComision(), Types.DECIMAL);
        return source;
    }

    private SqlParameterSource getParameterProcedureEstablecidoCliente(DtoCliente cliente, DtoProducto producto) {
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue(SqlVendedor.OPCION, cliente.getOpcion(), Types.INTEGER);
        source.addValue(SqlCliente.ID_CLIENTE, cliente.getIdCliente(), Types.INTEGER);
        source.addValue(SqlProducto.ID_PRODUCTOS, producto.getIdProducto(), Types.INTEGER);
        source.addValue(SqlProducto.COMISION, producto.getComision(), Types.DECIMAL);
        return source;
    }
    
    private SqlParameterSource getParameterProcedurePersonalizadoVendedor(DtoVendedor vendedor, DtoProducto producto) {
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue(SqlVendedor.OPCION, producto.getOpcion(), Types.INTEGER);
        source.addValue(SqlVendedor.ID_VENDEDOR, vendedor.getIdVendedor(), Types.INTEGER);
        source.addValue(SqlProducto.ID_PRODUCTOS_PERSONALIZADOS, producto.getIdProducto(), Types.INTEGER);
        source.addValue(SqlProducto.COMISION, producto.getComision(), Types.DECIMAL);
        return source;
    }
    
     private SqlParameterSource getParameterProcedurePersonalizadoCliente(DtoCliente cliente, DtoProducto producto) {
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue(SqlVendedor.OPCION, producto.getOpcion(), Types.INTEGER);
        source.addValue(SqlCliente.ID_CLIENTE, cliente.getIdCliente(), Types.INTEGER);
        source.addValue(SqlProducto.ID_PRODUCTOS_PERSONALIZADOS, producto.getIdProducto(), Types.INTEGER);
        source.addValue(SqlProducto.COMISION, producto.getComision(), Types.DECIMAL);
        return source;
    }

    @Override
    public Boolean verifyExistEstablecidoVendedor(DtoVendedor vendedor, DtoProducto producto) {
        try {
            SimpleJdbcCall simpleJdbcCall = Jdbc.getSimpleJdbcCallFunction(getJdbcTemplate(), Function.VERIFY_EXIST_PRODUCTO_ESTABLECIDO_VENDEDOR);
            SqlParameterSource parameterFunction = getParameterFunctionVendedorEstablecido(vendedor, producto);
            Integer executeFunction = simpleJdbcCall.executeFunction(Integer.class, parameterFunction);
            return executeFunction != 0;
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_VERIFICAR)
                    + DatosDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_VERIFICAR), ex);
        } catch (Exception ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_VERIFICAR)
                    + DatosDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_VERIFICAR), ex);
        }
    }

    @Override
    public Boolean verifyExistPersonalizadoVendedor(DtoVendedor vendedor, DtoProducto producto) {
        try {
            SimpleJdbcCall simpleJdbcCall = Jdbc.getSimpleJdbcCallFunction(getJdbcTemplate(), Function.VERIFY_EXIST_PRODUCTO_PERSONALIZADO_VENDEDOR);
            SqlParameterSource parameterFunction = getParameterFunctionVendedorPersonalizado(vendedor, producto);
            Integer executeFunction = simpleJdbcCall.executeFunction(Integer.class, parameterFunction);
            return executeFunction != 0;
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_VERIFICAR)
                    + DatosDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_VERIFICAR), ex);
        } catch (Exception ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_VERIFICAR)
                    + DatosDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_VERIFICAR), ex);
        }
    }

    @Override
    public Boolean verifyExistEstablecidoCliente(DtoCliente cliente, DtoProducto producto) {
        try {
            SimpleJdbcCall simpleJdbcCall = Jdbc.getSimpleJdbcCallFunction(getJdbcTemplate(), Function.VERIFY_EXIST_PRODUCTO_ESTABLECIDO_CLIENTE);
            SqlParameterSource parameterFunction = getParameterFunctionCliente(cliente, producto);
            Integer executeFunction = simpleJdbcCall.executeFunction(Integer.class, parameterFunction);
            return executeFunction != 0;
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_VERIFICAR)
                    + DatosDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_VERIFICAR), ex);
        } catch (Exception ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_VERIFICAR)
                    + DatosDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_VERIFICAR), ex);
        }
    }

    @Override
    public Boolean verifyExistPersonalizadoCliente(DtoCliente cliente, DtoProducto producto) {
        try {
            SimpleJdbcCall simpleJdbcCall = Jdbc.getSimpleJdbcCallFunction(getJdbcTemplate(), Function.VERIFY_EXIST_PRODUCTO_PERSONALIZADO_CLIENTE);
            SqlParameterSource parameterFunction = getParameterFunctionClientePersonalizado(cliente, producto);
            Integer executeFunction = simpleJdbcCall.executeFunction(Integer.class, parameterFunction);
            return executeFunction != 0;
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_VERIFICAR)
                    + DatosDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_VERIFICAR), ex);
        } catch (Exception ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_VERIFICAR)
                    + DatosDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_VERIFICAR), ex);
        }
    }

    private SqlParameterSource getParameterFunctionVendedorEstablecido(DtoVendedor vendedor, DtoProducto producto) {
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue(SqlProducto.ID_PRODUCTOS + "S", producto.getIdProducto(), Types.INTEGER);
        source.addValue(SqlVendedor.ID_VENDEDOR + "S", vendedor.getIdVendedor(), Types.INTEGER);
        return source;
    }

    private SqlParameterSource getParameterFunctionVendedorPersonalizado(DtoVendedor vendedor, DtoProducto producto) {
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue(SqlProducto.ID_PRODUCTOS_PERSONALIZADOS + "S", producto.getIdProducto(), Types.INTEGER);
        source.addValue(SqlVendedor.ID_VENDEDOR + "S", vendedor.getIdVendedor(), Types.INTEGER);
        return source;
    }
    
    private SqlParameterSource getParameterFunctionCliente(DtoCliente cliente, DtoProducto producto) {
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue(SqlProducto.ID_PRODUCTOS + "S", producto.getIdProducto(), Types.INTEGER);
        source.addValue(SqlCliente.ID_CLIENTE + "S", cliente.getIdCliente(), Types.INTEGER);
        return source;
    }
    
    private SqlParameterSource getParameterFunctionClientePersonalizado(DtoCliente cliente, DtoProducto producto) {
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue(SqlProducto.ID_PRODUCTOS_PERSONALIZADOS + "S", producto.getIdProducto(), Types.INTEGER);
        source.addValue(SqlCliente.ID_CLIENTE + "S", cliente.getIdCliente(), Types.INTEGER);
        return source;
    }
}
