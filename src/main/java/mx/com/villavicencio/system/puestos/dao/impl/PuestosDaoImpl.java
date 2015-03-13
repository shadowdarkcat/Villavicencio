package mx.com.villavicencio.system.puestos.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import mx.com.villavicencio.commons.exception.ApplicationException;
import mx.com.villavicencio.commons.messages.ApplicationMessages;
import mx.com.villavicencio.properties.Property;
import mx.com.villavicencio.properties.PropertiesBean;
import mx.com.villavicencio.system.puestos.dao.PuestosDao;
import mx.com.villavicencio.system.puestos.dao.sql.sql.SqlPost;
import mx.com.villavicencio.system.puestos.dao.sql.view.View;
import mx.com.villavicencio.system.puestos.dto.DtoPost;
import mx.com.villavicencio.system.puestos.factory.PostFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 *
 * @author Gabriel J
 */
public class PuestosDaoImpl extends JdbcDaoSupport implements PuestosDao {

    @Override
    public Collection<DtoPost> findAll() {
        try {
            return getJdbcTemplate().query(View.PUESTOS, new PostRowMapper());
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND)
                    + PuestosDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND), ex);
        }
    }

    @Override
    public DtoPost findById(DtoPost object) {
        Object[] args = {object.getPostId()};
        try {
            return getJdbcTemplate().queryForObject(View.PUESTO, args, new PostRowMapper());
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND)
                    + PuestosDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND), ex);
        }
    }

    @Override
    public void ingresar(DtoPost object) {
        ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO)
                + PuestosDaoImpl.class.getSimpleName());
        throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
    }

    @Override
    public void modificar(DtoPost object) {
        ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO)
                + PuestosDaoImpl.class.getSimpleName());
        throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
    }

    @Override
    public void eliminar(DtoPost object) {
        ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO)
                + PuestosDaoImpl.class.getSimpleName());
        throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
    }

    private static class PostRowMapper implements RowMapper<DtoPost> {

        @Override
        public DtoPost mapRow(ResultSet rs, int i) throws SQLException {
            DtoPost post = PostFactory.newInstance();
            post.setPostId(rs.getInt(SqlPost.ID_PUESTO));
            post.setPostName(rs.getString(SqlPost.NOMBRE_PUESTO));
            return post;
        }
    }
}
