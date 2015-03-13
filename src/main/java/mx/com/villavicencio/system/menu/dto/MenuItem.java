package mx.com.villavicencio.system.menu.dto;

import java.util.Collection;

/**
 *
 * @author Gabriel J
 */
public class MenuItem {

    private MenuItem parentItem;
    private Collection<MenuItem> childItems;
    private Integer id;
    private String link;
    private String leyenda;
    private String toolTip;
    private String target;

    public MenuItem getParentItem() {
        return parentItem;
    }

    public void setParentItem(MenuItem parentItem) {
        this.parentItem = parentItem;
    }

    public Collection<MenuItem> getChildItems() {
        return childItems;
    }

    public void setChildItems(Collection<MenuItem> childItems) {
        this.childItems = childItems;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLeyenda() {
        return leyenda;
    }

    public void setLeyenda(String leyenda) {
        this.leyenda = leyenda;
    }

    public String getToolTip() {
        return toolTip;
    }

    public void setToolTip(String toolTip) {
        this.toolTip = toolTip;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

}
