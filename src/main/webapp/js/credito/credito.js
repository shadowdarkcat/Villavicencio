$(document).ready(function () {
    $.datepicker.setDefaults($.datepicker.regional['es-MX']);
    $("#txtFechaRegistro").datepicker({
        minDate: new Date
        , maxDate: new Date
    });

    $.datepicker.setDefaults($.datepicker.regional['es-MX']);
    $("#txtFechaPago").datepicker({
        changeMonth: true
        , changeYear: true
        , minDate: new Date
        , maxDate: "+1Y"

    });
    if ($('#divCreditoPlazo').length > 0) {
        $('#divCreditoPlazo').dialog({
            resizable: false
            , width: 550
            , height: 450
            , autoOpen: false
            , cache: false
            , modal: true
            , buttons: {
                Aceptar: function () {
                    if (validarFecha()) {
                        enviarCreditoPlazo();
                    }
                }
                , Cerrar: function () {
                    $(this).dialog('close');
                }
            }
        });
    }

    if ($('#divCreditoMonetario').length > 0) {
        $('#divCreditoMonetario').dialog({
            resizable: false
            , width: 550
            , height: 450
            , autoOpen: false
            , cache: false
            , modal: true
            , buttons: {
                Aceptar: function () {
                    enviarCreditoMonetario();
                }
                , Cerrar: function () {
                    $(this).dialog('close');
                }
            }
        });
    }

    if ($('#divCreditoContraNota').length > 0) {
        $('#divCreditoContraNota').dialog({
            resizable: false
            , width: 550
            , height: 450
            , autoOpen: false
            , cache: false
            , modal: true
            , buttons: {
                Aceptar: function () {
                    if (validarFecha()) {
                        enviarCreditoPlazo();
                    }
                }
                , Cerrar: function () {
                    $(this).dialog('close');
                }
            }
        });
    }

});
function validarFecha() {

    var fechaRegistro = $('#txtFechaRegistro').val();
    var fechaPago = $('#txtFechaPago').val();
    if ((fechaRegistro.length == 0 || /^\s+$/.test(fechaRegistro)) && (fechaPago.length == 0 || /^\s+$/.test(fechaPago))) {
        $('#lblErrorFechaRegistro').show();
        $('#lblErrorFechaPago').show();
        return false;
    } else if (fechaRegistro.length == 0 || /^\s+$/.test(fechaRegistro)) {
        $('#lblErrorFechaRegistro').show();
        $('#lblErrorFechaPago').hide();
        return false;
    } else if (fechaPago.length == 0 || /^\s+$/.test(fechaPago)) {
        $('#lblErrorFechaPago').show();
        $('#lblErrorFechaRegistro').hide();
        return false;
    } else if (fechaRegistro == fechaPago) {
        $('#lblErrorFechasIguales').show();
    } else {
        $('#lblErrorFechaRegistro').hide();
        $('#lblErrorFechaPago').hide();
        $('#lblErrorFechasIguales').hide();
    }
    return true;
}

function creditoExcedido() {
    var cargo = $('#lblTotal').text().substring(1, ($('#lblCargo').text().length));
    cargo = cargo.replace(',', '');
    cargo = cargo.replace('.', '');
    var disponible = $('#lblLimiteCredito').text().substring(1, ($('#lblLimiteCredito').text().length));
    disponible = disponible.replace(',', '');
    disponible = disponible.replace('.', '');
    var rest = disponible - cargo;
    if (rest < 0) {
        $('#lblErrorExcedeCredito').show();
    } else {
        $('#lblErrorExcedeCredito').hide();
    }
    $('#txtCreditoDisponible').val(rest);
    $('#lblDisponible').text(rest);
    $('#lblDisponible').priceFormat({
        prefix: '$ '
    });
    $('#divCreditoMonetario').find('#txtCreditoDisponible').val(rest);
    $('#divCreditoMonetario').find('#lblDisponible').text(rest);
    $('#divCreditoMonetario').find('#lblDisponible').priceFormat({
        prefix: '$ '
    });

}
function aplicarCredito(tipoCredito, folioNota, idCredito, idNotaVenta) {
    var fecha = new Date();

    if (tipoCredito == 'PLAZO') {
        $('#divCreditoPlazo').find('#txtFolio').val(folioNota);
        $('#divCreditoPlazo').find('#txtIdCredito').val(idCredito);
        $('#divCreditoPlazo').find('#txtTipoCredito').val(tipoCredito);
        $('#divCreditoPlazo').find('#txtIdNotaVenta').val(idNotaVenta);
        creditoExcedido();
        $('#divCreditoPlazo').dialog('open');
    } else if (tipoCredito == 'MONETARIO') {
        $('#divCreditoMonetario').find('#txtFolio').val(folioNota);
        $('#divCreditoMonetario').find('#txtIdCredito').val(idCredito);
        $('#divCreditoMonetario').find('#txtTipoCredito').val(tipoCredito);
        $('#divCreditoMonetario').find('#txtIdNotaVenta').val(idNotaVenta);
        $('#divCreditoMonetario').find("#txtFechaRegistro").val(fecha.getDate() + "/" + (fecha.getMonth() + 1) + "/" + fecha.getFullYear());
        creditoExcedido();
        $('#divCreditoMonetario').dialog('open');
    } else if (tipoCredito == 'CONTRANOTA') {
        $('#divCreditoContraNota').find('#txtFolio').val(folioNota);
        $('#divCreditoContraNota').find('#txtIdCredito').val(idCredito);
        $('#divCreditoContraNota').find('#txtTipoCredito').val(tipoCredito);
        $('#divCreditoContraNota').find('#txtIdNotaVenta').val(idNotaVenta);
        $('#divCreditoContraNota').find("#txtFechaRegistro").val(fecha.getDate() + "/" + (fecha.getMonth() + 1) + "/" + fecha.getFullYear());
        $('#divCreditoContraNota').find("#txtFechaRegistro1").val(fecha.getDate() + "/" + (fecha.getMonth() + 1) + "/" + fecha.getFullYear());
        datosCreditoContraNota();
        $('#divCreditoContranota').dialog('open');
    }
}

