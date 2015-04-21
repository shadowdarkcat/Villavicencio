package mx.com.villavicencio.system.venta.nota.dto;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import mx.com.villavicencio.system.movimientos.movimientos.dto.DtoMovimientos;
import mx.com.villavicencio.system.pedido.pedido.dto.DtoPedido;
import mx.com.villavicencio.system.venta.detalle.dto.DtoDetalleNotaVenta;

/**
 *
 * @author Gabriel J
 */
public class DtoNotaVenta {

    private Integer idNotaVenta;
    private String folio;
    private Date fecha;
    private String nombreCliente;
    private String direccion;
    private String rfc;
    private Collection<DtoDetalleNotaVenta> detalles;
    private BigDecimal total;
    private String cantidadLetra;
    private String leyenda;
    private String observaciones;
    private Character statusNotaVenta;
    private DtoPedido pedido;
    private Collection<DtoMovimientos> movimientos;
    private DtoMovimientos movimiento;
    private Integer opcion;
    private Collection<DtoNotaVenta> notasVenta;

    public Integer getIdNotaVenta() {
        return idNotaVenta;
    }

    public void setIdNotaVenta(Integer idNotaVenta) {
        this.idNotaVenta = idNotaVenta;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public Collection<DtoDetalleNotaVenta> getDetalles() {
        return detalles;
    }

    public void setDetalles(Collection<DtoDetalleNotaVenta> detalles) {
        this.detalles = detalles;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getCantidadLetra() {
        return cantidadLetra;
    }

    public void setCantidadLetra(String cantidadLetra) {
        this.cantidadLetra = cantidadLetra;
    }

    public String getLeyenda() {
        return leyenda;
    }

    public void setLeyenda(String leyenda) {
        this.leyenda = leyenda;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Character getStatusNotaVenta() {
        return statusNotaVenta;
    }

    public void setStatusNotaVenta(Character statusNotaVenta) {
        this.statusNotaVenta = statusNotaVenta;
    }

    public DtoPedido getPedido() {
        return pedido;
    }

    public void setPedido(DtoPedido pedido) {
        this.pedido = pedido;
    }

    public Collection<DtoMovimientos> getMovimientos() {
        return movimientos;
    }

    public void setMovimientos(Collection<DtoMovimientos> movimientos) {
        this.movimientos = movimientos;
    }

    public DtoMovimientos getMovimiento() {
        return movimiento;
    }

    public void setMovimiento(DtoMovimientos movimiento) {
        this.movimiento = movimiento;
    }

    public Integer getOpcion() {
        return opcion;
    }

    public void setOpcion(Integer opcion) {
        this.opcion = opcion;
    }

    public Collection<DtoNotaVenta> getNotasVenta() {
        return notasVenta;
    }

    public void setNotasVenta(Collection<DtoNotaVenta> notasVenta) {
        this.notasVenta = notasVenta;
    }
}
