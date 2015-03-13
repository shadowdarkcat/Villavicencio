package mx.com.villavicencio.system.liquidacion.liquidacion.factory;

import mx.com.villavicencio.system.liquidacion.liquidacion.dto.DtoLiquidacion;
import mx.com.villavicencio.utils.NumberUtils;
import mx.com.villavicencio.utils.StringUtils;

/**
 *
 * @author Gabriel J
 */
public class LiquidacionFactory {

    public static DtoLiquidacion newInstance() {
        return new DtoLiquidacion();
    }

    public static DtoLiquidacion newInstance(Integer id) {
        DtoLiquidacion liquidacion = newInstance();
        liquidacion.setIdLiquidacion(id);
        return liquidacion;
    }

    public static DtoLiquidacion newInstance(String id) {
        if (!StringUtils.isReallyEmptyOrNull(id)) {
            return newInstance(NumberUtils.stringToNumber(id));
        } else {
            return null;
        }
    }
}
