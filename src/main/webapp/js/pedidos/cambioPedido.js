var idsCambio = [];
var nombresCambio = [];
var costosCambio = [];
var comisionesCambio = [];
var pesosCambio = [];
var piezasCambio = [];
var idsDetallePedido = [];
var pesoMinimoCambio = [];
var pesoMaximoCambio = [];
var removeIdPedido;
var removeIdDetallePedido;
var idPedidoDelete;
var actionDelete;
var idClienteDelete;
var idVendedorDelete;
$(document).ready(function () {

    if ($('#divCambioPedido').length > 0) {
        $('#divCambioPedido').dialog({
            resizable: false
            , width: 850
            , height: 820
            , modal: true
            , autoOpen: false
            , closeOnEscape: false
            , dialogClass: 'no-close'
            , buttons: {
                Cerrar: function () {
                    $(this).dialog('close');
                }
            }
        });
    }

    if ($('#divDeleteProducto').length > 0) {
        $('#divDeleteProducto').dialog({
            resizable: false
            , width: 350
            , height: 200
            , modal: true
            , autoOpen: false
            , buttons: {
                Aceptar: function () {
                    removeFromNota();
                    $(this).dialog('close');
                }
                , Cerrar: function () {
                    $(this).dialog('close');
                }
            }
        });
    }

    if ($('#divCapturaDatosCmabio').length > 0) {
        $('#divCapturaDatosCmabio').dialog({
            resizable: false
            , width: 470
            , height: 320
            , modal: true
            , autoOpen: false
            , closeOnEscape: false
            , dialogClass: 'no-close'
            , buttons: {
                Agregar: function () {
                    if ($('#txtPiezas').val() == '' || $('#txtPiezas').val().replace(/s+/, '') == '') {
                        $('#divCampoVacio').dialog('open');
                    } else {
                        var idProducto = $('#divCapturaDatosCmabio').find('#txtIdProducto').val();
                        var producto = $('#divCapturaDatosCmabio').find('#txtProducto').val();
                        var cantidad = $('#divCapturaDatosCmabio').find('#txtPiezas').val();
                        var costo = $('#divCapturaDatosCmabio').find('#txtCosto').val();
                        var peso = $('#divCapturaDatosCmabio').find('#txtPeso').val();
                        var minimo = $('#divCapturaDatosCmabio').find('#txtMinimo').val();
                        var maximo = $('#divCapturaDatosCmabio').find('#txtMaximo').val();
                        var comision = $('#divCapturaDatosCmabio').find('#txtComision').val();
                        getAddCambio(idProducto, producto, cantidad, costo, peso, comision, minimo, maximo);
                        $(this).dialog('close');
                    }
                }
                , Cerrar: function () {

                    $(this).dialog('close');
                }
            }
        });
    }

    if (('#divNotaPedidoCambio').length > 0) {
        $('#divNotaPedidoCambio').dialog({
            resizable: false
            , width: 700
            , height: 650
            , modal: true
            , cache: false
            , autoOpen: false
            , buttons: {
                Aceptar: function () {
                    $('#frmNotaPedidoCambio').submit();
                }
                , Cerrar: function () {
                    $(this).dialog('close');
                }
            }
        });
    }

    $('#btnCambioPedido').on('click', function () {
        var idPedido = $('.selected').find('#txtIdPedido').val();
        var action = $('#txtIdAction').val();
        var idCliente = $('.selected').find('#txtIdCliente').val();
        var idVendedor = $('.selected').find('#txtIdVendedorCliente').val();
        var status = $('.selected').find('#txtStatus').val();
        idPedidoDelete = idPedido;
        actionDelete = action;
        idClienteDelete = idCliente;
        idVendedorDelete = idVendedor;
        if ((idPedido != null) && (idPedido != undefined)) {
            if ((status != 'C') && (status != undefined) && (status != 'S') && (status != 'E')) {
                getDataCambio(idPedido, action, idCliente, idVendedor);
                $('#divCambioPedido').dialog('open');
            } else {
                $('#divPedidoNoVisible').dialog('open');
            }
        } else {
            $('#divPedidoNoSelected').dialog('open');
        }
    });

    $('#cboClienteCambio').change(function () {
        var idCliente = $('#cboClienteCambio option:selected').val();
        $('#divNotaPedidoCambio').find('#txtIdCliente').val(idCliente);
        $.ajax({
            url: contextoGlobal + '/controller/pedidosController'
            , type: 'post'
            , dataType: 'json'
            , cache: false
            , data: {
                method: 4
                , idClientePedido: idCliente
                , idVendedorPedido: ''
                , context: contextoGlobal
                , ajax: true
            }, success: function (response) {
                var items = '';
                $('#divCambioPedido').find('#divSliderProductos').empty();
                if (response != null) {
                    var data = '<br/><div u="slides"  style="cursor: move; position: absolute; overflow: hidden; left: 0px; top: 0px; width: 600px; height: 300px;">'
                    $.each(response, function (index, item) {
                        items += '<div><center><input type="button" id="btnShowDiv" name="btnShowDiv" value="' + item.nombreProducto + '"'
                                + 'class="text-muted;" onclick="showDataClienteCambio(' + item.idProducto + ',' + idCliente + ');" />'
                                + '</span></center><br/><center><img u="caption" id="'
                                + index
                                + '" name="' + index
                                + '" src="' + item.imagenProducto + '" height="100px" width="100px" /></center>'
                                + '</div>';
                    });
                }
                $('#divCambioPedido').find('#divSliderProductos').append(data + items
                        + '</div><span u="arrowleft" class="jssora06l" style="width: 45px; height: 45px; top: 123px; left: 8px;">'
                        + '</span><span u="arrowright" class="jssora06r" style="width: 45px; height: 45px; top: 123px; right: 8px">'
                        + '</span>');
                jssor_slider1_starter("divSliderProductos");
            }
            , error: function (jqXHR, textStatus, errorThrown) {
                console.log('ERROR L: ' + textStatus + ' (' + errorThrown + ')');
            }
        });
    });

    $('#cboVendedorCambio').change(function () {
        $("cboVendedorCambio").attr('disabled', 'disabled');
        var idVendedor = $('#cboVendedorCambio option:selected').val();
        $('#divNotaPedidoCambio').find('#txtIdVendedorCliente').val(idVendedor);
        $.ajax({
            url: contextoGlobal + '/controller/pedidosController'
            , type: 'post'
            , dataType: 'json'
            , cache: false
            , data: {
                method: 4
                , idVendedorPedido: idVendedor
                , context: contextoGlobal
                , ajax: true
            }, success: function (response) {
                var items = '';
                $('#divCambioPedido').find('#divSliderProductos').empty();
                if (response != null) {
                    var data = '<br/><div u="slides"  style="cursor: move; position: absolute; overflow: hidden; left: 0px; top: 0px; width: 600px; height: 300px;">'
                    $.each(response, function (index, item) {
                        items += '<div><center><input type="button" id="btnShowDiv" name="btnShowDiv" value="' + item.nombreProducto + '"'
                                + 'class="text-muted;" onclick="showDataVendedorCambio(' + item.idProducto + ',' + idVendedor + ');" />'
                                + '</span></center><br/><center><img u="caption" id="'
                                + index
                                + '" name="' + index
                                + '" src="' + item.imagenProducto + '" height="100px" width="100px" /></center>'
                                + '</div>';
                    });
                }
                $('#divCambioPedido').find('#divSliderProductos').append(data + items
                        + '</div><span u="arrowleft" class="jssora06l" style="width: 45px; height: 45px; top: 123px; left: 8px;">'
                        + '</span><span u="arrowright" class="jssora06r" style="width: 45px; height: 45px; top: 123px; right: 8px">'
                        + '</span>');
                jssor_slider1_starter("divSliderProductos");
            }
            , error: function (jqXHR, textStatus, errorThrown) {
                console.log('ERROR L: ' + textStatus + ' (' + errorThrown + ')');
            }
        });
    });

    $('#btnFichaCambio').on('click', function () {
        showDataCambio();
        $('#divNotaPedidoCambio').dialog('open');
    });
});

