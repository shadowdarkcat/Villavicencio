package mx.com.villavicencio.system.puestos.dao.sql.view;

import mx.com.villavicencio.generics.spring.dao.sql.SqlSentence;
import mx.com.villavicencio.system.puestos.dao.sql.sql.SqlPost;

/**
 *
 * @author Gabriel J
 */
public class View extends SqlSentence {

    private static final String TBL_PUESTOS = "vw_puestos";

    /* QUERY PUESTOS */
    public static final String PUESTOS;

    static {
        StringBuilder qry = new StringBuilder();
        qry.append(SELECT).append(NEW_LINE).append(TAB).append(ALL).append(NEW_LINE)
                .append(FROM).append(NEW_LINE).append(TAB).append(TBL_PUESTOS).append(SEMICOLON);
        PUESTOS = qry.toString();
    }
    /* FIN QUERY PUESTOS */

    /* QUERY PUESTO */
    public static final String PUESTO;

    static {
        StringBuilder qry = new StringBuilder();
        qry.append(SELECT).append(NEW_LINE).append(TAB).append(ALL).append(NEW_LINE)
                .append(FROM).append(NEW_LINE).append(TAB).append(TBL_PUESTOS).append(NEW_LINE)
                .append(WHERE).append(NEW_LINE).append(TAB).append(SqlPost.ID_PUESTO)
                .append(EQUALS).append(PARAMETER).append(SEMICOLON);
        PUESTO = qry.toString();
    }
    /* FIN QUERY PUESTO */
}
