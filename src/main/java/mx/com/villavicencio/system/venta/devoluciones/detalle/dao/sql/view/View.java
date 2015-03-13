package mx.com.villavicencio.system.venta.devoluciones.detalle.dao.sql.view;

import mx.com.villavicencio.generics.spring.dao.sql.SqlSentence;
import static mx.com.villavicencio.generics.spring.dao.sql.SqlSentence.ALL;
import static mx.com.villavicencio.generics.spring.dao.sql.SqlSentence.EQUALS;
import static mx.com.villavicencio.generics.spring.dao.sql.SqlSentence.FROM;
import static mx.com.villavicencio.generics.spring.dao.sql.SqlSentence.NEW_LINE;
import static mx.com.villavicencio.generics.spring.dao.sql.SqlSentence.PARAMETER;
import static mx.com.villavicencio.generics.spring.dao.sql.SqlSentence.SELECT;
import static mx.com.villavicencio.generics.spring.dao.sql.SqlSentence.SEMICOLON;
import static mx.com.villavicencio.generics.spring.dao.sql.SqlSentence.TAB;
import static mx.com.villavicencio.generics.spring.dao.sql.SqlSentence.WHERE;
import mx.com.villavicencio.system.venta.devoluciones.devoluciones.dao.sql.sql.SqlDevoluciones;

/**
 *
 * @author Gabriel J
 */
public class View extends SqlSentence {

    private static final String TBL_DETALLE_DEVOLUCIONES = "vw_detalledevoluciones";

    /* QUERY DETALLE DEVOLUCIONES POR ID DEVOLUCION */
    public static final String VIEW_DETALLE_DEVOLUCION;

    static {
        StringBuilder qry = new StringBuilder();
        qry.append(SELECT).append(NEW_LINE).append(TAB).append(ALL).append(NEW_LINE)
                .append(FROM).append(NEW_LINE).append(TAB).append(TBL_DETALLE_DEVOLUCIONES).append(NEW_LINE)
                .append(WHERE).append(NEW_LINE).append(TAB).append(SqlDevoluciones.ID_DEVOLUCIONES)
                .append(EQUALS).append(PARAMETER).append(SEMICOLON);
        VIEW_DETALLE_DEVOLUCION = qry.toString();
    }
    /* FIN QUERY DETALLE DEVOLUCIONES POR ID DEVOLUCION */
}