function getDataCambio(idPedido, action, idCliente, idVendedor) {
    $.ajax({
        url: contextoGlobal + '/controller/pedidosController'
        , type: 'post'
        , dataType: 'json'
        , cache: false
        , data: {
            method: 6
            , txtIdPedido: idPedido
            , txtIdAction: action
            , txtIdCliente: idCliente
            , txtIdVendedorCliente: idVendedor
            , context: contextoGlobal
            , ajax: true
        }
        , success: function (response) {
            idsCambio.length = 0;
            nombresCambio.length = 0;
            costosCambio.length = 0;
            comisionesCambio.length = 0;
            pesosCambio.length = 0;
            piezasCambio.length = 0;
            idsDetallePedido.length = 0;

            $('#divNotaPedidoCambio').find('#txtFecha').val(response.fecha);
            $('#divNotaPedidoCambio').find('#txtFolio').val(response.folio);
            $('#divNotaPedidoCambio').find('#txtVendedor').val(response.nombreVendedor);
            $('#divNotaPedidoCambio').find('#txtIdPedido').val(response.idPedido);
            if ((response.cliente != null) && (response.cliente.idCliente != undefined) && (response.cliente.idCliente != 0)) {
                $('#divCambioPedido').find('input:radio[name=rdbTipoCliente][value=1]').prop('checked', true);
                $('#divCambioPedido').find('#divVendedor').hide();
                $('#divCambioPedido').find('#divCliente').show();
                $('#divCambioPedido').find('#cboClienteCambio').val(response.cliente.idCliente);
                $('#divCambioPedido').find('#cboClienteCambio').change();
            } else if ((response.vendedor != null) && (response.vendedor.idVendedor != undefined) && (response.vendedor.idVendedor != 0)) {
                $('#divCambioPedido').find('input:radio[name=rdbTipoCliente][value=2]').prop('checked', true);
                $('#divCambioPedido').find('#divCliente').hide();
                $('#divCambioPedido').find('#divVendedor').show();
                $('#divCambioPedido').find('#cboVendedorCambio').val(response.vendedor.idVendedor);
                $('#divCambioPedido').find('#cboVendedorCambio').change();
            }
            var tr = '';
            $('#carritoPedidoCambio').find('#tblPedidoCambio > tbody').empty();
            $.each(response.detalles, function (index, item) {
                tr += '<tr id="prodCambio' + item.idDetallePedido + '">'
                        + '<td style="text-align: center">'
                        + '<input type="text" id="txtArrayNombreProducto" name="txtArrayNombreProducto" value="' + item.nombreProducto + '" readOnly="readOnly" /></td>'
                        + '<td style="text-align: center">'
                        + '<input type="text" id="txtArrayPiezas" name="txtArrayPiezas" value ="' + item.cantidadPiezas + '" readOnly="readOnly" /></td>'
                        + '<td><img id="del" src="' + contextoGlobal + '/image/remove_cart.png" width="24" height="24" onClick="removeData(' + response.idPedido + ',' + item.idDetallePedido + ',\'' + item.nombreProducto + '\',\'' + response.folio + '\');"'
                        + 'title="Elimina el producto ' + item.nombreProducto + ' de la nota con folio ' + response.folio + '" />'
                        + '<input type="hidden" id="txtArrayCosto" name="txtArrayCosto" value="' + item.costoUnitario + '" readOnly="readOnly"/>'
                        + '<input type="hidden" id="txtArrayComisiones" name="txtArrayComisiones" value="' + item.comision + '" />'
                        + '<input type="hidden" id="txtArrayPeso" name="txtArrayPeso" value="' + item.peso + '" />'
                        + '<input type="hidden" id="txtArrayIdProducto" name="txtArrayIdProducto" value="' + item.idProducto + '" readOnly="readOnly"/>'
                        + '<input type="hidden" id="txtArrayPesoMinimo" name="txtArrayPesoMinimo" value="' + item.pesoMinimo + '" />'
                        + '<input type="hidden" id="txtArrayPesoMaximo" name="txtArrayPesoMaximo" value="' + item.pesoMaximo + '" readOnly="readOnly"/>'
                        + '</td></tr>';
                idsCambio.push(item.idProducto);
                nombresCambio.push(item.nombreProducto);
                costosCambio.push(item.costoUnitario);
                comisionesCambio.push(item.comision);
                pesosCambio.push(item.peso);
                piezasCambio.push(item.cantidadPiezas);
                idsDetallePedido.push(item.idDetallePedido);
                pesoMinimoCambio.push(item.pesoMinimo);
                pesoMaximoCambio.push(item.pesoMaximo);
            });
            $('#carritoPedidoCambio').find('#tblPedidoCambio > tbody').append(tr);
            $('#carritoPedidoCambio').show();
        }, error: function (jqXHR, textStatus, errorThrown) {
            console.log('ERROR L: ' + textStatus + ' (' + errorThrown + ')');
        }
    });
}

