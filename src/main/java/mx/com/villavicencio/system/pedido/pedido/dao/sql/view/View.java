package mx.com.villavicencio.system.pedido.pedido.dao.sql.view;

import mx.com.villavicencio.generics.spring.dao.sql.SqlSentence;
import mx.com.villavicencio.system.pedido.pedido.dao.sql.sql.SqlPedido;

/**
 *
 * @author Gabriel J
 */
public class View extends SqlSentence {

    private static final String TBL_PEDIDO = "vw_pedido";

    /* QUERY PEDIDOS */
    public static final String PEDIDOS;

    static {
        StringBuilder qry = new StringBuilder();
        qry.append(SELECT).append(NEW_LINE).append(TAB).append(ALL)
                .append(NEW_LINE).append(FROM).append(NEW_LINE).append(TAB)
                .append(TBL_PEDIDO).append(SEMICOLON);
        PEDIDOS = qry.toString();
    }
    /*FIN QUERY PEDIDOS */

    /* QUERY PEDIDO */
    public static final String PEDIDO;

    static {
        StringBuilder qry = new StringBuilder();
        qry.append(SELECT).append(NEW_LINE).append(TAB).append(ALL)
                .append(NEW_LINE).append(FROM).append(NEW_LINE).append(TAB)
                .append(TBL_PEDIDO).append(NEW_LINE).append(WHERE)
                .append(NEW_LINE).append(TAB).append(SqlPedido.ID_PEDIDO)
                .append(EQUALS).append(PARAMETER).append(SEMICOLON);
        PEDIDO = qry.toString();
    }
    /*FIN QUERY PEDIDO */
}
