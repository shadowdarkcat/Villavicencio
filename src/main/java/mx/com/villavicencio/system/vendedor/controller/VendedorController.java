package mx.com.villavicencio.system.vendedor.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
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
import mx.com.villavicencio.system.credito.credito.dto.DtoCredito;
import mx.com.villavicencio.system.credito.credito.factory.CreditoFactory;
import mx.com.villavicencio.system.productos.productos.bo.ProductoBo;
import mx.com.villavicencio.system.productos.productos.dto.DtoProducto;
import mx.com.villavicencio.system.productos.productos.factory.ProductoFactory;
import mx.com.villavicencio.system.vendedor.reporte.factory.ReporteFactory;
import mx.com.villavicencio.system.usuario.dto.DtoUsuario;
import mx.com.villavicencio.system.vendedor.bo.VendedorBo;
import mx.com.villavicencio.system.vendedor.dto.DtoVendedor;
import mx.com.villavicencio.system.vendedor.factory.VendedorFactory;
import mx.com.villavicencio.utils.BooleanUtils;
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
public class VendedorController extends HttpServlet {

    private DtoUsuario user;
    private VendedorBo vendedorBo;
    private ProductoBo productoBo;
    private RequestDispatcher dispatcher;
    private Gson gson;
    private String rutaServer;
    private String rutaReporte;

    private static final int INIT_VENDEDOR = 0;
    private static final int INSERT_VENDEDOR = 1;
    private static final int DELETE_VENDEDOR = 2;
    private static final int MODIFY_VENDEDOR = 3;
    private static final int ACTIVATE_VENDEDOR = 4;
    private static final int GET_JSON_VENDEDOR = 5;
    private static final int GET_JSON_PRODUCTOS = 6;
    private static final int GET_JSON_PERSONALIZADO = 7;
    private static final int GET_JSON_CP = 8;
    private static final int GET_REPORTE = 9;

