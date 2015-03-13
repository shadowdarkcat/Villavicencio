$(document).ready(function () {
    $('#tblVendedores').DataTable({
        language: {
            url: contextoGlobal + '/resource/es_ES.json'
        }
    });

    var table = $('#tblVendedores').DataTable();


    if ($('#divTipoReporteVendedor').length > 0) {
        $('#divTipoReporteVendedor').dialog({
            resizable: false
            , width: 300
            , height: 150
            , autoOpen: false
            , cache: false
            , modal: true
            , buttons: {
                Aceptar: function () {
                    if (!validarReporteVendedor()) {
                        $('#errorReporteVendedor').dialog('open');
                    } else {
                        $('#frmReporteVendedores').submit();
                    }
                }
                , Cerrar: function () {
                    $(this).dialog('close');
                }
            }
        });
    }

    if ($('#errorReporteVendedor').length > 0) {
        $('#errorReporteVendedor').dialog({
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

    if ($('#divVendedorNoSelected').length > 0) {
        $('#divVendedorNoSelected').dialog({
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

    if ($('#divActivarVendedor').length > 0) {
        $('#divActivarVendedor').dialog({
            resizable: false
            , width: 300
            , height: 150
            , autoOpen: false
            , cache: false
            , modal: true
            , buttons: {
                Aceptar: function () {
                    if ($('#frmActivarVendedor').validate().form()) {
                        $('#frmActivarVendedor').submit();
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

    if ($('#divNoVisible').length > 0) {
        $('#divNoVisible').dialog({
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

    if ($('#divExisteVendedor').length > 0) {
        $('#divExisteVendedor').dialog({
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

    if ($('#divAltaVendedor').length > 0) {
        $('#divAltaVendedor').dialog({
            resizable: false
            , width: 1270
            , height: 830
            , modal: true
            , autoOpen: false
            , closeOnEscape: false
            , dialogClass: 'no-close'
            , buttons: {
                Aceptar: function () {
                    if ($('#frmAltaVendedor').validate().form()) {
                        $('#frmAltaVendedor').submit();
                        $('#divLateral').show();
                        $(this).dialog('close');
                    }
                }
                , Cerrar: function () {
                    $('#divLateral').show();
                    $(this).dialog('close');
                }
            }
        });
    }

    if ($('#divMessageBajaVendedor').length > 0) {
        $('#divMessageBajaVendedor').dialog({
            resizable: false
            , width: 280
            , height: 200
            , modal: true
            , autoOpen: false
            , buttons: {
                Aceptar: function () {
                    $('#frmBajaVendedor').submit();
                    $('#divLateral').show();
                }
                , Cerrar: function () {
                    $(this).dialog('close');
                }
            }
        });
    }

    if ($('#divBajaVendedor').length > 0) {
        $('#divBajaVendedor').dialog({
            resizable: false
            , width: 1270
            , height: 830
            , modal: true
            , autoOpen: false
            , closeOnEscape: false
            , dialogClass: 'no-close'
            , buttons: {
                Aceptar: function () {
                    $('#divMessageBajaVendedor').dialog('open');
                }
                , Cerrar: function () {
                    $(this).dialog('close');
                    $('#divLateral').show();
                }
            }
        });
    }

    if ($('#divMessageModificarVendedor').length > 0) {
        $('#divMessageModificarVendedor').dialog({
            resizable: false
            , width: 280
            , height: 200
            , modal: true
            , autoOpen: false
            , buttons: {
                Aceptar: function () {
                    if ($('#frmModificarVendedor').validate().form()) {
                        $('#frmModificarVendedor').submit();
                        $('#divLateral').show();
                        $(this).dialog('close');
                    }
                }
                , Cerrar: function () {
                    $(this).dialog('close');
                }
            }
        });
    }

    if ($('#divModificarVendedor').length > 0) {
        $('#divModificarVendedor').dialog({
            resizable: false
            , width: 1270
            , height: 830
            , modal: true
            , autoOpen: false
            , closeOnEscape: false
            , dialogClass: 'no-close'
            , buttons: {
                Aceptar: function () {
                    $('#divMessageModificarVendedor').dialog('open');
                }
                , Cerrar: function () {
                    $('#divLateral').show();
                    $(this).dialog('close');
                }
            }
        });
    }
    $('#txtColonia').keypress(function (event) {
        var keycode = (event.keyCode ? event.keyCode : event.which);
        if (keycode == '13') {
            var colonia = $('#txtColonia').val();
            $('#txtColonia').empty();
            findColonia(colonia);
        }
    });

    $('#rdbVendedor').on('click', function () {
        $('#divComisiones').show();
    });

    $('#chkCredito').click(function () {
        if ($(this).is(':checked')) {
            $('#divCreditos').show();
        } else {
            $('#divCreditos').hide();
            for (var i = 0; i < document.frmAltaVendedor.rdbCredito.length; i++) {
                document.frmAltaVendedor.rdbCredito[i].checked = false;
            }
            $('#divMonetario').hide();
            $('#txtMonetario').removeClass('required');
        }
    });

    $('#chkExterno').click(function () {
        if ($(this).is(':checked')) {
            $('#rdbProducto').prop('checked', true);
            buscarProductoVendedor();
        } else {
            $('#rdbProducto').prop('checked', false);
            $('#divProductoVendedor').empty();
        }
    });

    $(function () {
        var exist = $('#existVendedor').val();
        if (exist) {
            $('#divExisteVendedor').dialog('open');
        }
    });

    $('#tblVendedores tbody').on('click', 'tr', function () {
        $(this).removeClass('selected');
        table.$('tr.selected').removeClass('selected');
        $(this).addClass('selected');
    });

    $('#btnAltaVendedor').on('click', function () {
        loadColonia();
        $('#divLateral').hide();
        $('#divAltaVendedor').dialog('open');
    });

    $('#btnBajaVendedor').on('click', function () {
        var idVendedor = $('.selected').find('#txtIdVendedor').val();
        var visible = $('.selected').find('#chkVisible:checked').val();
        if ((idVendedor != null) && (idVendedor != undefined)) {
            if ((visible != false) && (visible != undefined)) {
                getDataVendedor(idVendedor);
                $('#divLateral').hide();
                $('#divBajaVendedor').dialog('open');
            } else {
                $('#divNoVisible').dialog('open');
            }
        } else {
            $('#divVendedorNoSelected').dialog('open');
        }
    });

    $('#btnCambioVendedor').on('click', function () {
        $("#tblProducto > tbody").html("");
        $("#lblTitulo").html("");
        var idVendedor = $('.selected').find('#txtIdVendedor').val();
        var visible = $('.selected').find('#chkVisible:checked').val();
        if ((idVendedor != null) && (idVendedor != undefined)) {
            if ((visible != false) && (visible != undefined)) {
                getDataVendedorCambios(idVendedor);
                $('#divLateral').hide();
                $('#divModificarVendedor').dialog('open');
            } else {
                $('#divNoVisible').dialog('open');
            }
        } else {
            $('#divVendedorNoSelected').dialog('open');
        }
    });

    $('#btnGenerar').on('click', function () {
        $('#divTipoReporteVendedor').dialog('open');
    });
});

function credito() {
    var id;
    var len = document.frmAltaVendedor.rdbCredito.length;
    for (var i = 0; i < len; i++) {
        if (document.frmAltaVendedor.rdbCredito[i].checked) {
            id = document.frmAltaVendedor.rdbCredito[i].value;
            if (id == 1) {
                $('#divMonetario').show();
                $('#txtMonetario').addClass('required');
            } else {
                $('#divMonetario').hide();
                $('#txtMonetario').removeClass('required');
            }
            break;
        }
    }
}

function creditoModificar() {
    var id;
    var len = document.frmModificarVendedor.rdbCredito.length;
    for (var i = 0; i < len; i++) {
        if (document.frmModificarVendedor.rdbCredito[i].checked) {
            id = document.frmModificarVendedor.rdbCredito[i].value;
            if (id == 1) {
                $('#divModificarVendedor').find('#divMonetario').show();
                $('#divModificarVendedor').find('#txtMonetario').addClass('required');
            } else {
                $('#divModificarVendedor').find('#divMonetario').hide();
                $('#divModificarVendedor').find('#txtMonetario').removeClass('required');
            }
            break;
        }
    }
}

function findColonia(colonia) {
    $.ajax({
        url: contextoGlobal + '/controller/vendedorController'
        , type: 'post'
        , dataType: 'json'
        , cache: false
        , data: {
            method: 8
            , txtColonia: colonia
            , ajax: true
        }
        , success: function (response) {
            var td;
            $('#delegacionMunicipio').empty('');
            $.each(response, function (index, item) {
                $('#txtColonia').val(item.colonia.toUpperCase());
                $('#txtCp').val(item.cp);
                if (item.delegacion != '') {
                    td = '<td><span class="text-muted" >*Delegaci&oacute;n :</span></td>'
                            + '<td><input type="text" id="txtDelegacion" name="txtDelegacion" class="required" value="'
                            + item.delegacion.toUpperCase() + '" readOnly="readOnly" /></td>';
                } else {
                    td = '<td><span class="text-muted" >*Municipio :</span></td>'
                            + '<td><input type="text" id="txtMunicipio" name="txtMunicipio" class="required" value="'
                            + item.municipio.toUpperCase() + '" readOnly="readOnly" /></td>';
                }
                $('#txtEstado').val(item.estado.toUpperCase());
                $('#txtCiudad').val(item.ciudad.toUpperCase());
            });
            $('#delegacionMunicipio').append(td);
        }
        , error: function (jqXHR, textStatus, errorThrown) {
            console.log("ERROR L: " + textStatus + " (" + errorThrown + ")");
        }
    });
}

function loadColonia() {
    var availableTags = [];
    $.ajax({
        url: contextoGlobal + '/controller/vendedorController'
        , type: 'post'
        , dataType: 'json'
        , cache: false
        , data: {
            method: 8
            , ajax: true
        }
        , success: function (response) {
            $.each(response, function (index, item) {
                if (item.delegacion != '') {
                    availableTags.push(item.colonia.toUpperCase() + ',' + item.delegacion.toUpperCase() + ',' + item.cp + ',' + item.estado);
                } else {
                    availableTags.push(item.colonia.toUpperCase() + ',' + item.municipio.toUpperCase() + ',' + item.cp + ',' + item.estado);
                }
            });
        }
        , error: function (jqXHR, textStatus, errorThrown) {
            console.log("ERROR L: " + textStatus + " (" + errorThrown + ")");
        }
    });

    $("#txtColonia").autocomplete({
        source: availableTags
    });
}

function buscarProductoVendedor(idVendedor) {
    if (idVendedor == undefined) {
        idVendedor = $('#divModificarVendedor').find('#txtIdVendedor').val();
        //idVendedor = $('#divBajaVendedor').find('#txtIdVendedor').val();
    }
    $.ajax({
        url: contextoGlobal + '/controller/vendedorController?method=6'
        , type: 'post'
        , dataType: 'html'
        , cache: false
        , data: {
            method: 6
            , txtIdVendedor: idVendedor
            , ajax: true
        }
        , success: function (response) {
            $('#divDown').find('#divProductoVendedor').html("");
            $('#divDown').find('#divProductoVendedor').append(response);
        }
        , error: function (jqXHR, textStatus, errorThrown) {
            console.log("ERROR L: " + textStatus + " (" + errorThrown + ")");
        }
    });
}

function buscarProductoVendedorPersonalizado(idVendedor) {

    if (idVendedor == undefined) {
        idVendedor = $('#divModificarVendedor').find('#txtIdVendedor').val();
        //idVendedor = $('#divBajaVendedor').find('#txtIdVendedor').val();         
    }
    $.ajax({
        url: contextoGlobal + '/controller/vendedorController'
        , type: 'post'
        , dataType: 'html'
        , cache: false
        , data: {
            method: 7
            , txtIdVendedor: idVendedor
            , ajax: true
        }
        , success: function (response) {
            $('#divDown').find('#divProductoVendedor').html("");
            $('#divDown').find('#divProductoVendedor').append(response);
        }
        , error: function (jqXHR, textStatus, errorThrown) {
            console.log("ERROR L: " + textStatus + " (" + errorThrown + ")");
        }
    });
}

function getDataVendedor(idVendedor) {
    $.ajax({
        url: contextoGlobal + '/controller/vendedorController'
        , type: 'post'
        , dataType: 'json'
        , cache: false
        , data: {
            method: 5
            , txtIdVendedor: idVendedor
            , ajax: true
        }
        , success: function (response) {
            $('#divDown').find('#lblTitulo').html('');
            $('#divBajaVendedor').find('#delegacionMunicipio').empty();
            $('#divBajaVendedor').find('#txtIdVendedor').val(response.idVendedor);
            $('#divBajaVendedor').find('#txtNombre').val(response.nombre);
            $('#divBajaVendedor').find('#txtApellidoPaterno').val(response.apellidoPaterno);
            $('#divBajaVendedor').find('#txtApellidoMaterno').val(response.apellidoMaterno);
            $('#divBajaVendedor').find('#txtRfc').val(response.rfc);
            $('#divBajaVendedor').find('#txtCalle').val(response.calle);
            $('#divBajaVendedor').find('#txtNoExterior').val(response.noExterior);
            $('#divBajaVendedor').find('#txtNoInterior').val(response.noInterior);
            $('#divBajaVendedor').find('#txtColonia').val(response.colonia);
            $('#divBajaVendedor').find('#txtCp').val(response.cp);
            if (response.delegacion != '') {
                $('#divBajaVendedor').find('#delegacionMunicipio').append('<td><span class="text-muted" >Delegaci&oacute;n :</span></td>'
                        + '<td><input type="text" id="txtDelegacion" name="txtDelegacion" class="required" value="'
                        + response.delegacion + '" readOnly="readOnly" /></td>');
            } else {
                $('#divBajaVendedor').find('#delegacionMunicipio').append('<td><span class="text-muted" >Municipio :</span></td>'
                        + '<td><input type="text" id="txtMunicipio" name="txtMunicipio" class="required" value="'
                        + response.municipio + '" readOnly="readOnly" /></td>');
            }
            $('#divBajaVendedor').find('#txtEstado').val(response.estado);
            $('#divBajaVendedor').find('#txtCiudad').val(response.ciudad);
            $('#divBajaVendedor').find('#txtTel1').val(response.telefono1);
            $('#divBajaVendedor').find('#txtTel2').val(response.telefono2);
            $('#divBajaVendedor').find('#txtTel3').val(response.telefono3);
            $('#divBajaVendedor').find('#txtMail1').val(response.correo1);
            $('#divBajaVendedor').find('#txtMail2').val(response.correo2);
            $('#divBajaVendedor').find('#txtMail3').val(response.correo3);
            if (response.comision != '0.00') {
                $('#divBajaVendedor').find('input:radio[name=rdbVendedor][value=1]').attr('checked', true);
                $('#divBajaVendedor').find('#txtPorcentaje').val(response.comision * 100);
                $('#divBajaVendedor').find('#divComisiones').show();
            } else {
                $('#divBajaVendedor').find('input:radio[name=rdbVendedor][value=1]').prop('checked', false);
                $('#divBajaVendedor').find('#divComisiones').hide();
            }
            if (response.externo) {
                $('#divBajaVendedor').find('#chkExterno').prop("checked", true);
                if ((response.establecidos != undefined)) {
                    if (response.establecidos.length > 0) {
                        //  buscarProductoVendedor();
                        $('#divBajaVendedor').find('input:radio[name=rdbProducto][value=1]').prop('checked', true);
                    }
                } else
                if ((response.personalizados != undefined)) {
                    if (response.personalizados.length > 0) {
                        //  buscarProductoVendedorPersonalizado();
                        $('#divBajaVendedor').find('input:radio[name=rdbProducto][value=2]').prop('checked', true);
                    }
                }
            } else {
                $('#divBajaVendedor').find('#chkExterno').prop("checked", false);
                $('#divBajaVendedor').find('input:radio[name=rdbProducto][value=1]').prop('checked', false);
                $('#divBajaVendedor').find('input:radio[name=rdbProducto][value=2]').prop('checked', false);
                $('#divDown').find('#divProductoVendedor').empty();
            }

            /* if (response.credito.idCredito != null) {
             $('#divBajaVendedor').find('#chkCredito').prop("checked", true);
             if (response.credito.tipoCredito == 'monetario') {
             $('#divBajaVendedor').find('input:radio[name=rdbCredito][value=1]').attr('checked', true);
             $('#divBajaVendedor').find('#txtMonetario').val(response.credito.cantidadMonetaria);
             $('#divBajaVendedor').find('#divMonetario').show();
             } else if (response.credito.tipoCredito == 'contraNota') {
             $('#divBajaVendedor').find('input:radio[name=rdbCredito][value=2]').attr('checked', true);
             $('#divBajaVendedor').find('#txtMonetario').empty();
             $('#divBajaVendedor').find('#divMonetario').hide();
             } else if (response.credito.tipoCredito == 'plazo') {
             $('#divBajaVendedor').find('input:radio[name=rdbCredito][value=3]').attr('checked', true);
             $('#divBajaVendedor').find('#txtMonetario').empty();
             $('#divBajaVendedor').find('#divMonetario').hide();
             }
             $('#divBajaVendedor').find('#divCreditos').show();
             }*/
            $('#divBajaVendedor').find('#rdbVendedor').find('#chkExterno').off("click");
            $('#divBajaVendedor').find('#chkExterno').off("click");
            $('#divBajaVendedor').find('#rdbProducto').off("click");
            /*$('#divBajaVendedor').find('#chkCredito').off("click");
             $('#divBajaVendedor').find('#rdbCredito').off("click");*/
        }
        , error: function (jqXHR, textStatus, errorThrown) {
            console.log("ERROR L: " + textStatus + " (" + errorThrown + ")");
        }
    });
}

function getDataVendedorCambios(idVendedor) {
    $.ajax({
        url: contextoGlobal + '/controller/vendedorController'
        , type: 'post'
        , dataType: 'json'
        , cache: false
        , data: {
            method: 5
            , txtIdVendedor: idVendedor
            , ajax: true
        }
        , success: function (response) {
            $('#divDown').find('#lblTitulo').html('');
            $('#divModificarVendedor').find('#delegacionMunicipio').empty();
            $('#divModificarVendedor').find('#txtIdVendedor').val(response.idVendedor);
            $('#divModificarVendedor').find('#txtNombre').val(response.nombre);
            $('#divModificarVendedor').find('#txtApellidoPaterno').val(response.apellidoPaterno);
            $('#divModificarVendedor').find('#txtApellidoMaterno').val(response.apellidoMaterno);
            $('#divModificarVendedor').find('#txtRfc').val(response.rfc);
            $('#divModificarVendedor').find('#txtCalle').val(response.calle);
            $('#divModificarVendedor').find('#txtNoExterior').val(response.noExterior);
            $('#divModificarVendedor').find('#txtNoInterior').val(response.noInterior);
            $('#divModificarVendedor').find('#txtColonia').val(response.colonia);
            $('#divModificarVendedor').find('#txtCp').val(response.cp);
            if (response.delegacion != '') {
                $('#divModificarVendedor').find('#delegacionMunicipio').append('<td><span class="text-muted" >Delegaci&oacute;n :</span></td>'
                        + '<td><input type="text" id="txtDelegacion" name="txtDelegacion" class="required" value="'
                        + response.delegacion + '" readOnly="readOnly" /></td>');
            } else {
                $('#divModificarVendedor').find('#delegacionMunicipio').append('<td><span class="text-muted" >Municipio :</span></td>'
                        + '<td><input type="text" id="txtMunicipio" name="txtMunicipio" class="required" value="'
                        + response.municipio + '" readOnly="readOnly" /></td>');
            }
            $('#divModificarVendedor').find('#txtEstado').val(response.estado);
            $('#divModificarVendedor').find('#txtCiudad').val(response.ciudad);
            $('#divModificarVendedor').find('#txtTel1').val(response.telefono1);
            $('#divModificarVendedor').find('#txtTel2').val(response.telefono2);
            $('#divModificarVendedor').find('#txtTel3').val(response.telefono3);
            $('#divModificarVendedor').find('#txtMail1').val(response.correo1);
            $('#divModificarVendedor').find('#txtMail2').val(response.correo2);
            $('#divModificarVendedor').find('#txtMail3').val(response.correo3);
            if (response.comision != '0.00') {
                $('#divModificarVendedor').find('input:radio[name=rdbVendedor][value=1]').prop('checked', true);
                $('#divModificarVendedor').find('#txtPorcentaje').val(response.comision * 100);
                $('#divModificarVendedor').find('#divComisiones').show();
            } else {
                $('#divModificarVendedor').find('input:radio[name=rdbVendedor][value=1]').prop('checked', false);
                $('#divModificarVendedor').find('#divComisiones').hide();
            }
            if (response.externo) {
                $('#divModificarVendedor').find('#chkExterno').prop("checked", true);
                if ((response.establecidos != undefined)) {
                    if (response.establecidos.length > 0) {
                        buscarProductoVendedor(idVendedor);
                        $('#divModificarVendedor').find('input:radio[name=rdbProducto][value=1]').prop('checked', true);
                        $('#divDown').show();
                    }
                } else
                if ((response.personalizados != undefined)) {
                    if (response.personalizados.length > 0) {
                        buscarProductoVendedorPersonalizado(idVendedor);
                        $('#divModificarVendedor').find('input:radio[name=rdbProducto][value=2]').prop('checked', true);
                        $('#divDown').show();
                    }
                }
            } else {
                $('#divModificarVendedor').find('#lblTitulo').append('No Existen Productos');
                $('#divDown').find('#divProductoVendedor').empty();
            }

            /*if (response.credito.idCredito != null) {
             $('#divModificarVendedor').find('#chkCredito').prop("checked", true);
             if (response.credito.tipoCredito == 'monetario') {
             $('#divModificarVendedor').find('input:radio[name=rdbCredito][value=1]').attr('checked', true);
             $('#divModificarVendedor').find('#txtMonetario').val(response.credito.cantidadMonetaria);
             $('#divModificarVendedor').find('#divMonetario').show();
             } else if (response.credito.tipoCredito == 'contraNota') {
             $('#divModificarVendedor').find('input:radio[name=rdbCredito][value=2]').attr('checked', true);
             $('#divModificarVendedor').find('#txtMonetario').empty();
             $('#divModificarVendedor').find('#divMonetario').hide();
             } else if (response.credito.tipoCredito == 'plazo') {
             $('#divModificarVendedor').find('input:radio[name=rdbCredito][value=3]').attr('checked', true);
             $('#divModificarVendedor').find('#txtMonetario').empty();
             $('#divModificarVendedor').find('#divMonetario').hide();
             }
             $('#divModificarVendedor').find('#divCreditos').show();
             }*/

            if ((response.establecidos != undefined)) {
                if (response.establecidos.length > 0) {
                    buscarProductoVendedor(idVendedor);
                    $('#divModificarVendedor').find('input:radio[name=rdbProducto][value=1]').prop('checked', true);
                    $('#divDown').show();
                }
            } else if ((response.personalizados != undefined)) {
                if (response.personalizados.length > 0) {
                    buscarProductoVendedorPersonalizado(idVendedor);
                    $('#divModificarVendedor').find('input:radio[name=rdbProducto][value=2]').prop('checked', true);
                    $('#divDown').show();
                }
            }
        }
        , error: function (jqXHR, textStatus, errorThrown) {
            console.log("ERROR L: " + textStatus + " (" + errorThrown + ")");
        }
    });
}

function baja() {
    if ($('#frmActivarVendedor').find('#chkVisible:checked').val()) {
        if ($('#divBajaVendedor').find('#chkVisible:checked').val()) {
            $('#divActivarVendedor').dialog('open');
        }
    }
}

function validarReporteVendedor() {
    var opciones = document.getElementsByName("rdbFormatoReporte");
    var seleccionado = false;
    for (var i = 0; i < opciones.length; i++) {
        if (opciones[i].checked) {
            seleccionado = true;
            break;
        }
    }
    return seleccionado;
}