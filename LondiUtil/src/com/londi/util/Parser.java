/*
 * Parser.java
 * Copyright (c) MACLE Sistemas LTDA.
 * Criado em 07 de fevereiro de 2023 - 12:41:52
 * Projeto ProjetosLondi
 * @author Márlon Schoenardie
 */
package com.londi.util;

import org.apache.commons.lang3.time.FastDateFormat;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Classe utilitária para conversões de tipos
 */
public class Parser {
    private static final DecimalFormat FMT_VALOR = new DecimalFormat("#,###,##0.00");
    private static final DecimalFormat FMT_QTD = new DecimalFormat("#0.##");
    private static final FastDateFormat FMT_DATA = FastDateFormat.getInstance("dd/MM/yyyy");
    private static final FastDateFormat FMT_HOUR = FastDateFormat.getInstance("HH:mm:ss");
    private static final Pattern PATTERN_EMAIL = Pattern.compile("^[\\w-]+(\\.[\\w-]+)*@([\\w-]+\\.)+[a-zA-Z]{2,7}$");

    /**
     * converte o valor String para um Int
     *
     * @param arg0 String
     * @return int
     */
    public static int strToInt(final String arg0) {
        int result = 0;
        if ((arg0 != null) && (!"".equals(arg0.trim())) && (!"null".equalsIgnoreCase(arg0.trim()) && ! "undefined".equalsIgnoreCase(arg0.trim()))) {
            result = Integer.parseInt(arg0.trim());
        }
        return result;
    }

    /**
     * converte o valor String para um long
     *
     * @param arg0 String
     * @return long
     */
    public static long strToLong(final String arg0) {
        long result = 0;
        if ((arg0 != null) && (!"".equals(arg0.trim())) && ! "null".equalsIgnoreCase(arg0.trim()) && ! "undefined".equalsIgnoreCase(arg0.trim())) {
            result = Long.parseLong(arg0);
        }
        return result;
    }

    /**
     * converte o valor String para um string se falhar (throw exception) retorna o valor default
     *
     * @param arg0 String
     * @param arg1 valor default
     * @return int
     */
    public static int strToIntDef(final String arg0, final int arg1) {
        int result = arg1;
        if (arg0 != null && !"".equals(arg0.trim()) && !"null".equalsIgnoreCase(arg0.trim()) && ! "undefined".equalsIgnoreCase(arg0.trim())) {
            try {
                result = strToInt(arg0);
            } catch (Throwable ignored) { } // valor default sera retornado
        }
        return result;
    }

    /**
     * Converte String para Date
     *
     * @param date string com a data
     * @param format formado ex "dd/MM/yyyy"
     * @return Date
     * @throws java.text.ParseException
     * @see java.util.Date
     */
    public static Date strToDate(String date, final String format) throws ParseException {
        if (format != null && !format.isEmpty()) {
            if (date != null && !"".equals(date.trim())) {
                // tenta tratar deformidades da digitacao
                if (date.length() < 10) {
                    String[] partes = date.split("/");
                    if (partes.length == 3) {
                        if (partes[2].length() == 2) {
                            int ano = strToInt(partes[2]);
                            if (ano <= 30) {
                                ano += 2000;
                            } else {
                                ano += 1900;
                            }
                            partes[2] = ano + "";
                        } else if (partes[2].length() != 4) {
                            throw new ParseException("Formato da data " + date + " estÃ¡ incorreto", 0);
                        }
                        date = partes[0] + "/" + partes[1] + "/" + partes[2];
                    }
                }
                java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(format);
                return sdf.parse(date);
            } else {
                return null;
            }
        } else {
            throw new ParseException("Formato (pattern) da data nÃ£o informado!", 0);
        }
    }

    /**
     * Retorna o OBJ de data conforme string recebida reconhece apenas em dois formatos "dd/mm/aaaa" ou "aaaa-mm-dd"
     * @param date string formatada com a data
     * @return Date
     * @throws java.text.ParseException
     */
    public static Date strToDate(String date) throws ParseException {
        if (date != null && ! "".equals(date.trim()) && ! "undefined".equalsIgnoreCase(date) && ! "null".equalsIgnoreCase(date)) {
            if (date.contains("-")) {
                return strToDate(date, "yyyy-MM-dd");
            } else {
                return strToDate(date, "dd/MM/yyyy");
            }
        } else {
            return null;
        }
    }

