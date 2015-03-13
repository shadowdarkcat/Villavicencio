<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<div id="divAltaLiquidacion" title="Registro Liquidación">   
    <img src="${pageContext.request.contextPath}/image/logo.jpg" style="width: 75px; height: 75px; position: absolute; margin-top: 5px;" />
    <label style="margin: 0px 0px 0px 510px;" class="text-muted">L I Q U I D A C I &Oacute; N </label>
    <label style=" text-align: center; margin: 0px 0px 0px 380px;" class="text-muted">FOLIO</label>
    <input type="text" id="txtFolio" name="txtFolio" readonly="readOnly" />
    <input type="hidden" id="txtIdAction" name="txtIdAction" value="${id}" />
    <input type="hidden" id="txtIdCliente" name="txtIdCliente" />
    <input type="hidden" id="txtIdVendedor" name="txtIdVendedor" />
    <input type="hidden" id="txtIdNotaVenta" name="txtIdNotaVenta" /> 
    <input type="hidden" id="statusNotaVenta" name="statusNotaVenta" value="L" readonly="readOnly" /> 
    <br /><br /><br /><br />

    <table style="margin: -25px 0px 0px 729px">
        <tbody>
            <tr>

                <td> <label style=" text-align: center;" class="text-muted">NOMBRE VENDEDOR</label></td>
                <td><input type="text" id="txtVendedor" name="txtVendedor" readonly="readOnly" /></td>
                <td><label style=" text-align: center;" class="text-muted">FECHA</label></td>
                <td><input type="text" id="txtFecha" name="txtFecha" readonly="readOnly"/></td>
            </tr>
        </tbody>
    </table>

    <table id="tblLiquidacion" class="table table-bordered table-hover" >
        <thead>               
            <tr>
                <th rowspan="2" style=" text-align: center;" class="text-muted">PRODUCTOS</th>
                <th colspan="2" style=" text-align: center;" class="text-muted">SALIDA</th>
                <th colspan="2" style=" text-align: center;" class="text-muted">DEVOLUCION</th>
                <th rowspan="2" style=" text-align: center;" class="text-muted">TOTAL KGS.</th>
                <th rowspan="2" style=" text-align: center;" class="text-muted">COSTO U.</th>
                <th rowspan="2" style=" text-align: center;" class="text-muted">SUBTOTAL</th>
                <th rowspan="2" style=" text-align: center;" class="text-muted">OBSERVACIONES</th>
            </tr>
            <tr>
                <th style=" text-align: center;" class="text-muted">PZAS</th>
                <th style=" text-align: center;" class="text-muted">KGS</th>
                <th style=" text-align: center;" class="text-muted">PZAS</th>
                <th style=" text-align: center;" class="text-muted">KGS</th>	
            </tr>
        </thead>
        <tbody></tbody>
    </table>
    <br/>   
    <table border="1" class="table table-bordered table-hover">
        <tr>
            <td><label class="text-muted">CANTIDAD CON LETRA :</label><br/>
                <label id="lblCantidadLetra" class="text-muted"></label>
            </td>
            <td>
                <table>
                    <tr>
                        <td class="text-muted"> SUBTOTAL </td><td class="text-muted"> 0.00</td>
                    </tr>
                    <tr>
                        <td class="text-muted">IVA</td><td class="text-muted" > 0.00</td>
                    </tr>
                    <tr>
                        <td class="text-muted">TOTAL </td>
                        <td>
                            <input style="text-align: center;" type="text" id="txtTotal" name="txtTotal" readonly="readOnly" />
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>
    <br/>
    <strong> <label id="lblObservacionesHead" class="text-muted" style="display: none;">OBSERVACIONES :</label></strong>    
    <label id="lblObservaciones" class="text-muted"></label>    
    <br/>
    <table>
        <tr>
            <td><label class="text-muted" style="font-size:12px;">DEBO Y PAGARE A LA ORDEN DE <strong>CARLOS ESPINOZA DE LOS MONTEROS
                        VIZCARRA</strong>.<br/>LA CANTIDAD DE:<label id="lblCantidad"> </label>. EN CASO DE PRESENTAR UNA MORA EN EL PAGO<br/>
                    DE LA PRESENTE, CAUSAR&Aacute; UN INTER&Eacute;S MENSUAL DEL 6% SOBRE EL IMPORTE<br/>
                    ASENTADO, DE ACUERDO A LOS T&Eacute;RMINOS DE LOS ART&Iacute;CULOS 170 Y 174 DE LA <br/>
                    LEY GENERAL DE LOS T&Iacute;TULOS Y OPERACIONES DE CR&Eacute;DITO.</label></td>
        </tr>
    </table>
</div>