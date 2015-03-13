<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div id="divAltaAbonos" title="Detalle Abonos"></div>
<div id="divRegistrarAbonos" title="Registro Abonos">
    <form id="frmRegistroAbono" name="frmRegistroAbono">
        <input type="hidden" id="txtIdNotaVenta" name="txtIdNotaVenta" readonly="readOnly" />
        <fieldset>
            <legend class="text-muted">TIPO DE PAGO</legend>
            <table>
                <tr>
                    <td><input type="radio" id="rdbTipoDePago" name="rdbTipoDePago" value="1" onclick="tipoDePago();">CHEQUE</td>
                </tr>
                <tr>
                    <td><input type="radio" id="rdbTipoDePago" name="rdbTipoDePago" value="2" onclick="tipoDePago();">DEPOSITO</td>
                </tr>
                <tr>
                    <td><input type="radio" id="rdbTipoDePago" name="rdbTipoDePago" value="3" onclick="tipoDePago();">EFECTIVO</td>
                </tr>
                <tr>
                    <td><input type="radio" id="rdbTipoDePago" name="rdbTipoDePago" value="4" onclick="tipoDePago();">TRANSFERENCIA</td>
                </tr>
            </table>
            <br/><br/>
            <div id="divBanco" style="display: none;">
                <select id="cboBanco" name="cboBanco">
                    <option>SELECCIONE ...</option>
                    <c:forEach items="${bancos}" var="bancos" varStatus="status">
                        <option value="${bancos.idBancos}">${bancos.nombre}</option>
                    </c:forEach>
                </select>
            </div>
        </fieldset>
        <br/><br/>
        <div id="divDatosAbono" style="display: none;">
            <table>
                <tr>
                    <td><label class="text-muted">FECHA REGISTRO:</label></td>
                    <td><input type="text" id="txtFecha"  name="txtFecha" disabled="disabled"></td>
                </tr>
                <tr>
                    <td><label class="text-muted">CANTIDAD A ABONAR: </label></td>
                    <td><input type="text"  id="txtCantidadAbono" name="txtCantidadAbono" onKeyUp="soloNumero(this);" disabled="disabled">
                        <label id="lblErrorCantidad" style="display: none;">Campo requerido</label></td>
                </tr>
            </table>
        </div>
    </form>
</div>

<div id="divTipoPagoNoSelected" title="Error">
    <table>
        <tr>
            <td style="text-align: center;">
                <p><span class="text-muted" >Por favor, seleccione el tipo de pago.</span></p>
            </td>
        </tr>
    </table>
</div>

<div id="divBancoNoSelected" title="Error">
    <table>
        <tr>
            <td style="text-align: center;">
                <p><span class="text-muted" >Por favor, seleccione un banco.</span></p>
            </td>
        </tr>
    </table>
</div>