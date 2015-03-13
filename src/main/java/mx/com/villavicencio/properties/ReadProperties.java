package mx.com.villavicencio.properties;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import mx.com.villavicencio.commons.exception.ApplicationException;
import mx.com.villavicencio.commons.messages.ApplicationMessages;

/**
 *
 * @author Gabriel J
 */
public class ReadProperties {

    private static String error;

    public ReadProperties() {
        error = "Error al cargar el archivo de propiedades";
    }

    public static Properties getPropertiesFromFile(String pathFile) {
        Properties properties = new Properties();
        try {
            URL url = ReadProperties.class.getClassLoader().getResource(pathFile);
            if (url != null) {
                properties.load(url.openStream());
            } else {
                return null;
            }
        } catch (MalformedURLException ex) {
            ApplicationMessages.errorMessage(error, ex);
            throw new ApplicationException(error, ex);
        } catch (IOException ex) {
            ApplicationMessages.errorMessage(error, ex);
            throw new ApplicationException(error, ex);
        }
        return properties;
    }

    public static Properties getPropertiesFromFile(URL url) {
        Properties propiedades = new Properties();
        try {
            if (url != null) {
                propiedades.load(url.openStream());
            } else {
                return null;
            }
        } catch (IOException ex) {
            ApplicationMessages.errorMessage(error, ex);
            throw new ApplicationException(error, ex);
        }
        return propiedades;
    }
}
