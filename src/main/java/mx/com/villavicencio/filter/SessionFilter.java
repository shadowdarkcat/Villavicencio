
package mx.com.villavicencio.filter;

import java.io.IOException;
import java.util.Properties;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import mx.com.villavicencio.commons.exception.ApplicationException;
import mx.com.villavicencio.commons.messages.ApplicationMessages;
import mx.com.villavicencio.generics.dispatcher.Dispatcher;
import mx.com.villavicencio.generics.variables.Variables;
import mx.com.villavicencio.properties.Property;
import mx.com.villavicencio.properties.ReadProperties;
import mx.com.villavicencio.system.usuario.dto.DtoUsuario;

/**
 *
 * @author Gabriel J
 */
public class SessionFilter implements Filter {

    private FilterConfig filterConfig = null;
    private final Properties errorFile;

    public SessionFilter() {
        this.errorFile = ReadProperties.getPropertiesFromFile(Property.ERROR_FILE);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        RequestDispatcher dispatcher;
        try {
            HttpSession session = ((HttpServletRequest) request).getSession(false);
            if (session == null) {
                ApplicationMessages.errorMessage(errorFile.getProperty(Property.ERROR_TIME_OUT_SESSION));
                throw new ApplicationException(errorFile.getProperty(Property.ERROR_TIME_OUT_SESSION));
            } else {
                DtoUsuario user = (DtoUsuario) session.getAttribute(Variables.USER);
                if (user == null) {
                    ApplicationMessages.errorMessage(errorFile.getProperty(Property.ERROR_CREATE_SESSION));
                    throw new ApplicationException(errorFile.getProperty(Property.ERROR_CREATE_SESSION));
                }
            }
            chain.doFilter(request, response);
        } catch (IOException | ServletException ex) {
            dispatcher = filterConfig.getServletContext().getRequestDispatcher(Dispatcher.HANDLER_EXCEPTION);
            request.setAttribute(Variables.EXCEPTION, ex);
            dispatcher.forward(request, response);
        } catch (ApplicationException ex) {
            dispatcher = filterConfig.getServletContext().getRequestDispatcher(Dispatcher.REDIRECT_LOGIN);
            request.setAttribute(Variables.EXCEPTION, ex);
            dispatcher.forward(request, response);
        } catch (Exception ex) {
            dispatcher = filterConfig.getServletContext().getRequestDispatcher(Dispatcher.HANDLER_EXCEPTION);
            request.setAttribute(Variables.EXCEPTION, ex);
            dispatcher.forward(request, response);
        }
    }

    @Override
    public void destroy() {
        this.filterConfig = null;
    }
}
