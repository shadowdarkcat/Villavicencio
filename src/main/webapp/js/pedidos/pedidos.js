var ids = [];
var nombres = [];
var costos = [];
var comisiones = [];
var pesos = [];
var piezas = [];
var pesoMinimo = [];
var pesoMaximo = [];
$(document).ready(function () {

    $('#tblPedidos').DataTable({
        language: {
            url: contextoGlobal + '/resource/es_ES.json'
        }
    });

    var table = $('#tblPedidos').DataTable();

    $(function () {
        jssor_slider1_starter = function (containerId) {
            var options = {
                $ArrowNavigatorOptions: {
                    $Class: $JssorArrowNavigator$,
                    $ChanceToShow: 2,
                    $AutoCenter: 2,
                    $Steps: 1
                }
            };
            var divSliderProductos = new $JssorSlider$(containerId, options);
        };
    });

    $('.solo-numero').keyup(function () {
        this.value = (this.value + '').replace(/[^0-9]/g, '');
    });

    if ($('#divTipoReportePedido').length > 0) {
        $('#divTipoReportePedido').dialog({
            resizable: false
            , width: 300
            , height: 150
            , autoOpen: false
            , cache: false
            , modal: true
            , buttons: {
                Aceptar: function () {
                    if (!validarReportePedido()) {
                        $('#errorReportePedidos').dialog('open');
                    } else {
                        $('#frmReportePedidos').submit();
                    }
                }
                , Cerrar: function () {
                    $(this).dialog('close');
                }
            }
        });
    }

    if ($('#errorReportePedidos').length > 0) {
        $('#errorReportePedidos').dialog({
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

    if ($('#divCampoVacio').length > 0) {
        $('#divCampoVacio').dialog({
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

    if ($('#divAltaPedido').length > 0) {
        $('#divAltaPedido').dialog({
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

    if ($('#divCapturaDatos').length > 0) {
        $('#divCapturaDatos').dialog({
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
                        var idProducto = $('#divCapturaDatos').find('#txtIdProducto').val();
                        var producto = $('#divCapturaDatos').find('#txtProducto').val();
                        var cantidad = $('#divCapturaDatos').find('#txtPiezas').val();
                        var costo = $('#divCapturaDatos').find('#txtCosto').val();
                        var peso = $('#divCapturaDatos').find('#txtPeso').val();
                        var comision = $('#divCapturaDatos').find('#txtComision').val();
                        var minimo = $('#divCapturaDatos').find('#txtMinimo').val();
                        var maximo = $('#divCapturaDatos').find('#txtMaximo').val();
                        getAdd(idProducto, producto, cantidad, costo, peso, comision, minimo, maximo);
                        $(this).dialog('close');
                    }
                }
                , Cerrar: function () {

                    $(this).dialog('close');
                }
            }
        });
    }

    if (('#divNotaPedido').length > 0) {
        $('#divNotaPedido').dialog({
            resizable: false
            , width: 700
            , height: 650
            , modal: true
            , cache: false
            , autoOpen: false
            , buttons: {
                Aceptar: function () {
                    $('#frmNotaPedido').submit();
                }
                , Cerrar: function () {
                    $(this).dialog('close');
                }
            }
        });
    }

    $('#btnFicha').on('click', function () {
        showData();
        $('#divNotaPedido').dialog('open');
    });

    $('#cboCliente').change(function () {
        $('#divNotaPedido').find('#txtIdVendedorCliente').val('');
        var idCliente = $('#cboCliente option:selected').val();
        $('#divNotaPedido').find('#txtIdCliente').val(idCliente);
        $('#divAltaPedido').find('#divSliderProductos').empty();
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
                if (response != null) {
                    $('#divNoProductos').hide();
                    var data = '<br/><div u="slides"  style="cursor: move; position: absolute; overflow: hidden; left: 0px; top: 0px; width: 600px; height: 300px;">'
                    $.each(response, function (index, item) {
                        items += '<div><center><input type="button" id="btnShowDiv" name="btnShowDiv" value="' + item.nombreProducto + '"'
                                + 'class="text-muted;" onclick="showDataCliente(' + item.idProducto + ',' + idCliente + ');" />'
                                + '</span></center><br/><center><img u="caption" id="'
                                + index
                                + '" name="' + index
                                + '" src="' + item.imagenProducto + '" height="100px" width="100px" /></center>'
                                + '</div>';
                    });
                } else {
                    $('#divNoProductos').show();
                }
                $('#divAltaPedido').find('#divSliderProductos').append(data + items
                        + '</div><span u="arrowleft" class="jssora06l" style="width: 45px; height: 45px; top: 123px; left: 8px;">'
                        + '</span><span u="arrowright" class="jssora06r" style="width: 45px; height: 45px; top: 123px; right: 8px">'
                        + '</span>');
                jssor_slider1_starter("divSliderProductos");
            }
            , error: function (jqXHR, textStatus, errorThrown) {
                $('#divAltaPedido').find('#divNoProductos').show();
                console.log('ERROR L: ' + textStatus + ' (' + errorThrown + ')');
            }
        });
    });

    $('#cboVendedor').change(function () {
        $('#divNotaPedido').find('#txtIdCliente').val('');
        var idVendedor = $('#cboVendedor option:selected').val();
        $('#divNotaPedido').find('#txtIdVendedorCliente').val(idVendedor);
        $('#divAltaPedido').find('#divSliderProductos').empty();
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
                if (response != null) {
                    $('#divAltaPedido').find('#divNoProductos').hide();
                    var data = '<br/><div u="slides"  style="cursor: move; position: absolute; overflow: hidden; left: 0px; top: 0px; width: 600px; height: 300px;">'
                    $.each(response, function (index, item) {
                        items += '<div><center><input type="button" id="btnShowDiv" name="btnShowDiv" value="' + item.nombreProducto + '"'
                                + 'class="text-muted;" onclick="showDataVendedor(' + item.idProducto + ',' + idVendedor + ');" />'
                                + '</span></center><br/><center><img u="caption" id="'
                                + index
                                + '" name="' + index
                                + '" src="' + item.imagenProducto + '" height="100px" width="100px" /></center>'
                                + '</div>';
                    });
                } else {
                    $('#divAltaPedido').find('#divNoProductos').show();
                }
                $('#divAltaPedido').find('#divSliderProductos').append(data + items
                        + '</div><span u="arrowleft" class="jssora06l" style="width: 45px; height: 45px; top: 123px; left: 8px;">'
                        + '</span><span u="arrowright" class="jssora06r" style="width: 45px; height: 45px; top: 123px; right: 8px">'
                        + '</span>');
                jssor_slider1_starter("divSliderProductos");
            }
            , error: function (jqXHR, textStatus, errorThrown) {
                $('#divAltaPedido').find('#divNoProductos').show();
                console.log('ERROR L: ' + textStatus + ' (' + errorThrown + ')');
            }
        });
    });

    $("input[name=rdbTipoCliente]").change(function () {
        var id = $(this).val();
        if (id == 1) {
            $('#divAltaPedido').find('#divCliente').show();
            $('#divAltaPedido').find('#cboVendedor').val('Seleccione ...');
            $('#divAltaPedido').find('#divVendedor').hide();
            $('#divAltaPedido').find('#divNoProductos').hide();
            $('#divAltaPedido').find('#divSliderProductos').empty();
        } else if (id == 2) {
            $('#divAltaPedido').find('#divVendedor').show();
            $('#divAltaPedido').find('#cboCliente').val('Seleccione ...');
            $('#divAltaPedido').find('#divCliente').hide();
            $('#divAltaPedido').find('#divNoProductos').hide();
            $('#divAltaPedido').find('#divSliderProductos').empty();
        }
    });

    $('#btnAltaPedido').on('click', function () {
        $('#divAltaPedido').dialog('open');
    });

    $('#tblPedidos tbody').on('click', 'tr', function () {
        $(this).removeClass('selected');
        table.$('tr.selected').removeClass('selected');
        $(this).addClass('selected');
    });

    $('#btnGenerarPedido').on('click', function () {
        $('#divTipoReportePedido').dialog('open');
    });

});

