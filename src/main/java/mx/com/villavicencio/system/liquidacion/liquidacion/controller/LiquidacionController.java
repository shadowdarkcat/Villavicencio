package mx.com.villavicencio.system.liquidacion.liquidacion.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
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
import mx.com.villavicencio.system.liquidacion.detalle.dto.DtoDetalleLiquidacion;
import mx.com.villavicencio.system.liquidacion.detalle.factory.DetalleLiquidacionFactory;
import mx.com.villavicencio.system.liquidacion.liquidacion.bo.LiquidacionBo;
import mx.com.villavicencio.system.liquidacion.liquidacion.dto.DtoLiquidacion;
import mx.com.villavicencio.system.liquidacion.liquidacion.factory.LiquidacionFactory;
import mx.com.villavicencio.system.movimientos.movimientos.dto.DtoMovimientos;
import mx.com.villavicencio.system.movimientos.movimientos.factory.MovimientosFactory;
import mx.com.villavicencio.system.pedido.detalles.dto.DtoDetallePedido;
import mx.com.villavicencio.system.pedido.pedido.factory.PedidoFactory;
import mx.com.villavicencio.system.usuario.dto.DtoUsuario;
import mx.com.villavicencio.system.venta.detalle.dto.DtoDetalleNotaVenta;
import mx.com.villavicencio.system.venta.detalle.factory.DetalleNotaVentaFactory;
import mx.com.villavicencio.system.venta.devoluciones.detalle.dto.DtoDetalleDevoluciones;
import mx.com.villavicencio.system.venta.devoluciones.detalle.factory.DetalleDevolucionesFactory;
import mx.com.villavicencio.system.venta.devoluciones.devoluciones.dto.DtoDevoluciones;
import mx.com.villavicencio.system.venta.devoluciones.devoluciones.factory.DevolucionesFactory;
import mx.com.villavicencio.system.venta.nota.bo.NotaVentaBo;
import mx.com.villavicencio.system.venta.nota.dto.DtoNotaVenta;
import mx.com.villavicencio.system.venta.nota.factory.NotaVentaFactory;
import mx.com.villavicencio.utils.DateUtils;
import mx.com.villavicencio.utils.NumberUtils;
import mx.com.villavicencio.utils.StringUtils;
import mx.com.villavicencio.utils.TraductorUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 *
 * @author Gabriel J
 *
 */
public class LiquidacionController extends HttpServlet {

    private DtoUsuario user;
    private NotaVentaBo notaVentaBo;
    private LiquidacionBo liquidacionBo;
    private Gson gson;
    private RequestDispatcher dispatcher;

    private static final int INIT_LIQUIDACION = 0;
    private static final int GET_LIQUIDACION = 1;
    private static final int GET_CANTIDAD_LETRA = 2;
    private static final int INSERT_LIQUIDACION = 3;

    @Override
    public void init() throws ServletException {
        super.init();
        WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
        notaVentaBo = (NotaVentaBo) context.getBean(Service.NOTA_VENTA_SERVICE);
        liquidacionBo = (LiquidacionBo) context.getBean(Service.LIQUIDACION_SERVICE);
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
        DtoNotaVenta notaVenta;
        DtoLiquidacion liquidacion;
        PrintWriter out = null;
        try {
            switch (method) {

                case INIT_LIQUIDACION:
                    break;
                case GET_LIQUIDACION:
                    notaVenta = getNotaVentaFromRequest(request);
                    notaVenta = this.notaVentaBo.findById(user, notaVenta);
                    out = response.getWriter();
                    notaVenta = getNotaVenta(notaVenta);
                    out.print(gson.toJson(notaVenta));
                    break;
                case GET_CANTIDAD_LETRA:
                    if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.TOTAL_VENTA))) {
                        String traducir = TraductorUtils.traducir(NumberUtils.stringToBigDecimal(request.getParameter(Text.TOTAL_VENTA)));
                        out = response.getWriter();
                        out.print(gson.toJson(traducir));
                    }
                    break;
                case INSERT_LIQUIDACION:
                    liquidacion = getFromRequest(request);
                    notaVenta = getNotaVentaFromRequest(request);
                    liquidacion.setOpcion(GenericTypes.INSERT);
                    liquidacionBo.ingresar(user, liquidacion);
                    notaVenta.setOpcion(GenericTypes.MODIFY);
                    this.notaVentaBo.modificar(user, notaVenta);
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

