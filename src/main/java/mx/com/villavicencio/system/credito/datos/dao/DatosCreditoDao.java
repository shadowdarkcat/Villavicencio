package mx.com.villavicencio.system.credito.datos.dao;

import mx.com.villavicencio.system.cliente.dto.DtoCliente;
import mx.com.villavicencio.system.credito.credito.dto.DtoCredito;
import mx.com.villavicencio.system.vendedor.dto.DtoVendedor;

/**
 *
 * @author Gabriel J
 */
public interface DatosCreditoDao {

    void insertDatosCredito(DtoCliente cliente, DtoVendedor vendedor, DtoCredito credito);

    Integer findDatosCreditoById (DtoCliente cliente, DtoVendedor vendedor);
}
