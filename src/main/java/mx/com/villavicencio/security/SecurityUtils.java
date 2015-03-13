package mx.com.villavicencio.security;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.servlet.http.HttpServletRequest;
import mx.com.villavicencio.commons.exception.ApplicationException;
import mx.com.villavicencio.generics.content.Content;
import mx.com.villavicencio.generics.variables.Variables;
import mx.com.villavicencio.properties.Property;
import mx.com.villavicencio.properties.PropertiesBean;
import mx.com.villavicencio.utils.NumberUtils;
import mx.com.villavicencio.utils.StringUtils;

/**
 *
 * @author Gabriel J
 */
public class SecurityUtils {

    public static Boolean isValidUrl(HttpServletRequest request) {
        return !StringUtils.isReallyEmptyOrNull(request.getParameter(Content.METHOD))
                && (!StringUtils.isSpace(request.getParameter(Content.METHOD)))
                && (NumberUtils.isNumeric(request.getParameter(Content.METHOD)));
    }

    public static Boolean isValidSession(HttpServletRequest session) {
        return session.getSession(false).getAttribute(Variables.USER) != null;
    }

    public static String md5(String palabra) {
        String md5 = null;
        try {
            byte[] defaultBytes = palabra.getBytes("UTF8");
            MessageDigest algorithm = MessageDigest.getInstance("MD5");
            algorithm.reset();
            algorithm.update(defaultBytes);
            byte messageDigest[] = algorithm.digest();

            StringBuilder hexString = new StringBuilder();
            for (int i = 0; i < messageDigest.length; i++) {
                int byteValue = 0xFF & messageDigest[i];
                String hexStringAux = byteValue < 16
                        ? "0" + Integer.toHexString(byteValue)
                        : Integer.toHexString(byteValue);

                hexString.append(hexStringAux);
            }
            md5 = hexString.toString();
        } catch (NoSuchAlgorithmException ex) {
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_MD5), ex);
        } catch (UnsupportedEncodingException ex) {
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_CODIFICACION_MD5), ex);
        }
        return md5;
    }
}
