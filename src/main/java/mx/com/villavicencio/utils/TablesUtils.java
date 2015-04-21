package mx.com.villavicencio.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import java.util.Collection;
import java.util.Objects;
import mx.com.villavicencio.generics.types.GenericTypes;
import mx.com.villavicencio.generics.variables.Variables;
import mx.com.villavicencio.system.cliente.dto.DtoCliente;
import mx.com.villavicencio.system.credito.credito.bo.CreditoBo;
import mx.com.villavicencio.system.credito.credito.dto.DtoCredito;
import mx.com.villavicencio.system.credito.credito.factory.CreditoFactory;
import mx.com.villavicencio.system.movimientos.abonos.bo.AbonosBo;
import mx.com.villavicencio.system.movimientos.abonos.dto.DtoAbonos;
import mx.com.villavicencio.system.movimientos.movimientos.bo.MovimientosBo;
import mx.com.villavicencio.system.movimientos.movimientos.dto.DtoMovimientos;
import mx.com.villavicencio.system.movimientos.movimientos.factory.MovimientosFactory;
import mx.com.villavicencio.system.productos.productos.dto.DtoProducto;
import mx.com.villavicencio.system.usuario.dto.DtoUsuario;
import mx.com.villavicencio.system.vendedor.dto.DtoVendedor;
import mx.com.villavicencio.system.venta.detalle.bo.DetalleNotaVentaBo;
import mx.com.villavicencio.system.venta.detalle.dto.DtoDetalleNotaVenta;
import mx.com.villavicencio.system.venta.detalle.factory.DetalleNotaVentaFactory;
import mx.com.villavicencio.system.venta.devoluciones.detalle.dto.DtoDetalleDevoluciones;
import mx.com.villavicencio.system.venta.nota.bo.NotaVentaBo;
import mx.com.villavicencio.system.venta.nota.dto.DtoNotaVenta;

/**
 *
 * @author Gabriel J
 */
public class TablesUtils {

    private static CreditoBo creditoBo;
    private static DtoUsuario user;
    private static NotaVentaBo notaVentaBo;
    private static DetalleNotaVentaBo detalleNotaVentaBo;
    private static MovimientosBo movimientosBo;
    private static AbonosBo abonosBo;

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

