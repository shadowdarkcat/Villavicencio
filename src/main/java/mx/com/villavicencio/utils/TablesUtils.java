package mx.com.villavicencio.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import java.util.Collection;
import java.util.Objects;
import mx.com.villavicencio.generics.variables.Variables;
import mx.com.villavicencio.system.cliente.dto.DtoCliente;
import mx.com.villavicencio.system.movimientos.abonos.dto.DtoAbonos;
import mx.com.villavicencio.system.movimientos.movimientos.dto.DtoMovimientos;
import mx.com.villavicencio.system.movimientos.movimientos.factory.MovimientosFactory;
import mx.com.villavicencio.system.productos.productos.dto.DtoProducto;
import mx.com.villavicencio.system.vendedor.dto.DtoVendedor;
import mx.com.villavicencio.system.venta.devoluciones.detalle.dto.DtoDetalleDevoluciones;
import mx.com.villavicencio.system.venta.nota.dto.DtoNotaVenta;

/**
 *
 * @author Gabriel J
 */
public class TablesUtils {

    public static String createTableProducto(Collection<DtoProducto> producto) {
        StringBuilder table = new StringBuilder();
        StringBuilder content = new StringBuilder();
        table.append("<br/><div class='contenido' id='divTablaTodos'><center><fieldset style='width: 700px;'>")
                .append("<center><label id='lblTituloCliente' style='font-size: 14px;'></label></center>")
                .append("<legend><span class='text-muted'>Tipo Producto Establecidos</span></legend>")
                .append("<div id='divInfoCliente'>")
                .append("<table id='tblProductoGeneral' class='table table-bordered table-hover' style='width: 700px;'>")
                .append(" <thead><tr><th style='text-align: center'>Producto</th>")
                .append("<th style='text-align: center'>Costo</th><th style='text-align: center'>Comisi&oacute;n</th></tr></thead>")
                .append("<tbody><tr class='${status.index % 2 eq 0 ? 'odd' : 'even'}' id='trProductos'>");
        for (DtoProducto productos : producto) {
            if (productos.getVisible() != false) {
                BigDecimal comision = BigDecimal.ZERO;
                if (productos.getComision() != null) {
                    comision = NumberUtils.convertPorcentajeToNumber(productos.getComision());
                }
                content.append("<tr><td style='text-align: center'>")
                        .append("<grouping id='txtIdProductos[]' name='txtIdProductos[]'>")
                        .append("<input type='hidden' id='txtIdsProducto' name='txtIdsProducto' value='")
                        .append(productos.getIdProducto()).append("' readOnly='readOnly' />")
                        .append(productos.getNombreProducto()).append("</td>")
                        .append("<td style='text-align: center'>").append("<grouping id='txtCosto[]' name='txtCosto[]'>")
                        .append("<input type='text' id='txtCostos' name='txtCostos' value='").append(productos.getCostoUnitario())
                        .append("' readOnly = 'readOnly' />").append("</td>")
                        .append("<td style='text-align: center'>").append("<grouping id='txtComision[]' name='txtComision[]'>")
                        .append("<input type='text' id='txtComisiones' name='txtComisiones' value='").append(comision)
                        .append("' />").append("</td>");
            }
        }
        table.append(content);
        table.append("</tbody></table></div></fieldset></center></div>");
        return table.toString();
    }

