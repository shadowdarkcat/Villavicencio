package mx.com.villavicencio.system.menu.dao.sql.view;

import mx.com.villavicencio.generics.spring.dao.sql.SqlSentence;
import mx.com.villavicencio.system.menu.dao.sql.sql.SqlMenu;
import mx.com.villavicencio.system.usuario.dao.sql.sql.SqlUsuario;

/**
 *
 * @author Gabriel J
 */
public class View extends SqlSentence {

    private static final String VIEW_MENU = "vw_menu";
    private static final String VIEW_PRIVILEGIOS = "vw_menuprivilegio";
    private static final String VIEW_MENU_ALMACEN = "vw_menualmacen";
    private static final String VIEW_PRIVILEGIOS_ALMACEN = "vw_menuprivilegioalmacen";

    /* QUERY MENU */
    public static final String MENU;

    static {
        StringBuilder qry = new StringBuilder();
        qry.append(SELECT).append(NEW_LINE).append(TAB).append(ALL).append(NEW_LINE)
                .append(FROM).append(NEW_LINE).append(TAB).append(VIEW_MENU).append(NEW_LINE)
                .append(WHERE).append(NEW_LINE).append(TAB).append(SqlMenu.ID_PADRE)
                .append(EQUALS).append(PARAMETER).append(SEMICOLON);
        MENU = qry.toString();
    }
    /* FIN QUERY MENU */

    /* QUERY MENU PRIVILEGIOS */
    public static final String PRIVILEGIOS;

    static {
        StringBuilder qry = new StringBuilder();
        qry.append(SELECT).append(NEW_LINE).append(TAB).append(ALL).append(NEW_LINE)
                .append(FROM).append(NEW_LINE).append(TAB).append(VIEW_PRIVILEGIOS).append(NEW_LINE)
                .append(WHERE).append(NEW_LINE).append(TAB).append(SqlUsuario.ID_USUARIO)
                .append(EQUALS).append(PARAMETER).append(SEMICOLON);
        PRIVILEGIOS = qry.toString();
    }
    /* FIN QUERY MENU PRIVILEGIOS */
    
     /* QUERY MENU ALMACEN*/
    public static final String MENU_ALMACEN;

    static {
        StringBuilder qry = new StringBuilder();
        qry.append(SELECT).append(NEW_LINE).append(TAB).append(ALL).append(NEW_LINE)
                .append(FROM).append(NEW_LINE).append(TAB).append(VIEW_MENU_ALMACEN).append(NEW_LINE)
                .append(WHERE).append(NEW_LINE).append(TAB).append(SqlMenu.ID_PADRE)
                .append(EQUALS).append(PARAMETER).append(SEMICOLON);
        MENU_ALMACEN = qry.toString();
    }
    /* FIN QUERY MENU ALMACEN*/

    /* QUERY MENU PRIVILEGIOS ALMACEN*/
    public static final String PRIVILEGIOS_ALMACEN;

    static {
        StringBuilder qry = new StringBuilder();
        qry.append(SELECT).append(NEW_LINE).append(TAB).append(ALL).append(NEW_LINE)
                .append(FROM).append(NEW_LINE).append(TAB).append(VIEW_PRIVILEGIOS_ALMACEN).append(NEW_LINE)
                .append(WHERE).append(NEW_LINE).append(TAB).append(SqlUsuario.ID_USUARIO)
                .append(EQUALS).append(PARAMETER).append(SEMICOLON);
        PRIVILEGIOS_ALMACEN = qry.toString();
    }
    /* FIN QUERY MENU PRIVILEGIOS ALMACEN*/
}
