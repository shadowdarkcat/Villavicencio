package mx.com.villavicencio.system.liquidacion.liquidacion.bo.impl;

import java.util.ArrayList;
import java.util.Collection;
import mx.com.villavicencio.commons.exception.ApplicationException;
import mx.com.villavicencio.commons.messages.ApplicationMessages;
import mx.com.villavicencio.properties.PropertiesBean;
import mx.com.villavicencio.properties.Property;
import mx.com.villavicencio.system.liquidacion.detalle.bo.DetalleLiquidacionBo;
import mx.com.villavicencio.system.liquidacion.detalle.dto.DtoDetalleLiquidacion;
import mx.com.villavicencio.system.liquidacion.detalle.factory.DetalleLiquidacionFactory;
import mx.com.villavicencio.system.liquidacion.liquidacion.bo.LiquidacionBo;
import mx.com.villavicencio.system.liquidacion.liquidacion.dao.LiquidacionDao;
import mx.com.villavicencio.system.liquidacion.liquidacion.dto.DtoLiquidacion;
import mx.com.villavicencio.system.usuario.dto.DtoUsuario;

/**
 *
 * @author Gabriel J
 */
public class LiquidacionBoImpl implements LiquidacionBo {

    private LiquidacionDao liquidacionDao;
    private DetalleLiquidacionBo detalleLiquidacionBo;

    @Override
    public Collection<DtoLiquidacion> findAll(DtoUsuario user) {
        if ((user.getIdUsuario() != 0) && (user != null)) {
            Collection<DtoLiquidacion> collection = new ArrayList<>();
            for (DtoLiquidacion liquidaciones : this.liquidacionDao.findAll()) {
                DtoDetalleLiquidacion detalle = DetalleLiquidacionFactory.newInstance();
                detalle.setIdLiquidacion(liquidaciones.getIdLiquidacion());
                liquidaciones.setDetalleLiquidacion(this.detalleLiquidacionBo.findAll(user, detalle));
                collection.add(liquidaciones);
            }
            return collection;
        } else {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public DtoLiquidacion findById(DtoUsuario user, DtoLiquidacion object) {
        if ((user.getIdUsuario() != 0) && (user != null)) {
            object = this.liquidacionDao.findById(object);
            DtoDetalleLiquidacion detalle = DetalleLiquidacionFactory.newInstance();
            detalle.setIdLiquidacion(object.getIdLiquidacion());
            object.setDetalleLiquidacion(this.detalleLiquidacionBo.findAll(user, detalle));
            return object;
        } else {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public void ingresar(DtoUsuario user, DtoLiquidacion object) {
        if ((user.getIdUsuario() != 0) && (user != null)) {
            object = this.liquidacionDao.insert(object);
            for (DtoDetalleLiquidacion detalles : object.getDetalleLiquidacion()) {
                detalles.setIdLiquidacion(object.getIdLiquidacion());
                detalles.setOpcion(object.getOpcion());
                this.detalleLiquidacionBo.ingresar(user, detalles);
            }
        } else {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public void modificar(DtoUsuario user, DtoLiquidacion object
    ) {
        if ((user.getIdUsuario() != 0) && (user != null)) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
        } else {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public void eliminar(DtoUsuario user, DtoLiquidacion object
    ) {
        if ((user.getIdUsuario() != 0) && (user != null)) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
        } else {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    public LiquidacionDao getLiquidacionDao() {
        return liquidacionDao;
    }

    public void setLiquidacionDao(LiquidacionDao liquidacionDao) {
        this.liquidacionDao = liquidacionDao;
    }

    public DetalleLiquidacionBo getDetalleLiquidacionBo() {
        return detalleLiquidacionBo;
    }

    public void setDetalleLiquidacionBo(DetalleLiquidacionBo detalleLiquidacionBo) {
        this.detalleLiquidacionBo = detalleLiquidacionBo;
    }

}
