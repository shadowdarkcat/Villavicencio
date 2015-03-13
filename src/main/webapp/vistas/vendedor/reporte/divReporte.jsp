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
        <div id="divTipoReporteVendedor" title="Tipo Reporte">          
            <form id="frmReporteVendedores" name="frmReporteVendedores" method="post" action="${pageContext.request.contextPath}/controller/vendedorController?method=9" target="_blank">
                <input type="hidden" id="txtIdAction" name="txtIdAction" value="${id}" />
                <input type="hidden" id="reportName" name="reportName" value="vendedores" /> 
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
        <div id="errorReporteVendedor" title="Error !!!">
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