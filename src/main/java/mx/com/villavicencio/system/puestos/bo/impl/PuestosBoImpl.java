package mx.com.villavicencio.system.puestos.bo.impl;

import java.util.Collection;
import mx.com.villavicencio.commons.exception.ApplicationException;
import mx.com.villavicencio.commons.messages.ApplicationMessages;
import mx.com.villavicencio.properties.Property;
import mx.com.villavicencio.properties.PropertiesBean;
import mx.com.villavicencio.system.puestos.bo.PuestosBo;
import mx.com.villavicencio.system.puestos.dao.PuestosDao;
import mx.com.villavicencio.system.puestos.dto.DtoPost;
import mx.com.villavicencio.system.usuario.dto.DtoUsuario;

/**
 *
 * @author Gabriel J
 */
public class PuestosBoImpl implements PuestosBo {

    private PuestosDao puestosDao;

    @Override
    public Collection<DtoPost> findAll(DtoUsuario user) {
        if ((user.getIdUsuario() != 0) && (user != null)) {
            return this.puestosDao.findAll();
        } else {
            ApplicationMessages.warnMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO)
                    + PuestosBoImpl.class.getSimpleName());
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public DtoPost findById(DtoUsuario user, DtoPost object) {
        if ((user.getIdUsuario() != 0) && (user != null)) {
            return this.puestosDao.findById(object);
        } else {
            ApplicationMessages.warnMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO)
                    + PuestosBoImpl.class.getSimpleName());
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public void ingresar(DtoUsuario user, DtoPost object) {
        if ((user.getIdUsuario() != 0) && (user != null)) {
            ApplicationMessages.warnMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO)
                    + PuestosBoImpl.class.getSimpleName());
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
        } else {
            ApplicationMessages.warnMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO)
                    + PuestosBoImpl.class.getSimpleName());
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public void modificar(DtoUsuario user, DtoPost object) {
        if ((user.getIdUsuario() != 0) && (user != null)) {
            ApplicationMessages.warnMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO)
                    + PuestosBoImpl.class.getSimpleName());
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
        } else {
            ApplicationMessages.warnMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO)
                    + PuestosBoImpl.class.getSimpleName());
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public void eliminar(DtoUsuario user, DtoPost object) {
        if ((user.getIdUsuario() != 0) && (user != null)) {
            ApplicationMessages.warnMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO)
                    + PuestosBoImpl.class.getSimpleName());
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
        } else {
            ApplicationMessages.warnMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO)
                    + PuestosBoImpl.class.getSimpleName());
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    public void setPuestosDao(PuestosDao puestosDao) {
        this.puestosDao = puestosDao;
    }
}
