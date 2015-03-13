package mx.com.villavicencio.system.productos.inventario.dao.sql.view;

import mx.com.villavicencio.generics.spring.dao.sql.SqlSentence;
import mx.com.villavicencio.system.productos.productos.dao.sql.sql.SqlProducto;

/**
 *
 * @author Gabriel J
 */
public class View extends SqlSentence {

    private static final String TBL_INVENTARIO = "vw_inventario";

   /*QUERY INVENTARIOS*/
    public static final String VIEW_INVENTARIO;

    static {
        StringBuilder qry = new StringBuilder();
        qry.append(SELECT).append(NEW_LINE).append(TAB).append(ALL).append(NEW_LINE)
                .append(FROM).append(NEW_LINE).append(TAB).append(TBL_INVENTARIO)
                .append(NEW_LINE).append(WHERE).append(NEW_LINE).append(TAB)
                .append(SqlProducto.NOMBRE_PRODUCTO).append(EQUALS).append(PARAMETER)
                .append(SEMICOLON);
        VIEW_INVENTARIO = qry.toString();
    }
    /*FIN QUERY INVENTARIOS*/
}