    /**
     * Tenta converter String para Date senão devolve data default
     *
     * @param date string com a data
     * @param def data padrÃ£o a ser devolvida se o parse falhar
     * @return Date
     * @see java.util.Date
     */
    public static Date strToDateDef(String date, final Date def) {
        try {
            if (date != null && !"".equals(date.trim()) && !"null".equals(date)) {
                return strToDate(date);
            } else {
                return def;
            }
        } catch (ParseException e) {
            return def;
        }
    }

    /**
     * Tenta converter String para Date senão devolve data default
     *
     * @param date string com a data
     * @param format string com o pattern estilo "dd/MM/yy"
     * @param def data padrã a ser devolvida se o parse falhar
     * @return Date
     * @see java.util.Date
     */
    public static Date strToDateDef(String date, String format, final Date def) {
        try {
            if (date != null && !"".equals(date.trim())) {
                return strToDate(date, format);
            } else {
                return def;
            }
        } catch (ParseException e) {
            return def;
        }
    }

    /**
     * Converte String para hora
     *
     * @param hora string com a hora
     * @return Date
     * @throws java.text.ParseException
     */
    public static Date strToTime(final String hora) throws ParseException {
        return strToDate(hora, "HH:mm:ss");
    }

    /**
     * Converte String para hora
     *
     * @param dataHora string com a hora e hora
     * @return Date
     * @throws java.text.ParseException
     */
    public static Date strToDateTime(final String dataHora) throws ParseException {
        if (dataHora.contains("-")) {
            return strToDate(dataHora, "yyyy-MM-dd HH:mm:ss");
        } else {
            return strToDate(dataHora, "dd/MM/yyyy HH:mm:ss");
        }
    }

    /**
     * Converte Date para String
     *
     * @param date string com a data
     * @param format formado ex "dd/MM/yyyy"
     * @return Date
     */
    public static String dateToStr(final Date date, final String format) {
        if (date != null) {
            if (DateUtil.isDefaultDate(date)) {
                return "";
            }
            FastDateFormat df = FastDateFormat.getInstance(format);
            return df.format(date);
        } else {
            return "";
        }
    }

    /**
     * Converte Date para String
     *
     * @param date string com a data
     * @return Date
     */
    public static String dateToStr(final Date date) {
        if (DateUtil.isDefaultDate(date)) {
            return "";
        }
        return FMT_DATA.format(date);
    }

    /**
     * Retorna data e hora em String no formato '23/12/2005 13:45:17'
     *
     * @param dateTime data e hora
     * @return String com data hora
     */
    public static String dateTimeToStr(final Date dateTime) {
        return dateToStr(dateTime, "dd/MM/yyyy HH:mm:ss");
    }

    /**
     * Converte hora em string no formato 23:59:59
     *
     * @param hora date com hora a ser parseado
     * @return String formatada
     * @see java.util.Date
     */
    public static String timeToStr(final Date hora) {
        return timeToStr(hora, "HH:mm:ss");
    }

    /**
     * Converte hora em string no formato especificado
     *
     * @param hora date com hora a ser parseado
     * @param format formato da hora
     * @return String formatada
     * @see java.util.Date
     */
    public static String timeToStr(final Date hora, final String format) {
        if (hora != null) {
            if ("HH:mm:ss".equals(format)) {
                return FMT_HOUR.format(hora);
            } else {
                FastDateFormat dtime = FastDateFormat.getInstance(format);
                return dtime.format(hora);
            }
        } else {
            return "";
        }
    }

    /**
     * Converte Formata numero float para string
     *
     * @param number numero a ser formatado
     * @param mask mascra
     * @return String formatada
     */
    public static String floatToStrF(final double number, final String mask) {
        DecimalFormat f = new DecimalFormat();
        if (Double.isNaN(number)) {
            return "";
        } else if ((mask != null) && (!mask.isEmpty())) {
            f.applyPattern(mask);
        }
        return f.format(number);
    }

