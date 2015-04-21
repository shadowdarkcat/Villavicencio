package mx.com.villavicencio.system.vendedor.bo.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import mx.com.villavicencio.commons.exception.ApplicationException;
import mx.com.villavicencio.commons.messages.ApplicationMessages;
import mx.com.villavicencio.generics.types.GenericTypes;
import mx.com.villavicencio.properties.PropertiesBean;
import mx.com.villavicencio.properties.Property;
import mx.com.villavicencio.system.credito.credito.bo.CreditoBo;
import mx.com.villavicencio.system.credito.datos.bo.DatosCreditoBo;
import mx.com.villavicencio.system.productos.productos.bo.ProductoBo;
import mx.com.villavicencio.system.productos.datos.bo.DatosBo;
import mx.com.villavicencio.system.productos.productos.dto.DtoProducto;
import mx.com.villavicencio.system.productos.productos.factory.ProductoFactory;
import mx.com.villavicencio.system.usuario.dto.DtoUsuario;
import mx.com.villavicencio.system.vendedor.bo.VendedorBo;
import mx.com.villavicencio.system.vendedor.dao.VendedorDao;
import mx.com.villavicencio.system.vendedor.dto.DtoVendedor;
import mx.com.villavicencio.system.vendedor.factory.VendedorFactory;

/**
 *
 * @author Gabriel J
 */
public class VendedorBoImpl implements VendedorBo {

    private VendedorDao vendedorDao;
    private ProductoBo productoBo;
    private DatosBo datosBo;

