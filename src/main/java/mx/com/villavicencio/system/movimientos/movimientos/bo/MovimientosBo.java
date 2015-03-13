package mx.com.villavicencio.system.movimientos.movimientos.bo;

import java.util.Collection;
import mx.com.villavicencio.generics.spring.bo.GenericBo;
import mx.com.villavicencio.system.movimientos.movimientos.dto.DtoMovimientos;
import mx.com.villavicencio.system.usuario.dto.DtoUsuario;

/**
 *
 * @author Gabriel J
 */
public interface MovimientosBo extends GenericBo<DtoUsuario, DtoMovimientos> {

    Collection<DtoMovimientos> findAll(DtoUsuario user, DtoMovimientos object);
}
