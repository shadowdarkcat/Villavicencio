package mx.com.villavicencio.system.movimientos.movimientos.dao.sql.view;

import mx.com.villavicencio.generics.spring.dao.sql.SqlSentence;
import mx.com.villavicencio.generics.types.GenericTypes;
import mx.com.villavicencio.system.venta.nota.dao.sql.sql.SqlNotaVenta;

/**
 *
 * @author Gabriel J
 */
public class View extends SqlSentence {

    private static final String TBL_MOVIMIENTOS = "vw_movimientos";

    /* QUERY MOVIMIENTOS POR ID NOTA */
    public static final String VIEW_MOVIMIENTOS;

    static {
        StringBuilder qry = new StringBuilder();
        qry.append(SELECT).append(NEW_LINE).append(TAB).append(ALL).append(NEW_LINE)
                .append(FROM).append(NEW_LINE).append(TAB).append(TBL_MOVIMIENTOS).append(NEW_LINE)
                .append(WHERE).append(NEW_LINE).append(TAB).append(SqlNotaVenta.ID_NOTA_VENTA)
                .append(EQUALS).append(PARAMETER).append(NEW_LINE).append(SEMICOLON);
        VIEW_MOVIMIENTOS = qry.toString();
    }
    /* FIN QUERY MOVIMIENTOS POR ID NOTA */

    /* QUERY MOVIMIENTOS POR ID NOTA */
    public static final String VIEW_MOVIMIENTO;

    static {
        StringBuilder qry = new StringBuilder();
        qry.append(SELECT).append(NEW_LINE).append(TAB).append(ALL).append(NEW_LINE)
                .append(FROM).append(NEW_LINE).append(TAB).append(TBL_MOVIMIENTOS).append(NEW_LINE)
                .append(WHERE).append(NEW_LINE).append(TAB).append(SqlNotaVenta.ID_NOTA_VENTA)
                .append(EQUALS).append(PARAMETER).append(NEW_LINE)
                .append(NEW_LINE).append(LIMIT).append(GenericTypes.ONE).append(SEMICOLON);
        VIEW_MOVIMIENTO = qry.toString();
    }
    /* FIN QUERY MOVIMIENTOS POR ID NOTA */
}
