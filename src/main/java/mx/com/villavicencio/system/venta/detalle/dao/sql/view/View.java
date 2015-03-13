package mx.com.villavicencio.system.venta.detalle.dao.sql.view;

import mx.com.villavicencio.generics.spring.dao.sql.SqlSentence;
import mx.com.villavicencio.system.venta.nota.dao.sql.sql.SqlNotaVenta;

/**
 *
 * @author Gabriel J
 */
public class View extends SqlSentence {

    private static final String TBL_DETALLE_NOTA_VENTA = "vw_notaventadetalle";

    /* QUERY DETALLES NOTA VENTA */
    public static final String VIEW_DETALLE_NOTA_VENTA;

    static {
        StringBuilder qry = new StringBuilder();
        qry.append(SELECT).append(NEW_LINE).append(TAB).append(ALL)
                .append(NEW_LINE).append(FROM).append(NEW_LINE).append(TAB)
                .append(TBL_DETALLE_NOTA_VENTA).append(NEW_LINE).append(WHERE)
                .append(NEW_LINE).append(TAB).append(SqlNotaVenta.ID_NOTA_VENTA)
                .append(EQUALS).append(PARAMETER).append(SEMICOLON);
        VIEW_DETALLE_NOTA_VENTA = qry.toString();
    }
    /* FIN QUERY DETALLES NOTA VENTA*/
}
