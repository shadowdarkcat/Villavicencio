package mx.com.villavicencio.system.movimientos.bancos.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import mx.com.villavicencio.commons.exception.ApplicationException;
import mx.com.villavicencio.commons.messages.ApplicationMessages;
import mx.com.villavicencio.properties.PropertiesBean;
import mx.com.villavicencio.properties.Property;
import mx.com.villavicencio.system.movimientos.bancos.dao.BancosDao;
import mx.com.villavicencio.system.movimientos.bancos.dao.sql.sql.SqlBancos;
import mx.com.villavicencio.system.movimientos.bancos.dao.sql.view.View;
import mx.com.villavicencio.system.movimientos.bancos.dto.DtoBancos;
import mx.com.villavicencio.system.movimientos.bancos.factory.BancosFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 *
 * @author Gabriel J
 */
public class BancosDaoImpl extends JdbcDaoSupport implements BancosDao {

    @Override
    public Collection<DtoBancos> findAll() {
        try {
            return getJdbcTemplate().query(View.VIEW_BANCOS, new BancosRowMapper());
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND)
                    + "\n" + BancosDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND));
        }
    }

    @Override
    public DtoBancos findById(DtoBancos object) {
        Object[] args = {object.getIdBancos()};
        try {
            return getJdbcTemplate().queryForObject(View.VIEW_BANCO, args, new BancosRowMapper());
        } catch (EmptyResultDataAccessException ex) {
            return BancosFactory.newInstance();
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND)
                    + "\n" + BancosDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND));
        }
    }

    @Override
    public void ingresar(DtoBancos object) {
        ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO) 
                + "\n" + BancosDaoImpl.class.getSimpleName());
        throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
    }

    @Override
    public void modificar(DtoBancos object) {
        ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO)
                + "\n" + BancosDaoImpl.class.getSimpleName());
        throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
    }

    @Override
    public void eliminar(DtoBancos object) {
        ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO) 
                + "\n" + BancosDaoImpl.class.getSimpleName());
        throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
    }

    private static class BancosRowMapper implements RowMapper<DtoBancos> {

        @Override
        public DtoBancos mapRow(ResultSet rs, int i) throws SQLException {
            DtoBancos bancos = BancosFactory.newInstance();
            bancos.setIdBancos(rs.getInt(SqlBancos.ID_BANCOS));
            bancos.setNombre(rs.getString(SqlBancos.NOMBRE_BANCO));
            bancos.setRazonSocial(rs.getString(SqlBancos.RAZON_SOCIAL_BANCO));
            return bancos;
        }
    }
}
