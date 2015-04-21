package mx.com.villavicencio.system.pedido.pedido.dto;

import java.util.Collection;
import java.util.Date;
import mx.com.villavicencio.system.cliente.dto.DtoCliente;
import mx.com.villavicencio.system.pedido.detalles.dto.DtoDetallePedido;
import mx.com.villavicencio.system.productos.productos.dto.DtoProducto;
import mx.com.villavicencio.system.vendedor.dto.DtoVendedor;
import mx.com.villavicencio.system.venta.nota.dto.DtoNotaVenta;

/**
 *
 * @author Gabriel J
 */
public class DtoPedido {

    private Integer idPedido;
    private Date fecha;
    private String folio;
    private String nombreVendedor;
    private Character statusAlmacen;
    private Collection<DtoDetallePedido> detalles;
    private Integer opcion;
    private DtoCliente cliente;
    private DtoVendedor vendedor;
    private DtoProducto producto;
    private String strFecha;
    private DtoDetallePedido detalle;
    private Collection<DtoPedido> pedidos;
   
    public Integer getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Integer idPedido) {
        this.idPedido = idPedido;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getNombreVendedor() {
        return nombreVendedor;
    }

    public void setNombreVendedor(String nombreVendedor) {
        this.nombreVendedor = nombreVendedor;
    }

    public Character getStatusAlmacen() {
        return statusAlmacen;
    }

    public void setStatusAlmacen(Character statusAlmacen) {
        this.statusAlmacen = statusAlmacen;
    }

    public Collection<DtoDetallePedido> getDetalles() {
        return detalles;
    }

    public void setDetalles(Collection<DtoDetallePedido> detalles) {
        this.detalles = detalles;
    }

    public Integer getOpcion() {
        return opcion;
    }

    public void setOpcion(Integer opcion) {
        this.opcion = opcion;
    }

    public DtoCliente getCliente() {
        return cliente;
    }

    public void setCliente(DtoCliente cliente) {
        this.cliente = cliente;
    }

    public DtoVendedor getVendedor() {
        return vendedor;
    }

    public void setVendedor(DtoVendedor vendedor) {
        this.vendedor = vendedor;
    }

    public DtoProducto getProducto() {
        return producto;
    }

    public void setProducto(DtoProducto producto) {
        this.producto = producto;
    }

    public String getStrFecha() {
        return strFecha;
    }

    public void setStrFecha(String strFecha) {
        this.strFecha = strFecha;
    }

    public DtoDetallePedido getDetalle() {
        return detalle;
    }

    public void setDetalle(DtoDetallePedido detalle) {
        this.detalle = detalle;
    }

    public Collection<DtoPedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(Collection<DtoPedido> pedidos) {
        this.pedidos = pedidos;
    }
}
