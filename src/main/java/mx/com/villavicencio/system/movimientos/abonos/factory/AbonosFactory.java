package mx.com.villavicencio.system.movimientos.abonos.factory;

import mx.com.villavicencio.system.movimientos.abonos.dto.DtoAbonos;
import mx.com.villavicencio.utils.NumberUtils;
import mx.com.villavicencio.utils.StringUtils;

/**
 *
 * @author Gabriel J
 */
public class AbonosFactory {

    public static DtoAbonos newInstance() {
        return new DtoAbonos();
    }

    public static DtoAbonos newInstance(Integer id) {
        DtoAbonos abonos = newInstance();
        abonos.setIdAbonos(id);
        return abonos;
    }

    public static DtoAbonos newInstance(String id) {
        if (!StringUtils.isReallyEmptyOrNull(id)) {
            return newInstance(NumberUtils.stringToNumber(id));
        } else {
            return null;
        }
    }
}
