<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Clientes</title>
    </head>
    <body>
        <div id="divContainer" class="container-fluid">
            <div class="row">
                <div id="divLateral" class="col-sm-3 col-md-2 sidebar">
                    <ul id="ulListProductos" class="nav nav-sidebar">
                        <li class="active"><a href="#">Acci&oacute;n <span class="sr-only">(current)</span></a></li>
                        <br/>
                        <c:if test="${id == 0}">
                            <li id="liAlta">                            
                                <input type="button" id="btnAltaCliente" name="btnAltaCliente" value="Alta Cliente" />                                
                            <li>
                            </c:if>
                            <c:if test="${id == 1}">
                            <li id="liBaja">            
                                <input type="button" id="btnBajaCliente" name="btnBajaCliente" value="Baja Cliente" />
                            </li>
                        </c:if>
                        <c:if test="${id == 2}">
                            <li id="liCambios">            
                                <input type="button" id="btnCambioCliente" name="btnCambioCliente" value="Cambios Cliente" />
                            </li>
                        </c:if>
                        <c:if test="${id == 3}">
                            <li id="liReporte">                            
                                <input type="button" id="btnGenerarCliente" name="btnGenerarCliente" value="Generar Reporte" />                              
                            <li>
                        </c:if>       
                    </ul>
                </div>
                <div id="divDatos" class="col-sm-9 col-sm-offset-3 col-md-9 col-md-offset-2 main">
                    <div class="row placeholders">
                        <div class="col-xs-6 col-sm-9 placeholder">
                            <c:if test="${empty clientes}">
                                <label class="page-header">No hay clientes que mostrar</label>
                            </c:if>
                            <c:if test="${not empty clientes}">
                                <fieldset>
                                    <legend class="pager">LISTA DE CLIENTES</legend>
                                    <form id="frmActivarCliente" name="frmActivarCliente" method="post" action="${pageContext.request.contextPath}/controller/clienteController?method=4" >
                                        <input type="hidden" id="txtIdAction" name="txtIdAction" value="${id}" />
                                        <table id="tblClientes" align="center" class="table table-bordered table-hover" style="width: 900px;">
                                            <thead>
                                                <tr>
                                                    <th style="text-align: center"># Cliente</th>
                                                    <th style="text-align: center">Empresa</th>
                                                    <th style="text-align: center">Detalles</th>
                                                    <th style="text-align: center">Visible</th>
                                                    <th style="text-align: center">Cr&eacute;dito</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach items="${clientes}" var="clientes" varStatus="status">
                                                    <tr>
                                                        <td style="text-align: center">
                                                            <input type="hidden" name="txtIdCliente" id="txtIdCliente" value="${clientes.idCliente}" />
                                                            ${clientes.idCliente}
                                                        </td>

                                                        <td style="text-align: center">
                                                            <c:if test="${not empty clientes.empresa}">
                                                                ${clientes.empresa} ${clientes.razonSocial}                                                            
                                                            </c:if>
                                                        </td>
                                                        <td style="text-align: center">
                                                            <a href="${pageContext.request.contextPath}/controller/clienteController?method=9&txtIdCliente=${clientes.idCliente}&reportName=clientes"
                                                               target="_blank">
                                                                <img src="${pageContext.request.contextPath}/image/report.png" width="32" height="32"
                                                                     title="Ver Detalle" name="detalleCliente" style="image-orientation: left;"/>
                                                            </a>
                                                        </td>
                                                        <td style="text-align: center">     
                                                            <c:if test="${clientes.visible == true}">
                                                                <input type="checkbox" id="chkVisible" name="chkVisible" value="${clientes.visible},${clientes.idCliente}" checked="${clientes.visible}" disabled="disabled">Sí
                                                            </c:if>
                                                            <c:choose>
                                                                <c:when test="${id == 1}">
                                                                    <c:if test="${clientes.visible == false}">
                                                                        <input type="checkbox" id="chkVisible" name="chkVisible" value="${clientes.visible},${clientes.idCliente}" onclick="bajaCliente();"> No
                                                                    </c:if>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <c:if test="${clientes.visible == false}">
                                                                        <input type="checkbox" id="chkVisible" name="chkVisible" value="${clientes.visible},${clientes.idCliente}" disabled="disabled"> No
                                                                    </c:if>
                                                                </c:otherwise>
                                                            </c:choose>                                    
                                                        </td>
                                                        <td>
                                                            <c:if test="${not empty clientes.credito.tipoCredito}">
                                                                Sí
                                                            </c:if>
                                                            <c:if test="${empty clientes.credito.tipoCredito}">
                                                                No
                                                            </c:if>
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                            </tbody>
                                        </table>
                                    </form>
                                </fieldset>
                            </c:if>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>

<div id="divClienteNoSelected" title="Error">
    <table>
        <tr>
            <td style="text-align: center;">
                <p><span class="text-muted" >Por favor, seleccione un cliente.</span></p>
            </td>
        </tr>
    </table>
</div>
<div id="divActivarCliente" title="Aviso">
    <table>
        <tr>
            <td style="text-align: center;">
                <p><span class="text-muted" >¿ Desea activar el cliente ?</span></p>
            </td>
        </tr>
    </table>
</div>
<div id="divNoVisible" title="Error">
    <table>
        <tr>
            <td style="text-align: center;">
                <p><span class="text-muted" >No se puede modificar el registro, su estado es no visible</span></p>
            </td>
        </tr>
    </table>
</div> 
<c:if test="${exist == true}">
    <div id="divExisteCliente" title="Error">
        <table>
            <tr>
                <td style="text-align: center;">
                    <input type="hidden" id="existCliente" name="existCliente" value="${exist}" />
                    <p><span class="text-muted" >El cliente ya existe</span></p>
                </td>
            </tr>
        </table>
    </div>
</c:if>