    public static String createTablePersonalizado(Collection<DtoProducto> producto) {
        StringBuilder table = new StringBuilder();
        StringBuilder content = new StringBuilder();
        table.append("<br/><div class='contenido' id='divTablaTodos'><center><fieldset style='width: 700px;'>")
                .append("<center><label id='lblTituloCliente' style='font-size: 14px;'></label></center>")
                .append("<legend><span class='text-muted'>Tipo Producto Personalizados</span></legend>")
                .append("<div id='divInfoCliente'>")
                .append("<table id='tblProductoGeneral' class='table table-bordered table-hover' style='width: 700px;'")
                .append(" <thead><tr><th style='text-align: center'>Producto</th>")
                .append("<th style='text-align: center'>Costo</th><th style='text-align: center'>Comisi&oacute;n</th></tr></thead>")
                .append("<tbody><tr class='${status.index % 2 eq 0 ? 'odd' : 'even'}' id='trProductos'>");
        for (DtoProducto productos : producto) {
            BigDecimal comision = BigDecimal.ZERO;
            if (productos.getComision() != null) {
                comision = NumberUtils.convertPorcentajeToNumber(productos.getComision());
            }
            content.append("<tr><td style='text-align: center'>")
                    .append("<grouping id='txtIdProductosPersonalizados[]' name='txtIdProductosPersonalizados[]'>")
                    .append("<input type='hidden' id='txtIdsProductoPersonalizado' name='txtIdsProductoPersonalizado' value='")
                    .append(productos.getIdProducto()).append("' readOnly='readOnly' />")
                    .append("<grouping id='txtNombreProductos[]' name='txtNombreProductos[]'>")
                    .append("<input type='text' id='txtNombresProductos' name='txtNombresProductos' value ='")
                    .append(productos.getNombreProducto()).append("' readOnly='readOnly'/>").append("</td>")
                    .append("<td style='text-align: center'>").append("<grouping id='txtCosto[]' name='txtCosto[]'>")
                    .append("<input type='text' id='txtCostos' name='txtCostos' value='").append(productos.getCostoUnitario())
                    .append("'/>").append("</td>")
                    .append("<td style='text-align: center'>").append("<grouping id='txtComision[]' name='txtComision[]'>")
                    .append("<input type='text' id='txtComisiones' name='txtComisiones' value='").append(comision)
                    .append("' />")
                    .append("<grouping id='txtClavesProductos[]' name='txtClavesProductos[]'>")
                    .append("<input type='hidden' id='txtClavesProducto' name='txtClavesProducto' value='")
                    .append(productos.getClaveProducto()).append("' readOnly='readOnly' />")
                    .append("<grouping id='txtPesoProductos[]' name='txtPesoProductos[]'>")
                    .append("<input type='hidden' id='txtPesosProducto' name='txtPesosProducto' value='")
                    .append(productos.getPeso()).append("' readOnly='readOnly' />")
                    .append("<grouping id='txtPesoMinimos[]' name='txtPesoMinimos[]'>")
                    .append("<input type='hidden' id='txtPesosMinimo' name='txtPesosMinimo' value='")
                    .append(productos.getPesoMinimo()).append("' readOnly='readOnly' />")
                    .append("<grouping id='txtPesoMaximos[]' name='txtPesoMaximos[]'>")
                    .append("<input type='hidden' id='txtPesoMaximos' name='txtPesoMaximos' value='")
                    .append(productos.getPesoMaximo()).append("' readOnly='readOnly' />")
                    .append("<grouping id='txtImagenProductos[]' name='txtImagenProductos[]'>")
                    .append("<input type='hidden' id='txtImagenesProducto' name='txtImagenesProducto' value='")
                    .append(productos.getImagenProducto()).append("' readOnly='readOnly' />")
                    .append("</td>");
        }
        table.append(content);
        table.append("</tbody></table></div></fieldset></center></div>");
        return table.toString();
    }