function removeData(idPedido, idDetallePedido, nombre, folio) {
    removeIdPedido = idPedido;
    removeIdDetallePedido = idDetallePedido;
    var tdProducto = '<span class="text-muted" >El producto ' + nombre + ' ser&aacute; eliminado </span>';
    var tdFolio = '<span class="text-muted" > de la nota de pedido con folio ' + folio + '</span>';
    $('#divDeleteProducto').find('#tdProducto').empty();
    $('#divDeleteProducto').find('#tdtFolio').empty();
    $('#divDeleteProducto').find('#tdProducto').append(tdProducto);
    $('#divDeleteProducto').find('#tdtFolio').append(tdFolio);
    $('#divDeleteProducto').dialog('open');
}

function quitarCambio(indexRow) {
    $("#prodCambio" + indexRow).remove();
    if ($('#tblPedidoCambio >tbody >tr').length == 0) {
        $('#carritoPedidoCambio').hide();
        idsCambio.length = 0;
        nombresCambio.length = 0;
        costosCambio.length = 0;
        comisionesCambio.length = 0;
        pesosCambio.length = 0;
        piezasCambio.length = 0;
        idsDetallePedido.length = 0;
        pesoMinimoCambio.length = 0;
        pesoMaximoCambio.length = 0;
    } else {
        idsCambio.splice(indexRow - 1, 1);
        nombresCambio.splice(indexRow - 1, 1);
        costosCambio.splice(indexRow - 1, 1);
        comisionesCambio.splice(indexRow - 1, 1);
        pesosCambio.splice(indexRow - 1, 1);
        piezasCambio.splice(indexRow - 1, 1);
        idsDetallePedido.splice(indexRow - 1, 1);
        pesoMinimoCambio.splice(indexRow - 1, 1);
        pesoMaximoCambio.splice(indexRow - 1, 1);
    }
}

