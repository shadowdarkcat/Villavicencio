package mx.com.villavicencio.system.productos.productos.dto;

import java.math.BigDecimal;
import java.util.Collection;
import mx.com.villavicencio.system.productos.inventario.dto.DtoInventario;

/**
 *
 * @author Gabriel J
 */
public class DtoProducto {

    private Integer idProducto;
    private String claveProducto;
    private String nombreProducto;
    private String peso;
    private BigDecimal pesoMinimo;
    private BigDecimal pesoMaximo;
    private BigDecimal costoUnitario;
    private String imagenProducto = null;
    private Boolean visible;
    private Collection<DtoProducto> personalizados;
    private Integer opcion;
    private BigDecimal comision;
    private Integer piezas;
    private DtoInventario inventario;

    public Integer getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    public String getClaveProducto() {
        return claveProducto;
    }

    public void setClaveProducto(String claveProducto) {
        this.claveProducto = claveProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public BigDecimal getPesoMinimo() {
        return pesoMinimo;
    }

    public void setPesoMinimo(BigDecimal pesoMinimo) {
        this.pesoMinimo = pesoMinimo;
    }

    public BigDecimal getPesoMaximo() {
        return pesoMaximo;
    }

    public void setPesoMaximo(BigDecimal pesoMaximo) {
        this.pesoMaximo = pesoMaximo;
    }

    public BigDecimal getCostoUnitario() {
        return costoUnitario;
    }

    public void setCostoUnitario(BigDecimal costoUnitario) {
        this.costoUnitario = costoUnitario;
    }

    public String getImagenProducto() {
        return imagenProducto;
    }

    public void setImagenProducto(String imagenProducto) {
        this.imagenProducto = imagenProducto;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public Collection<DtoProducto> getPersonalizados() {
        return personalizados;
    }

    public void setPersonalizados(Collection<DtoProducto> personalizados) {
        this.personalizados = personalizados;
    }

    public Integer getOpcion() {
        return opcion;
    }

    public void setOpcion(Integer opcion) {
        this.opcion = opcion;
    }

    public BigDecimal getComision() {
        return comision;
    }

    public void setComision(BigDecimal comision) {
        this.comision = comision;
    }

    public Integer getPiezas() {
        return piezas;
    }

    public void setPiezas(Integer piezas) {
        this.piezas = piezas;
    }

    public DtoInventario getInventario() {
        return inventario;
    }

    public void setInventario(DtoInventario inventario) {
        this.inventario = inventario;
    }
}
