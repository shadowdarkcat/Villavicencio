<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">       
        <title>Clientes</title>
    </head>
    <body>
        <div class="formulario" style="width: 100%;"> 
            <div id="divAltaCliente" title="Registro Cliente">
                <form id="frmAltaCliente" name="frmAltaCliente" method="post" action="${pageContext.request.contextPath}/controller/clienteController?method=1">
                    <input type="hidden" id="txtIdAction" name="txtIdAction" value="${id}" />
                    <div id="Izquierda" style="width: 35%;">
                        <fieldset>
                            <legend class="text-muted" >Empresa</legend>
                            <table>
                                <tr>
                                    <td><span class="text-muted" >* Empresa :</span></td>
                                    <td><input type="text" id="txtEmpresa" name="txtEmpresa" class="required" onkeypress="mayuscula(this);"/></td>
                                </tr>
                                <tr>
                                    <td><span class="text-muted" >* Razon Social :</span></td>
                                    <td><input type="text" id="txtRazonSocial" name="txtRazonSocial" class="required" onkeypress="mayuscula(this);"/></td>
                                </tr>
                                <tr>
                                    <td><span class="text-muted" >RFC :</span></td>
                                    <td><input type="text" id="txtRfc" name="txtRfc" onkeypress="mayuscula(this);"/></td>
                                </tr>
                                <tr>
                                    <td><span class="text-muted" >Correo Empresa :</span></td>
                                    <td><input type="text" id="txtCorreoEmpresa" name="txtCorreoEmpresa" /></td>
                                </tr>
                                <tr>
                                    <td><span class="text-muted" >Sitio Web :</span></td>
                                    <td><input type="text" id="txtSitioWeb" name="txtSitioWeb" /></td>
                                </tr>
                            </table>
                        </fieldset>                       
                        <br/>
                        <fieldset>
                            <legend>
                                <span class="text-muted" > Direcci&oacute;n</span>
                            </legend>
                            <table>
                                <tr>
                                    <td><span class="text-muted" >* Calle :</span>
                                    <td><input type="text" id="txtCalle" name="txtCalle" class="required" onkeypress="mayuscula(this);"/></td>
                                </tr>
                                <tr>
                                    <td><span class="text-muted" >* No Exterior :</span></td>
                                    <td><input type="text" id="txtNoExterior" name="txtNoExterior" class="required" /></td>
                                </tr>
                                <tr>
                                    <td><span class="text-muted" >No Interior :</span></td>
                                    <td><input type="text" id="txtNoInterior" name="txtNoInterior"/></td>
                                </tr>
                                <tr>
                                    <td><span class="text-muted" >* Colonia :</span></td>
                                    <td><input type="text" id="txtColonia" name="txtColonia" class="required" onkeypress="mayuscula(this);"/></td>
                                </tr>
                                <tr>
                                    <td><span class="text-muted" >* Cp :</span></td>
                                    <td><input type="text" id="txtCp" name="txtCp" class="required" readonly="readOnly"/></td>
                                </tr>
                                <tr id="delegacionMunicipio"></tr>
                                <tr>
                                    <td><span class="text-muted" >* Estado :</span></td>
                                    <td><input type="text" id="txtEstado" name="txtEstado" class="required" readonly="readOnly"/></td>
                                </tr>
                                <tr>
                                    <td><span class="text-muted" > Ciudad :</span></td>
                                    <td><input type="text" id="txtCiudad" name="txtCiudad" readonly="readOnly"/></td>
                                </tr>
                            </table>
                        </fieldset>
                    </div>
                    <div id="Derecha" style="width: 35%;">                       
                        <fieldset>
                            <legend><span class="text-muted" >Datos Cliente</span></legend>
                            <table>
                                <tr>
                                    <td><span class="text-muted" >* Nombre : </span></td>
                                    <td><input type="text" id="txtNombre" name="txtNombre" class="required" onkeypress="mayuscula(this);"</td>
                                </tr>
                                <tr>
                                    <td><span class="text-muted" >* Apellido Paterno :</span></td>
                                    <td><input type="text" name="txtApellidoPaterno" id="txtApellidoPaterno" class="required" onkeypress="mayuscula(this);"/></td>
                                </tr>
                                <tr>
                                    <td><span class="text-muted" >* Apellido Materno :</span></td>
                                    <td><input type="text" name="txtApellidoMaterno" id="txtApellidoMaterno" class="required" onkeypress="mayuscula(this);"/></td>
                                </tr> 
                            </table>
                        </fieldset>
                        <br/>
                        <fieldset>
                            <legend>
                                <span class="text-muted">Forma de Contacto</span>
                            </legend>
                            <table>
                                <tr>
                                    <td><span class="text-muted">* No Telefono 1 :</span></td>
                                    <td><input type="text" id="txtTel1" name="txtTel1" class="required digits"/></td>
                                </tr>
                                <tr>
                                    <td><span class="text-muted" >No Telefono 2 :</span></td>
                                    <td><input type="text" id="txtTel2" name="txtTel2" class="digits" /></td>
                                </tr>
                                <tr>
                                    <td><span class="text-muted" >No Telefono 3 :</span></td>
                                    <td><input type="text" id="txtTel3" name="txtTel3" class="digits" /></td>
                                </tr>
                                <tr>
                                    <td><span class="text-muted" >* Correo-Electr&oacute;nico 1 :</span></td>
                                    <td><input type="text" id="txtMail1" name="txtMail1" class="required email" onkeypress="minuscula(this);"/></td>
                                </tr>
                                <tr>
                                    <td><span class="text-muted" >Correo Electr&oacute;nico 2 :</span></td>
                                    <td><input type="text" id="txtMail2" name="txtMail2" class="email" onkeypress="minuscula(this);"/></td>
                                </tr>
                                <tr>
                                    <td><span class="text-muted" >Correo Electr&oacute;nico 3 :</span></td>
                                    <td><input type="text" id="txtMail3" name="txtMail3" class="email" onkeypress="minuscula(this);"/></td>
                                </tr>
                            </table>
                        </fieldset>
                    </div>
                    <div id="Derecha1" style="width: 30%;">
                         <fieldset>
                            <legend><span class="text-muted">Contactos</span></legend>
                            <table>
                                <tr>
                                    <td><span class="text-muted">* Contacto 1 :</span></td>
                                    <td><input type="text" id="txtContacto1" name="txtContacto1" class="required" onkeypress="mayuscula(this);"/></td>
                                </tr>
                                <tr>
                                    <td><span class="text-muted">Contacto 2 :</span></td>
                                    <td><input type="text" id="txtContacto2" name="txtContacto2"  onkeypress="mayuscula(this);"/></td>
                                </tr>
                                <tr>
                                    <td><span class="text-muted">Contacto 3 :</span></td>
                                    <td><input type="text" id="txtContacto3" name="txtContacto3"  onkeypress="mayuscula(this);"/></td>
                                </tr>
                                </tr>
                            </table>
                        </fieldset>        
                        <br/>
                        <fieldset>
                            <legend><span class="text-muted">Extras Cliente</span></legend>
                            <table>
                                <tr>
                                    <td><input type="checkbox" id="chkCredito" name="chkCredito" value="1" /><span class="text-muted">Credito</span></td>
                                </tr>
                                <tr>
                                    <td>
                                        <div id="divCreditos" style="display: none;">  
                                            <br/>
                                            <input type="radio" id="rdbCredito" name="rdbCredito" value="1" onclick="creditoCliente();" /><span class="text-muted">Monetario</span>
                                            <input type="radio" id="rdbCredito" name="rdbCredito" value="2" onclick="creditoCliente();"/><span class="text-muted">Contra Nota</span>
                                            <input type="radio" id="rdbCredito" name="rdbCredito" value="3" onclick="creditoCliente();"/><span class="text-muted">Plazo</span>
                                            <div id="divMonetarioCliente" style="display: none;">
                                                <table>
                                                    <tr>
                                                        <td>
                                                            <span class="text-muted">Cantidad $:</span></td>
                                                        <td><input type="text" id="txtMonetario" name="txtMonetario" /></td>
                                                    </tr>
                                                </table>
                                            </div>
                                        </div>
                                    </td>
                                </tr>
                            </table>
                            <br/>
                            <fieldset>
                                <legend class="text-muted">Vendedor</legend>
                                <table>
                                    <tr>
                                        <td><select name="cboVendedor" id="cboVendedor">
                                                <option value="">Seleccione ...</option>
                                                <c:forEach items="${vendedores}" var="vendedores" varStatus="status">
                                                    <c:if test="${vendedores.visible == true && vendedores.externo != true}">
                                                        <option value="${vendedores.idVendedor}">
                                                            ${vendedores.nombre} ${vendedores.apellidoPaterno} ${vendedores.apellidoMaterno}
                                                        </option>
                                                    </c:if>
                                                </c:forEach>
                                            </select>
                                        </td>
                                    </tr>
                                </table>
                            </fieldset>
                            <br/>
                            <fieldset>
                                <legend><span class="text-muted">Productos</span></legend>
                                <table>
                                    <tr>
                                        <td><input type="radio" id="rdbProducto" name="rdbProducto" value="1" onclick="buscarProductoCliente();">Productos Establecidos</td>
                                        <td><input type="radio" id="rdbProducto" name="rdbProducto" value="2" onclick="buscarProductoClientePersonalizado()">Productos Personalizados</td>
                                    </tr>
                                </table>
                            </fieldset>
                        </fieldset>
                    </div>
                    <div id="divDown">
                        <div id="divProductoCliente"></div>
                    </div>
                </form>
            </div>        
        </div>
    </body>
</html>