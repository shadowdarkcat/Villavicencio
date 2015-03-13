var dtoPeso = ['KG', 'PIEZAS', 'GR'];

$(document).ready(function () {

    $('#tblProductos').DataTable({
        language: {
            url: contextoGlobal + '/resource/es_ES.json'
        }
    });
    var table = $('#tblProductos').DataTable();

    $(function () {
        var cbo = '<select id="cboUnidadPeso" name="cboUnidadPeso" class="required"> <option value="">Seleccione ...</option>';
        var option = '';
        $.each(dtoPeso, function (index, item) {
            option += '<option value="' + item + '">' + item + '</option>';
        });
        cbo += option;
        $('#tdCbo').append(cbo);
    });



    if ($('#errorReporte').length > 0) {
        $('#errorReporte').dialog({
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


    if ($('#divProductoNoSelected').length > 0) {
        $('#divProductoNoSelected').dialog({
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

    if ($('#divActivarProducto').length > 0) {
        $('#divActivarProducto').dialog({
            resizable: false
            , width: 250
            , height: 150
            , autoOpen: false
            , cache: false
            , modal: true
            , buttons: {
                Aceptar: function () {
                    if ($('#frmActivarProducto').validate().form()) {
                        $('#frmActivarProducto').submit();
                    } else {
                        $(this).dialog('close');
                    }
                }
                , Cerrar: function () {
                    $(this).dialog('close');
                }
            }
        });
    }

    if ($('#divProductoNoVisible').length > 0) {
        $('#divProductoNoVisible').dialog({
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

    if ($('#divExisteProducto').length > 0) {
        $('#divExisteProducto').dialog({
            resizable: false
            , width: 200
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

    $(function () {
        var exist = $('#existProducto').val();
        if (exist) {
            $('#divExisteProducto').dialog('open');
        }
    });


    if ($('#divAltaProducto').length > 0) {
        $('#divAltaProducto').dialog({
            resizable: false
            , width: 800
            , height: 350
            , modal: false
            , autoOpen: false
            , buttons: {
                Registrar: function () {
                    if ($('#frmAltaProducto').validate().form()) {
                        if (flag == false) {
                            $('#frmAltaProducto').submit();
                            $(this).dialog('close');
                        }
                    }
                }
                , Cerrar: function () {
                    $(this).dialog('close');
                }
            }
        });
    }

    if ($("#divMessageBajaProducto").length > 0) {
        $("#divMessageBajaProducto").dialog({
            resizable: false
            , width: 280
            , height: 200
            , modal: true
            , autoOpen: false
            , buttons: {
                Aceptar: function () {
                    if ($('#frmBajaProducto').validate().form()) {
                        $('#frmBajaProducto').submit();
                        $(this).dialog('close');
                    } else {
                        $(this).dialog('close');
                    }
                }
                , Cerrar: function () {
                    $(this).dialog("close");
                }
            }
        });
    }

    if ($("#divBajaProducto").length > 0) {
        $("#divBajaProducto").dialog({
            resizable: false
            , width: 400
            , height: 310
            , modal: true
            , autoOpen: false
            , buttons: {
                Aceptar: function () {
                    $("#divMessageBajaProducto").dialog("open");
                }
                , Cerrar: function () {
                    $(this).dialog("close");
                }
            }
        });
    }

    if ($('#divMessageModificarProd').length > 0) {
        $('#divMessageModificarProd').dialog({
            resizable: false
            , width: 280
            , height: 200
            , modal: true
            , autoOpen: false
            , cache: false
            , buttons: {
                Aceptar: function () {
                    if ($('#frmModificarProducto').validate().form()) {
                        $('#frmModificarProducto').submit();
                        $(this).dialog('close');
                    } else {
                        $(this).dialog('close');
                    }
                }
                , Cerrar: function () {
                    $(this).dialog('close');
                }
            }
        });
    }

    if ($('#divModificarProducto').length > 0) {
        $('#divModificarProducto').dialog({
            resizable: false
            , width: 520
            , height: 350
            , modal: true
            , autoOpen: false
            , buttons: {
                Aceptar: function () {
                    $('#divMessageModificarProd').dialog('open');
                }
                , Cerrar: function () {
                    $(this).dialog('close');
                }
            }
        });
    }

    $('#btnAltaProducto').on('click', function () {
        $('#divAltaProducto').dialog('open');
    });

    $('#btnBajaProducto').on('click', function () {
        var idProducto = $('.selected').find('#txtIdProducto').val();
        var visible = $('.selected').find('#chkVisible:checked').val();
        if ((idProducto != null) && (idProducto != undefined)) {
            if ((visible != false) && (visible != undefined)) {
                getDataProductoBaja(idProducto);
                $('#divBajaProducto').dialog('open');
            } else {
                $('#divProductoNoVisible').dialog('open');
            }
        } else {
            $('#divProductoNoSelected').dialog('open');
        }
    });

    $('#btnCambioProducto').on('click', function () {
        var idProducto = $('.selected').find('#txtIdProducto').val();
        var visible = $('.selected').find('#chkVisible:checked').val();
        if ((idProducto != null) || (idProducto != undefined)) {
            if ((visible != null) && (visible != undefined)) {
                getDataProductoCambios(idProducto);
                $('#divModificarProducto').dialog('open');
            } else {
                $('#divProductoNoVisible').dialog('open');
            }
        } else {
            $('#divProductoNoSelected').dialog('open');
        }
    });

    $('#tblProductos tbody').on('click', 'tr', function () {
        $(this).removeClass('selected');
        table.$('tr.selected').removeClass('selected');
        $(this).addClass('selected');
    });

    $('#chkVisible').on('click', function () {
        if ($('#frmActivarProducto').find('#chkVisible:checked').val()) {
            if ($('#divBajaProducto').find('#chkVisible:checked').val()) {
                $('#divActivarProducto').dialog('open');
            }
        }
    });
});
var flag;
function minMax() {
    var maximo = $('#txtMaximo').val();
    var minimo = $('#txtMinimo').val();
    if (Number(maximo) < Number(minimo)) {
        $('#divMinimoAlert').show();
        flag = true;
    } else {
        $('#divMinimoAlert').hide();
        flag = false;
    }
}

function getDataProductoBaja(idProducto) {
    $.ajax({
        url: contextoGlobal + '/controller/productoController'
        , type: 'post'
        , dataType: 'json'
        , cache: false
        , data: {
            method: 5
            , txtIdProducto: idProducto
            , ajax: true
        }
        , success: function (response) {
            $('#divBajaProducto').find('#txtIdProducto').val(response.idProducto);
            $('#divBajaProducto').find('#txtProducto').val(response.nombreProducto);
            $('#divBajaProducto').find('#txtClv').val(response.claveProducto);
            $('#divBajaProducto').find('#txtUnidadPeso').val(response.peso);
            $('#divBajaProducto').find('#txtMaximo').val(response.pesoMaximo);
            $('#divBajaProducto').find('#txtMinimo').val(response.pesoMinimo);
            $('#divBajaProducto').find('#txtCosto').val(response.costoUnitario);
        }
        , error: function (jqXHR, textStatus, errorThrown) {
            console.log("ERROR L: " + textStatus + " (" + errorThrown + ")");
        }
    });
}

function getDataProductoCambios(idProducto) {
    $.ajax({
        url: contextoGlobal + '/controller/productoController'
        , type: 'post'
        , dataType: 'json'
        , cache: false
        , data: {
            method: 5
            , txtIdProducto: idProducto
            , ajax: true
        }
        , success: function (response) {
            $('#divModificarProducto').find('#txtIdProducto').val(response.idProducto);
            $('#divModificarProducto').find('#txtProducto').val(response.nombreProducto);
            $('#divModificarProducto').find('#txtClv').val(response.claveProducto);
            $('#divModificarProducto').find('#cboUnidadPeso').val(response.peso);
            $('#divModificarProducto').find('#txtMaximo').val(response.pesoMaximo);
            $('#divModificarProducto').find('#txtMinimo').val(response.pesoMinimo);
            $('#divModificarProducto').find('#txtCosto').val(response.costoUnitario);
        }
        , error: function (jqXHR, textStatus, errorThrown) {
            console.log("ERROR L: " + textStatus + " (" + errorThrown + ")");
        }
    });
}

function validarReporte() {
    var opciones = document.getElementsByName("rdbFormatoReporte");
    var seleccionado = false;
    for (var i = 0; i < opciones.length; i++) {
        if (opciones[i].checked) {
            seleccionado = true;
            break;
        }
    }
    if (!seleccionado) {
        $('#errorReporte').dialog('open');
    } else {
        $('#frmReporteProductos').submit();
    }
}