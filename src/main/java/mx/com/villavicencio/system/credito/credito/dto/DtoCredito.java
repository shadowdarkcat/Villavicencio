package mx.com.villavicencio.system.credito.credito.dto;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Gabriel J
 */

public class DtoCredito {

    private Integer idCredito;
    private String tipoCredito;
    private Date fechaRegistro;
    private Date fechaPago;
    private String folioNota;
    private BigDecimal cantidadMonetaria;
    private String estatusCredito;
    private Integer opcion;

    public Integer getIdCredito() {
        return idCredito;
    }

    public void setIdCredito(Integer idCredito) {
        this.idCredito = idCredito;
    }

    public String getTipoCredito() {
        return tipoCredito;
    }

    public void setTipoCredito(String tipoCredito) {
        this.tipoCredito = tipoCredito;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public String getFolioNota() {
        return folioNota;
    }

    public void setFolioNota(String folioNota) {
        this.folioNota = folioNota;
    }

    public BigDecimal getCantidadMonetaria() {
        return cantidadMonetaria;
    }

    public void setCantidadMonetaria(BigDecimal cantidadMonetaria) {
        this.cantidadMonetaria = cantidadMonetaria;
    }

    public String getEstatusCredito() {
        return estatusCredito;
    }

    public void setEstatusCredito(String estatusCredito) {
        this.estatusCredito = estatusCredito;
    }

    public Integer getOpcion() {
        return opcion;
    }

    public void setOpcion(Integer opcion) {
        this.opcion = opcion;
    }
}