    /**
     * formatação para valores em geral (separador de milhar, 2 casas decimais)
     * @param number numero a ser formatado
     * @return String formatada
     */
    public static String floatToStr(final double number) {
        if (Double.isNaN(number)) {
            return "";
        } else {
            return FMT_VALOR.format(number);
        }
    }

    /**
     * formatação para quantidade (casas decimais opcionais, de 0 a 2)
     * @param number numero a ser formatado
     * @return String formatada
     */
    public static String floatToStrQtd(final double number) {
        return FMT_QTD.format(number);
    }

    /**
     * Converte Formata String para float
     *
     * @param number numero a ser formatado
     * @param mask mascara a ser utilizada #.00
     * @return double
     */
    public static double strToFloat(final String number, final String mask) throws ParseException {
        if ((number != null) && !(number.trim().isEmpty()) && ! "null".equalsIgnoreCase(number) && ! "undefined".equalsIgnoreCase(number)) {
            DecimalFormat f = new DecimalFormat();
            if ((mask != null) && (!mask.isEmpty())) {
                f.applyPattern(mask);
            }
            return f.parse(number.trim()).doubleValue();
        } else {
            return 0;
        }
    }

    @Deprecated
    public static double StrToFloat(String number)
            throws ParseException {
        return strToFloat(number, "#,##0.00");
    }

    public static double strToFloat(final String number)
            throws ParseException {
        return strToFloat(number, "#,##0.00");
    }

    public static double strToFloatDef(final String number, final double value) {
        try {
            return strToFloat(number, "#,##0.00");
        } catch (Throwable e) {
            return value;
        }
    }

    /**
     * Formata double em String com numero definido de casas no padrÃ£o JAVA
     *
     * @param x double a ser convertido
     * @param digit numero de digitos depois do '.'
     * @return String formatada
     */
    public static String floatToStrJ(double x, int digit) {
        return String.format("%." + digit + "f", x).replace(",", ".");
    }

    /**
     * Remove todos os caracteres excetos 0-9
     *
     * @param arg String formatada com . . / : ...
     * @return String somente com digitos de 0 .. 9
     */
    public static String trimToDigits(final String arg) {
        return (arg == null) ? "" : arg.replaceAll("[^0-9]", "");
    }

    /**
     * Remove todos os caracteres excetos 0-9 e pontucao
     * rotina para extrair numero de strings
     * @param arg String formatada com . . / : ...
     * @return String somente com digitos de 0 .. 9
     */
    public static String trimToNumber(final String arg) {
        String result = (arg == null) ? "" : arg.replaceAll("[^0-9\\-.,]", "");
        if (! StringUtil.isEmptyStr(result)) {
            if (result.endsWith(".")) {
                result = result.substring(0, result.length() - 1);
            }
        }
        return result;
    }

    /**
     * Remove todos os caracteres e numeros excetos a-z | A-Z
     *
     * @param arg formatada com caracteres . / , ...
     * @return String somente com digitos de a-z | A-Z
     */
    public static String trimToChars(final String arg) {
        return trimToChars(arg, "");
    }

    public static String trimToChars(final String arg, final String replace) {
        return (arg == null) ? "" : arg.replaceAll("[^a-zA-Z]", replace);
    }

    /**
     * Insere zeros a esquerda
     *
     * @param str String
     * @param newLen int - tamanho da nova string com zeros a esquerda
     * @param fillChar caracter que sera usado para porencher o tamanho
     * @param trunc boolean - se a string original for maior que NnewLen ela trunca
     * @return String com zeros a esquerda
     */
    public static String LFill(final String str, final int newLen, final char fillChar, final boolean trunc) {
        StringBuilder result = (str != null) ? new StringBuilder(str) : new StringBuilder();
        if (str == null || str.length() <= newLen) {
            while (result.length() < newLen) {
                result.insert(0, fillChar);
            }
        } else {
            if (trunc) {
                return result.substring(0, newLen);
            }
        }
        return result.toString();
    }

    public static String LFill(String str, int newLen, boolean trunc) {
        return LFill(str, newLen, '0', trunc);
    }

    public static String LFill(int value, int newLen, boolean trunc) {
        return LFill(value + "", newLen, trunc);
    }

    public static String LFill(long value, int newLen, boolean trunc) {
        return LFill(value + "", newLen, trunc);
    }

