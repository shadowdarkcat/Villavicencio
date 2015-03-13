package mx.com.villavicencio.commons.messages;

import org.apache.log4j.Logger;

/**
 *
 * @author Gabriel J
 */

public class ApplicationMessages {

    private final static  Logger log = Logger.getLogger(ApplicationMessages.class);

    public static void debugMessage(String message, Exception ex) {
        log.debug(message, ex);
    }

    public static void warnMessage(String message) {
        log.warn(message);
    }

    public static void errorMessage(String message, Exception ex) {
        log.error(message, ex);
    }

    public static void errorMessage(String message) {
        log.error(message);
    }

    public static void errorMessage(String message, Throwable ex) {
        log.error(message, ex);
    }

    public static void infoMessage(String message) {
        log.info(message);
    }
}