    public static DtoCliente createTableDetalles(DtoCliente cliente, String context) {

        StringBuilder table = new StringBuilder();
        StringBuilder content = new StringBuilder();
        BigDecimal totalAdeudo = BigDecimal.ZERO;
        BigDecimal totalAbonado = BigDecimal.ZERO;
        BigDecimal totalFaltante = BigDecimal.ZERO;

        table.append("<table id='tblDetallesMovimiento' class='table table-bordered table-hover' style='width: 90%;'>")
                .append("<thead><tr>")
                .append("<th style='text-align: center'>Folio Nota</th>")
                .append("<th style='text-align: center'>Tipo Nota</th>")
                .append("<th style='text-align: center'>Fecha</th>")
                .append("<th style='text-align: center'>Estatus</th>")
                .append("<th style='text-align: center'>Detalle</th>")
                .append("<th style='text-align: center'></th>")
                .append("</tr></thead>")
                .append("<tbody>");

        for (DtoMovimientos movs : cliente.getMovimientos()) {
            content.append("<tr>");
            if ((movs.getNotaVenta().getIdNotaVenta() != null) && (movs.getPedido().getIdPedido() != null)) {
                content.append("<td style='text-align: center'><input type='hidden' id='txtIdNotaVenta' name='txtIdNotaVenta' value='")
                        .append(movs.getNotaVenta().getIdNotaVenta()).append("' readOnly='readOnly' />")
                        .append(movs.getNotaVenta().getFolio()).append("</td>")
                        .append("<td style='text-align: center'>Venta</td>")
                        .append("<td style='text-align: center'>").append(DateUtils.dateToString(movs.getNotaVenta().getFecha())).append("</td>")
                        .append("<td style='text-align: center'>").append(movs.getNotaVenta().getStatusNotaVenta()).append("</td>")
                        .append("<td style='text-align: center'><a href='").append(context).append("/controller/clienteController?method=6&txtIdCliente=").append(cliente.getIdCliente())
                        .append("&txtIdNotaVenta=").append(movs.getNotaVenta().getIdNotaVenta()).append("' target='_blank'><img src='").append(context).append("/image/report.png' width='25' height='25'")
                        .append("title='Ver Detalle' name='detalleNota' style='image-orientation: left;'/></a></td>")
                        .append("<td style='text-align: center'><input type='button' id='btnAbonos' name='btnAbonos' value='Abonar' onClick='registrarAbono(")
                        .append(movs.getNotaVenta().getIdNotaVenta()).append(");' /></td>");
            } else if (movs.getPedido().getIdPedido() != null) {
                content.append("<td style='text-align: center'><input type='hidden' id='txtIdPedido' name='txtIdPedido' value='")
                        .append(movs.getPedido().getIdPedido()).append("' readOnly='readOnly' />")
                        .append(movs.getPedido().getFolio()).append("</td>")
                        .append("<td style='text-align: center'>Pedido</td>")
                        .append("<td style='text-align: center'>").append(DateUtils.dateToString(movs.getPedido().getFecha())).append("</td>")
                        .append("<td style='text-align: center'>").append(movs.getPedido().getStatusAlmacen()).append("</td>")
                        .append("<td style='text-align: center'><a href='").append(context).append("/controller/clienteController?method=6&txtIdCliente=").append(cliente.getIdCliente())
                        .append("&txtIdPedido=").append(movs.getPedido().getIdPedido()).append("' target='_blank'><img src='").append(context).append("/image/report.png' width='25' height='25'")
                        .append("title='Ver Detalle' name='detallePedido' style='image-orientation: left;'/></a></td>")
                        .append("<td style='text-align: center'><input type='button' id='btnAbonos' name='btnAbonos' value='Abonar' disabled='disabled' /></td>");
            }
            content.append("</tr>");
            if (movs.getAbonos().getAbono() != null) {
                totalAbonado = totalAbonado.add(movs.getAbonos().getAbono());
            }
            if (movs.getCargos().getCargo() != null) {
                totalAdeudo = totalAdeudo.add(movs.getCargos().getCargo());
            }
            if ((movs.getAbonos().getAbono() != null) && (movs.getCargos().getCargo() != null)) {
                totalFaltante = totalAdeudo.subtract(totalAbonado);
            }
        }
        table.append(content).append("</tbody></table>");
        cliente.setTotalAdeudo(totalAdeudo);
        cliente.setTotalAbonado(totalAbonado);
        cliente.setTotalFaltante(totalFaltante);
        cliente.setTable(table.toString());
        return cliente;
    }