function showDataCliente(idProducto, idCliente) {
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
            var img = '<img src="' + response.imagenProducto + '" height="150px" width="120px"/>';
            $('#divCapturaDatos').find('#Izquierda').empty();
            $('#divCapturaDatos').find('#nombreProducto').empty();
            $('#divCapturaDatos').find('#txtPiezas').val('');
            $('#Izquierda').append(img);
            $('#divCapturaDatos').find('#nombreProducto').append(response.nombreProducto);
            $('#divCapturaDatos').find('#txtProducto').val(response.nombreProducto);
            $('#divCapturaDatos').find('#txtIdProducto').val(response.idProducto);
            $('#divCapturaDatos').find('#txtCosto').val(response.costoUnitario);
            $('#divCapturaDatos').find('#txtPeso').val(response.peso);
            $('#divCapturaDatos').find('#txtMinimo').val(response.pesoMinimo);
            $('#divCapturaDatos').find('#txtMaximo').val(response.pesoMaximo);
            $('#divCapturaDatos').find('#txtComision').val(response.comision);
            $('#divCapturaDatos').dialog('open');
        }
        , error: function (jqXHR, textStatus, errorThrown) {
            console.log('ERROR L: ' + textStatus + ' (' + errorThrown + ')');
        }
    });
}

function showDataVendedor(idProducto, idVendedor) {
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
            var img = '<img src="' + response.imagenProducto + '" height="150px" width="120px"/>';
            $('#divCapturaDatos').find('#Izquierda').empty();
            $('#divCapturaDatos').find('#nombreProducto').empty();
            $('#divCapturaDatos').find('#txtPiezas').val('');
            $('#Izquierda').append(img);
            $('#divCapturaDatos').find('#nombreProducto').append(response.nombreProducto);
            $('#divCapturaDatos').find('#txtProducto').val(response.nombreProducto);
            $('#divCapturaDatos').find('#txtIdProducto').val(response.idProducto);
            $('#divCapturaDatos').find('#txtCosto').val(response.costoUnitario);
            $('#divCapturaDatos').find('#txtPeso').val(response.peso);
            $('#divCapturaDatos').find('#txtMinimo').val(response.pesoMinimo);
            $('#divCapturaDatos').find('#txtMaximo').val(response.pesoMaximo);
            $('#divCapturaDatos').find('#txtComision').val(response.comision);
            $('#divCapturaDatos').dialog('open');
        }
        , error: function (jqXHR, textStatus, errorThrown) {
            console.log('ERROR L: ' + textStatus + ' (' + errorThrown + ')');
        }
    });
}

