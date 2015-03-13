package mx.com.villavicencio.commons.exception;

/**
 *
 * @author Gabriel J
 */

public class MessageException {

    public static Exception messageException(String message, String cause) {
        Throwable throwable = new Throwable(cause);
        Exception ex = new Exception(message, throwable);
        return ex;
    }
}