        for (DtoMovimientos movs : cliente.getMovimientos()) {
            if ((movs.getNotaVenta().getIdNotaVenta() != null) && (movs.getPedido() != null) && (movs.getPedido().getIdPedido() != null)) {
                BigDecimal totalAdeudo = BigDecimal.ZERO;
                BigDecimal totalAbonado = BigDecimal.ZERO;
                BigDecimal totalFaltante = BigDecimal.ZERO;
                for (DtoMovimientos movs1 : cliente.getMovimientos()) {
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
                        .append("<td style='text-align: center'>").append(movs.getNotaVenta().getStatusNotaVenta()).append("</td>");
                if (movs.getNotaVenta().getIdNotaVenta() != null) {
                    content.append("<td style='text-align: center'><a href='").append(context).append("/controller/notaVentaController?method=2&txtIdNotaVenta=").append(movs.getNotaVenta().getIdNotaVenta())
                            .append("&reportName=notaVenta' target='_blank'><img src='").append(context).append("/image/report.png' width='32' height='32'")
                            .append("title='Ver Detalle Nota Venta' name='detalleNota' style='image-orientation: left;'/></a></td>");
                } else {
                    content.append("<td style='text-align: center'><a href='").append(context).append("/controller/pedidosController?method=10")
                            .append("&txtIdPedido=").append(movs.getPedido().getIdPedido()).append("&reportName=pedidos' target='_blank'><img src='").append(context).append("/image/report.png' width='32' height='32'")
                            .append("title='Ver Detalle Pedido' name='detalleNota' style='image-orientation: left;'/></a></td>");
                }
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
                        .append("<td style='text-align: center'>").append(movs.getPedido().getStatusAlmacen()).append("</td>");
                if (movs.getNotaVenta().getIdNotaVenta() != null) {
                    content.append("<td style='text-align: center'><a href='").append(context).append("/controller/notaVentaController?method=2&txtIdNotaVenta=").append(movs.getNotaVenta().getIdNotaVenta())
                            .append("&reportName=notaVenta' target='_blank'><img src='").append(context).append("/image/report.png' width='32' height='32'")
                            .append("title='Ver Detalle Nota Venta' name='detalleNota' style='image-orientation: left;'/></a></td>");
                } else {
                    content.append("<td style='text-align: center'><a href='").append(context).append("/controller/pedidosController?method=10")
                            .append("&txtIdPedido=").append(movs.getPedido().getIdPedido()).append("&reportName=pedidos' target='_blank'><img src='").append(context).append("/image/report.png' width='32' height='32'")
                            .append("title='Ver Detalle Pedido' name='detalleNota' style='image-orientation: left;'/></a></td>");
                }
                content.append("<td style='text-align: center'><input type='button' id='btnAbonos' name='btnAbonos' value='Abonar' disabled='disabled' /></td>")
                        .append("</tr>");
            }
        }
        for (DtoMovimientos movs : cliente.getMovimientos()) {
            if (movs.getCargos().getCargo() != null) {
                totalAdeudoGlobal = totalAdeudoGlobal.add(movs.getCargos().getCargo());
            }
        }
        for (DtoMovimientos movs : cliente.getMovimientos()) {
            if (movs.getAbonos().getAbono() != null) {
                totalAbonadoGlobal = totalAbonadoGlobal.add(movs.getAbonos().getAbono());
                totalFaltanteGlobal = totalAdeudoGlobal.subtract(totalAbonadoGlobal);
            }
        }
        table.append(content).append("</tbody></table>");
        cliente.setTotalAdeudo(totalAdeudoGlobal);
        cliente.setTotalAbonado(totalAbonadoGlobal);
        cliente.setTotalFaltante(totalFaltanteGlobal);
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
                        .append("<td style='text-align: center'>").append(movs.getNotaVenta().getStatusNotaVenta()).append("</td>");
                if (movs.getNotaVenta().getIdNotaVenta() != null) {
                    content.append("<td style='text-align: center'><a href='").append(context).append("/controller/notaVentaController?method=2&txtIdNotaVenta=").append(movs.getNotaVenta().getIdNotaVenta())
                            .append("&reportName=notaVenta' target='_blank'><img src='").append(context).append("/image/report.png' width='32' height='32'")
                            .append("title='Ver Detalle Nota Venta' name='detalleNota' style='image-orientation: left;'/></a></td>");
                } else {
                    content.append("<td style='text-align: center'><a href='").append(context).append("/controller/pedidosController?method=10")
                            .append("&txtIdPedido=").append(movs.getPedido().getIdPedido()).append("&reportName=pedidos' target='_blank'><img src='").append(context).append("/image/report.png' width='32' height='32'")
                            .append("title='Ver Detalle Pedido' name='detalleNota' style='image-orientation: left;'/></a></td>");
                }

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
                        .append("<td style='text-align: center'>").append(movs.getPedido().getStatusAlmacen()).append("</td>");
                if (movs.getNotaVenta().getIdNotaVenta() != null) {
                    content.append("<td style='text-align: center'><a href='").append(context).append("/controller/notaVentaController?method=2&txtIdNotaVenta=").append(movs.getNotaVenta().getIdNotaVenta())
                            .append("&reportName=notaVenta' target='_blank'><img src='").append(context).append("/image/report.png' width='32' height='32'")
                            .append("title='Ver Detalle Nota Venta' name='detalleNota' style='image-orientation: left;'/></a></td>");
                } else {
                    content.append("<td style='text-align: center'><a href='").append(context).append("/controller/pedidosController?method=10")
                            .append("&txtIdPedido=").append(movs.getPedido().getIdPedido()).append("&reportName=pedidos' target='_blank'><img src='").append(context).append("/image/report.png' width='32' height='32'")
                            .append("title='Ver Detalle Pedido' name='detalleNota' style='image-orientation: left;'/></a></td>");
                }

                content.append("<td style='text-align: center'><input type='button' id='btnAbonos' name='btnAbonos' value='Abonar' disabled='disabled' /></td>")
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
                .append("</td></tr><tr><td><strong>VENDEDOR :</strong> <input type='text' id='txtNombreVendedor' name='txtNombreVendedor' value='")
                .append(notaVenta.getPedido().getNombreVendedor()).append("' size='").append((notaVenta.getPedido().getNombreVendedor().length() + 2)).append("' readOnly='readOnly' />")
                .append("</td></tr></tbody></table></div><div id='listaCargos' style='width:300px; margin:-160px 0px 0px 310px;'><table class='table table-bordered table-hover'><thead><tr><th colspan='2' style=' text-align: center; width:25px;' >CARGOS</th></tr></thead>")
                .append("<tbody><tr><td><strong>CARGO :</strong></td><td> <label id='lblCargo'>");

        if (getCargoAnterior(notaVenta).compareTo(BigDecimal.ZERO) == 0) {
            sb.append(getCargo(notaVenta));
        } else {
            sb.append(getCargoAnterior(notaVenta));
        }
        sb.append(
                "</label></td></tr>")
                .append("<tr><td><strong>DEVOLUCI&Oacute;N :</strong></td><td> <label id='lblDevolucion'>").append(getCargoDevolucion(notaVenta))
                .append("</label></td></tr>")
                .append("<tr><td><strong>CARGO TOTAL :</strong></td><td> <label id='lblTotal'>").append(getCargo(notaVenta))
                .append("</label></td></tr></tbody></table></div>");
        sb.append("<div id='listaCredito' style='width:485px; margin:-180px 0px 0px 620px;'>")
                .append("<table class='table table-bordered table-hover'><thead><tr><th colspan='2' style=' text-align: center;' >CREDITOS</th></tr></thead>")
                .append("<tbody><tr><td><strong>TIPO CR&Eacute;DITO :</strong> ");
        DtoCredito credito = getCredito(notaVenta);
        if (credito != null) {
            if (credito.getIdCredito() != null) {
                sb.append(credito.getTipoCredito());
                if (!StringUtils.isReallyEmptyOrNull(credito.getEstatusCredito())) {
                    sb.append(" </td><td><strong>ESTATUS CR&Eacute;DITO : </strong>").append(
                            ("A".equals(credito.getEstatusCredito()) ? "VIGENTE" : ("V".equals(credito.getEstatusCredito()) ? "VENCIDO" : "SIN APLICAR"))).append("</td></tr>")
                            .append("<tr><td><strong> FECHA REGISTRO : </strong>").append(DateUtils.dateToString(credito.getFechaRegistro())).append("</td>");
                    if (credito.getTipoCredito().equals(Variables.PLAZO.toUpperCase())) {
                        sb.append(" <td><br><strong>FECHA LIMITE : </strong>").append(credito.getFechaPago()).append("</td></tr>");
                        if (credito.getFolioNota() != null) {
                            sb.append("<tr><td colspan='2'><strong>NO NOTAS  :</strong> ").append(credito.getFolioNota()).append("</td></tr>");
                        }
                    } else if (credito.getTipoCredito().equals(Variables.CONTRA_NOTA.toUpperCase())) {
                        if (credito.getFolioNota() != null) {
                            sb.append("<tr><td><strong>NO NOTA : </strong>").append(credito.getFolioNota()).append("</td></tr>");
                        }
                    } else if (credito.getTipoCredito().equals(Variables.MONETARIO.toUpperCase())) {
                        if (credito.getFolioNota() != null) {
                            sb.append("<tr><td><strong> NO NOTAS : </strong>").append(credito.getFolioNota()).append("</td></tr>");
                        }
                    }
                }
                if ((credito.getEstatusCredito() == null) || (!"A".equals(credito.getEstatusCredito()))) {
                    sb.append("<tr><td><strong>LIMITE CR&Eacute;DITO :</strong> <label id='lblLimiteCredito'>").append(credito.getCantidadMonetaria()).append("</label>").append("</td>")
                            .append("<td><strong>CR&Eacute;DITO DISPONIBLE : </strong><label id='lblCreditoUsado'>").append(getCreditoActual(notaVenta, credito)).append("</label>").append("</td></tr>");
                    if (DateUtils.dateToString(notaVenta.getFecha()).equals(DateUtils.dateToString(DateUtils.dateNow()))) {
                        sb.append("<tr><td colspan='2'><center><input type='button' id='btnAplicar' name='btnAplicar' onClick='aplicarCredito(\"")
                                .append(credito.getTipoCredito()).append("\",\"").append(notaVenta.getFolio()).append("\",").append(credito.getIdCredito())
                                .append(",").append(notaVenta.getIdNotaVenta())
                                .append(");' value='Aplicar Cr&eacute;dito' /></center>").append("</td></tr></tbody></table></div>");
                    } else {
                        sb.append("</tbody></table></div>");
                    }
                } else {
                    sb.append("<tr><td><strong>LIMITE CR&Eacute;DITO : </strong><label id='lblLimiteCredito'>").append(credito.getCantidadMonetaria()).append("</label>").append("</td>")
                            .append("<td><strong><label id='lblNegativo'>CR&Eacute;DITO DISPONIBLE : </label></strong><label id='lblCreditoUsado'>").append(getCreditoActual(notaVenta, credito)).append("</label>").append("</td></tr>")
                            .append("<tr><td colspan='2' style=' text-align: center; width:25px;'><center><input type='button' id='btnAplicar' name='btnAplicar' value='Cr&eacute;dito Aplicado' disabled='disabled'/></center>").append("</td></tr></tbody></table></div>");
                }
            } else {
                sb.append("<tr><td colspan='2' style=' text-align: center; width:25px;'>Sin C&eacute;dito</center>").append("</td></tr></tbody></table></div><br/><br/><br/>");
            }
        }

        sb.append(
                "<table id='tblDetalleAbono' class='display'><thead><tr><th style='text-align: center; width:25px;'> NO ABONO</th>")
                .append("<th style='text-align: center; width:25px;'> FECHA ABONO</th>")
                .append("<th style='text-align: center; width:25px;'> TIPO PAGO</th>")
                .append("<th style='text-align: center; width:25px;'> BANCO </th>")
                .append("<th style='text-align: center; width:25px;'> ABONO ANTERIOR</th>")
                .append("<th style='text-align: center; width:25px;'> CARGO RESTANTE</th></tr></thead><tbody>");
        StringBuilder content = new StringBuilder();
        Integer index = 1;
        DecimalFormat formato = new DecimalFormat("$ #,###.00");
        for (DtoMovimientos movs : notaVenta.getMovimientos()) {
            DtoAbonos abono = movs.getAbonos();
            if (abono.getIdAbonos() != null) {
                BigDecimal cargo = getCargo(notaVenta);
                cargo = cargo.subtract(movs.getAbonos().getAbono());
                String abonoFormateado = formato.format(abono.getAbonoAnterior());
                String cargoFormateado = formato.format(cargo);
                content.append("<tr>").append("<td style='text-align: center;'>").append(index).append("</td>")
                        .append("<td style='text-align: center;'>").append(DateUtils.dateToString(abono.getFechaCaptura())).append("</td>")
                        .append("<td style='text-align: center;'>").append(abono.getTipoPago()).append("</td>")
                        .append("<td style='text-align: center;'>").append(abono.getBancos().getNombre() != null ? abono.getBancos().getNombre() : "NO APLICA").append("</td>")
                        .append("<td style='text-align: center;'>").append(abonoFormateado).append("</td>")
                        .append("<td style='text-align: center;'>").append(cargoFormateado).append("</td>")
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

    private static BigDecimal getCargo(DtoNotaVenta object) {
        BigDecimal cargo = BigDecimal.ZERO;
        for (DtoMovimientos movs : object.getMovimientos()) {
            cargo = movs.getCargos().getCargo();
        }
        return cargo;
    }

    private static BigDecimal getCargoAnterior(DtoNotaVenta object) {
        BigDecimal cargoAnterior = BigDecimal.ZERO;
        for (DtoMovimientos movs : object.getMovimientos()) {
            cargoAnterior = (movs.getCargos().getCargoAnterior() == null ? BigDecimal.ZERO : movs.getCargos().getCargoAnterior());
        }
        return cargoAnterior;
    }

    private static BigDecimal getCargoDevolucion(DtoNotaVenta object) {
        Integer index = 0;
        Integer idNotaVentaAnterior = 0;
        BigDecimal cargoDevolucion = BigDecimal.ZERO;
        for (DtoMovimientos movs : object.getMovimientos()) {
            if (!Objects.equals(idNotaVentaAnterior, movs.getNotaVenta().getIdNotaVenta())) {
                for (DtoDetalleDevoluciones devoluciones : movs.getDevoluciones().getDetalles()) {
                    cargoDevolucion = cargoDevolucion.add(devoluciones.getSubtotal());
                }
            }
            index++;
            idNotaVentaAnterior = movs.getNotaVenta().getIdNotaVenta();
        }
        return cargoDevolucion;
    }

    private static DtoCredito getCredito(DtoNotaVenta object) {
        Integer index = 0;
        Integer idNotaVentaAnterior = 0;
        DtoCredito credito = CreditoFactory.newInstance();
        for (DtoMovimientos movs : object.getMovimientos()) {
            if (!Objects.equals(idNotaVentaAnterior, movs.getNotaVenta().getIdNotaVenta())) {
                if (!StringUtils.isReallyEmptyOrNull(movs.getCredito().getTipoCredito())) {
                    if (movs.getCredito().getTipoCredito().equals(Variables.PLAZO.toUpperCase())) {
                        credito.setCantidadMonetaria(movs.getCredito().getCantidadMonetaria());
                        if (movs.getCredito().getFechaPago() != null) {
                            credito.setFechaPago(movs.getCredito().getFechaPago());
                            credito.setFolioNota(movs.getCredito().getFolioNota());
                        }
                    } else if (movs.getCredito().getTipoCredito().equals(Variables.MONETARIO.toUpperCase())) {
                        credito.setCantidadMonetaria(movs.getCredito().getCantidadMonetaria());
                        credito.setFolioNota(movs.getCredito().getFolioNota());
                    } else if (movs.getCredito().getTipoCredito().equals(Variables.CONTRA_NOTA.toUpperCase())) {
                        credito.setFolioNota(movs.getCredito().getFolioNota());
                    }
                    if (!StringUtils.isReallyEmptyOrNull(movs.getCredito().getEstatusCredito())) {
                        credito.setEstatusCredito(movs.getCredito().getEstatusCredito());
                    }
                    credito.setFechaRegistro(movs.getCredito().getFechaRegistro());
                    credito.setIdCredito(movs.getCredito().getIdCredito());
                    credito.setTipoCredito(movs.getCredito().getTipoCredito());
                }
            }
            index++;
            idNotaVentaAnterior = movs.getNotaVenta().getIdNotaVenta();
        }
        return credito;
    }

    private static BigDecimal getCreditoActual(DtoNotaVenta object, DtoCredito credito) {
        BigDecimal abonoCredito = BigDecimal.ZERO;
        BigDecimal creditoActual;
        BigDecimal cargo = BigDecimal.ZERO;
        BigDecimal cargoCredito;
        DtoCredito cred = creditoBo.findById(user, credito);
        Integer idAbonoAnterior = 0;
        if (cred.getFolioNota() != null) {
            if (cred.getFolioNota().contains(",")) {
                cargoCredito = BigDecimal.ZERO;
                for (DtoNotaVenta notas : notaVentaBo.findAll(user)) {
                    notas.setDetalles(detalleNotaVentaBo.findAll(user, DetalleNotaVentaFactory.newInstance(notas.getIdNotaVenta())));
                    for (String str : StringUtils.split(cred.getFolioNota())) {
                        if (notas.getFolio().equals(str)) {
                            for (DtoDetalleNotaVenta detalles : notas.getDetalles()) {
                                cargoCredito = cargoCredito.add(detalles.getSubTotal());
                            }
                            for (DtoMovimientos movs : object.getMovimientos()) {
                                if (notas.getFolio().equals(str)) {
                                    if (movs.getDevoluciones().getIdDevoluciones() != null) {
                                        cargoCredito = movs.getCargos().getCargo();
                                    }
                                    cargo = cargo.add(movs.getCargos().getCargo());
                                    break;
                                }
                            }
                        }
                    }
                }
                for (String str : StringUtils.split(cred.getFolioNota())) {
                    for (DtoNotaVenta notas : notaVentaBo.findAll(user)) {
                        if (notas.getFolio().equals(str)) {
                            DtoMovimientos movimientos = MovimientosFactory.newInstance();
                            movimientos.setNotaVenta(notas);
                            for (DtoMovimientos movs : movimientosBo.findAll(user, movimientos)) {
                                movs.setAbonos(abonosBo.findById(user, movs.getAbonos()));
                                if (movs.getAbonos().getIdAbonos() != null) {
                                    if (!Objects.equals(idAbonoAnterior, movs.getAbonos().getIdAbonos())) {
                                        abonoCredito = abonoCredito.add(movs.getAbonos().getAbono());
                                        idAbonoAnterior = movs.getAbonos().getIdAbonos();
                                    }
                                }
                            }
                        }
                    }
                }
                cargoCredito = credito.getCantidadMonetaria().subtract(cargoCredito);
                creditoActual = new BigDecimal(new DecimalFormat("######.00").format(cargoCredito.add(abonoCredito)));
            } else {
                cargoCredito = BigDecimal.ZERO;
                for (DtoNotaVenta notas : notaVentaBo.findAll(user)) {
                    notas.setDetalles(detalleNotaVentaBo.findAll(user, DetalleNotaVentaFactory.newInstance(notas.getIdNotaVenta())));
                    if (notas.getFolio().equals(object.getFolio())) {
                        for (DtoDetalleNotaVenta detalles : notas.getDetalles()) {
                            cargoCredito = cargoCredito.add(detalles.getSubTotal());
                        }

                        for (DtoMovimientos movs : object.getMovimientos()) {
                            if (notas.getFolio().equals(object.getFolio())) {
                                if (movs.getDevoluciones().getIdDevoluciones() != null) {
                                    cargoCredito = movs.getCargos().getCargo();
                                }
                                cargo = cargo.add(movs.getCargos().getCargo());
                                break;
                            }
                        }
                        for (DtoMovimientos movs : object.getMovimientos()) {
                            if (Objects.equals(object.getIdNotaVenta(), movs.getNotaVenta().getIdNotaVenta())) {
                                if (movs.getAbonos().getIdAbonos() != null) {
                                    abonoCredito = abonoCredito.add(movs.getAbonos().getAbono());
                                }
                            }
                        }
                    }
                }
                cargoCredito = credito.getCantidadMonetaria().subtract(cargoCredito);
                creditoActual = new BigDecimal(new DecimalFormat("######.00").format(cargoCredito.add(abonoCredito)));
            }
            if (creditoActual.compareTo(cred.getCantidadMonetaria()) == 0) {
                updateCredito(cred);
            }
        } else {
            creditoActual = cred.getCantidadMonetaria();
        }
        return creditoActual;
    }

    private static void updateCredito(DtoCredito object) {
        DtoCredito credito = CreditoFactory.newInstance(object.getIdCredito());
        credito.setTipoCredito(object.getTipoCredito());
        credito.setOpcion(GenericTypes.THREE);
        creditoBo.modificar(user, credito);
    }

    public static void setCreditoBo(CreditoBo aCreditoBo) {
        creditoBo = aCreditoBo;
    }

    public static void setUser(DtoUsuario aUser) {
        user = aUser;
    }

    public static void setNotaVentaBo(NotaVentaBo aNotaVentaBo) {
        notaVentaBo = aNotaVentaBo;
    }

    public static void setDetalleNotaVentaBo(DetalleNotaVentaBo aDetalleNotaVentaBo) {
        detalleNotaVentaBo = aDetalleNotaVentaBo;
    }

    public static void setMovimientosBo(MovimientosBo aMovimientosBo) {
        movimientosBo = aMovimientosBo;
    }

    public static void setAbonosBo(AbonosBo aAbonosBo) {
        abonosBo = aAbonosBo;
    }

}
