package mx.com.villavicencio.system.movimientos.movimientos.dto;

import java.math.BigDecimal;
import java.util.Collection;
import mx.com.villavicencio.system.credito.credito.dto.DtoCredito;
import mx.com.villavicencio.system.movimientos.abonos.dto.DtoAbonos;
import mx.com.villavicencio.system.movimientos.cargos.dto.DtoCargos;
import mx.com.villavicencio.system.pedido.pedido.dto.DtoPedido;
import mx.com.villavicencio.system.venta.devoluciones.devoluciones.dto.DtoDevoluciones;
import mx.com.villavicencio.system.venta.nota.dto.DtoNotaVenta;

/**
 *
 * @author Gabriel J
 */

public class DtoMovimientos {

    private DtoCargos cargos;
    private DtoAbonos abonos;
    private DtoCredito credito;
    private DtoNotaVenta notaVenta;
    private DtoDevoluciones devoluciones;
    private DtoPedido pedido;
    private Integer opcion;
    private Collection<DtoMovimientos> movimientos;
    private Integer noMovimiento;   
    
    public DtoCargos getCargos() {
        return cargos;
    }

    public void setCargos(DtoCargos cargos) {
        this.cargos = cargos;
    }

    public DtoAbonos getAbonos() {
        return abonos;
    }

    public void setAbonos(DtoAbonos abonos) {
        this.abonos = abonos;
    }

    public DtoCredito getCredito() {
        return credito;
    }

    public void setCredito(DtoCredito credito) {
        this.credito = credito;
    }

    public DtoNotaVenta getNotaVenta() {
        return notaVenta;
    }

    public void setNotaVenta(DtoNotaVenta notaVenta) {
        this.notaVenta = notaVenta;
    }

    public DtoDevoluciones getDevoluciones() {
        return devoluciones;
    }

    public void setDevoluciones(DtoDevoluciones devoluciones) {
        this.devoluciones = devoluciones;
    }

    public DtoPedido getPedido() {
        return pedido;
    }

    public void setPedido(DtoPedido pedido) {
        this.pedido = pedido;
    }

    public Integer getOpcion() {
        return opcion;
    }

    public void setOpcion(Integer opcion) {
        this.opcion = opcion;
    }

    public Collection<DtoMovimientos> getMovimientos() {
        return movimientos;
    }

    public void setMovimientos(Collection<DtoMovimientos> movimientos) {
        this.movimientos = movimientos;
    }

    public Integer getNoMovimiento() {
        return noMovimiento;
    }

    public void setNoMovimiento(Integer noMovimiento) {
        this.noMovimiento = noMovimiento;
    }
}
