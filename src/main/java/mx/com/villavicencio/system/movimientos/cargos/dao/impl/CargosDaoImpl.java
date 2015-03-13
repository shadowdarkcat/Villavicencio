package mx.com.villavicencio.system.movimientos.cargos.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import mx.com.villavicencio.commons.exception.ApplicationException;
import mx.com.villavicencio.commons.messages.ApplicationMessages;
import mx.com.villavicencio.properties.PropertiesBean;
import mx.com.villavicencio.properties.Property;
import mx.com.villavicencio.system.movimientos.cargos.dao.CargosDao;
import mx.com.villavicencio.system.movimientos.cargos.dao.sql.sql.SqlCargos;
import mx.com.villavicencio.system.movimientos.cargos.dao.sql.view.View;
import mx.com.villavicencio.system.movimientos.cargos.dto.DtoCargos;
import mx.com.villavicencio.system.movimientos.cargos.factory.CargosFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 *
 * @author Gabriel J
 */
public class CargosDaoImpl extends JdbcDaoSupport implements CargosDao {

    @Override
    public Collection<DtoCargos> findAll() {
        ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO)
                + "\n" + CargosDaoImpl.class.getSimpleName());
        throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
    }

    @Override
    public DtoCargos findById(DtoCargos object) {
        Object[] args = {object.getIdCargos()};
        try {
            return getJdbcTemplate().queryForObject(View.VIEW_CARGOS, args, new CargosRowMapper());
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND)
                    + "\n" + CargosDaoImpl.class.getSimpleName());
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND));
        }
    }

    @Override
    public void ingresar(DtoCargos object) {
        ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO)
                + "\n" + CargosDaoImpl.class.getSimpleName());
        throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
    }

    @Override
    public void modificar(DtoCargos object) {
        ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO)
                + "\n" + CargosDaoImpl.class.getSimpleName());
        throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
    }

    @Override
    public void eliminar(DtoCargos object) {
        ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO)
                + "\n" + CargosDaoImpl.class.getSimpleName());
        throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
    }

    private static class CargosRowMapper implements RowMapper<DtoCargos> {

        @Override
        public DtoCargos mapRow(ResultSet rs, int i) throws SQLException {
            DtoCargos cargos = CargosFactory.newInstance();
            cargos.setIdCargos(rs.getInt(SqlCargos.ID_CARGOS));
            cargos.setFechaCargo(rs.getDate(SqlCargos.FECHA_CARGO));
            cargos.setCargo(rs.getBigDecimal(SqlCargos.CARGO));
            cargos.setCargoAnterior(rs.getBigDecimal(SqlCargos.CARGO_ANTERIOR));
            return cargos;
        }
    }
}
