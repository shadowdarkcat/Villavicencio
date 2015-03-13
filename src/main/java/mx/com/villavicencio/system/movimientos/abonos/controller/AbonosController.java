package mx.com.villavicencio.system.movimientos.abonos.controller;

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
import mx.com.villavicencio.system.cliente.dto.DtoCliente;
import mx.com.villavicencio.system.cliente.factory.ClienteFactory;
import mx.com.villavicencio.system.movimientos.abonos.bo.AbonosBo;
import mx.com.villavicencio.system.movimientos.abonos.dto.DtoAbonos;
import mx.com.villavicencio.system.movimientos.abonos.factory.AbonosFactory;
import mx.com.villavicencio.system.movimientos.bancos.bo.BancosBo;
import mx.com.villavicencio.system.movimientos.bancos.dto.DtoBancos;
import mx.com.villavicencio.system.movimientos.bancos.factory.BancosFactory;
import mx.com.villavicencio.system.usuario.dto.DtoUsuario;
import mx.com.villavicencio.system.vendedor.dto.DtoVendedor;
import mx.com.villavicencio.system.vendedor.factory.VendedorFactory;
import mx.com.villavicencio.system.venta.nota.bo.NotaVentaBo;
import mx.com.villavicencio.system.venta.nota.dto.DtoNotaVenta;
import mx.com.villavicencio.system.venta.nota.factory.NotaVentaFactory;
import mx.com.villavicencio.utils.DateUtils;
import mx.com.villavicencio.utils.NumberUtils;
import mx.com.villavicencio.utils.StringUtils;
import mx.com.villavicencio.utils.TablesUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 *
 * @author Gabriel J
 *
 */
public class AbonosController extends HttpServlet {

    private DtoUsuario user;
    private AbonosBo abonosBo;
    private NotaVentaBo notaVentaBo;
    private BancosBo bancosBo;
    private Gson gson;

    private RequestDispatcher dispatcher;

    private static final int INIT_ABONOS = 0;
    private static final int GET_JSON_DETALLES = 1;
    private static final int GET_JSON_DETALLES_ABONOS = 2;
    private static final int INSERT_ABONO = 3;

    @Override
    public void init() throws ServletException {
        super.init();
        WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
        abonosBo = (AbonosBo) context.getBean(Service.ABONOS_SERVICE);
        notaVentaBo = (NotaVentaBo) context.getBean(Service.NOTA_VENTA_SERVICE);
        bancosBo = (BancosBo) context.getBean(Service.BANCOS_SERVICE);
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

    private void method(Integer method, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        DtoCliente cliente = null;
        DtoVendedor vendedor = null;
        PrintWriter out = null;

        try {
            switch (method) {
                case INIT_ABONOS:
                    dispatcher = getServletContext().getRequestDispatcher(Dispatcher.ABONOS);
                    request.setAttribute(Variables.CLIENTES, this.abonosBo.findAllClientes(user));
                    request.setAttribute(Variables.VENDEDORES, this.abonosBo.findAllVendedores(user));
                    request.setAttribute(Variables.BANCOS, this.bancosBo.findAll(user));
                    request.setAttribute(Variables.ID, request.getParameter(Variables.ID));
                    dispatcher.forward(request, response);
                    break;
                case GET_JSON_DETALLES:
                    if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.ID_CLIENTE))) {
                        cliente = ClienteFactory.newInstance(request.getParameter(Text.ID_CLIENTE));
                        cliente = abonosBo.findByIdCliente(user, cliente);
                        cliente = TablesUtils.createTableDetalles(cliente, request.getParameter(Variables.CONTEXT));
                        out = response.getWriter();
                        out.print(gson.toJson(cliente));
                    } else if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.ID_VENDEDOR))) {
                        vendedor = VendedorFactory.newInstance(request.getParameter(Text.ID_VENDEDOR));
                        vendedor = abonosBo.findByIdVendedor(user, vendedor);
                        vendedor = TablesUtils.createTableDetalles(vendedor, request.getParameter(Variables.CONTEXT));
                        out = response.getWriter();
                        out.print(gson.toJson(vendedor));
                    }
                    break;

                case GET_JSON_DETALLES_ABONOS:
                    if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.ID_NOTA_VENTA))) {
                        DtoNotaVenta notaVenta = NotaVentaFactory.newInstance(request.getParameter(Text.ID_NOTA_VENTA));
                        notaVenta = notaVentaBo.findById(user, notaVenta);
                        String createTableAltaAbonos = TablesUtils.createTableAltaAbonos(notaVenta, request.getParameter(Variables.CONTEXT), request.getParameter(Text.ID_ACTION));
                        out = response.getWriter();
                        out.print(createTableAltaAbonos);
                    }
                    break;

                case INSERT_ABONO:
                    DtoAbonos abono = getAbonoFromRequest(request);
                    abono.setOpcion(GenericTypes.INSERT);
                    this.abonosBo.ingresar(user, abono, request.getParameter(Text.ID_NOTA_VENTA));
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

    private DtoAbonos getAbonoFromRequest(HttpServletRequest request) {
        DtoAbonos abonos = AbonosFactory.newInstance();

        if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.ID_ABONO))) {
            abonos.setIdAbonos(NumberUtils.stringToNumber(request.getParameter(Text.ID_ABONO)));
        }

        if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.FECHA))) {
            abonos.setFechaCaptura(DateUtils.stringToDate(request.getParameter(Text.FECHA)));
        }

        if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.CANTIDAD_ABONO))) {
            abonos.setAbono(NumberUtils.stringToBigDecimal(
                    request.getParameter(Text.CANTIDAD_ABONO)
            )
            );
        }
        if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.RDB_TIPO_PAGO))) {
            if (NumberUtils.stringToNumber(request.getParameter(Text.RDB_TIPO_PAGO)) == 1) {
                abonos.setTipoPago(
                        PropertiesBean.getPagos().getProperty(Property.CHEQUE)
                );
            } else if (NumberUtils.stringToNumber(request.getParameter(Text.RDB_TIPO_PAGO)) == 2) {
                abonos.setTipoPago(
                        PropertiesBean.getPagos().getProperty(Property.DEPOSITO)
                );
            } else if (NumberUtils.stringToNumber(request.getParameter(Text.RDB_TIPO_PAGO)) == 3) {
                abonos.setTipoPago(
                        PropertiesBean.getPagos().getProperty(Property.EFECTIVO)
                );
            } else if (NumberUtils.stringToNumber(request.getParameter(Text.RDB_TIPO_PAGO)) == 4) {
                abonos.setTipoPago(
                        PropertiesBean.getPagos().getProperty(Property.TRANSFERENCIA)
                );
            }
        }
        abonos.setBancos(getBancosFromRequest(request));
        return abonos;
    }

    private DtoBancos getBancosFromRequest(HttpServletRequest request) {
        DtoBancos bancos = BancosFactory.newInstance();
        if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.CBO_BANCO))) {
            bancos.setIdBancos(NumberUtils.stringToNumber(request.getParameter(Text.CBO_BANCO)));
        }

        return bancos;
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
