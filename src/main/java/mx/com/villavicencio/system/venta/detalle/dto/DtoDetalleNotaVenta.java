package mx.com.villavicencio.system.venta.detalle.dto;

import java.math.BigDecimal;

/**
 *
 * @author Gabriel J
 */

public class DtoDetalleNotaVenta {

    private Integer idNotaVenta;
    private Integer cantidadPiezas;
    private String nombreProducto;
    private BigDecimal cantidadKilos;
    private BigDecimal costoUnitario;
    private BigDecimal subTotal;
    private Boolean isAgotado;
    private Boolean isMuestra;
    private String observaciones;
    
    public Integer getIdNotaVenta() {
        return idNotaVenta;
    }

    public void setIdNotaVenta(Integer idNotaVenta) {
        this.idNotaVenta = idNotaVenta;
    }

    public Integer getCantidadPiezas() {
        return cantidadPiezas;
    }

    public void setCantidadPiezas(Integer cantidadPiezas) {
        this.cantidadPiezas = cantidadPiezas;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public BigDecimal getCantidadKilos() {
        return cantidadKilos;
    }

    public void setCantidadKilos(BigDecimal cantidadKilos) {
        this.cantidadKilos = cantidadKilos;
    }

    public BigDecimal getCostoUnitario() {
        return costoUnitario;
    }

    public void setCostoUnitario(BigDecimal costoUnitario) {
        this.costoUnitario = costoUnitario;
    }

    public BigDecimal getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
    }

    public Boolean getIsAgotado() {
        return isAgotado;
    }

    public void setIsAgotado(Boolean isAgotado) {
        this.isAgotado = isAgotado;
    }

    public Boolean getIsMuestra() {
        return isMuestra;
    }

    public void setIsMuestra(Boolean isMuestra) {
        this.isMuestra = isMuestra;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}
