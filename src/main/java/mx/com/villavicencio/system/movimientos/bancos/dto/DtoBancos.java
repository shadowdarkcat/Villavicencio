package mx.com.villavicencio.system.movimientos.bancos.dto;

/**
 *
 * @author Gabriel J
 */

public class DtoBancos {

    private Integer idBancos;
    private String nombre;
    private String razonSocial;

    public Integer getIdBancos() {
        return idBancos;
    }

    public void setIdBancos(Integer idBancos) {
        this.idBancos = idBancos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }
}
