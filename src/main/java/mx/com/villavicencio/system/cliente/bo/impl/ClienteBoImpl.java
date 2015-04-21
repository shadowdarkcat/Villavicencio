package mx.com.villavicencio.system.cliente.bo.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import mx.com.villavicencio.commons.exception.ApplicationException;
import mx.com.villavicencio.commons.messages.ApplicationMessages;
import mx.com.villavicencio.generics.types.GenericTypes;
import mx.com.villavicencio.properties.PropertiesBean;
import mx.com.villavicencio.properties.Property;
import mx.com.villavicencio.system.cliente.bo.ClienteBo;
import mx.com.villavicencio.system.cliente.dao.ClienteDao;
import mx.com.villavicencio.system.cliente.dto.DtoCliente;
import mx.com.villavicencio.system.cliente.factory.ClienteFactory;
import mx.com.villavicencio.system.credito.credito.bo.CreditoBo;
import mx.com.villavicencio.system.credito.datos.bo.DatosCreditoBo;
import mx.com.villavicencio.system.credito.credito.dto.DtoCredito;
import mx.com.villavicencio.system.productos.productos.bo.ProductoBo;
import mx.com.villavicencio.system.productos.datos.bo.DatosBo;
import mx.com.villavicencio.system.productos.productos.dto.DtoProducto;
import mx.com.villavicencio.system.productos.productos.factory.ProductoFactory;
import mx.com.villavicencio.system.usuario.dto.DtoUsuario;
import mx.com.villavicencio.system.vendedor.bo.VendedorBo;
import mx.com.villavicencio.system.vendedor.factory.VendedorFactory;
import mx.com.villavicencio.utils.StringUtils;

/**
 *
 * @author Gabriel J
 */
public class ClienteBoImpl implements ClienteBo {

    private ClienteDao clienteDao;
    private ProductoBo productoBo;
    private CreditoBo creditoBo;
    private DatosBo datosBo;
    private DatosCreditoBo datosCreditoBo;
    private VendedorBo vendedorBo;

