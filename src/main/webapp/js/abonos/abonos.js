var tableDetalles;
var tableClienteMovimiento;
var tableVendedorMovimiento;
var tableDetallesAbonos;
var tipoPagoSelected;
var idCliente;
var idVendedor;
$(document).ready(function () {


    $("#txtFecha").datepicker();
    $("#txtFecha").datepicker("setDate", new Date);

    $('#tblClientesMovimiento').DataTable({
        language: {
            url: contextoGlobal + '/resource/es_ES.json'
        }
    });

    $('#tblVendedoresMovimiento').DataTable({
        language: {
            url: contextoGlobal + '/resource/es_ES.json'
        }
    });

    tableClienteMovimiento = $('#tblClientesMovimiento').DataTable();
    tableVendedorMovimiento = $('#tblVendedoresMovimiento').DataTable();


    $('#tblClientesMovimiento tbody').on('click', 'tr', function () {
        $(this).removeClass('selected');
        tableClienteMovimiento.$('tr.selected').removeClass('selected');
        $(this).addClass('selected');
    });

    $('#tblVendedoresMovimiento tbody').on('click', 'tr', function () {
        $(this).removeClass('selected');
        tableVendedorMovimiento.$('tr.selected').removeClass('selected');
        $(this).addClass('selected');
    });

    $('#tblDetallesMovimiento tbody').on('click', 'tr', function () {
        $(this).removeClass('selected');
        tableDetalles.$('tr.selected').removeClass('selected');
        $(this).addClass('selected');
    });

    if ($('#divNotaNoSelected').length > 0) {
        $('#divNotaNoSelected').dialog({
            resizable: false
            , width: 350
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

    if ($('#divRadioNoSelected').length > 0) {
        $('#divRadioNoSelected').dialog({
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

    if ($('#divTipoPagoNoSelected').length > 0) {
        $('#divTipoPagoNoSelected').dialog({
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

    if ($('#divBancoNoSelected').length > 0) {
        $('#divBancoNoSelected').dialog({
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

    if ($('#divDetallesVenta').length > 0) {
        $('#divDetallesVenta').dialog({
            resizable: false
            , width: 900
            , height: 800
            , modal: true
            , cache: false
            , autoOpen: false
            , closeOnEscape: false
            , dialogClass: 'no-close'
            , buttons: {
                Cerrar: function () {
                    $(this).dialog('close');
                    $('#divLateral').show();
                }
            }
        });
    }

    if ($('#divAltaAbonos').length > 0) {
        $('#divAltaAbonos').dialog({
            resizable: false
            , width: 1130
            , height: 900
            , modal: true
            , cache: false
            , autoOpen: false
            , closeOnEscape: false
            , dialogClass: 'no-close'
            , buttons: {
                Registrar: function () {
                    $('#divRegistrarAbonos').dialog('open');
                }
                , Cerrar: function () {
                    $(this).dialog('close');
                    if ((idCliente != undefined) || (idVendedor != undefined)) {
                        $('#divDetallesVenta').dialog('close');
                        getDataCliente(idCliente);
                        getDataVendedor(idVendedor);
                    }
                }
            }
        });
    }

    if ($('#divRegistrarAbonos').length > 0) {
        $('#divRegistrarAbonos').dialog({
            resizable: false
            , width: 500
            , height: 500
            , modal: true
            , cache: false
            , autoOpen: false
            , closeOnEscape: false
            , dialogClass: 'no-close'
            , buttons: {
                Registrar: function () {
                    if (tipoPagoSelected == true) {
                        if (tipoPagoSelected != 3) {
                            if ($('#cboBanco option:selected').val() != undefined) {
                                sendAjax();
                            } else {
                                $('#divBancoNoSelected').dialog('open');
                            }
                        } else {
                            sendAjax();
                        }
                    } else {
                        $('#divTipoPagoNoSelected').dialog('open');
                    }
                }
                , Cerrar: function () {
                    $(this).dialog('close');
                }
            }
        });
    }

    if ($('#divLiquidacion').length > 0) {
        $('#divLiquidacion').dialog({
            resizable: false
            , width: 950
            , height: 900
            , modal: true
            , cache: false
            , autoOpen: false
            , closeOnEscape: false
            , dialogClass: 'no-close'
            , buttons: {
                Liquidar: function () {
                    $('#divRegistrarAbonos').dialog('open');
                }
                , Cerrar: function () {
                    $(this).dialog('close');
                }
            }
        });
    }

    $('input[name=rdbTipoClienteMovimiento]').change(function () {
        id = $(this).val();
        if (id == 1) {
            $('#divClienteMovimiento').show();
            $('#divVendedorMovimiento').hide();
        } else if (id == 2) {
            $('#divVendedorMovimiento').show();
            $('#divClienteMovimiento').hide();
        }
    });

    $('#btnAltaAbono').on('click', function () {
        idCliente = $('.selected').find('#txtIdCliente').val();
        idVendedor = $('.selected').find('#txtIdVendedor').val();
        if (!$('input[name=rdbTipoClienteMovimiento]').is(':checked')) {
            $('#divRadioNoSelected').dialog('open');
        } else {
            if ((idCliente != undefined) || (idVendedor != undefined)) {
                getDataCliente(idCliente);
                getDataVendedor(idVendedor);
            } else {
                $('#divNotaNoSelected').dialog('open');
            }
        }
    });

    $('#cboBanco').change(function () {
        var idBanco = $('#cboBanco option:selected').val();
        if (idBanco != undefined) {
            $('#divDatosAbono').show();
            $('#txtCantidadAbono').removeProp('disabled');
        } else {
            $('#divDatosAbono').hide();
            $('#txtCantidadAbono').prop('disabled', 'disabled');
        }
    });
});

function getDataCliente(idCliente) {
    if (idCliente != undefined) {
        tableClienteMovimiento.$('tr.selected').removeClass('selected');
        $('#divDetallesVenta').find('#divDetallesPrevioMovimientos').empty();
        $('#divAltaLiquidacion').find('#txtIdCliente').val(idCliente);
        $.ajax({
            url: contextoGlobal + '/controller/abonosController'
            , type: 'post'
            , dataType: 'json'
            , cache: false
            , data: {
                method: 1
                , txtIdCliente: idCliente
                , context: contextoGlobal
                , ajax: true
            }
            , success: function (response) {
                $('#tblDetallesMovimiento').empty();
                var noInterior = ' ';
                var empresa = response.empresa + ' ' + response.razonSocial;
                var rfc = (response.rfc != null ? response.rfc : 'NO APLICA');
                if ((response.noInterior != undefined) && (response.noInterior != null) && (response.noInterior != 'null')) {
                    noInterior = response.noInterior;
                }
                var noExterior = (response.noExterior).toString();
                noExterior += ' ' + noInterior;
                var direccion = response.calle + ', ' + noExterior
                        + ', ' + response.colonia + ', ' + response.codigoPostal;
                if (response.delegacion != '' && response.delegacion != 'null' && response.delegacion != undefined) {
                    direccion += ', ' + response.delegacion;
                } else {
                    direccion += ', ' + response.municipio;
                }
                direccion += ', ' + response.estado + ', ' + response.ciudad;
                $('#divDetallesVenta').find('#lblNombre').text(empresa);
                $('#divDetallesVenta').find('#lblDireccion').text(direccion);
                $('#divDetallesVenta').find('#lblRfc').text(rfc);
                $('#divDetallesVenta').find('#lblTotalAdeudo').text(response.totalAdeudo.toFixed(2));
                $('#divDetallesVenta').find('#lblTotalAbonado').text(response.totalAbonado.toFixed(2));
                $('#divDetallesVenta').find('#lblTotalFaltante').text(response.totalFaltante.toFixed(2));
                $('#divDetallesVenta').find('#divDetallesPrevioMovimientos').append(response.table);
                $('#tblDetallesMovimiento').DataTable({
                    language: {
                        url: contextoGlobal + '/resource/es_ES.json'
                    }
                });
                tableDetalles = $('#tblDetallesMovimiento').DataTable();
                $('#lblTotalAdeudo').priceFormat({
                    prefix: '$ '
                });

                $('#lblTotalAbonado').priceFormat({
                    prefix: '$ '
                });

                $('#lblTotalFaltante').priceFormat({
                    prefix: '$ '
                });

                $('#divDetallesVenta').dialog('open');
                $('#divLateral').hide();
            }
            , error: function (jqXHR, textStatus, errorThrown) {
                console.log("ERROR L: " + textStatus + " (" + errorThrown + ")");
            }
        });
    }
}

function getDataVendedor(idVendedor) {
    if (idVendedor != undefined) {
        tableVendedorMovimiento.$('tr.selected').removeClass('selected');
        $('#divDetallesVenta').find('#divDetallesPrevioMovimientos').empty();
        $('#divAltaLiquidacion').find('#txtIdVendedor').val(idVendedor);
        $.ajax({
            url: contextoGlobal + '/controller/abonosController'
            , type: 'post'
            , dataType: 'json'
            , cache: false
            , data: {
                method: 1
                , txtIdVendedor: idVendedor
                , context: contextoGlobal
                , ajax: true
            }
            , success: function (response) {
                var noInterior = ' ';
                var nombre = response.nombre + ' ' + response.apellidoPaterno + ' ' + response.apellidoMaterno;
                var rfc = (response.rfc != null ? response.rfc : 'NO APLICA');
                if ((response.noInterior != undefined) && (response.noInterior != null) && (response.noInterior != 'null')) {
                    noInterior = response.noInterior;
                }
                var noExterior = (response.noExterior).toString();
                noExterior += ' ' + noInterior;
                var direccion = response.calle + ', ' + noExterior
                        + ', ' + response.colonia + ', ' + response.cp;
                if (response.delegacion != '' && response.delegacion != 'null' && response.delegacion != undefined) {
                    direccion += ', ' + response.delegacion;
                } else {
                    direccion += ', ' + response.municipio;
                }
                direccion += ', ' + response.estado + ', ' + response.ciudad;
                $('#divDetallesVenta').find('#lblNombre').text(nombre);
                $('#divDetallesVenta').find('#lblDireccion').text(direccion);
                $('#divDetallesVenta').find('#lblRfc').text(rfc);
                $('#divDetallesVenta').find('#lblTotalAdeudo').text(response.totalAdeudo.toFixed(2));
                $('#divDetallesVenta').find('#lblTotalAbonado').text(response.totalAbonado.toFixed(2));
                $('#divDetallesVenta').find('#lblTotalFaltante').text(response.totalFaltante.toFixed(2));
                $('#divDetallesVenta').find('#divDetallesPrevioMovimientos').append(response.table);
                $('#tblDetallesMovimiento').DataTable({
                    language: {
                        url: contextoGlobal + '/resource/es_ES.json'
                        , "scrollY": "200px"
                        , "scrollCollapse": true
                    }
                });
                tableDetalles = $('#tblDetallesMovimiento').DataTable();
                $('#lblTotalAdeudo').priceFormat({
                    prefix: '$ '
                });

                $('#lblTotalAbonado').priceFormat({
                    prefix: '$ '
                });

                $('#lblTotalFaltante').priceFormat({
                    prefix: '$ '
                });

                $('#divDetallesVenta').dialog('open');
                $('#divLateral').hide();
            }
            , error: function (jqXHR, textStatus, errorThrown) {
                console.log("ERROR L: " + textStatus + " (" + errorThrown + ")");
            }
        });
    }
}

function registrarAbono(idNotaVenta) {
    $('#frmRegistroAbono').find('#txtIdNotaVenta').val(idNotaVenta);
    $('#divAltaAbonos').empty();
    $.ajax({
        url: contextoGlobal + '/controller/abonosController'
        , type: 'post'
        , dataType: 'html'
        , cache: false
        , data: {
            method: 2
            , txtIdNotaVenta: idNotaVenta
            , context: contextoGlobal
            , txtIdAction: $('#divDetallesVenta').find('#txtIdAction').val()
            , ajax: true
        }
        , success: function (response) {

            $('#divAltaAbonos').append(response);
            $('#lblCargo').priceFormat({
                prefix: '$ '
            });

            $('#lblDevolucion').priceFormat({
                prefix: '$ '
            });

            $('#lblTotal').priceFormat({
                prefix: '$ '
            });

            $('#lblLimiteCredito').priceFormat({
                prefix: '$ '
            });
            var dispo = parseFloat($('#frmRegistroAbono').find('#lblCreditoUsado').text());
            if (dispo < 0) {
                $('#frmRegistroAbono').find('#lblNegativo').empty();
                $('#frmRegistroAbono').find('#lblNegativo').text('CREDITO SALDO DEUDOR :');
                $('#lblCreditoUsado').priceFormat({
                    prefix: '$ - '
                });
                $('#frmRegistroAbono').find('#lblCreditoUsado').css('color','red');
            } else {
                $('#lblCreditoUsado').priceFormat({
                    prefix: '$ '
                });
            }

            $('#tblDetalleAbono').DataTable({
                language: {
                    url: contextoGlobal + '/resource/es_ES.json'
                }
                , "scrollY": "200px"
                , "scrollCollapse": true
            });
            tableDetallesAbonos = $('#tblDetalleAbono.display').DataTable();
            $('#divAltaAbonos').dialog('open');
        }
        , error: function (jqXHR, textStatus, errorThrown) {
            console.log("ERROR L: " + textStatus + " (" + errorThrown + ")");
        }
    });
}

function tipoDePago() {
    $('#txtCantidadAbono').prop('disabled', 'disabled');
    var id;
    var elements = document.getElementsByName('rdbTipoDePago');
    for (var i = 0; i < elements.length; i++) {
        if (elements[i].checked) {
            id = elements[i].value;
            if (id == 1) {
                $('#cboBanco').val('SELECCIONE ...');
                $('#divBanco').show();
                $('#divDatosAbono').hide();
            } else if (id == 2) {
                $('#cboBanco').val('SELECCIONE ...');
                $('#divBanco').show();
                $('#divDatosAbono').hide();

            } else if (id == 3) {
                $('#divBanco').hide();
                $('#cboBanco').val('SELECCIONE ...');
                $('#divDatosAbono').show();
                $('#txtCantidadAbono').removeProp('disabled');
            } else if (id == 4) {
                $('#cboBanco').val('SELECCIONE ...');
                $('#divBanco').show();
                $('#divDatosAbono').hide();
            }
            tipoPagoSelected = true;
            break;
        } else {
            tipoPagoSelected = false;
        }
    }

}

function soloNumero(campo) {
    campo.value = (campo.value + '').replace(/[^.0-9]/g, '');
}
var flagSenAjax;

function sendAjax() {
    var abono = $('#divDatosAbono').find('#txtCantidadAbono').val();
    if (abono.length == 0 || /^\s+$/.test(abono)) {
        $('#divDatosAbono').find('#lblErrorCantidad').show();
        return;
    } else {
        $('#divDatosAbono').find('#lblErrorCantidad').hide();
    }
    var id;
    var idBanco = $('#cboBanco option:selected').val();
    if (idBanco == 'SELECCIONE ...') {
        idBanco = '';
    }
    var elements = document.getElementsByName('rdbTipoDePago');
    for (var i = 0; i < elements.length; i++) {
        if (elements[i].checked) {
            id = elements[i].value;
        }
    }
    $.ajax({
        url: contextoGlobal + '/controller/abonosController'
        , type: 'post'
        , dataType: 'json'
        , cache: false
        , data: {
            method: 3
            , txtIdNotaVenta: $('#frmRegistroAbono').find('#txtIdNotaVenta').val()
            , rdbTipoDePago: id
            , cboBanco: idBanco
            , txtFecha: $('#frmRegistroAbono').find('#txtFecha').val()
            , txtCantidadAbono: abono
            , ajax: true
        }
        , success: function (response) {
            if (response == true) {
                $('#divRegistrarAbonos').dialog('close');
                registrarAbono($('#frmRegistroAbono').find('#txtIdNotaVenta').val());
            }
        }
        , error: function (jqXHR, textStatus, errorThrown) {
            console.log("ERROR L: " + textStatus + " (" + errorThrown + ")");
        }
    });
}

function liquidar(idNotaVenta) {
    generarLiquidacion(idNotaVenta);
}