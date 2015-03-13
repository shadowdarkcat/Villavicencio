package mx.com.villavicencio.commons.response;

import javax.servlet.http.HttpServletResponse;
import mx.com.villavicencio.generics.content.Content;
import mx.com.villavicencio.generics.types.GenericTypes;

/**
 *
 * @author Gabriel J
 */
public class Response {

    public static HttpServletResponse getHttpServletResponse(HttpServletResponse response) {
        response.setContentType(Content.CONTENT_TYPE);
        response.setHeader(Content.HEADER, Content.CACHE);
        response.setDateHeader(Content.EXPIRES, GenericTypes.ZERO);
        return response;
    }
}
