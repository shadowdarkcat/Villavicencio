package mx.com.villavicencio.system.venta.devoluciones.detalle.bo.impl;

import java.util.Collection;
import mx.com.villavicencio.commons.exception.ApplicationException;
import mx.com.villavicencio.properties.PropertiesBean;
import mx.com.villavicencio.properties.Property;
import mx.com.villavicencio.system.usuario.dto.DtoUsuario;
import mx.com.villavicencio.system.venta.devoluciones.detalle.bo.DetalleDevolucionesBo;
import mx.com.villavicencio.system.venta.devoluciones.detalle.dao.DetalleDevolucionesDao;
import mx.com.villavicencio.system.venta.devoluciones.detalle.dto.DtoDetalleDevoluciones;

/**
 *
 * @author Gabriel J
 */
public class DetalleDevolucionesBoImpl implements DetalleDevolucionesBo {

    private DetalleDevolucionesDao detalleDevolucionesDao;

    @Override
    public Collection<DtoDetalleDevoluciones> findAll(DtoUsuario user, DtoDetalleDevoluciones object) {
        if ((user != null) && (user.getIdUsuario() != 0)) {
            return detalleDevolucionesDao.findAll(object);
        } else {
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public Collection<DtoDetalleDevoluciones> findAll(DtoUsuario user) {
        if ((user != null) && (user.getIdUsuario() != 0)) {
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
        } else {
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public DtoDetalleDevoluciones findById(DtoUsuario user, DtoDetalleDevoluciones object) {
        if ((user != null) && (user.getIdUsuario() != 0)) {
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
        } else {
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public void ingresar(DtoUsuario user, DtoDetalleDevoluciones object) {
        if ((user != null) && (user.getIdUsuario() != 0)) {
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
        } else {
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public void modificar(DtoUsuario user, DtoDetalleDevoluciones object) {
        if ((user != null) && (user.getIdUsuario() != 0)) {
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
        } else {
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public void eliminar(DtoUsuario user, DtoDetalleDevoluciones object) {
        if ((user != null) && (user.getIdUsuario() != 0)) {
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
        } else {
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    public void setDetalleDevolucionesDao(DetalleDevolucionesDao detalleDevolucionesDao) {
        this.detalleDevolucionesDao = detalleDevolucionesDao;
    }

}
