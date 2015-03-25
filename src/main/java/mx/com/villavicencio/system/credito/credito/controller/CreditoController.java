package mx.com.villavicencio.system.credito.credito.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.PrintWriter;
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
import mx.com.villavicencio.system.credito.credito.bo.CreditoBo;
import mx.com.villavicencio.system.credito.credito.dto.DtoCredito;
import mx.com.villavicencio.system.credito.credito.factory.CreditoFactory;
import mx.com.villavicencio.system.usuario.dto.DtoUsuario;
import mx.com.villavicencio.utils.DateUtils;
import mx.com.villavicencio.utils.NumberUtils;
import mx.com.villavicencio.utils.StringUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 *
 * @author Gabriel J
 */
public class CreditoController extends HttpServlet {

    private DtoUsuario user;
    private CreditoBo creditoBo;
    private RequestDispatcher dispatcher;
    private Gson gson;

    private static final int INSERT_CREDITO = 1;

    @Override
    public void init() throws ServletException {
        super.init();
        WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
        creditoBo = (CreditoBo) context.getBean(Service.CREDITO_SERVICE);
        gson = new GsonBuilder().setDateFormat(Formats.DATE_FORMAT).create();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        user = Content.getSession(request);
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

    private void method(Integer method, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        DtoCredito credito = null;
        PrintWriter out = null;
        try {
            switch (method) {
                case INSERT_CREDITO:
                    credito = getCreditoFromRequest(request);
                    DtoCredito findBydId = this.creditoBo.findById(user, credito);
                    if((findBydId != null) && (!StringUtils.isReallyEmptyOrNull(findBydId.getFolioNota()))){
                        credito.setFolioNota(credito.getFolioNota() + GenericTypes.SEPARATOR + findBydId.getFolioNota());
                    }
                    credito.setOpcion(GenericTypes.THREE);
                    this.creditoBo.modificar(user, credito);
                    out = response.getWriter();
                    out.print(gson.toJson(Boolean.TRUE));
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
        } finally {
            if (out != null) {
                ClosePrint.closePrintWriter(out);
            }
        }
    }

    private DtoCredito getCreditoFromRequest(HttpServletRequest request) {
        DtoCredito credito = CreditoFactory.newInstance();

        if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.ID_CREDITO))) {
            credito.setIdCredito(NumberUtils.stringToNumber(request.getParameter(Text.ID_CREDITO)));
        }

        if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.TIPO_CREDITO))) {
            credito.setTipoCredito(request.getParameter(Text.TIPO_CREDITO));
        }

        if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.FECHA_REGISTRO))) {
            credito.setFechaRegistro(DateUtils.stringToDate(request.getParameter(Text.FECHA_REGISTRO)));
        }

        if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.FECHA_PAGO))) {
            credito.setFechaPago(DateUtils.stringToDate(request.getParameter(Text.FECHA_PAGO)));
        }

        if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.FOLIO))) {
            credito.setFolioNota(request.getParameter(Text.FOLIO));
        }

        credito.setEstatusCredito(Variables.APLICADO);
        return credito;
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

}
