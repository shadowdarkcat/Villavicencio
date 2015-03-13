package mx.com.villavicencio.image.helper;

import java.io.File;
import mx.com.villavicencio.generics.variables.Variables;
import mx.com.villavicencio.utils.StringUtils;

/**
 *
 * @author Gabriel J
 */
public class ImageViewHelper {

    public static String getPath(String context, String absolutePath) {
        String outcome;
        String so = System.getProperty(Variables.SISTEMA_OPERATIVO);
        if (so.equalsIgnoreCase(Variables.SISTEMA_OPERATIVO_LINUX)) {
            char separator[] = context.substring(0, 1).toCharArray();
            if (separator[0] == '/') {
                context = context.replaceAll("/", "\\\\");
            }
        } else {
             absolutePath = absolutePath.replaceAll("\\\\", "/");
        }
        outcome = absolutePath.substring(absolutePath.indexOf(context));

        return outcome;
    }

    public static void deleteImageFromServer(String path) {
        if (!StringUtils.isReallyEmptyOrNull(path)) {
            File file = new File(path);
            if ((file.exists()) && (file.canRead())) {
                file.delete();
            }
        }
    }
}
