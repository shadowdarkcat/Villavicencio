package mx.com.villavicencio.system.productos.inventario.factory;

import mx.com.villavicencio.system.productos.inventario.dto.DtoInventario;
import mx.com.villavicencio.utils.NumberUtils;
import mx.com.villavicencio.utils.StringUtils;

/**
 *
 * @author Gabriel J
 */
public class InventarioFactory {

    public static DtoInventario newInstance() {
        return new DtoInventario();
    }

    public static DtoInventario newInstance(Integer id) {
        DtoInventario inventario = newInstance();
        inventario.setIdInventario(id);
        return inventario;
    }

    public static DtoInventario newInstance(String id) {
        if (!StringUtils.isReallyEmptyOrNull(id)) {
            return newInstance(NumberUtils.stringToNumber(id));
        } else {
            return null;
        }
    }
}
