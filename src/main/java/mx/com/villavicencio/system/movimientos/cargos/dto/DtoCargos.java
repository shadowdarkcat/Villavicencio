package mx.com.villavicencio.system.movimientos.cargos.dto;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Gabriel J
 */

public class DtoCargos {

    private Integer opcion;
    private Integer idCargos;
    private Date fechaCargo;
    private BigDecimal cargo;
    private BigDecimal cargoAnterior;

    public Integer getOpcion() {
        return opcion;
    }

    public void setOpcion(Integer opcion) {
        this.opcion = opcion;
    }

    public Integer getIdCargos() {
        return idCargos;
    }

    public void setIdCargos(Integer idCargos) {
        this.idCargos = idCargos;
    }

    public Date getFechaCargo() {
        return fechaCargo;
    }

    public void setFechaCargo(Date fechaCargo) {
        this.fechaCargo = fechaCargo;
    }

    public BigDecimal getCargo() {
        return cargo;
    }

    public void setCargo(BigDecimal cargo) {
        this.cargo = cargo;
    }

    public BigDecimal getCargoAnterior() {
        return cargoAnterior;
    }

    public void setCargoAnterior(BigDecimal cargoAnterior) {
        this.cargoAnterior = cargoAnterior;
    }
}
