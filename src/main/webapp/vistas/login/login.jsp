<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Acceso Usuarios</title>
        <jsp:include  page="${pageContext.request.contextPath}/../decorators/scripts/scripts.jsp"/>
    </head>
    <body>
        <div class="container">
            <form class="form-signin" role="form" method="post" action="${pageContext.request.contextPath}/loginController?method=1">
                <h2 class="form-signin-heading"><center>Acceso Sistema</center></h2>
                <label for="txtUser" class="sr-only">Nombre Usuario</label>
                <input type="text" id="txtUser" name = "txtUser" class="form-control" placeholder="Nombre Usuario" required autofocus>
                <label for="txtPass" class="sr-only">Contrase&ntilde;a</label>
                <input type="password" id="txtPass" name = "txtPass" class="form-control" placeholder="Contraseña" required>
                <button class="btn btn-lg btn-primary btn-block" type="submit">Acceder</button>
            </form>
        </div>
    </body>
</html>