    @Override
    public void init() throws ServletException {
        super.init();
        WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
        vendedorBo = (VendedorBo) context.getBean(Service.VENDEDOR_SERVICE);
        productoBo = (ProductoBo) context.getBean(Service.PRODUCTO_SERVICE);
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

    private void method(Integer method, HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        DtoVendedor vendedor;
        PrintWriter out = null;
        try {
            switch (method) {
                case INIT_VENDEDOR:
                    dispatcher = getServletContext().getRequestDispatcher(Dispatcher.VENDEDORES);
                    request.setAttribute(Variables.VENDEDORES, this.vendedorBo.findAll(user));
                    request.setAttribute(Variables.PRODUCTOS, this.productoBo.findAll(user));
                    request.setAttribute(Variables.ID, request.getParameter(Variables.ID));
                    dispatcher.forward(request, response);
                    break;
                case INSERT_VENDEDOR:
                    dispatcher = getServletContext().getRequestDispatcher(Dispatcher.VENDEDORES);
                    vendedor = getFromRequest(request);
                    vendedor.setOpcion(GenericTypes.INSERT);
                    Boolean verifyExist = this.vendedorBo.verifyExistVendedor(user, vendedor);
                    if (!verifyExist) {
                        this.vendedorBo.ingresar(user, vendedor);
                    } else {
                        request.setAttribute(Variables.EXIST, verifyExist);
                    }
                    request.setAttribute(Variables.VENDEDORES, this.vendedorBo.findAll(user));
                    request.setAttribute(Variables.ID, request.getParameter(Text.ID_ACTION));
                    dispatcher.forward(request, response);
                    break;
                case DELETE_VENDEDOR:
                    dispatcher = getServletContext().getRequestDispatcher(Dispatcher.VENDEDORES);
                    vendedor = getFromRequest(request);
                    vendedor.setOpcion(GenericTypes.DELETE);
                    this.vendedorBo.eliminar(user, vendedor);
                    request.setAttribute(Variables.VENDEDORES, this.vendedorBo.findAll(user));
                    request.setAttribute(Variables.ID, request.getParameter(Text.ID_ACTION));
                    dispatcher.forward(request, response);
                    break;
                case MODIFY_VENDEDOR:
                    dispatcher = getServletContext().getRequestDispatcher(Dispatcher.VENDEDORES);
                    vendedor = getFromRequest(request);
                    vendedor.setOpcion(GenericTypes.MODIFY);
                    this.vendedorBo.modificar(user, vendedor);
                    request.setAttribute(Variables.VENDEDORES, this.vendedorBo.findAll(user));
                    request.setAttribute(Variables.ID, request.getParameter(Text.ID_ACTION));
                    dispatcher.forward(request, response);
                    break;
                case ACTIVATE_VENDEDOR:
                    dispatcher = getServletContext().getRequestDispatcher(Dispatcher.VENDEDORES);
                    vendedor = getFromRequest(request);
                    vendedor.setOpcion(GenericTypes.DELETE);
                    this.vendedorBo.eliminar(user, vendedor);
                    request.setAttribute(Variables.VENDEDORES, this.vendedorBo.findAll(user));
                    request.setAttribute(Variables.ID, request.getParameter(Text.ID_ACTION));
                    dispatcher.forward(request, response);
                    break;
                case GET_JSON_VENDEDOR:
                    vendedor = getFromRequest(request);
                    DtoVendedor findById = this.vendedorBo.findById(user, vendedor);
                    if (findById != null) {
                        out = response.getWriter();
                        out.print(gson.toJson(findById));
                    }
                    break;
                case GET_JSON_PRODUCTOS:
                    vendedor = getFromRequest(request);
                    findById = this.vendedorBo.findById(user, vendedor);
                    Collection<DtoProducto> establecidos = findById.getEstablecidos();
                    if (establecidos != null) {
                        if (!establecidos.isEmpty()) {
                            String createTablePersonalizado = TablesUtils.createTableProducto(establecidos);
                            out = response.getWriter();
                            out.print(createTablePersonalizado);
                        } else {
                            Collection<DtoProducto> findAll = this.productoBo.findAll(user);
                            String createTableProducto = TablesUtils.createTableProducto(findAll);
                            out = response.getWriter();
                            out.print(createTableProducto);
                        }
                    } else {
                        Collection<DtoProducto> findAll = this.productoBo.findAll(user);
                        String createTableProducto = TablesUtils.createTableProducto(findAll);
                        out = response.getWriter();
                        out.print(createTableProducto);
                    }
                    break;
                case GET_JSON_PERSONALIZADO:
                    vendedor = getFromRequest(request);
                    findById = this.vendedorBo.findById(user, vendedor);
                    Collection<DtoProducto> personalizado = findById.getPersonalizados();
                    if (personalizado != null) {
                        if (!personalizado.isEmpty()) {
                            String createTablePersonalizado = TablesUtils.createTablePersonalizado(personalizado);
                            out = response.getWriter();
                            out.print(createTablePersonalizado);
                        } else {
                            Collection<DtoProducto> findAll = this.productoBo.findAll(user);
                            String createTableProducto = TablesUtils.createTablePersonalizado(findAll);
                            out = response.getWriter();
                            out.print(createTableProducto);
                        }
                    } else {
                        Collection<DtoProducto> findAll = this.productoBo.findAll(user);
                        String createTableProducto = TablesUtils.createTablePersonalizado(findAll);
                        out = response.getWriter();
                        out.print(createTableProducto);
                    }
                    break;
                case GET_JSON_CP:
                    vendedor = getFromRequest(request);
                    out = response.getWriter();
                    out.print(gson.toJson(this.vendedorBo.getDataCp(user, vendedor)));
                    break;
                case GET_REPORTE:
                    vendedor = getFromRequest(request);
                    if ((vendedor.getIdVendedor() != null) && (vendedor.getIdVendedor() != 0)) {
                        vendedor = this.vendedorBo.findById(user, vendedor);
                        rutaServer = getServletContext().getRealPath("/");
                        rutaReporte = getServletContext().getRealPath("/reports/"
                                + request.getParameter(Variables.NOMBRE_REPORTE) + "/");

                        ReporteFactory.newInstance().generarReporte(user, vendedor, rutaServer,
                                rutaReporte, Variables.PDF, response);
                    } else {
                        rutaServer = getServletContext().getRealPath("/");
                        rutaReporte = getServletContext().getRealPath("/reports/"
                                + request.getParameter(Variables.NOMBRE_REPORTE) + "/");
                        if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.FORMATO_REPORTE))) {
                            if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.FORMATO_REPORTE))) {
                                switch (request.getParameter(Text.FORMATO_REPORTE)) {
                                    case Variables.PDF:
                                        ReporteFactory.newInstance().generarReporte(user, this.vendedorBo.findAll(user), rutaServer,
                                                rutaReporte, Variables.PDF, response);
                                        break;
                                    case Variables.EXCEL:
                                        ReporteFactory.newInstance().generarReporte(user, this.vendedorBo.findAll(user), rutaServer, rutaReporte, Variables.EXCEL, response);
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

    private DtoVendedor getFromRequest(HttpServletRequest request) {
        DtoVendedor vendedor = VendedorFactory.newInstance();

        if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.ID_VENDEDOR))) {
            vendedor.setIdVendedor(NumberUtils.stringToNumber(request.getParameter(Text.ID_VENDEDOR)));
        }

