<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <jsp:include page="../../decorators/principal/principal.jsp"/>
        <title>Productos</title>
    </head>
    <body>          
        <c:choose>
            <c:when test="${id == 0}">
                <jsp:include page="alta/alta.jsp" />                    
            </c:when>
            <c:when test="${id == 1}">
                <jsp:include page="baja/baja.jsp" />
            </c:when>
            <c:when test="${id == 2}">
                <jsp:include page="cambios/modificar.jsp" />
            </c:when>
            <c:when test="${id == 3}">
                <jsp:include page="reporte/reporte.jsp" />
            </c:when>
        </c:choose>
    </body>
</html>
