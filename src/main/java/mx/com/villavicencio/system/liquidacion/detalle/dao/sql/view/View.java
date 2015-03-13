package mx.com.villavicencio.system.liquidacion.detalle.dao.sql.view;

import mx.com.villavicencio.generics.spring.dao.sql.SqlSentence;
import mx.com.villavicencio.system.liquidacion.liquidacion.dao.sql.sql.SqlLiquidacion;

/**
 *
 * @author Gabriel J
 */
public class View extends SqlSentence {

    private static final String TBL_DETALLE_LIQUIDACION = "vw_detalleliquidacion";

    /* QUERY DETALLES LIQUIDACION POR ID LIQUIDACION */
    public static final String VIEW_DETALLE_LIQUIDACION;

    static {
        StringBuilder qry = new StringBuilder();
        qry.append(SELECT).append(NEW_LINE).append(TAB).append(ALL).append(NEW_LINE)
                .append(FROM).append(NEW_LINE).append(TAB).append(TBL_DETALLE_LIQUIDACION)
                .append(NEW_LINE).append(WHERE).append(NEW_LINE).append(TAB)
                .append(SqlLiquidacion.ID_LIQUIDACION).append(EQUALS).append(PARAMETER).append(SEMICOLON);
        VIEW_DETALLE_LIQUIDACION = qry.toString();
    }
    /* FIN QUERY DETALLES LIQUIDACION POR ID LIQUIDACION */
}
