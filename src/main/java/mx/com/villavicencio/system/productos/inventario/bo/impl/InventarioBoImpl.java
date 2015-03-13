package mx.com.villavicencio.system.productos.inventario.bo.impl;

import java.util.Collection;
import mx.com.villavicencio.commons.exception.ApplicationException;
import mx.com.villavicencio.properties.PropertiesBean;
import mx.com.villavicencio.properties.Property;
import mx.com.villavicencio.system.productos.inventario.bo.InventarioBo;
import mx.com.villavicencio.system.productos.inventario.dao.InventarioDao;
import mx.com.villavicencio.system.productos.inventario.dto.DtoInventario;
import mx.com.villavicencio.system.usuario.dto.DtoUsuario;

/**
 *
 * @author Gabriel J
 */
public class InventarioBoImpl implements InventarioBo {

    private InventarioDao inventarioDao;

    @Override
    public Collection<DtoInventario> findAll(DtoUsuario user) {
        if ((user != null) && (user.getIdUsuario() != 0)) {
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
        } else {
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public DtoInventario findById(DtoUsuario user, DtoInventario object) {
        if ((user != null) && (user.getIdUsuario() != 0)) {
            return this.inventarioDao.findById(object);
        } else {
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public void ingresar(DtoUsuario user, DtoInventario object) {
        if ((user != null) && (user.getIdUsuario() != 0)) {
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
        } else {
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public void modificar(DtoUsuario user, DtoInventario object) {
        if ((user != null) && (user.getIdUsuario() != 0)) {
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
        } else {
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public void eliminar(DtoUsuario user, DtoInventario object) {
        if ((user != null) && (user.getIdUsuario() != 0)) {
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
        } else {
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    public void setInventarioDao(InventarioDao inventarioDao) {
        this.inventarioDao = inventarioDao;
    }
}
