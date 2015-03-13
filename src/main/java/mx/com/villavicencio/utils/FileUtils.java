package mx.com.villavicencio.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import mx.com.villavicencio.commons.exception.ApplicationException;
import mx.com.villavicencio.commons.messages.ApplicationMessages;
import mx.com.villavicencio.properties.Property;
import mx.com.villavicencio.properties.PropertiesBean;

/**
 *
 * @author Gabriel J
 */
public class FileUtils {

    public static byte[] getBytes(File file) {
        InputStream is = null;
        try {
            is = new FileInputStream(file);
        } catch (FileNotFoundException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FOUND)
                    + " " + FileUtils.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FOUND), ex);
        }
        return getBytes(is);
    }

    public static byte[] getBytes(String str) {
        byte[] buffer = null;
        InputStream inputStream = null;
        try {
            buffer = str.getBytes();
            int totalBytes = buffer.length;
            inputStream = new ByteArrayInputStream(buffer);
            buffer = new byte[totalBytes];
            inputStream.read(buffer, 0, totalBytes);
        } catch (IOException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_READ)
                    + " " + FileUtils.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_READ), ex);
        } finally {
            try {
                inputStream.close();
            } catch (IOException ex) {
                ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_READ)
                        + " " + FileUtils.class.getSimpleName(), ex);
                throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_READ), ex);
            }
        }
        return buffer;
    }

    public static byte[] getBytes(InputStream inputStream) {
        byte[] buffer = null;
        try {
            int totalBytes = inputStream.available();
            buffer = new byte[totalBytes];
            inputStream.read(buffer, 0, totalBytes);
            inputStream.close();
        } catch (IOException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_READ)
                    + " " + FileUtils.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_READ), ex);
        }
        return buffer;
    }
}