        if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.NOMBRE))) {
            vendedor.setNombre(request.getParameter(Text.NOMBRE));
        }
        if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.APELLIDO_PATERNO))) {
            vendedor.setApellidoPaterno(request.getParameter(Text.APELLIDO_PATERNO));
        }
        if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.APELLIDO_MATERNO))) {
            vendedor.setApellidoMaterno(request.getParameter(Text.APELLIDO_MATERNO));
        }

        if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.RFC))) {
            vendedor.setRfc(request.getParameter(Text.RFC));
        }

        if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.CALLE))) {
            vendedor.setCalle(request.getParameter(Text.CALLE));
        }
        if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.NO_EXTERIOR))) {
            vendedor.setNoExterior(request.getParameter(Text.NO_EXTERIOR));
        }
        if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.NO_INTERIOR))) {
            vendedor.setNoInterior(request.getParameter(Text.NO_INTERIOR));
        }
        if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.COLONIA))) {
            String parameterValues = Arrays.toString(request.getParameterValues(Text.COLONIA));
            parameterValues = StringUtils.replace(parameterValues);
            if (parameterValues.contains(",")) {
                String[] split = StringUtils.split(parameterValues);
                if (split[3].equals("MÉXICO")) {
                    vendedor.setColonia(split[0]);
                    vendedor.setMunicipio(split[1]);
                    vendedor.setCp(split[2]);
                    vendedor.setEstado(split[3]);
                } else if (split[3].equals("DISTRITO FEDERAL")) {
                    vendedor.setColonia(split[0]);
                    vendedor.setDelegacion(split[1]);
                    vendedor.setCp(split[2]);
                    vendedor.setEstado(split[3]);
                }
            } else {
                vendedor.setColonia(request.getParameter(Text.COLONIA));
            }
        }
        if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.CP))) {
            vendedor.setCp(request.getParameter(Text.CP));
        }

        if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.DELEGACION))) {
            vendedor.setDelegacion(request.getParameter(Text.DELEGACION));
        }

        if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.MUNICIPIO))) {
            vendedor.setMunicipio(request.getParameter(Text.MUNICIPIO));
        }

        if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.ESTADO))) {
            vendedor.setEstado(request.getParameter(Text.ESTADO));
        }

        if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.CIUDAD))) {
            vendedor.setCiudad(request.getParameter(Text.CIUDAD));
        }

        if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.TELEFONO1))) {
            vendedor.setTelefono1(request.getParameter(Text.TELEFONO1));
        }
        if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.TELEFONO2))) {
            vendedor.setTelefono2(request.getParameter(Text.TELEFONO2));
        }
        if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.TELEFONO3))) {
            vendedor.setTelefono3(request.getParameter(Text.TELEFONO3));
        }
        if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.CORREO1))) {
            vendedor.setCorreo1(request.getParameter(Text.CORREO1));
        }
        if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.CORREO2))) {
            vendedor.setCorreo2(request.getParameter(Text.CORREO2));
        }
        if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.CORREO3))) {
            vendedor.setCorreo3(request.getParameter(Text.CORREO3));
        }
        if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.PORCENTAJE))) {
            vendedor.setComision(
                    NumberUtils.convertNumberToPorcentaje(
                            NumberUtils.stringToBigDecimal(
                                    request.getParameter(Text.PORCENTAJE)
                            )
                    )
            );
        }

        if (!StringUtils.isReallyEmptyOrNull(request.getParameterValues(Text.CHECK_VISIBLE))) {
            String parameterValues = Arrays.toString(request.getParameterValues(Text.CHECK_VISIBLE));
            parameterValues = StringUtils.replace(parameterValues);
            String[] split = StringUtils.split(parameterValues);
            for (String string : split) {
                if (NumberUtils.isNumeric(string)) {
                    vendedor.setIdVendedor(NumberUtils.stringToNumber(string));
                } else {
                    vendedor.setVisible(BooleanUtils.isChecked(string));
                }
            }
        } else {
            vendedor.setVisible(Boolean.TRUE);
        }

        if (!StringUtils.isReallyEmptyOrNull(request.getParameterValues(Text.CHK_EXTERNO))) {
            String parameterValues = Arrays.toString(request.getParameterValues(Text.CHK_EXTERNO));
            parameterValues = StringUtils.replace(parameterValues);
            String[] split = StringUtils.split(parameterValues);
            for (String string : split) {
                if (NumberUtils.isNumeric(string)) {
                    vendedor.setExterno(BooleanUtils.isChecked(string));
                }
            }
        } else {
            vendedor.setExterno(Boolean.FALSE);
        }

        if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.RDB_PRODUCTO))) {
            if (NumberUtils.stringToNumber(request.getParameter(Text.RDB_PRODUCTO)) == 1) {
                vendedor.setEstablecidos(getEstablecidosFromRequest(request));
            } else if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.RDB_PRODUCTO))) {
                if (NumberUtils.stringToNumber(request.getParameter(Text.RDB_PRODUCTO)) == 2) {
                    vendedor.setPersonalizados(getPersonalizadosFromRequest(request));
                }
            }
        }
        vendedor.setCredito(getCreditoFromRequest(request));
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

    private Collection<DtoProducto> getEstablecidosFromRequest(HttpServletRequest request) {
        Collection<DtoProducto> productos = new ArrayList<>();

        String[] idProductos = null;
        String[] comisionProducto = null;

        if (!StringUtils.isReallyEmptyOrNull(request.getParameterValues(Text.IDS_ESTABLECIDOS))) {
            idProductos = request.getParameterValues(Text.IDS_ESTABLECIDOS);
        }
        if (!StringUtils.isReallyEmptyOrNull(request.getParameterValues(Text.COMISIONES_ESTABLECIDOS))) {
            comisionProducto = request.getParameterValues(Text.COMISIONES_ESTABLECIDOS);
        }
        if (!StringUtils.isReallyEmptyOrNull(idProductos)) {
            for (int index = 0; index < idProductos.length; index++) {
                DtoProducto producto = ProductoFactory.newInstance();
                producto.setIdProducto(
                        NumberUtils.stringToNumber(
                                idProductos[index]
                        )
                );
                if (comisionProducto[index] != null) {
                    producto.setComision(
                            NumberUtils.convertNumberToPorcentaje(
                                    NumberUtils.stringToBigDecimal(
                                            comisionProducto[index]
                                    )
                            )
                    );
                }
                productos.add(producto);

            }
        }
        return productos;
    }

    private Collection<DtoProducto> getPersonalizadosFromRequest(HttpServletRequest request) {
        Collection<DtoProducto> productos = new ArrayList<>();

        String[] idProductos = null;
        String[] costoProductos = null;
        String[] nombreProducto = null;
        String[] peso = null;
        String[] pesoMinimo = null;
        String[] pesoMaximo = null;
        String[] comisionProducto = null;
        String[] imagen = null;
        String[] clave = null;
        if (!StringUtils.isReallyEmptyOrNull(request.getParameterValues(Text.IDS_PERSONALIZADO))) {
            idProductos = request.getParameterValues(Text.IDS_PERSONALIZADO);
        }
        if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.COSTO_PERSONALIZADOS))) {
            costoProductos = request.getParameterValues(Text.COSTO_PERSONALIZADOS);
        }
        if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.NOMBRE_PERSONALIZADO))) {
            nombreProducto = request.getParameterValues(Text.NOMBRE_PERSONALIZADO);
        }
        if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.PESO_PERSONALIZADOS))) {
            peso = request.getParameterValues(Text.PESO_PERSONALIZADOS);
        }
        if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.PESO_MINIMO_PERSONALIZADO))) {
            pesoMinimo = request.getParameterValues(Text.PESO_MINIMO_PERSONALIZADO);
        }
        if (!StringUtils.isReallyEmptyOrNull(request.getParameterValues(Text.PESO_MAXIMO_PERSONALIZADO))) {
            pesoMaximo = request.getParameterValues(Text.PESO_MAXIMO_PERSONALIZADO);
        }
        if (!StringUtils.isReallyEmptyOrNull(request.getParameterValues(Text.COMISIONES_PERSONALIZADOS))) {
            comisionProducto = request.getParameterValues(Text.COMISIONES_PERSONALIZADOS);
        }
        if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.IMAGEN_PERSONALIZADO))) {
            imagen = request.getParameterValues(Text.IMAGEN_PERSONALIZADO);
        }
        if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.CLAVES_PERSONALIZADOS))) {
            clave = request.getParameterValues(Text.CLAVES_PERSONALIZADOS);
        }
        for (int index = 0; index < idProductos.length; index++) {
            DtoProducto producto = ProductoFactory.newInstance();

            if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.ID_ACTION))
                    && (NumberUtils.stringToNumber(request.getParameter(Text.ID_ACTION)) != 1)) {
                producto.setIdProducto(
                        NumberUtils.stringToNumber(
                                idProductos[index]
                        )
                );
            }

            if (costoProductos[index] != null) {
                producto.setCostoUnitario(
                        NumberUtils.stringToBigDecimal(
                                costoProductos[index]
                        )
                );
            }

            if (nombreProducto[index] != null) {
                producto.setNombreProducto(nombreProducto[index]);
            }

            if (peso[index] != null) {
                producto.setPeso(peso[index]);
            }

            if (pesoMinimo[index] != null) {
                producto.setPesoMinimo(
                        NumberUtils.stringToBigDecimal(
                                pesoMinimo[index]
                        )
                );
            }

            if (pesoMaximo[index] != null) {
                producto.setPesoMaximo(
                        NumberUtils.stringToBigDecimal(
                                pesoMaximo[index]
                        )
                );
            }

            if (comisionProducto[index] != null) {
                producto.setComision(
                        NumberUtils.convertNumberToPorcentaje(
                                NumberUtils.stringToBigDecimal(
                                        comisionProducto[index]
                                )
                        )
                );
            }

            if (clave[index] != null) {
                producto.setClaveProducto(
                        clave[index]
                );
            }

            if (!StringUtils.isReallyEmptyOrNull(imagen)) {
                producto.setImagenProducto(
                        StringUtils.replace(
                                imagen[index]
                        )
                );
            }
            productos.add(producto);
        }
        return productos;
    }

    private DtoCredito getCreditoFromRequest(HttpServletRequest request) {
        DtoCredito credito = CreditoFactory.newInstance();
        if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.RDB_CREDITO))) {
            if (NumberUtils.stringToNumber(request.getParameter(Text.RDB_CREDITO)) == 1) {
                credito.setTipoCredito(Variables.MONETARIO);
                credito.setCantidadMonetaria(NumberUtils.stringToBigDecimal(
                        request.getParameter(Text.CREDITO_MONETARIO)
                )
                );
            } else if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.RDB_CREDITO))) {
                if (NumberUtils.stringToNumber(request.getParameter(Text.RDB_CREDITO)) == 2) {
                    credito.setTipoCredito(Variables.CONTRA_NOTA);
                }
            } else if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.RDB_CREDITO))) {
                if (NumberUtils.stringToNumber(request.getParameter(Text.RDB_CREDITO)) == 3) {
                    credito.setTipoCredito(Variables.PLAZO);
                }
            }
        }
        return credito;
    }
}
