$(document).ready(function () {

    if ($('#divPedidoNoSelected').length > 0) {
        $('#divPedidoNoSelected').dialog({
            resizable: false
            , width: 300
            , height: 150
            , autoOpen: false
            , cache: false
            , modal: true
            , buttons: {
                Cerrar: function () {
                    $(this).dialog('close');
                }
            }
        });
    }

    if ($('#divPedidoNoVisible').length > 0) {
        $('#divPedidoNoVisible').dialog({
            resizable: false
            , width: 300
            , height: 200
            , autoOpen: false
            , cache: false
            , modal: true
            , buttons: {
                Cerrar: function () {
                    $(this).dialog('close');
                }
            }
        });
    }

    if (('#divBajaNotaPedido').length > 0) {
        $('#divBajaNotaPedido').dialog({
            resizable: false
            , width: 700
            , height: 650
            , modal: true
            , cache: false
            , autoOpen: false
            , buttons: {
                Aceptar: function () {
                    $('#divMessageBajaPedido').dialog('open');
                }
                , Cerrar: function () {
                    $(this).dialog('close');
                }
            }
        });
    }

    if ($('#divMessageBajaPedido').length > 0) {
        $('#divMessageBajaPedido').dialog({
            resizable: false
            , width: 280
            , height: 200
            , modal: true
            , autoOpen: false
            , buttons: {
                Aceptar: function () {
                    $('#frmBajaNotaPedido').submit();
                }
                , Cerrar: function () {
                    $(this).dialog('close');
                }
            }
        });
    }

    $('#btnBajaPedido').on('click', function () {
        var idPedido = $('.selected').find('#txtIdPedido').val();
        var action = $('#txtIdAction').val();
        var idCliente = $('.selected').find('#txtIdCliente').val();
        var idVendedor = $('.selected').find('#txtIdVendedorCliente').val();
        var status = $('.selected').find('#txtStatus').val();
        if ((idPedido != null) && (idPedido != undefined)) {
            if ((status != 'C') && (status != undefined) && (status != 'S') && (status != 'E')) {
                getDataBaja(idPedido, action, idCliente, idVendedor);
                $('#divBajaNotaPedido').dialog('open');
            } else {
                $('#divPedidoNoVisible').dialog('open');
            }
        } else {
            $('#divPedidoNoSelected').dialog('open');
        }
    });
});

function getDataBaja(id, action, idCliente, idVendedor) {
    $.ajax({
        url: contextoGlobal + '/controller/pedidosController'
        , type: 'post'
        , dataType: 'json'
        , cache: false
        , data: {
            method: 6
            , txtIdPedido: id
            , txtIdAction: action
            , txtIdCliente: idCliente
            , txtIdVendedorCliente: idVendedor
            , context: contextoGlobal
            , ajax: true
        }
        , success: function (response) {
            $('#divBajaNotaPedido').find('#tblNotaPedido >tbody').empty();
            $('#divBajaNotaPedido').find('#txtIdPedido').val(response.idPedido);
            $('#divBajaNotaPedido').find('#txtFolio').val(response.folio);
            $('#divBajaNotaPedido').find('#txtIdAction').val(action);
            $('#divBajaNotaPedido').find('#txtIdCliente').val(response.cliente.idCliente);
            $('#divBajaNotaPedido').find('#txtIdVendedorCliente').val(response.vendedor.idVendedor);
            $('#divBajaNotaPedido').find('#txtFecha').val(response.fecha);
            $('#divBajaNotaPedido').find('#txtFolio').val(response.folio);
            $('#divBajaNotaPedido').find('#txtVendedor').val(response.nombreVendedor);
            var tr;
            $.each(response.detalles, function (index, item) {
                tr += '<tr><td>'
                        + '<input type="text" id="txtArrayNombreProducto[]" name="txtArrayNombreProducto[]" readOnly="readOnly" value ="' + item.nombreProducto + '"/></td>'
                        + '<td><input type="text" id="txtArrayPiezas[]" name="txtArrayPiezas[]" readOnly="readOnly" value ="' + item.cantidadPiezas + '"/>'
                        + '<input type="hidden" id="txtArrayIdProducto[]" name="txtArrayIdProducto[]" readOnly="readOnly" value ="' + item.idProducto + '"/>'
                        + '<input type="hidden" id="txtArrayCosto[]" name="txtArrayCosto[]" readOnly="readOnly" value ="' + item.costoUnitario + '"/>'
                        + '<input type="hidden" id="txtArrayPeso[]" name="txtArrayPeso[]" readOnly="readOnly" value ="' + item.peso + '"/>'
                        + '<input type="hidden" id="txtArrayComisiones[]" name="txtArrayComisiones[]" readOnly="readOnly" value ="' + item.comision + '"/>'
                        + '</td></tr>';
            });
            $('#divBajaNotaPedido').find('#tblNotaPedido >tbody').append(tr);
        }, error: function (jqXHR, textStatus, errorThrown) {
            console.log('ERROR L: ' + textStatus + ' (' + errorThrown + ')');
        }
    });
}