    public static DtoVendedor createTableDetalles(DtoVendedor vendedor, String context) {

        StringBuilder table = new StringBuilder();
        StringBuilder content = new StringBuilder();
        BigDecimal totalAdeudoGlobal = BigDecimal.ZERO;
        BigDecimal totalAbonadoGlobal = BigDecimal.ZERO;
        BigDecimal totalFaltanteGlobal = BigDecimal.ZERO;

        table.append("<table id='tblDetallesMovimiento' class='table table-bordered table-hover' style='width: 90%;'>")
                .append("<thead><tr>")
                .append("<th style='text-align: center'>Folio Nota</th>")
                .append("<th style='text-align: center'>Tipo Nota</th>")
                .append("<th style='text-align: center'>Fecha</th>")
                .append("<th style='text-align: center'>Estatus</th>")
                .append("<th style='text-align: center'>Detalle</th>")
                .append("<th style='text-align: center'></th>")
                .append("</tr></thead>")
                .append("<tbody>");

        for (DtoMovimientos movs : vendedor.getMovimientos()) {
            if ((movs.getNotaVenta().getIdNotaVenta() != null) && (movs.getPedido() != null) && (movs.getPedido().getIdPedido() != null)) {
                BigDecimal totalAdeudo = BigDecimal.ZERO;
                BigDecimal totalAbonado = BigDecimal.ZERO;
                BigDecimal totalFaltante = BigDecimal.ZERO;
                for (DtoMovimientos movs1 : vendedor.getMovimientos()) {
                    if (movs.getNotaVenta().getIdNotaVenta().equals(movs1.getNotaVenta().getIdNotaVenta())) {
                        if (movs1.getCargos().getCargo() != null) {
                            totalAdeudo = totalAdeudo.add(movs1.getCargos().getCargo());
                        }
                        if (movs1.getAbonos().getAbono() != null) {
                            totalAbonado = totalAbonado.add(movs1.getAbonos().getAbono());
                            totalFaltante = totalAdeudo.subtract(totalAbonado);
                        }
                    }
                }
                content.append("<tr>")
                        .append("<td style='text-align: center'><input type='hidden' id='txtIdNotaVenta' name='txtIdNotaVenta' value='")
                        .append(movs.getNotaVenta().getIdNotaVenta()).append("' readOnly='readOnly' />")
                        .append(movs.getNotaVenta().getFolio()).append("</td>")
                        .append("<td style='text-align: center'>Venta</td>")
                        .append("<td style='text-align: center'>").append(DateUtils.dateToString(movs.getNotaVenta().getFecha())).append("</td>")
                        .append("<td style='text-align: center'>").append(movs.getNotaVenta().getStatusNotaVenta()).append("</td>")
                        .append("<td style='text-align: center'><a href='").append(context).append("/controller/clienteController?method=6&txtIdCliente=").append(vendedor.getIdVendedor())
                        .append("&txtIdNotaVenta=").append(movs.getNotaVenta().getIdNotaVenta()).append("' target='_blank'><img src='").append(context).append("/image/report.png' width='32' height='32'")
                        .append("title='Ver Detalle' name='detalleNota' style='image-orientation: left;'/></a></td>");
                if ((totalFaltante.compareTo(BigDecimal.ZERO) == 0) && (totalAbonado.compareTo(BigDecimal.ZERO) != 0)) {
                    if (movs.getNotaVenta().getStatusNotaVenta() != 'L') {
                        content.append("<td style='text-align: center'><input type='button' id='btnLiquidar' name='btnLiquidar' value='Liquidar' onClick='liquidar(")
                                .append(movs.getNotaVenta().getIdNotaVenta()).append(");' /></td>");
                    } else {
                        content.append("<td style='text-align: center'><label><strong>Liquidado</strong></label></td>");
                    }
                } else {
                    content.append("<td style='text-align: center'><input type='button' id='btnAbonos' name='btnAbonos' value='Abonar' onClick='registrarAbono(")
                            .append(movs.getNotaVenta().getIdNotaVenta()).append(");' /></td>");
                }
                content.append("</tr>");
            } else if ((movs.getPedido() != null) && (movs.getPedido().getIdPedido() != null)) {
                content.append("<tr>")
                        .append("<td style='text-align: center'><input type='hidden' id='txtIdPedido' name='txtIdPedido' value='")
                        .append(movs.getPedido().getIdPedido()).append("' readOnly='readOnly' />")
                        .append(movs.getPedido().getFolio()).append("</td>")
                        .append("<td style='text-align: center'>Pedido</td>")
                        .append("<td style='text-align: center'>").append(DateUtils.dateToString(movs.getPedido().getFecha())).append("</td>")
                        .append("<td style='text-align: center'>").append(movs.getPedido().getStatusAlmacen()).append("</td>")
                        .append("<td style='text-align: center'><a href='").append(context).append("/controller/clienteController?method=6&txtIdCliente=").append(vendedor.getIdVendedor())
                        .append("&txtIdPedido=").append(movs.getPedido().getIdPedido()).append("' target='_blank'><img src='").append(context).append("/image/report.png' width='32' height='32'")
                        .append("title='Ver Detalle' name='detallePedido' style='image-orientation: left;'/></a></td>")
                        .append("<td style='text-align: center'><input type='button' id='btnAbonos' name='btnAbonos' value='Abonar' disabled='disabled' /></td>")
                        .append("</tr>");
            }
        }
        for (DtoMovimientos movs : vendedor.getMovimientos()) {
            if (movs.getCargos().getCargo() != null) {
                totalAdeudoGlobal = totalAdeudoGlobal.add(movs.getCargos().getCargo());
            }
        }
        for (DtoMovimientos movs : vendedor.getMovimientos()) {
            if (movs.getAbonos().getAbono() != null) {
                totalAbonadoGlobal = totalAbonadoGlobal.add(movs.getAbonos().getAbono());
                totalFaltanteGlobal = totalAdeudoGlobal.subtract(totalAbonadoGlobal);
            }
        }
        table.append(content).append("</tbody></table>");
        vendedor.setTotalAdeudo(totalAdeudoGlobal);
        vendedor.setTotalAbonado(totalAbonadoGlobal);
        vendedor.setTotalFaltante(totalFaltanteGlobal);
        vendedor.setTable(table.toString());
        return vendedor;
    }

