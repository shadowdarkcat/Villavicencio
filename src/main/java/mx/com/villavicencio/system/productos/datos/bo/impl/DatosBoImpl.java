package mx.com.villavicencio.system.productos.datos.bo.impl;

import mx.com.villavicencio.commons.exception.ApplicationException;
import mx.com.villavicencio.commons.messages.ApplicationMessages;
import mx.com.villavicencio.properties.PropertiesBean;
import mx.com.villavicencio.properties.Property;
import mx.com.villavicencio.system.cliente.dto.DtoCliente;
import mx.com.villavicencio.system.productos.datos.bo.DatosBo;
import mx.com.villavicencio.system.productos.datos.dao.DatosDao;
import mx.com.villavicencio.system.productos.productos.dto.DtoProducto;
import mx.com.villavicencio.system.usuario.dto.DtoUsuario;
import mx.com.villavicencio.system.vendedor.dto.DtoVendedor;

/**
 *
 * @author Gabriel J
 */
public class DatosBoImpl implements DatosBo {

    private DatosDao datosDao;

    @Override
    public void productoEstablecidoVendedor(DtoUsuario user, DtoVendedor vendedor, DtoProducto producto) {
        if ((user.getIdUsuario() != 0) && (user != null)) {
            datosDao.productoEstablecidoVendedor(vendedor, producto);
        } else {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public void productoPersonalizadoVendedor(DtoUsuario user, DtoVendedor vendedor, DtoProducto producto) {
        if ((user.getIdUsuario() != 0) && (user != null)) {
            datosDao.productoPersonalizadoVendedor(vendedor, producto);
        } else {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public void productoEstablecidoCliente(DtoUsuario user, DtoCliente cliente, DtoProducto producto) {
        if ((user.getIdUsuario() != 0) && (user != null)) {
           this.datosDao.productoEstablecidoCliente(cliente, producto);
        } else {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public void productoPersonalizadoCliente(DtoUsuario user, DtoCliente cliente, DtoProducto producto) {
        if ((user.getIdUsuario() != 0) && (user != null)) {
            this.datosDao.productoPersonalizadoCliente(cliente, producto);
        } else {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public Boolean verifyExistEstablecidoVendedor(DtoUsuario user, DtoVendedor vendedor, DtoProducto producto) {
        if ((user.getIdUsuario() != 0) && (user != null)) {
            return this.datosDao.verifyExistEstablecidoVendedor(vendedor, producto);
        } else {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public Boolean verifyExistPersonalizadoVendedor(DtoUsuario user, DtoVendedor vendedor, DtoProducto producto) {
        if ((user.getIdUsuario() != 0) && (user != null)) {
            return this.datosDao.verifyExistPersonalizadoVendedor(vendedor, producto);
        } else {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public Boolean verifyExistEstablecidoCliente(DtoUsuario user, DtoCliente cliente, DtoProducto producto) {
        if ((user.getIdUsuario() != 0) && (user != null)) {
            return this.datosDao.verifyExistEstablecidoCliente(cliente, producto);
        } else {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public Boolean verifyExistPersonalizadoCliente(DtoUsuario user, DtoCliente cliente, DtoProducto producto) {
        if ((user.getIdUsuario() != 0) && (user != null)) {
            return this.datosDao.verifyExistPersonalizadoCliente(cliente, producto);
        } else {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    public void setDatosDao(DatosDao datosDao) {
        this.datosDao = datosDao;
    }

}
