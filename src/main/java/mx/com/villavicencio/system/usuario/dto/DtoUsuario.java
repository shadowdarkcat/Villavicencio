package mx.com.villavicencio.system.usuario.dto;

import java.util.Collection;
import mx.com.villavicencio.system.cliente.dto.DtoCliente;
import mx.com.villavicencio.system.vendedor.dto.DtoVendedor;

/**
 *
 * @author Gabriel J
 */
public class DtoUsuario {

    private Integer idUsuario;
    private String nombreUsuario;
    private String password;
    private String nombreCompleto;
    private String noTelefono;
    private String puesto;
    private Integer opcion;
    private Integer idVendedor;
    private Collection<DtoCliente> clientes;
    private Collection<DtoVendedor> vendedores;
    private Boolean isAdmin;
    private Boolean isExterno;

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getNoTelefono() {
        return noTelefono;
    }

    public void setNoTelefono(String noTelefono) {
        this.noTelefono = noTelefono;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public Integer getOpcion() {
        return opcion;
    }

    public void setOpcion(Integer opcion) {
        this.opcion = opcion;
    }

    public Integer getIdVendedor() {
        return idVendedor;
    }

    public void setIdVendedor(Integer idVendedor) {
        this.idVendedor = idVendedor;
    }

    public Collection<DtoCliente> getClientes() {
        return clientes;
    }

    public void setClientes(Collection<DtoCliente> clientes) {
        this.clientes = clientes;
    }

    public Collection<DtoVendedor> getVendedores() {
        return vendedores;
    }

    public void setVendedores(Collection<DtoVendedor> vendedores) {
        this.vendedores = vendedores;
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public Boolean getIsExterno() {
        return isExterno;
    }

    public void setIsExterno(Boolean isExterno) {
        this.isExterno = isExterno;
    }
}
