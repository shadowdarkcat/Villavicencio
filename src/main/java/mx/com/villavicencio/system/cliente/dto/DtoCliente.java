package mx.com.villavicencio.system.cliente.dto;

import java.math.BigDecimal;
import java.util.Collection;
import mx.com.villavicencio.system.credito.credito.dto.DtoCredito;
import mx.com.villavicencio.system.movimientos.movimientos.dto.DtoMovimientos;
import mx.com.villavicencio.system.productos.productos.dto.DtoProducto;
import mx.com.villavicencio.system.vendedor.dto.DtoVendedor;

/**
 *
 * @author Gabriel J
 */
public class DtoCliente {

    private Integer idCliente;
    private String empresa;
    private String razonSocial;
    private String rfc;
    private String correoEmpresa;
    private String sitioWeb;
    private String calle;
    private String codigoPostal;
    private String colonia;
    private String delegacion;
    private String municipio;
    private String estado;
    private String ciudad;
    private String noExterior;
    private String noInterior;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String telefono1;
    private String telefono2;
    private String telefono3;
    private String correo1;
    private String correo2;
    private String correo3;
    private String tipoCredito;
    private BigDecimal cantidadMonetaria;
    private Boolean visible;
    private Integer opcion;
    private DtoCredito credito;
    private Collection<DtoProducto> establecidos;
    private Collection<DtoProducto> personalizados;
    private DtoVendedor vendedor;
    private String contacto1;
    private String contacto2;
    private String contacto3;
    private Collection<DtoMovimientos> movimientos;
    private DtoMovimientos movimiento;
    private BigDecimal totalAdeudo;
    private BigDecimal totalAbonado;
    private BigDecimal totalFaltante;
    private String table;
    private Collection<DtoCliente>clientes;
    
    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getCorreoEmpresa() {
        return correoEmpresa;
    }

    public void setCorreoEmpresa(String correoEmpresa) {
        this.correoEmpresa = correoEmpresa;
    }

    public String getSitioWeb() {
        return sitioWeb;
    }

    public void setSitioWeb(String sitioWeb) {
        this.sitioWeb = sitioWeb;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
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

    public String getTipoCredito() {
        return tipoCredito;
    }

    public void setTipoCredito(String tipoCredito) {
        this.tipoCredito = tipoCredito;
    }

    public BigDecimal getCantidadMonetaria() {
        return cantidadMonetaria;
    }

    public void setCantidadMonetaria(BigDecimal cantidadMonetaria) {
        this.cantidadMonetaria = cantidadMonetaria;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public Integer getOpcion() {
        return opcion;
    }

    public void setOpcion(Integer opcion) {
        this.opcion = opcion;
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

    public DtoVendedor getVendedor() {
        return vendedor;
    }

    public void setVendedor(DtoVendedor vendedor) {
        this.vendedor = vendedor;
    }

    public String getContacto1() {
        return contacto1;
    }

    public void setContacto1(String contacto1) {
        this.contacto1 = contacto1;
    }

    public String getContacto2() {
        return contacto2;
    }

    public void setContacto2(String contacto2) {
        this.contacto2 = contacto2;
    }

    public String getContacto3() {
        return contacto3;
    }

    public void setContacto3(String contacto3) {
        this.contacto3 = contacto3;
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

    public Collection<DtoCliente> getClientes() {
        return clientes;
    }

    public void setClientes(Collection<DtoCliente> clientes) {
        this.clientes = clientes;
    }
}