    private DtoLiquidacion getFromRequest(HttpServletRequest request) {
        DtoLiquidacion liquidacion = LiquidacionFactory.newInstance();
        if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.ID_LIQUIDACION))) {
            liquidacion.setIdLiquidacion(NumberUtils.stringToNumber(request.getParameter(Text.ID_LIQUIDACION)));
        }
        if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.FOLIO))) {
            liquidacion.setFolio(request.getParameter(Text.FOLIO));
        }
        if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.FECHA))) {
            liquidacion.setFecha(DateUtils.stringToDate(request.getParameter(Text.FECHA)));
        }
        if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.NOMBRE_VENDEDOR))) {
            liquidacion.setNombreVendedor(request.getParameter(Text.NOMBRE_VENDEDOR));
        }
        if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.OBSERVACIONES))) {
            liquidacion.setObservaciones(request.getParameter(Text.OBSERVACIONES));
        }
        liquidacion.setDetalleLiquidacion(getDetalleFromRequest(request));
        return liquidacion;
    }

    private Collection<DtoDetalleLiquidacion> getDetalleFromRequest(HttpServletRequest request) {
        Collection<DtoDetalleLiquidacion> collection = new ArrayList<>();
        String[] productos = null;
        String[] piezasSalida = null;
        String[] kilosSalida = null;
        String[] piezasDevolucion = null;
        String[] kilosDevolucion = null;
        String[] kilosTotal = null;
        String[] costoUnitario = null;
        String[] subtotal = null;
        String[] observaciones = null;

        if (!StringUtils.isReallyEmptyOrNull(request.getParameterValues(Text.ARRAY_NOMBRE_PRODUCTO))) {
            productos = request.getParameterValues(Text.ARRAY_NOMBRE_PRODUCTO);
        }

        if (!StringUtils.isReallyEmptyOrNull(request.getParameterValues(Text.ARRAY_PIEZAS))) {
            piezasSalida = request.getParameterValues(Text.ARRAY_PIEZAS);
        }

        if (!StringUtils.isReallyEmptyOrNull(request.getParameterValues(Text.ARRAY_KILOS))) {
            kilosSalida = request.getParameterValues(Text.ARRAY_KILOS);
        }

        if (!StringUtils.isReallyEmptyOrNull(request.getParameterValues(Text.ARRAY_PIEZAS_DEVOLUCION))) {
            piezasDevolucion = request.getParameterValues(Text.ARRAY_PIEZAS_DEVOLUCION);
        }

        if (!StringUtils.isReallyEmptyOrNull(request.getParameterValues(Text.ARRAY_KILOS_DEVOLUCION))) {
            kilosDevolucion = request.getParameterValues(Text.ARRAY_KILOS_DEVOLUCION);
        }

        if (!StringUtils.isReallyEmptyOrNull(request.getParameterValues(Text.ARRAY_KILOS_TOTAL))) {
            kilosTotal = request.getParameterValues(Text.ARRAY_KILOS_TOTAL);
        }

        if (!StringUtils.isReallyEmptyOrNull(request.getParameterValues(Text.ARRAY_COSTO))) {
            costoUnitario = request.getParameterValues(Text.ARRAY_COSTO);
        }

        if (!StringUtils.isReallyEmptyOrNull(request.getParameterValues(Text.ARRAY_SUBTOTAL))) {
            subtotal = request.getParameterValues(Text.ARRAY_SUBTOTAL);
        }

        if (!StringUtils.isReallyEmptyOrNull(request.getParameterValues(Text.ARRAY_OBSERVACIONES))) {
            observaciones = request.getParameterValues(Text.ARRAY_OBSERVACIONES);
        }

        for (Integer index = 0; index < productos.length; index++) {
            DtoDetalleLiquidacion detalle = DetalleLiquidacionFactory.newInstance();
            if (!StringUtils.isReallyEmptyOrNull(productos)) {
                if (!StringUtils.isReallyEmptyOrNull(productos[index])) {
                    detalle.setNombreProducto(productos[index]);
                }
            }
            if (!StringUtils.isReallyEmptyOrNull(piezasSalida)) {
                if (!StringUtils.isReallyEmptyOrNull(piezasSalida[index])) {
                    detalle.setPiezasSalida(NumberUtils.stringToNumber(piezasSalida[index]));
                }
            }
            if (!StringUtils.isReallyEmptyOrNull(kilosSalida)) {
                if (!StringUtils.isReallyEmptyOrNull(kilosSalida[index])) {
                    detalle.setKilosSalida(NumberUtils.stringToBigDecimal(kilosSalida[index]));
                }
            }
            if (!StringUtils.isReallyEmptyOrNull(piezasDevolucion)) {
                if (index < piezasDevolucion.length) {
                    if (!StringUtils.isReallyEmptyOrNull(piezasDevolucion[index])) {
                        detalle.setPiezasDevolucion(NumberUtils.stringToNumber(piezasDevolucion[index]));
                    }
                }
            }
            if (!StringUtils.isReallyEmptyOrNull(kilosDevolucion)) {
                if (index < kilosDevolucion.length) {
                    if (!StringUtils.isReallyEmptyOrNull(kilosDevolucion[index])) {
                        detalle.setKilosDevolucion(NumberUtils.stringToBigDecimal(kilosDevolucion[index]));
                    }
                }
            }
            if (!StringUtils.isReallyEmptyOrNull(kilosTotal)) {
                if (index < kilosTotal.length) {
                    if (!StringUtils.isReallyEmptyOrNull(kilosTotal[index])) {
                        detalle.setKilosTotal(NumberUtils.stringToBigDecimal(kilosTotal[index]));
                    }
                } else if (!StringUtils.isReallyEmptyOrNull(kilosSalida[index])) {
                    detalle.setKilosTotal(NumberUtils.stringToBigDecimal(kilosSalida[index]));
                }

            }
            if (!StringUtils.isReallyEmptyOrNull(costoUnitario)) {
                if (!StringUtils.isReallyEmptyOrNull(costoUnitario[index])) {
                    detalle.setCostoUnitario(NumberUtils.stringToBigDecimal(costoUnitario[index]));
                }
            }
            if (!StringUtils.isReallyEmptyOrNull(subtotal)) {
                if (!StringUtils.isReallyEmptyOrNull(subtotal[index])) {
                    detalle.setSubtotal(NumberUtils.stringToBigDecimal(subtotal[index]));
                }
            }
            if (!StringUtils.isReallyEmptyOrNull(observaciones)) {
                if (!StringUtils.isReallyEmptyOrNull(observaciones[index])) {
                    detalle.setObservaciones(observaciones[index]);
                }
            }
            collection.add(detalle);
        }
        return collection;
    }

    private DtoNotaVenta getNotaVentaFromRequest(HttpServletRequest request) {
        DtoNotaVenta notaVenta = NotaVentaFactory.newInstance();
        if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.ID_NOTA_VENTA))) {
            notaVenta.setIdNotaVenta(NumberUtils.stringToNumber(request.getParameter(Text.ID_NOTA_VENTA)));
        }

        if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.STATUS_NOTA_VENTA))) {
            notaVenta.setStatusNotaVenta(request.getParameter(Text.STATUS_NOTA_VENTA).charAt(0));
        }
        notaVenta.setPedido(PedidoFactory.newInstance());
        return notaVenta;
    }

    private DtoNotaVenta getNotaVenta(DtoNotaVenta notaVenta) {
        DtoNotaVenta nota = NotaVentaFactory.newInstance();
        nota.setIdNotaVenta(notaVenta.getIdNotaVenta());
        nota.setNombreCliente(notaVenta.getPedido().getNombreVendedor());
        nota.setFolio(notaVenta.getFolio());
        nota.setDetalles(notaVenta.getDetalles());
        for (DtoMovimientos movs : notaVenta.getMovimientos()) {
            DtoDevoluciones devolucion = DevolucionesFactory.newInstance();
            DtoMovimientos movimientos = MovimientosFactory.newInstance();
            Collection<DtoDetalleDevoluciones> collection = new ArrayList<>();
            for (DtoDetalleDevoluciones detalles : movs.getDevoluciones().getDetalles()) {
                DtoDetalleDevoluciones devoluciones = detalles;
                collection.add(devoluciones);
            }
            devolucion.setDetalles(collection);
            movimientos.setDevoluciones(devolucion);
            nota.setMovimiento(movimientos);
            break;
        }
        Collection<DtoDetalleNotaVenta> collection = new ArrayList<>();
        for (DtoDetallePedido detalles : notaVenta.getPedido().getDetalles()) {
            for (DtoDetalleNotaVenta detalleNota : notaVenta.getDetalles()) {
                if (detalles.getNombreProducto().equals(detalleNota.getNombreProducto())) {
                    DtoDetalleNotaVenta detalle = detalleNota;
                    detalle.setObservaciones(detalles.getObservacion());
                    collection.add(detalle);
                    break;
                }
            }
        }

        if (!nota.getMovimiento().getDevoluciones().getDetalles().isEmpty()) {
            if (nota.getMovimiento().getDevoluciones().getDetalles().size() != notaVenta.getDetalles().size()) {
                Collection<DtoDetalleDevoluciones> collectionDevolucion = new ArrayList<>();
                DtoDevoluciones devolucion = DevolucionesFactory.newInstance();
                DtoMovimientos movimientos = MovimientosFactory.newInstance();
                DtoDetalleDevoluciones devoluciones = null;
                for (DtoDetalleNotaVenta detalleNota : notaVenta.getDetalles()) {
                    for (DtoDetalleDevoluciones detalleDevolucion : nota.getMovimiento().getDevoluciones().getDetalles()) {
                        if (detalleNota.getNombreProducto().equals(detalleDevolucion.getNombreProducto())) {
                            devoluciones = detalleDevolucion;
                            collectionDevolucion.add(devoluciones);
                        } else {
                            devoluciones = DetalleDevolucionesFactory.newInstance();
                            devoluciones.setCantidadKilos(BigDecimal.ZERO);
                            devoluciones.setCantidadPiezas(GenericTypes.ZERO);
                            devoluciones.setCosto(detalleNota.getCostoUnitario());
                            collectionDevolucion.add(devoluciones);
                        }

                    }
                }
                devolucion.setDetalles(collectionDevolucion);
                movimientos.setDevoluciones(devolucion);
                nota.setMovimiento(movimientos);
            }
        }
        nota.setDetalles(collection);
        nota.setObservaciones(notaVenta.getObservaciones());
        return nota;
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
