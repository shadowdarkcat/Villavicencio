package mx.com.villavicencio.system.pedido.pedido.bo;

import java.util.Collection;
import mx.com.villavicencio.generics.spring.bo.GenericBo;
import mx.com.villavicencio.system.pedido.pedido.dto.DtoPedido;
import mx.com.villavicencio.system.usuario.dto.DtoUsuario;

/**
 *
 * @author Gabriel J
 */
public interface PedidoBo extends GenericBo<DtoUsuario, DtoPedido> {

    DtoPedido createFolio(DtoUsuario user);    
    
    void deleteProducto(DtoUsuario user, DtoPedido object);
    
    DtoPedido findPedidoReporteById(DtoUsuario user, DtoPedido object);

    Collection<DtoPedido> findAllPedidoReporte(DtoUsuario user);
}
