package mx.com.villavicencio.system.productos.productos.dao.sql.view;

import mx.com.villavicencio.generics.spring.dao.sql.SqlSentence;
import mx.com.villavicencio.system.cliente.dao.sql.sql.SqlCliente;
import mx.com.villavicencio.system.productos.productos.dao.sql.sql.SqlProducto;
import mx.com.villavicencio.system.vendedor.dao.sql.sql.SqlVendedor;

/**
 *
 * @author Gabriel J
 */
public class View extends SqlSentence {

    private static final String VW_PRODUCTO = "vw_producto";
    private static final String VW_DATOS_ESTABLECIDOS = "vw_productoEstablecidoVendedor";
    private static final String VW_DATOS_PERSONALIZADOS = "vw_productopersonalizadovendedor";
    private static final String VW_DATOS_CLIENTES_ESTABLECIDOS = "vw_productoEstablecidoCliente";
    private static final String VW_DATOS_CLIENTES_PERSONALIZADOS = "vw_productopersonalizadoCliente";

    /* QUERY PRODUCTOS */
    public static final String PRODUCTOS;

    static {
        StringBuilder qry = new StringBuilder();
        qry.append(SELECT).append(NEW_LINE).append(TAB).append(ALL).append(NEW_LINE)
                .append(FROM).append(NEW_LINE).append(TAB).append(VW_PRODUCTO).append(SEMICOLON);
        PRODUCTOS = qry.toString();
    }
    /* FIN QUERY PRODUCTOS */

    /* QUERY PRODUCTO */
    public static final String PRODUCTO;

    static {
        StringBuilder qry = new StringBuilder();
        qry.append(SELECT).append(NEW_LINE).append(TAB).append(ALL).append(NEW_LINE)
                .append(FROM).append(NEW_LINE).append(TAB).append(VW_PRODUCTO).append(NEW_LINE)
                .append(WHERE).append(NEW_LINE).append(TAB).append(SqlProducto.ID_PRODUCTOS)
                .append(EQUALS).append(PARAMETER).append(SEMICOLON);
        PRODUCTO = qry.toString();
    }
    /* FIN QUERY PRODUCTO */

    /* QUERY PRODUCTOS VENDEDOR ESTABLECIDO */
    public static final String DATOS_PRODUCTOS_VENDEDOR_ESTABLECIDO;

    static {
        StringBuilder qry = new StringBuilder();
        qry.append(SELECT).append(NEW_LINE).append(TAB).append(ALL).append(NEW_LINE)
                .append(FROM).append(NEW_LINE).append(TAB).append(VW_DATOS_ESTABLECIDOS)
                .append(NEW_LINE).append(WHERE).append(NEW_LINE).append(TAB)
                .append(SqlVendedor.ID_VENDEDOR).append(EQUALS).append(PARAMETER).append(SEMICOLON);
        DATOS_PRODUCTOS_VENDEDOR_ESTABLECIDO = qry.toString();
    }
    /* FIN QUERY PRODUCTOS VENDEDOR ESTABLECIDO*/

    /* QUERY PRODUCTOS VENDEDOR PERSONALIZADOS */
    public static final String DATOS_PRODUCTOS_VENDEDOR_PERSONALIZADO;

    static {
        StringBuilder qry = new StringBuilder();
        qry.append(SELECT).append(NEW_LINE).append(TAB).append(ALL).append(NEW_LINE)
                .append(FROM).append(NEW_LINE).append(TAB).append(VW_DATOS_PERSONALIZADOS)
                .append(NEW_LINE).append(WHERE).append(NEW_LINE).append(TAB)
                .append(SqlVendedor.ID_VENDEDOR).append(EQUALS).append(PARAMETER)
                .append(NEW_LINE).append(AND).append(NEW_LINE).append(TAB)
                .append(SqlProducto.ID_PRODUCTOS_PERSONALIZADOS).append(EQUALS).append(PARAMETER)
                .append(SEMICOLON);
        DATOS_PRODUCTOS_VENDEDOR_PERSONALIZADO = qry.toString();
    }
    /* FIN QUERY PRODUCTOS VENDEDOR PERSONALIZADOS*/

