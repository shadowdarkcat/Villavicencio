<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="formulario"> 
    <div id="divModificarProducto" title="Cambio Producto">
        <form enctype="multipart/form-data" id="frmModificarProducto" name="frmModificarProducto" method="post" action="${pageContext.request.contextPath}/controller/productoController?method=3">
            <input type="hidden" id="txtIdAction" name="txtIdAction" value="${id}" />
            <table>
                <tr>
                    <td><span class="text-muted" >* Producto :</span></td>
                    <td>
                        <input type="hidden" name="txtIdProducto" id="txtIdProducto" readonly="readonly" />
                        <input type="text" name="txtProducto" id="txtProducto" class="required" onkeypress="mayuscula(this);"/></td>
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
                    <td><input type="text" id="txtMaximo" name="txtMaximo" class="required" />
                        <div id="divMaximoAlert" style="display: none;">
                            <span class="text-muted" > <label>El peso maximo no puede ser menor al peso minimo</label></span>                        </div>
                    </td>
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
<div id="divMessageModificarProd" title="Advertencia">
    <table>
        <tr>
            <td><span class="text-muted" >El registro ser&aacute; modificado</span></td>
        </tr>
        <tr>
            <td><span class="text-muted" >¿Desea continuar?</span></td>
        </tr>
    </table>
</div>