function showDataClienteCambio(idProducto, idCliente) {
    $.ajax({
        url: contextoGlobal + '/controller/pedidosController'
        , type: 'post'
        , dataType: 'json'
        , context: contextoGlobal
        , cache: false
        , data: {
            method: 4
            , idClientePedido: idCliente
            , txtIdProducto: idProducto
            , context: contextoGlobal
            , ajax: true
        }, success: function (response) {
            var img = '<img src="' + response.imagenProducto + 'height="150px" width="120px"/>';
            $('#divCapturaDatosCmabio').find('#Izquierda').empty();
            $('#divCapturaDatosCmabio').find('#nombreProducto').empty();
            $('#divCapturaDatosCmabio').find('#txtPiezas').val('');
            $('#Izquierda').append(img);
            $('#divCapturaDatosCmabio').find('#nombreProducto').append(response.nombreProducto);
            $('#divCapturaDatosCmabio').find('#txtProducto').val(response.nombreProducto);
            $('#divCapturaDatosCmabio').find('#txtIdProducto').val(response.idProducto);
            $('#divCapturaDatosCmabio').find('#txtCosto').val(response.costoUnitario);
            $('#divCapturaDatosCmabio').find('#txtPeso').val(response.peso);
            $('#divCapturaDatosCmabio').find('#txtMinimo').val(response.pesoMinimo);
            $('#divCapturaDatosCmabio').find('#txtMaximo').val(response.pesoMaximo);
            $('#divCapturaDatosCmabio').find('#txtComision').val(response.comision);
            $('#divCapturaDatosCmabio').dialog('open');
        }
        , error: function (jqXHR, textStatus, errorThrown) {
            console.log('ERROR L: ' + textStatus + ' (' + errorThrown + ')');
        }
    });
}

