<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="Pragma" content="no-cache">
        <meta http-equiv="Expires" content="-1">
        <title>Reporte Productos</title>
    </head>
    <body>  
        <div id="divContainer" class="container-fluid">
            <div class="row">
                <div class="col-sm-3 col-md-2 sidebar">
                    <ul id="ulListProductos" class="nav nav-sidebar">
                        <li class="active"><a href="#">Acci&oacute;n <span class="sr-only">(current)</span></a></li>
                        <br/>
                        <c:if test="${id == 3}">
                            <li id="liAlta">                            
                                <input type="button" id="btnGenerar" name="btnGenerar" onclick="validarReporte();" value="Generar Reporte" />                              
                            <li>
                            </c:if>                     
                    </ul>

                </div>
                <div id="divDatos" class="col-sm-9 col-sm-offset-3 col-md-9 col-md-offset-2 main">
                    <div class="row placeholders">
                        <form id="frmReporteProductos" name="frmReporteProductos" method="post" action="${pageContext.request.contextPath}/controller/productoController?method=7" target="_blank">
                            <input type="hidden" id="txtIdAction" name="txtIdAction" value="${id}" />
                            <input type="hidden" id="reportName" name="reportName" value="productos" /> 
                            <center>
                                <table>
                                    <tr>
                                        <td><span class="text-muted" >TIPO REPORTE </span></td>
                                    </tr>
                                    <tr>
                                        <td><input type="radio" id="rdbFormatoReporte" name="rdbFormatoReporte" value="PDF">PDF
                                            <input type="radio" id="rdbFormatoReporte" name="rdbFormatoReporte" value="XLS">EXCEL</td>
                                    </tr>                
                                </table>
                            </center>                            
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <div id="errorReporte" title="Error !!!">
            <table>
                <tr>
                    <td style="text-align: center;">
                        <p><span class="text-muted" >Seleccione el formato del reporte</span></p>
                    </td>
                </tr>
            </table>
        </div>
    </body>      
</html>