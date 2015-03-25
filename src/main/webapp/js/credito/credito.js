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
                        enviarCredito();
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
    var cargo = $('#lblCargo').text().substring(1, ($('#lblCargo').text().length));
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
}
function aplicarCredito(tipoCredito, folioNota, idCredito, idNotaVenta) {
    $('#divCreditoPlazo').find('#txtFolio').val(folioNota);
    $('#divCreditoPlazo').find('#txtIdCredito').val(idCredito);
    $('#divCreditoPlazo').find('#txtTipoCredito').val(tipoCredito);
    $('#divCreditoPlazo').find('#txtIdNotaVenta').val(idNotaVenta);
    creditoExcedido();
    $('#divCreditoPlazo').dialog('open');
}

function enviarCredito() {
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
                console.log(notaVenta);
                registrarAbono(notaVenta);
            }
        }
        , error: function (jqXHR, textStatus, errorThrown) {
            console.log("ERROR L: " + textStatus + " (" + errorThrown + ")");
        }
    });
}
