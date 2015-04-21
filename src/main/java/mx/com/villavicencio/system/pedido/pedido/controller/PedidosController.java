package mx.com.villavicencio.system.pedido.pedido.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
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
import mx.com.villavicencio.system.pedido.detalles.dto.DtoDetallePedido;
import mx.com.villavicencio.system.pedido.detalles.factory.DetallePedidoFactory;
import mx.com.villavicencio.system.pedido.pedido.bo.PedidoBo;
import mx.com.villavicencio.system.pedido.pedido.dto.DtoPedido;
import mx.com.villavicencio.system.pedido.pedido.factory.PedidoFactory;
import mx.com.villavicencio.system.pedido.reporte.factory.ReporteFactory;
import mx.com.villavicencio.system.productos.productos.dto.DtoProducto;
import mx.com.villavicencio.system.productos.productos.factory.ProductoFactory;
import mx.com.villavicencio.system.usuario.dto.DtoUsuario;
import mx.com.villavicencio.system.vendedor.dto.DtoVendedor;
import mx.com.villavicencio.system.vendedor.factory.VendedorFactory;
import mx.com.villavicencio.system.venta.nota.bo.NotaVentaBo;
import mx.com.villavicencio.system.venta.nota.dto.DtoNotaVenta;
import mx.com.villavicencio.utils.DateUtils;
import mx.com.villavicencio.utils.ImageUtils;
import mx.com.villavicencio.utils.NumberUtils;
import mx.com.villavicencio.utils.StringUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 *
 * @author Gabriel J
 *
 */
public class PedidosController extends HttpServlet {

    private DtoUsuario user;
    private RequestDispatcher dispatcher;
    private PedidoBo pedidoBo;
    private Gson gson;
    private String rutaServer;
    private String rutaReporte;

    private static final int INIT_PEDIDO = 0;
    private static final int INSERT_PEDIDO = 1;
    private static final int DELETE_PEDIDO = 2;
    private static final int MODIFY_PEDIDO = 3;
    private static final int GET_JSON_SESSION = 4;
    private static final int GET_JSON_CARRITO = 5;
    private static final int GET_DETALLE_PEDIDO = 6;
    private static final int GET_ARRAY_PEDIDO = 7;
    private static final int SET_NOTA_VENTA = 8;
    private static final int GET_ARRAY_PEDIDO_CAMBIO = 9;
    private static final int GET_REPORTE = 10;

