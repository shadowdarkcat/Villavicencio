package mx.com.villavicencio.system.liquidacion.detalle.factory;

import mx.com.villavicencio.system.liquidacion.detalle.dto.DtoDetalleLiquidacion;
import mx.com.villavicencio.utils.NumberUtils;
import mx.com.villavicencio.utils.StringUtils;

/**
 *
 * @author Gabriel J
 */
public class DetalleLiquidacionFactory {

    public static DtoDetalleLiquidacion newInstance() {
        return new DtoDetalleLiquidacion();
    }

    public static DtoDetalleLiquidacion newInstance(Integer id) {
        DtoDetalleLiquidacion detalleLiquidacion = newInstance();
        detalleLiquidacion.setIdDetalleLiquidacion(id);
        return detalleLiquidacion;
    }

    public static DtoDetalleLiquidacion newInstance(String id) {
        if (!StringUtils.isReallyEmptyOrNull(id)) {
            return newInstance(NumberUtils.stringToNumber(id));
        } else {
            return null;
        }
    }
}
