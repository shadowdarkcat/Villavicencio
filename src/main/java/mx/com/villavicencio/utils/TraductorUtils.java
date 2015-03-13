package mx.com.villavicencio.utils;

import java.text.DecimalFormat;
import java.util.StringTokenizer;

/**
 *
 * @author Gabriel J
 */
public class TraductorUtils {

    private static final String UNIDADES[] = {"cero", "un", "dos", "tres", "cuatro",
        "cinco", "seis", "siete", "ocho", "nueve"};

    private static final String DECENAS[] = {null, "diez", "veinte", "treinta",
        "cuarenta", "cincuenta", "sesenta", "setenta", "ochenta", "noventa"};

    private static final String CENTENAS[] = {null, "ciento", "doscientos", "trescientos",
        "cuatrocientos", "quinientos", "seiscientos", "setecientos",
        "ochocientos", "novecientos"};

    private final String MILES[] = {null, "mil", "dos mil", "tres mil", "cuatro mil",
        "cinco mil", "seis mil", "siete mil", "ocho mil", "nueve mil"};

    public static String traducirRefactor(Number numero) {
        StringBuilder cantidadConLetra = new StringBuilder();
        if (numero == null) {
            return "El valor es nulo, favor de verificarlo.";
        }
        DecimalFormat formatter = new DecimalFormat("###########0.00");
        String s = formatter.format(numero);
        String[] cantidadArray = s.split("\\.");

        String parteEntera = new StringBuilder(cantidadArray[0]).reverse().toString();
        String parteDecimal = cantidadArray[1];

        String[] parteString = parteEntera.trim().split("");
        char[] parteEnteraChars = parteEntera.toCharArray();

        for (int i = parteEnteraChars.length - 1; i >= 0; i--) {
            String cantidad = null;
            switch(i) {
                case 0:
                    if(parteEnteraChars.length == 1) {
                        cantidad = UNIDADES[Integer.parseInt(String.valueOf(parteEnteraChars[i]))];
                    } else if (Integer.parseInt(String.valueOf(parteEnteraChars[i])) != 0) {
                        cantidad = " y " + UNIDADES[Integer.parseInt(String.valueOf(parteEnteraChars[i]))];
                    }
                    break;

                case 1:
                    cantidad = DECENAS[Integer.parseInt(String.valueOf(parteEnteraChars[i]))];
                    break;

                case 2:
                    if(Integer.parseInt(String.valueOf(parteEnteraChars[i - 1])) == 0
                            && Integer.parseInt(String.valueOf(parteEnteraChars[i - 2])) == 0) {

                        cantidad = CENTENAS[Integer.parseInt(String.valueOf(parteEnteraChars[i]))];
                        if(cantidad.equalsIgnoreCase("ciento")) {
                            cantidad = "cien";
                        }
                    } else {
                        cantidad = CENTENAS[Integer.parseInt(String.valueOf(parteEnteraChars[i]))];
                    }
                    break;

                case 3:
                    break;

                case 4:
                    break;

                case 5:
                    break;

                case 6:
                    break;

                case 7:
                    break;

                case 8:
                    break;

                case 9:
                    break;

                case 10:
                    break;
            }
            if (cantidad != null) {
                cantidadConLetra.append(cantidad);
            }
        }

        cantidadConLetra = new StringBuilder(correggirCatidad(cantidadConLetra.toString()));
        cantidadConLetra.append(" pesos ").append(parteDecimal).append("/M.N");
        return cantidadConLetra.toString();
    }

    private static String correggirCatidad(String cantidad) {
        if(cantidad.contains("diez y ")) {
            cantidad = cantidad.replace("diez y un", "once");
            cantidad = cantidad.replace("diez y dos", "doce");
            cantidad = cantidad.replace("diez y tres", "trece");
            cantidad = cantidad.replace("diez y cuatro", "catorce");
            cantidad = cantidad.replace("diez y cinco", "quince");
            cantidad = cantidad.replace("diez y seis", "dieciseis");
            cantidad = cantidad.replace("diez y siete", "diecisiete");
            cantidad = cantidad.replace("diez y ocho", "dieciocho");
            cantidad = cantidad.replace("diez y nueve", "diecinueve");
        }
        if(cantidad.contains("veinte y ")) {
            cantidad = cantidad.replace("veinte y un", "veintiun");
            cantidad = cantidad.replace("veinte y dos", "veintidos");
            cantidad = cantidad.replace("veinte y tres", "veintitres");
            cantidad = cantidad.replace("veinte y cuatro", "veinticuatro");
            cantidad = cantidad.replace("veinte y cinco", "veinticinco");
            cantidad = cantidad.replace("veinte y seis", "veintiseis");
            cantidad = cantidad.replace("veinte y siete", "veintisiete");
            cantidad = cantidad.replace("veinte y ocho", "veintiocho");
            cantidad = cantidad.replace("veinte y nueve", "veintinueve");
        }
        return cantidad;
    }

