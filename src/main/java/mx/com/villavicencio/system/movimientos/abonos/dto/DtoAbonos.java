package mx.com.villavicencio.system.movimientos.abonos.dto;

import java.math.BigDecimal;
import java.util.Date;
import mx.com.villavicencio.system.movimientos.bancos.dto.DtoBancos;

/**
 *
 * @author Gabriel J
 */

public class DtoAbonos {

    private Integer idAbonos;
    private Date fechaCaptura;
    private BigDecimal abono;
    private BigDecimal abonoAnterior;
    private String tipoPago;
    private DtoBancos bancos;
    private Integer opcion;
    
    public Integer getIdAbonos() {
        return idAbonos;
    }

    public void setIdAbonos(Integer idAbonos) {
        this.idAbonos = idAbonos;
    }

    public Date getFechaCaptura() {
        return fechaCaptura;
    }

    public void setFechaCaptura(Date fechaCaptura) {
        this.fechaCaptura = fechaCaptura;
    }

    public BigDecimal getAbono() {
        return abono;
    }

    public void setAbono(BigDecimal abono) {
        this.abono = abono;
    }

    public BigDecimal getAbonoAnterior() {
        return abonoAnterior;
    }

    public void setAbonoAnterior(BigDecimal abonoAnterior) {
        this.abonoAnterior = abonoAnterior;
    }

    public String getTipoPago() {
        return tipoPago;
    }

    public void setTipoPago(String tipoPago) {
        this.tipoPago = tipoPago;
    }

    public DtoBancos getBancos() {
        return bancos;
    }

    public void setBancos(DtoBancos bancos) {
        this.bancos = bancos;
    }

    public Integer getOpcion() {
        return opcion;
    }

    public void setOpcion(Integer opcion) {
        this.opcion = opcion;
    }
}
