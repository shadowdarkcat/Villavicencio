package mx.com.villavicencio.system.liquidacion.detalle.dto;

import java.math.BigDecimal;

/**
 *
 * @author Gabriel J
 */

public class DtoDetalleLiquidacion {

    private Integer idDetalleLiquidacion;
    private String nombreProducto;
    private Integer piezasSalida;
    private BigDecimal kilosSalida;
    private Integer piezasDevolucion;
    private BigDecimal kilosDevolucion;
    private BigDecimal kilosTotal;
    private BigDecimal costoUnitario;
    private BigDecimal subtotal;
    private String observaciones;
    private Integer opcion;
    private Integer idLiquidacion;
    
    public Integer getIdDetalleLiquidacion() {
        return idDetalleLiquidacion;
    }

    public void setIdDetalleLiquidacion(Integer idDetalleLiquidacion) {
        this.idDetalleLiquidacion = idDetalleLiquidacion;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public Integer getPiezasSalida() {
        return piezasSalida;
    }

    public void setPiezasSalida(Integer piezasSalida) {
        this.piezasSalida = piezasSalida;
    }

    public BigDecimal getKilosSalida() {
        return kilosSalida;
    }

    public void setKilosSalida(BigDecimal kilosSalida) {
        this.kilosSalida = kilosSalida;
    }

    public Integer getPiezasDevolucion() {
        return piezasDevolucion;
    }

    public void setPiezasDevolucion(Integer piezasDevolucion) {
        this.piezasDevolucion = piezasDevolucion;
    }

    public BigDecimal getKilosDevolucion() {
        return kilosDevolucion;
    }

    public void setKilosDevolucion(BigDecimal kilosDevolucion) {
        this.kilosDevolucion = kilosDevolucion;
    }

    public BigDecimal getKilosTotal() {
        return kilosTotal;
    }

    public void setKilosTotal(BigDecimal kilosTotal) {
        this.kilosTotal = kilosTotal;
    }

    public BigDecimal getCostoUnitario() {
        return costoUnitario;
    }

    public void setCostoUnitario(BigDecimal costoUnitario) {
        this.costoUnitario = costoUnitario;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Integer getOpcion() {
        return opcion;
    }

    public void setOpcion(Integer opcion) {
        this.opcion = opcion;
    }

    public Integer getIdLiquidacion() {
        return idLiquidacion;
    }

    public void setIdLiquidacion(Integer idLiquidacion) {
        this.idLiquidacion = idLiquidacion;
    }
}