    public static String traducir(Number numero) {
        if (numero == null) {
            return "El valor es nulo, favor de verificarlo.";
        }
        DecimalFormat formatter = new DecimalFormat("#########0.00");
        String s = formatter.format(numero);
        String s1 = "";
        if (s.compareTo("") == 0) {
            return "";
        }

        try {
            String as[] = new String[10];
            String as1[] = new String[10];
            String as2[] = new String[10];
            String as3[] = new String[10];
            String s2 = new String("");
            String s3 = new String("");
            String s4 = new String("");
            String s5 = new String("");
            String s6 = new String("0");
            String s7 = new String("");
            String s8 = new String("");
            String s9 = new String("");
            String s10 = new String("");
            String s11 = new String("");
            String s13 = new String("");
            String s14 = new String("");
            String s15 = new String("");
            int i = 0;
            int j = 0;
            int k = 0;
            byte byte0 = 0;
            boolean flag = false;
            as[0] = "cero";
            as[1] = "un";
            as[2] = "dos";
            as[3] = "tres";
            as[4] = "cuatro";
            as[5] = "cinco";
            as[6] = "seis";
            as[7] = "siete";
            as[8] = "ocho";
            as[9] = "nueve";
            as1[1] = "diez";
            as1[2] = "veinte";
            as1[3] = "treinta";
            as1[4] = "cuarenta";
            as1[5] = "cincuenta";
            as1[6] = "sesenta";
            as1[7] = "setenta";
            as1[8] = "ochenta";
            as1[9] = "noventa";
            as2[1] = "ciento";
            as2[2] = "doscientos";
            as2[3] = "trescientos";
            as2[4] = "cuatrocientos";
            as2[5] = "quinientos";
            as2[6] = "seiscientos";
            as2[7] = "setecientos";
            as2[8] = "ochocientos";
            as2[9] = "novecientos";
            as3[1] = "mil";
            as3[2] = "dos mil";
            as3[3] = "tres mil";
            as3[4] = "cuatro mil";
            as3[5] = "cinco mil";
            as3[6] = "seis mil";
            as3[7] = "siete mil";
            as3[8] = "ocho mil";
            as3[9] = "nueve mil";
            i = s.length();
            //System.out.println("grande" + i);ñ
            StringTokenizer tokens = new StringTokenizer(s, ".");
            int ban = 0;
            while (tokens.hasMoreTokens()) {
                if (ban == 0) {
                    s14 = tokens.nextToken();
                    ban++;
                } else {
                    s13 = tokens.nextToken();
                }
            }
            if (s13.length() > 2) {
                s13 = s13.substring(0, 2);
            } else {
                while (s13.length() < 2) {
                    s13 += "0";
                }
            }
            k = s14.length();
            byte0 = 1;
            if (k > 6) {
                s6 = s14;
                int l = k;
                s14 = s14.substring(0, k - 6);
                k -= 6;
                byte0 = 2;
            }
            for (int j1 = 1; j1 <= byte0; j1++) {
                if (j1 == 2) {
                    s14 = s6.substring(s6.length() - 6, s6.length());
                    k = 6;
                }
                String s12;
                if (Integer.parseInt(s14) % 10 != 0) {
                    s12 = as[Integer.parseInt(s14.substring(k - 1, k))];
                } else if(Integer.parseInt(s14) > 0) {
                    s12 = "";
                } else {
                    s12 = "cero";
                }
                if (k > 1) {
                    if (Integer.parseInt(s14.substring(k - 2, k - 1)) > 0) {
                        if (Integer.parseInt(s14.substring(k - 1, k)) > 0) {
                            s10 = as1[Integer.parseInt(s14.substring(k - 2, k - 1))] + " y ";
                        } else {
                            s10 = as1[Integer.parseInt(s14.substring(k - 2, k - 1))];
                        }
                    } else {
                        s10 = "";
                    }
                }
                if (k > 2) {
                    if (Integer.parseInt(s14.substring(k - 3, k - 2)) > 1) {
                        s9 = as2[Integer.parseInt(s14.substring(k - 3, k - 2))] + " ";
                    } else {
                        if (Integer.parseInt(s14.substring(k - 3, k - 2)) != 0);
                        s9 = "";
                        if (Integer.parseInt(s14.substring(k - 3, k - 2)) == 1 && Integer.parseInt(s14.substring(2, s14.length())) > 0) {
                            s9 = as2[Integer.parseInt(s14.substring(k - 3, k - 2))] + " ";
                        }
                        if (Integer.parseInt(s14.substring(k - 3, k - 2)) == 1 && Integer.parseInt(s14.substring(2, s14.length())) == 0) {
                            s9 = "ciento ";
                        }
                    }
                }
                if (k > 3) {
                    if (Integer.parseInt(s14.substring(k - 4, k - 3)) > 1 && Integer.parseInt(s14.substring(k - 1, k - 0)) == 0) {
                        s5 = as[Integer.parseInt(s14.substring(k - 4, k - 3))] + " mil";
                    } else if(Integer.parseInt(s14.substring(k - 4, k - 3)) > 1 && Integer.parseInt(s14.substring(k - 1, k - 0)) > 0) {
                        s5 = as[Integer.parseInt(s14.substring(k - 4, k - 3))] + " mil ";
                    }
                    else {
                        if (Integer.parseInt(s14.substring(k - 4, k - 3)) == 1) {
                            s5 = "un mil ";
                        }
                        if (Integer.parseInt(s14.substring(k - 4, k - 3)) == 0) {
                            s5 = " mil ";
                        }
                    }
                }
                if (k > 4) {
                    if (Integer.parseInt(s14.substring(k - 5, k - 4)) > 0) {
                        if (Integer.parseInt(s14.substring(k - 4, k - 3)) > 0) {
                            s2 = as1[Integer.parseInt(s14.substring(k - 5, k - 4))] + " y ";
                        } else {
                            s2 = as1[Integer.parseInt(s14.substring(k - 5, k - 4))];
                        }
                    } else {
                        s2 = "";
                    }
                }
                if (k > 5) {
                    if (Integer.parseInt(s14.substring(k - 6, k - 5)) > 1) {
                        s3 = as2[Integer.parseInt(s14.substring(k - 6, 1))] + " ";
                    } else {
                        if (Integer.parseInt(s14.substring(k - 6, k - 5)) == 0) {
                            s3 = "";
                        }
                        if (Integer.parseInt(s14.substring(k - 6, k - 5)) == 1 && Integer.parseInt(s14.substring(k - 5, k - 3)) > 0) {
                            s3 = as2[Integer.parseInt(s14.substring(k - 6, 1))] + " ";
                        }
                        if (Integer.parseInt(s14.substring(k - 6, k - 5)) == 1 && Integer.parseInt(s14.substring(k - 5, k - 3)) == 0) {
                            s3 = "cien ";
                        }
                    }
                }
                if (s10.equalsIgnoreCase("diez y ")) {
                    switch (Integer.parseInt(s14.substring(k - 1, k))) {
                        case 1: // '\001'
                            s7 = "once";
                            break;

                        case 2: // '\002'
                            s7 = "doce";
                            break;

                        case 3: // '\003'
                            s7 = "trece";
                            break;

                        case 4: // '\004'
                            s7 = "catorce";
                            break;

                        case 5: // '\005'
                            s7 = "quince";
                            break;

                        case 6: // '\006'
                            s7 = "dieciseis";
                            break;

                        case 7: // '\007'
                            s7 = "diecisiete";
                            break;

                        case 8: // '\b'
                            s7 = "dieciocho";
                            break;

                        case 9: // '\t'
                            s7 = "diecinueve";
                            break;

                        case 0: // '\0'
                            s7 = "diez";
                            break;
                    }
                    s10 = s7;
                    s12 = "";
                }
                if (s10.equalsIgnoreCase("veinte y ")) {
                    switch (Integer.parseInt(s14.substring(k - 1, k))) {
                        case 1: // '\001'
                            s7 = "veintiun";
                            break;

                        case 2: // '\002'
                            s7 = "veintidos";
                            break;

                        case 3: // '\003'
                            s7 = "veintitres";
                            break;

                        case 4: // '\004'
                            s7 = "veinticuatro";
                            break;

                        case 5: // '\005'
                            s7 = "veinticinco";
                            break;

                        case 6: // '\006'
                            s7 = "veintiseis";
                            break;

                        case 7: // '\007'
                            s7 = "veintisiete";
                            break;

                        case 8: // '\b'
                            s7 = "veintiocho";
                            break;

                        case 9: // '\t'
                            s7 = "veintinueve";
                            break;

                        case 0: // '\0'
                            s7 = "veinte";
                            break;
                    }
                    s10 = s7;
                    s12 = "";
                }
                if (s2.equalsIgnoreCase("diez y ")) {
                    switch (Integer.parseInt(s14.substring(k - 4, k - 3))) {
                        case 1: // '\001'
                            s7 = "once mil ";
                            break;

                        case 2: // '\002'
                            s7 = "doce mil ";
                            break;

                        case 3: // '\003'
                            s7 = "trece mil ";
                            break;

                        case 4: // '\004'
                            s7 = "catorce mil ";
                            break;

                        case 5: // '\005'
                            s7 = "quince mil ";
                            break;

                        case 6: // '\006'
                            s7 = "dieciseis mil ";
                            break;

                        case 7: // '\007'
                            s7 = "diecisiete mil ";
                            break;

                        case 8: // '\b'
                            s7 = "dieciocho mil ";
                            break;

                        case 9: // '\t'
                            s7 = "diecinueve mil ";
                            break;

                        case 0: // '\0'
                            s7 = "diez mil ";
                            break;
                    }
                    s2 = s7;
                    s5 = "";
                }
                if (s2.equalsIgnoreCase("veinte y ")) {
                    switch (Integer.parseInt(s14.substring(k - 4, k - 3))) {
                        case 1: // '\001'
                            s7 = "veintiun mil ";
                            break;

                        case 2: // '\002'
                            s7 = "veintidos mil ";
                            break;

                        case 3: // '\003'
                            s7 = "veintitres mil ";
                            break;

                        case 4: // '\004'
                            s7 = "veinticuatro mil ";
                            break;

                        case 5: // '\005'
                            s7 = "veinticinco mil ";
                            break;

                        case 6: // '\006'
                            s7 = "veintiseis mil ";
                            break;

                        case 7: // '\007'
                            s7 = "veintisiete mil ";
                            break;

                        case 8: // '\b'
                            s7 = "veintiocho mil ";
                            break;

                        case 9: // '\t'
                            s7 = "veintinueve mil ";
                            break;

                        case 0: // '\0'
                            s7 = "veinte ";
                            break;
                    }
                    s2 = s7;
                    s5 = "";
                }
                if (j1 == 1 && byte0 == 2) {
                    s8 = s3 + s2 + s5 + s9 + s10 + s12;
                    if (s8.substring(0, 2).compareTo("un") == 0) {
                        s8 = s3 + s2 + s5 + s9 + s10 + s12 + " mill\363n ";
                    } else {
                        s8 = s3 + s2 + s5 + s9 + s10 + s12 + " millones ";
                    }
                }
                if (byte0 > 1 && Integer.parseInt(s14.substring(0, 1)) == 0 && Integer.parseInt(s14.substring(1, 2)) == 0 && Integer.parseInt(s14.substring(2, 3)) == 0) {
                    s5 = "";
                }
                if (j1 == 1 && byte0 == 1) {
                    s8 = s3 + s2 + s5 + s9 + s10 + s12;
                }
                if (j1 == 2 && byte0 == 2) {
                    s8 = s8 + s3 + s2 + s5 + s9 + s10 + s12;
                }
            }

            String s16 = new String("");
            //System.out.println("numero" + s);
            if (Double.parseDouble(s) >= 1.0D && Double.parseDouble(s) < 2D) {
                s16 = " peso ";
            } else {
                s16 = " pesos ";
            }
            //System.out.println("respnu" + s6);
            if (Double.parseDouble(s6) >= 1000000D && Double.parseDouble(s6) % 1000000D == 0.0D) {
                s1 = "(" + s8 + " de pesos " + s13 + "/100 M.N.)";
            } else {
                s1 = "(" + s8 + s16 + s13 + "/100 M.N.)";
            }
        } catch (Exception exception) {
            s1 = "Cantidad inválida, verifique por favor";
        }
        return s1;
    }
}
