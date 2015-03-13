package mx.com.villavicencio.system.movimientos.cargos.dao.sql.view;

import mx.com.villavicencio.generics.spring.dao.sql.SqlSentence;
import mx.com.villavicencio.system.movimientos.cargos.dao.sql.sql.SqlCargos;

/**
 *
 * @author Gabriel J
 */

public class View extends SqlSentence{

    private static final String TBL_CARGOS = "vw_cargos";
    
    /* QUERY CARGOS POR ID */
    public static final String VIEW_CARGOS;
    static{
        StringBuilder qry = new StringBuilder();
        qry.append(SELECT + NEW_LINE + TAB + ALL + NEW_LINE + FROM + NEW_LINE + TAB
        + TBL_CARGOS + NEW_LINE + WHERE + NEW_LINE + TAB + SqlCargos.ID_CARGOS 
        + EQUALS + PARAMETER + SEMICOLON);
        VIEW_CARGOS = qry.toString();
    }
    /* FIN QUERY CARGOS POR ID */
}
