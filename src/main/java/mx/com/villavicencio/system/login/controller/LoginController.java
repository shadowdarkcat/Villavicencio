package mx.com.villavicencio.system.login.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import mx.com.villavicencio.commons.exception.ApplicationException;
import mx.com.villavicencio.commons.exception.MessageException;
import mx.com.villavicencio.commons.messages.ApplicationMessages;
import mx.com.villavicencio.commons.response.Response;
import mx.com.villavicencio.generics.content.Content;
import mx.com.villavicencio.generics.dispatcher.Dispatcher;
import mx.com.villavicencio.generics.service.Service;
import mx.com.villavicencio.generics.text.Text;
import mx.com.villavicencio.generics.variables.Variables;
import mx.com.villavicencio.properties.PropertiesBean;
import mx.com.villavicencio.properties.Property;
import mx.com.villavicencio.security.SecurityUtils;
import mx.com.villavicencio.system.cliente.bo.ClienteBo;
import mx.com.villavicencio.system.usuario.bo.UsuarioBo;
import mx.com.villavicencio.system.usuario.dto.DtoUsuario;
import mx.com.villavicencio.system.usuario.factory.UsuarioFactory;
import mx.com.villavicencio.system.vendedor.bo.VendedorBo;
import mx.com.villavicencio.system.vendedor.dto.DtoVendedor;
import mx.com.villavicencio.utils.NumberUtils;
import org.apache.log4j.MDC;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 *
 * @author Gabriel J
 *
 */
public class LoginController extends HttpServlet {

    private static final int INIT_LOGIN = 0;
    private static final int VALIDATE_LOGIN = 1;
    private static final int REDIRECT_USER = 2;
    private static final int LOGOUT = 3;

    private RequestDispatcher dispatcher;
    private UsuarioBo usersBo;
    private ClienteBo clienteBo;
    private VendedorBo vendedorBo;

    @Override
    public void init() throws ServletException {
        super.init();
        WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
        usersBo = (UsuarioBo) context.getBean(Service.USER_SERVICE);
        clienteBo = (ClienteBo) context.getBean(Service.CLIENTE_SERVICE);
        vendedorBo = (VendedorBo) context.getBean(Service.VENDEDOR_SERVICE);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            response = Response.getHttpServletResponse(response);
            request.setCharacterEncoding(Content.UTF8);
            if (SecurityUtils.isValidUrl(request)) {
                method(NumberUtils.stringToNumber(request.getParameter(Content.METHOD)), request, response);
            } else {
                ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            }
        } catch (ApplicationException | IOException | NumberFormatException | ServletException ex) {
            dispatcher = getServletContext().getRequestDispatcher(Dispatcher.HANDLER_EXCEPTION);
            request.setAttribute(Variables.EXCEPTION, ex);
            dispatcher.forward(request, response);
        }
    }

    private void method(Integer method, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            switch (method) {
                case INIT_LOGIN:
                    dispatcher = getServletContext().getRequestDispatcher(Dispatcher.LOGIN);
                    dispatcher.forward(request, response);
                    break;
                case VALIDATE_LOGIN:
                    DtoUsuario validUser = usersBo.validar(getUserFromRequest(request));
                    if ((validUser != null) &&(!"ALMACEN".equals(validUser.getPuesto()))) {
                        dispatcher = getServletContext().getRequestDispatcher(Dispatcher.PRINCIPAL);
                        request.getSession().setAttribute(Variables.MENU_USUARIO, usersBo.getMenu(validUser));
                        validUser.setClientes(clienteBo.findByIdVendedor(validUser));
                        Collection<DtoVendedor> findAll = vendedorBo.findAll(validUser);
                        if (validUser.getIsAdmin()) {
                            validUser.setVendedores(findAll);
                        } else {
                            validUser.setVendedores(find(findAll,validUser));
                            validUser.setIsExterno(findExterno(findAll, validUser));
                        }
                        request.getSession().setAttribute(Variables.USER, validUser);
                        MDC.put(Variables.USER_ID, validUser.getIdUsuario());
                        ApplicationMessages.infoMessage(PropertiesBean.getInfoFile().getProperty(Property.INFO_SESSION));
                        dispatcher.forward(request, response);
                    } else {
                        response.sendRedirect(request.getContextPath() + Dispatcher.LOGIN_CONTROLLER);
                    }
                    break;
                case REDIRECT_USER:
                    request.setAttribute(Variables.LEYENDA, PropertiesBean.getErrorFile().getProperty(Property.ERROR_LOGIN));
                    dispatcher.forward(request, response);
                    break;
                case LOGOUT:
                    dispatcher = getServletContext().getRequestDispatcher(Dispatcher.LOGIN);
                    HttpSession session = request.getSession(false);
                    if (session != null && session.getAttribute(Variables.USER) != null) {
                        DtoUsuario user = (DtoUsuario) session.getAttribute(Variables.USER);
                        MDC.put(Variables.USER_ID, user.getIdUsuario());
                        ApplicationMessages.infoMessage(PropertiesBean.getInfoFile().getProperty(Property.INFO_SESSION_TERMINADA));
                        session.invalidate();
                        dispatcher.forward(request, response);
                    }
                    break;
                default:
                    Exception ex = MessageException.messageException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO), PropertiesBean.getErrorFile().getProperty(Property.OPCION_INVALIDA));
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
        }
    }

    private DtoUsuario getUserFromRequest(HttpServletRequest request) {
        DtoUsuario user = UsuarioFactory.newInstance();
        user.setNombreUsuario(request.getParameter(Text.USER).toUpperCase());
        user.setPassword(request.getParameter(Text.PASSWORD).toUpperCase());
        return user;
    }

    private Boolean findExterno(Collection<DtoVendedor> findAll, DtoUsuario user) {
        for (DtoVendedor vendedor : findAll) {
            if ((vendedor.getExterno()) && (Objects.equals(user.getIdVendedor(), vendedor.getIdVendedor()))) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    private Collection<DtoVendedor> find(Collection<DtoVendedor> findAll, DtoUsuario validUser) {
        Collection<DtoVendedor> collection = new ArrayList<>();
        for (DtoVendedor vendedores : findAll) {
            if ((vendedores.getExterno()) && (Objects.equals(vendedores.getIdVendedor(), validUser.getIdVendedor()))) {
                collection.add(vendedores);
                break;
            }
        }
        return collection;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