    @Override
    public Boolean verifyExistVendedor(DtoUsuario user, DtoVendedor object) {
        if ((user.getIdUsuario() != 0) && (user != null)) {
            return this.vendedorDao.verifyExistVendedor(object);
        } else {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public Collection<DtoVendedor> findAll(DtoUsuario user) {
        if ((user.getIdUsuario() != 0) && (user != null)) {
            Collection<DtoVendedor> collection = new ArrayList<>();
            for (DtoVendedor vendedores : this.vendedorDao.findAll()) {
                if (!vendedores.getNombre().equalsIgnoreCase("VILLAVICENCIO")) {
                    DtoVendedor vendedor = VendedorFactory.newInstance();                   
                    vendedor.setIdVendedor(vendedores.getIdVendedor());
                    vendedor.setNombre(vendedores.getNombre());
                    vendedor.setApellidoPaterno(vendedores.getApellidoPaterno());
                    vendedor.setApellidoMaterno(vendedores.getApellidoMaterno());
                    vendedor.setRfc(vendedores.getRfc());
                    vendedor.setCalle(vendedores.getCalle());
                    vendedor.setCp(vendedores.getCp());
                    vendedor.setColonia(vendedores.getColonia());
                    vendedor.setDelegacion(vendedores.getDelegacion());
                    vendedor.setMunicipio(vendedores.getMunicipio());
                    vendedor.setEstado(vendedores.getEstado());
                    vendedor.setCiudad(vendedores.getCiudad());
                    vendedor.setNoExterior(vendedores.getNoExterior());
                    vendedor.setNoInterior(vendedores.getNoInterior());
                    vendedor.setTelefono1(vendedores.getTelefono1());
                    vendedor.setTelefono2(vendedores.getTelefono2());
                    vendedor.setTelefono3(vendedores.getTelefono3());
                    vendedor.setCorreo1(vendedores.getCorreo1());
                    vendedor.setCorreo2(vendedores.getCorreo2());
                    vendedor.setCorreo3(vendedores.getCorreo3());
                    vendedor.setComision(vendedores.getComision());
                    vendedor.setVisible(vendedores.getVisible());
                    vendedor.setExterno(vendedores.getExterno());
                    vendedor.setEstablecidos(this.productoBo.findDatosProductoEstablecidoVendedorById(user, vendedores.getIdVendedor()));
                    vendedor.setPersonalizados(this.productoBo.findDatosProductoPersonalizadoVendedorById(user, vendedores.getIdVendedor()));
                    if (vendedor.getPersonalizados() != null) {
                        if (!vendedor.getPersonalizados().isEmpty()) {
                            vendedor.getEstablecidos().clear();
                        }
                    }
                    collection.add(vendedor);
                }
            }
            return collection;
        } else {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public DtoVendedor findById(DtoUsuario user, DtoVendedor object) {
        if ((user.getIdUsuario() != 0) && (user != null)) {
            object = this.vendedorDao.findById(object);
            Collection<DtoProducto> establecido = this.productoBo.findDatosProductoEstablecidoVendedorById(user, object.getIdVendedor());
            Collection<DtoProducto> personalizado = this.productoBo.findDatosProductoPersonalizadoVendedorById(user, object.getIdVendedor());
            if ((!establecido.isEmpty()) && (personalizado.isEmpty())) {
                object.setEstablecidos(establecido);
            } else if ((!personalizado.isEmpty()) && (establecido.isEmpty())) {
                object.setPersonalizados(personalizado);
            } else if ((!establecido.isEmpty()) && (!personalizado.isEmpty())) {
                object.setPersonalizados(personalizado);
            }
            object.setEstablecidos(getProductoNuevo(user, object.getEstablecidos()));
            object.setPersonalizados(getProductoNuevoPersonalizado(user, object.getPersonalizados()));
            //object.setCredito(this.creditoBo.findCreditoByIdVendedor(user, CreditoFactory.newInstance(), object.getIdVendedor()));
            return object;
        } else {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public void ingresar(DtoUsuario user, DtoVendedor object) {
        if ((user.getIdUsuario() != 0) && (user != null)) {
            object = this.vendedorDao.insert(object);
            insertDatosProductos(user, object);
            /* if (object.getCredito() != null) {
             if (!StringUtils.isReallyEmptyOrNull(object.getCredito().getTipoCredito())) {
             object.getCredito().setOpcion(object.getOpcion());
             object.setCredito(this.creditoBo.insert(user, object.getCredito()));
             this.datosCreditoBo.insertDatosCredito(user, ClienteFactory.newInstance(), object, object.getCredito());
             }
             }*/
        } else {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public void modificar(DtoUsuario user, DtoVendedor object) {
        if ((user.getIdUsuario() != 0) && (user != null)) {
            this.vendedorDao.modificar(object);
            modifyDatosEstablecidos(user, object);
            modifyDatosPersonalizados(user, object);
            /*if (object.getCredito() != null) {
             if (!StringUtils.isReallyEmptyOrNull(object.getCredito().getTipoCredito())) {
             object.getCredito().setOpcion(object.getOpcion());
             this.creditoBo.modificar(user, object.getCredito());
             }
             }*/
        } else {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public void eliminar(DtoUsuario user, DtoVendedor object) {
        if ((user.getIdUsuario() != 0) && (user != null)) {
            this.vendedorDao.eliminar(object);
        } else {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public Collection<DtoVendedor> getDataCp(DtoUsuario user, DtoVendedor object) {
        if ((user.getIdUsuario() != 0) && (user != null)) {
            return this.vendedorDao.getDataCp(object);
        } else {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    private void insertDatosProductos(DtoUsuario user, DtoVendedor object) {
        if (object.getEstablecidos() != null) {
            for (DtoProducto establecidos : object.getEstablecidos()) {
                this.datosBo.productoEstablecidoVendedor(user, object, establecidos);
            }
        } else if (object.getPersonalizados() != null) {
            for (DtoProducto personalizados : object.getPersonalizados()) {
                personalizados.setOpcion(object.getOpcion());    
                personalizados = this.productoBo.insertPersonalizado(user, personalizados);
                this.datosBo.productoPersonalizadoVendedor(user, object, personalizados);
            }
        }
    }

    private void modifyDatosEstablecidos(DtoUsuario user, DtoVendedor object) {
        Integer localOpcion = object.getOpcion();
        if (object.getEstablecidos() != null) {
            for (DtoProducto productos : object.getEstablecidos()) {
                Boolean verifyExist = this.datosBo.verifyExistEstablecidoVendedor(user, object, productos);
                if (verifyExist) {
                    this.datosBo.productoEstablecidoVendedor(user, object, productos);
                } else {
                    object.setOpcion(GenericTypes.INSERT);
                    this.datosBo.productoEstablecidoVendedor(user, object, productos);
                }
                object.setOpcion(localOpcion);
            }
        }
    }

    private void modifyDatosPersonalizados(DtoUsuario user, DtoVendedor object) {
        Boolean verifyExist = Boolean.FALSE;
        Integer localOpcion = object.getOpcion();
        if (object.getPersonalizados() != null) {
            for (DtoProducto productos : object.getPersonalizados()) {
                DtoProducto findByIdPersonalizado = this.productoBo.findProductoPersonalizadoVendedorById(user, productos, object.getIdVendedor());
                if (findByIdPersonalizado != null) {
                    productos.setIdProducto(findByIdPersonalizado.getIdProducto());
                    verifyExist = this.datosBo.verifyExistPersonalizadoVendedor(user, object, productos);
                }
                if (verifyExist) {
                    productos.setOpcion(GenericTypes.MODIFY);
                    this.datosBo.productoPersonalizadoVendedor(user, object, productos);
                } else {
                    productos.setOpcion(GenericTypes.INSERT);
                    this.datosBo.productoPersonalizadoVendedor(user, object, productos);
                }
                object.setOpcion(localOpcion);
            }
        }
    }

    private Collection<DtoProducto> getProductoNuevo(DtoUsuario user, Collection<DtoProducto> object) {

        Collection<DtoProducto> findAll = productoBo.findAll(user);
        Integer id = null;
        if (object != null) {
            for (DtoProducto productos : findAll) {
                for (DtoProducto products : object) {
                    id = products.getIdProducto();
                    if (Objects.equals(productos.getIdProducto(), id)) {
                        break;
                    }
                }
                if (!Objects.equals(productos.getIdProducto(), id)) {
                    DtoProducto producto = ProductoFactory.newInstance();
                    producto.setIdProducto(productos.getIdProducto());
                    producto.setClaveProducto(productos.getClaveProducto());
                    producto.setImagenProducto(productos.getImagenProducto());

                    producto.setNombreProducto(productos.getNombreProducto());
                    producto.setPeso(productos.getPeso());
                    producto.setPesoMaximo(productos.getPesoMaximo());
                    producto.setPesoMinimo(productos.getPesoMinimo());
                    producto.setCostoUnitario(productos.getCostoUnitario());
                    producto.setVisible(productos.getVisible());
                    object.add(producto);
                }
            }
        }
        return object;
    }

    private Collection<DtoProducto> getProductoNuevoPersonalizado(DtoUsuario user, Collection<DtoProducto> object) {

        Collection<DtoProducto> findAll = productoBo.findAll(user);
        String clave = null;
        if (object != null) {
            for (DtoProducto productos : findAll) {
                for (DtoProducto products : object) {
                    clave = products.getClaveProducto();
                    if (Objects.equals(productos.getClaveProducto(), clave)) {
                        break;
                    }
                }
                if (!Objects.equals(productos.getClaveProducto(), clave)) {
                    DtoProducto producto = ProductoFactory.newInstance();
                    producto.setIdProducto(productos.getIdProducto());
                    producto.setClaveProducto(productos.getClaveProducto());
                    producto.setImagenProducto(productos.getImagenProducto());

                    producto.setNombreProducto(productos.getNombreProducto());
                    producto.setPeso(productos.getPeso());
                    producto.setPesoMaximo(productos.getPesoMaximo());
                    producto.setPesoMinimo(productos.getPesoMinimo());
                    producto.setCostoUnitario(productos.getCostoUnitario());
                    producto.setVisible(productos.getVisible());
                    object.add(producto);
                }
            }
        }
        return object;
    }

    public void setVendedorDao(VendedorDao vendedorDao) {
        this.vendedorDao = vendedorDao;
    }

    public void setProductoBo(ProductoBo productoBo) {
        this.productoBo = productoBo;
    }

    public void setDatosBo(DatosBo datosBo) {
        this.datosBo = datosBo;
    }
}
