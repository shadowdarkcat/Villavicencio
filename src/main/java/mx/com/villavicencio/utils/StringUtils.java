package mx.com.villavicencio.utils;

/**
 *
 * @author Gabriel J
 */
public class StringUtils {

    public static boolean isReallyEmptyOrNull(String cadena) {
        return cadena == null || cadena.trim().isEmpty() || cadena.equals("null");
    }

    public static boolean isReallyEmptyOrNull(String[] cadena) {
        return cadena == null || cadena.length == 0;
    }

    public static boolean isSpace(String cadena) {
        return Character.isSpaceChar(cadena.charAt(0));
    }

    public static String replace(String string) {
        string = string.replace("[", "");
        string = string.replace("]", "");
        return string;
    }

    public static String[] split(String string) {
        String[] split = string.split(",");
        return split;
    }

    public static String numberToString(Object number) {
        return String.valueOf(number);
    }

    public static Character stringToChar(String str) {
        return str.charAt(0);
    }

    public static String objectToString(Object object) {
        return String.valueOf(object);
    }
}
