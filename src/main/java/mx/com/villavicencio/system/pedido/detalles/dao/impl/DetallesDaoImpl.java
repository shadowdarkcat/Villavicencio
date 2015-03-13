package mx.com.villavicencio.system.pedido.detalles.dao.impl;

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
import mx.com.villavicencio.system.pedido.detalles.dao.DetallesDao;
import mx.com.villavicencio.system.pedido.detalles.dao.sql.function.Function;
import mx.com.villavicencio.system.pedido.detalles.dao.sql.procedure.Procedure;
import mx.com.villavicencio.system.pedido.detalles.dao.sql.sql.SqlDetallePedido;
import mx.com.villavicencio.system.pedido.detalles.dao.sql.view.View;
import mx.com.villavicencio.system.pedido.detalles.dto.DtoDetallePedido;
import mx.com.villavicencio.system.pedido.detalles.factory.DetallePedidoFactory;
import mx.com.villavicencio.system.productos.productos.dao.sql.sql.SqlProducto;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 *
 * @author Gabriel J
 */
public class DetallesDaoImpl extends JdbcDaoSupport implements DetallesDao {

    @Override
    public Collection<DtoDetallePedido> findAll() {
        try {
            return getJdbcTemplate().query(View.DETALLES_PEDIDOS, new DetallesPedidoRowMapper());
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND)
                    + "\n" + ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND));
        }
    }

    @Override
    public DtoDetallePedido findById(DtoDetallePedido object) {
        Object[] args = {object.getIdDetallePedido()};
        try {
            return getJdbcTemplate().queryForObject(View.DETALLE_PEDIDO, args, new DetallesPedidoRowMapper());
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND)
                    + "\n" + DetallesDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND)
                    + "\n" + DetallesDaoImpl.class.getSimpleName(), ex);
        }
    }

    @Override
    public void ingresar(DtoDetallePedido object) {
        ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
        throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
    }

    @Override
    public DtoDetallePedido insert(DtoDetallePedido object) {
        try {
            SimpleJdbcCall simpleJdbcCall = Jdbc.getSimpleJdbcCall(getJdbcTemplate(), Procedure.PROCEDURE_DETALLE_PEDIDO);
            simpleJdbcCall.execute(getParameterProcedure(object));
            simpleJdbcCall = Jdbc.getSimpleJdbcCallFunction(getJdbcTemplate(), Function.LAST_INDEX_DETALLE);
            Integer executeFunction = simpleJdbcCall.executeFunction(Integer.class, GenericTypes.NONE);
            object.setIdDetallePedido(executeFunction);
            return object;
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND)
                    + "\n" + DetallesDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND)
                    + "\n" + DetallesDaoImpl.class.getSimpleName(), ex);
        }
    }

    @Override
    public void modificar(DtoDetallePedido object) {
        try {
            SimpleJdbcCall simpleJdbcCall = Jdbc.getSimpleJdbcCall(getJdbcTemplate(), Procedure.PROCEDURE_DETALLE_PEDIDO);
            simpleJdbcCall.execute(getParameterProcedure(object));
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND)
                    + "\n" + DetallesDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND)
                    + "\n" + DetallesDaoImpl.class.getSimpleName(), ex);
        }
    }

    @Override
    public void eliminar(DtoDetallePedido object) {
        ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
        throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
    }

    private SqlParameterSource getParameterProcedure(DtoDetallePedido object) {
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue(SqlDetallePedido.OPCION, object.getOpcion(), Types.INTEGER);
        source.addValue(SqlDetallePedido.ID_DETALLE_PEDIDO, object.getIdDetallePedido(), Types.INTEGER);
        source.addValue(SqlProducto.ID_PRODUCTOS, object.getIdProducto(), Types.INTEGER);
        source.addValue(SqlProducto.NOMBRE_PRODUCTO, object.getNombreProducto(), Types.VARCHAR);
        source.addValue(SqlProducto.COSTO_UNITARIO, object.getCostoUnitario(), Types.DECIMAL);
        source.addValue(SqlDetallePedido.CANTIDAD_PIEZAS, object.getCantidadPiezas(), Types.INTEGER);
        source.addValue(SqlProducto.PESO, object.getPeso(), Types.VARCHAR);
        source.addValue(SqlProducto.PESO_MINIMO, object.getPesoMinimo(), Types.DECIMAL);
        source.addValue(SqlProducto.PESO_MAXIMO, object.getPesoMaximo(), Types.DECIMAL);
        source.addValue(SqlProducto.COMISION, object.getComision(), Types.DECIMAL);
        source.addValue(SqlDetallePedido.MUESTRA, Boolean.FALSE, Types.BOOLEAN);
        source.addValue(SqlDetallePedido.OBSERVACION, object.getObservacion(), Types.VARCHAR);
        return source;
    }

    private static class DetallesPedidoRowMapper implements RowMapper<DtoDetallePedido> {

        @Override
        public DtoDetallePedido mapRow(ResultSet rs, int i) throws SQLException {
            DtoDetallePedido detallePedido = DetallePedidoFactory.newInstance();
            detallePedido.setIdDetallePedido(rs.getInt(SqlDetallePedido.ID_DETALLE_PEDIDO));
            detallePedido.setIdProducto(rs.getInt(SqlProducto.ID_PRODUCTOS));
            detallePedido.setNombreProducto(rs.getString(SqlProducto.NOMBRE_PRODUCTO));
            detallePedido.setCostoUnitario(rs.getBigDecimal(SqlProducto.COSTO_UNITARIO));
            detallePedido.setCantidadPiezas(rs.getInt(SqlDetallePedido.CANTIDAD_PIEZAS));
            detallePedido.setPeso(rs.getString(SqlProducto.PESO));
            detallePedido.setPesoMinimo(rs.getBigDecimal(SqlProducto.PESO_MINIMO));
            detallePedido.setPesoMaximo(rs.getBigDecimal(SqlProducto.PESO_MAXIMO));
            detallePedido.setComision(rs.getBigDecimal(SqlProducto.COMISION));
            detallePedido.setObservacion(rs.getString(SqlDetallePedido.OBSERVACION));
            return detallePedido;
        }
    }
}
