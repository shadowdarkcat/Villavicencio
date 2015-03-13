<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="/WEB-INF/images.tld" prefix="images"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">        
    </head>

    <body>
        <div id="divContainer" class="container-fluid">
            <div class="row">
                <div class="col-sm-3 col-md-2 sidebar">
                    <ul id="ulListProductos" class="nav nav-sidebar">
                        <li class="active"><a href="#">Acci&oacute;n <span class="sr-only">(current)</span></a></li>
                        <br/>
                        <c:if test="${id == 0}">
                            <li id="liAlta">                            
                                <input type="button" id="btnAltaProducto" name="btnAltaProducto" value="Alta Producto" />                                
                            <li>
                            </c:if>
                            <c:if test="${id == 1}">
                            <li id="liBaja">            
                                <input type="button" id="btnBajaProducto" name="btnBajaProducto" value="Baja Producto" />
                            </li>
                        </c:if>
                        <c:if test="${id == 2}">
                            <li id="liCambios">            
                                <input type="button" id="btnCambioProducto" name="btnCambioProducto" value="Cambios Producto" />
                            </li>
                        </c:if>
                    </ul>

                </div>
                <div id="divDatos" class="col-sm-9 col-sm-offset-3 col-md-9 col-md-offset-2 main">
                    <div class="row placeholders">
                        <form enctype="multipart/form-data" id="frmActivarProducto" name="frmActivarProducto" method="post" action="${pageContext.request.contextPath}/controller/productoController?method=4">
                            <div class="col-xs-6 col-sm-9 placeholder">
                                <c:if test="${empty productos}">
                                    <label class="page-header">No hay productos que mostrar</label>
                                </c:if>
                                <c:if test="${not empty productos}">
                                    <fieldset>
                                        <legend class="pager">LISTA DE PRODUCTOS</legend>

                                        <table id="tblProductos" class="table table-bordered table-hover" style="width: 900px;">
                                            <thead>
                                                <tr>
                                                    <th style="text-align: center"># Producto</th>
                                                    <th style="text-align: center">Producto</th>
                                                    <th style="text-align: center">Clave Producto</th>
                                                    <th style="text-align: center">Peso</th>
                                                    <th style="text-align: center">Costo</th>
                                                    <th style="text-align: center">Peso Minimo</th>
                                                    <th style="text-align: center">Peso Maximo</th>
                                                    <th style="text-align: center">Foto</th>
                                                    <th style="text-align: center">Visible</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach items="${productos}" var="productos" varStatus="status">                                            
                                                    <tr>                               
                                                        <td style="text-align: center">
                                                            <input type="hidden" id="txtIdProducto" name="txtIdproducto" value="${productos.idProducto}" />
                                                            ${productos.idProducto}
                                                        </td> 
                                                        <td style="text-align: center">${productos.nombreProducto}</td> 
                                                        <td style="text-align: center">${productos.claveProducto}</td> 
                                                        <td style="text-align: center">${productos.peso}</td>  
                                                        <td style="text-align: center">${productos.costoUnitario}</td>  
                                                        <td style="text-align: center">${productos.pesoMinimo}</td>  
                                                        <td style="text-align: center">${productos.pesoMaximo}</td>  
                                                        <td style="text-align: center"> 
                                                            <c:if test="${not empty productos.imagenProducto}">
                                                                <img src="${images:pathImage(pageContext.request.contextPath, productos.imagenProducto)}" height="50px" width="50px" class="required" />
                                                            </c:if>
                                                            <c:if test="${empty productos.imagenProducto}">
                                                                <img src="${pageContext.request.contextPath}/image/noDisponible.jpg" height="50px" width="50px" class="required"/>
                                                            </c:if>
                                                        </td> 
                                                        <td style="text-align: center">
                                                            <c:if test="${productos.visible == true}">
                                                                <input type="checkbox" id="chkVisible" name="chkVisible" value="${productos.visible},${productos.idProducto}" checked="${productos.visible}" disabled="disabled">Sí
                                                            </c:if>
                                                            <c:choose>
                                                                <c:when test="${id == 1}">
                                                                    <c:if test="${productos.visible == false}">
                                                                        <input type="checkbox" id="chkVisible" name="chkVisible" value="${productos.visible},${productos.idProducto}"> No
                                                                    </c:if>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <c:if test="${productos.visible == false}">
                                                                        <input type="checkbox" id="chkVisible" name="chkVisible" value="${productos.visible},${productos.idProducto}" disabled="disabled"> No
                                                                    </c:if> 
                                                                </c:otherwise>
                                                            </c:choose>             
                                                        </td>  
                                                    </tr>
                                                </c:forEach>
                                            </tbody>
                                        </table>
                                    </fieldset>
                                </c:if>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <div id="divProductoNoSelected" title="Error">
            <table>
                <tr>
                    <td style="text-align: center;">
                        <p><span class="text-muted" >Por favor, seleccione un producto.</span></p>
                    </td>
                </tr>
            </table>
        </div>
        <div id="divActivarProducto" title="Aviso">
            <table>
                <tr>
                    <td style="text-align: center;">
                        <p><span class="text-muted" >¿ Desea activar el producto ?</span></p>
                    </td>
                </tr>
            </table>
        </div>
        <div id="divProductoNoVisible" title="Error">
            <table>
                <tr>
                    <td style="text-align: center;">
                        <p><span class="text-muted" >No se puede modificar el registro, su estado es no visible</span></p>
                    </td>
                </tr>
            </table>
        </div>
        <c:if test="${exist == true}">
            <div id="divExisteProducto" title="Error">
                <table>
                    <tr>
                        <td style="text-align: center;">
                            <input type="hidden" id="existProducto" name="existProducto" value="${exist}"/>
                            <p><span class="text-muted" >El producto ya existe</span></p>
                        </td>
                    </tr>
                </table>
            </div>
        </c:if>
    </body>
</html>
