var arrayProductos = [];
var arrayPiezas = [];
var arrayKilos = [];
var arrayPiezasDevolucion = [];
var arrayKilosDevolucion = [];
var arrayTotalKilos = [];
var arrayCostoUnitario = [];
var arraySubtotal = [];
var arrayObservaciones = [];
$(document).ready(function () {
    $('#divAltaLiquidacion').find('#txtFecha').datepicker();
    $('#divAltaLiquidacion').find('#txtFecha').datepicker("setDate", new Date);
    if ($('#divAltaLiquidacion').length > 0) {
        $('#divAltaLiquidacion').dialog({
            resizable: false
            , width: 1280
            , height: 900
            , modal: true
            , cache: false
            , autoOpen: false
            , closeOnEscape: false
            , dialogClass: 'no-close'
            , buttons: {
                Registrar: function () {
                    guardarLiquidacion();
                }
                , Cerrar: function () {
                    $(this).dialog('close');
                }
            }
        });
    }
});
function generarLiquidacion(idNotaVenta) {
    $.ajax({
        url: contextoGlobal + '/controller/liquidacionController'
        , type: 'post'
        , dataType: 'json'
        , cache: false
        , data: {
            method: 1
            , txtIdNotaVenta: idNotaVenta
            , ajax: true
        }
        , success: function (response) {
            limpiar();
            var tbody = '';
            var indice = 0;
            var total = 0;
            $('#divAltaLiquidacion').find('#tblLiquidacion >tbody').empty();
            $('#divAltaLiquidacion').find('#txtVendedor').val(response.nombreCliente);
            $('#divAltaLiquidacion').find('#txtFolio').val(response.folio);
            $('#divAltaLiquidacion').find('#txtIdNotaVenta').val(idNotaVenta);
            $.each(response.detalles, function (index, item) {
                if (item.observaciones != null) {
                    $('#divAltaLiquidacion').find('#lblObservacionesHead').show();
                }
                tbody += '<tr>'
                        + '<td>' + item.nombreProducto + '</td>'
                        + '<td>' + item.cantidadPiezas + '</td>'
                        + '<td>' + item.cantidadKilos.toFixed(2) + '</td>';
                if (response.movimiento.devoluciones.detalles.length > 0) {
                    $.each(response.movimiento.devoluciones.detalles, function (index1, items) {
                        if (index1 == index) {
                            var totalKilos = parseFloat(item.cantidadKilos.toFixed(2)) - parseFloat(items.cantidadKilos.toFixed(2));
                            var subtotal = totalKilos * (item.costoUnitario.toFixed(2));
                            tbody += '<td>' + items.cantidadPiezas + '</td>'
                                    + '<td>' + items.cantidadKilos.toFixed(2) + '</td>'
                                    + '<td>' + totalKilos.toFixed(2) + '</td>'
                                    + '<td>' + formato_numero(items.costo, 2, '.', ',', '$ ') + '</td>'
                                    + '<td>' + formato_numero(subtotal, 2, '.', ',', '$ ') + '</td>'
                                    + '<td>' + (item.observaciones != null ? item.observaciones : '') + '</td>'
                                    + '</tr>';
                            total += subtotal;
                            indice = index1;
                            arrayPiezasDevolucion.push(items.cantidadPiezas);
                            arrayKilosDevolucion.push(items.cantidadKilos);
                            arrayTotalKilos.push(totalKilos);
                            arrayCostoUnitario.push(items.costo);
                            arraySubtotal.push(subtotal);
                            return;
                        }
                    });
                    if (index != indice) {
                        tbody += '<td>0</td>'
                                + '<td>0.00</td>'
                                + '<td>' + item.cantidadKilos.toFixed(2) + '</td>'
                                + '<td>' + formato_numero(item.costoUnitario, 2, '.', ',', '$ ') + '</td>'
                                + '<td>' + formato_numero(item.subTotal, 2, '.', ',', '$ ') + '</td>'
                                + '<td>' + (item.observaciones != null ? item.observaciones : '') + '</td>'
                                + '</tr>';
                        total += item.subTotal;
                    }
                } else {
                    tbody += '<td>0</td>'
                            + '<td>0.00</td>'
                            + '<td>' + item.cantidadKilos.toFixed(2) + '</td>'
                            + '<td>' + formato_numero(item.costoUnitario, 2, '.', ',', '$ ') + '</td>'
                            + '<td>' + formato_numero(item.subTotal, 2, '.', ',', '$ ') + '</td>'
                            + '<td>' + (item.observaciones != null ? item.observaciones : '') + '</td>'
                            + '</tr>';
                    total += parseFloat(item.subTotal);
                }
                arrayProductos.push(item.nombreProducto);
                arrayPiezas.push(item.cantidadPiezas);
                arrayKilos.push(item.cantidadKilos);                
                arrayCostoUnitario.push(item.costoUnitario);
                arraySubtotal.push(item.subTotal);
                arrayObservaciones.push(item.observaciones);
            });
            $('#divAltaLiquidacion').find('#txtTotal').val(total.toFixed(2));
            $('#divAltaLiquidacion').find('#txtTotal').priceFormat({
                prefix: '$ '
            });
            $('#divAltaLiquidacion').find('#tblLiquidacion >tbody').append(tbody);
            $('#divAltaLiquidacion').find('#lblObservaciones').text(response.observaciones);
            $.ajax({
                url: contextoGlobal + '/controller/liquidacionController'
                , type: 'post'
                , dataType: 'json'
                , cache: false
                , data: {
                    method: 2
                    , txtTotal: total
                    , ajax: true
                }
                , success: function (response) {
                    $('#divAltaLiquidacion').find('#lblCantidadLetra').text(response);
                }
                , error: function (jqXHR, textStatus, errorThrown) {
                    console.log('ERROR L: ' + textStatus + ' (' + errorThrown + ')');
                }
            });
            $('#divAltaLiquidacion').find('#lblCantidad').text(total.toFixed(2));
            $('#divAltaLiquidacion').find('#lblCantidad').priceFormat({
                prefix: '$ '
            });
            $('#divAltaLiquidacion').dialog('open');
        }
        , error: function (jqXHR, textStatus, errorThrown) {
            console.log("ERROR L: " + textStatus + " (" + errorThrown + ")");
        }
    });
}

