package mx.com.villavicencio.system.movimientos.abonos.bo.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import mx.com.villavicencio.commons.exception.ApplicationException;
import mx.com.villavicencio.commons.messages.ApplicationMessages;
import mx.com.villavicencio.properties.PropertiesBean;
import mx.com.villavicencio.properties.Property;
import mx.com.villavicencio.system.cliente.bo.ClienteBo;
import mx.com.villavicencio.system.cliente.dto.DtoCliente;
import mx.com.villavicencio.system.credito.credito.factory.CreditoFactory;
import mx.com.villavicencio.system.movimientos.abonos.bo.AbonosBo;
import mx.com.villavicencio.system.movimientos.abonos.dao.AbonosDao;
import mx.com.villavicencio.system.movimientos.abonos.dto.DtoAbonos;
import mx.com.villavicencio.system.movimientos.abonos.factory.AbonosFactory;
import mx.com.villavicencio.system.movimientos.cargos.bo.CargosBo;
import mx.com.villavicencio.system.movimientos.cargos.factory.CargosFactory;
import mx.com.villavicencio.system.movimientos.movimientos.bo.MovimientosBo;
import mx.com.villavicencio.system.movimientos.movimientos.dto.DtoMovimientos;
import mx.com.villavicencio.system.movimientos.movimientos.factory.MovimientosFactory;
import mx.com.villavicencio.system.pedido.datos.bo.DatosPedidoBo;
import mx.com.villavicencio.system.pedido.datos.dto.DtoDatosPedido;
import mx.com.villavicencio.system.pedido.datos.factory.DatosPedidoFactory;
import mx.com.villavicencio.system.pedido.pedido.bo.PedidoBo;
import mx.com.villavicencio.system.pedido.pedido.dto.DtoPedido;
import mx.com.villavicencio.system.pedido.pedido.factory.PedidoFactory;
import mx.com.villavicencio.system.usuario.dto.DtoUsuario;
import mx.com.villavicencio.system.vendedor.bo.VendedorBo;
import mx.com.villavicencio.system.vendedor.dto.DtoVendedor;
import mx.com.villavicencio.system.venta.devoluciones.devoluciones.bo.DevolucionesBo;
import mx.com.villavicencio.system.venta.devoluciones.devoluciones.factory.DevolucionesFactory;
import mx.com.villavicencio.system.venta.nota.bo.NotaVentaBo;
import mx.com.villavicencio.system.venta.nota.dto.DtoNotaVenta;
import mx.com.villavicencio.system.venta.nota.factory.NotaVentaFactory;

/**
 *
 * @author Gabriel J
 */
public class AbonosBoImpl implements AbonosBo {

    private AbonosDao abonosDao;
    private ClienteBo clienteBo;
    private DatosPedidoBo datosPedidoBo;
    private PedidoBo pedidoBo;
    private NotaVentaBo notaVentaBo;
    private MovimientosBo movimientosBo;
    private VendedorBo vendedorBo;
    private CargosBo cargosBo;
    private DevolucionesBo devolucionesBo;

