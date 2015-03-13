<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">       
        <title>Vendedores</title>
    </head>
    <body>
        <div class="formulario" style="width: 100%;"> 
            <div id="divModificarVendedor" title="Cambios Vendedor">
                <form id="frmModificarVendedor" name="frmModificarVendedor" method="post" action="${pageContext.request.contextPath}/controller/vendedorController?method=3">
                    <input type="hidden" id="txtIdAction" name="txtIdAction" value="${id}" />
                    <div id="Izquierda" style="width: 35%;">
                        <fieldset>
                            <legend><span class="text-muted" >Datos Vendedor</span></legend>
                            <table>
                                <tr>
                                    <td><span class="text-muted" >* Nombre : </span></td>
                                    <td><input type="hidden" id="txtIdVendedor" name="txtIdVendedor" readOnly="readOnly" />
                                        <input type="text" id="txtNombre" name="txtNombre" class="required" onkeypress="mayuscula(this);"</td>
                                </tr>
                                <tr>
                                    <td><span class="text-muted" >* Apellido Paterno :</span></td>
                                    <td><input type="text" name="txtApellidoPaterno" id="txtApellidoPaterno" class="required" onkeypress="mayuscula(this);"/></td>
                                </tr>
                                <tr>
                                    <td><span class="text-muted" >* Apellido Materno :</span></td>
                                    <td><input type="text" name="txtApellidoMaterno" id="txtApellidoMaterno" class="required" onkeypress="mayuscula(this);"/></td>
                                </tr> 

                                <tr>
                                    <td><span class="text-muted" > RFC :</span></td>
                                    <td><input type="text" id="txtRfc" name="txtRfc" onkeypress="mayuscula(this);"/></td>
                                </tr>
                            </table>
                        </fieldset>
                        <br/>

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
                                    <td><span class="text-muted" >Ciudad :</span></td>
                                    <td><input type="text" id="txtCiudad" name="txtCiudad" readonly="readOnly"/></td>
                                </tr>
                            </table>
                        </fieldset>
                    </div>
                    <div id="Derecha" style="width: 35%;">
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
                            <legend><span class="text-muted">Extras Vendedor</span></legend>
                            <table>
                                <tr>
                                    <td><input type="radio" id="rdbVendedor" name="rdbVendedor" value="1" /><span class="text-muted">Comision</span></td>
                                    <td><input type="checkbox" id="chkExterno" name="chkExterno" value="1" /><span class="text-muted">Externo</span></td>
                                   <!-- <td><input type="checkbox" id="chkCredito" name="chkCredito" value="1" /><span class="text-muted">Credito</span></td>!-->
                                </tr>
                            </table>
                            <br/>
                            <div id="divComisiones" style="display: none;">
                                <fieldset>
                                    <legend>
                                        <span class="text-muted">Porcentaje</span>
                                    </legend>
                                    <table>
                                        <tr>
                                            <td><span class="text-muted">Porcentaje :</span></td>
                                            <td><input type="text" id="txtPorcentaje" name="txtPorcentaje" value="0.00"/></td>
                                        </tr>
                                    </table>
                                </fieldset>
                            </div>
                            <br/>
                            <div id="divCreditos" style="display: none;">
                                <fieldset>
                                    <legend>
                                        <span class="text-muted">Tipo Cr&eacute;dito</span>
                                    </legend>

                                    <input type="radio" id="rdbCredito" name="rdbCredito" value="1" onclick="creditoModificar();" /><span class="text-muted">Monetario</span>
                                    <input type="radio" id="rdbCredito" name="rdbCredito" value="2" onclick="creditoModificar();"/><span class="text-muted">Contra Nota</span>
                                    <input type="radio" id="rdbCredito" name="rdbCredito" value="3" onclick="creditoModificar();"/><span class="text-muted">Plazo</span>
                                </fieldset>
                            </div>
                            <div id="divMonetario" style="display: none;">

                                <table>
                                    <tr>
                                        <td>
                                            <span class="text-muted">Cantidad $:</span></td>
                                        <td><input type="text" id="txtMonetario" name="txtMonetario" /></td>
                                    </tr>
                                </table>
                            </div>
                            <br/>
                            <fieldset>
                                <legend><span class="text-muted">Productos</span></legend>
                                <table>
                                    <tr>
                                        <td><input type="radio" id="rdbProducto" name="rdbProducto" value="1" onclick="buscarProductoVendedor();">Productos Establecidos</td>
                                        <td><input type="radio" id="rdbProducto" name="rdbProducto" value="2" onclick="buscarProductoVendedorPersonalizado()">Productos Personalizados</td>
                                    </tr>
                                </table>
                            </fieldset>
                        </fieldset>
                    </div>
                    <div id="divDown">
                        <div id="divProductoVendedor"></div>
                    </div>
                </form>
            </div>        
        </div>
    </body>
</html>
<div id="divMessageModificarVendedor" title="Advertencia">
    <table>
        <tr>
            <td><span class="text-muted">El registro ser&aacute; modificado</span></td>
        </tr>
        <tr>
            <td><span class="text-muted">¿Desea continuar?</span></td>
        </tr>
    </table>
</div>