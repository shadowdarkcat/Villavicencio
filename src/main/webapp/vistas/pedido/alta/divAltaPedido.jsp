<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
        <meta name="viewport" content="width=device-width, initial-scale=1.0">        
        <title>Pedidos</title>
    </head>
    <body>
        <div id="divAltaPedido" title="Registro Pedido">           
            <c:if test="${user.isAdmin == true}">
                <div id="divTipoCliente">
                    <fieldset>
                        <legend>
                            <span class="text-muted" >TIPO DE CLIENTE</span>
                        </legend>
                        <input type="radio" id="rdbTipoCliente" name="rdbTipoCliente" value="1" ><span class="text-muted" >Cliente</span>
                        <input type="radio" id="rdbTipoCliente" name="rdbTipoCliente" value="2" ><span class="text-muted" >Vendedor</span>
                    </fieldset>
                </div>
            </c:if>           
            <br/>
            <c:choose>
                <c:when test="${(user.isAdmin != true) && (user.isExterno != true)}">
                    <div id="divCliente">
                        <fieldset>
                            <legend>
                                <span class="text-muted" >LISTA DE CLIENTES</span>
                            </legend>
                            <table>
                                <tr>
                                    <td><span class="text-muted" >Seleccione cliente :</span></td>
                                    <td><select id="cboCliente" name="cboCliente">
                                            <option>Seleccione ...</option>
                                            <c:forEach items="${user.clientes}" var="clientes" varStatus="status" >
                                                <c:choose>
                                                    <c:when test="${empty clientes.empresa}">
                                                        <option value="${clientes.idCliente}"> ${clientes.idCliente} - ${clientes.nombre}  ${clientes.apellidoPaterno}  ${clientes.apellidoMaterno}</option>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <c:if test="${user.isAdmin == true}">
                                                            <option value="${clientes.idCliente}"> ${clientes.idCliente} - ${clientes.empresa}  ${clientes.razonSocial}</option>
                                                        </c:if>
                                                        <c:if test="${user.isAdmin == false}">
                                                            <option value="${clientes.idCliente}">${clientes.empresa}  ${clientes.razonSocial}</option>
                                                        </c:if>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                        </select>
                                    </td>
                                </tr>
                            </table>
                        </fieldset>
                    </div>
                </c:when>
                <c:otherwise>
                    <div id="divCliente" style="display: none;">
                        <fieldset>
                            <legend>
                                <span class="text-muted" >LISTA DE CLIENTES</span>
                            </legend>
                            <table>
                                <tr>
                                    <td><span class="text-muted" >Seleccione cliente :</span></td>
                                    <td><select id="cboCliente" name="cboCliente">
                                            <option>Seleccione ...</option>
                                            <c:forEach items="${user.clientes}" var="clientes" varStatus="status" >
                                                <c:choose>
                                                    <c:when test="${empty clientes.empresa}">
                                                        <option value="${clientes.idCliente}"> ${clientes.idCliente} - ${clientes.nombre}  ${clientes.apellidoPaterno}  ${clientes.apellidoMaterno}</option>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <c:if test="${user.isAdmin == true}">
                                                            <option value="${clientes.idCliente}"> ${clientes.idCliente} - ${clientes.empresa}  ${clientes.razonSocial}</option>
                                                        </c:if>
                                                        <c:if test="${user.isAdmin == false}">
                                                            <option value="${clientes.idCliente}">${clientes.empresa}  ${clientes.razonSocial}</option>
                                                        </c:if>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                        </select>
                                    </td>
                                </tr>
                            </table>
                        </fieldset>
                    </div>
                </c:otherwise>
            </c:choose>

            <br/>
            <c:choose>
                <c:when test="${(user.isExterno == true) && (user.isAdmin != true)}">
                    <div id="divVendedor">
                        <fieldset>
                            <legend>
                                <span class="text-muted" >LISTA DE VENDEDORES</span>
                            </legend>
                            <table>
                                <tr>
                                    <td><span class="text-muted" >Seleccione vendedor :</span></td>
                                    <td>
                                        <c:if test="${user.isExterno == true}">
                                            <select id="cboVendedor" name="cboVendedor" > 
                                                <option>Seleccione ...</option>
                                                <c:forEach items="${user.vendedores}" var="vendedores" varStatus="status" >
                                                    <option value="${vendedores.idVendedor}"> ${vendedores.nombre}  ${vendedores.apellidoPaterno}  ${vendedores.apellidoMaterno}</option>
                                                </c:forEach>
                                            </select>
                                        </c:if>
                                        <c:if test="${user.isExterno != true}">
                                            <select id="cboVendedor" name="cboVendedor">  
                                                <option>Seleccione ...</option>
                                                <c:forEach items="${user.vendedores}" var="vendedores" varStatus="status" >
                                                    <c:if test="${user.isAdmin == true}">
                                                        <option value="${vendedores.idVendedor}"> ${vendedores.idVendedor} - ${vendedores.nombre}  ${vendedores.apellidoPaterno}  ${vendedores.apellidoMaterno}</option>
                                                    </c:if>
                                                    <c:if test="${user.isAdmin == false}">
                                                        <option value="${vendedores.idVendedor}"> ${vendedores.nombre}  ${vendedores.apellidoPaterno}  ${vendedores.apellidoMaterno}</option>
                                                    </c:if>
                                                </c:forEach>
                                            </select>
                                        </c:if>
                                    </td>
                                </tr>
                            </table>
                        </fieldset>
                    </div>
                </c:when>
                <c:otherwise>
                    <div id="divVendedor" style="display: none;">
                        <fieldset>
                            <legend>
                                <span class="text-muted" >LISTA DE VENDEDORES</span>
                            </legend>
                            <table>
                                <tr>
                                    <td><span class="text-muted" >Seleccione vendedor :</span></td>
                                    <td>
                                        <c:if test="${user.isExterno == true}">
                                            <select id="cboVendedor" name="cboVendedor" > 
                                                <option>Seleccione ...</option>
                                                <c:forEach items="${user.vendedores}" var="vendedores" varStatus="status" >
                                                    <option value="${vendedores.idVendedor}"> ${vendedores.nombre}  ${vendedores.apellidoPaterno}  ${vendedores.apellidoMaterno}</option>
                                                </c:forEach>
                                            </select>
                                        </c:if>
                                        <c:if test="${user.isExterno != true}">
                                            <select id="cboVendedor" name="cboVendedor">  
                                                <option>Seleccione ...</option>
                                                <c:forEach items="${user.vendedores}" var="vendedores" varStatus="status" >
                                                    <c:if test="${user.isAdmin == true}">
                                                        <option value="${vendedores.idVendedor}"> ${vendedores.idVendedor} - ${vendedores.nombre}  ${vendedores.apellidoPaterno}  ${vendedores.apellidoMaterno}</option>
                                                    </c:if>
                                                    <c:if test="${user.isAdmin == false}">
                                                        <option value="${vendedores.idVendedor}"> ${vendedores.nombre}  ${vendedores.apellidoPaterno}  ${vendedores.apellidoMaterno}</option>
                                                    </c:if>
                                                </c:forEach>
                                            </select>
                                        </c:if>
                                    </td>
                                </tr>
                            </table>
                        </fieldset>
                    </div>
                </c:otherwise>
            </c:choose>
            <br/>            
            <div id="divNoProductos" style="display: none;">
                <span class="text-muted">No existen productos para este cliente o vendedor</span>
            </div>
            <div id="divSliderProductos" style="position: relative; top: 0px; left: 0px; width: 600px; height: 220px;">
            </div>   
            <div id="carritoPedido" style="display: none;">
                <fieldset>
                    <legend>
                        <span class="text-muted" >LISTA DE PEDIDO</span>
                    </legend>
                    <table id="tblPedido" class="table table-bordered table-hover">
                        <thead>
                            <tr>
                                <th style="text-align: center;">Producto</th>
                                <th style="text-align: center;">Cantidad</th>      
                            </tr>
                        </thead>
                        <tbody>                            
                        </tbody>                    
                    </table>
                    <input type="button" id="btnFicha" name="btnFicha" value="Generar Nota"  />
                </fieldset>
            </div>

            <div id="divCapturaDatos" title="Captura Producto Pedido">
                <div id="Izquierda" style="width: 30%;">        
                </div>
                <div id="Derecha" style="width: 60%;">
                    <table>
                        <tr>
                            <td id="nombreProducto"></td>
                        </tr><br/><br/>
                        <tr>
                            <td>Cantidad Piezas :</td>
                            <td><input type="text" id="txtPiezas" name="txtPiezas" class="solo-numero" />                
                                <input type="hidden" id="txtIdProducto" name="txtIdProducto" />
                                <input type="hidden" id="txtProducto" name="txtProducto" />                   
                                <input type="hidden" id="txtCosto" name="txtCosto" />
                                <input type="hidden" id="txtPeso" name="txtPeso" />
                                <input type="hidden" id="txtMinimo" name="txtMinimo" />
                                <input type="hidden" id="txtMaximo" name="txtMaximo" />
                                <input type="hidden" id="txtComision" name="txtComision" />
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
            <div id="divCampoVacio" title="Error">
                <span class="text-muted" >El campo piezas no puede estar vacio</span>
            </div>
        </div>
    </body>
</html>