    /**
     * monta string copiando chars por nroRepl vezes. Ex: string("ab", 3) = "ababab"
     * @param chars qualquer string a multiplicar
     * @param nroRepl numero de multiplicacoes
     * @return String
     */
    public static String string(final String chars, int nroRepl) {
        if (chars == null || chars.length() == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        while ((nroRepl--) > 0) {
            sb.append(chars);
        }
        return sb.toString();
    }

    /**
     * Insere caracteres a direita
     *
     * @param str texto a ser completado com caracteres a direita
     * @param newLen - tamanho da nova string com zeros a esquerda
     * @param fillChar caracter que sera usado para porencher o tamanho
     * @param trunc - se a string original for maior que NnewLen ela trunca
     * @return String com (0) zeros a direita
     */
    public static String RFill(final String str, final int newLen, final char fillChar, final boolean trunc) {
        StringBuilder result = (str != null) ? new StringBuilder(str) : new StringBuilder();
        if (str == null || str.length() <= newLen) {
            while (result.length() < newLen) {
                result.append(fillChar);
            }
        } else {
            if (trunc) {
                if (newLen > 0) {
                    return result.substring(0, newLen);
                } else {
                    return "";
                }
            }
        }
        return result.toString();
    }

    public static String RFill(String str, int NewLen) {
        return RFill(str, NewLen, ' ', true);
    }

    /**
     * Formata o fone
     * <br>
     * Telefone com 8 digitos
     * <br>
     * (051) 3030-2020, (51) 2020-1010, 3010-2020
     * <br><br>
     * Telefone com 9 digitos
     * <br>
     * (051) 93030-2020, (51) 92020-1010, 93010-2020
     * <br>
     * @param fone fone a ser formatado
     * @return String formatada
     */
    public static String formatFone(String fone) {
        String S = fone != null ? trimToDigits(fone) : "";
        try {
            if (S.length() == 7) {
                S = S.substring(0, 3) + "-" + S.substring(3);
            } else if (S.length() == 8) {
                S = S.substring(0, 4) + "-" + S.substring(4);
            } else if (S.length() == 9) {
                S = S.substring(0, 5) + "-" + S.substring(5);
            } else if (S.length() == 10) {
                S = "(" + S.substring(0, 2) + ") " + S.substring(2, 6) + "-" + S.substring(6);
            } else if (S.length() == 11) {
                if (S.startsWith("0")) {
                    S = "(" + S.substring(0, 3) + ") " + S.substring(3, 7) + "-" + S.substring(7);
                } else {
                    S = "(" + S.substring(0, 2) + ") " + S.substring(2, 7) + "-" + S.substring(7);
                }
            } else if (S.length() == 12) {
                if (S.startsWith("0")) {
                    S = "(" + S.substring(0, 3) + ") " + S.substring(3, 8) + "-" + S.substring(8);
                } else {
                    S = "(" + S.substring(0, 2) + ") " + S.substring(2, 8) + "-" + S.substring(8);
                }
            }
        } catch (Exception e) {
            //do nothing
        }
        return S;
    }

    public static String formatFoneInternacional(String fone, String ddiPais) {
        String S = fone != null ? trimToDigits(fone) : "";
        try {
            if (!S.startsWith(ddiPais)) {
                S = "+" + ddiPais + " " + S;
            } else {
                S = "+" + S.substring(0, ddiPais.length()) + " " + S.substring(ddiPais.length());
            }
        } catch (Exception e) {
            //do nothing
        }
        return S;
    }

    /**
     * Formata o CEP ex 93300-000
     * @param CodCEP cep
     * @return String formatada
     */
    public static String formatCEP(final int CodCEP) {
        String S = LFill(CodCEP + "", 8, true);
        try {
            S = S.substring(0, 5) + "-" + S.substring(5);
        } catch (Exception e) {
            //do nothing
        }
        return S;
    }

    /**
     * Formata o CNPJ EX 43.992.908/0004-85
     * @param CNPJ cnpj a ser formatado
     * @return String formatada
     */
    public static String formataCNPJ(final String CNPJ) {
        String S = "";
        if ((CNPJ != null) && (!Parser.trimToDigits(CNPJ).isEmpty())) {
            S = CNPJ;
            S = LFill(S, 14, true);
            try {
                S = S.substring(0, 2) + "." + S.substring(2, 5) + "." + S.substring(5, 8) + "/" + S.substring(8, 12) + "-" + S.substring(12);
            } catch (Exception e) {
                // do nothing
            }
        }
        return S;
    }

    /**
     * Formata o CPF EX 801813910-85
     * @param CPF cpf a ser formatado
     * @return String formatada
     */
    public static String formataCPF(final String CPF) {
        return formataCPF(CPF, false);
    }

    /**
     * Formata CPF - EX. 801.813.910-85
     * @param CPF cpf a ser formatada
     * @param isPontos se deve incluir os .
     * @return cpf formatado
     */
    public static String formataCPF(final String CPF, boolean isPontos) {
        String S = "";
        if ((CPF != null) && (! Parser.trimToDigits(CPF).isEmpty())) {
            S = Parser.trimToDigits(CPF);
            S = LFill(S, 11, true);
            try {
                if (isPontos) {
                    S = S.substring(0, 3) + "." + S.substring(3, 6) + "." + S.substring(6, 9) + "-" + S.substring(9, 11);
                } else {
                    S = S.substring(0, 9) + "-" + S.substring(9, 11);
                }
            } catch (Exception e) {
                // do nothing
            }
        }
        return S;
    }

    /**
     * @param arg bool
     * @return retorna booleanos formatados S/N
     */
    public static String boolToStr(final boolean arg) {
        return arg ? "'S'" : "'N'";
    }

    /**
     * Converte dados de string em boolean
     *
     * @param bool string contendo S ou true para retornar true senÃ¯Â¿Â½o false
     * @return boolean
     */
    public static boolean strToBool(final String bool) {
        return "S".equalsIgnoreCase(bool) || "'S'".equalsIgnoreCase(bool) || "true".equalsIgnoreCase(bool) || "'true'".equalsIgnoreCase(bool) || "T".equalsIgnoreCase(bool) || "on".equalsIgnoreCase(bool) || "ativo".equalsIgnoreCase(bool) || "SIM".equalsIgnoreCase(bool) || "Y".equalsIgnoreCase(bool) || "YES".equalsIgnoreCase(bool);
    }

    /**
     * converte objeto para string utilizando dateToStr ou floatToStr conforme o tipo do objeto, com opcao de retorno default
     * @param obj qualquer OBJ
     * @param dflt valor default
     * @return String
     */
    public static String toStr(final Object obj, final String dflt) {
        if (obj == null) {
            return dflt;
        }
        if (obj instanceof String) {
            return (String) obj;
        }
        if (obj instanceof Date) {
            return dateToStr((Date) obj);
        }
        if (obj instanceof Double) {
            return floatToStr(((Double) obj));
        }
        if (obj instanceof Float) {
            return floatToStr(((Float) obj).doubleValue());
        }
        if (obj instanceof Boolean) {
            return boolToStr((Boolean) obj);
        }
        return obj.toString();
    }

    /**
     * converte objeto para string utilizando dateToStr ou floatToStr conforme o tipo do objeto
     * @param obj qualquer OBJ
     * @return String
     */
    public static String toStr(final Object obj) {
        return toStr(obj, "");
    }

    /**
     * Retorna se o E-mail informado é um E-mail valido
     *
     * @param email email a ser validado
     * @return boolean isEmailValido
     */
    public static boolean isEmailValido(final String email) {
        if (StringUtil.isEmptyStr(email)) return false;
        Matcher m = PATTERN_EMAIL.matcher(email.trim());
        return m.find();
    }

    public static String formataNrNFeDANFE(String numeroNota){
        String S = numeroNota != null ? numeroNota : "";
        S = LFill(S, 9, '0', true);
        try {
            String prim = "";
            String seg = "";
            String terc = "";
            prim = S.substring(0, 3).concat(".");
            seg = S.substring(3, 6).concat(".");
            terc = S.substring(6);
            S = prim.concat(seg).concat(terc);
        } catch (Exception e) {
            //do nothing
        }
        return S;
    }

    public static void main(String[] args) throws ParseException {
        System.out.println(Parser.strToFloat("969.348545489745", "#,00"));
    }
}
