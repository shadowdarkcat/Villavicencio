function mayuscula(campo) {
    $(campo).keyup(function () {
        $(this).val($(this).val().toUpperCase());
    });
}

function minuscula(campo) {
    $(campo).keyup(function () {
        $(this).val($(this).val().toLowerCase());
    });
}

function formato_numero(numero, decimales, separador_decimal, separador_miles, Simbolo) { 
    numero = parseFloat(numero);
    if (isNaN(numero)) {
        return "";
    }
    if (decimales !== undefined) {
        numero = numero.toFixed(decimales);
    }
    numero = numero.toString().replace(".", separador_decimal !== undefined ? separador_decimal : ",");
    if (separador_miles) {
        var miles = new RegExp("(-?[0-9]+)([0-9]{3})");
        while (miles.test(numero)) {
            numero = numero.replace(miles, "$1" + separador_miles + "$2");
        }
    }
    return Simbolo + numero;
}