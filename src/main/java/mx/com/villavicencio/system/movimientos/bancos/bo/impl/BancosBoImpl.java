package mx.com.villavicencio.system.movimientos.bancos.bo.impl;

import java.util.Collection;
import mx.com.villavicencio.commons.exception.ApplicationException;
import mx.com.villavicencio.properties.PropertiesBean;
import mx.com.villavicencio.properties.Property;
import mx.com.villavicencio.system.movimientos.bancos.bo.BancosBo;
import mx.com.villavicencio.system.movimientos.bancos.dao.BancosDao;
import mx.com.villavicencio.system.movimientos.bancos.dto.DtoBancos;
import mx.com.villavicencio.system.usuario.dto.DtoUsuario;

/**
 *
 * @author Gabriel J
 */
public class BancosBoImpl implements BancosBo {

    private BancosDao bancosDao;

    @Override
    public Collection<DtoBancos> findAll(DtoUsuario user) {
        if ((user != null) && (user.getIdUsuario() != 0)) {
            return bancosDao.findAll();
        } else {
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public DtoBancos findById(DtoUsuario user, DtoBancos object) {
        if ((user != null) && (user.getIdUsuario() != 0)) {
            return bancosDao.findById(object);
        } else {
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public void ingresar(DtoUsuario user, DtoBancos object) {
        if ((user != null) && (user.getIdUsuario() != 0)) {
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
        } else {
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public void modificar(DtoUsuario user, DtoBancos object) {
        if ((user != null) && (user.getIdUsuario() != 0)) {
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
        } else {
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public void eliminar(DtoUsuario user, DtoBancos object) {
        if ((user != null) && (user.getIdUsuario() != 0)) {
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
        } else {
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    public void setBancosDao(BancosDao bancosDao) {
        this.bancosDao = bancosDao;
    }
}