function showDataVendedorCambio(idProducto, idVendedor) {
    $.ajax({
        url: contextoGlobal + '/controller/pedidosController'
        , type: 'post'
        , dataType: 'json'
        , context: contextoGlobal
        , cache: false
        , data: {
            method: 4
            , idVendedorPedido: idVendedor
            , txtIdProducto: idProducto
            , context: contextoGlobal
            , ajax: true
        }, success: function (response) {
            var img = '<img src="' + response.imagenProducto + 'height="150px" width="120px"/>';
            $('#divCapturaDatosCmabio').find('#Izquierda').empty();
            $('#divCapturaDatosCmabio').find('#nombreProducto').empty();
            $('#divCapturaDatosCmabio').find('#txtPiezas').val('');
            $('#Izquierda').append(img);
            $('#divCapturaDatosCmabio').find('#nombreProducto').append(response.nombreProducto);
            $('#divCapturaDatosCmabio').find('#txtProducto').val(response.nombreProducto);
            $('#divCapturaDatosCmabio').find('#txtIdProducto').val(response.idProducto);
            $('#divCapturaDatosCmabio').find('#txtCosto').val(response.costoUnitario);
            $('#divCapturaDatosCmabio').find('#txtPeso').val(response.peso);
            $('#divCapturaDatosCmabio').find('#txtMinimo').val(response.pesoMinimo);
            $('#divCapturaDatosCmabio').find('#txtMaximo').val(response.pesoMaximo);
            $('#divCapturaDatosCmabio').find('#txtComision').val(response.comision);
            $('#divCapturaDatosCmabio').dialog('open');
        }
        , error: function (jqXHR, textStatus, errorThrown) {
            console.log('ERROR L: ' + textStatus + ' (' + errorThrown + ')');
        }
    });
}

function getAddCambio(idProducto, producto, cantidad, costo, peso, comision, minimo, maximo) {
    $.ajax({
        url: contextoGlobal + '/controller/pedidosController'
        , type: 'post'
        , dataType: 'json'
        , cache: false
        , data: {
            method: 5
            , txtIdProducto: idProducto
            , txtProducto: producto
            , txtPiezas: cantidad
            , txtCosto: costo
            , txtPeso: peso
            , txtComision: comision
            , txtMinimo: minimo
            , txtMaximo: maximo
            , context: contextoGlobal
            , ajax: true
        }
        , success: function (response) {
            $('#divCapturaDatosCambio').find('#txtPiezas').empty();
            var tbody = '';
            tbody += ' <tr id="prodCambio' + response.producto.idProducto + '">'
                    + '<td style="text-align: center; width:25px;">'
                    + '<input type="text" id="txtArrayNombreProducto" name="txtArrayNombreProducto" value="' + response.producto.nombreProducto + '" readOnly="readOnly"/></td>'
                    + '<td style="text-align: center">'
                    + '<input type="text" id="txtArrayPiezas" name="txtArrayPiezas" value="' + response.producto.piezas + '" readOnly="readOnly" class="required"/></td>'
                    + '<td> <img id="del" src="' + contextoGlobal + '/image/remove_cart.png" width="24" height="24" onClick="quitarCambio(' + response.producto.idProducto + ');"'
                    + 'title="Elimina el producto ' + response.producto.nombreProducto + ' de la lista de pedido" />'
                    + '<input type="hidden" id="txtArrayCosto" name="txtArrayCosto" value="' + response.producto.costoUnitario + '" readOnly="readOnly"/>'
                    + '<input type="hidden" id="txtArrayComisiones" name="txtArrayComisiones" value="' + response.producto.comision + '" />'
                    + '<input type="hidden" id="txtArrayPeso" name="txtArrayPeso" value="' + response.producto.peso + '" />'
                    + '<input type="hidden" id="txtArrayIdProducto" name="txtArrayIdProducto" value="' + response.producto.idProducto + '" readOnly="readOnly"/>'
                    + '<input type="hidden" id="txtArrayPesoMinimo" name="txtArrayPesoMinimo" value="' + response.producto.pesoMinimo + '" />'
                    + '<input type="hidden" id="txtArrayPesoMaximo" name="txtArrayPesoMaximo" value="' + response.producto.pesoMaximo + '" readOnly="readOnly"/>'
                    + '</td></tr>';
            idsCambio.push(response.producto.idProducto);
            nombresCambio.push(response.producto.nombreProducto);
            costosCambio.push(response.producto.costoUnitario);
            comisionesCambio.push(response.producto.comision);
            pesosCambio.push(response.producto.peso);
            piezasCambio.push(response.producto.piezas);
            pesoMinimoCambio.push(response.producto.pesoMinimo);
            pesoMaximoCambio.push(response.producto.pesoMaximo);
            $('#carritoPedidoCambio').find('#tblPedidoCambio > tbody').append(tbody);
            $('#carritoPedidoCambio').show();
        }
        , error: function (jqXHR, textStatus, errorThrown) {
            console.log('ERROR L: ' + textStatus + ' (' + errorThrown + ')');
        }
    });
}

