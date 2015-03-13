package mx.com.villavicencio.system.productos.productos.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
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
import mx.com.villavicencio.image.helper.ImageViewHelper;
import mx.com.villavicencio.properties.PropertiesBean;
import mx.com.villavicencio.properties.Property;
import mx.com.villavicencio.security.SecurityUtils;
import mx.com.villavicencio.system.productos.productos.bo.ProductoBo;
import mx.com.villavicencio.system.productos.productos.dto.DtoProducto;
import mx.com.villavicencio.system.productos.productos.factory.ProductoFactory;
import mx.com.villavicencio.system.productos.reporte.factory.ReporteFactory;
import mx.com.villavicencio.system.productos.reporte.reporte.ReporteProductos;
import mx.com.villavicencio.system.usuario.dto.DtoUsuario;
import mx.com.villavicencio.utils.BooleanUtils;
import mx.com.villavicencio.utils.NumberUtils;
import mx.com.villavicencio.utils.StringUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.servlet.ServletRequestContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 *
 * @author Gabriel J
 *
 */
public class ProductoController extends HttpServlet {

    private DtoUsuario user;
    private RequestDispatcher dispatcher;

    private ProductoBo productoBo;
    private Gson gson;
    private String id;
    private String rutaServer;
    private String rutaReporte;

    private static final int INIT_PRODUCTO = 0;
    private static final int INSERT_PRODUCTO = 1;
    private static final int DELETE_PRODUCTO = 2;
    private static final int MODIFY_PRODUCTO = 3;
    private static final int ACTIVATE_PRODUCTO = 4;
    private static final int GET_JSON_PRODUCTO = 5;
    private static final int GET_ALL_JSON_PRODUCTO = 6;
    private static final int GET_REPORT = 7;