function getAdd(idProducto, producto, cantidad, costo, peso, comision, minimo, maximo) {
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
            $('#divCapturaDatos').find('#txtPiezas').empty();
            var tbody = '';
            tbody += ' <tr id="prod' + response.producto.idProducto + '">'
                    + '<td style="text-align: center; width:25px;">'
                    + '<input type="text" id="txtArrayNombreProducto" name="txtArrayNombreProducto" value="' + response.producto.nombreProducto + '" readOnly="readOnly"/></td>'
                    + '<td style="text-align: center">'
                    + '<input type="text" id="txtArrayPiezas" name="txtArrayPiezas" value="' + response.producto.piezas + '" readOnly="readOnly" class="required"/></td>'
                    + '<td> <img id="del" src="' + contextoGlobal + '/image/remove_cart.png" width="24" height="24" onClick="quitar(' + response.producto.idProducto + ');"'
                    + 'title="Elimina el producto ' + response.producto.nombreProducto + ' de la lista de pedido" />'
                    + '<input type="hidden" id="txtArrayCosto" name="txtArrayCosto" value="' + response.producto.costoUnitario + '" readOnly="readOnly"/>'
                    + '<input type="hidden" id="txtArrayComisiones" name="txtArrayComisiones" value="' + response.producto.comision + '" />'
                    + '<input type="hidden" id="txtArrayPeso" name="txtArrayPeso" value="' + response.producto.peso + '" />'
                    + '<input type="hidden" id="txtArrayIdProducto" name="txtArrayIdProducto" value="' + response.producto.idProducto + '" readOnly="readOnly"/>'
                    + '<input type="hidden" id="txtArrayPesoMinimo" name="txtArrayPesoMinimo" value="' + response.producto.pesoMinimo + '" readOnly="readOnly"/>'
                    + '<input type="hidden" id="txtArrayPesoMaximo" name="txtArrayPesoMaximo" value="' + response.producto.pesoMaximo + '" readOnly="readOnly"/>'
                    + '</td></tr>';
            ids.push(response.producto.idProducto);
            nombres.push(response.producto.nombreProducto);
            costos.push(response.producto.costoUnitario);
            comisiones.push(response.producto.comision);
            pesos.push(response.producto.peso);
            piezas.push(response.producto.piezas);
            pesoMinimo.push(response.producto.pesoMinimo);
            pesoMaximo.push(response.producto.pesoMaximo);
            $('#carritoPedido').find('#tblPedido > tbody').append(tbody);
            $('#carritoPedido').show();
        }
        , error: function (jqXHR, textStatus, errorThrown) {
            console.log('ERROR L: ' + textStatus + ' (' + errorThrown + ')');
        }
    });
}

