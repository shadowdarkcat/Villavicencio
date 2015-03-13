package mx.com.villavicencio.system.venta.devoluciones.devoluciones.dao.sql.view;

import mx.com.villavicencio.generics.spring.dao.sql.SqlSentence;
import mx.com.villavicencio.system.venta.devoluciones.devoluciones.dao.sql.sql.SqlDevoluciones;

/**
 *
 * @author Gabriel J
 */
public class View extends SqlSentence {

    private static final String TBL_DEVOLUCIONES = "vw_devoluciones";

    /*  QUERY DEVOLUCIONES POR ID DEVOLUCIONES */
    public static final String VIEW_DEVOLUCIONES;

    static {
        StringBuilder qry = new StringBuilder();
        qry.append(SELECT).append(NEW_LINE).append(TAB).append(ALL).append(NEW_LINE)
                .append(FROM).append(NEW_LINE).append(TAB).append(TBL_DEVOLUCIONES).append(NEW_LINE)
                .append(WHERE).append(NEW_LINE).append(TAB).append(SqlDevoluciones.ID_DEVOLUCIONES)
                .append(EQUALS).append(PARAMETER).append(SEMICOLON);
        VIEW_DEVOLUCIONES = qry.toString();
    }
    /* FIN QUERY DEVOLUCIONES */
}
