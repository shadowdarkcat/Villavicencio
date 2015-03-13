package mx.com.villavicencio.system.liquidacion.liquidacion.dto;

import java.util.Collection;
import java.util.Date;
import mx.com.villavicencio.system.liquidacion.detalle.dto.DtoDetalleLiquidacion;

/**
 *
 * @author Gabriel J
 */
public class DtoLiquidacion {

    private Integer idLiquidacion;
    private String folio;
    private Date fecha;
    private String nombreVendedor;
    private String observaciones;
    private Collection<DtoDetalleLiquidacion> detalleLiquidacion;
    private Integer opcion;

    public Integer getIdLiquidacion() {
        return idLiquidacion;
    }

    public void setIdLiquidacion(Integer idLiquidacion) {
        this.idLiquidacion = idLiquidacion;
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

    public String getNombreVendedor() {
        return nombreVendedor;
    }

    public void setNombreVendedor(String nombreVendedor) {
        this.nombreVendedor = nombreVendedor;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Collection<DtoDetalleLiquidacion> getDetalleLiquidacion() {
        return detalleLiquidacion;
    }

    public void setDetalleLiquidacion(Collection<DtoDetalleLiquidacion> detalleLiquidacion) {
        this.detalleLiquidacion = detalleLiquidacion;
    }

    public Integer getOpcion() {
        return opcion;
    }

    public void setOpcion(Integer opcion) {
        this.opcion = opcion;
    }
}
