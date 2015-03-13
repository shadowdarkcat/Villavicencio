package mx.com.villavicencio.system.usuario.bo.impl;

import java.util.Collection;
import java.util.StringTokenizer;
import mx.com.villavicencio.commons.exception.ApplicationException;
import mx.com.villavicencio.commons.messages.ApplicationMessages;
import mx.com.villavicencio.properties.PropertiesBean;
import mx.com.villavicencio.properties.Property;
import mx.com.villavicencio.security.SecurityUtils;
import mx.com.villavicencio.system.menu.dao.MenuDao;
import mx.com.villavicencio.system.menu.dto.MenuItem;
import mx.com.villavicencio.system.usuario.bo.UsuarioBo;
import mx.com.villavicencio.system.usuario.dao.UsuarioDao;
import mx.com.villavicencio.system.usuario.dto.DtoUsuario;
import mx.com.villavicencio.utils.StringUtils;

/**
 *
 * @author Gabriel J
 */
public class UsuarioBoImpl implements UsuarioBo {

    private UsuarioDao usuarioDao;
    private MenuDao menuDao;

    @Override
    public Collection<DtoUsuario> findAll(DtoUsuario user) {
        if ((user.getIdUsuario() != 0) && (user != null)) {
            return this.usuarioDao.findAll();
        } else {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public DtoUsuario findById(DtoUsuario user, DtoUsuario object) {
        if ((user.getIdUsuario() != 0) && (user != null)) {
            return this.usuarioDao.findById(object);
        } else {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public DtoUsuario validar(DtoUsuario user) {
        String md5 = SecurityUtils.md5(user.getPassword());
        user.setPassword(md5);
        user = this.usuarioDao.validateUsuario(user);
        if (user != null) {
            user.setIsAdmin("ADMINISTRADOR".equals(user.getPuesto()) ? Boolean.TRUE : Boolean.FALSE);
            return user;
        }
        return null;
    }

    @Override
    public Boolean verifyExistUsuario(DtoUsuario user, DtoUsuario object) {
        if ((user.getIdUsuario() != 0) && (user != null)) {
            return this.usuarioDao.verifyExistUsuario(object);
        } else {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public DtoUsuario insert(DtoUsuario user, DtoUsuario object) {
        if ((user.getIdUsuario() != 0) && (user != null)) {
            return this.usuarioDao.insert(object);
        } else {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public void modificar(DtoUsuario user, DtoUsuario object) {
        if ((user.getIdUsuario() != 0) && (user != null)) {
            this.usuarioDao.modificar(object);
        } else {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public String getMenu(DtoUsuario user) {

        StringBuilder buffer = new StringBuilder();
        if (user != null) {
            Collection<MenuItem> menu = menuDao.getMenuPrivilegios(user);
            for (MenuItem item : menu) {
                if (StringUtils.isReallyEmptyOrNull(item.getLink())) {
                    buffer.append("\n<li class='dropdown'><a href='#' target='")
                            .append(item.getTarget()).append("' class='dropdown-toggle' data-toggle='dropdown'>\n").append(item.getLeyenda())
                            .append("\n<b class='caret'></b></a>\n<ul class='dropdown-menu' role='menu'>\n");
                    buffer.append(getMenuString(item));
                } else {
                    buffer.append("\n<li><a href='").append(item.getLink()).append("' target='")
                            .append(item.getTarget()).append("'>").append(item.getLeyenda())
                            .append("</a></li>\n");
                }
                if (StringUtils.isReallyEmptyOrNull(item.getLink())) {
                    buffer.append("</ul>").append("</li>");
                }
            }
        }
        buffer.append("</ul></li>");        
        return buffer.toString();
    }

    private String getMenuString(MenuItem itemA) {
        StringBuilder buffer = new StringBuilder();
        for (MenuItem item : itemA.getChildItems()) {
            if (item.getLink() == null) {
                buffer.append("\n<li class='dropdown-submenu'><a tabindex='0' data-toggle='dropdown'>").append(item.getLeyenda())
                        .append("</a>\n<ul class='dropdown-menu'>\n");
            } else {
                buffer.append("\n<li><a href='").append(item.getLink()).append("' target='")
                        .append(item.getTarget()).append("'>").append(item.getLeyenda())
                        .append("</a></li>\n");
            }
            buffer.append(getMenuString(item));
        }
        return buffer.toString();
    }
    public void setUsuarioDao(UsuarioDao usuarioDao) {
        this.usuarioDao = usuarioDao;
    }

    public void setMenuDao(MenuDao menuDao) {
        this.menuDao = menuDao;
    }
}
