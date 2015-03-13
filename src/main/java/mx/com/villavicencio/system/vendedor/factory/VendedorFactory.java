package mx.com.villavicencio.system.vendedor.factory;

import mx.com.villavicencio.system.vendedor.dto.DtoVendedor;
import mx.com.villavicencio.utils.NumberUtils;
import mx.com.villavicencio.utils.StringUtils;

/**
 *
 * @author Gabriel J
 */
public class VendedorFactory {

    public static DtoVendedor newInstance() {
        return new DtoVendedor();
    }

    public static DtoVendedor newInstance(Integer id) {
        DtoVendedor vendedor = newInstance();
        vendedor.setIdVendedor(id);
        return vendedor;
    }

    public static DtoVendedor newInstance(String id) {
        if (!StringUtils.isReallyEmptyOrNull(id)) {
            return newInstance(NumberUtils.stringToNumber(id));
        } else {
            return null;
        }
    }
}
