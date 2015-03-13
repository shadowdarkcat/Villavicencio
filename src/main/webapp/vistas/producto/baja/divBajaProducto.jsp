<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<div id="divBajaProducto" title="Baja Producto">
    <form enctype="multipart/form-data" id="frmBajaProducto" name="frmBajaProducto" method="post" action="${pageContext.request.contextPath}/controller/productoController?method=2">
        <input type="hidden" id="txtIdAction" name="txtIdAction" value="${id}" />
        <table> 
            <tr>
                <td><span class="text-muted" >Producto :</span></td>
                <td><input type="text" name="txtProducto" id="txtProducto" class="required" onkeypress="mayuscula(this);"readonly="readonly"/>
                    <input type="hidden" name="txtIdProducto" id="txtIdProducto" readonly="readonly" />
                </td>
            </tr>
            <tr>
                <td><span class="text-muted" >Clave Producto :</span></td>
                <td><input type="text" name="txtClv" id="txtClv" class="required" onkeypress="mayuscula(this);"readonly="readonly"/></td>
            </tr>
            <tr>
                <td><span class="text-muted" >Unidad Peso :</span></td>
                <td><input type="text" id="txtUnidadPeso" name="txtUnidadPeso" class="required" readonly="readonly"/></td>
            </tr>
            <tr>  
                <td><span class="text-muted" >Peso Maximo :</span></td>
                <td><input type="text" id="txtMaximo" name="txtMaximo" class="required" readonly="readonly"/></td>
            </tr>
            <tr>  
                <td><span class="text-muted" >Peso Minimo :</span></td>
                <td><input type="text" id="txtMinimo" name="txtMinimo" class="required" onblur="minMax();" readonly="readonly"/></td>
            </tr>
            <tr>
                <td><span class="text-muted" >Costo :</span></td>
                <td><input type="text" name="txtCosto" id="txtCosto" class="required" readonly="readonly"/></td>
            </tr>
            <tr>
                <td><span class="text-muted" >Visible :</span></td>
                <td><input type="checkbox" id="chkVisible" name="chkVisible"  value="true" checked="checked" onclick="return false">S&iacute;</td>
            </tr>
        </table>
    </form>
</div>
<div id="divMessageBajaProducto" title="Advertencia">
    <table>
        <tr>
            <td><span class="text-muted" >El registro ser&aacute; dado de baja</span></td>
        </tr>
        <tr>
            <td><span class="text-muted" >¿Desea continuar?</span></td>
        </tr>
    </table>
</div>