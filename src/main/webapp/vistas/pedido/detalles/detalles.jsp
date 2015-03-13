<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@taglib uri="/WEB-INF/images.tld" prefix="images"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Pedidos</title>
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
                                <input type="button" id="btnAltaPedido" name="btnAltaPedido" value="Alta Pedido" />                                
                            <li>
                            </c:if>
                            <c:if test="${id == 1}">
                            <li id="liBaja">            
                                <input type="button" id="btnBajaPedido" name="btnBajaPedido" value="Cancelar Pedido" />
                            </li>
                        </c:if>
                        <c:if test="${id == 2}">
                            <li id="liCambios">            
                                <input type="button" id="btnCambioPedido" name="btnCambioPedido" value="Cambios Pedido" />
                            </li>
                        </c:if>
                        <c:if test="${id == 3}">
                            <li id="liReporte">                            
                                <input type="button" id="btnGenerarPedido" name="btnGenerarPedido" value="Generar Reporte" />                              
                            <li>
                            </c:if>      
                            <br/><br/>
                        <li class="active"><a href="#">Nota de Abreviaturas</a></li><br/>
                        <li>P = PENDIENTE</li><br/>
                        <li>S = SURTIENDO</li><br/>
                        <li>C = CANCELADO</li><br/>
                        <li>E = ENTREGADO</li><br/>
                    </ul>
                </div>
                <div id="divDatos" class="col-sm-9 col-sm-offset-3 col-md-9 col-md-offset-2 main">
                    <div class="row placeholders">
                        <div class="col-xs-6 col-sm-9 placeholder">
                            <input type="hidden" id="txtIsAdmin" name="txtIsAdmin" value="${user.isAdmin}" />
                            <c:if test="${empty pedidos}">
                                <label class="page-header">No hay pedidos que mostrar</label>
                            </c:if>
                            <c:if test="${not empty pedidos}">
                                <input type="hidden" id="txtIdAction" name="txtIdAction" value="${id}" />
                                <fieldset>
                                    <legend class="pager">LISTA DE PEDIDOS</legend>
                                    <table id="tblPedidos" align="center" class="table table-bordered table-hover" style="width: 900px;">
                                        <thead>
                                        <th style="text-align: center"># Pedido</th>
                                        <th style="text-align: center">Folio</th>
                                        <th style="text-align: center">Cliente</th>
                                        <th style="text-align: center">Estado</th>
                                        <th style="text-align: center">Fecha</th>
                                        <th style="text-align: center">Detalle</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach items="${pedidos}" var="pedidos" varStatus="status">
                                                <tr> 
                                                    <td style="text-align: center">
                                                        <input type="hidden" id="txtIdPedido" name="txtIdPedido" value="${pedidos.idPedido}" readonly="readOnly"/>                                                        
                                                        ${pedidos.idPedido}
                                                    </td>
                                                    <td style="text-align: center">
                                                        ${pedidos.folio}
                                                    </td>
                                                    <td style="text-align: center">
                                                        <c:choose>
                                                            <c:when test="${user.isAdmin == true}">
                                                                <c:if test="${not empty pedidos.cliente.empresa}">
                                                                    <input type="hidden" id="txtIdCliente" name="txtIdCliente" value="${pedidos.cliente.idCliente}" readonly="readOnly" />
                                                                    ${pedidos.cliente.empresa} ${pedidos.cliente.razonSocial}                                                            
                                                                </c:if>
                                                                <c:if test="${not empty pedidos.vendedor}">                                                                
                                                                    <input type="hidden" id="txtIdVendedorCliente" name="txtIdVendedorCliente" value="${pedidos.vendedor.idVendedor}" readonly="readOnly" />
                                                                    ${pedidos.vendedor.nombre} ${pedidos.vendedor.apellidoPaterno} ${pedidos.vendedor.apellidoMaterno}

                                                                </c:if>
                                                            </c:when>          
                                                            <c:when test="${(user.isExterno == true) && (user.isAdmin != true)}">
                                                                <input type="hidden" id="txtIdVendedorCliente" name="txtIdVendedorCliente" value="${pedidos.vendedor.idVendedor}" readonly="readOnly" />
                                                                ${pedidos.vendedor.nombre} ${pedidos.vendedor.apellidoPaterno} ${pedidos.vendedor.apellidoMaterno}
                                                            </c:when>
                                                            <c:otherwise>                                                            
                                                                <input type="hidden" id="txtIdCliente" name="txtIdCliente" value="${pedidos.cliente.idCliente}" readonly="readOnly" />
                                                                ${pedidos.cliente.empresa} ${pedidos.cliente.razonSocial}
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </td>
                                                    <td style="text-align: center">
                                                        <input type="hidden" id="txtStatus" name="txtStatus" value="${pedidos.statusAlmacen}" readonly="readOnly" />                                                    
                                                        ${pedidos.statusAlmacen}
                                                    </td>
                                                    <td style="text-align: center">
                                                        <fmt:formatDate pattern="dd-MM-yyyy" value="${pedidos.fecha}"/>
                                                    </td>
                                                    <td>
                                                        <a id="detallePedido" href="${pageContext.request.contextPath}/controller/pedidosController?method=10&txtIdPedido=${pedidos.idPedido}&reportName=pedidos"
                                                               target="_blank">
                                                            <img  src="${pageContext.request.contextPath}/image/report.png" width="32" height="32"
                                                                  title="Ver Detalle" name="detalleCliente" style="image-orientation: left;" />
                                                        </a>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </fieldset>
                            </c:if>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
    <div id="divPedidoNoSelected">
        <table>
            <tr>
                <td style="text-align: center;">
                    <p><span class="text-muted" >Por favor, seleccione un pedido.</span></p>
                </td>
            </tr>
        </table>
    </div>
    <div id="divPedidoNoVisible" title="Error">
        <table>
            <tr>
                <td style="text-align: center;">
                    <p><span class="text-muted" >No se puede modificar el pedido, su estado es cancelado</span></p>
                </td>
            </tr>
        </table>
    </div>
</html>