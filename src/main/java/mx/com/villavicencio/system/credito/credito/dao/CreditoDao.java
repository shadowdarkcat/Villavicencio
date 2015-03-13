package mx.com.villavicencio.system.credito.credito.dao;

import mx.com.villavicencio.generics.spring.dao.GenericDao;
import mx.com.villavicencio.system.credito.credito.dto.DtoCredito;

/**
 *
 * @author Gabriel J
 */
public interface CreditoDao extends GenericDao<DtoCredito> {

    DtoCredito insert(DtoCredito object);

    DtoCredito findCreditoByIdVendedor(DtoCredito object, Integer id);
    
    DtoCredito findCreditoByIdCliente(DtoCredito object, Integer id);
}