function quitar(indexRow) {
    $("#prod" + indexRow).remove();
    if ($('#tblPedido >tbody >tr').length == 0) {
        $('#carritoPedido').hide();
        ids.length = 0;
        nombres.length = 0;
        costos.length = 0;
        comisiones.length = 0;
        pesos.length = 0;
        piezas.length = 0;
        pesoMinimo.length = 0;
        pesoMaximo.length = 0;
    } else {
        ids.splice(indexRow - 1, 1);
        nombres.splice(indexRow - 1, 1);
        costos.splice(indexRow - 1, 1);
        comisiones.splice(indexRow - 1, 1);
        pesos.splice(indexRow - 1, 1);
        piezas.splice(indexRow - 1, 1);
        pesoMinimo.splice(indexRow - 1, 1);
        pesoMaximo.splice(indexRow - 1, 1);
    }
}

function showData() {
    $.ajax({
        url: contextoGlobal + '/controller/pedidosController'
        , type: 'post'
        , dataType: 'json'
        , cache: false
        , data: {
            method: 7
            , txtArrayIdProducto: ids
            , txtArrayNombreProducto: nombres
            , txtArrayPiezas: piezas
            , txtArrayCosto: costos
            , txtArrayPeso: pesos
            , txtArrayComisiones: comisiones
            , txtArrayPesoMinimo: pesoMinimo
            , txtArrayPesoMaximo: pesoMaximo
            , ajax: true
        }
        , success: function (response) {
            $('#divNotaPedido').find('#tblNotaPedido >tbody').empty();
            var nombre = response.vendedor.nombre;
            if (nombre != 'VILLAVICENCIO') {
                nombre = response.vendedor.nombre + ' ' + response.vendedor.apellidoPaterno + ' ' + response.vendedor.apellidoMaterno;
            } else {
                nombre = response.vendedor.nombre;
            }
            $('#divNotaPedido').find('#txtFecha').val(response.strFecha);
            $('#divNotaPedido').find('#txtFolio').val(response.folio);
            $('#divNotaPedido').find('#txtVendedor').val(nombre);
            var tr;
            $.each(response.detalles, function (index, item) {
                tr += '<tr><td>'
                        + '<input type="text" id="txtArrayNombreProducto[]" name="txtArrayNombreProducto[]" readOnly="readOnly" value ="' + item.nombreProducto + '"/></td>'
                        + '<td><input type="text" id="txtArrayPiezas[]" name="txtArrayPiezas[]" readOnly="readOnly" value ="' + item.cantidadPiezas + '"/>'
                        + '<input type="hidden" id="txtArrayIdProducto[]" name="txtArrayIdProducto[]" readOnly="readOnly" value ="' + item.idProducto + '"/>'
                        + '<input type="hidden" id="txtArrayCosto[]" name="txtArrayCosto[]" readOnly="readOnly" value ="' + item.costoUnitario + '"/>'
                        + '<input type="hidden" id="txtArrayPeso[]" name="txtArrayPeso[]" readOnly="readOnly" value ="' + item.peso + '"/>'
                        + '<input type="hidden" id="txtArrayComisiones[]" name="txtArrayComisiones[]" readOnly="readOnly" value ="' + item.comision + '"/>'
                        + '<input type="hidden" id="txtArrayPesoMinimo[]" name="txtArrayPesoMinimo[]" value="' + item.pesoMinimo + '" readOnly="readOnly"/>'
                        + '<input type="hidden" id="txtArrayPesoMaximo[]" name="txtArrayPesoMaximo[]" value="' + item.pesoMaximo + '" readOnly="readOnly"/>'
                        + '</td></tr>';
            });
            $('#divNotaPedido').find('#tblNotaPedido >tbody').append(tr);
        }
        , error: function (jqXHR, textStatus, errorThrown) {
            console.log('ERROR L: ' + textStatus + ' (' + errorThrown + ')');
        }
    });
}

function buscar() {
    $('#divAltaPedido').find('#cboVendedor').change();
}

function validarReportePedido() {
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