    @Override
    public Collection<DtoCliente> findAllClientes(DtoUsuario user) {
        if ((user != null) && (user.getIdUsuario() != 0)) {
            Collection<DtoCliente> collection = new ArrayList<>();
            for (DtoCliente clientes : this.clienteBo.findAll(user)) {
                DtoDatosPedido datosPedido = DatosPedidoFactory.newInstance();
                datosPedido.setIdCliente(clientes.getIdCliente());
                datosPedido = datosPedidoBo.findById(user, datosPedido);
                if (datosPedido != null) {
                    DtoPedido pedido = PedidoFactory.newInstance(datosPedido.getIdPedido());
                    pedido.setCliente(clientes);
                    pedido = pedidoBo.findById(user, pedido);

                    DtoNotaVenta notaVenta = NotaVentaFactory.newInstance();
                    notaVenta = notaVentaBo.findByIdPedido(user, pedido);

                    DtoMovimientos movimientos = MovimientosFactory.newInstance();
                    movimientos.setNotaVenta(notaVenta);
                    movimientos = movimientosBo.findById(user, movimientos);
                    movimientos.setPedido(pedido);
                    clientes.setMovimiento(movimientos);
                    collection.add(clientes);
                }
            }
            return collection;
        } else {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public Collection<DtoVendedor> findAllVendedores(DtoUsuario user) {
        if ((user != null) && (user.getIdUsuario() != 0)) {
            Collection<DtoVendedor> collection = new ArrayList<>();
            for (DtoVendedor vendedores : this.vendedorBo.findAll(user)) {
                DtoDatosPedido datosPedido = DatosPedidoFactory.newInstance();
                datosPedido.setIdVendedor(vendedores.getIdVendedor());
                datosPedido = datosPedidoBo.findById(user, datosPedido);
                if (datosPedido != null) {
                    DtoPedido pedido = PedidoFactory.newInstance(datosPedido.getIdPedido());
                    pedido.setVendedor(vendedores);
                    pedido = pedidoBo.findById(user, pedido);

                    DtoNotaVenta notaVenta = NotaVentaFactory.newInstance();
                    notaVenta = notaVentaBo.findByIdPedido(user, pedido);

                    DtoMovimientos movimientos = MovimientosFactory.newInstance();
                    movimientos.setNotaVenta(notaVenta);
                    movimientos = movimientosBo.findById(user, movimientos);
                    movimientos.setPedido(pedido);
                    vendedores.setMovimiento(movimientos);
                    collection.add(vendedores);
                }
            }
            return collection;
        } else {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public DtoCliente findByIdCliente(DtoUsuario user, DtoCliente object) {
        if ((user != null) && (user.getIdUsuario() != 0)) {
            Integer idAnteriorPedido = 0;
            Integer idAnteriorVenta = 0;
            Collection<DtoMovimientos> collection = new ArrayList<>();
            object = this.clienteBo.findById(user, object);
            DtoDatosPedido datosPedido = DatosPedidoFactory.newInstance();
            datosPedido.setIdCliente(object.getIdCliente());
            for (DtoDatosPedido pedidos : datosPedidoBo.findDatosById(user, datosPedido)) {
                if (!Objects.equals(idAnteriorPedido, pedidos.getIdPedido())) {
                    DtoPedido pedido = PedidoFactory.newInstance(pedidos.getIdPedido());
                    pedido.setCliente(object);
                    pedido = pedidoBo.findById(user, pedido);
                    DtoNotaVenta notaVenta = NotaVentaFactory.newInstance();
                    notaVenta = notaVentaBo.findByIdPedido(user, pedido);

                    DtoMovimientos movimientos = MovimientosFactory.newInstance();
                    movimientos.setNotaVenta(notaVenta);
                    for (DtoMovimientos movs : movimientosBo.findAll(user, movimientos)) {
                        if (!Objects.equals(idAnteriorVenta, notaVenta.getIdNotaVenta())) {
                            movs.setPedido(pedido);//no modificar
                            movs.setNotaVenta(notaVenta);// no modificar
                            movs.setAbonos(findById(user, movs.getAbonos()));
                            movs.setCargos(cargosBo.findById(user, movs.getCargos()));
                            collection.add(movs);
                            idAnteriorVenta = notaVenta.getIdNotaVenta();
                        }
                    }
                    if (notaVenta.getIdNotaVenta() == null) {
                        movimientos.setNotaVenta(NotaVentaFactory.newInstance());
                        movimientos.setAbonos(AbonosFactory.newInstance());
                        movimientos.setCargos(CargosFactory.newInstance());
                        movimientos.setPedido(pedido);
                        collection.add(movimientos);
                        idAnteriorPedido = pedido.getIdPedido();
                    }
                }
            }
            object.setMovimientos(collection);
            return object;
        } else {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public DtoVendedor findByIdVendedor(DtoUsuario user, DtoVendedor object) {
        if ((user != null) && (user.getIdUsuario() != 0)) {
            Integer idAnteriorPedido = 0;
            Integer idAnteriorVenta = 0;
            Collection<DtoMovimientos> collection = new ArrayList<>();
            object = this.vendedorBo.findById(user, object);
            DtoDatosPedido datosPedido = DatosPedidoFactory.newInstance();
            datosPedido.setIdVendedor(object.getIdVendedor());
            for (DtoDatosPedido pedidos : datosPedidoBo.findDatosById(user, datosPedido)) {
                if (!Objects.equals(idAnteriorPedido, pedidos.getIdPedido())) {
                    DtoPedido pedido = PedidoFactory.newInstance(pedidos.getIdPedido());
                    pedido.setVendedor(object);
                    pedido = pedidoBo.findById(user, pedido);
                    DtoNotaVenta notaVenta = NotaVentaFactory.newInstance();
                    notaVenta = notaVentaBo.findByIdPedido(user, pedido);

                    DtoMovimientos movimientos = MovimientosFactory.newInstance();
                    movimientos.setNotaVenta(notaVenta);
                    for (DtoMovimientos movs : movimientosBo.findAll(user, movimientos)) {
                        if (!Objects.equals(idAnteriorVenta, notaVenta.getIdNotaVenta())) {
                            movs.setPedido(pedido);//no modificar
                            movs.setNotaVenta(notaVenta);// no modificar
                            movs.setAbonos(findById(user, movs.getAbonos()));
                            movs.setCargos(cargosBo.findById(user, movs.getCargos()));
                            movs.setDevoluciones(devolucionesBo.findById(user, movs.getDevoluciones()));
                            collection.add(movs);
                            idAnteriorVenta = notaVenta.getIdNotaVenta();
                        }
                        if ((movs.getAbonos() != null) && (movs.getAbonos().getIdAbonos() != null)) {
                            movs.setAbonos(findById(user, movs.getAbonos()));
                            collection.add(movs);
                        }
                    }
                    if (notaVenta.getIdNotaVenta() == null) {
                        movimientos.setNotaVenta(NotaVentaFactory.newInstance());
                        movimientos.setAbonos(AbonosFactory.newInstance());
                        movimientos.setCargos(CargosFactory.newInstance());
                        movimientos.setPedido(pedido);
                        collection.add(movimientos);
                    }
                    idAnteriorPedido = pedido.getIdPedido();
                }
            }
            object.setMovimientos(collection);
            return object;
        } else {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public Collection<DtoAbonos> findAll(DtoUsuario user) {
        if ((user != null) && (user.getIdUsuario() != 0)) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
        } else {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public DtoAbonos findById(DtoUsuario user, DtoAbonos object) {
        if ((user != null) && (user.getIdUsuario() != 0)) {
            return abonosDao.findById(object);
        } else {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public void ingresar(DtoUsuario user, DtoAbonos object) {
        if ((user != null) && (user.getIdUsuario() != 0)) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
        } else {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public void ingresar(DtoUsuario user, DtoAbonos object, String idNotaVenta) {
        if ((user != null) && (user.getIdUsuario() != 0)) {
            DtoMovimientos movimientos = MovimientosFactory.newInstance();
            movimientos.setNotaVenta(NotaVentaFactory.newInstance(idNotaVenta));
            for (DtoMovimientos movs : movimientosBo.findAll(user, movimientos)) {
                movimientos = movs;
                DtoAbonos findById = this.findById(user, movs.getAbonos());
                movimientos.setAbonos(findById);
                if ((movs.getCredito() != null) && (movs.getCredito().getIdCredito() != null)) {
                    if (movs.getCredito().getIdCredito() == 0) {
                        movimientos.setCredito(CreditoFactory.newInstance());
                    }
                }
                if ((movs.getDevoluciones()!= null) && (movs.getDevoluciones().getIdDevoluciones()!= null)) {
                    if (movs.getDevoluciones().getIdDevoluciones()== 0) {
                        movimientos.setDevoluciones(DevolucionesFactory.newInstance());
                    }
                }
            }
            object = abonosDao.insert(object);
            movimientos.setAbonos(object);
            movimientos.setOpcion(object.getOpcion());
            if (movimientos.getAbonos().getAbono() != null) {
                movimientos.getAbonos().setAbonoAnterior(movimientos.getAbonos().getAbono());
            } 
            movimientosBo.ingresar(user, movimientos);
        } else {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public void modificar(DtoUsuario user, DtoAbonos object
    ) {
        if ((user != null) && (user.getIdUsuario() != 0)) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
        } else {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public void eliminar(DtoUsuario user, DtoAbonos object
    ) {
        if ((user != null) && (user.getIdUsuario() != 0)) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
        } else {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    public void setClienteBo(ClienteBo clienteBo) {
        this.clienteBo = clienteBo;
    }

    public void setDatosPedidoBo(DatosPedidoBo datosPedidoBo) {
        this.datosPedidoBo = datosPedidoBo;
    }

    public void setPedidoBo(PedidoBo pedidoBo) {
        this.pedidoBo = pedidoBo;
    }

    public void setNotaVentaBo(NotaVentaBo notaVentaBo) {
        this.notaVentaBo = notaVentaBo;
    }

    public void setMovimientosBo(MovimientosBo movimientosBo) {
        this.movimientosBo = movimientosBo;
    }

    public void setVendedorBo(VendedorBo vendedorBo) {
        this.vendedorBo = vendedorBo;
    }

    public void setAbonosDao(AbonosDao abonosDao) {
        this.abonosDao = abonosDao;
    }

    public void setCargosBo(CargosBo cargosBo) {
        this.cargosBo = cargosBo;
    }

    public void setDevolucionesBo(DevolucionesBo devolucionesBo) {
        this.devolucionesBo = devolucionesBo;
    }

}
