package mx.com.villavicencio.system.usuario.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mx.com.villavicencio.commons.exception.ApplicationException;
import mx.com.villavicencio.commons.exception.MessageException;
import mx.com.villavicencio.commons.messages.ApplicationMessages;
import mx.com.villavicencio.commons.response.Response;
import mx.com.villavicencio.generics.content.Content;
import mx.com.villavicencio.generics.dispatcher.Dispatcher;
import mx.com.villavicencio.generics.formats.Formats;
import mx.com.villavicencio.generics.print.ClosePrint;
import mx.com.villavicencio.generics.service.Service;
import mx.com.villavicencio.generics.text.Text;
import mx.com.villavicencio.generics.types.GenericTypes;
import mx.com.villavicencio.generics.variables.Variables;
import mx.com.villavicencio.properties.PropertiesBean;
import mx.com.villavicencio.properties.Property;
import mx.com.villavicencio.security.SecurityUtils;
import mx.com.villavicencio.system.menu.bo.MenuBo;
import mx.com.villavicencio.system.menu.dto.MenuItem;
import mx.com.villavicencio.system.puestos.bo.PuestosBo;
import mx.com.villavicencio.system.usuario.bo.UsuarioBo;
import mx.com.villavicencio.system.usuario.dto.DtoUsuario;
import mx.com.villavicencio.system.usuario.factory.UsuarioFactory;
import mx.com.villavicencio.system.vendedor.bo.VendedorBo;
import mx.com.villavicencio.utils.NumberUtils;
import mx.com.villavicencio.utils.StringUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 *
 * @author Gabriel J
 *
 */
public class UsuariosController extends HttpServlet {

    private PuestosBo puestosBo;
    private MenuBo menuBo;
    private UsuarioBo userBo;

    private Gson gson;

    private DtoUsuario user;
    private VendedorBo vendedorBo;

    private RequestDispatcher dispatcher;

    private static final int INIT_USUARIOS = 0;
    private static final int ALTA_USUARIOS = 1;
    private static final int DELETE_USUARIOS = 2;
    private static final int MODIFY_USUARIOS = 3;
    private static final int ACTIVATE_USUARIO = 4;
    private static final int GET_JSON_USUARIOS = 5;
    private static final int MODIFY_PRIVILEGIOS = 6;
    private static final int GET_JSON_PRIVILEGIOS = 7;

