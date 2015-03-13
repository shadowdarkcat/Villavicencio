<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div id="divContainer" class="container-fluid">
    <div class="row">
        <div class="col-sm-3 col-md-2 sidebar">
            <ul id="ulListProductos" class="nav nav-sidebar">
                <li class="active"><a href="#">Acci&oacute;n <span class="sr-only">(current)</span></a></li>
                <br/>
                <c:if test="${id == 0}">
                    <li id="liAlta">                            
                        <input type="button" id="btnAltaUsuario" name="btnAltaUsuario" value="Alta Usuario" />                                
                    <li>
                    </c:if>                   
                    <c:if test="${id == 2}">
                    <li id="liCambios">            
                        <input type="button" id="btnCambioUsuario" name="btnCambioUsuario" value="Cambios Usuario" />
                    </li>
                    <br/>
                    <li>            
                        <input type="button" id="btnCambioPrivilegio" name="btnCambioPrivilegio" value="Cambio Privilegios" />
                    </li>
                </c:if>
            </ul>

        </div>
        <div id="divDatos" class="col-sm-9 col-sm-offset-3 col-md-9 col-md-offset-2 main">
            <div class="row placeholders">
                <div id="divFormulario" class="formulario">
                    <fieldset>
                        <legend class="pager">LISTADO DE USUARIOS</legend>
                    </fieldset>                       
                    <table id="tblUsuarios" class="table table-bordered table-hover">
                        <thead>
                            <tr>
                                <th style="text-align: center"># Usuario</th>
                                <th style="text-align: center">Nombre</th>
                                <th style="text-align: center">No Tel&eacute;fono</th>
                                <th style="text-align: center">Nombre Usuario</th>
                                <th style="text-align: center">Puesto</th>
                                <!-- <th style="text-align: center">Visible</th>!-->
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${usuarios}" var="usuarios" varStatus="status">
                                <tr class="${status.index % 2 eq 0 ? "odd" : "even"}" id="trUsuarios">
                                    <td style="text-align: center">
                                        <input type="hidden" name="txtIdUsuario" id="txtIdUsuario" value="${usuarios.idUsuario}" />
                                        ${usuarios.idUsuario}
                                    </td>
                                    <td style="text-align: center">
                                        ${usuarios.nombreCompleto}
                                    </td>
                                    <td style="text-align: center">
                                        ${usuarios.noTelefono}
                                    </td>
                                    <td style="text-align: center">
                                        ${usuarios.nombreUsuario}
                                    </td>
                                    <td style="text-align: center">
                                        <input type="hidden" id="txtPuesto" name="txtPuesto" value="${usuarios.puesto}" readonly="readOnly" />
                                        ${usuarios.puesto}
                                    </td>
                                </tr>                         
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
<div id="divUsuarioNoSelected" title="Error">
    <table>
        <tr>
            <td><span class="text-muted" >
                    <p>Por favor, seleccione un usuario.</p></span>
            </td>
        </tr>
    </table>
</div>
<c:if test="${exist == true}">
    <div id="divExisteUsuario" title="Error">
        <table>
            <tr>
                <td>
                    <input type="hidden" id="existUsuario" name="existUsuario" value="${exist}"/>
                    <span class="text-muted" > <p>El usuario ya existe</p></span>
                </td>
            </tr>
        </table>
    </div>
</c:if>