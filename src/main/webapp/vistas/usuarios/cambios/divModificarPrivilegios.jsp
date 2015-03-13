<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="formulario" style="width: 100%;">
    <div id="divModificarPrivilegio" title="Cambio Privilegios">
        <c:if test="${modificar == true}">
            <form id="frmModificarPrivilegios" name="frmModificarPrivilegios" method="post" action="${pageContext.request.contextPath}/controller/usuariosController?method=6">
                <input type="hidden" id="txtIdAction" name="txtIdAction" value="${id}" readonly="readOnly" />
                <input type="hidden" id="txtIdUsuario" name="txtIdUsuario" value="${usuario.idUsuario}" readonly="readonly"/>                      
                <input type="hidden" id="txtPuestoResponse" name="txtPuestoResponse" value="${usuario.puesto}" readonly="readOnly" />
                <label for="txtNombre"><span>*</span> Nombre Completo :</label>
                <label id="lblNombre">${usuario.nombreCompleto}</label>
                <br/><br/>Privilegios :<br/><br/>
                <div id="cuadroArbol">
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
        </c:if>
        <c:if test="${modificar == false}">
            <form id="frmModificarPrivilegios" name="frmModificarPrivilegios" method="post" action="${pageContext.request.contextPath}/controller/usuariosController?method=7">
                <input type="hidden" id="txtIdAction" name="txtIdAction" value="${id}" readonly="readOnly" />
                <input type="hidden" id="txtIdUsuario" name="txtIdUsuario" class="required" readonly="readonly"/>    
                <input type="hidden" id="txtPuesto" name="txtPuesto" readonly="readOnly" />
            </form>
        </c:if>
    </div>
</div>
<div id="divMessageModificarPrivilegios">
    <table>
        <tr>
            <td><label>Los privilegios ser&aacute;n modificados</label></td>
        </tr>
        <tr>
            <td><label>¿Desea continuar?</label></td>
        </tr>
    </table>
</div>

<c:if test="${modificar == true}">
    <input type="hidden" id="testModificar" name="testModificar" value="${modificar}"/>
</c:if>