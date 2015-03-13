package mx.com.villavicencio.system.credito.datos.dao.sql.view;

import mx.com.villavicencio.generics.spring.dao.sql.SqlSentence;
import mx.com.villavicencio.generics.types.GenericTypes;
import mx.com.villavicencio.system.credito.datos.dao.sql.sql.SqlDatosCredito;

/**
 *
 * @author Gabriel J
 */
public class View extends SqlSentence {

    private static final String TBL_DATOS_CREDITO = "vw_datosCredito";

    /* QUERY BUSQUEDA DATOS CREDITO ID CLIENTE */
    public static final String VIEW_DATOS_CREDITO_CLIENTE;

    static {
        StringBuilder qry = new StringBuilder();
        qry.append(SELECT + NEW_LINE + TAB + ALL + NEW_LINE + FROM + NEW_LINE + TAB + TBL_DATOS_CREDITO
                + NEW_LINE + WHERE + NEW_LINE + TAB + SqlDatosCredito.ID_CLIENTES + EQUALS + PARAMETER 
                + NEW_LINE + LIMIT + GenericTypes.ONE + SEMICOLON);
        VIEW_DATOS_CREDITO_CLIENTE = qry.toString();
    }
    /* FIN QUERY BUSQUEDA DATOS CREDITO ID CLIENTE */

    /* QUERY BUSQUEDA DATOS CREDITO ID VENDEDOR */
    public static final String VIEW_DATOS_CREDITO_VENDEDOR;

    static {
        StringBuilder qry = new StringBuilder();
        qry.append(SELECT + NEW_LINE + TAB + ALL + NEW_LINE + FROM + NEW_LINE + TAB + TBL_DATOS_CREDITO
                + NEW_LINE + WHERE + NEW_LINE + TAB + SqlDatosCredito.ID_VENDEDORES + EQUALS + PARAMETER 
                + NEW_LINE + LIMIT + GenericTypes.ONE + SEMICOLON);
        VIEW_DATOS_CREDITO_VENDEDOR = qry.toString();
    }
    /* FIN QUERY BUSQUEDA DATOS CREDITO ID VENDEDOR */
}
