package mx.com.villavicencio.system.pedido.datos.dao.sql.view;

import mx.com.villavicencio.generics.spring.dao.sql.SqlSentence;
import mx.com.villavicencio.generics.types.GenericTypes;
import mx.com.villavicencio.system.cliente.dao.sql.sql.SqlCliente;
import mx.com.villavicencio.system.pedido.pedido.dao.sql.sql.SqlPedido;
import mx.com.villavicencio.system.vendedor.dao.sql.sql.SqlVendedor;

/**
 *
 * @author Gabriel J
 */
public class View extends SqlSentence {

    private static final String TBL_DATOS_PEDIDO = "vw_datospedido";

    /* QUERY DATOS PEDIDO */
    public static final String PEDIDOS;

    static {
        StringBuilder qry = new StringBuilder();
        qry.append(SELECT).append(NEW_LINE).append(TAB).append(ALL).append(NEW_LINE)
                .append(FROM).append(NEW_LINE).append(TAB).append(TBL_DATOS_PEDIDO).append(SEMICOLON);
        PEDIDOS = qry.toString();
    }
    /* FIN QUERY DATOS PEDIDO */

    /* QUERY DATOS PEDIDO CLIENTE */
    public static final String PEDIDOS_CLIENTE;

    static {
        StringBuilder qry = new StringBuilder();
        qry.append(SELECT).append(NEW_LINE).append(TAB).append(ALL).append(NEW_LINE)
                .append(FROM).append(NEW_LINE).append(TAB).append(TBL_DATOS_PEDIDO)
                .append(NEW_LINE).append(WHERE).append(NEW_LINE).append(TAB)
                .append(SqlCliente.ID_CLIENTE).append(EQUALS).append(PARAMETER).append(NEW_LINE)
                .append(AND).append(NEW_LINE).append(TAB)
                .append(SqlPedido.ID_PEDIDO).append(EQUALS).append(PARAMETER).append(NEW_LINE)
                .append(AND).append(NEW_LINE).append(TAB).append(SqlVendedor.ID_VENDEDOR)
                .append(NULL).append(SEMICOLON);
        PEDIDOS_CLIENTE = qry.toString();
    }
    /* FIN QUERY DATOS PEDIDO CLIENTE */

    /* QUERY DATOS PEDIDO VENDEDOR */
    public static final String PEDIDOS_VENDEDOR;

    static {
        StringBuilder qry = new StringBuilder();
        qry.append(SELECT).append(NEW_LINE).append(TAB).append(ALL).append(NEW_LINE)
                .append(FROM).append(NEW_LINE).append(TAB).append(TBL_DATOS_PEDIDO)
                .append(NEW_LINE).append(WHERE).append(NEW_LINE).append(TAB)
                .append(SqlVendedor.ID_VENDEDOR).append(EQUALS).append(PARAMETER).append(NEW_LINE)
                .append(AND).append(NEW_LINE).append(TAB)
                .append(SqlPedido.ID_PEDIDO).append(EQUALS).append(PARAMETER).append(NEW_LINE)
                .append(AND).append(NEW_LINE).append(TAB).append(SqlCliente.ID_CLIENTE)
                .append(NULL).append(SEMICOLON);
        PEDIDOS_VENDEDOR = qry.toString();
    }
    /* FIN QUERY DATOS PEDIDO VENDEDOR */

    /* QUERY DATOS PEDIDO SIN ID PEDIDO PARA CLIENTE */
    public static final String PEDIDOS_WHITHOUT_PEDIDO_CLIENTE;

    static {
        StringBuilder qry = new StringBuilder();
        qry.append(SELECT).append(NEW_LINE).append(TAB).append(ALL).append(NEW_LINE)
                .append(FROM).append(NEW_LINE).append(TAB).append(TBL_DATOS_PEDIDO)
                .append(NEW_LINE).append(WHERE).append(NEW_LINE).append(TAB)
                .append(SqlCliente.ID_CLIENTE).append(EQUALS).append(PARAMETER).append(NEW_LINE)
                .append(AND).append(NEW_LINE).append(TAB).append(SqlVendedor.ID_VENDEDOR)
                .append(NULL).append(SEMICOLON);
        PEDIDOS_WHITHOUT_PEDIDO_CLIENTE = qry.toString();
    }
    /* FIN QUERY DATOS SIN ID PEDIDO */

    /* QUERY DATOS PEDIDO SIN ID PEDIDO PARA VENDEDOR */
    public static final String PEDIDOS_WHITHOUT_PEDIDO_VENDEDOR;

