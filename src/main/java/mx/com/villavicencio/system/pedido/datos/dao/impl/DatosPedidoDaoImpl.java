package mx.com.villavicencio.system.pedido.datos.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import mx.com.villavicencio.commons.exception.ApplicationException;
import mx.com.villavicencio.commons.jdbc.Jdbc;
import mx.com.villavicencio.commons.messages.ApplicationMessages;
import mx.com.villavicencio.properties.PropertiesBean;
import mx.com.villavicencio.properties.Property;
import mx.com.villavicencio.system.cliente.dao.sql.sql.SqlCliente;
import mx.com.villavicencio.system.pedido.pedido.dao.sql.sql.SqlPedido;
import mx.com.villavicencio.system.pedido.datos.dao.DatosPedidoDao;
import mx.com.villavicencio.system.pedido.datos.dao.sql.procedure.Procedure;
import mx.com.villavicencio.system.pedido.datos.dao.sql.view.View;
import mx.com.villavicencio.system.pedido.datos.dto.DtoDatosPedido;
import mx.com.villavicencio.system.pedido.datos.factory.DatosPedidoFactory;
import mx.com.villavicencio.system.pedido.detalles.dao.sql.sql.SqlDetallePedido;
import mx.com.villavicencio.system.vendedor.dao.sql.sql.SqlVendedor;
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
public class DatosPedidoDaoImpl extends JdbcDaoSupport implements DatosPedidoDao {

    @Override
    public Collection<DtoDatosPedido> findAll() {
        ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO)
                + "\n" + DatosPedidoDaoImpl.class.getSimpleName());
        throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
    }

    @Override
    public DtoDatosPedido findById(DtoDatosPedido object) {
        Object[] args = null;
        try {
            if (object.getIdPedido() != null) {
                args = new Object[]{object.getIdPedido()};
                return getJdbcTemplate().queryForObject(View.SEARCH_IDS, args, new DatosRowmapper());
            } else if (object.getIdCliente() != null) {
                args = new Object[]{object.getIdCliente()};
                return getJdbcTemplate().queryForObject(View.SEARCH_ID_CLIENTE, args, new DatosRowmapper());
            } else if (object.getIdVendedor() != null) {
                args = new Object[]{object.getIdVendedor()};
                return getJdbcTemplate().queryForObject(View.SEARCH_ID_VENDEDOR, args, new DatosRowmapper());
            }
            return DatosPedidoFactory.newInstance();
        } catch (IncorrectResultSizeDataAccessException ex) {
            return null;
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND)
                    + "\n" + DatosPedidoDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND), ex);
        }
    }

    @Override
    public Collection<DtoDatosPedido> findDatosById(DtoDatosPedido object) {
        Object[] args = null;
        try {
            if ((object.getIdCliente() != null) && (object.getIdPedido() != null)) {
                args = new Object[]{object.getIdCliente(), object.getIdPedido()};
                return getJdbcTemplate().query(View.PEDIDOS_CLIENTE, args, new DatosRowmapper());
            } else if ((object.getIdVendedor() != null) && (object.getIdPedido() != null)) {
                args = new Object[]{object.getIdVendedor(), object.getIdPedido()};
                return getJdbcTemplate().query(View.PEDIDOS_VENDEDOR, args, new DatosRowmapper());
            } else if (object.getIdCliente() != null) {
                args = new Object[]{object.getIdCliente()};
                return getJdbcTemplate().query(View.PEDIDOS_WHITHOUT_PEDIDO_CLIENTE, args, new DatosRowmapper());
            } else if (object.getIdVendedor() != null) {
                args = new Object[]{object.getIdVendedor()};
                return getJdbcTemplate().query(View.PEDIDOS_WHITHOUT_PEDIDO_VENDEDOR, args, new DatosRowmapper());
            } else if ((object.getIdPedido() != null) && (object.getIdCliente() == null) && (object.getIdVendedor() == null)) {
                args = new Object[]{object.getIdPedido()};
                return getJdbcTemplate().query(View.PEDIDOS_WHITHOUT_VENDEDORCLIENTE, args, new DatosRowmapper());
            }
            return new ArrayList<>();
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND)
                    + "\n" + DatosPedidoDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND), ex);
        }
    }

    @Override
    public void ingresar(DtoDatosPedido object) {
        try {
            SimpleJdbcCall simpleJdbcCall = Jdbc.getSimpleJdbcCall(getJdbcTemplate(), Procedure.PROCEDURE_DATOS_PEDIDO);
            simpleJdbcCall.execute(getParameterProcedure(object));
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_INSERT)
                    + "\n" + DatosPedidoDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_INSERT));
        }
    }

    @Override
    public void modificar(DtoDatosPedido object) {
        ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO)
                + "\n" + DatosPedidoDaoImpl.class.getSimpleName());
        throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
    }

    @Override
    public void eliminar(DtoDatosPedido object) {
        ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO)
                + "\n" + DatosPedidoDaoImpl.class.getSimpleName());
        throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
    }

    private SqlParameterSource getParameterProcedure(DtoDatosPedido object) {
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue(SqlPedido.ID_PEDIDO, object.getIdPedido(), Types.INTEGER);
        source.addValue(SqlCliente.ID_CLIENTE, object.getIdCliente(), Types.INTEGER);
        source.addValue(SqlVendedor.ID_VENDEDOR, object.getIdVendedor(), Types.INTEGER);
        source.addValue(SqlDetallePedido.ID_DETALLE_PEDIDO, object.getIdDetallePedido(), Types.INTEGER);
        return source;
    }

    private static class DatosRowmapper implements RowMapper<DtoDatosPedido> {

        @Override
        public DtoDatosPedido mapRow(ResultSet rs, int i) throws SQLException {
            DtoDatosPedido datosPedido = DatosPedidoFactory.newInstance();
            datosPedido.setIdPedido(rs.getInt(SqlPedido.ID_PEDIDO));
            datosPedido.setIdCliente(rs.getInt(SqlCliente.ID_CLIENTE));
            datosPedido.setIdVendedor(rs.getInt(SqlVendedor.ID_VENDEDOR));
            datosPedido.setIdDetallePedido(rs.getInt(SqlDetallePedido.ID_DETALLE_PEDIDO));
            return datosPedido;
        }
    }
}