    /* QUERY PRODUCTOS VENDEDOR PERSONALIZADOS */
    public static final String DATOS_PRODUCTOS_VENDEDOR_PERSONALIZADOS;

    static {
        StringBuilder qry = new StringBuilder();
        qry.append(SELECT).append(NEW_LINE).append(TAB).append(ALL).append(NEW_LINE)
                .append(FROM).append(NEW_LINE).append(TAB).append(VW_DATOS_PERSONALIZADOS)
                .append(NEW_LINE).append(WHERE).append(NEW_LINE).append(TAB)
                .append(SqlVendedor.ID_VENDEDOR).append(EQUALS).append(PARAMETER)
                .append(SEMICOLON);
        DATOS_PRODUCTOS_VENDEDOR_PERSONALIZADOS = qry.toString();
    }
    /* FIN QUERY PRODUCTOS VENDEDOR PERSONALIZADOS*/

    /* QUERY PRODUCTOS CLIENTES ESTABLECIDO */
    public static final String DATOS_PRODUCTOS_CLIENTE_ESTABLECIDO;

    static {
        StringBuilder qry = new StringBuilder();
        qry.append(SELECT).append(NEW_LINE).append(TAB).append(ALL).append(NEW_LINE)
                .append(FROM).append(NEW_LINE).append(TAB).append(VW_DATOS_CLIENTES_ESTABLECIDOS)
                .append(NEW_LINE).append(WHERE).append(NEW_LINE).append(TAB)
                .append(SqlCliente.ID_CLIENTE).append(EQUALS).append(PARAMETER).append(SEMICOLON);
        DATOS_PRODUCTOS_CLIENTE_ESTABLECIDO = qry.toString();
    }
    /* FIN QUERY PRODUCTOS CLIENTES ESTABLECIDO*/

    /* QUERY PRODUCTOS CLIENTES PERSONALIZADOS */
    public static final String DATOS_PRODUCTOS_CLIENTE_PERSONALIZADO;

    static {
        StringBuilder qry = new StringBuilder();
        qry.append(SELECT).append(NEW_LINE).append(TAB).append(ALL).append(NEW_LINE)
                .append(FROM).append(NEW_LINE).append(TAB).append(VW_DATOS_CLIENTES_PERSONALIZADOS)
                .append(NEW_LINE).append(WHERE).append(NEW_LINE).append(TAB)
                .append(SqlCliente.ID_CLIENTE).append(EQUALS).append(PARAMETER)                
                .append(SEMICOLON);
        DATOS_PRODUCTOS_CLIENTE_PERSONALIZADO = qry.toString();
    }
    /* FIN QUERY PRODUCTOS CLIENTES PERSONALIZADOS*/

    /* QUERY PRODUCTOS CLIENTES PERSONALIZADOS */
    public static final String DATOS_PRODUCTOS_CLIENTES_PERSONALIZADOS;

    static {
        StringBuilder qry = new StringBuilder();
        qry.append(SELECT).append(NEW_LINE).append(TAB).append(ALL).append(NEW_LINE)
                .append(FROM).append(NEW_LINE).append(TAB).append(VW_DATOS_CLIENTES_PERSONALIZADOS)
                .append(NEW_LINE).append(WHERE).append(NEW_LINE).append(TAB)
                .append(SqlCliente.ID_CLIENTE).append(EQUALS).append(PARAMETER)
                .append(NEW_LINE).append(AND).append(NEW_LINE).append(TAB)
                .append(SqlProducto.ID_PRODUCTOS_PERSONALIZADOS).append(EQUALS).append(PARAMETER)
                .append(SEMICOLON);
        DATOS_PRODUCTOS_CLIENTES_PERSONALIZADOS = qry.toString();
    }
    /* FIN QUERY PRODUCTOS CLIENTES PERSONALIZADOS*/

    public static void main(String[] args) {
        System.out.println(DATOS_PRODUCTOS_CLIENTE_ESTABLECIDO);
    }
}
