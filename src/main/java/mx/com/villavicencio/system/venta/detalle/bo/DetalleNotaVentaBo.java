package mx.com.villavicencio.system.venta.detalle.bo;

import java.util.Collection;
import mx.com.villavicencio.generics.spring.bo.GenericBo;
import mx.com.villavicencio.system.usuario.dto.DtoUsuario;
import mx.com.villavicencio.system.venta.detalle.dto.DtoDetalleNotaVenta;

/**
 *
 * @author Gabriel J
 */
public interface DetalleNotaVentaBo extends GenericBo<DtoUsuario, DtoDetalleNotaVenta> {

    Collection<DtoDetalleNotaVenta> findAll(DtoUsuario user, DtoDetalleNotaVenta object);
}
