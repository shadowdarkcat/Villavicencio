package mx.com.villavicencio.system.credito.datos.bo.impl;

import mx.com.villavicencio.commons.exception.ApplicationException;
import mx.com.villavicencio.commons.messages.ApplicationMessages;
import mx.com.villavicencio.properties.PropertiesBean;
import mx.com.villavicencio.properties.Property;
import mx.com.villavicencio.system.cliente.dto.DtoCliente;
import mx.com.villavicencio.system.credito.datos.bo.DatosCreditoBo;
import mx.com.villavicencio.system.credito.datos.dao.DatosCreditoDao;
import mx.com.villavicencio.system.credito.credito.dto.DtoCredito;
import mx.com.villavicencio.system.usuario.dto.DtoUsuario;
import mx.com.villavicencio.system.vendedor.dto.DtoVendedor;

/**
 *
 * @author Gabriel J
 */
public class DatosCreditoBoImpl implements DatosCreditoBo {

    private DatosCreditoDao datosCreditoDao;

    @Override
    public void insertDatosCredito(DtoUsuario user, DtoCliente cliente, DtoVendedor vendedor, DtoCredito credito) {
        if ((user.getIdUsuario() != 0) && (user != null)) {
            this.datosCreditoDao.insertDatosCredito(cliente, vendedor, credito);
        } else {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }
    @Override
    public Integer findDatosCreditoById(DtoUsuario user, DtoCliente cliente, DtoVendedor vendedor){
        if ((user.getIdUsuario() != 0) && (user != null)) {
            return this.datosCreditoDao.findDatosCreditoById(cliente, vendedor);
        } else {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }
    public void setDatosCreditoDao(DatosCreditoDao datosCreditoDao) {
        this.datosCreditoDao = datosCreditoDao;
    }

}
