package mx.com.villavicencio.system.pedido.pedido.bo.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import mx.com.villavicencio.commons.exception.ApplicationException;
import mx.com.villavicencio.commons.messages.ApplicationMessages;
import mx.com.villavicencio.generics.types.GenericTypes;
import mx.com.villavicencio.properties.PropertiesBean;
import mx.com.villavicencio.properties.Property;
import mx.com.villavicencio.system.cliente.bo.ClienteBo;
import mx.com.villavicencio.system.cliente.dto.DtoCliente;
import mx.com.villavicencio.system.cliente.factory.ClienteFactory;
import mx.com.villavicencio.system.pedido.datos.bo.DatosPedidoBo;
import mx.com.villavicencio.system.pedido.datos.dto.DtoDatosPedido;
import mx.com.villavicencio.system.pedido.datos.factory.DatosPedidoFactory;
import mx.com.villavicencio.system.pedido.detalles.bo.DetalleBo;
import mx.com.villavicencio.system.pedido.detalles.dto.DtoDetallePedido;
import mx.com.villavicencio.system.pedido.detalles.factory.DetallePedidoFactory;
import mx.com.villavicencio.system.pedido.pedido.bo.PedidoBo;
import mx.com.villavicencio.system.pedido.pedido.dao.PedidoDao;
import mx.com.villavicencio.system.pedido.pedido.dto.DtoPedido;
import mx.com.villavicencio.system.pedido.pedido.factory.PedidoFactory;
import mx.com.villavicencio.system.usuario.dto.DtoUsuario;
import mx.com.villavicencio.system.vendedor.bo.VendedorBo;
import mx.com.villavicencio.system.vendedor.dto.DtoVendedor;
import mx.com.villavicencio.system.vendedor.factory.VendedorFactory;
import mx.com.villavicencio.utils.DateUtils;
import mx.com.villavicencio.utils.NumberUtils;

/**
 *
 * @author Gabriel J
 */
public class PedidoBoImpl implements PedidoBo {

    private PedidoDao pedidoDao;
    private DetalleBo detalleBo;
    private DatosPedidoBo datosBo;
    private ClienteBo clienteBo;
    private VendedorBo vendedorBo;

