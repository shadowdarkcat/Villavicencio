<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div id="divCreditoPlazo" title="Crédito a Plazo">
    <img src="${pageContext.request.contextPath}/image/logo.jpg" style="width: 75px; height: 75px; position: absolute; margin-top: 5px;" />
    <label class="text-muted" style="margin: 0px 0px 0px 210px;">C&Eacute;DITO A PLAZO </label>
    <br /><br /><br /><br /><br /><br />
    <form id="frmCreditoPlazo" name="frmCreditoPlazo" >
        <div id="divFechaRegistro" style="margin: -55px 0px 0px 220px;">
            <label class="text-muted">FECHA REGISTRO:</label>
            <input type="text" id="txtFechaRegistro" name="txtFechaRegistro" readonly="readOnly" />
            <label id="lblErrorFechaRegistro" class="text-muted" style="display: none;">Campo requerido</label>
        </div>
        <br /><br />
        <input type="hidden" id="txtIdNotaVenta" name="txtIdNotaVenta" readonly="readOnly" />
        <input type="hidden" id="txtIdCredito" name="txtIdCredito" readonly="readOnly" />
        <input type="hidden" id="txtTipoCredito" name="txtTipoCredito" readonly="readOnly" />

        <label class="text-muted">FOLIO NOTA VENTA :</label> 
        <input type="text" id="txtFolio" name="txtFolio" readonly="readOnly"/>
        <br /><br/>
        <table>
            <tr>

                <td><label class="text-muted">FECHA LIMITE DE PAGO:</label></td>
                <td><input type="text" id="txtFechaPago" name="txtFechaPago" readonly="readOnly" />
                    <label id="lblErrorFechaPago" class="text-muted" style="display: none;">Campo requerido</label></td>
            </tr>
        </table>
        <label id="lblErrorFechasIguales" class="text-muted" style="display: none;">La fecha de registro no puede ser igual a la fecha de pago</label>
        <br/>
        <label class="text-muted">SALDO DISPONIBLE TRAS APLICAR EL CR&Eacute;DITO</label> <label id="lblDisponible" class="text-muted"></label>
        <input type="hidden" id="txtCreditoDisponible" name="txtCreditoDisponible" readonly="readOnly" />
        <label id="lblErrorExcedeCredito" class="text-muted" style="display: none;">El cargo excede el c&eacute;dito disponible<br/>si continua generara un saldo deudor</label>
    </form>
</div>

<div id="divCreditoMonetario" title="Crédito Monetario">
    <img src="${pageContext.request.contextPath}/image/logo.jpg" style="width: 75px; height: 75px; position: absolute; margin-top: 5px;" />
    <label class="text-muted" style="margin: 0px 0px 0px 210px;">C&Eacute;DITO MONETARIO </label>
    <br /><br /><br /><br /><br /><br />
    <form id="frmCreditoMonetario" name="frmCreditoMonetario" >
        <div id="divFechaRegistro" style="margin: -55px 0px 0px 220px;">
            <label class="text-muted">FECHA REGISTRO:</label>
            <input type="text" id="txtFechaRegistro" name="txtFechaRegistro" readonly="readOnly" />
            <label id="lblErrorFechaRegistro" class="text-muted" style="display: none;">Campo requerido</label>
        </div>
        <br /><br />
        <input type="hidden" id="txtIdNotaVenta" name="txtIdNotaVenta" readonly="readOnly" />
        <input type="hidden" id="txtIdCredito" name="txtIdCredito" readonly="readOnly" />
        <input type="hidden" id="txtTipoCredito" name="txtTipoCredito" readonly="readOnly" />

        <label class="text-muted">FOLIO NOTA VENTA :</label> 
        <input type="text" id="txtFolio" name="txtFolio" readonly="readOnly"/>
        <br /><br/>
        <label class="text-muted">SALDO DISPONIBLE TRAS APLICAR EL CR&Eacute;DITO</label> <label id="lblDisponible" class="text-muted"></label>
        <input type="hidden" id="txtCreditoDisponible" name="txtCreditoDisponible" readonly="readOnly" />
        <label id="lblErrorExcedeCredito" class="text-muted" style="display: none;">El cargo excede el c&eacute;dito disponible<br/>si continua generara un saldo deudor</label>
    </form>
</div>

<div id="divCreditoContraNota" title="Crédito Contranota">
    <img src="${pageContext.request.contextPath}/image/logo.jpg" style="width: 75px; height: 75px; position: absolute; margin-top: 5px;" />
    <label class="text-muted" style="margin: 0px 0px 0px 210px;">C&Eacute;DITO CONTRANOTA </label>
    <br /><br /><br /><br /><br /><br />
    <form id="frmCreditoContraNota" name="frmCreditoContraNota" >
        <div id="divFechaRegistro" style="margin: -55px 0px 0px 220px;">
            <label class="text-muted">FECHA REGISTRO:</label>
            <input type="text" id="txtFechaRegistro" name="txtFechaRegistro" readonly="readOnly" />
            <label id="lblErrorFechaRegistro" class="text-muted" style="display: none;">Campo requerido</label>
        </div>
        <br /><br />
        <input type="hidden" id="txtIdNotaVenta" name="txtIdNotaVenta" readonly="readOnly" />
        <input type="hidden" id="txtIdCredito" name="txtIdCredito" readonly="readOnly" />
        <input type="hidden" id="txtTipoCredito" name="txtTipoCredito" readonly="readOnly" />

       <label class="text-muted">FOLIO NOTA VENTA :</label> 
        <input type="text" id="txtFolio" name="txtFolio" readonly="readOnly"/>
        <br /><br/>
        <table>
            <tr>
                <td><label class="text-muted">DE:</label></td>
                <td><input type="text" id="txtCliente" name="txtCliente" readonly="readOnly" />
                <td><input type="text" id="txtCargo" name="txtCargo" readonly="readOnly" /></td>                
            </tr>
            <tr>
                <td><input type="text" id="txtCantidadLetra" name="txtCantidadLetra" readonly="readOnly" />  </td> 
            </tr>
            <tr>
                <td><label class="text-muted">POR CONCEPTO DE:</label></td>
                <td><input type="text" id="txtNotaVenta" name="txtNotaVenta" readonly="readOnly" />  </td>                  
            </tr>
            
             <tr>
                <td><label class="text-muted">DEL:</label></td>
                <td><input type="text" id="txtFechaRegistro1" name="txtFechaRegistro1" readonly="readOnly" />
                    <label id="lblErrorFechaPago" class="text-muted" style="display: none;">Campo requerido</label></td>
                <td><label class="text-muted">AL:</label>
                <input type="text" id="txtFechaPago" name="txtFechaPago" readonly="readOnly" />
                    <label id="lblErrorFechaPago" class="text-muted" style="display: none;">Campo requerido</label></td>
            </tr>
        </table>
        
    </form>
</div>

