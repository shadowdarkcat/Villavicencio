package mx.com.villavicencio.system.productos.productos.bo.impl;

import java.util.ArrayList;
import java.util.Collection;
import mx.com.villavicencio.commons.exception.ApplicationException;
import mx.com.villavicencio.commons.messages.ApplicationMessages;
import mx.com.villavicencio.properties.PropertiesBean;
import mx.com.villavicencio.properties.Property;
import mx.com.villavicencio.system.productos.inventario.bo.InventarioBo;
import mx.com.villavicencio.system.productos.inventario.dto.DtoInventario;
import mx.com.villavicencio.system.productos.inventario.factory.InventarioFactory;
import mx.com.villavicencio.system.productos.productos.bo.ProductoBo;
import mx.com.villavicencio.system.productos.productos.dao.ProductoDao;
import mx.com.villavicencio.system.productos.productos.dto.DtoProducto;
import mx.com.villavicencio.system.usuario.dto.DtoUsuario;

/**
 *
 * @author Gabriel J
 */
public class ProductoBoImpl implements ProductoBo {

    private ProductoDao productoDao;
    private InventarioBo inventarioBo;

    @Override
    public Boolean verifyExist(DtoUsuario user, DtoProducto object) {
        if ((user.getIdUsuario() != 0) && (user != null)) {
            return this.productoDao.verifyExist(object);
        } else {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public Collection<DtoProducto> findAll(DtoUsuario user) {
        if ((user.getIdUsuario() != 0) && (user != null)) {
            Collection<DtoProducto> collection = new ArrayList<>();
            for (DtoProducto productos : this.productoDao.findAll()) {
                DtoInventario inventario = InventarioFactory.newInstance();
                inventario.setNombreProducto(productos.getNombreProducto());
                productos.setInventario(this.inventarioBo.findById(user, inventario));
                collection.add(productos);
            }
            return collection;
        } else {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public DtoProducto findById(DtoUsuario user, DtoProducto object) {
        if ((user.getIdUsuario() != 0) && (user != null)) {
            return this.productoDao.findById(object);
        } else {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public void ingresar(DtoUsuario user, DtoProducto object) {
        if ((user.getIdUsuario() != 0) && (user != null)) {
            this.productoDao.ingresar(object);
        } else {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public DtoProducto insertPersonalizado(DtoUsuario user, DtoProducto personalizado) {
        if ((user.getIdUsuario() != 0) && (user != null)) {
            return this.productoDao.insertPersonalizado(personalizado);
        } else {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public void modificar(DtoUsuario user, DtoProducto object) {
        if ((user.getIdUsuario() != 0) && (user != null)) {
            this.productoDao.modificar(object);
        } else {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public void eliminar(DtoUsuario user, DtoProducto object) {
        if ((user.getIdUsuario() != 0) && (user != null)) {
            this.productoDao.eliminar(object);
        } else {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public Collection<DtoProducto> findDatosProductoEstablecidoVendedorById(DtoUsuario user, Integer id) {
        if ((user.getIdUsuario() != 0) && (user != null)) {
            return this.productoDao.findDatosProductoEstablecidoVendedorById(id);
        } else {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public Collection<DtoProducto> findDatosProductoPersonalizadoVendedorById(DtoUsuario user, Integer id) {
        if ((user.getIdUsuario() != 0) && (user != null)) {
            return this.productoDao.findDatosProductoPersonalizadoVendedorById(id);
        } else {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public DtoProducto findProductoPersonalizadoVendedorById(DtoUsuario user, DtoProducto producto, Integer id) {
        if ((user.getIdUsuario() != 0) && (user != null)) {
            return this.productoDao.findProductoPersonalizadoVendedorById(producto, id);
        } else {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public Collection<DtoProducto> findDatosProductoEstablecidoClienteById(DtoUsuario user, Integer id) {
        if ((user.getIdUsuario() != 0) && (user != null)) {
            return this.productoDao.findDatosProductoEstablecidoClienteById(id);
        } else {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public Collection<DtoProducto> findDatosProductoPersonalizadoClienteById(DtoUsuario user, Integer id) {
        if ((user.getIdUsuario() != 0) && (user != null)) {
            return this.productoDao.findDatosProductoPersonalizadoClienteById(id);
        } else {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public DtoProducto findProductoPersonalizadoClienteById(DtoUsuario user, DtoProducto producto, Integer id) {
        if ((user.getIdUsuario() != 0) && (user != null)) {
            return this.productoDao.findProductoPersonalizadoClienteById(producto, id);
        } else {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    public void setProductoDao(ProductoDao productoDao) {
        this.productoDao = productoDao;
    }

    public InventarioBo getInventarioBo() {
        return inventarioBo;
    }

    public void setInventarioBo(InventarioBo inventarioBo) {
        this.inventarioBo = inventarioBo;
    }
}
