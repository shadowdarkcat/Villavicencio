package mx.com.villavicencio.system.movimientos.bancos.factory;

import mx.com.villavicencio.system.movimientos.bancos.dto.DtoBancos;
import mx.com.villavicencio.utils.NumberUtils;
import mx.com.villavicencio.utils.StringUtils;

/**
 *
 * @author Gabriel J
 */
public class BancosFactory {

    public static DtoBancos newInstance() {
        return new DtoBancos();
    }

    public static DtoBancos newInstance(Integer id) {
        DtoBancos bancos = newInstance();
        bancos.setIdBancos(id);
        return bancos;
    }

    public static DtoBancos newInstance(String id) {
        if (!StringUtils.isReallyEmptyOrNull(id)) {
            return newInstance(NumberUtils.stringToNumber(id));
        } else {
            return null;
        }
    }
}
