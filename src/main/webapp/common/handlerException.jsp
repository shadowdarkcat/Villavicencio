<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>   
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <jsp:include page="../decorators/principal/principal.jsp"/>
            <title>Error de Sistema</title>
        </head>
        <body>
            <table width="100%"  >
                <tbody>
                    <tr>
                        <td width="33%">&nbsp;</td>
                        <td width="33%">&nbsp;</td>
                        <td width="33%">&nbsp;</td>
                    </tr>
                    <tr>
                        <td>&nbsp;</td>
                        <td>
                            <div class="exception">
                                <fieldset>
                                    <legend><span class="left">▀</span>Error<span class="right">▀</span></legend>
                                    <table>
                                        <tr>
                                            <td>
                                                <div class="cause">Ha ocurrido un error en el sistema</div>
                                                <div class="mensaje">${ex.message}</div>
                                            </td>
                                        </tr>
                                        <c:if test="${not empty ex.cause}">
                                            <tr>
                                                <td>
                                                    <div id="detailExceptionDiv">
                                                        <div class="cause">Causa: </div>
                                                        <div class="detailException">${ex.cause}</div>
                                                    </div>
                                                </td>
                                            </tr>
                                        </c:if>
                                    </table>
                                </fieldset>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                    </tr>
                </tbody>
            </table>
        </body>
</html>