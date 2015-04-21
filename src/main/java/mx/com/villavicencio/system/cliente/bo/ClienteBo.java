package mx.com.villavicencio.system.cliente.bo;

import java.util.Collection;
import mx.com.villavicencio.generics.spring.bo.GenericBo;
import mx.com.villavicencio.system.cliente.dto.DtoCliente;
import mx.com.villavicencio.system.usuario.dto.DtoUsuario;

/**
 *
 * @author Gabriel J
 */
public interface ClienteBo extends GenericBo<DtoUsuario, DtoCliente> {

    Boolean verifyExistCliente(DtoUsuario user, DtoCliente object);

    Collection<DtoCliente> getDataCp(DtoUsuario user, DtoCliente object);

    Collection<DtoCliente> findByIdVendedor(DtoUsuario user);

    DtoCliente findClienteReporteById(DtoUsuario user, DtoCliente object);

    Collection<DtoCliente> findAllClienteReporte(DtoUsuario user);
}