function showDataCambio() {
    $.ajax({
        url: contextoGlobal + '/controller/pedidosController'
        , type: 'post'
        , dataType: 'json'
        , cache: false
        , data: {
            method: 9
            , txtArrayIdProducto: idsCambio
            , txtArrayNombreProducto: nombresCambio
            , txtArrayPiezas: piezasCambio
            , txtArrayCosto: costosCambio
            , txtArrayPeso: pesosCambio
            , txtArrayComisiones: comisionesCambio
            , txtArrayIdDetalles: idsDetallePedido
            , txtArrayPesoMinimo: pesoMinimoCambio
            , txtArrayPesoMaximo: pesoMaximoCambio
            , ajax: true
        }
        , success: function (response) {
            $('#divNotaPedidoCambio').find('#tblNotaPedidoCambio >tbody').empty();
            var tr;
            $.each(response.detalles, function (index, item) {
                tr += '<tr><td>'
                        + '<input type="text" id="txtArrayNombreProducto[]" name="txtArrayNombreProducto[]" readOnly="readOnly" value ="' + item.nombreProducto + '"/></td>'
                        + '<td><input type="text" id="txtArrayPiezas[]" name="txtArrayPiezas[]" readOnly="readOnly" value ="' + item.cantidadPiezas + '"/>'
                        + '<input type="hidden" id="txtArrayIdProducto[]" name="txtArrayIdProducto[]" readOnly="readOnly" value ="' + item.idProducto + '"/>'
                        + '<input type="hidden" id="txtArrayCosto[]" name="txtArrayCosto[]" readOnly="readOnly" value ="' + item.costoUnitario + '"/>'
                        + '<input type="hidden" id="txtArrayPeso[]" name="txtArrayPeso[]" readOnly="readOnly" value ="' + item.peso + '"/>'
                        + '<input type="hidden" id="txtArrayComisiones[]" name="txtArrayComisiones[]" readOnly="readOnly" value ="' + item.comision + '"/>'
                        + '<input type="hidden" id="txtArrayPesoMinimo[]" name="txtArrayPesoMinimo[]" readOnly="readOnly" value ="' + item.pesoMinimo + '"/>'
                        + '<input type="hidden" id="txtArrayPesoMaximo[]" name="txtArrayPesoMaximo[]" readOnly="readOnly" value ="' + item.pesoMaximo + '"/>';
                if (item.idDetallePedido != null) {
                    tr += '<input type="hidden" id="txtArrayIdDetalles[]" name="txtArrayIdDetalles[]" readOnly="readOnly" value ="' + item.idDetallePedido + '"/>'
                }
                +'</td></tr>';
            });
            $('#divNotaPedidoCambio').find('#tblNotaPedidoCambio >tbody').append(tr);
        }
        , error: function (jqXHR, textStatus, errorThrown) {
            console.log('ERROR L: ' + textStatus + ' (' + errorThrown + ')');
        }
    });
}

function removeFromNota() {
    $.ajax({
        url: contextoGlobal + '/controller/pedidosController'
        , type: 'post'
        , dataType: 'json'
        , cache: false
        , data: {
            method: 8
            , txtIdPedido: removeIdPedido
            , txtIdDetalle: removeIdDetallePedido
            , ajax: true
        }
        , success: function (response) {
            if (response == true) {
                quitarCambio(removeIdDetallePedido);
                getDataCambio(idPedidoDelete, actionDelete, idClienteDelete, idVendedorDelete);
            }
        }, error: function (jqXHR, textStatus, errorThrown) {
            console.log('ERROR L: ' + textStatus + ' (' + errorThrown + ')');
        }
    });
}