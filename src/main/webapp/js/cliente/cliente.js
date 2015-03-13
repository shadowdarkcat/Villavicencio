$(document).ready(function () {

    $('#tblClientes').DataTable({
        language: {
            url: contextoGlobal + '/resource/es_ES.json'
        }
    });

    var table = $('#tblClientes').DataTable();

    if ($('#divTipoReporte').length > 0) {
        $('#divTipoReporte').dialog({
            resizable: false
            , width: 300
            , height: 150
            , autoOpen: false
            , cache: false
            , modal: true
            , buttons: {
                Aceptar: function () {
                    if (!validarReporteCliente()) {
                        $('#errorReporteCliente').dialog('open');
                    } else {
                        $('#frmReporteClientes').submit();
                    }
                }
                , Cerrar: function () {
                    $(this).dialog('close');
                }
            }
        });
    }

    if ($('#errorReporteCliente').length > 0) {
        $('#errorReporteCliente').dialog({
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

    if ($('#divClienteNoSelected').length > 0) {
        $('#divClienteNoSelected').dialog({
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

    if ($('#divActivarCliente').length > 0) {
        $('#divActivarCliente').dialog({
            resizable: false
            , width: 300
            , height: 150
            , autoOpen: false
            , cache: false
            , modal: true
            , buttons: {
                Aceptar: function () {
                    if ($('#frmActivarCliente').validate().form()) {
                        $('#frmActivarCliente').submit();
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

    if ($('#divExisteCliente').length > 0) {
        $('#divExisteCliente').dialog({
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

    if ($('#divAltaCliente').length > 0) {
        $('#divAltaCliente').dialog({
            resizable: false
            , width: 1270
            , height: 830
            , modal: true
            , autoOpen: false
            , closeOnEscape: false
            , dialogClass: 'no-close'
            , buttons: {
                Aceptar: function () {
                    if ($('#frmAltaCliente').validate().form()) {
                        $('#frmAltaCliente').submit();
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

    if ($('#divMessageBajaCliente').length > 0) {
        $('#divMessageBajaCliente').dialog({
            resizable: false
            , width: 280
            , height: 200
            , modal: true
            , autoOpen: false
            , buttons: {
                Aceptar: function () {
                    $('#frmBajaCliente').submit();
                    $('#divLateral').show();
                }
                , Cerrar: function () {
                    $(this).dialog('close');
                }
            }
        });
    }

    if ($('#divBajaCliente').length > 0) {
        $('#divBajaCliente').dialog({
            resizable: false
            , width: 1270
            , height: 830
            , modal: true
            , autoOpen: false
            , closeOnEscape: false
            , dialogClass: 'no-close'
            , buttons: {
                Aceptar: function () {
                    $('#divMessageBajaCliente').dialog('open');
                }
                , Cerrar: function () {
                    $(this).dialog('close');
                    $('#divLateral').show();
                }
            }
        });
    }

    $('#chkCredito').click(function () {
        if ($('#chkCredito').is(':checked')) {
            $('#divCreditos').show();
        } else {
            $('#divCreditos').hide();
            for (var i = 0; i < document.frmAltaCliente.rdbCredito.length; i++) {
                document.frmAltaCliente.rdbCredito[i].checked = false;
            }
            $('#divMonetario').hide();
            $('#txtMonetario').removeClass('required');
        }
    });

    if ($('#divMessageModificarCliente').length > 0) {
        $('#divMessageModificarCliente').dialog({
            resizable: false
            , width: 280
            , height: 200
            , modal: true
            , autoOpen: false
            , buttons: {
                Aceptar: function () {
                    if ($('#frmModificarCliente').validate().form()) {
                        $('#frmModificarCliente').submit();
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

    if ($('#divModificarCliente').length > 0) {
        $('#divModificarCliente').dialog({
            resizable: false
            , width: 1270
            , height: 830
            , modal: true
            , autoOpen: false
            , closeOnEscape: false
            , dialogClass: 'no-close'
            , buttons: {
                Aceptar: function () {
                    $('#divMessageModificarCliente').dialog('open');
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

    $(function () {
        var exist = $('#existCliente').val();
        if (exist) {
            $('#divExisteCliente').dialog('open');
        }
    });

    $('#btnAltaCliente').on('click', function () {
        loadColonia();
        $('#divLateral').hide();
        $('#divAltaCliente').dialog('open');
    });


    $('#tblClientes tbody').on('click', 'tr', function () {
        $(this).removeClass('selected');
        table.$('tr.selected').removeClass('selected');
        $(this).addClass('selected');
    });

    $('#btnBajaCliente').on('click', function () {
        var idCliente = $('.selected').find('#txtIdCliente').val();
        var visible = $('.selected').find('#chkVisible:checked').val();
        if ((idCliente != null) && (idCliente != undefined)) {
            if ((visible != false) && (visible != undefined)) {
                getDataCliente(idCliente);
                $('#divLateral').hide();
                $('#divBajaCliente').dialog('open');
            } else {
                $('#divNoVisible').dialog('open');
            }
        } else {
            $('#divClienteNoSelected').dialog('open');
        }
    });

    $('#btnCambioCliente').on('click', function () {
        $("#tblProducto > tbody").html("");
        $("#lblTitulo").html("");
        var idCliente = $('.selected').find('#txtIdCliente').val();
        var visible = $('.selected').find('#chkVisible:checked').val();
        $('#divModificarCliente').find('#txtIdCliente').val(idCliente);
        if ((idCliente != null) && (idCliente != undefined)) {
            if ((visible != false) && (visible != undefined)) {
                getDataClienteCambios(idCliente);
                $('#divLateral').hide();
                $('#divModificarCliente').dialog('open');
            } else {
                $('#divNoVisible').dialog('open');
            }
        } else {
            $('#divClienteNoSelected').dialog('open');
        }
    });

    $('#btnGenerarCliente').on('click', function () {
        $('#divTipoReporte').dialog('open');
    });

});

function creditoCliente() {
    var id;
    var len = document.frmAltaCliente.rdbCredito.length;
    for (var i = 0; i < len; i++) {
        if (document.frmAltaCliente.rdbCredito[i].checked) {
            id = document.frmAltaCliente.rdbCredito[i].value;
            if (id == 1) {
                $('#divMonetarioCliente').show();
                $('#txtMonetario').addClass('required');
                $('#txtMonetario').val('');
            } else if (id == 3) {
                $('#divMonetarioCliente').show();
                $('#txtMonetario').addClass('required');
                $('#txtMonetario').val('');
            } else {
                $('#divMonetarioCliente').hide();
                $('#txtMonetario').removeClass('required');
                $('#txtMonetario').val('');
            }
            break;
        }
    }
}

function loadColonia() {
    var availableTags = [];
    $.ajax({
        url: contextoGlobal + '/controller/clienteController'
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
                    availableTags.push(item.colonia.toUpperCase() + ',' + item.delegacion.toUpperCase() + ',' + item.codigoPostal + ',' + item.estado);
                } else {
                    availableTags.push(item.colonia.toUpperCase() + ',' + item.municipio.toUpperCase() + ',' + item.codigoPostal + ',' + item.estado);
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

function buscarProductoCliente(idCliente) {
    if (idCliente == undefined) {
        idCliente = $('#divModificarCliente').find('#txtIdCliente').val();
    }
    $.ajax({
        url: contextoGlobal + '/controller/clienteController?method=6'
        , type: 'post'
        , dataType: 'html'
        , cache: false
        , data: {
            method: 6
            , txtIdCliente: idCliente
            , ajax: true
        }
        , success: function (response) {
            $('#divDown').find('#divProductoCliente').html("");
            $('#divDown').find('#divProductoCliente').append(response);
        }
        , error: function (jqXHR, textStatus, errorThrown) {
            console.log("ERROR L: " + textStatus + " (" + errorThrown + ")");
        }
    });
}

function buscarProductoClientePersonalizado(idCliente) {
    if (idCliente == undefined) {
        idCliente = $('#divModificarCliente').find('#txtIdCliente').val();
    }
    $.ajax({
        url: contextoGlobal + '/controller/clienteController'
        , type: 'post'
        , dataType: 'html'
        , cache: false
        , data: {
            method: 7
            , txtIdCliente: idCliente
            , ajax: true
        }
        , success: function (response) {
            $('#divDown').find('#divProductoCliente').html("");
            $('#divDown').find('#divProductoCliente').append(response);
        }
        , error: function (jqXHR, textStatus, errorThrown) {
            console.log("ERROR L: " + textStatus + " (" + errorThrown + ")");
        }
    });
}

function getDataCliente(idCliente) {
    $.ajax({
        url: contextoGlobal + '/controller/clienteController'
        , type: 'post'
        , dataType: 'json'
        , cache: false
        , data: {
            method: 5
            , txtIdCliente: idCliente
            , ajax: true
        }
        , success: function (response) {
            $('#divDown').find('#lblTitulo').html('');
            $('#divBajaCliente').find('#delegacionMunicipio').empty();
            $('#divBajaCliente').find('#txtIdCliente').val(response.idCliente);
            $('#divBajaCliente').find('#txtEmpresa').val(response.empresa);
            $('#divBajaCliente').find('#txtRazonSocial').val(response.razonSocial);
            $('#divBajaCliente').find('#txtRfc').val(response.rfc);
            $('#divBajaCliente').find('#txtCorreoEmpresa').val(response.correoEmpresa);
            $('#divBajaCliente').find('#txtSitioWeb').val(response.sitioWeb);

            $('#divBajaCliente').find('#txtNombre').val(response.nombre);
            $('#divBajaCliente').find('#txtApellidoPaterno').val(response.apellidoPaterno);
            $('#divBajaCliente').find('#txtApellidoMaterno').val(response.apellidoMaterno);

            $('#divBajaCliente').find('#txtCalle').val(response.calle);
            $('#divBajaCliente').find('#txtNoExterior').val(response.noExterior);
            $('#divBajaCliente').find('#txtNoInterior').val(response.noInterior);
            $('#divBajaCliente').find('#txtColonia').val(response.colonia);
            $('#divBajaCliente').find('#txtCp').val(response.cp);
            if (response.delegacion != '') {
                $('#divBajaCliente').find('#delegacionMunicipio').append('<td><span class="text-muted" >Delegaci&oacute;n :</span></td>'
                        + '<td><input type="text" id="txtDelegacion" name="txtDelegacion" class="required" value="'
                        + response.delegacion + '" readOnly="readOnly" /></td>');
            } else {
                $('#divBajaCliente').find('#delegacionMunicipio').append('<td><span class="text-muted" >Municipio :</span></td>'
                        + '<td><input type="text" id="txtMunicipio" name="txtMunicipio" class="required" value="'
                        + response.municipio + '" readOnly="readOnly" /></td>');
            }
            $('#divBajaCliente').find('#txtEstado').val(response.estado);
            $('#divBajaCliente').find('#txtCiudad').val(response.ciudad);
            $('#divBajaCliente').find('#txtTel1').val(response.telefono1);
            $('#divBajaCliente').find('#txtTel2').val(response.telefono2);
            $('#divBajaCliente').find('#txtTel3').val(response.telefono3);
            $('#divBajaCliente').find('#txtMail1').val(response.correo1);
            $('#divBajaCliente').find('#txtMail2').val(response.correo2);
            $('#divBajaCliente').find('#txtMail3').val(response.correo3);

            if (response.credito.tipoCredito != null && response.credito.tipoCredito != 'null' && response.credito.tipoCredito != undefined) {
                if (response.credito.tipoCredito == 'MONETARIO') {
                    $('#divBajaCliente').find('input:radio[name=rdbCredito][value=1]').prop('checked', true);
                    $('#divBajaCliente').find('#txtMonetario').val(response.credito.cantidadMonetaria);
                    $('#divBajaCliente').find('#divMonetario').show();
                } else if (response.credito.tipoCredito == 'CONTRANOTA') {
                    $('#divBajaCliente').find('input:radio[name=rdbCredito][value=2]').prop('checked', true);
                    $('#divBajaCliente').find('#txtMonetario').empty();
                    $('#divBajaCliente').find('#divMonetario').hide();
                } else if (response.credito.tipoCredito == 'PLAZO') {
                    $('#divBajaCliente').find('input:radio[name=rdbCredito][value=3]').prop('checked', true);
                    $('#divBajaCliente').find('#txtMonetario').empty();
                    $('#divBajaCliente').find('#divMonetario').hide();
                }
                $('#divBajaCliente').find('#chkCredito').prop('checked', true);
                $('#divModificarCliente').find('#divCreditos').show();
            }

            $('#divBajaCliente').find('#rdbCliente').find('#chkExterno').off("click");
            $('#divBajaCliente').find('#rdbProducto').off('click');
            $('#divBajaCliente').find('#chkCredito').off('click');
            $('#divBajaCliente').find('#rdbCredito').off('click');

            $('#divBajaCliente').find('#txtContacto1').val(response.contacto1);
            $('#divBajaCliente').find('#txtContacto2').val(response.contacto2);
            $('#divBajaCliente').find('#txtContacto3').val(response.contacto3);
        }
        , error: function (jqXHR, textStatus, errorThrown) {
            console.log("ERROR L: " + textStatus + " (" + errorThrown + ")");
        }
    });
}

function getDataClienteCambios(idCliente) {
    $.ajax({
        url: contextoGlobal + '/controller/clienteController'
        , type: 'post'
        , dataType: 'json'
        , cache: false
        , data: {
            method: 5
            , txtIdCliente: idCliente
            , ajax: true
        }
        , success: function (response) {
            $('#divDown').find('#lblTitulo').html('');
            $('#divModificarCliente').find('#delegacionMunicipio').empty();
            $('#divModificarCliente').find('#txtIdCliente').val(idCliente);
            $('#divModificarCliente').find('#txtEmpresa').val(response.empresa);
            $('#divModificarCliente').find('#txtRazonSocial').val(response.razonSocial);
            $('#divModificarCliente').find('#txtCorreoEmpresa').val(response.correoEmpresa);
            $('#divModificarCliente').find('#txtSitioWeb').val(response.sitioWeb);
            $('#divModificarCliente').find('#txtNombre').val(response.nombre);
            $('#divModificarCliente').find('#txtApellidoPaterno').val(response.apellidoPaterno);
            $('#divModificarCliente').find('#txtApellidoMaterno').val(response.apellidoMaterno);
            $('#divModificarCliente').find('#txtRfc').val(response.rfc);
            $('#divModificarCliente').find('#txtCalle').val(response.calle);
            $('#divModificarCliente').find('#txtNoExterior').val(response.noExterior);
            $('#divModificarCliente').find('#txtNoInterior').val(response.noInterior);
            $('#divModificarCliente').find('#txtColonia').val(response.colonia);
            $('#divModificarCliente').find('#txtCp').val(response.codigoPostal);
            if (response.delegacion != '' && response.delegacion != 'null' && response.delegacion != undefined) {
                $('#divModificarCliente').find('#delegacionMunicipio').append('<td><span class="text-muted" >Delegaci&oacute;n :</span></td>'
                        + '<td><input type="text" id="txtDelegacion" name="txtDelegacion" class="required" value="'
                        + response.delegacion + '" readOnly="readOnly" /></td>');
            } else {
                $('#divModificarCliente').find('#delegacionMunicipio').append('<td><span class="text-muted" >Municipio :</span></td>'
                        + '<td><input type="text" id="txtMunicipio" name="txtMunicipio" class="required" value="'
                        + response.municipio + '" readOnly="readOnly" /></td>');
            }
            $('#divModificarCliente').find('#txtEstado').val(response.estado);
            $('#divModificarCliente').find('#txtCiudad').val(response.ciudad);
            $('#divModificarCliente').find('#txtTel1').val(response.telefono1);
            $('#divModificarCliente').find('#txtTel2').val(response.telefono2);
            $('#divModificarCliente').find('#txtTel3').val(response.telefono3);
            $('#divModificarCliente').find('#txtMail1').val(response.correo1);
            $('#divModificarCliente').find('#txtMail2').val(response.correo2);
            $('#divModificarCliente').find('#txtMail3').val(response.correo3);
            if (response.credito.tipoCredito != null && response.credito.tipoCredito != 'null' && response.credito.tipoCredito != undefined) {
                if (response.credito.tipoCredito == 'MONETARIO') {
                    $('#divModificarCliente').find('input:radio[name=rdbCreditoM][value=1]').prop('checked', true);
                    $('#divModificarCliente').find('#txtMonetario').val(response.credito.cantidadMonetaria);
                    $('#divModificarCliente').find('#divMonetarioCliente').show();
                } else if (response.credito.tipoCredito == 'CONTRANOTA') {
                    $('#divModificarCliente').find('input:radio[name=rdbCreditoM][value=2]').prop('checked', true);
                    $('#divModificarCliente').find('#txtMonetario').empty();
                    $('#divModificarCliente').find('#divMonetarioCliente').hide();
                } else if (response.credito.tipoCredito == 'PLAZO') {
                    $('#divModificarCliente').find('input:radio[name=rdbCreditoM][value=3]').prop('checked', true);
                    $('#divModificarCliente').find('#txtMonetario').val(response.credito.cantidadMonetaria);
                    $('#divModificarCliente').find('#divMonetarioCliente').show();
                }
                $('#divModificarCliente').find('#chkCredito').prop('checked', true);
                $('#divModificarCliente').find('#divCreditos').show();
            }
            if (response.vendedor.nombre != 'VILLAVICENCIO') {
                $('#divModificarCliente').find('#cboVendedor').val(response.vendedor.idVendedor);
            } else {
                $('#divModificarCliente').find('#cboVendedor').val('Seleccione ...');
            }
            $('#divModificarCliente').find('#txtContacto1').val(response.contacto1);
            $('#divModificarCliente').find('#txtContacto2').val(response.contacto2);
            $('#divModificarCliente').find('#txtContacto3').val(response.contacto3);

            if ((response.establecidos != undefined)) {
                if (response.establecidos.length > 0) {
                    buscarProductoCliente(idCliente);
                    $('#divModificarCliente').find('input:radio[name=rdbProducto][value=1]').prop('checked', true);
                    $('#divDown').show();
                }
            } else if ((response.personalizados != undefined)) {
                if (response.personalizados.length > 0) {
                    buscarProductoClientePersonalizado(idCliente);
                    $('#divModificarCliente').find('input:radio[name=rdbProducto][value=2]').prop('checked', true);
                    $('#divDown').show();
                }
            }
        }
        , error: function (jqXHR, textStatus, errorThrown) {
            console.log("ERROR L: " + textStatus + " (" + errorThrown + ")");
        }
    });
}
function bajaCliente() {
    if ($('#frmActivarCliente').find('#chkVisible:checked').val()) {
        if ($('#divBajaCliente').find('#chkVisible:checked').val()) {
            $('#divActivarCliente').dialog('open');
        }
    }
}

function creditosModificarCliente() {
    var id;
    var len = document.frmModificarCliente.rdbCreditoM.length;
    for (var i = 0; i < len; i++) {
        if (document.frmModificarCliente.rdbCreditoM[i].checked) {
            id = document.frmModificarCliente.rdbCreditoM[i].value;
            if (id == 1) {
                $('#divModificarCliente').find('#divMonetarioCliente').show();
                $('#divModificarCliente').find('#txtMonetario').addClass('required');
                $('#divModificarCliente').find('#txtMonetario').val('');
            } else if (id == 3) {
                $('#divModificarCliente').find('#divMonetarioCliente').show();
                $('#divModificarCliente').find('#txtMonetario').addClass('required');
                $('#divModificarCliente').find('#txtMonetario').val('');
            } else {
                $('#divModificarCliente').find('#divMonetarioCliente').hide();
                $('#divModificarCliente').find('#txtMonetario').removeClass('required');
                $('#divModificarCliente').find('#txtMonetario').val('');
            }
            break;
        }
    }
}

function validarReporteCliente() {
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