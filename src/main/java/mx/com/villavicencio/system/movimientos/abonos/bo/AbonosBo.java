package mx.com.villavicencio.system.movimientos.abonos.bo;

import java.util.Collection;
import mx.com.villavicencio.generics.spring.bo.GenericBo;
import mx.com.villavicencio.system.cliente.dto.DtoCliente;
import mx.com.villavicencio.system.movimientos.abonos.dto.DtoAbonos;
import mx.com.villavicencio.system.usuario.dto.DtoUsuario;
import mx.com.villavicencio.system.vendedor.dto.DtoVendedor;

/**
 *
 * @author Gabriel J
 */
public interface AbonosBo extends GenericBo<DtoUsuario, DtoAbonos> {

    Collection<DtoCliente> findAllClientes(DtoUsuario user);

    Collection<DtoVendedor> findAllVendedores(DtoUsuario user);
    
    DtoVendedor findByIdVendedor(DtoUsuario user, DtoVendedor object);

    DtoCliente findByIdCliente(DtoUsuario user, DtoCliente object);
    
    void ingresar(DtoUsuario user, DtoAbonos object, String idNotaVenta);
}