    @Override
    public void init() throws ServletException {
        super.init();
        WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
        productoBo = (ProductoBo) context.getBean(Service.PRODUCTO_SERVICE);
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
        DtoProducto producto = null;
        PrintWriter out = null;
        try {
            switch (method) {
                case INIT_PRODUCTO:
                    dispatcher = getServletContext().getRequestDispatcher(Dispatcher.PRODUCTO);
                    request.setAttribute(Variables.PRODUCTOS, this.productoBo.findAll(user));
                    request.setAttribute(Variables.ID, request.getParameter(Variables.ID));
                    dispatcher.forward(request, response);
                    break;

                case INSERT_PRODUCTO:
                    dispatcher = getServletContext().getRequestDispatcher(Dispatcher.PRODUCTO);
                    producto = getProductoFromRequest(request, method);
                    producto.setOpcion(GenericTypes.INSERT);
                    Boolean verifyExist = this.productoBo.verifyExist(user, producto);
                    if (!verifyExist) {
                        this.productoBo.ingresar(user, producto);
                    } else {
                        request.setAttribute(Variables.EXIST, verifyExist);
                    }
                    request.setAttribute(Variables.PRODUCTOS, this.productoBo.findAll(user));
                    request.setAttribute(Variables.ID, this.id);
                    dispatcher.forward(request, response);
                    break;

                case DELETE_PRODUCTO:
                    dispatcher = getServletContext().getRequestDispatcher(Dispatcher.PRODUCTO);
                    producto = getProductoFromRequest(request, method);
                    producto.setOpcion(GenericTypes.DELETE);
                    this.productoBo.eliminar(user, producto);
                    request.setAttribute(Variables.PRODUCTOS, this.productoBo.findAll(user));
                    request.setAttribute(Variables.ID, this.id);
                    dispatcher.forward(request, response);
                    break;

                case MODIFY_PRODUCTO:
                    dispatcher = getServletContext().getRequestDispatcher(Dispatcher.PRODUCTO);
                    producto = getProductoFromRequest(request, method);
                    producto.setOpcion(GenericTypes.MODIFY);
                    this.productoBo.modificar(user, producto);
                    request.setAttribute(Variables.PRODUCTOS, this.productoBo.findAll(user));
                    request.setAttribute(Variables.ID, this.id);
                    dispatcher.forward(request, response);
                    break;

                case ACTIVATE_PRODUCTO:
                    dispatcher = getServletContext().getRequestDispatcher(Dispatcher.PRODUCTO);
                    producto = getProductoFromRequest(request, method);
                    producto.setOpcion(GenericTypes.DELETE);
                    this.productoBo.eliminar(user, producto);
                    request.setAttribute(Variables.PRODUCTOS, this.productoBo.findAll(user));
                    request.setAttribute(Variables.ID, this.id);
                    dispatcher.forward(request, response);
                    break;

                case GET_JSON_PRODUCTO:
                    out = response.getWriter();
                    producto = ProductoFactory.newInstance(request.getParameter(Text.ID_PRODUCTO));
                    producto = this.productoBo.findById(user, producto);
                    out.print(gson.toJson(producto));
                    break;

                case GET_ALL_JSON_PRODUCTO:
                    out = response.getWriter();
                    Collection<DtoProducto> findAll = this.productoBo.findAll(user);
                    out.print(gson.toJson(findAll));
                    break;

                case GET_REPORT:
                    rutaServer = getServletContext().getRealPath("/");
                    rutaReporte = getServletContext().getRealPath("/reports/"
                            + request.getParameter(Variables.NOMBRE_REPORTE) + "/");
                    if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.FORMATO_REPORTE))) {
                        if (null != request.getParameter(Text.FORMATO_REPORTE)) {
                            switch (request.getParameter(Text.FORMATO_REPORTE)) {
                                case Variables.PDF:
                                    ReporteFactory.newInstance().generarReporte(user, this.productoBo.findAll(user), rutaServer,
                                            rutaReporte, Variables.PDF, response);
                                    break;
                                case Variables.EXCEL:
                                    ReporteFactory.newInstance().generarReporte(user, this.productoBo.findAll(user), rutaServer, rutaReporte, Variables.EXCEL, response);
                                    break;
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

    private DtoProducto getProductoFromRequest(HttpServletRequest request, Integer method) {
        String string = null;
        DtoProducto producto = ProductoFactory.newInstance();
        ServletRequestContext src = new ServletRequestContext(request);
        try {
            String servletContext = getInitParameter(Variables.PATH);
            String path = servletContext + File.separator;
            DiskFileItemFactory factory = new DiskFileItemFactory((1024 * 255), new File(System.getProperty(Variables.IO_TMP_DIR)));
            ServletFileUpload upload = new ServletFileUpload(factory);
            List list = upload.parseRequest(src);
            Iterator it = list.iterator();
            while (it.hasNext()) {
                FileItem item = (FileItem) it.next();
                switch (item.getFieldName()) {
                    case Text.ID_PRODUCTO:
                        if (!StringUtils.isReallyEmptyOrNull(item.getString())) {
                            producto.setIdProducto(NumberUtils.stringToNumber(item.getString()));
                        }
                        break;

                    case Text.PRODUCTO:
                        if (!StringUtils.isReallyEmptyOrNull(item.getString())) {
                            producto.setNombreProducto(item.getString());
                        }
                        break;

                    case Text.CLAVE_PRODUCTO:
                        if (!StringUtils.isReallyEmptyOrNull(item.getString())) {
                            producto.setClaveProducto(item.getString());
                        }
                        break;

                    case Text.CBO_UNIDAD_PESO:
                        if (!StringUtils.isReallyEmptyOrNull(item.getString())) {
                            producto.setPeso(item.getString());
                        }
                        break;

                    case Text.MAXIMO:
                        if (!StringUtils.isReallyEmptyOrNull(item.getString())) {
                            producto.setPesoMaximo(NumberUtils.stringToBigDecimal(item.getString()));
                        }
                        break;

                    case Text.MINIMO:
                        if (!StringUtils.isReallyEmptyOrNull(item.getString())) {
                            producto.setPesoMinimo(NumberUtils.stringToBigDecimal(item.getString()));
                        }
                        break;

                    case Text.COSTO:
                        if (!StringUtils.isReallyEmptyOrNull(item.getString())) {
                            producto.setCostoUnitario(NumberUtils.stringToBigDecimal(item.getString()));
                        }
                        break;

                    case Text.ID_ACTION:
                        id = item.getString();
                        break;

                    case Text.IMAGE:
                        if (item.getSize() < 2097152) {
                            if (item.getFieldName().equals(Text.IMAGE)) {
                                if (!StringUtils.isReallyEmptyOrNull(item.getString())) {
                                    if (method == INSERT_PRODUCTO) {
                                        producto.setImagenProducto(uploadImage(path, item));
                                    } else if (method == MODIFY_PRODUCTO) {
                                        DtoProducto prod = ProductoFactory.newInstance();
                                        prod.setIdProducto(producto.getIdProducto());
                                        prod = this.productoBo.findById(user, prod);
                                        ImageViewHelper.deleteImageFromServer(prod.getImagenProducto());
                                        producto.setImagenProducto(uploadImage(path, item));
                                    }
                                }
                            }
                        }
                        break;
                }
                if (item.getFieldName().equals(Text.CHECK_VISIBLE)) {
                    string = item.getString();
                }
            }

            if (!StringUtils.isReallyEmptyOrNull(string)) {
                string = StringUtils.replace(string);
                String[] split = StringUtils.split(string);
                for (String strings : split) {
                    if (NumberUtils.isNumeric(strings)) {
                        producto.setIdProducto(NumberUtils.stringToNumber(strings));
                    } else {
                        producto.setVisible(BooleanUtils.isChecked(strings));
                    }
                }
            } else {
                producto.setVisible(Boolean.TRUE);
            }
        } catch (FileUploadException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_IMAGE), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_IMAGE));
        } catch (Exception ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FORM), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FORM));
        }
        return producto;
    }

    private String uploadImage(String path, FileItem item) {
        File uploadFile;
        String valor = null;
        try {
            uploadFile = File.createTempFile(Variables.PRODUCTO, "."
                    + org.springframework.util.StringUtils.getFilenameExtension(item.getName()), getPathFile(new File(path)));
            item.write(uploadFile);
            valor = uploadFile.getPath();
        } catch (IOException ex) {
            valor = null;
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_IMAGE), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_IMAGE));
        } catch (Exception ex) {
            valor = null;
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_IMAGE), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_IMAGE));
        }
        return valor;
    }

    private File getPathFile(File file) {
        Calendar date = Calendar.getInstance();
        StringBuilder path = new StringBuilder(file.getAbsolutePath());
        path.append(File.separator);
        path.append(date.get(Calendar.YEAR));
        path.append(File.separator);
        path.append(getPathDate(date.get(Calendar.MONTH) + 1));
        path.append(File.separator);
        path.append(getPathDate(date.get(Calendar.DATE)));
        path.append(File.separator);
        File newFile = new File(path.toString());
        if (!newFile.exists()) {
            newFile.mkdirs();
        }
        return newFile;
    }

    private static String getPathDate(int number) {
        return number < 9 ? "0" + number : String.valueOf(number);
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
