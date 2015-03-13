<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <title>Nota Pedido</title>
    </head>
    <body>
        <div id="divBajaNotaPedido" title="Cancela Nota de Pedido">
            <form id="frmBajaNotaPedido" name="frmBajaNotaPedido" method="post" action="${pageContext.request.contextPath}/controller/pedidosController?method=2">
                <img src="${pageContext.request.contextPath}/image/logo.jpg" style="width: 75px; height: 75px; position: absolute; margin-top: 5px;" />
                <label class="text-muted" style="margin: 0px 0px 0px 320px;">PEDIDO </label>
                <br /><br /><br /><br /><br /><br /><br /><br />
                <input type="hidden" id="txtIdPedido" name="txtIdPedido"  readonly="readOnly" />
                <input type="hidden" id="txtIdAction" name="txtIdAction"  readonly="readOnly" value="${id}" />
                <input type="hidden" id="txtIdCliente" name="txtIdCliente" readonly="readOnly" />
                <input type="hidden" id="txtIdVendedor" name="txtIdVendedor" readonly="readOnly" value="${user.idVendedor}"/>
                <input type="hidden" id="txtIdVendedorCliente" name="txtIdVendedorCliente" readonly="readOnly" />
                <input type="hidden" id="txtEstatus" name="txtEstatus" readonly="readOnly" value="C" />
                <label class="text-muted">FECHA :</label>
                <input type="text" id="txtFecha" name="txtFecha" readonly="readOnly" />
                <br /><br />
                <label class="text-muted">FOLIO :</label> 
                <input type="text" id="txtFolio" name="txtFolio" readonly="readOnly"/>
                <br /><br/>
                <label class="text-muted">VENDEDOR :</label>
                <input type="text" id="txtVendedor" name="txtVendedor" readonly="readOnly" />
                <table id="tblNotaPedido" class="table table-bordered table-hover">
                    <thead>
                        <tr>
                            <th style="text-align: center;">Producto</th>
                            <th style="text-align: center;">Cantidad</th>                
                        </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
            </form>
        </div>

        <div id="divMessageBajaPedido" title="Advertencia">
            <table>
                <tr>
                    <td><span class="text-muted">El pedido ser&aacute; cancelado</span></td>                    
                </tr>
                <tr>
                    <td><span class="text-muted">No se podr&aacute; revertir el estado a pendiente</span></td>
                </tr>
                <tr>
                    <td><span class="text-muted">¿Desea continuar?</span></td>
                </tr>
            </table>
        </div>
    </body>
</html>