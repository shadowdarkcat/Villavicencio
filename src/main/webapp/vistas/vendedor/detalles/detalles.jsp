<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Vendedores</title>
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
                                <input type="button" id="btnAltaVendedor" name="btnAltaVendedor" value="Alta Vendedor" />                                
                            <li>
                            </c:if>
                            <c:if test="${id == 1}">
                            <li id="liBaja">            
                                <input type="button" id="btnBajaVendedor" name="btnBajaVendedor" value="Baja Vendedor" />
                            </li>
                        </c:if>
                        <c:if test="${id == 2}">
                            <li id="liCambios">            
                                <input type="button" id="btnCambioVendedor" name="btnCambioVendedor" value="Cambios Vendedor" />
                            </li>
                        </c:if>
                        <c:if test="${id == 3}">
                            <li id="liReporte">                            
                                <input type="button" id="btnGenerar" name="btnGenerar" value="Generar Reporte" />                              
                            <li>
                        </c:if>       
                    </ul>

                </div>
                <div id="divDatos" class="col-sm-9 col-sm-offset-3 col-md-9 col-md-offset-2 main">
                    <div class="row placeholders">
                        <div class="col-xs-6 col-sm-9 placeholder">
                            <c:if test="${empty vendedores}">
                                <label class="page-header">No hay vendedores que mostrar</label>
                            </c:if>
                            <c:if test="${not empty vendedores}">
                                <fieldset>
                                    <legend class="pager">LISTA DE VENDEDORES</legend>
                                    <form id="frmActivarVendedor" name="frmActivarVendedor" method="post" action="${pageContext.request.contextPath}/controller/vendedorController?method=4" >
                                        <input type="hidden" id="txtIdAction" name="txtIdAction" value="${id}" />
                                        <table id="tblVendedores" align="center" class="table table-bordered table-hover" style="width: 900px;">
                                            <thead>
                                                <tr>
                                                    <th style="text-align: center"># Vendedor</th>
                                                    <th style="text-align: center">Nombre Completo</th>
                                                    <th style="text-align: center">Detalles</th>
                                                    <th style="text-align: center">Visible</th>
                                                    <th style="text-align: center">Externo</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach items="${vendedores}" var="vendedores" varStatus="status">
                                                    <tr>
                                                        <td style="text-align: center">
                                                            <input type="hidden" name="txtIdVendedor" id="txtIdVendedor" value="${vendedores.idVendedor}" />
                                                            ${vendedores.idVendedor}
                                                        </td>
                                                        <td style="text-align: center">
                                                            ${vendedores.nombre}  ${vendedores.apellidoPaterno}  ${vendedores.apellidoMaterno}
                                                        </td>
                                                        <td style="text-align: center">
                                                            <a href="${pageContext.request.contextPath}/controller/vendedorController?method=9&txtIdVendedor=${vendedores.idVendedor}&reportName=vendedores"
                                                               target="_blank">
                                                                <img src="${pageContext.request.contextPath}/image/report.png" width="32" height="32"
                                                                     title="Ver Detalle" name="detalleVendedor" style="image-orientation: left;"/>
                                                            </a>
                                                        </td>
                                                        <td style="text-align: center">     
                                                            <c:if test="${vendedores.visible == true}">
                                                                <input type="checkbox" id="chkVisible" name="chkVisible" value="${vendedores.visible},${vendedores.idVendedor}" checked="${vendedores.visible}" disabled="disabled">Sí
                                                            </c:if>
                                                            <c:choose>
                                                                <c:when test="${id == 1}">
                                                                    <c:if test="${vendedores.visible == false}">
                                                                        <input type="checkbox" id="chkVisible" name="chkVisible" value="${vendedores.visible},${vendedores.idVendedor}" onclick="baja();"> No
                                                                    </c:if>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <c:if test="${vendedores.visible == false}">
                                                                        <input type="checkbox" id="chkVisible" name="chkVisible" value="${vendedores.visible},${vendedores.idVendedor}" disabled="disabled"> No
                                                                    </c:if>
                                                                </c:otherwise>
                                                            </c:choose>                                    
                                                        </td>
                                                        <td>
                                                            <c:if test="${vendedores.externo == true}">
                                                                Sí
                                                            </c:if>
                                                            <c:if test="${vendedores.externo == false}">
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

<div id="divVendedorNoSelected" title="Error">
    <table>
        <tr>
            <td style="text-align: center;">
                <p><span class="text-muted" >Por favor, seleccione un vendedor.</span></p>
            </td>
        </tr>
    </table>
</div>
<div id="divActivarVendedor" title="Aviso">
    <table>
        <tr>
            <td style="text-align: center;">
                <p><span class="text-muted" >¿ Desea activar el vendedor ?</span></p>
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
    <div id="divExisteVendedor" title="Error">
        <table>
            <tr>
                <td style="text-align: center;">
                    <input type="hidden" id="existVendedor" name="existVendedor" value="${exist}" />
                    <p><span class="text-muted" >El vendedor ya existe</span></p>
                </td>
            </tr>
        </table>
    </div>
</c:if>