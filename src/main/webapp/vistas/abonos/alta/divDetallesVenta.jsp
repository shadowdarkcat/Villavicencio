<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<div id="divDetallesVenta" title="DETALLES VENTA">
    <div id="IzquierdaMovimientos" style="width: 50%;">
        <input type="hidden" id="txtIdAction" name="txtIdAction" value="${id}" />
        <fieldset> 
            <legend class="text-muted" style="text-align: center;">DATOS CLIENTE</legend>
            <table align="center" class="table table-bordered table-hover">             
                <tbody>
                    <tr>
                        <td>
                            <strong><label class="text-muted">NOMBRE :</label></strong> 
                            <label id="lblNombre" class="text-muted"></label>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <strong><label class="text-muted">DIRECCI&Oacute;N :</label></strong>
                            <label id="lblDireccion" class="text-muted"></label>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <strong><label class="text-muted">RFC :</label></strong>
                            <label id="lblRfc" class="text-muted"></label> 
                        </td>
                    </tr>
                </tbody>
            </table>     
        </fieldset>
    </div>

    <div id="DerechaMovimientos" style="width: 30%;"> 
        <fieldset> 
            <legend class="text-muted" style="text-align: center;">ESTADO DE CUENTA</legend>
            <table align="center" class="table table-bordered table-hover">             
                <tbody>
                    <tr>
                        <td>
                            <strong><label class="text-muted">TOTAL ADEUDO : </label></strong> 
                            <label id="lblTotalAdeudo" class="text-muted"></label>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <strong><label class="text-muted">TOTAL ABONADO : </label></strong>
                            <label id="lblTotalAbonado" class="text-muted"></label>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <strong><label class="text-muted">TOTAL FALTANTE : </label></strong>
                            <label id="lblTotalFaltante" class="text-muted"></label> 
                        </td>
                    </tr>
                </tbody>
            </table>     
        </fieldset>
    </div>
    <div id="divDownMovimientos" style="text-align: center;">
        <fieldset>
            <legend class="text-muted">DETALLE MOVIMIENTOS</legend>
            <div id="divDetallesPrevioMovimientos"></div>
        </fieldset>
    </div>
</div>