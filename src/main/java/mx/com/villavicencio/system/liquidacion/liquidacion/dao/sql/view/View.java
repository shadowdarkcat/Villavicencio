package mx.com.villavicencio.system.liquidacion.liquidacion.dao.sql.view;

import mx.com.villavicencio.generics.spring.dao.sql.SqlSentence;
import mx.com.villavicencio.system.liquidacion.liquidacion.dao.sql.sql.SqlLiquidacion;

/**
 *
 * @author Gabriel J
 */
public class View extends SqlSentence {

    private static final String TBL_LIQUIDACION = "vw_liquidacion";

    /* QUERY LIQUIDACIONES */
    public static final String VIEW_LIQUIDACIONES;

    static {
        StringBuilder qry = new StringBuilder();
        qry.append(SELECT).append(NEW_LINE).append(TAB).append(ALL).append(NEW_LINE)
                .append(FROM).append(NEW_LINE).append(TAB).append(TBL_LIQUIDACION)
                .append(SEMICOLON);
        VIEW_LIQUIDACIONES = qry.toString();
    }
    /* FIN QUERY LIQUIDACIONES */

    /* QUERY LIQUIDACION */
    public static final String VIEW_LIQUIDACION;

    static {
        StringBuilder qry = new StringBuilder();
        qry.append(SELECT).append(NEW_LINE).append(TAB).append(ALL).append(NEW_LINE)
                .append(FROM).append(NEW_LINE).append(TAB).append(TBL_LIQUIDACION)
                .append(NEW_LINE).append(WHERE).append(NEW_LINE).append(TAB)
                .append(SqlLiquidacion.ID_LIQUIDACION).append(EQUALS).append(PARAMETER).append(SEMICOLON);
        VIEW_LIQUIDACION = qry.toString();
    }
    /* FIN QUERY LIQUIDACION */
}
