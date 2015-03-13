package mx.com.villavicencio.system.movimientos.abonos.dao.sql.view;

import mx.com.villavicencio.generics.spring.dao.sql.SqlSentence;
import mx.com.villavicencio.system.movimientos.abonos.dao.sql.sql.SqlAbonos;

/**
 *
 * @author Gabriel J
 */
public class View extends SqlSentence {

    private static final String TBL_ABONOS = "vw_abonos";

    /* QUERY ABONOS POR ID */
    public static final String VIEW_ABONOS;

    static {
        StringBuilder qry = new StringBuilder();
        qry.append(SELECT).append(NEW_LINE).append(TAB).append(ALL).append(NEW_LINE)
                .append(FROM).append(NEW_LINE).append(TAB).append(TBL_ABONOS).append(NEW_LINE)
                .append(WHERE).append(NEW_LINE).append(TAB).append(SqlAbonos.ID_ABONOS)
                .append(EQUALS).append(PARAMETER).append(SEMICOLON);
        VIEW_ABONOS = qry.toString();
    }
    /* FIN QUERY ABONOS POR ID */
}
