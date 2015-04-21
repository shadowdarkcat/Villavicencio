<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

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
                                <input type="button" id="btnAltaAbono" name="btnAltaAbono" value="Alta Abono" />                                
                            <li>
                            </c:if>
                    </ul>
                </div>
                <div id="divDatos" class="col-sm-9 col-sm-offset-3 col-md-9 col-md-offset-2 main">
                    <div class="row placeholders">
                        <div class="col-xs-6 col-sm-9 placeholder">
                            <div id="divTipoCliente">
                                <fieldset>
                                    <legend>
                                        <span class="text-muted" >TIPO DE CLIENTE</span>
                                    </legend>
                                    <input type="radio" id="rdbTipoClienteMovimiento" name="rdbTipoClienteMovimiento" value="1" ><span class="text-muted" >Cliente</span>
                                    <input type="radio" id="rdbTipoClienteMovimiento" name="rdbTipoClienteMovimiento" value="2" ><span class="text-muted" >Vendedor</span>
                                </fieldset>
                            </div>
                            <br/>
                            <div id="divClienteMovimiento" style="display: none;">
                                <c:if test="${empty clientes}">
                                    <label class="page-header">No hay clientes que mostrar</label>
                                </c:if>
                                <c:if test="${not empty clientes}">
                                    <table id="tblClientesMovimiento" align="center" class="table table-bordered table-hover" style="width: 900px;">
                                        <thead>
                                            <tr>
                                                <th style="text-align: center"># Cliente</th>
                                                <th style="text-align: center">Empresa</th>
                                                <th style="text-align: center">Detalles</th>
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
                                                        <a href="${pageContext.request.contextPath}/controller/abonosController?method=4&txtIdCliente=${clientes.idCliente}&reportName=movimientos"
                                                           target="_blank">
                                                            <img src="${pageContext.request.contextPath}/image/report.png" width="32" height="32"
                                                                 title="Ver Detalle Movimientos" name="detalleCliente" style="image-orientation: left;"/>
                                                        </a>
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
                                </c:if>
                            </div>
                            <div id="divVendedorMovimiento" style="display: none;">
                                <c:if test="${empty vendedores}">
                                    <label class="page-header">No hay clientes que mostrar</label>
                                </c:if>
                                <c:if test="${not empty clientes}">
                                    <table id="tblVendedoresMovimiento" align="center" class="table table-bordered table-hover" style="width: 900px;">
                                        <thead>
                                            <tr>
                                                <th style="text-align: center"># Vendedor</th>
                                                <th style="text-align: center">Nombre Completo</th>
                                                <th style="text-align: center">Detalles</th>
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
                                                        <a href="${pageContext.request.contextPath}/controller/abonosController?method=4&txtIdVendedor=${vendedores.idVendedor}&reportName=movimientos"
                                                           target="_blank">
                                                            <img src="${pageContext.request.contextPath}/image/report.png" width="32" height="32"
                                                                 title="Ver Detalle Movimientos" name="detalleCliente" style="image-orientation: left;"/>
                                                        </a>         
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
                                </c:if>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
    <div id="divNotaNoSelected" title="Error">
        <table>
            <tr>
                <td style="text-align: center;">
                    <p><span class="text-muted" >Por favor, seleccione un registro.</span></p>
                </td>
            </tr>
        </table>
    </div>
   
    <div id="divRadioNoSelected" title="Error">
        <table>
            <tr>
                <td style="text-align: center;">
                    <p><span class="text-muted" >Por favor, seleccione el tipo de cliente.</span></p>
                </td>
            </tr>
        </table>
    </div>
</html>

