package mx.com.villavicencio.system.venta.nota.bo.impl;

import java.util.ArrayList;
import java.util.Collection;
import mx.com.villavicencio.commons.exception.ApplicationException;
import mx.com.villavicencio.commons.messages.ApplicationMessages;
import mx.com.villavicencio.properties.PropertiesBean;
import mx.com.villavicencio.properties.Property;
import mx.com.villavicencio.system.cliente.factory.ClienteFactory;
import mx.com.villavicencio.system.credito.credito.bo.CreditoBo;
import mx.com.villavicencio.system.credito.credito.dto.DtoCredito;
import mx.com.villavicencio.system.credito.credito.factory.CreditoFactory;
import mx.com.villavicencio.system.credito.datos.bo.DatosCreditoBo;
import mx.com.villavicencio.system.movimientos.abonos.bo.AbonosBo;
import mx.com.villavicencio.system.movimientos.bancos.bo.BancosBo;
import mx.com.villavicencio.system.movimientos.cargos.bo.CargosBo;
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
import mx.com.villavicencio.system.vendedor.factory.VendedorFactory;
import mx.com.villavicencio.system.venta.detalle.bo.DetalleNotaVentaBo;
import mx.com.villavicencio.system.venta.detalle.factory.DetalleNotaVentaFactory;
import mx.com.villavicencio.system.venta.devoluciones.devoluciones.bo.DevolucionesBo;
import mx.com.villavicencio.system.venta.nota.bo.NotaVentaBo;
import mx.com.villavicencio.system.venta.nota.dao.NotaVentaDao;
import mx.com.villavicencio.system.venta.nota.dto.DtoNotaVenta;
import mx.com.villavicencio.utils.TablesUtils;

/**
 *
 * @author Gabriel J
 */
public class NotaVentaBoImpl implements NotaVentaBo {

    private NotaVentaDao notaVentaDao;
    private DetalleNotaVentaBo detalleNotaVentaBo;
    private MovimientosBo movimientosBo;
    private AbonosBo abonosBo;
    private CreditoBo creditoBo;
    private CargosBo cargosBo;
    private DevolucionesBo devolucionesBo;
    private PedidoBo pedidoBo;
    private BancosBo bancosBo;
    private DatosPedidoBo datosPedidoBo;
    private DatosCreditoBo datosCreditoBo;

    @Override
    public DtoNotaVenta findByIdPedido(DtoUsuario user, DtoPedido object) {
        if ((user != null) && (user.getIdUsuario() != 0)) {
            return notaVentaDao.findByIdPedido(object);
        } else {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public Collection<DtoNotaVenta> findAll(DtoUsuario user) {
        if ((user != null) && (user.getIdUsuario() != 0)) {
            return this.notaVentaDao.findAll();
        } else {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public DtoNotaVenta findById(DtoUsuario user, DtoNotaVenta object) {
        if ((user != null) && (user.getIdUsuario() != 0)) {
            Collection<DtoMovimientos> collection = new ArrayList<>();
            object = this.notaVentaDao.findById(object);
            object.setDetalles(this.detalleNotaVentaBo.findAll(user, DetalleNotaVentaFactory.newInstance(object.getIdNotaVenta())));
            object.setPedido(pedidoBo.findById(user, PedidoFactory.newInstance(object.getPedido().getIdPedido())));
            DtoMovimientos movimientos = MovimientosFactory.newInstance();
            movimientos.setNotaVenta(object);
            DtoDatosPedido datosPedido = DatosPedidoFactory.newInstance();
            datosPedido.setIdPedido(object.getPedido().getIdPedido());
            datosPedido = datosPedidoBo.findById(user, datosPedido);
            object.getPedido().setCliente(ClienteFactory.newInstance());
            object.getPedido().setVendedor(VendedorFactory.newInstance());
            if ((datosPedido.getIdCliente() != null) && (datosPedido.getIdCliente() != 0)) {
                object.getPedido().getCliente().setIdCliente(datosPedido.getIdCliente());
            } else if ((datosPedido.getIdVendedor() != null) && (datosPedido.getIdVendedor() != 0)) {
                object.getPedido().getVendedor().setIdVendedor(datosPedido.getIdVendedor());
            }
            Integer findDatosCreditoById = datosCreditoBo.findDatosCreditoById(user, object.getPedido().getCliente(), object.getPedido().getVendedor());
            if (findDatosCreditoById != null) {
                movimientos.setCredito(CreditoFactory.newInstance(findDatosCreditoById));
                movimientos.setCredito(creditoBo.findById(user, movimientos.getCredito()));
                object.setMovimiento(movimientos);
                TablesUtils.setCreditoBo(creditoBo);
                TablesUtils.setUser(user);
                TablesUtils.setNotaVentaBo(this);
            }
            for (DtoMovimientos movs : movimientosBo.findAll(user, movimientos)) {
                movs.setCargos(cargosBo.findById(user, movs.getCargos()));
                movs.setAbonos(abonosBo.findById(user, movs.getAbonos()));
                movs.getAbonos().setBancos(bancosBo.findById(user, movs.getAbonos().getBancos()));
                movs.setCredito(creditoBo.findById(user, movs.getCredito()));
                movs.setDevoluciones(devolucionesBo.findById(user, movs.getDevoluciones()));
                collection.add(movs);
            }
            object.setMovimientos(collection);
            TablesUtils.setDetalleNotaVentaBo(detalleNotaVentaBo);
            return object;
        } else {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public void ingresar(DtoUsuario user, DtoNotaVenta object) {
        if ((user != null) && (user.getIdUsuario() != 0)) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
        } else {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public void modificar(DtoUsuario user, DtoNotaVenta object) {
        if ((user != null) && (user.getIdUsuario() != 0)) {
            this.notaVentaDao.modificar(object);
        } else {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public void eliminar(DtoUsuario user, DtoNotaVenta object) {
        if ((user != null) && (user.getIdUsuario() != 0)) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
        } else {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    public void setNotaVentaDao(NotaVentaDao notaVentaDao) {
        this.notaVentaDao = notaVentaDao;
    }

    public void setMovimientosBo(MovimientosBo movimientosBo) {
        this.movimientosBo = movimientosBo;
    }

    public void setAbonosBo(AbonosBo abonosBo) {
        this.abonosBo = abonosBo;
    }

    public void setCreditoBo(CreditoBo creditoBo) {
        this.creditoBo = creditoBo;
    }

    public void setCargosBo(CargosBo cargosBo) {
        this.cargosBo = cargosBo;
    }

    public void setDevolucionesBo(DevolucionesBo devolucionesBo) {
        this.devolucionesBo = devolucionesBo;
    }

    public void setPedidoBo(PedidoBo pedidoBo) {
        this.pedidoBo = pedidoBo;
    }

    public void setBancosBo(BancosBo bancosBo) {
        this.bancosBo = bancosBo;
    }

    public void setDatosPedidoBo(DatosPedidoBo datosPedidoBo) {
        this.datosPedidoBo = datosPedidoBo;
    }

    public void setDatosCreditoBo(DatosCreditoBo datosCreditoBo) {
        this.datosCreditoBo = datosCreditoBo;
    }

    public void setDetalleNotaVentaBo(DetalleNotaVentaBo detalleNotaVentaBo) {
        this.detalleNotaVentaBo = detalleNotaVentaBo;
    }

}
