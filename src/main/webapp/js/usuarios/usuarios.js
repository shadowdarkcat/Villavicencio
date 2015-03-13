$(document).ready(function () {
    $('#tblUsuarios').DataTable({
        language: {
            url: contextoGlobal + '/resource/es_ES.json'
        }
    });

    var table = $('#tblUsuarios').DataTable();

    if ($('#divUsuarioNoSelected').length > 0) {
        $('#divUsuarioNoSelected').dialog({
            resizable: false
            , width: 300
            , height: 150
            , modal: true
            , autoOpen: false
            , cache: false
            , buttons: {
                Cerrar: function () {
                    $(this).dialog('close');
                }
            }
        });
    }

    if ($('#divExisteUsuario').length > 0) {
        $('#divExisteUsuario').dialog({
            resizable: false
            , width: 200
            , height: 130
            , modal: true
            , autoOpen: false
            , buttons: {
                Cerrar: function () {
                    $(this).dialog('close');
                }
            }
        });
    }

    if ($('#divAltaUsuario').length > 0) {
        $('#divAltaUsuario').dialog({
            resizable: false
            , width: 500
            , height: 500
            , modal: true
            , autoOpen: false
            , buttons: {
                Aceptar: function () {
                    if ($('#frmAltaUsuario').validate().form()) {
                        if ($('#txtPwd').val() == $('#txtPwd1').val()) {
                            $('#frmAltaUsuario').submit();
                        }
                    } else {
                        $('#cboPuesto').focus();
                    }
                }
                , Cerrar: function () {
                    $(this).dialog('close');
                }
            }
        });
    }

    $('#cboPuesto').change(function () {
        var idCbo = $('#cboPuesto option:selected').val();
        if (idCbo != 'VENDEDOR') {
            $('#cboTextName').val('');
            $('#trCboVendedor').hide();
            $('#trNombre').show();
            $('#txtNombre').addClass('required');
        } else {
            $('#trNombre').hide();
            $('#trCboVendedor').show();
        }
        if (idCbo != 'ALMACEN') {
            $('#menuAdministrador').show();
            $('#menuAlmacen').hide();
        } else {
            $('#menuAdministrador').hide();
            $('#menuAlmacen').show();
        }
    });

    $('#cboVendedor').change(function () {
        $('#cboTextName').val('');
        var idCboVendedor = $('#cboVendedor option:selected').val();
        if (idCboVendedor != 0) {
            var name = $('#cboVendedor option:selected').html();
            $('#cboTextName').val(name);
            $('#txtNombre').removeClass();
        }
    });

    if ($('#divModificarUsuario').length > 0) {
        $('#divModificarUsuario').dialog({
            resizable: false
            , width: 500
            , height: 500
            , modal: true
            , autoOpen: false
            , buttons: {
                Aceptar: function () {
                    $('#divMessageModificar').dialog('open');
                }
                , Cerrar: function () {
                    $(this).dialog('close');
                }
            }
        });
    }

    if ($('#divMessageModificar').length > 0) {
        $('#divMessageModificar').dialog({
            resizable: false
            , width: 300
            , height: 230
            , modal: true
            , autoOpen: false
            , buttons: {
                Aceptar: function () {
                    if ($('#frmModificarUsuario').validate().form()) {
                        $('#frmModificarUsuario').submit();
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

    if ($('#divMessageModificarPrivilegios').length > 0) {
        $('#divMessageModificarPrivilegios').dialog({
            resizable: false
            , width: 300
            , height: 200
            , modal: true
            , autoOpen: false
            , buttons: {
                Aceptar: function () {
                    if ($('#frmModificarPrivilegios').validate().form()) {
                        $('#frmModificarPrivilegios').submit();
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

    if ($('#divModificarPrivilegio').length > 0) {
        $('#divModificarPrivilegio').dialog({
            resizable: false
            , width: 500
            , height: 500
            , modal: true
            , autoOpen: false
            , buttons: {
                Aceptar: function () {
                    $('#divMessageModificarPrivilegios').dialog('open');
                }
                , Cerrar: function () {
                    $(this).dialog('close');
                }
            }
        });
    }

    $(function () {
        var testModificar = $('#testModificar').val();
        if (testModificar) {
            $('#divModificarPrivilegio').dialog('open');
            var puesto = $('#divModificarPrivilegio').find('#txtPuestoResponse').val();
            if (puesto != 'ALMACEN') {
                $('#menuAdministrador').show();
                $('#menuAlmacen').hide();
            } else {
                $('#menuAdministrador').hide();
                $('#menuAlmacen').show();
            }
        }
    });

    $(function () {
        var exist = $('#existUsuario').val();
        if (exist) {
            $('#divExisteUsuario').dialog('open');
        }
    });

    $('#tblUsuarios tbody').on('click', 'tr', function () {
        $(this).removeClass('selected');
        table.$('tr.selected').removeClass('selected');
        $(this).addClass('selected');
    });

    $('#btnAltaUsuario').on('click', function () {
        $('#divAltaUsuario').dialog('open');
    });

    $('#btnCambioUsuario').on('click', function () {
        var idUsuario = $('.selected').find('#txtIdUsuario').val();
        if ((idUsuario != null) && (idUsuario != undefined) && (idUsuario != '')) {
            getDataUsuario(idUsuario);
            $('#divModificarUsuario').dialog('open');
        } else {
            $('#divUsuarioNoSelected').dialog('open');
        }
    });

    $('#btnCambioPrivilegio').on('click', function () {
        var idUsuario = $('.selected').find('#txtIdUsuario').val();
        var puesto = $('.selected').find('#txtPuesto').val();
        if ((idUsuario != null) && (idUsuario != undefined) && (idUsuario != '')) {
            $('#frmModificarPrivilegios').find('#txtIdUsuario').val(idUsuario);
            $('#frmModificarPrivilegios').find('#txtPuesto').val(puesto);
            $('#frmModificarPrivilegios').submit();
        } else {
            $('#divUsuarioNoSelected').dialog('open');
        }
    });
});

function getDataUsuario(idUsuario) {
    $.ajax({
        url: contextoGlobal + '/controller/usuariosController'
        , type: 'post'
        , dataType: 'json'
        , cache: false
        , data: {
            method: 5
            , txtIdUsuario: idUsuario
            , ajax: true
        }
        , success: function (response) {
            console.log(response);
            $('#divModificarUsuario').find('#txtIdUsuario').val(response.idUsuario);
            $('#divModificarUsuario').find('#cboPuesto').val(response.puesto);
            if (response.idVendedor != 0 && response.idVendedor != undefined) {
                $('#divModificarUsuario').find('#cboVendedor').val(response.idVendedor);
                $('#divModificarUsuario').find('#cboTextName').val(response.nombreCompleto);
                $('#divModificarUsuario').find('#trNombre').hide();
                $('#divModificarUsuario').find('#trCboVendedor').show();
                $('#divModificarUsuario').find('#txtNombre').removeClass();
            } else {
                $('#divModificarUsuario').find('#txtNombre').val(response.nombreCompleto);
                $('#divModificarUsuario').find('#trCboVendedor').hide();
                $('#divModificarUsuario').find('#trNombre').show();
                $('#divModificarUsuario').find('#txtNombre').addClass('required');
            }
            $('#divModificarUsuario').find('#txtTel1').val(response.noTelefono);
            $('#divModificarUsuario').find('#txtNombreUsuario').val(response.nombreUsuario);
        }
        , error: function (jqXHR, textStatus, errorThrown) {
            console.log("ERROR L: " + textStatus + " (" + errorThrown + ")");
        }
    });
}
function equal() {
    var pass1 = $('#txtPwd').val();
    var pass2 = $('#txtPwd1').val();

    if (pass1 != pass2) {
        $('#error').show();
    } else {
        $('#error').hide();
    }
}
