package mx.com.villavicencio.system.pedido.detalles.dao.sql.view;

import mx.com.villavicencio.generics.spring.dao.sql.SqlSentence;
import mx.com.villavicencio.system.pedido.detalles.dao.sql.sql.SqlDetallePedido;

/**
 *
 * @author Gabriel J
 */
public class View extends SqlSentence {

    private static final String TBL_DETALLE_PEDIDO = "vw_detallePedido";

    /* QUERY DETALLES DE PEDIDOS */
    public static final String DETALLES_PEDIDOS;

    static {
        StringBuilder qry = new StringBuilder();
        qry.append(SELECT).append(NEW_LINE).append(TAB).append(ALL).append(NEW_LINE)
                .append(FROM).append(NEW_LINE).append(TAB).append(TBL_DETALLE_PEDIDO)
                .append(SEMICOLON);
        DETALLES_PEDIDOS = qry.toString();
    }
    /* FIN QUERY DETALLES DE PEDIDOS*/

    /* QUERY DETALLE DE PEDIDO */
    public static final String DETALLE_PEDIDO;

    static {
        StringBuilder qry = new StringBuilder();
        qry.append(SELECT).append(NEW_LINE).append(TAB).append(ALL).append(NEW_LINE)
                .append(FROM).append(NEW_LINE).append(TAB).append(TBL_DETALLE_PEDIDO)
                .append(NEW_LINE).append(WHERE)
                .append(NEW_LINE).append(TAB).append(SqlDetallePedido.ID_DETALLE_PEDIDO)
                .append(EQUALS).append(PARAMETER).append(SEMICOLON);
        DETALLE_PEDIDO = qry.toString();
    }
    /* FIN QUERY DETALLE PEDIDO*/
}
