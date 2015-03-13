package mx.com.villavicencio.system.pedido.datos.bo;

import java.util.Collection;
import mx.com.villavicencio.generics.spring.bo.GenericBo;
import mx.com.villavicencio.system.pedido.datos.dto.DtoDatosPedido;
import mx.com.villavicencio.system.usuario.dto.DtoUsuario;

/**
 *
 * @author Gabriel J
 */
public interface DatosPedidoBo extends GenericBo<DtoUsuario, DtoDatosPedido> {

    Collection<DtoDatosPedido> findDatosById(DtoUsuario user, DtoDatosPedido object);
}
