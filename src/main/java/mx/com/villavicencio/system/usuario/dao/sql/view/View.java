package mx.com.villavicencio.system.usuario.dao.sql.view;

import mx.com.villavicencio.commons.messages.ApplicationMessages;
import mx.com.villavicencio.generics.spring.dao.sql.SqlSentence;
import mx.com.villavicencio.system.usuario.dao.sql.sql.SqlUsuario;

/**
 *
 * @author Gabriel J
 */
public class View extends SqlSentence {

    private static final String VIEW_USUARIO = "vw_usuario";

    /* QUERY USUARIOS */
    public static final String USUARIOS;

    static {
        StringBuilder qry = new StringBuilder();
        qry.append(SELECT).append(NEW_LINE).append(TAB).append(ALL).append(NEW_LINE)
                .append(FROM).append(NEW_LINE).append(TAB).append(VIEW_USUARIO).append(SEMICOLON);
        USUARIOS = qry.toString();
    }
    /* FIN QUERY USUARIOS */

    /* QUERY USUARIO */
    public static final String USUARIO;

    static {
        StringBuilder qry = new StringBuilder();
        qry.append(SELECT).append(NEW_LINE).append(TAB).append(ALL).append(NEW_LINE)
                .append(FROM).append(NEW_LINE).append(TAB).append(VIEW_USUARIO).append(NEW_LINE)
                .append(WHERE).append(NEW_LINE).append(TAB).append(SqlUsuario.NOMBRE_USUARIO)
                .append(LIKE).append(PARAMETER).append(NEW_LINE).append(AND).append(NEW_LINE)
                .append(TAB).append(SqlUsuario.PASSWORD).append(LIKE).append(PARAMETER).append(SEMICOLON);
        USUARIO = qry.toString();
    }
    /* FIN QUERY USUARIO */
    
    /* QUERY USUARIO BY ID */
    public static final String USUARIO_BY_ID;

    static {
        StringBuilder qry = new StringBuilder();
        qry.append(SELECT).append(NEW_LINE).append(TAB).append(ALL).append(NEW_LINE)
                .append(FROM).append(NEW_LINE).append(TAB).append(VIEW_USUARIO).append(NEW_LINE)
                .append(WHERE).append(NEW_LINE).append(TAB).append(SqlUsuario.ID_USUARIO)
                .append(EQUALS).append(PARAMETER).append(SEMICOLON);
        USUARIO_BY_ID = qry.toString();
    }
    /* FIN QUERY USUARIO BY ID */
    public static void main(String [] args){
        ApplicationMessages.infoMessage(USUARIO);
    }
}
