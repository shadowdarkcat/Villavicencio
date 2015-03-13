package mx.com.villavicencio.properties;

import java.util.Properties;

/**
 *
 * @author Gabriel J
 */

public class PropertiesBean {

    private static final Properties errorFile = ReadProperties.getPropertiesFromFile(Property.ERROR_FILE);
    private static final Properties infoFile = ReadProperties.getPropertiesFromFile(Property.INFO_FILE);
    private static final Properties pagos = ReadProperties.getPropertiesFromFile(Property.TIPO_PAGO);

    public static Properties getErrorFile() {
        return errorFile;
    }

    public static Properties getInfoFile() {
        return infoFile;
    }
    
    public static Properties getPagos(){
        return pagos;
    }
}