    @Override
    public void init() throws ServletException {
        super.init();
        WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
        pedidoBo = (PedidoBo) context.getBean(Service.PEDIDO_SERVICE);
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
        DtoPedido pedido;
        PrintWriter out = null;
        try {
            switch (method) {
                case INIT_PEDIDO:
                    dispatcher = getServletContext().getRequestDispatcher(Dispatcher.PEDIDOS);
                    request.setAttribute(Variables.PEDIDOS, pedidoBo.findAll(user));
                    request.setAttribute(Variables.ID, request.getParameter(Variables.ID));
                    dispatcher.forward(request, response);
                    break;

                case INSERT_PEDIDO:
                    pedido = getPedidoFromRequest(request);
                    pedido.setOpcion(GenericTypes.INSERT);
                    pedidoBo.ingresar(user, pedido);
                    request.setAttribute(Variables.PEDIDOS, pedidoBo.findAll(user));
                    request.setAttribute(Variables.ID, request.getParameter(Text.ID_ACTION));
                    dispatcher.forward(request, response);
                    break;

                case DELETE_PEDIDO:
                    dispatcher = getServletContext().getRequestDispatcher(Dispatcher.PEDIDOS);
                    pedido = getPedidoFromRequest(request);
                    pedido.setOpcion(GenericTypes.DELETE);
                    pedidoBo.eliminar(user, pedido);
                    request.setAttribute(Variables.PEDIDOS, pedidoBo.findAll(user));
                    request.setAttribute(Variables.ID, request.getParameter(Text.ID_ACTION));
                    dispatcher.forward(request, response);
                    break;

                case MODIFY_PEDIDO:
                    dispatcher = getServletContext().getRequestDispatcher(Dispatcher.PEDIDOS);
                    pedido = getPedidoFromRequest(request);
                    pedido.setOpcion(GenericTypes.MODIFY);
                    pedidoBo.modificar(user, pedido);
                    request.setAttribute(Variables.PEDIDOS, pedidoBo.findAll(user));
                    request.setAttribute(Variables.ID, request.getParameter(Text.ID_ACTION));
                    dispatcher.forward(request, response);
                    break;

                case GET_JSON_SESSION:
                    if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Variables.ID_CLIENTE_PEDIDO))
                            && (StringUtils.isReallyEmptyOrNull(request.getParameter(Text.ID_PRODUCTO)))) {
                        Integer idPedidoCliente = NumberUtils.stringToNumber(request.getParameter(Variables.ID_CLIENTE_PEDIDO));
                        for (DtoCliente clientes : user.getClientes()) {
                            if (idPedidoCliente.equals(clientes.getIdCliente())) {
                                if ((clientes.getEstablecidos() != null) && (!clientes.getEstablecidos().isEmpty())) {
                                    out = response.getWriter();
                                    out.print(gson.toJson(ImageUtils.getEstablecidos(clientes, request.getParameter(Variables.CONTEXT))));
                                } else if ((clientes.getPersonalizados() != null) && (!clientes.getPersonalizados().isEmpty())) {
                                    out = response.getWriter();
                                    out.print(gson.toJson(ImageUtils.getPersonalizado(clientes, request.getParameter(Variables.CONTEXT))));
                                }
                            }
                        }
                    } else if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Variables.ID_CLIENTE_PEDIDO))
                            && (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.ID_PRODUCTO)))) {
                        Integer idPedidoCliente = NumberUtils.stringToNumber(request.getParameter(Variables.ID_CLIENTE_PEDIDO));
                        Integer idProducto = NumberUtils.stringToNumber(request.getParameter(Text.ID_PRODUCTO));
                        for (DtoCliente clientes : user.getClientes()) {
                            if (idPedidoCliente.equals(clientes.getIdCliente())) {
                                if ((clientes.getEstablecidos() != null) && (!clientes.getEstablecidos().isEmpty())) {
                                    for (DtoProducto productos : clientes.getEstablecidos()) {
                                        if (Objects.equals(idProducto, productos.getIdProducto())) {
                                            out = response.getWriter();
                                            out.print(gson.toJson(ImageUtils.getEstablecidos(productos, request.getParameter(Variables.CONTEXT))));
                                            break;
                                        }
                                    }
                                } else if ((clientes.getPersonalizados() != null) && (!clientes.getPersonalizados().isEmpty())) {
                                    for (DtoProducto productos : clientes.getPersonalizados()) {
                                        if (Objects.equals(idProducto, productos.getIdProducto())) {
                                            out = response.getWriter();
                                            out.print(gson.toJson(ImageUtils.getPersonalizado(productos, request.getParameter(Variables.CONTEXT))));
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }

                    if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Variables.ID_VENDEDOR_PEDIDO))
                            && (StringUtils.isReallyEmptyOrNull(request.getParameter(Text.ID_PRODUCTO)))) {
                        Integer idPedidoCliente = NumberUtils.stringToNumber(request.getParameter(Variables.ID_VENDEDOR_PEDIDO));
                        for (DtoVendedor vendedores : user.getVendedores()) {
                            if (idPedidoCliente.equals(vendedores.getIdVendedor())) {
                                if ((vendedores.getEstablecidos() != null) && (!vendedores.getEstablecidos().isEmpty())) {
                                    out = response.getWriter();
                                    out.print(gson.toJson(ImageUtils.getEstablecidos(vendedores, request.getParameter(Variables.CONTEXT))));
                                } else if ((vendedores.getPersonalizados() != null) && (!vendedores.getPersonalizados().isEmpty())) {
                                    out = response.getWriter();
                                    out.print(gson.toJson(ImageUtils.getPersonalizado(vendedores, request.getParameter(Variables.CONTEXT))));
                                }
                            }
                        }
                    } else if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Variables.ID_VENDEDOR_PEDIDO))
                            && (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.ID_PRODUCTO)))) {
                        Integer idPedidoVendedor = NumberUtils.stringToNumber(request.getParameter(Variables.ID_VENDEDOR_PEDIDO));
                        Integer idProducto = NumberUtils.stringToNumber(request.getParameter(Text.ID_PRODUCTO));
                        for (DtoVendedor vendedores : user.getVendedores()) {
                            if (idPedidoVendedor.equals(vendedores.getIdVendedor())) {
                                if ((vendedores.getEstablecidos() != null) && (!vendedores.getEstablecidos().isEmpty())) {
                                    for (DtoProducto productos : vendedores.getEstablecidos()) {
                                        if (Objects.equals(idProducto, productos.getIdProducto())) {
                                            out = response.getWriter();
                                            out.print(gson.toJson(ImageUtils.getEstablecidos(productos, request.getParameter(Variables.CONTEXT))));
                                            break;
                                        }
                                    }

                                } else if ((vendedores.getPersonalizados() != null) && (!vendedores.getPersonalizados().isEmpty())) {
                                    for (DtoProducto productos : vendedores.getPersonalizados()) {
                                        if (Objects.equals(idProducto, productos.getIdProducto())) {
                                            out = response.getWriter();
                                            out.print(gson.toJson(ImageUtils.getPersonalizado(productos, request.getParameter(Variables.CONTEXT))));
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                    break;

                case GET_JSON_CARRITO:
                    pedido = getCarritoFromRequest(request);
                    out = response.getWriter();
                    out.print(gson.toJson(pedido));
                    break;

                case GET_DETALLE_PEDIDO:
                    pedido = getPedidoFromRequest(request);
                    pedido = pedidoBo.findById(user, pedido);
                    out = response.getWriter();
                    out.print(gson.toJson(pedido));
                    break;

                case GET_ARRAY_PEDIDO:
                    pedido = this.pedidoBo.createFolio(user);
                    pedido.setDetalles(iterateDetalle(getDetallesFromRequest(request)));
                    out = response.getWriter();
                    out.print(gson.toJson(pedido));
                    break;

                case GET_ARRAY_PEDIDO_CAMBIO:
                    pedido = PedidoFactory.newInstance();
                    pedido.setDetalles(iterateDetalle(getDetallesFromRequest(request)));
                    out = response.getWriter();
                    out.print(gson.toJson(pedido));
                    break;

                case SET_NOTA_VENTA:
                    if ((!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.ID_PEDIDO)))
                            && (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.ID_DETALLE)))) {
                        DtoDetallePedido detalle = DetallePedidoFactory.newInstance(request.getParameter(Text.ID_DETALLE));
                        pedido = PedidoFactory.newInstance(request.getParameter(Text.ID_PEDIDO));
                        pedido.setDetalle(detalle);
                        this.pedidoBo.deleteProducto(user, pedido);
                        out = response.getWriter();
                        out.print(gson.toJson(Boolean.TRUE));
                    }
                    break;
                
                    case GET_REPORTE:
                    pedido = getPedidoFromRequest(request);
                    if ((pedido.getIdPedido()!= null) && (pedido.getIdPedido()!= 0)) {
                        pedido = this.pedidoBo.findPedidoReporteById(user, pedido);
                        rutaServer = getServletContext().getRealPath("/");
                        rutaReporte = getServletContext().getRealPath("/reports/"
                                + request.getParameter(Variables.NOMBRE_REPORTE) + "/");
                        
                        ReporteFactory.newInstance().generarReporte(user, pedido, rutaServer,
                                rutaReporte, Variables.PDF, response);
                    } else {
                        rutaServer = getServletContext().getRealPath("/");
                        rutaReporte = getServletContext().getRealPath("/reports/"
                                + request.getParameter(Variables.NOMBRE_REPORTE) + "/");
                        if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.FORMATO_REPORTE))) {
                            if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.FORMATO_REPORTE))) {
                                switch (request.getParameter(Text.FORMATO_REPORTE)) {
                                    case Variables.PDF:
                                        ReporteFactory.newInstance().generarReporte(user, this.pedidoBo.findAllPedidoReporte(user), rutaServer,
                                                rutaReporte, Variables.PDF, response);
                                        break;
                                    case Variables.EXCEL:
                                        ReporteFactory.newInstance().generarReporte(user, this.pedidoBo.findAll(user), rutaServer, rutaReporte, Variables.EXCEL, response);
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

    private DtoPedido getPedidoFromRequest(HttpServletRequest request) {
        DtoPedido pedido = PedidoFactory.newInstance();

        if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.ID_PEDIDO))) {
            pedido.setIdPedido(NumberUtils.stringToNumber(request.getParameter(Text.ID_PEDIDO)));
        }
        if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.FECHA))) {
            pedido.setFecha(DateUtils.stringToDate(request.getParameter(Text.FECHA)));
        }
        if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.FOLIO))) {
            pedido.setFolio(request.getParameter(Text.FOLIO));
        }
        if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.NOMBRE_VENDEDOR))) {
            pedido.setNombreVendedor(request.getParameter(Text.NOMBRE_VENDEDOR));
        }
        if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.STATUS_ALMACEN))) {
            pedido.setStatusAlmacen(StringUtils.stringToChar(request.getParameter(Text.STATUS_ALMACEN)));
        }
        pedido.setDetalles(getDetallesFromRequest(request));
        pedido.setCliente(getClienteFromRequest(request));
        pedido.setVendedor(getVendedorFromRequest(request));
        return pedido;
    }

    private DtoPedido getCarritoFromRequest(HttpServletRequest request) {
        DtoPedido pedido = PedidoFactory.newInstance();
        pedido.setProducto(getProductoFromRequest(request));
        return pedido;
    }

    private DtoProducto getProductoFromRequest(HttpServletRequest request) {

        DtoProducto producto = ProductoFactory.newInstance();

        if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.ID_PRODUCTO))) {
            producto.setIdProducto(NumberUtils.stringToNumber(request.getParameter(Text.ID_PRODUCTO)));
        }

        if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.PIEZAS))) {
            producto.setPiezas(NumberUtils.stringToNumber(request.getParameter(Text.PIEZAS)));
        }

        if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.COSTO))) {
            producto.setCostoUnitario(NumberUtils.stringToBigDecimal(request.getParameter(Text.COSTO)));
        }
        if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.PRODUCTO))) {
            producto.setNombreProducto(request.getParameter(Text.PRODUCTO));
        }
        if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.PESO))) {
            producto.setPeso(request.getParameter(Text.PESO));
        }
        if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.MINIMO))) {
            producto.setPesoMinimo(NumberUtils.stringToBigDecimal(request.getParameter(Text.MINIMO)));
        }
        if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.MAXIMO))) {
            producto.setPesoMaximo(NumberUtils.stringToBigDecimal(request.getParameter(Text.MAXIMO)));
        }
        if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.COMISION))) {
            producto.setComision(NumberUtils.stringToBigDecimal(request.getParameter(Text.COMISION)));
        }
        return producto;
    }

    private Collection<DtoDetallePedido> getDetallesFromRequest(HttpServletRequest request) {
        Collection<DtoDetallePedido> detalles = new ArrayList<>();
        String[] idProductos = null;
        ArrayList<String[]> idDetalles = new ArrayList<>();
        String[] nombreProducto = null;
        String[] piezas = null;
        String[] costos = null;
        String[] comisiones = null;
        String[] pesos = null;
        String[] pesoMinimo = null;
        String[] pesoMaximo = null;

        if (!StringUtils.isReallyEmptyOrNull(request.getParameterValues(Text.ARRAY_ID_PRODUCTO))) {
            idProductos = request.getParameterValues(Text.ARRAY_ID_PRODUCTO);
        }
        if (!StringUtils.isReallyEmptyOrNull(request.getParameterValues(Text.ARRAY_ID_DETALLE))) {
            idDetalles.add(request.getParameterValues(Text.ARRAY_ID_DETALLE));
        }
        if (!StringUtils.isReallyEmptyOrNull(request.getParameterValues(Text.ARRAY_NOMBRE_PRODUCTO))) {
            nombreProducto = request.getParameterValues(Text.ARRAY_NOMBRE_PRODUCTO);
        }
        if (!StringUtils.isReallyEmptyOrNull(request.getParameterValues(Text.ARRAY_PIEZAS))) {
            piezas = request.getParameterValues(Text.ARRAY_PIEZAS);
        }
        if (!StringUtils.isReallyEmptyOrNull(request.getParameterValues(Text.ARRAY_COSTO))) {
            costos = request.getParameterValues(Text.ARRAY_COSTO);
        }
        if (!StringUtils.isReallyEmptyOrNull(request.getParameterValues(Text.ARRAY_COMISIONES))) {
            comisiones = request.getParameterValues(Text.ARRAY_COMISIONES);
        }
        if (!StringUtils.isReallyEmptyOrNull(request.getParameterValues(Text.ARRAY_PESO))) {
            pesos = request.getParameterValues(Text.ARRAY_PESO);
        }
        if (!StringUtils.isReallyEmptyOrNull(request.getParameterValues(Text.ARRAY_PESO_MINIMO))) {
            pesoMinimo = request.getParameterValues(Text.ARRAY_PESO_MINIMO);
        }
        if (!StringUtils.isReallyEmptyOrNull(request.getParameterValues(Text.ARRAY_PESO_MAXIMO))) {
            pesoMaximo = request.getParameterValues(Text.ARRAY_PESO_MAXIMO);
        }
        if (!StringUtils.isReallyEmptyOrNull(idProductos)) {
            for (int index = 0; index < idProductos.length; index++) {
                DtoDetallePedido detalle = DetallePedidoFactory.newInstance();
                detalle.setIdProducto(
                        NumberUtils.stringToNumber(
                                idProductos[index]
                        )
                );

                if (!idDetalles.isEmpty()) {
                    for (Iterator iterator = idDetalles.iterator(); iterator.hasNext();) {
                        Object next = iterator.next();
                        String[] ids = (String[]) next;
                        for (Integer indexIterator = 0; indexIterator < ids.length; indexIterator++) {
                            if (!StringUtils.isReallyEmptyOrNull(ids[indexIterator])) {
                                detalle.setIdDetallePedido(
                                        NumberUtils.stringToNumber(
                                                ids[indexIterator]
                                        )
                                );
                                ids[indexIterator] = null;
                                break;
                            }
                        }
                    }
                }

                detalle.setNombreProducto(nombreProducto[index]);
                detalle.setCantidadPiezas(
                        NumberUtils.stringToNumber(piezas[index])
                );
                detalle.setCostoUnitario(
                        NumberUtils.stringToBigDecimal(costos[index])
                );
                detalle.setComision(
                        NumberUtils.stringToBigDecimal(comisiones[index])
                );

                detalle.setPesoMinimo(
                        NumberUtils.stringToBigDecimal(pesoMinimo[index])
                );
                detalle.setPesoMaximo(
                        NumberUtils.stringToBigDecimal(pesoMaximo[index])
                );

                detalle.setPeso(pesos[index]);
                detalles.add(detalle);
            }
        }
        return detalles;
    }

    private Collection<DtoDetallePedido> iterateDetalle(Collection<DtoDetallePedido> detallesFromRequest) {
        Collection<DtoDetallePedido> detalles = new ArrayList<>();
        Integer idAnt = 0;
        Integer canAnt = 0;
        Boolean flag = Boolean.FALSE;
        for (DtoDetallePedido array : detallesFromRequest) {
            DtoDetallePedido detalle = DetallePedidoFactory.newInstance();
            if (Objects.equals(idAnt, array.getIdProducto())) {
                detalle.setCantidadPiezas(array.getCantidadPiezas() + canAnt);
                detalle.setNombreProducto(array.getNombreProducto());
                Iterator<DtoDetallePedido> iterator = detalles.iterator();
                while (iterator.hasNext()) {
                    DtoDetallePedido next = iterator.next();
                    if (Objects.equals(idAnt, next.getIdProducto())) {
                        detalle.setCantidadPiezas(array.getCantidadPiezas() + next.getCantidadPiezas());
                        if (next.getIdDetallePedido() != null) {
                            detalle.setIdDetallePedido(next.getIdDetallePedido());
                        }
                        iterator.remove();
                        break;
                    }
                }
            } else {
                Iterator<DtoDetallePedido> iterator = detalles.iterator();
                while (iterator.hasNext()) {
                    DtoDetallePedido next = iterator.next();
                    if (Objects.equals(array.getIdProducto(), next.getIdProducto())) {
                        detalle.setCantidadPiezas(array.getCantidadPiezas() + next.getCantidadPiezas());
                        detalle.setNombreProducto(array.getNombreProducto());
                        if (next.getIdDetallePedido() != null) {
                            detalle.setIdDetallePedido(next.getIdDetallePedido());
                        }
                        iterator.remove();
                        flag = Boolean.TRUE;
                        break;
                    }
                }
                if (!Objects.equals(flag, Boolean.TRUE)) {
                    detalle.setNombreProducto(array.getNombreProducto());
                    detalle.setCantidadPiezas(array.getCantidadPiezas());
                    if (array.getIdDetallePedido() != null) {
                        detalle.setIdDetallePedido(array.getIdDetallePedido());
                    }
                }
            }
            detalle.setIdProducto(array.getIdProducto());
            detalle.setComision(array.getComision());
            detalle.setCostoUnitario(array.getCostoUnitario());
            detalle.setPeso(array.getPeso());
            detalle.setPesoMinimo(array.getPesoMinimo());
            detalle.setPesoMaximo(array.getPesoMaximo());
            idAnt = array.getIdProducto();
            canAnt = array.getCantidadPiezas();
            detalles.add(detalle);
        }
        return detalles;
    }

    private DtoCliente getClienteFromRequest(HttpServletRequest request) {
        DtoCliente cliente = ClienteFactory.newInstance();
        if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.ID_CLIENTE))) {
            cliente.setIdCliente(NumberUtils.stringToNumber(request.getParameter(Text.ID_CLIENTE)));
        }
        return cliente;
    }

    private DtoVendedor getVendedorFromRequest(HttpServletRequest request) {
        DtoVendedor vendedor = VendedorFactory.newInstance();
        if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.ID_VENDEDOR_CLIENTE))) {
            vendedor.setIdVendedor(NumberUtils.stringToNumber(request.getParameter(Text.ID_VENDEDOR_CLIENTE)));
        }
        return vendedor;
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
