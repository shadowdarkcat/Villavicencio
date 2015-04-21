package mx.com.villavicencio.system.vendedor.dto;

import java.math.BigDecimal;
import java.util.Collection;
import mx.com.villavicencio.system.credito.credito.dto.DtoCredito;
import mx.com.villavicencio.system.movimientos.movimientos.dto.DtoMovimientos;
import mx.com.villavicencio.system.productos.productos.dto.DtoProducto;

/**
 *
 * @author Gabriel J
 */
public class DtoVendedor {

    private Integer idVendedor;
    private Integer idPedido;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String rfc;
    private String calle;
    private String cp;
    private String colonia;
    private String delegacion;
    private String municipio;
    private String estado;
    private String ciudad;
    private String noExterior;
    private String noInterior;
    private String telefono1;
    private String telefono2;
    private String telefono3;
    private String correo1;
    private String correo2;
    private String correo3;
    private BigDecimal comision;
    private Boolean visible;
    private Boolean externo;
    private DtoCredito credito;
    private Collection<DtoProducto> establecidos;
    private Collection<DtoProducto> personalizados;
    private Collection<DtoVendedor> vendedores;
    private Integer opcion;
    private Collection<DtoMovimientos> movimientos;
    private DtoMovimientos movimiento;
    private BigDecimal totalAdeudo;
    private BigDecimal totalAbonado;
    private BigDecimal totalFaltante;
    private String table;
    
    public Integer getIdVendedor() {
        return idVendedor;
    }

    public void setIdVendedor(Integer idVendedor) {
        this.idVendedor = idVendedor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getDelegacion() {
        return delegacion;
    }

    public void setDelegacion(String delegacion) {
        this.delegacion = delegacion;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getNoExterior() {
        return noExterior;
    }

    public void setNoExterior(String noExterior) {
        this.noExterior = noExterior;
    }

    public String getNoInterior() {
        return noInterior;
    }

    public void setNoInterior(String noInterior) {
        this.noInterior = noInterior;
    }

    public String getTelefono1() {
        return telefono1;
    }

    public void setTelefono1(String telefono1) {
        this.telefono1 = telefono1;
    }

    public String getTelefono2() {
        return telefono2;
    }

    public void setTelefono2(String telefono2) {
        this.telefono2 = telefono2;
    }

    public String getTelefono3() {
        return telefono3;
    }

    public void setTelefono3(String telefono3) {
        this.telefono3 = telefono3;
    }

    public String getCorreo1() {
        return correo1;
    }

    public void setCorreo1(String correo1) {
        this.correo1 = correo1;
    }

    public String getCorreo2() {
        return correo2;
    }

    public void setCorreo2(String correo2) {
        this.correo2 = correo2;
    }

    public String getCorreo3() {
        return correo3;
    }

    public void setCorreo3(String correo3) {
        this.correo3 = correo3;
    }

    public BigDecimal getComision() {
        return comision;
    }

    public void setComision(BigDecimal comision) {
        this.comision = comision;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public Boolean getExterno() {
        return externo;
    }

    public void setExterno(Boolean externo) {
        this.externo = externo;
    }

    public DtoCredito getCredito() {
        return credito;
    }

    public void setCredito(DtoCredito credito) {
        this.credito = credito;
    }

    public Collection<DtoProducto> getEstablecidos() {
        return establecidos;
    }

    public void setEstablecidos(Collection<DtoProducto> establecidos) {
        this.establecidos = establecidos;
    }

    public Collection<DtoProducto> getPersonalizados() {
        return personalizados;
    }

    public void setPersonalizados(Collection<DtoProducto> personalizados) {
        this.personalizados = personalizados;
    }

    public Collection<DtoVendedor> getVendedores() {
        return vendedores;
    }

    public void setVendedores(Collection<DtoVendedor> vendedores) {
        this.vendedores = vendedores;
    }

    public Integer getOpcion() {
        return opcion;
    }

    public void setOpcion(Integer opcion) {
        this.opcion = opcion;
    }

    public Integer getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Integer idPedido) {
        this.idPedido = idPedido;
    }

    public Collection<DtoMovimientos> getMovimientos() {
        return movimientos;
    }

    public void setMovimientos(Collection<DtoMovimientos> movimientos) {
        this.movimientos = movimientos;
    }

    public DtoMovimientos getMovimiento() {
        return movimiento;
    }

    public void setMovimiento(DtoMovimientos movimiento) {
        this.movimiento = movimiento;
    }

    public BigDecimal getTotalAdeudo() {
        return totalAdeudo;
    }

    public void setTotalAdeudo(BigDecimal totalAdeudo) {
        this.totalAdeudo = totalAdeudo;
    }

    public BigDecimal getTotalAbonado() {
        return totalAbonado;
    }

    public void setTotalAbonado(BigDecimal totalAbonado) {
        this.totalAbonado = totalAbonado;
    }

    public BigDecimal getTotalFaltante() {
        return totalFaltante;
    }

    public void setTotalFaltante(BigDecimal totalFaltante) {
        this.totalFaltante = totalFaltante;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }
}