    static {
        StringBuilder qry = new StringBuilder();
        qry.append(SELECT).append(NEW_LINE).append(TAB).append(ALL).append(NEW_LINE)
                .append(FROM).append(NEW_LINE).append(TAB).append(TBL_DATOS_PEDIDO)
                .append(NEW_LINE).append(WHERE).append(NEW_LINE).append(TAB)
                .append(SqlVendedor.ID_VENDEDOR).append(EQUALS).append(PARAMETER).append(NEW_LINE)
                .append(AND).append(NEW_LINE).append(TAB).append(SqlCliente.ID_CLIENTE)
                .append(NULL).append(SEMICOLON);
        PEDIDOS_WHITHOUT_PEDIDO_VENDEDOR = qry.toString();
    }
    /* FIN QUERY DATOS SIN ID PEDIDO */

    /* QUERY DATOS PEDIDO PARA MOSTRAR CLIENTE EN VISTA*/
    public static final String SEARCH_IDS;

    static {
        StringBuilder qry = new StringBuilder();
        qry.append(SELECT).append(NEW_LINE).append(TAB).append(ALL).append(NEW_LINE)
                .append(FROM).append(NEW_LINE).append(TAB).append(TBL_DATOS_PEDIDO).append(NEW_LINE)
                .append(WHERE).append(NEW_LINE).append(TAB).append(SqlPedido.ID_PEDIDO)
                .append(EQUALS).append(PARAMETER).append(NEW_LINE).append(LIMIT)
                .append(GenericTypes.ONE).append(SEMICOLON);
        SEARCH_IDS = qry.toString();
    }
    /* FIN QUERY DATOS PEDIDO PARA MOSTRAR CLIENTE EN VISTA*/

    /* QUERY DATOS PEDIDO PARA MOSTRAR PEDIDO CLIENTE EN VISTA*/
    public static final String SEARCH_ID_CLIENTE;

    static {
        StringBuilder qry = new StringBuilder();
        qry.append(SELECT).append(NEW_LINE).append(TAB).append(ALL).append(NEW_LINE)
                .append(FROM).append(NEW_LINE).append(TAB).append(TBL_DATOS_PEDIDO).append(NEW_LINE)
                .append(WHERE).append(NEW_LINE).append(TAB).append(SqlCliente.ID_CLIENTE)
                .append(EQUALS).append(PARAMETER).append(NEW_LINE).append(LIMIT)
                .append(GenericTypes.ONE).append(SEMICOLON);
        SEARCH_ID_CLIENTE = qry.toString();
    }
    /* FIN QUERY DATOS PEDIDO PARA MOSTRAR PEDIDO CLIENTE EN VISTA*/

    /* QUERY DATOS PEDIDO PARA MOSTRAR PEDIDO VENDEDOR EN VISTA*/
    public static final String SEARCH_ID_VENDEDOR;

    static {
        StringBuilder qry = new StringBuilder();
        qry.append(SELECT).append(NEW_LINE).append(TAB).append(ALL).append(NEW_LINE)
                .append(FROM).append(NEW_LINE).append(TAB).append(TBL_DATOS_PEDIDO).append(NEW_LINE)
                .append(WHERE).append(NEW_LINE).append(TAB).append(SqlVendedor.ID_VENDEDOR)
                .append(EQUALS).append(PARAMETER).append(NEW_LINE).append(LIMIT)
                .append(GenericTypes.ONE).append(SEMICOLON);
        SEARCH_ID_VENDEDOR = qry.toString();
    }
    /* FIN QUERY DATOS PEDIDO PARA MOSTRAR PEDIDO VENDEDOR EN VISTA*/
    
    /* QUERY DATOS PEDIDO SIN ID VENDEDOR Y CLIENTE*/
    public static final String PEDIDOS_WHITHOUT_VENDEDORCLIENTE;

    static {
        StringBuilder qry = new StringBuilder();
        qry.append(SELECT).append(NEW_LINE).append(TAB).append(ALL).append(NEW_LINE)
                .append(FROM).append(NEW_LINE).append(TAB).append(TBL_DATOS_PEDIDO).append(NEW_LINE)
                .append(WHERE).append(NEW_LINE).append(TAB).append(SqlPedido.ID_PEDIDO)
                .append(EQUALS).append(PARAMETER).append(SEMICOLON);
        PEDIDOS_WHITHOUT_VENDEDORCLIENTE = qry.toString();
    }
    /* FIN QUERY DATOS PEDIDO SIN ID VENDEDOR Y CLIENTE*/
}
