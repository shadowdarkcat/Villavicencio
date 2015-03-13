package mx.com.villavicencio.system.liquidacion.detalle.bo;

import java.util.Collection;
import mx.com.villavicencio.generics.spring.bo.GenericBo;
import mx.com.villavicencio.system.liquidacion.detalle.dto.DtoDetalleLiquidacion;
import mx.com.villavicencio.system.usuario.dto.DtoUsuario;

/**
 *
 * @author Gabriel J
 */
public interface DetalleLiquidacionBo extends GenericBo<DtoUsuario, DtoDetalleLiquidacion> {

    Collection<DtoDetalleLiquidacion> findAll(DtoUsuario user, DtoDetalleLiquidacion object);
}
