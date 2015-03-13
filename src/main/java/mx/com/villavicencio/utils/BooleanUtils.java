package mx.com.villavicencio.utils;

/**
 *
 * @author Gabriel J
 */
public class BooleanUtils {

    public static Boolean isChecked(String string) {
        Boolean bool = Boolean.valueOf(string);
        return bool != true;
    }
}