    @Override
    public Collection<DtoPedido> findAll(DtoUsuario user) {
        if ((user.getIdUsuario() != 0) && (user != null)) {
            if (user.getIdVendedor() == 1) {
                Collection<DtoPedido> collection = new ArrayList<>();
                Collection<DtoDetallePedido> collectionPedido = new ArrayList<>();
                for (DtoPedido pedidos : this.pedidoDao.findAll()) {
                    DtoDatosPedido datos = DatosPedidoFactory.newInstance();
                    DtoPedido pedido = pedidos;
                    datos.setIdPedido(pedido.getIdPedido());
                    datos = this.datosBo.findById(user, datos);
                    if ((datos.getIdCliente() != null) && (datos.getIdCliente() != 0)) {
                        DtoCliente cliente = ClienteFactory.newInstance(datos.getIdCliente());
                        pedido.setCliente(this.clienteBo.findById(user, cliente));
                    } else if ((datos.getIdVendedor() != null) && (datos.getIdVendedor() != 0)) {
                        DtoVendedor vendedor = VendedorFactory.newInstance(datos.getIdVendedor());
                        pedido.setVendedor(this.vendedorBo.findById(user, vendedor));
                    }
                    for (DtoDetallePedido detalles : this.detalleBo.findAll(user)) {
                        if (Objects.equals(detalles.getIdDetallePedido(), datos.getIdDetallePedido())) {
                            collectionPedido.add(detalles);
                        }
                    }
                    pedido.setDetalles(collectionPedido);
                    collection.add(pedido);
                }
                return collection;
            } else {
                Collection<DtoPedido> collection = new ArrayList<>();
                for (DtoPedido pedidos : this.pedidoDao.findAll()) {
                    if (pedidos.getNombreVendedor().equals(user.getNombreCompleto())) {
                        DtoDatosPedido datos = DatosPedidoFactory.newInstance();
                        DtoPedido pedido = pedidos;
                        datos.setIdPedido(pedido.getIdPedido());
                        datos = this.datosBo.findById(user, datos);
                        if ((datos.getIdCliente() != null) && (datos.getIdCliente() != 0)) {
                            DtoCliente cliente = ClienteFactory.newInstance(datos.getIdCliente());
                            pedido.setCliente(this.clienteBo.findById(user, cliente));
                        }
                        DtoVendedor vendedor = VendedorFactory.newInstance(datos.getIdVendedor());
                        pedido.setVendedor(this.vendedorBo.findById(user, vendedor));
                        collection.add(pedido);
                    }
                }
                return collection;
            }
        } else {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public DtoPedido findById(DtoUsuario user, DtoPedido object) {
        if ((user.getIdUsuario() != 0) && (user != null)) {
            Collection<DtoDetallePedido> collection = new ArrayList<>();
            DtoCliente cliente = object.getCliente();
            DtoVendedor vendedor = object.getVendedor();
            object = this.pedidoDao.findById(object);
            object.setCliente(cliente);
            object.setVendedor(vendedor);
            DtoDatosPedido datos = DatosPedidoFactory.newInstance();
            if ((object.getCliente() != null) && (object.getCliente().getIdCliente() != null)) {
                datos.setIdCliente(object.getCliente().getIdCliente());
                object.setCliente(this.clienteBo.findById(user, object.getCliente()));

            } else if ((object.getVendedor() != null) && (object.getVendedor().getIdVendedor() != null)) {
                datos.setIdVendedor(object.getVendedor().getIdVendedor());
                object.setVendedor(this.vendedorBo.findById(user, object.getVendedor()));
            }
            datos.setIdPedido(object.getIdPedido());
            Collection<DtoDatosPedido> findDatosById = this.datosBo.findDatosById(user, datos);
            for (DtoDatosPedido detalles : findDatosById) {
                DtoDetallePedido detalle = DetallePedidoFactory.newInstance(detalles.getIdDetallePedido());
                detalle.setComision(NumberUtils.convertPorcentajeToNumber(detalle.getComision()));
                collection.add(this.detalleBo.findById(user, detalle));
                if ((detalles.getIdCliente() != null) && (detalles.getIdCliente() != 0)) {
                    object.getCliente().setIdCliente(detalles.getIdCliente());
                    object.setCliente(this.clienteBo.findById(user, object.getCliente()));
                } else if ((detalles.getIdVendedor() != null) && (detalles.getIdVendedor() != 0)) {
                    object.getVendedor().setIdVendedor(detalles.getIdVendedor());
                    object.setVendedor(this.vendedorBo.findById(user, object.getVendedor()));
                }
            }
            object.setDetalles(collection);
            return object;
        } else {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public void ingresar(DtoUsuario user, DtoPedido object) {
        if ((user.getIdUsuario() != 0) && (user != null)) {
            object = this.pedidoDao.insert(object);
            for (DtoDetallePedido detalle : object.getDetalles()) {
                DtoDatosPedido datos = DatosPedidoFactory.newInstance();
                detalle.setOpcion(object.getOpcion());
                detalle = this.detalleBo.insert(user, detalle);
                datos.setIdCliente(object.getCliente().getIdCliente());
                datos.setIdDetallePedido(detalle.getIdDetallePedido());
                datos.setIdPedido(object.getIdPedido());
                datos.setIdVendedor(object.getVendedor().getIdVendedor());
                this.datosBo.ingresar(user, datos);
            }
        } else {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public void modificar(DtoUsuario user, DtoPedido object) {
        if ((user.getIdUsuario() != 0) && (user != null)) {
            Collection<DtoDetallePedido> findAll = this.detalleBo.findAll(user);
            for (DtoDetallePedido request : object.getDetalles()) {
                for (DtoDetallePedido detalles : findAll) {
                    Integer opcion = object.getOpcion();
                    if (Objects.equals(request.getIdDetallePedido(), detalles.getIdDetallePedido())) {
                        request.setOpcion(opcion);
                        this.detalleBo.modificar(user, request);
                        break;
                    } else if (null == request.getIdDetallePedido()) {
                        DtoDatosPedido datos = DatosPedidoFactory.newInstance();
                        request.setOpcion(GenericTypes.INSERT);
                        request = this.detalleBo.insert(user, request);
                        datos.setIdCliente(object.getCliente().getIdCliente());
                        datos.setIdDetallePedido(request.getIdDetallePedido());
                        datos.setIdPedido(object.getIdPedido());
                        datos.setIdVendedor(object.getVendedor().getIdVendedor());
                        this.datosBo.ingresar(user, datos);
                        object.setOpcion(opcion);
                        break;
                    }
                }
            }
        } else {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public void eliminar(DtoUsuario user, DtoPedido object) {
        if ((user.getIdUsuario() != 0) && (user != null)) {
            this.pedidoDao.eliminar(object);
        } else {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public void deleteProducto(DtoUsuario user, DtoPedido object) {
        if ((user.getIdUsuario() != 0) && (user != null)) {
            this.pedidoDao.deleteProducto(object);
        } else {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public DtoPedido createFolio(DtoUsuario user) {
        DtoPedido pedido = PedidoFactory.newInstance();
        DtoVendedor vendedor = VendedorFactory.newInstance(user.getIdVendedor());
        pedido.setVendedor(vendedorBo.findById(user, vendedor));
        pedido.setFolio(this.pedidoDao.createFolio());
        pedido.setStrFecha(DateUtils.dateToString(DateUtils.dateNow()));
        return pedido;
    }

    public void setPedidoDao(PedidoDao pedidoDao) {
        this.pedidoDao = pedidoDao;
    }

    public void setDetalleBo(DetalleBo detalleBo) {
        this.detalleBo = detalleBo;
    }

    public void setDatosBo(DatosPedidoBo datosBo) {
        this.datosBo = datosBo;
    }

    public ClienteBo getClienteBo() {
        return clienteBo;
    }

    public void setClienteBo(ClienteBo clienteBo) {
        this.clienteBo = clienteBo;
    }

    public VendedorBo getVendedorBo() {
        return vendedorBo;
    }

    public void setVendedorBo(VendedorBo vendedorBo) {
        this.vendedorBo = vendedorBo;
    }

}
