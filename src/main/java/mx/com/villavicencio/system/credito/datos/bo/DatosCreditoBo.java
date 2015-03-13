package mx.com.villavicencio.system.credito.datos.bo;

import mx.com.villavicencio.system.cliente.dto.DtoCliente;
import mx.com.villavicencio.system.credito.credito.dto.DtoCredito;
import mx.com.villavicencio.system.usuario.dto.DtoUsuario;
import mx.com.villavicencio.system.vendedor.dto.DtoVendedor;

/**
 *
 * @author Gabriel J
 */
public interface DatosCreditoBo {

    void insertDatosCredito(DtoUsuario user, DtoCliente cliente, DtoVendedor vendedor, DtoCredito credito);
    Integer findDatosCreditoById(DtoUsuario user, DtoCliente cliente, DtoVendedor vendedor);
}
