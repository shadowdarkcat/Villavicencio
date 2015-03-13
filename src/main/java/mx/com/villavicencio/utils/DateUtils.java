package mx.com.villavicencio.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import mx.com.villavicencio.commons.exception.ApplicationException;
import mx.com.villavicencio.commons.messages.ApplicationMessages;
import mx.com.villavicencio.properties.Property;
import mx.com.villavicencio.properties.PropertiesBean;

/**
 *
 * @author Gabriel J
 */
public class DateUtils {

    public static Date stringToDate(String str) {
        DateFormat df = null;
        Date date = null;
        try {
            df = DateFormat.getDateInstance();
            date = df.parse(str);
        } catch (ParseException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.EXCEPTION_PARSE_DATE)
                    + "\n" + NumberUtils.class.getSimpleName());
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.EXCEPTION_PARSE_DATE), ex);
        }
        return date;
    }

    public static String dateToString(Date date) {
        DateFormat fecha = new SimpleDateFormat("dd/MM/yyyy");
        return fecha.format(date);
    }

    public static Date dateNow() {
        return new Date();
    }
}
