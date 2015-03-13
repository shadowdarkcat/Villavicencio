package mx.com.villavicencio.system.menu.bo.impl;

import java.util.Collection;
import mx.com.villavicencio.commons.exception.ApplicationException;
import mx.com.villavicencio.commons.messages.ApplicationMessages;
import mx.com.villavicencio.properties.PropertiesBean;
import mx.com.villavicencio.properties.Property;
import mx.com.villavicencio.system.menu.bo.MenuBo;
import mx.com.villavicencio.system.menu.dao.MenuDao;
import mx.com.villavicencio.system.menu.dto.MenuItem;
import mx.com.villavicencio.system.usuario.dto.DtoUsuario;

/**
 *
 * @author Gabriel J
 */
public class MenuBoImpl implements MenuBo {

    private MenuDao menuDao;

    @Override
    public Collection<MenuItem> getMenu(DtoUsuario user) {
        if ((user.getIdUsuario() != 0) && (user != null)) {
            return this.menuDao.getMenu(user);
        } else {
            ApplicationMessages.warnMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO)
            );
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public Collection<MenuItem> getMenuPrivilegios(DtoUsuario user) {
        if ((user.getIdUsuario() != 0) && (user != null)) {
            return this.menuDao.getMenuPrivilegios(user);
        } else {
            ApplicationMessages.warnMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO)
            );
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    private Collection<MenuItem> getMenuAlmacen(DtoUsuario user) {
        if ((user.getIdUsuario() != 0) && (user != null)) {
            return this.menuDao.getMenuAlmacen(user);
        } else {
            ApplicationMessages.warnMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO)
            );
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    private Collection<MenuItem> getMenuPrivilegiosAlmacen(DtoUsuario user) {
        if ((user.getIdUsuario() != 0) && (user != null)) {
            return this.menuDao.getMenuPrivilegiosAlmacen(user);
        } else {
            ApplicationMessages.warnMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO)
            );
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public String getConvercionMenu(DtoUsuario user) {
        StringBuilder br = new StringBuilder();
        if ("ADMINISTRADOR".equals(user.getPuesto())) {
            br.append(primerRecorridoMenu(getMenu(user), getMenuPrivilegios(user)));
            br.append(primerRecorridoMenuAlmacen(getMenuAlmacen(user), getMenuPrivilegiosAlmacen(user)));
        } else if ("ALMACEN".equals(user.getPuesto().trim())) {
            br.append(primerRecorridoMenuAlmacen(getMenuAlmacen(user), getMenuPrivilegiosAlmacen(user)));
        } else {
            br.append(primerRecorridoMenu(getMenu(user), getMenuPrivilegios(user)));
        }
        return br.toString();
    }

    @Override
    public void insertMultiplesPrivilegios(DtoUsuario user, Collection<MenuItem> nuevoMenu, DtoUsuario object) {
        if ((user.getIdUsuario() != 0) && (user != null)) {
            if (!"ALMACEN".equals(object.getPuesto())) {
                this.menuDao.insertMultiplesPrivilegios(object, nuevoMenu);
            } else if ("ALMACEN".equals(object.getPuesto())) {
                this.menuDao.insertMultiplesPrivilegiosAlmacen(object, nuevoMenu);
            }
        } else {
            ApplicationMessages.warnMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public void insertPrivilegio(DtoUsuario user, MenuItem menuItem) {
        if ((user.getIdUsuario() != 0) && (user != null)) {
            this.menuDao.insertPrivilegio(user, menuItem);
        } else {
            ApplicationMessages.warnMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public void deletePrivilegios(DtoUsuario user) {
        if ((user.getIdUsuario() != 0) && (user != null)) {
            this.deletePrivilegios(user);
        } else {
            ApplicationMessages.warnMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    private String primerRecorridoMenu(Collection<MenuItem> menu, Collection<MenuItem> menuDeUsuario) {
        StringBuilder str = new StringBuilder();
        str.append("<div id='menuAdministrador' style='display: display;'>");
        str.append("<ul id='nodoPrincipal'>");
        str.append(recorridoMenu(menu, menuDeUsuario));
        str.append("</ul>");
        str.append("</div>");
        return str.toString();
    }

    private String primerRecorridoMenuAlmacen(Collection<MenuItem> menu, Collection<MenuItem> menuDeUsuario) {
        StringBuilder str = new StringBuilder();
        str.append("<div id='menuAlmacen' style='display:none;'>");
        str.append("<ul id='nodoPrincipalAlmacen'>");
        str.append(recorridoMenuAlmacen(menu, menuDeUsuario));
        str.append("</ul>");
        str.append("</div>");
        return str.toString();
    }

    private String recorridoMenu(Collection<MenuItem> menu, Collection<MenuItem> menuDeUsuario) {
        StringBuilder str = new StringBuilder();
        if (menu != null && !menu.isEmpty()) {
            for (MenuItem menuHijo : menu) {
                str.append("<li><input type='checkbox' name='idMenu' ")
                        .append(existeNodo(menuHijo, menuDeUsuario) ? "checked" : "")
                        .append(" value='")
                        .append(menuHijo.getId())
                        .append("'><label class='text-muted'>")
                        .append(menuHijo.getLeyenda())
                        .append("</label>");
                if (menuHijo.getChildItems() != null && !menuHijo.getChildItems().isEmpty()) {
                    str.append("<ul>");
                    str.append(recorridoMenu(menuHijo.getChildItems(), menuDeUsuario));
                    str.append("</ul>");
                }
                str.append("</li>");
            }
        }
        return str.toString();
    }

    private String recorridoMenuAlmacen(Collection<MenuItem> menu, Collection<MenuItem> menuDeUsuario) {
        StringBuilder str = new StringBuilder();
        if (menu != null && !menu.isEmpty()) {
            for (MenuItem menuHijo : menu) {
                str.append("<li><input type='checkbox' name='idMenu' ")
                        .append(existeNodo(menuHijo, menuDeUsuario) ? "checked" : "")
                        .append(" value='")
                        .append(menuHijo.getId())
                        .append("'><label class='text-muted'>")
                        .append(menuHijo.getLeyenda())
                        .append("</label>");
                if (menuHijo.getChildItems() != null && !menuHijo.getChildItems().isEmpty()) {
                    str.append("<ul>");
                    str.append(recorridoMenu(menuHijo.getChildItems(), menuDeUsuario));
                    str.append("</ul>");
                }
                str.append("</li>");
            }
        }
        return str.toString();
    }

    private boolean existeNodo(MenuItem nodo, Collection<MenuItem> menuDeUsuario) {
        if (menuDeUsuario.contains(nodo)) {
            return true;
        } else {
            boolean bandera = false;
            for (MenuItem menuItem : menuDeUsuario) {
                if (menuItem.getChildItems() != null && !menuItem.getChildItems().isEmpty()) {
                    if (bandera) {
                        return true;
                    } else {
                        bandera = existeNodo(nodo, menuItem.getChildItems());
                    }
                }
            }
            return bandera;
        }
    }

    public void setMenuDao(MenuDao menuDao) {
        this.menuDao = menuDao;
    }
}
