package mx.com.villavicencio.system.vendedor.dao.sql.view;

import mx.com.villavicencio.generics.spring.dao.sql.SqlSentence;
import mx.com.villavicencio.system.vendedor.dao.sql.sql.SqlVendedor;

/**
 *
 * @author Gabriel J
 */
public class View extends SqlSentence {

    private static final String TBL_VENDEDOR = "vw_vendedores";
    private static final String TBL_CODIGO_POSTAL = "codigopostal";

    /* QUERY VENDEDORES */
    public static final String VENDEDORES;

    static {
        StringBuilder qry = new StringBuilder();
        qry.append(SELECT).append(NEW_LINE).append(TAB).append(ALL).append(NEW_LINE)
                .append(FROM).append(NEW_LINE).append(TAB).append(TBL_VENDEDOR).append(SEMICOLON);
        VENDEDORES = qry.toString();
    }
    /* FIN QUERY VENDEDORES */

    /* QUERY CP */
    public static final String CP;
  
    static {
        StringBuilder qry = new StringBuilder();
        qry.append(SELECT).append(NEW_LINE).append(TAB).append(ALL).append(NEW_LINE)
                .append(FROM).append(NEW_LINE).append(TAB).append(TBL_CODIGO_POSTAL).append(NEW_LINE)
                .append(WHERE).append(NEW_LINE).append(TAB).append(SqlVendedor.CP)
                .append(LIKE).append(PARAMETER)
                .append(NEW_LINE)
                .append(AND).append(NEW_LINE).append(TAB).append(SqlVendedor.COLONIA)
                .append(LIKE).append(PARAMETER)
                .append(NEW_LINE)
                .append(AND).append(NEW_LINE).append(TAB).append(SqlVendedor.DELEGAGION)
                .append(LIKE).append(PARAMETER)
                .append(NEW_LINE)
                .append(AND).append(NEW_LINE).append(TAB).append(SqlVendedor.MUNICIPIO)
                .append(LIKE).append(PARAMETER)
                .append(NEW_LINE)
                .append(AND).append(NEW_LINE).append(TAB).append(SqlVendedor.ESTADO)
                .append(LIKE).append(PARAMETER)
                .append(SEMICOLON);
        CP = qry.toString();
    }
    /* FIN QUERY CP */

    /* QUERY CPS */
    public static final String CPS;

    static {
        StringBuilder qry = new StringBuilder();
        qry.append(SELECT).append(NEW_LINE).append(TAB).append(ALL).append(NEW_LINE)
                .append(FROM).append(NEW_LINE).append(TAB).append(TBL_CODIGO_POSTAL).append(SEMICOLON);
        CPS = qry.toString();
    }
    /* FIN QUERY CPS */

    /* QUERY VENDEDOR */
    public static final String VENDEDOR;

    static {
        StringBuilder qry = new StringBuilder();
        qry.append(SELECT).append(NEW_LINE).append(TAB).append(ALL).append(NEW_LINE)
                .append(FROM).append(NEW_LINE).append(TAB).append(TBL_VENDEDOR).append(NEW_LINE)
                .append(WHERE).append(NEW_LINE).append(TAB).append(SqlVendedor.ID_VENDEDOR)
                .append(EQUALS).append(PARAMETER).append(SEMICOLON);
        VENDEDOR = qry.toString();
    }
    /* FIN QUERY VENDEDOR */
}
