package mx.com.villavicencio.system.venta.devoluciones.devoluciones.factory;

import mx.com.villavicencio.system.venta.devoluciones.devoluciones.dto.DtoDevoluciones;
import mx.com.villavicencio.utils.NumberUtils;
import mx.com.villavicencio.utils.StringUtils;

/**
 *
 * @author Gabriel J
 */
public class DevolucionesFactory {

    public static DtoDevoluciones newInstance() {
        return new DtoDevoluciones();
    }

    public static DtoDevoluciones newInstance(Integer id) {
        DtoDevoluciones devoluciones = newInstance();
        devoluciones.setIdDevoluciones(id);
        return devoluciones;
    }

    public static DtoDevoluciones newInstance(String id) {
        if (!StringUtils.isReallyEmptyOrNull(id)) {
            return newInstance(NumberUtils.stringToNumber(id));
        } else {
            return null;
        }
    }
}