    public static String createTableAltaAbonos(DtoNotaVenta notaVenta, String context, String action) {
        StringBuilder sb = new StringBuilder();
        BigDecimal cargo = BigDecimal.ZERO;
        BigDecimal cargoAnterior = BigDecimal.ZERO;
        BigDecimal cargoDevolucion = BigDecimal.ZERO;
        BigDecimal credito = BigDecimal.ZERO;
        String tipoCredito = null;
        String statusCredito = null;
        String fechaRegistro = "";
        String fechaLimite = null;
        Integer idCredito = null;
        String noNota = null;
        Integer index = 0;
        Integer idNotaVentaAnterior = 0;

        for (DtoMovimientos movs : notaVenta.getMovimientos()) {
            cargo = movs.getCargos().getCargo();
            cargoAnterior = (movs.getCargos().getCargoAnterior() == null ? BigDecimal.ZERO : movs.getCargos().getCargoAnterior());
            if (!Objects.equals(idNotaVentaAnterior, movs.getNotaVenta().getIdNotaVenta())) {
                for (DtoDetalleDevoluciones devoluciones : movs.getDevoluciones().getDetalles()) {
                    cargoDevolucion = cargoDevolucion.add(devoluciones.getSubtotal());
                }
            }
            if (!StringUtils.isReallyEmptyOrNull(movs.getCredito().getTipoCredito())) {
                notaVenta.setMovimiento(MovimientosFactory.newInstance());
                tipoCredito = movs.getCredito().getTipoCredito();
                if (tipoCredito.equals(Variables.PLAZO.toUpperCase())) {
                    credito = movs.getCredito().getCantidadMonetaria();
                    if (movs.getCredito().getFechaPago() != null) {
                        fechaLimite = DateUtils.dateToString(movs.getCredito().getFechaPago());
                    }
                } else if (tipoCredito.equals(Variables.MONETARIO.toUpperCase())) {
                    credito = movs.getCredito().getCantidadMonetaria();
                } else if (tipoCredito.equals(Variables.CONTRA_NOTA.toUpperCase())) {
                    noNota = movs.getCredito().getFolioNota();
                }
                if (!StringUtils.isReallyEmptyOrNull(movs.getCredito().getEstatusCredito())) {
                    statusCredito = movs.getCredito().getEstatusCredito();
                    if (movs.getCredito().getEstatusCredito() == "A") {
                        statusCredito = "VIGENTE";
                    } else if (movs.getCredito().getEstatusCredito() == "V") {
                        statusCredito = "VENCIDO";
                    }
                } else {
                    statusCredito = "SIN APLICAR";
                }
                if (movs.getCredito().getFechaPago() != null) {
                    fechaRegistro = DateUtils.dateToString(movs.getCredito().getFechaRegistro());
                } else {
                    fechaRegistro = "SIN APLICAR";
                }
                idCredito = movs.getCredito().getIdCredito();
            }
            index++;
            idNotaVentaAnterior = movs.getNotaVenta().getIdNotaVenta();
        }

        if (!StringUtils.isReallyEmptyOrNull(notaVenta.getMovimiento().getCredito().getTipoCredito())) {
            tipoCredito = notaVenta.getMovimiento().getCredito().getTipoCredito();
            if (tipoCredito.equals(Variables.PLAZO.toUpperCase())) {
                credito = notaVenta.getMovimiento().getCredito().getCantidadMonetaria();
                if (notaVenta.getMovimiento().getCredito().getFechaPago() != null) {
                    fechaLimite = DateUtils.dateToString(notaVenta.getMovimiento().getCredito().getFechaPago());
                }
            } else if (tipoCredito.equals(Variables.MONETARIO.toUpperCase())) {
                credito = notaVenta.getMovimiento().getCredito().getCantidadMonetaria();
            } else if (tipoCredito.equals(Variables.CONTRA_NOTA.toUpperCase())) {
                noNota = notaVenta.getMovimiento().getCredito().getFolioNota();
            }
            if (!StringUtils.isReallyEmptyOrNull(notaVenta.getMovimiento().getCredito().getEstatusCredito())) {
                statusCredito = notaVenta.getMovimiento().getCredito().getEstatusCredito();
            } else {
                statusCredito = "SIN APLICAR";
            }
            if (notaVenta.getMovimiento().getCredito().getFechaPago() != null) {
                fechaRegistro = DateUtils.dateToString(notaVenta.getMovimiento().getCredito().getFechaRegistro());
            } else {
                fechaRegistro = "SIN APLICAR";
            }
            idCredito = notaVenta.getMovimiento().getCredito().getIdCredito();
        }

        sb.append(
                "<form id='frmRegistroAbono' name='frmRegistroAbono'>").append("<img src='").append(context)
                .append("/image/logo.jpg' style='width: 75px; height: 75px; position: absolute; margin-top: 5px;' />")
                .append("<label style='margin: 0px 0px 0px 320px;' class='text-muted'>NOTA MOVIMIENTOS </label>")
                .append("<br /><br /><br /><br /><br /><br />")
                .append("<input type='hidden' id='txtIdAction' name='txtIdAction' value='").append(action).append("' />")
                .append("<input type='hidden' id='txtIdNotaVenta' name='txtIdNotaVenta' value='").append(notaVenta.getIdNotaVenta()).append("' />")
                .append("<input type='hidden' id='txtStatusNota' name='txtStatusNota' readonly='readOnly' readonly='readOnly' />")
                .append("<table class='table table-bordered table-hover' style='width:20px;'><thead><tr><th>FECHA</th><th>FOLIO</th></tr></thead>")
                .append("<tbody><td><input type='text' id='txtFecha' name='txtFecha' readonly='readOnly' value ='")
                .append(DateUtils.dateToString(DateUtils.dateNow())).append("' /></td><td>")
                .append("<input type='text' id='txtFolio' name='txtFolio' readonly='readOnly' value='")
                .append(notaVenta.getFolio()).append("' /></td></tbody></table>")
                .append("<div id='listaCliente' style='width:300px;'><table class='table table-bordered table-hover'><thead><tr><th style='text-align: center; width:25px;' >CLIENTE</th></tr></thead>")
                .append("<tbody><tr><td><strong>NOMBRE :</strong> <input type='text' id='txtNombreCliente' name='txtNombreCliente' value='")
                .append(notaVenta.getNombreCliente()).append("' size='").append((notaVenta.getNombreCliente().length() + 2)).append("' readOnly='readOnly' />")
                .append("</td><tr><td><strong>VENDEDOR :</strong> <input type='text' id='txtNombreVendedor' name='txtNombreVendedor' value='")
                .append(notaVenta.getPedido().getNombreVendedor()).append("' size='").append((notaVenta.getPedido().getNombreVendedor().length() + 2)).append("' readOnly='readOnly' />")
                .append("</td></tr></tbody></table></div><div id='listaCargos' style='width:300px; margin:-138px 0px 0px 310px;'><table class='table table-bordered table-hover'><thead><tr><th colspan='2' style=' text-align: center;' width:25px;' >CARGOS</th></tr></thead>")
                .append("<tr><td><strong>CARGO :</strong></td><td> <label id='lblCargo'>");
        if (cargoAnterior.compareTo(BigDecimal.ZERO) == 0) {
            sb.append(cargo);
        } else {
            sb.append(cargoAnterior);
        }

        sb.append(
                "</label></td></tr>")
                .append("<tr><td><strong>DEVOLUCI&Oacute;N :</strong></td><td> <label id='lblDevolucion'>").append(cargoDevolucion)
                .append("</label></td></tr>")
                .append("<tr><td><strong>CARGO TOTAL :</strong></td><td> <label id='lblTotal'>").append(cargo)
                .append("</label></td></tr></tbody></table></div>");
        if (idCredito
                != null) {
            sb.append("<div id='listaCredito' style='width:300px; margin:-180px 0px 0px 620px;'>")
                    .append("<table class='table table-bordered table-hover'><thead><tr><th style='text-align: center; width:25px;' >CREDITOS</th></tr></thead>")
                    .append("<tbody><tr><td>TIPO CR&Eacute;DITO : ").append(tipoCredito).append(" <br/>ESTATUS CR&Eacute;DITO : ").append(statusCredito).append("</td></tr>")
                    .append("<tr><td> FECHA REGISTRO : ").append(fechaRegistro);
            if (tipoCredito.equals(Variables.PLAZO.toUpperCase())) {
                if (fechaLimite != null) {
                    sb.append(" FECHA LIMITE : ").append(fechaLimite).append("</td></tr>");
                } else {
                    sb.append("</td></tr>");
                }
            } else if (tipoCredito.equals(Variables.CONTRA_NOTA.toUpperCase())) {
                if (noNota != null) {
                    sb.append(" NO NOTA : ").append(noNota).append("</td></tr>");
                }
            }
            if (statusCredito != "VIGENTE") {
                sb.append("<tr><td>LIMITE CR&Eacute;DITO : <label id='lblLimiteCredito'>").append(credito).append("</label>")
                        .append("<br/><center><input type='button' id='btnAplicar' name='btnAplicar' onClick='aplicarCredito(\"")
                        .append(tipoCredito).append("\");' value='Aplicar Crédito' /></center>").append("</td></tr></tbody></table></div>");
            } else {
                sb.append("<tr><td>LIMITE CR&Eacute;DITO : <label id='lblLimiteCredito'>").append(credito).append("</label>")
                        .append("<br/><center><input type='button' id='btnAplicar' name='btnAplicar' onClick='aplicarCredito(\"")
                        .append(tipoCredito).append("\");' value='Aplicar Crédito' disabled='disabled'/></center>").append("</td></tr></tbody></table></div>");
            }
        }

        sb.append(
                "<table id='tblDetalleAbono' class='display'><thead><tr><th style='text-align: center; width:25px;'> NO ABONO</th>")
                .append("<th style='text-align: center; width:25px;'> FECHA ABONO</th>")
                .append("<th style='text-align: center; width:25px;'> TIPO PAGO</th>")
                .append("<th style='text-align: center; width:25px;'> BANCO </th>")
                .append("<th style='text-align: center; width:25px;'> ABONO ANTERIOR</th>")
                .append("<th style='text-align: center; width:25px;'> CARGO RESTANTE</th></thead><tbody>");
        StringBuilder content = new StringBuilder();
        index = 1;
        DecimalFormat formato = new DecimalFormat("$ #,###.00");
        for (DtoMovimientos movs : notaVenta.getMovimientos()) {
            DtoAbonos abono = movs.getAbonos();
            if (abono.getIdAbonos() != null) {
                cargo = cargo.subtract(movs.getAbonos().getAbono());
                String abonoFormateado = formato.format(abono.getAbonoAnterior());
                String cargoFormateado = formato.format(cargo);
                content.append("<tr>").append("<td>").append(index).append("</td>")
                        .append("<td>").append(DateUtils.dateToString(abono.getFechaCaptura())).append("</td>")
                        .append("<td>").append(abono.getTipoPago()).append("</td>")
                        .append("<td>").append(abono.getBancos().getNombre() != null ? abono.getBancos().getNombre() : "NO APLICA").append("</td>")
                        .append("<td>").append(abonoFormateado).append("</td>")
                        .append("<td>").append(cargoFormateado).append("</td>")
                        .append("</tr>");
                index++;
            }
        }

        content.append(
                "</tbody></table>");
        sb.append(content)
                .append("</form>");
        return sb.toString();
    }
}
