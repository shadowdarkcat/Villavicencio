<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="formulario" style="width: 100%;">    
    <div id="divAltaUsuario" title="Registro Usuario">
        <form id="frmAltaUsuario" name="frmAltaUsuario" method="post" action="${pageContext.request.contextPath}/controller/usuariosController?method=1">
            <input type="hidden" id="txtIdAction" name="txtIdAction" value="${id}" />
            <input type="hidden" id="cboTextName" name="cboTextName" />
            <table>
                <tr>
                    <td><span class="text-muted" >* Puesto :</span></td>
                    <td>
                        <select id="cboPuesto" name="cboPuesto" class="required">
                            <option value="0">Seleccione ...</option>
                            <c:forEach items="${puestos}" var="puestos" varStatus="status">
                                <option value="${puestos.postName}">${puestos.postName}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr id="trCboVendedor" style="display: none;">
                    <td><span class="text-muted" >* Vendedor :</span></td>
                    <td><select id="cboVendedor" name="cboVendedor" class="required">
                            <option value="0">Seleccione ...</option>
                            <c:forEach items="${vendedores}" var="vendedores" varStatus="status">
                                <option value="${vendedores.idVendedor}"> 
                                    ${vendedores.nombre} ${vendedores.apellidoPaterno} ${vendedores.apellidoMaterno}
                                </option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr id="trNombre" style="display: none;">
                    <td><span class="text-muted" >* Nombre Completo :</span></td>
                    <td><input type="text" id="txtNombre" name="txtNombre" class="required" onkeypress="mayuscula(this);" /></td>
                </tr>
                <tr>
                    <td><span class="text-muted" >* No Tel&eacute;fono :</span></td>
                    <td><input type="tel" id="txtTel1" name="txtTel1" class="required"/></td>
                </tr>
                <tr>
                    <td><span class="text-muted" >* Nombre Usuario :</span></td>
                    <td><input type="text" id="txtNombreUsuario" name="txtNombreUsuario" class="required" onkeypress="mayuscula(this);"/></td>
                </tr>
                <tr>
                    <td><span class="text-muted" >* Contrase&ntilde;a :</span></td>
                    <td><input type="password" id="txtPwd" name="txtPwd" class="required" onkeypress="mayuscula(this);" />
                </tr>
                <tr>
                    <td><span class="text-muted" >* Repetir Contrase&ntilde;a :</span></td>
                    <td><input type="password" id="txtPwd1" name="txtPwd1" class="required" onblur="equal();" onkeypress="mayuscula(this);" />
                        <label id="error" style="display: none"><span class="text-muted" >La contrase&ntilde;a no coincide</span></label></td>
                </tr>

                <tr>
                    <td>Privilegios :</td>

                </tr>
            </table>
            <div id="cuadroArbol">      
                <br/>
                <c:choose>
                    <c:when test="${not empty menuString}">
                        ${menuString}
                    </c:when>
                    <c:otherwise>
                        <c:otherwise>
                            No se obtuvieron resultados
                        </c:otherwise>
                    </c:otherwise>
                </c:choose>
            </div>
        </form>
    </div>
</div>