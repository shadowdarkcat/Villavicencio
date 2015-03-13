package mx.com.villavicencio.system.venta.nota.factory;

import mx.com.villavicencio.system.venta.nota.dto.DtoNotaVenta;
import mx.com.villavicencio.utils.NumberUtils;
import mx.com.villavicencio.utils.StringUtils;

/**
 *
 * @author Gabriel J
 */
public class NotaVentaFactory {

    public static DtoNotaVenta newInstance() {
        return new DtoNotaVenta();
    }

    public static DtoNotaVenta newInstance(Integer id) {
        DtoNotaVenta notaVenta = newInstance();
        notaVenta.setIdNotaVenta(id);
        return notaVenta;
    }

    public static DtoNotaVenta newInstance(String id) {
        if (!StringUtils.isReallyEmptyOrNull(id)) {
            return newInstance(NumberUtils.stringToNumber(id));
        } else {
            return null;
        }
    }
}
