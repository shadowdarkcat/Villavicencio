package mx.com.villavicencio.system.venta.devoluciones.detalle.dto;

import java.math.BigDecimal;

/**
 *
 * @author Gabriel J
 */

public class DtoDetalleDevoluciones {

    private Integer idDevoluciones;
    private String nombreProducto;
    private Integer cantidadPiezas;
    private BigDecimal cantidadKilos;
    private BigDecimal costo;
    private BigDecimal subtotal;

    public Integer getIdDevoluciones() {
        return idDevoluciones;
    }

    public void setIdDevoluciones(Integer idDevoluciones) {
        this.idDevoluciones = idDevoluciones;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public Integer getCantidadPiezas() {
        return cantidadPiezas;
    }

    public void setCantidadPiezas(Integer cantidadPiezas) {
        this.cantidadPiezas = cantidadPiezas;
    }

    public BigDecimal getCantidadKilos() {
        return cantidadKilos;
    }

    public void setCantidadKilos(BigDecimal cantidadKilos) {
        this.cantidadKilos = cantidadKilos;
    }

    public BigDecimal getCosto() {
        return costo;
    }

    public void setCosto(BigDecimal costo) {
        this.costo = costo;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }
}
