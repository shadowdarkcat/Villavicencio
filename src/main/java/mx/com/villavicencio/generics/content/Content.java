package mx.com.villavicencio.generics.content;

import javax.servlet.http.HttpServletRequest;
import mx.com.villavicencio.generics.variables.Variables;
import mx.com.villavicencio.system.usuario.dto.DtoUsuario;

/**
 *
 * @author Gabriel J
 */
public class Content {

    public static final String CONTENT_TYPE = "text/html;charset=UTF-8";
    public static final String UTF8 = "UTF-8";
    public static final String HEADER = "Cache-Control";
    public static final String CACHE = "no-cache, no-store, must-revalidate";
    public static final String EXPIRES = "Expires";
    public static final String METHOD = "method";
    public static final String ID = "idAction";
    public static final String CONTENT_TYPE_PDF = "application/pdf";
    public static final String MD5 = "MD5";

    public static final DtoUsuario getSession(HttpServletRequest request) {
        return (DtoUsuario) request.getSession(false).getAttribute(Variables.USER);
    }
}
