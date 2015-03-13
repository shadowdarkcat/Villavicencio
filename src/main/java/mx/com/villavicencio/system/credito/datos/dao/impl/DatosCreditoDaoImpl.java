package mx.com.villavicencio.system.credito.datos.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import mx.com.villavicencio.commons.exception.ApplicationException;
import mx.com.villavicencio.commons.jdbc.Jdbc;
import mx.com.villavicencio.commons.messages.ApplicationMessages;
import mx.com.villavicencio.properties.PropertiesBean;
import mx.com.villavicencio.properties.Property;
import mx.com.villavicencio.system.cliente.dto.DtoCliente;
import mx.com.villavicencio.system.credito.datos.dao.DatosCreditoDao;
import mx.com.villavicencio.system.credito.datos.dao.sql.sql.SqlDatosCredito;
import mx.com.villavicencio.system.credito.credito.dto.DtoCredito;
import mx.com.villavicencio.system.credito.datos.dao.sql.procedure.Procedure;
import mx.com.villavicencio.system.credito.datos.dao.sql.view.View;
import mx.com.villavicencio.system.vendedor.dto.DtoVendedor;
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
public class DatosCreditoDaoImpl extends JdbcDaoSupport implements DatosCreditoDao {

    @Override
    public void insertDatosCredito(DtoCliente cliente, DtoVendedor vendedor, DtoCredito credito) {
        try {
            SimpleJdbcCall simpleJdbcCall = Jdbc.getSimpleJdbcCall(getJdbcTemplate(), Procedure.PROCEDURE_DATOS_CREDITO);
            simpleJdbcCall.execute(getParameters(cliente, vendedor, credito));
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_INSERT)
                    + DatosCreditoDaoImpl.class
                    .getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_INSERT));
        }
    }

    @Override
    public Integer findDatosCreditoById(DtoCliente cliente, DtoVendedor vendedor) {
        Object [] args;
        try {
            if((cliente != null) && (cliente.getIdCliente() != null)){
                args = new Object[]{cliente.getIdCliente()};
                return getJdbcTemplate().queryForObject(View.VIEW_DATOS_CREDITO_CLIENTE, args, new DatosCreditoRowMapper());
            }else if((vendedor != null) && (vendedor.getIdVendedor() != null)){
                args = new Object[]{vendedor.getIdVendedor()};
                return getJdbcTemplate().queryForObject(View.VIEW_DATOS_CREDITO_VENDEDOR, args, new DatosCreditoRowMapper());
            }
            return null;
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_INSERT)
                    + DatosCreditoDaoImpl.class
                    .getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_INSERT));
        }
    }

    private SqlParameterSource getParameters(DtoCliente cliente, DtoVendedor vendedor, DtoCredito credito) {
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue(SqlDatosCredito.ID_CREDITOS, credito.getIdCredito(), Types.INTEGER);
        source.addValue(SqlDatosCredito.ID_CLIENTES, cliente.getIdCliente(), Types.INTEGER);
        source.addValue(SqlDatosCredito.ID_VENDEDORES, vendedor.getIdVendedor(), Types.INTEGER);
        return source;
    }

    private static class DatosCreditoRowMapper implements RowMapper<Integer> {

        @Override
        public Integer mapRow(ResultSet rs, int i) throws SQLException {
            return rs.getInt(SqlDatosCredito.ID_CREDITOS);
        }
    }
}