function guardarLiquidacion() {
    $.ajax({
        url: contextoGlobal + '/controller/liquidacionController'
        , type: 'post'
        , dataType: 'json'
        , cache: false
        , data: {
            method: 3
            , txtFolio: $('#divAltaLiquidacion').find('#txtFolio').val()
            , txtFecha: $('#divAltaLiquidacion').find('#txtFecha').val()
            , txtVendedor: $('#divAltaLiquidacion').find('#txtVendedor').val()
            , txtObservaciones: $('#divAltaLiquidacion').find('#lblObservaciones').text()
            , txtArrayNombreProducto: arrayProductos
            , txtArrayPiezas: arrayPiezas
            , txtArrayKilos: arrayKilos
            , txtArrayPiezasDevolucion: arrayPiezasDevolucion
            , txtArrayKilosDevolucion: arrayKilosDevolucion
            , txtArrayKilosTotal: arrayTotalKilos
            , txtArrayCosto: arrayCostoUnitario
            , txtArraySubtotal: arraySubtotal
            , txtArrayObservaciones: arrayObservaciones
            , statusNotaVenta: $('#divAltaLiquidacion').find('#statusNotaVenta').val()
            , txtIdNotaVenta: $('#divAltaLiquidacion').find('#txtIdNotaVenta').val()
            , ajax: true
        }
        , success: function (response) {
            if (response == true) {
                $('#divAltaLiquidacion').dialog('close');
                getDataCliente($('#divAltaLiquidacion').find('#txtIdCliente').val());
                getDataVendedor($('#divAltaLiquidacion').find('#txtIdVendedor').val());
            }
        }
        , error: function (jqXHR, textStatus, errorThrown) {
            console.log('ERROR L: ' + textStatus + ' (' + errorThrown + ')');
        }
    });
}

function limpiar() {
    arrayProductos.length = 0;
    arrayPiezas.length = 0;
    arrayKilos.length = 0;
    arrayPiezasDevolucion.length = 0;
    arrayKilosDevolucion.length = 0;
    arrayTotalKilos.length = 0;
    arrayCostoUnitario.length = 0;
    arraySubtotal.length = 0;
    arrayObservaciones.length = 0;
}