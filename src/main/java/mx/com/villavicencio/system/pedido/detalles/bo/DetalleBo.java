package mx.com.villavicencio.system.pedido.detalles.bo;

import java.util.Collection;
import mx.com.villavicencio.generics.spring.bo.GenericBo;
import mx.com.villavicencio.system.pedido.detalles.dto.DtoDetallePedido;
import mx.com.villavicencio.system.usuario.dto.DtoUsuario;

/**
 *
 * @author Gabriel J
 */
public interface DetalleBo extends GenericBo<DtoUsuario, DtoDetallePedido> {

    DtoDetallePedido insert(DtoUsuario user, DtoDetallePedido object);
}
