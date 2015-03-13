package mx.com.villavicencio.system.movimientos.bancos.dao.sql.view;

import mx.com.villavicencio.generics.spring.dao.sql.SqlSentence;
import mx.com.villavicencio.system.movimientos.bancos.dao.sql.sql.SqlBancos;

/**
 *
 * @author Gabriel J
 */
public class View extends SqlSentence {

    private static final String TBL_BANCOS = "vw_bancos";

    /* QUERY LISTA DE BANCOS */
    public static final String VIEW_BANCOS;

    static {
        StringBuilder qry = new StringBuilder();
        qry.append(SELECT).append(NEW_LINE).append(TAB).append(ALL).append(NEW_LINE)
                .append(FROM).append(NEW_LINE).append(TAB).append(TBL_BANCOS)
                .append(ORDER_BY).append(SqlBancos.NOMBRE_BANCO).append(ASC).append(SEMICOLON);
        VIEW_BANCOS = qry.toString();
    }
    /* FIN QUERY LISTA DE BANCOS */

    /* QUERY BANCO */
    public static final String VIEW_BANCO;

    static {
        StringBuilder qry = new StringBuilder();
        qry.append(SELECT).append(NEW_LINE).append(TAB).append(ALL).append(NEW_LINE)
                .append(FROM).append(NEW_LINE).append(TAB).append(TBL_BANCOS)
                .append(NEW_LINE).append(WHERE).append(NEW_LINE).append(TAB)
                .append(SqlBancos.ID_BANCOS).append(EQUALS).append(PARAMETER).append(SEMICOLON);
        VIEW_BANCO = qry.toString();
    }
    /* FIN QUERY BANCO */
}
