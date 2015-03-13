<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="formulario"> 
    <div id="divAltaProducto" title="Registro Producto">
        <form id="frmAltaProducto" enctype="multipart/form-data" name="frmAltaProducto" method="post" action="${pageContext.request.contextPath}/controller/productoController?method=1">
            <input type="hidden" id="txtIdAction" name="txtIdAction" value="${id}" />
            <table>
                <tr>
                    <td><span class="text-muted" >* Producto :</span></td>
                    <td><input type="text" name="txtProducto" id="txtProducto" class="required" onkeypress="mayuscula(this);"/></td>
                </tr>
                <tr>
                    <td><span class="text-muted" >* Clave Producto :</span></td>
                    <td><input type="text" name="txtClv" id="txtClv" class="required" onkeypress="mayuscula(this);"/></td>
                </tr>
                <tr>
                    <td><span class="text-muted" >* Unidad Peso :</span></td>
                    <td id="tdCbo"></td>
                </tr>
                <tr>  
                    <td><span class="text-muted" >* Peso Maximo :</span></td>
                    <td><input type="text" id="txtMaximo" name="txtMaximo" class="required" /></td>
                </tr>
                <tr>  
                    <td><span class="text-muted" >* Peso Minimo :</span></td>
                    <td><input type="text" id="txtMinimo" name="txtMinimo" class="required" onblur="minMax();" />
                        <div id="divMinimoAlert" style="display: none;">
                            <span class="text-muted" ><label>El peso minimo no puede ser mayor al peso maximo</label></span>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td><span class="text-muted" >* Costo :</span></td>
                    <td><input type="text" name="txtCosto" id="txtCosto" class="required" /></td>
                </tr>
                <tr>
                    <td><span class="text-muted" >Imagen Producto :</span></td>
                    <td><input <input type="file" name="txtImageProducto" id="txtImageProducto" /></td>
                </tr>
            </table>
        </form>
    </div>
</div>