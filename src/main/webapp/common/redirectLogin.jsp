<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
         <jsp:include page="../decorators/principal/principal.jsp"/>
        <link href="${pageContext.request.contextPath}/css/styleException.css" rel="stylesheet" type="text/css" />
        <title>Redirecci&oacute;n</title>
        <script type="text/javascript">
            function enviarForm() {
                document.getElementById('formRedirect').submit()
            }
        </script>
    </head>
    <body onload="setTimeout(enviarForm, 3000)">
        <div style="display: none">
            <form action="${pageContext.request.contextPath}" id="formRedirect" target="_parent">
                <input type="submit" onclick="enviarForm()" value="enviar" />
            </form>
        </div>
        <table width="100%"  >
            <tbody>
                <tr>
                    <td width="33%">&nbsp;</td>
                    <td>&nbsp;</td>
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
                    <td>&nbsp;</td>
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
