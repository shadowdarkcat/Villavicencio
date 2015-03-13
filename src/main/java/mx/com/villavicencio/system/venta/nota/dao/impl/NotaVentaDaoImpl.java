package mx.com.villavicencio.system.venta.nota.dao.impl;

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
import mx.com.villavicencio.system.pedido.pedido.dao.sql.sql.SqlPedido;
import mx.com.villavicencio.system.pedido.pedido.dto.DtoPedido;
import mx.com.villavicencio.system.pedido.pedido.factory.PedidoFactory;
import mx.com.villavicencio.system.venta.nota.dao.NotaVentaDao;
import mx.com.villavicencio.system.venta.nota.dao.sql.procedure.Procedure;
import mx.com.villavicencio.system.venta.nota.dao.sql.sql.SqlNotaVenta;
import mx.com.villavicencio.system.venta.nota.dao.sql.view.View;
import mx.com.villavicencio.system.venta.nota.dto.DtoNotaVenta;
import mx.com.villavicencio.system.venta.nota.factory.NotaVentaFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 *
 * @author Gabriel J
 */
public class NotaVentaDaoImpl extends JdbcDaoSupport implements NotaVentaDao {

    @Override
    public DtoNotaVenta findByIdPedido(DtoPedido object) {
        Object[] args = {object.getIdPedido()};
        try {
            return getJdbcTemplate().queryForObject(View.VIEW_NOTA_VENTA, args, new NotaVentaRowMapper());
        } catch (IncorrectResultSizeDataAccessException ex) {
            return NotaVentaFactory.newInstance();
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND)
                    + "\n" + NotaVentaDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND)
                    + "\n" + NotaVentaDaoImpl.class.getSimpleName());
        }
    }

    @Override
    public Collection<DtoNotaVenta> findAll() {
        ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO) + "\n" + NotaVentaDaoImpl.class.getSimpleName());
        throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO) + "\n" + NotaVentaDaoImpl.class.getSimpleName());
    }

    @Override
    public DtoNotaVenta findById(DtoNotaVenta object) {
        Object[] args = {object.getIdNotaVenta()};
        try {
            return getJdbcTemplate().queryForObject(View.VIEW_NOTA_VENTA_ID, args, new NotaVentaRowMapper());
        } catch (IncorrectResultSizeDataAccessException ex) {
            return NotaVentaFactory.newInstance();
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND)
                    + "\n" + NotaVentaDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND)
                    + "\n" + NotaVentaDaoImpl.class.getSimpleName());
        }
    }

    @Override
    public void ingresar(DtoNotaVenta object) {
        ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO)
                + "\n" + NotaVentaDaoImpl.class.getSimpleName());
        throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO)
                + "\n" + NotaVentaDaoImpl.class.getSimpleName());
    }

    @Override
    public void modificar(DtoNotaVenta object) {
        try {
            SimpleJdbcCall simpleJdbcCall = Jdbc.getSimpleJdbcCall(getJdbcTemplate(), Procedure.PROCEDURE_NOTAVENTA);
            simpleJdbcCall.execute(getProcedureParameter(object));
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO)
                    + "\n" + NotaVentaDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO)
                    + "\n" + NotaVentaDaoImpl.class.getSimpleName());
        }
    }

    @Override
    public void eliminar(DtoNotaVenta object) {
        ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO)
                + "\n" + NotaVentaDaoImpl.class.getSimpleName());
        throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO)
                + "\n" + NotaVentaDaoImpl.class.getSimpleName());
    }

    private MapSqlParameterSource getProcedureParameter(DtoNotaVenta object){
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue(SqlNotaVenta.OPCION, object.getOpcion(), Types.INTEGER);
        source.addValue(SqlNotaVenta.ID_NOTA_VENTA, object.getIdNotaVenta(), Types.INTEGER);
        source.addValue(SqlNotaVenta.FECHA, object.getFecha(), Types.DATE);
        source.addValue(SqlNotaVenta.FOLIO, object.getFolio(), Types.INTEGER);
        source.addValue(SqlNotaVenta.NOMBRE_CLIENTE, object.getNombreCliente(), Types.VARCHAR);
        source.addValue(SqlNotaVenta.DIRECCION, object.getDireccion(), Types.VARCHAR);
        source.addValue(SqlNotaVenta.RFC, object.getRfc(), Types.VARCHAR);
        source.addValue(SqlNotaVenta.STATUS_NOTA_VENTA, object.getStatusNotaVenta(), Types.CHAR);
        source.addValue(SqlNotaVenta.OBSERVACIONES, object.getObservaciones(), Types.VARCHAR);
        source.addValue(SqlPedido.ID_PEDIDO, object.getPedido().getIdPedido(),Types.INTEGER);
        return source;
    }
    private static class NotaVentaRowMapper implements RowMapper<DtoNotaVenta> {

        @Override
        public DtoNotaVenta mapRow(ResultSet rs, int i) throws SQLException {
            DtoNotaVenta notaVenta = NotaVentaFactory.newInstance();
            notaVenta.setIdNotaVenta(rs.getInt(SqlNotaVenta.ID_NOTA_VENTA));
            notaVenta.setFecha(rs.getDate(SqlNotaVenta.FECHA));
            String format = String.format("%04d", rs.getInt(SqlNotaVenta.FOLIO));
            notaVenta.setFolio(format);
            notaVenta.setNombreCliente(rs.getString(SqlNotaVenta.NOMBRE_CLIENTE));
            notaVenta.setDireccion(rs.getString(SqlNotaVenta.DIRECCION));
            notaVenta.setRfc(rs.getString(SqlNotaVenta.RFC));
            notaVenta.setStatusNotaVenta(rs.getString(SqlNotaVenta.STATUS_NOTA_VENTA).charAt(GenericTypes.ZERO));
            notaVenta.setObservaciones(rs.getString(SqlNotaVenta.OBSERVACIONES));
            notaVenta.setPedido(
                    PedidoFactory.newInstance(
                            rs.getInt(SqlPedido.ID_PEDIDO))
            );
            return notaVenta;
        }
    }
}
