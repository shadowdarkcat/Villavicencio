package mx.com.villavicencio.system.credito.credito.dao.sql.view;

import mx.com.villavicencio.generics.spring.dao.sql.SqlSentence;
import mx.com.villavicencio.system.cliente.dao.sql.sql.SqlCliente;
import mx.com.villavicencio.system.credito.credito.dao.sql.sql.SqlCredito;
import mx.com.villavicencio.system.vendedor.dao.sql.sql.SqlVendedor;

/**
 *
 * @author Gabriel J
 */
public class View extends SqlSentence {

    private static final String TBL_CREDITO = "vw_credito";
    private static final String TBL_DATOS_CREDITO = "vw_datoscredito";

    /*QUERY CREDITO */
    public static final String CREDITO;

    static {
        StringBuilder qry = new StringBuilder();
        qry.append(SELECT).append(NEW_LINE).append(TAB).append(ALL).append(NEW_LINE)
                .append(FROM).append(NEW_LINE).append(TAB).append(TBL_CREDITO)
                .append(NEW_LINE).append(WHERE).append(NEW_LINE).append(TAB)
                .append(SqlCredito.ID_CREDITO).append(EQUALS).append(PARAMETER)
                .append(SEMICOLON);
        CREDITO = qry.toString();
    }
    /* FIN QUERY CREDITO */

    /*QUERY DATOS CREDITO */
    public static final String DATOS_CREDITO;

    static {
        StringBuilder qry = new StringBuilder();
        qry.append(SELECT).append(NEW_LINE).append(TAB).append(ALL).append(NEW_LINE)
                .append(FROM).append(NEW_LINE).append(TAB).append(TBL_DATOS_CREDITO)
                .append(NEW_LINE).append(WHERE).append(NEW_LINE).append(TAB)
                .append(SqlVendedor.ID_VENDEDOR).append(EQUALS).append(PARAMETER)
                .append(SEMICOLON);
        DATOS_CREDITO = qry.toString();
    }
    /* FIN QUERY DATOS CREDITO */
    
    /*QUERY DATOS CREDITO CLIENTE */
    public static final String DATOS_CREDITO_CLIENTE;

    static {
        StringBuilder qry = new StringBuilder();
        qry.append(SELECT).append(NEW_LINE).append(TAB).append(ALL).append(NEW_LINE)
                .append(FROM).append(NEW_LINE).append(TAB).append(TBL_DATOS_CREDITO)
                .append(NEW_LINE).append(WHERE).append(NEW_LINE).append(TAB)
                .append(SqlCliente.ID_CLIENTE).append(EQUALS).append(PARAMETER)
                .append(SEMICOLON);
        DATOS_CREDITO_CLIENTE = qry.toString();
    }
    /* FIN QUERY DATOS CREDITO */
}