    @Override
    public Boolean verifyExistCliente(DtoUsuario user, DtoCliente object) {
        if ((user.getIdUsuario() != 0) && (user != null)) {
            return this.clienteDao.verifyExistCliente(object);
        } else {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public Collection<DtoCliente> findAll(DtoUsuario user) {
        if ((user.getIdUsuario() != 0) && (user != null)) {
            Collection<DtoCliente> collection = new ArrayList<>();
            for (DtoCliente clientes : this.clienteDao.findAll()) {
                clientes.setVendedor(this.vendedorBo.findById(user, clientes.getVendedor()));
                clientes.setEstablecidos(this.productoBo.findDatosProductoEstablecidoClienteById(user, clientes.getIdCliente()));
                clientes.setPersonalizados(this.productoBo.findDatosProductoPersonalizadoClienteById(user, clientes.getIdCliente()));
                collection.add(clientes);
            }
            return collection;
        } else {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public DtoCliente findById(DtoUsuario user, DtoCliente object) {
        if ((user.getIdUsuario() != 0) && (user != null)) {
            object = this.clienteDao.findById(object);
            object.setVendedor(this.vendedorBo.findById(user, object.getVendedor()));
            Collection<DtoProducto> establecido = this.productoBo.findDatosProductoEstablecidoClienteById(user, object.getIdCliente());
            Collection<DtoProducto> personalizado = this.productoBo.findDatosProductoPersonalizadoClienteById(user, object.getIdCliente());
            if ((!establecido.isEmpty()) && (personalizado.isEmpty())) {
                object.setEstablecidos(establecido);
            } else if ((!personalizado.isEmpty()) && (establecido.isEmpty())) {
                object.setPersonalizados(personalizado);
            } else if ((!establecido.isEmpty()) && (!personalizado.isEmpty())) {
                object.setPersonalizados(personalizado);
            }
            object.setEstablecidos(getProductoNuevo(user, object.getEstablecidos()));
            object.setPersonalizados(getProductoNuevoPersonalizado(user, object.getPersonalizados()));
            DtoCredito findCreditoByIdCliente = this.creditoBo.findCreditoByIdCliente(user, object.getCredito(), object.getIdCliente());
            if ((findCreditoByIdCliente != null) && (findCreditoByIdCliente.getIdCredito() != null)) {
                object.setCredito(findCreditoByIdCliente);
            }
            return object;
        } else {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public void ingresar(DtoUsuario user, DtoCliente object) {
        if ((user.getIdUsuario() != 0) && (user != null)) {
            object = this.clienteDao.insert(object);
            insertDatosProductos(user, object);
            if (object.getCredito() != null) {
                if (!StringUtils.isReallyEmptyOrNull(object.getCredito().getTipoCredito())) {
                    object.getCredito().setOpcion(object.getOpcion());
                    object.setCredito(this.creditoBo.insert(user, object.getCredito()));
                    this.datosCreditoBo.insertDatosCredito(user, object, VendedorFactory.newInstance(), object.getCredito());
                }
            }
        } else {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public void modificar(DtoUsuario user, DtoCliente object) {
        if ((user.getIdUsuario() != 0) && (user != null)) {
            this.clienteDao.modificar(object);
            if (object.getCredito() != null) {
                if (!StringUtils.isReallyEmptyOrNull(object.getCredito().getTipoCredito())) {
                    DtoCredito findCreditoByIdCliente = this.creditoBo.findCreditoByIdCliente(user, object.getCredito(), object.getIdCliente());
                    if (findCreditoByIdCliente.getIdCredito() != null) {
                        object.getCredito().setOpcion(object.getOpcion());
                        this.creditoBo.modificar(user, object.getCredito());
                    } else {
                        object.getCredito().setOpcion(GenericTypes.INSERT);
                        object.setCredito(this.creditoBo.insert(user, object.getCredito()));
                        this.datosCreditoBo.insertDatosCredito(user, object, VendedorFactory.newInstance(), object.getCredito());
                    }
                }
            }
            modifyDatosEstablecidos(user, object);
            modifyDatosPersonalizados(user, object);
        } else {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public void eliminar(DtoUsuario user, DtoCliente object) {
        if ((user.getIdUsuario() != 0) && (user != null)) {
            this.clienteDao.eliminar(object);
        } else {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public Collection<DtoCliente> getDataCp(DtoUsuario user, DtoCliente object) {
        if ((user.getIdUsuario() != 0) && (user != null)) {
            return this.clienteDao.getDataCp(object);
        } else {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public DtoCliente findClienteReporteById(DtoUsuario user, DtoCliente object) {
        if ((user.getIdUsuario() != 0) && (user != null)) {
            object = this.clienteDao.findById(object);
            object.setVendedor(this.vendedorBo.findById(user, object.getVendedor()));
            Collection<DtoProducto> establecido = this.productoBo.findDatosProductoEstablecidoClienteById(user, object.getIdCliente());
            Collection<DtoProducto> personalizado = this.productoBo.findDatosProductoPersonalizadoClienteById(user, object.getIdCliente());
            if ((!establecido.isEmpty()) && (personalizado.isEmpty())) {
                object.setEstablecidos(establecido);
            } else if ((!personalizado.isEmpty()) && (establecido.isEmpty())) {
                object.setPersonalizados(personalizado);
            } else if ((!establecido.isEmpty()) && (!personalizado.isEmpty())) {
                object.setPersonalizados(personalizado);
            }
            DtoCredito findCreditoByIdCliente = this.creditoBo.findCreditoByIdCliente(user, object.getCredito(), object.getIdCliente());
            if ((findCreditoByIdCliente != null) && (findCreditoByIdCliente.getIdCredito() != null)) {
                object.setCredito(findCreditoByIdCliente);
            }
            return object;
        } else {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public Collection<DtoCliente> findAllClienteReporte(DtoUsuario user) {
        if ((user.getIdUsuario() != 0) && (user != null)) {
            Collection<DtoCliente> collection = new ArrayList<>();
            for (DtoCliente clientes : this.clienteDao.findAll()) {
                clientes.setVendedor(this.vendedorBo.findById(user, clientes.getVendedor()));
                clientes.setEstablecidos(this.productoBo.findDatosProductoEstablecidoClienteById(user, clientes.getIdCliente()));
                clientes.setPersonalizados(this.productoBo.findDatosProductoPersonalizadoClienteById(user, clientes.getIdCliente()));
                collection.add(clientes);
            }
            return collection;
        } else {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    private void insertDatosProductos(DtoUsuario user, DtoCliente object) {
        if (object.getEstablecidos() != null) {
            for (DtoProducto establecidos : object.getEstablecidos()) {
                this.datosBo.productoEstablecidoCliente(user, object, establecidos);
            }
        } else if (object.getPersonalizados() != null) {
            for (DtoProducto personalizados : object.getPersonalizados()) {
                personalizados.setOpcion(object.getOpcion());
                personalizados = this.productoBo.insertPersonalizado(user, personalizados);
                this.datosBo.productoPersonalizadoCliente(user, object, personalizados);
            }
        }
    }

    private void modifyDatosEstablecidos(DtoUsuario user, DtoCliente object) {
        Integer localOpcion = object.getOpcion();
        if (object.getEstablecidos() != null) {
            for (DtoProducto productos : object.getEstablecidos()) {
                Boolean verifyExist = this.datosBo.verifyExistEstablecidoCliente(user, object, productos);
                if (verifyExist) {
                    this.datosBo.productoEstablecidoCliente(user, object, productos);
                } else {
                    object.setOpcion(GenericTypes.INSERT);
                    this.datosBo.productoEstablecidoCliente(user, object, productos);
                }
                object.setOpcion(localOpcion);
            }
        }
    }

    private void modifyDatosPersonalizados(DtoUsuario user, DtoCliente object) {
        Boolean verifyExist = Boolean.FALSE;
        Integer localOpcion = object.getOpcion();
        if (object.getPersonalizados() != null) {
            for (DtoProducto productos : object.getPersonalizados()) {
                DtoProducto findByIdPersonalizado = this.productoBo.findProductoPersonalizadoClienteById(user, productos, object.getIdCliente());
                if (findByIdPersonalizado != null) {
                    productos.setIdProducto(findByIdPersonalizado.getIdProducto());
                    verifyExist = this.datosBo.verifyExistPersonalizadoCliente(user, object, productos);
                }
                if (verifyExist) {
                    productos.setOpcion(GenericTypes.MODIFY);
                    productos = this.productoBo.insertPersonalizado(user, productos);
                    this.datosBo.productoPersonalizadoCliente(user, object, productos);
                } else {
                    productos.setOpcion(GenericTypes.INSERT);
                    productos = this.productoBo.insertPersonalizado(user, productos);
                    this.datosBo.productoPersonalizadoCliente(user, object, productos);
                }
                object.setOpcion(localOpcion);
                verifyExist = Boolean.FALSE;
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

    @Override
    public Collection<DtoCliente> findByIdVendedor(DtoUsuario user) {
        if ((user.getIdUsuario() != 0) && (user != null)) {
            DtoCliente clienteFind = ClienteFactory.newInstance(user.getIdVendedor());
            Collection<DtoCliente> collection = new ArrayList<>();
            if (user.getIdUsuario() == 1) {
                for (DtoCliente clientes : this.clienteDao.findAll()) {
                    collection.add(getDtoCliente(clientes, user));
                }
            } else {
                for (DtoCliente clientes : this.clienteDao.findCollectionById(clienteFind)) {
                    collection.add(getDtoCliente(clientes, user));
                }
            }
            return collection;
        } else {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    private DtoCliente getDtoCliente(DtoCliente clientes, DtoUsuario user) {
        DtoCliente cliente = ClienteFactory.newInstance();
        cliente.setIdCliente(clientes.getIdCliente());
        cliente.setEmpresa(clientes.getEmpresa());
        cliente.setRazonSocial(clientes.getRazonSocial());
        cliente.setRfc(clientes.getRfc());
        cliente.setCorreoEmpresa(clientes.getCorreoEmpresa());
        cliente.setSitioWeb(clientes.getSitioWeb());
        cliente.setNombre(clientes.getNombre());
        cliente.setApellidoPaterno(clientes.getApellidoPaterno());
        cliente.setApellidoMaterno(clientes.getApellidoMaterno());
        cliente.setRfc(clientes.getRfc());
        cliente.setCalle(clientes.getCalle());
        cliente.setCodigoPostal(clientes.getCodigoPostal());
        cliente.setColonia(clientes.getColonia());
        cliente.setDelegacion(clientes.getDelegacion());
        cliente.setMunicipio(clientes.getMunicipio());
        cliente.setEstado(clientes.getEstado());
        cliente.setCiudad(clientes.getCiudad());
        cliente.setNoExterior(clientes.getNoExterior());
        cliente.setNoInterior(clientes.getNoInterior());
        cliente.setTelefono1(clientes.getTelefono1());
        cliente.setTelefono2(clientes.getTelefono2());
        cliente.setTelefono3(clientes.getTelefono3());
        cliente.setContacto1(clientes.getCorreo1());
        cliente.setContacto2(clientes.getContacto2());
        cliente.setContacto3(clientes.getContacto3());
        cliente.setCorreo1(clientes.getCorreo1());
        cliente.setCorreo2(clientes.getCorreo2());
        cliente.setCorreo3(clientes.getCorreo3());
        cliente.setCredito(clientes.getCredito());
        cliente.setVisible(clientes.getVisible());
        Collection<DtoProducto> establecido = this.productoBo.findDatosProductoEstablecidoClienteById(user, clientes.getIdCliente());
        Collection<DtoProducto> personalizado = this.productoBo.findDatosProductoPersonalizadoClienteById(user, clientes.getIdCliente());
        if ((!establecido.isEmpty()) && (personalizado.isEmpty())) {
            cliente.setEstablecidos(establecido);
        } else if ((!personalizado.isEmpty()) && (establecido.isEmpty())) {
            cliente.setPersonalizados(personalizado);
        } else if ((!establecido.isEmpty()) && (!personalizado.isEmpty())) {
            cliente.setPersonalizados(personalizado);
        }
        return cliente;
    }

    public void setClienteDao(ClienteDao clienteDao) {
        this.clienteDao = clienteDao;
    }

    public void setProductoBo(ProductoBo productoBo) {
        this.productoBo = productoBo;
    }

    public void setCreditoBo(CreditoBo creditoBo) {
        this.creditoBo = creditoBo;
    }

    public void setDatosBo(DatosBo datosBo) {
        this.datosBo = datosBo;
    }

    public void setDatosCreditoBo(DatosCreditoBo datosCreditoBo) {
        this.datosCreditoBo = datosCreditoBo;
    }

    public void setVendedorBo(VendedorBo vendedorBo) {
        this.vendedorBo = vendedorBo;
    }
}
