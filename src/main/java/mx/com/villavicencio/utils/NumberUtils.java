package mx.com.villavicencio.utils;

import java.math.BigDecimal;
import java.math.BigInteger;
import mx.com.villavicencio.commons.exception.ApplicationException;
import mx.com.villavicencio.commons.messages.ApplicationMessages;
import mx.com.villavicencio.properties.PropertiesBean;
import mx.com.villavicencio.properties.Property;

/**
 *
 * @author Gabriel J
 */
public class NumberUtils {

    private static final BigDecimal PORCENTAJE = new BigDecimal(100.00);

    public static boolean isNumeric(String cadena) {
        return Character.isDigit(cadena.charAt(0));
    }

    public static Integer stringToNumber(String cadena) {
        try {
            return Integer.parseInt(cadena);
        } catch (NumberFormatException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NUMBER_FORMAT_EXCEPTION)
                    + "\n" + NumberUtils.class.getSimpleName());
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NUMBER_FORMAT_EXCEPTION), ex);
        }
    }

    public static BigDecimal convertNumberToPorcentaje(BigDecimal porcentaje) {
        if (porcentaje.equals(BigDecimal.ZERO)) {
            return BigDecimal.ZERO;
        }
        BigDecimal divide = porcentaje.divide(PORCENTAJE).setScale(2, BigDecimal.ROUND_DOWN);
        return divide;
    }

    public static BigDecimal stringToBigDecimal(String cadena) {
        try {
            BigDecimal bigDecimal = new BigDecimal(cadena).setScale(2, BigDecimal.ROUND_DOWN);
            return bigDecimal;
        } catch (NumberFormatException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NUMBER_FORMAT_EXCEPTION)
                    + "\n" + NumberUtils.class.getSimpleName());
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NUMBER_FORMAT_EXCEPTION), ex);
        }
    }

    public static BigDecimal integerToBigDecimal(Integer cadena) {
        try {
            BigDecimal bigDecimal = new BigDecimal(cadena).setScale(2, BigDecimal.ROUND_DOWN);
            return bigDecimal;
        } catch (NumberFormatException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NUMBER_FORMAT_EXCEPTION)
                    + "\n" + NumberUtils.class.getSimpleName());
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NUMBER_FORMAT_EXCEPTION), ex);
        }
    }

    public static BigDecimal convertPorcentajeToNumber(BigDecimal porcentaje) {
        if (porcentaje != null) {
            return porcentaje.multiply(PORCENTAJE);
        } else {
            return new BigDecimal(BigInteger.ZERO);
        }
    }
}