    @Override
    public void init() throws ServletException {
        super.init();
        WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
        puestosBo = (PuestosBo) context.getBean(Service.PUESTOS_SERVICE);
        menuBo = (MenuBo) context.getBean(Service.MENU_SERVICE);
        userBo = (UsuarioBo) context.getBean(Service.USER_SERVICE);
        vendedorBo = (VendedorBo) context.getBean(Service.VENDEDOR_SERVICE);
        gson = new GsonBuilder().setDateFormat(Formats.DATE_FORMAT).create();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        user = (DtoUsuario) request.getSession(false).getAttribute(Variables.USER);
        try {
            response = Response.getHttpServletResponse(response);
            request.setCharacterEncoding(Content.UTF8);

            if (SecurityUtils.isValidUrl(request) && SecurityUtils.isValidSession(request)) {
                method(NumberUtils.stringToNumber(request.getParameter(Content.METHOD)), request, response);
            } else {
                throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            }
        } catch (ApplicationException | IOException | NumberFormatException | ServletException ex) {
            dispatcher = getServletContext().getRequestDispatcher(Dispatcher.HANDLER_EXCEPTION);
            request.setAttribute(Variables.EXCEPTION, ex);
            dispatcher.forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void method(Integer method, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DtoUsuario usuario;
        PrintWriter out = null;
        try {
            switch (method) {
                case INIT_USUARIOS:
                    dispatcher = getServletContext().getRequestDispatcher(Dispatcher.USUARIOS);
                    request.setAttribute(Variables.PUESTOS, this.puestosBo.findAll(user));
                    request.setAttribute(Variables.MENU_STRING, this.menuBo.getConvercionMenu(user));
                    request.setAttribute(Variables.USUARIOS, this.userBo.findAll(user));
                    request.setAttribute(Variables.VENDEDORES, this.vendedorBo.findAll(user));
                    request.setAttribute(Variables.MODIFICAR_PRIVILEGIO, false);
                    request.setAttribute(Variables.ID, request.getParameter(Variables.ID));
                    dispatcher.forward(request, response);
                    break;
                case ALTA_USUARIOS:
                    dispatcher = getServletContext().getRequestDispatcher(Dispatcher.USUARIOS);
                    usuario = getFromRequest(request);
                    usuario.setOpcion(GenericTypes.INSERT);
                    Boolean verifyExist = this.userBo.verifyExistUsuario(user, usuario);
                    if (!verifyExist) {
                        usuario = this.userBo.insert(user, usuario);
                        this.menuBo.insertMultiplesPrivilegios(user, this.getPropiedadesDelMenu(request), usuario);
                    } else {
                        request.setAttribute(Variables.EXIST, verifyExist);
                    }
                    request.setAttribute(Variables.PUESTOS, this.puestosBo.findAll(user));
                    request.setAttribute(Variables.MENU_STRING, this.menuBo.getConvercionMenu(user));
                    request.setAttribute(Variables.USUARIOS, this.userBo.findAll(user));
                    request.setAttribute(Variables.VENDEDORES, this.vendedorBo.findAll(user));
                    request.setAttribute(Variables.MODIFICAR_PRIVILEGIO, false);
                    request.setAttribute(Variables.ID, request.getParameter(Text.ID_ACTION));
                    dispatcher.forward(request, response);
                    break;
                case DELETE_USUARIOS:
                    ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
                    break;
                case MODIFY_USUARIOS:
                    dispatcher = getServletContext().getRequestDispatcher(Dispatcher.USUARIOS);
                    usuario = getFromRequest(request);
                    usuario.setOpcion(GenericTypes.MODIFY);
                    this.userBo.modificar(user, usuario);
                    request.setAttribute(Variables.PUESTOS, this.puestosBo.findAll(user));
                    request.setAttribute(Variables.MENU_STRING, this.menuBo.getConvercionMenu(user));
                    request.setAttribute(Variables.USUARIOS, this.userBo.findAll(user));
                    request.setAttribute(Variables.VENDEDORES, this.vendedorBo.findAll(user));
                    request.setAttribute(Variables.MODIFICAR_PRIVILEGIO, false);
                    request.setAttribute(Variables.ID, request.getParameter(Text.ID_ACTION));
                    dispatcher.forward(request, response);
                    break;
                case ACTIVATE_USUARIO:
                    Exception ex = MessageException.messageException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO), PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
                    dispatcher = getServletContext().getRequestDispatcher(Dispatcher.HANDLER_EXCEPTION);
                    ApplicationMessages.errorMessage(ex.getMessage(), ex.getCause());
                    request.setAttribute(Variables.EXCEPTION, ex);
                    dispatcher.forward(request, response);
                    break;
                case GET_JSON_USUARIOS:
                    usuario = getFromRequest(request);
                    DtoUsuario findById = this.userBo.findById(user, usuario);
                    out = response.getWriter();
                    out.print(gson.toJson(findById));
                    break;
                case MODIFY_PRIVILEGIOS:
                    dispatcher = getServletContext().getRequestDispatcher(Dispatcher.USUARIOS);
                    usuario = getFromRequest(request);
                    usuario.setOpcion(GenericTypes.MODIFY);
                    this.menuBo.insertMultiplesPrivilegios(user, this.getPropiedadesDelMenu(request), usuario);
                    request.setAttribute(Variables.PUESTOS, this.puestosBo.findAll(user));
                    request.setAttribute(Variables.MENU_STRING, this.menuBo.getConvercionMenu(user));
                    request.setAttribute(Variables.USUARIOS, this.userBo.findAll(user));
                    request.setAttribute(Variables.VENDEDORES, this.vendedorBo.findAll(user));
                    request.setAttribute(Variables.MODIFICAR_PRIVILEGIO, false);
                    request.setAttribute(Variables.ID, request.getParameter(Text.ID_ACTION));
                    dispatcher.forward(request, response);
                    break;
                case GET_JSON_PRIVILEGIOS:
                    dispatcher = getServletContext().getRequestDispatcher(Dispatcher.USUARIOS);
                    usuario = getFromRequest(request);
                    request.setAttribute(Variables.MENU_STRING, this.menuBo.getConvercionMenu(usuario));
                    request.setAttribute(Variables.MODIFICAR_PRIVILEGIO, true);
                    request.setAttribute(Variables.USUARIO, this.userBo.findById(user, usuario));
                    request.setAttribute(Variables.USUARIOS, this.userBo.findAll(user));
                    request.setAttribute(Variables.ID, request.getParameter(Text.ID_ACTION));
                    dispatcher.forward(request, response);
                    break;
                default:
                    ex = MessageException.messageException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO), PropertiesBean.getErrorFile().getProperty(Property.OPCION_INVALIDA));
                    dispatcher = getServletContext().getRequestDispatcher(Dispatcher.HANDLER_EXCEPTION);
                    ApplicationMessages.errorMessage(ex.getMessage(), ex.getCause());
                    request.setAttribute(Variables.EXCEPTION, ex);
                    dispatcher.forward(request, response);
            }
        } catch (ApplicationException | IOException | ServletException ex) {
            dispatcher = getServletContext().getRequestDispatcher(Dispatcher.HANDLER_EXCEPTION);
            request.setAttribute(Variables.EXCEPTION, ex);
            dispatcher.forward(request, response);
        } catch (Exception ex) {
            dispatcher = getServletContext().getRequestDispatcher(Dispatcher.HANDLER_EXCEPTION);
            request.setAttribute(Variables.EXCEPTION, ex);
            dispatcher.forward(request, response);
        } finally {
            if (out != null) {
                ClosePrint.closePrintWriter(out);
            }
        }
    }

    private Collection<MenuItem> getPropiedadesDelMenu(HttpServletRequest request) {
        String valores[] = request.getParameterValues(Variables.ID_MENU);
        Collection<MenuItem> menu = new ArrayList<>();
        MenuItem menuItem = null;
        if (valores != null) {
            for (int i = 0; i < valores.length; i++) {
                menuItem = new MenuItem();
                menuItem.setId(Integer.parseInt(valores[i]));
                menu.add(menuItem);
            }
        }
        return menu;
    }

    private DtoUsuario getFromRequest(HttpServletRequest request) {
        DtoUsuario users = UsuarioFactory.newInstance();

        if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.ID_USUARIO))) {
            users.setIdUsuario(NumberUtils.stringToNumber(request.getParameter(Text.ID_USUARIO)));
        }
        
        if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.CBO_TEXT_NAME))) {
            users.setNombreCompleto(request.getParameter(Text.CBO_TEXT_NAME).toUpperCase().trim());
        }

        if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.NOMBRE))) {
            users.setNombreCompleto(request.getParameter(Text.NOMBRE).toUpperCase());
        }

        if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.NOMBRE_USUARIO))) {
            users.setNombreUsuario(request.getParameter(Text.NOMBRE_USUARIO).toUpperCase());
        }

        if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.TELEFONO_1))) {
            users.setNoTelefono(request.getParameter(Text.TELEFONO_1));
        }

        if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.PASSWORD_USUARIO))) {
            users.setPassword(request.getParameter(Text.PASSWORD_USUARIO).toUpperCase());
        }

        if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.CBO_VENDEDOR))) {
            if (NumberUtils.stringToNumber(request.getParameter(Text.CBO_VENDEDOR)) != 0) {
                users.setIdVendedor(NumberUtils.stringToNumber(request.getParameter(Text.CBO_VENDEDOR)));
            } else {
                users.setIdVendedor(GenericTypes.ONE);
            }
        } else {
            users.setIdVendedor(GenericTypes.ONE);
        }
        if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.CBO_PUESTO))) {
            users.setPuesto(request.getParameter(Text.CBO_PUESTO));
        }

        if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.PUESTO))) {
            users.setPuesto(request.getParameter(Text.PUESTO));
        }

        if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.PUESTO_RESPONSE))) {
            users.setPuesto(request.getParameter(Text.PUESTO_RESPONSE));
        }
        return users;
    }
}