function enviarCreditoPlazo() {
    $.ajax({
        url: contextoGlobal + '/controller/creditoController'
        , type: 'post'
        , dataType: 'json'
        , cache: false
        , data: {
            method: 1
            , txtFolio: $('#divCreditoPlazo').find('#txtFolio').val()
            , txtFechaRegistro: $('#divCreditoPlazo').find('#txtFechaRegistro').val()
            , txtFechaPago: $('#divCreditoPlazo').find('#txtFechaPago').val()
            , txtIdCredito: $('#divCreditoPlazo').find('#txtIdCredito').val()
            , txtTipoCredito: $('#divCreditoPlazo').find('#txtTipoCredito').val()
            , ajax: true
        }
        , success: function (response) {
            if (response == true) {
                var notaVenta = $('#divCreditoPlazo').find('#txtIdNotaVenta').val();
                $('#divAltaAbonos').dialog('close');
                $('#divCreditoPlazo').dialog('close');
                registrarAbono(notaVenta);
            }
        }
        , error: function (jqXHR, textStatus, errorThrown) {
            console.log("ERROR L: " + textStatus + " (" + errorThrown + ")");
        }
    });
}

function enviarCreditoMonetario() {
    $.ajax({
        url: contextoGlobal + '/controller/creditoController'
        , type: 'post'
        , dataType: 'json'
        , cache: false
        , data: {
            method: 1
            , txtFolio: $('#divCreditoMonetario').find('#txtFolio').val()
            , txtFechaRegistro: $('#divCreditoMonetario').find('#txtFechaRegistro').val()
            , txtIdCredito: $('#divCreditoMonetario').find('#txtIdCredito').val()
            , txtTipoCredito: $('#divCreditoMonetario').find('#txtTipoCredito').val()
            , ajax: true
        }
        , success: function (response) {
            if (response == true) {
                var notaVenta = $('#divCreditoMonetario').find('#txtIdNotaVenta').val();
                $('#divAltaAbonos').dialog('close');
                $('#divCreditoMonetario').dialog('close');
                registrarAbono(notaVenta);
            }
        }
        , error: function (jqXHR, textStatus, errorThrown) {
            console.log("ERROR L: " + textStatus + " (" + errorThrown + ")");
        }
    });
}

function datosCreditoContraNota() {
    $.ajax({
        url: contextoGlobal + '/controller/notaVentaController'
        , type: 'post'
        , dataType: 'json'
        , cache: false
        , data: {
            method: 1
            , txtIdNotaVenta: $('#divCreditoContraNota').find('#txtIdNotaVenta').val()
            , ajax: true
        }
        , success: function (response) {
            $('#divCreditoContraNota').find('#txtCliente').val(response.nombreCliente);
            $('#divCreditoContraNota').find('#txtCargo').val(response.total);
            $('#divCreditoContraNota').find('#txtCantidadLetra').val(response.cantidadLetra + ' PESOS');
            $('#divCreditoContraNota').find('#txtNotaVenta').val('PAGO DE LA NOTA DE VENTA CON FOLIO ' + response.folio);
            $('#divCreditoContraNota').find('#txtCargo').priceFormat({
                prefix: '$ '
            });
            $.datepicker.setDefaults($.datepicker.regional['es-MX']);
            $('#divCreditoContraNota').find("#txtFechaPago").datepicker({
                changeMonth: true
                , changeYear: true
                , minDate: new Date
                , maxDate: "+1Y"

            });
            $('#divCreditoContraNota').dialog('open');
        }
        , error: function (jqXHR, textStatus, errorThrown) {
            console.log("ERROR L: " + textStatus + " (" + errorThrown + ")");
        }
    });

}