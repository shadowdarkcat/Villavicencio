package mx.com.villavicencio.system.venta.nota.controller;

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
import mx.com.villavicencio.generics.variables.Variables;
import mx.com.villavicencio.properties.PropertiesBean;
import mx.com.villavicencio.properties.Property;
import mx.com.villavicencio.security.SecurityUtils;
import mx.com.villavicencio.system.usuario.dto.DtoUsuario;
import mx.com.villavicencio.system.venta.nota.bo.NotaVentaBo;
import mx.com.villavicencio.system.venta.nota.dto.DtoNotaVenta;
import mx.com.villavicencio.system.venta.nota.factory.NotaVentaFactory;
import mx.com.villavicencio.system.venta.nota.reporte.factory.ReporteFactory;
import mx.com.villavicencio.utils.NumberUtils;
import mx.com.villavicencio.utils.StringUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 *
 * @author Gabriel J
 */
public class NotaVentaController extends HttpServlet {

    private DtoUsuario user;
    private NotaVentaBo notaVentaBo;
    private String rutaServer;
    private String rutaReporte;
    private Gson json;
    private RequestDispatcher dispatcher;

    private static final int GET_NOTAVENTA = 1;
    private static final int GET_REPORTE_NOTAVENTA = 2;

    @Override
    public void init() throws ServletException {
        super.init();
        WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
        notaVentaBo = (NotaVentaBo) context.getBean(Service.NOTA_VENTA_SERVICE);
        json = new GsonBuilder().setDateFormat(Formats.DATE_FORMAT).create();
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

    private void method(Integer method, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DtoNotaVenta notaVenta;
        PrintWriter out = null;
        try {
            switch (method) {
                case GET_NOTAVENTA:
                    notaVenta = getNotaVentaFromRequest(request);
                    notaVenta = this.notaVentaBo.findNotaVentaById(user, notaVenta);
                    out = response.getWriter();
                    out.print(json.toJson(notaVenta));
                    break;
                case GET_REPORTE_NOTAVENTA:
                    notaVenta = getNotaVentaFromRequest(request);
                    if ((notaVenta.getIdNotaVenta() != null) && (notaVenta.getIdNotaVenta() != 0)) {
                        notaVenta = this.notaVentaBo.findNotaVentaReportById(user, notaVenta);
                        rutaServer = getServletContext().getRealPath("/");
                        rutaReporte = getServletContext().getRealPath("/reports/"
                                + request.getParameter(Variables.NOMBRE_REPORTE) + "/");
                        ReporteFactory.newInstance().generarReporte(user, notaVenta, rutaServer,
                                rutaReporte, Variables.PDF, response);
                    } else {
                        rutaServer = getServletContext().getRealPath("/");
                        rutaReporte = getServletContext().getRealPath("/reports/"
                                + request.getParameter(Variables.NOMBRE_REPORTE) + "/");
                        if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.FORMATO_REPORTE))) {
                            if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.FORMATO_REPORTE))) {
                                switch (request.getParameter(Text.FORMATO_REPORTE)) {
                                    case Variables.PDF:
                                        ReporteFactory.newInstance().generarReporte(user, this.notaVentaBo.findAllReport(user), rutaServer,
                                                rutaReporte, Variables.PDF, response);
                                        break;
                                    case Variables.EXCEL:
                                        ReporteFactory.newInstance().generarReporte(user, this.notaVentaBo.findAllReport(user), rutaServer, rutaReporte, Variables.EXCEL, response);
                                        break;
                                }
                            }
                        }
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
        } finally {
            if (out != null) {
                ClosePrint.closePrintWriter(out);
            }
        }
    }

    private DtoNotaVenta getNotaVentaFromRequest(HttpServletRequest request) {
        DtoNotaVenta notaVenta = NotaVentaFactory.newInstance();
        if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.ID_NOTA_VENTA))) {
            notaVenta.setIdNotaVenta(NumberUtils.stringToNumber(request.getParameter(Text.ID_NOTA_VENTA)));
        }
        return notaVenta;
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
