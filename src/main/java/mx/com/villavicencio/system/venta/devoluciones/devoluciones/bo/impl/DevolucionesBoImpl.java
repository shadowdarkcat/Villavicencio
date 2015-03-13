package mx.com.villavicencio.system.venta.devoluciones.devoluciones.bo.impl;

import java.util.Collection;
import mx.com.villavicencio.commons.exception.ApplicationException;
import mx.com.villavicencio.properties.PropertiesBean;
import mx.com.villavicencio.properties.Property;
import mx.com.villavicencio.system.usuario.dto.DtoUsuario;
import mx.com.villavicencio.system.venta.devoluciones.detalle.bo.DetalleDevolucionesBo;
import mx.com.villavicencio.system.venta.devoluciones.detalle.dto.DtoDetalleDevoluciones;
import mx.com.villavicencio.system.venta.devoluciones.detalle.factory.DetalleDevolucionesFactory;
import mx.com.villavicencio.system.venta.devoluciones.devoluciones.bo.DevolucionesBo;
import mx.com.villavicencio.system.venta.devoluciones.devoluciones.dao.DevolucionesDao;
import mx.com.villavicencio.system.venta.devoluciones.devoluciones.dto.DtoDevoluciones;

/**
 *
 * @author Gabriel J
 */
public class DevolucionesBoImpl implements DevolucionesBo {

    private DevolucionesDao devolucionesDao;
    private DetalleDevolucionesBo detalleDevolucionesBo;

    @Override
    public Collection<DtoDevoluciones> findAll(DtoUsuario user) {
        if ((user != null) && (user.getIdUsuario() != 0)) {
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
        } else {
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public DtoDevoluciones findById(DtoUsuario user, DtoDevoluciones object) {
        if ((user != null) && (user.getIdUsuario() != 0)) {
            object = devolucionesDao.findById(object);
            DtoDetalleDevoluciones devoluciones = DetalleDevolucionesFactory.newInstance(object.getIdDevoluciones());
            object.setDetalles(detalleDevolucionesBo.findAll(user, devoluciones));
            return object;
        } else {
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public void ingresar(DtoUsuario user, DtoDevoluciones object) {
        if ((user != null) && (user.getIdUsuario() != 0)) {
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
        } else {
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public void modificar(DtoUsuario user, DtoDevoluciones object) {
        if ((user != null) && (user.getIdUsuario() != 0)) {
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
        } else {
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public void eliminar(DtoUsuario user, DtoDevoluciones object) {
        if ((user != null) && (user.getIdUsuario() != 0)) {
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
        } else {
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    public void setDevolucionesDao(DevolucionesDao devolucionesDao) {
        this.devolucionesDao = devolucionesDao;
    }

    public void setDetalleDevolucionesBo(DetalleDevolucionesBo detalleDevolucionesBo) {
        this.detalleDevolucionesBo = detalleDevolucionesBo;
    }
}
