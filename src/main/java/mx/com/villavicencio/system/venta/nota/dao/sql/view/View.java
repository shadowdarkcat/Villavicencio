package mx.com.villavicencio.system.venta.nota.dao.sql.view;

import mx.com.villavicencio.generics.spring.dao.sql.SqlSentence;
import mx.com.villavicencio.system.pedido.pedido.dao.sql.sql.SqlPedido;
import mx.com.villavicencio.system.venta.nota.dao.sql.sql.SqlNotaVenta;

/**
 *
 * @author Gabriel J
 */
public class View extends SqlSentence {

    private static final String TBL_NOTA_VENTA = "vw_notaventa";

    /*QUERY BUSQUEDA NOTA VENTA POR EL PEDIDO*/
    public static final String VIEW_NOTA_VENTA;

    static {
        StringBuilder qry = new StringBuilder();
        qry.append(SELECT).append(NEW_LINE).append(TAB).append(ALL).append(NEW_LINE)
                .append(FROM).append(NEW_LINE).append(TAB).append(TBL_NOTA_VENTA)
                .append(NEW_LINE).append(WHERE).append(NEW_LINE).append(TAB)
                .append(SqlPedido.ID_PEDIDO).append(EQUALS).append(PARAMETER).append(SEMICOLON);
        VIEW_NOTA_VENTA = qry.toString();
    }
    /*FIN QUERY BUSQUEDA NOTA VENTA POR EL PEDIDO*/

    /*QUERY BUSQUEDA NOTA VENTA POR ID NOTA VENTA*/
    public static final String VIEW_NOTA_VENTA_ID;

    static {
        StringBuilder qry = new StringBuilder();
        qry.append(SELECT).append(NEW_LINE).append(TAB).append(ALL).append(NEW_LINE)
                .append(FROM).append(NEW_LINE).append(TAB).append(TBL_NOTA_VENTA)
                .append(NEW_LINE).append(WHERE).append(NEW_LINE).append(TAB)
                .append(SqlNotaVenta.ID_NOTA_VENTA).append(EQUALS).append(PARAMETER).append(SEMICOLON);
        VIEW_NOTA_VENTA_ID = qry.toString();
    }
    /*QUERY BUSQUEDA NOTA VENTA POR ID NOTA VENTA*/
}
