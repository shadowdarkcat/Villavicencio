package mx.com.villavicencio.system.credito.credito.bo;

import mx.com.villavicencio.generics.spring.bo.GenericBo;
import mx.com.villavicencio.system.credito.credito.dto.DtoCredito;
import mx.com.villavicencio.system.usuario.dto.DtoUsuario;

/**
 *
 * @author Gabriel J
 */
public interface CreditoBo extends GenericBo<DtoUsuario, DtoCredito> {

    DtoCredito insert(DtoUsuario user, DtoCredito object);

    DtoCredito findCreditoByIdVendedor(DtoUsuario user, DtoCredito object, Integer id);
    
    DtoCredito findCreditoByIdCliente(DtoUsuario user, DtoCredito object, Integer id);
}
