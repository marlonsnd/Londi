/*
 * StringUtil.java
 * Copyright (c) MACLE Sistemas LTDA.
 * Criado em 07 de fevereiro de 2023 - 12:53:14
 * Projeto ProjetosLondi
 * @author Márlon Schoenardie
 */
package com.londi.util;

import org.apache.commons.text.StringEscapeUtils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

/**
 * Classe utilitaria de manuseio de strings
 */
public abstract class StringUtil {

    /**
     * verifica se a string esta em branco ou nula
     *
     * @param arg0 do tipo String
     * @return boolean
     */
    public static boolean isEmptyStr(final String arg0) {
        return (arg0 == null) || ("".equals(arg0.trim())) || "null".equalsIgnoreCase(arg0.trim()) || "undefined".equalsIgnoreCase(arg0.trim());
    }

    /**
     * verifica se a STR é um número
     *
     * @param arg0 a ser testado do tipo String
     * @return boolean
     */
    public static boolean isValidItenger(final String arg0) {
        if (arg0 == null || "".equals(arg0.trim())) {
            return false;
        }
        String pattern = "0123456789";
        for (int i = 0; i < arg0.length(); i++) {
            if (pattern.indexOf(arg0.charAt(i)) == -1) {
                return false;
            }
        }
        return true;
    }

    /**
     * Quebra a string conforme informado na quantidade de caracter por linha
     *
     * @param arg0 String a ser quebrada
     * @param qtdCaracterLinha qtd maximo de carateres por linha
     * @return String
     */
    public static String quebraString(String arg0, int qtdCaracterLinha) {
        if (arg0 == null || "".equals(arg0)) {
            return "";
        }
        boolean patternEspaco = false;
        String pattern = " ";
        StringBuilder novaLinha = new StringBuilder();
        int cont = 1;
        for (int i = 0; i < arg0.length(); i++) {
            if (cont == qtdCaracterLinha || patternEspaco) {
                if (pattern.indexOf(arg0.charAt(i)) != -1) {
                    novaLinha.append("<br>").append(arg0.charAt(i));
                    patternEspaco = false;
                    cont = 0;
                } else {
                    novaLinha.append(arg0.charAt(i));
                    patternEspaco = true;
                }

            } else {
                novaLinha.append(arg0.charAt(i));
            }
            cont++;
        }
        return novaLinha.toString();
    }

    /**
     * Retorna a palavra ou frase capitalizada<BR> EX: <b>D</b>eixando a <b>P</b>rimeira <b>L</b>etra em <b>M</b>aisculo
     *
     * @param arg0 String a ser capitalizada
     * @return String Capitalizada
     */
    public static String capitalizarString(String arg0) {
        if (null == arg0 || "".equals(arg0) || arg0.length() == 0) {
            return "";
        }
        String[] s = arg0.split(" ");
        StringBuilder result = new StringBuilder();
        for (String item : s) {
            if (item.length() > 2) {
                result.append(item.substring(0, 1).toUpperCase()).append(item.substring(1).toLowerCase()).append(" ");
            } else {
                result.append(item.toLowerCase()).append(" ");
            }
        }
        if (isEmptyStr(arg0)) {
            return "";
        }
        try {
            return result.substring(0, result.length() - 1);
        } catch (Exception ex) {
            return "";
        }
    }

    /**
     * Retorna a palavra ou frase capitalizada e efetua correção de palavras para formato HTML<BR> EX capitalizada: <b>D</b>eixando a <b>P</b>rimeira <b>L</b>etra em
     * <b>M</b>aisculo<BR> EX correção Calcados = Cal&ccedil;ado
     *
     * @param arg0 String a ser capitalizada
     * @param isCorrecaoPalavra TRUE para corrigir/acentuar a resposta
     * @return String capitalizada
     */
    public static String capitalizarString(String arg0, boolean isCorrecaoPalavra) {
        String strCapitalizada = capitalizarString(arg0.replace("/", "/ "));
        if (isCorrecaoPalavra) {
            return correcaoPalavraSite(strCapitalizada);
        } else {
            return strCapitalizada;
        }
    }

    /**
     * Retorna a frase corrigida palavra por palavra da frase
     * @param frase String a ser acentuada/corrigida
     * @return String Frase
     */
    public static String correcaoFraseSite(String frase) {
        String[] s = frase.split(" ");
        StringBuilder result = new StringBuilder();
        for (String item : s) {
            String correcaoPalavra = correcaoPalavraSite(item);
            result.append(correcaoPalavra).append(" ");
        }
        return result.toString();
    }

    /**
     * Retorna a palavra com coreção de acentuação
     * @param palavra palavra a ser corrigida/acentuada
     * @return String
     */
    public static String correcaoPalavraSite(String palavra) {
        String result = palavra;
        // substitui tags
        if (palavra.length() > 3) {
            result = palavra
                    //marca
                    .replace("Bebece ", "Bebec&ecirc;")
                    .replace("pe Com pe", "P&eacute; com P&eacute;")
                    .replace("Novope", "Novop&eacute;")
                    .replace("Coca Cola", "Coca-Cola")
                    .replace("Coca-cola", "Coca-Cola")
                    .replace("Intuicao", "Intui&ccedil;&atilde;o")
                    .replace("d Moon", "D Moon")
                    .replace("Jota pe", "Jota Pe")
                    .replace("dj", "DJ")
                    .replace("M.officer", "M.Officer")
                    .replace("Ortope", "Ortop&eacute;")
                    //menu nivel
                    .replace("Estacao", "Esta&ccedil;&atilde;o")
                    //nivel grupo
                    .replace("Calcados", "Cal&ccedil;ado")
                    .replace("Calcado", "Cal&ccedil;ado")
                    .replace("Confeccao", "Confec&ccedil;&atilde;o")
                    .replace("Confeccoes", "Confec&ccedil;&atilde;o")
                    .replace("Acessorios", "Acess&oacute;rio")
                    //nivel tipo
                    .replace("Tenis", "T&ecirc;nis")
                    .replace("Sandalia", "Sand&aacute;lia")
                    .replace("Sapatenis", "Sapat&ecirc;nis")
                    .replace("Bone", "Bon&eacute;")
                    .replace("Oculos", "&Oacute;culos")
                    .replace("Calca", "Cal&ccedil;a")
                    .replace("Calcao", "Cal&ccedil&atilde;o")
                    .replace("Blusao", "Blus&atilde;o")
                    .replace("Macacao", "Macac&atilde;o")
                    .replace("Maio", "Mai&ocirc;")
                    .replace("Biquini", "Biqu&iacute;ni")
                    .replace("Meiao", "Mei&atilde;o")
                    .replace("Cabeca", "Cabe&ccedil;a")
                    .replace("Corsario", "Cors&aacute;rio")
                    .replace("Baby-look", "Baby-Look")
                    .replace("P/sapatos", "p/ Sapatos")
                    //nivel estilo
                    .replace("Salao", "Sal&atilde;o")
                    .replace("Natacao", "Nata&ccedil;&atilde;o")
                    .replace("Salao", "Sal&atilde;o")
                    .replace("Termica", "T&eacute;rmica")
                    .replace("Reposicao", "Reposi&ccedil;&atilde;o")
                    .replace("Basica", "B&aacute;sica")
                    .replace("Danca", "Dan&ccedil;a")
                    .replace("Retro", "Retr&ocirc;")
                    .replace("Invisivel", "Invis&iacute;vel")
                    .replace("Classico", "Cl&aacute;ssico")
                    .replace("Machao", "Mach&atilde;o")
                    .replace("Botao", "Bot&atilde;o")
                    .replace("Capitao", "Capit&atilde;o")
                    .replace("Gola v", "Gola V")
                    //nivel classe
                    .replace("Bebe", "Beb&ecirc;")
                    //detalhe/sola
                    .replace("Sola Pre", "Sola Pr&eacute;")
                    .replace("Pvc", "PVC")
                    .replace("Botoes", "Bot&otilde;es")
                    .replace("Cortica", "Corti&ccedil;a")
                    .replace("Pre-salto", "Pr&eacute;-Salto")
                    .replace("Acrilico", "Acr&iacute;lico")
                    .replace("Acrilica", "Acr&iacute;lica")
                    .replace("Gremio", "Gr&ecirc;mio")
                    .replace("Meia-pata", "Meia-Pata")
                    .replace("Sola pu", "Sola PU")
                    //cor
                    .replace("Avela", "Avel&atilde;")
                    .replace("Cafe", "Caf&eacute;")
                    .replace("Fucsia", "F&uacute;csia")
                    .replace("Limao", "Lim&atilde;o")
                    .replace("Marron", "Marrom")
                    .replace("Nautico", "N&aacute;utico")
                    .replace("Petroleo", "Petr&oacute;leo")
                    .replace("Salmao", "Salm&atilde;o")
                    .replace("Amazonia", "Amaz&ocirc;nia")
                    .replace("Pessego", "P&ecirc;ssego")
                    .replace("C&iacute;trico", "Citrico")
                    //nivel estação
                    .replace("inverno", "Inverno")
                    .replace("Verao", "Ver&atilde;o")
                    //genérigo
                    .replace("Medio", "M&eacute;dio")
                    .replace("Eva", "E.V.A")
                    .replace("E.v.a", "E.V.A")
                    .replace("ultimo", "&Uacute;ltimo")
                    .replace("/ ", "/");
        } else if (palavra.length() == 3) {
            result = palavra
                    //genérigo
                    .replace("Pre", "Pr&eacute;");
        } else if (palavra.length() == 2) {
            result = palavra
                    //nivel estilo
                    .replace("La", "L&atilde;");
        }
        return result;
    }

    /**
     * retorna string "" se arg0 for null ou um substring de maxlegth
     *
     * @param arg0 do tipo String
     * @param maxLength do tipo int - tamanho maximo de retorno da Str. Abaixo de 1 para sem limite
     * @return String
     */
    public static String validateStr(String arg0, int maxLength) {
        if (arg0 != null && !"null".equals(arg0)) {
            if (maxLength > 0) {
                return arg0.substring(0, Math.min(maxLength, arg0.length()));
            }
            return arg0;
        }
        return "";
    }

    /**
     * signature - chama validateStr sem limite de tamanho
     *
     * @param arg0 String a ser validada
     * @return String
     */
    public static String validateStr(String arg0) {
        return validateStr(arg0, 0);
    }

    /**
     * Valida se contem algum caracter diferente de 0-9 e A-Z e a-z e mais os caracteres no array plusValids (ou seja, caracteres acentuados, caracteres especiais)
     *
     * @param value String a ser conferida
     * @param plusValids caracteres adicionais válidos
     * @return String
     */
    public static boolean isValidChars(String value, char[] plusValids) {
        for (int i = 0; i < value.length(); i++) {
            if (!isValidChar(value.charAt(i), plusValids)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isValidChars(String value) {
        return isValidChars(value, new char[]{});
    }

    /**
     * Converte formato UTF-8 para ISO8859-1
     *
     * @param utfString String em UFT-8
     * @return String em ISO-8859-1
     */
    public String toISO88591(String utfString) {
        String ISO88591String;
        if (null != utfString && !utfString.isEmpty()) {
            byte[] stringBytesUTF = utfString.getBytes(StandardCharsets.UTF_8);
            ISO88591String = new String(stringBytesUTF, StandardCharsets.ISO_8859_1);
        } else {
            ISO88591String = utfString;
        }
        return ISO88591String;
    }

    /**
     * Remove acentos e caracteres especiais
     *
     * @param str String a ser removido os caracteres especiais (acentos)
     * @return String
     */
    public static String filterChars(String str) {
        if (str == null || "".equals(str) || "null".equals(str)) {
            return "";
        }
        String patternComAcento = "àáãäâÀÁÃÄÂèéëêÈÉËÊìíïîÌÍÏÎòóõöôÒÓÕÖÔùúüûÙÚÜÛçÇñÑ";
        String patternSemAcento = "aaaaaAAAAAeeeeEEEEiiiiIIIIoooooOOOOOuuuuUUUUcCnN";
        StringBuilder sb = new StringBuilder(str
                .replace("&atilde;", "a").replace("&Atilde;", "A").replace("&ATILDE;", "A").replace("&etilde;", "e").replace("&Etilde;", "E").replace("&ETILDE;", "E").replace("&otilde;", "o").replace("&Otilde;", "O").replace("&OTILDE;", "O")
                .replace("&aacute;", "a").replace("&Aacute;", "A").replace("&AACUTE;", "A").replace("&eacute;", "e").replace("&Eacute;", "E").replace("&EACUTE;", "E").replace("&oacute;", "o").replace("&Oacute;", "O").replace("&OACUTE;", "O")
                .replace("&ccedil;", "c").replace("&Ccedil;", "C").replace("&CCEDIL;", "C"));
        char c;
        int ix;
        for (int i = 0; i < sb.length(); i++) {
            c = sb.charAt(i);
            if (c > 127) {
                ix = patternComAcento.indexOf(c);
                if (ix > -1) {
                    sb.setCharAt(i, patternSemAcento.charAt(ix));
                } else {
                    sb.setCharAt(i, ' ');
                }
            }
        }
        return sb.toString();
    }

    public static boolean isValidChar(char chara, char[] plusValids) {
        //0-9
        if (chara >= 48 && chara <= 57) {
            return true;
        }
        //A-Z
        if (chara >= 65 && chara <= 90) {
            return true;
        }
        //a-z
        if (chara >= 97 && chara <= 122) {
            return true;
        }
        if (plusValids != null) {
            for (char plusValid : plusValids) {
                if (plusValid == chara) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Remove por espaços todos caracteres diferentes de 0-9, a-z, A-Z
     *
     * @param str String a ser validada
     * @param plusValids array de char que podem ser considerados como válidos
     * @param charreplace char que substitui quando um char invalido for achado na string
     * @return String
     */
    public static String trimToValidChars(final String str, char[] plusValids, char charreplace) {
        if (str == null || str.isEmpty() || "null".equalsIgnoreCase(str)) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        char c;
        for (int i = 0; i < str.length(); i++) {
            c = str.charAt(i);
            if (isValidChar(c, plusValids)) {
                sb.append(c);
            } else {
                if ( charreplace > 0) {
                    sb.append(charreplace);
                }
            }
        }
        return sb.toString();
    }

    /**
     * Remove por espaços todos caracteres diferentes de 0-9, a-z, A-Z
     * @param str String a ser validada
     * @return String
     */
    public static String trimToValidChars(final String str) {
        return trimToValidChars(str, null, ' ');
    }

    /**
     * Abrevia uma string com no máximo max caracteres, utilizando reticências
     *
     * @param arg String a ser cortada
     * @param max maximo de caracteres
     * @return String
     */
    public static String abreviaString(String arg, int max) {
        if (arg == null) {
            return "";
        }
        final String str = arg.trim();
        if (str.length() <= max) {
            return str;
        }
        for (int i = max - 4; i > 0; i--) {
            if (str.charAt(i) == ' ') {
                return str.substring(0, i).trim() + "...";
            }
        }
        return str.substring(0, max - 3) + "...";
    }

    /**
     * Abrevia uma string com no máximo max caracteres, utilizando reticências
     *
     * @param str String a ser cortada
     * @param max maximo de caracteres
     * @param pontos TRUE para adicionar '...' ao final da String
     * @return String
     */
    public static String abreviaString(String str, int max, boolean pontos) {
        if (str == null) {
            return "";
        }
        str = str.trim();
        if (str.length() <= max) {
            return str;
        }
        for (int i = (pontos ? max - 4 : max - 1); i > 0; i--) {
            if (str.charAt(i) == ' ') {
                return str.substring(0, i).trim() + (pontos ? "..." : "");
            }
        }
        return str.substring(0, max - 3) + (pontos ? "..." : "");
    }

    /**
     * Abrevia uma string com no máximo max caracteres, utilizando reticências e mantendo o último nome
     *
     * @param str String a ser cortada
     * @param max maximo de caracteres
     * @return String
     */
    public static String abreviaNome(String str, int max) {
        if (str == null) {
            return "";
        }
        str = str.trim();
        if (str.length() <= max) {
            return str;
        }
        if (str.indexOf(' ') == -1) // não tem como abreviar por não ter espaços
        {
            return abreviaString(str, max);
        }

        // pega o último nome
        int posUlt = str.lastIndexOf(' ') + 1;
        String ult = str.substring(posUlt);
        int max2 = Math.min(posUlt, max - ult.length());

        // verifica se deve cortar a string deve ter no minimo 3 casas no ponto max2
        if (max2 > 3) {
            // verifica ponto de corte
            for (int i = max2 - 4; i > 0; i--) {
                if (str.charAt(i) == ' ') {
                    return str.substring(0, i).trim() + "..." + ult;
                }
            }
            return str.substring(0, max2 - 3) + "..." + ult;
        } else {
            return abreviaString(str, max);
        }
    }

    /**
     * converte string para JSON/JavaScript, evitando interpretação de caracteres especiais (&, \n \r \t)
     *
     * @param str String a ser convertida
     * @return String formatada XML
     */
    public static String escapeJavaScript(String str) {
        if (isEmptyStr(str)) {
            return "";
        }
        return StringEscapeUtils.escapeEcmaScript(str).replace("\\/", "/");
    }

    /**
     * converte JSON/JavaScript para String evitando interpretação de caracteres especiais (&, \n \r \t)
     *
     * @param javaScript String a ser convertida
     * @return String formatada XML
     */
    public static String javaScriptToString(String javaScript) {
        if (isEmptyStr(javaScript)) {
            return "";
        }
        return StringEscapeUtils.unescapeEcmaScript(javaScript);
    }

    /**
     * converte string para xml/html, evitando interpretação de caracteres especiais (&, < e >) pelo browser
     *
     * @param str String a ser convertida ao padrão XML
     * @return String formatada XML
     */
    public static String escapeXml(String str) {
        if (isEmptyStr(str)) {
            return "";
        }
        return StringEscapeUtils.escapeXml10(str);
    }

    /**
     * Converte string para html, deixando tags de bold, italic e underscore, bem como traduzindo final de linha e espaços iniciais;
     *
     * @param str String a ser traduzido
     * @param tagsFonte se true, faz com que tags de bold, italic e underscore sejam permitidas (não traduzidas para xml)
     * @param tagQuebraLinha se true, traduz quebras de linha para tags BR
     * @param tagEspacoInicial se true, traduz espaços iniciais para tags NBSP, mantendo espaços em html
     * @return String formatada
     */
    public static String stringToHTML(String str, boolean tagsFonte, boolean tagQuebraLinha, boolean tagEspacoInicial) {
        if (str == null || str.length() == 0) {
            return "";
        }
        str = escapeXml(str).replace("ç", "&ccedil;").replace("Ç", "&Ccedil;").replace("ã", "&atilde;").replace("Ã", "&Atilde;").replace("?", "&etilde;").replace("?", "&Etilde;").replace("õ", "&otilde;").replace("Õ", "&Otilde;").replace("á", "&aacute;").replace("Á", "&Aacute;").replace("é", "&eacute;").replace("É", "&Eacute;").replace("í", "&iacute;").replace("Í", "&Iacute;").replace("ó", "&oacute;").replace("Ó", "&Oacute;").replace("ú", "&uacute;").replace("Ú", "&Uacute;").replace("ê", "&ecirc;").replace("Ê", "&Ecirc;").replace("º", "&ordm;").replace("°", "&deg;");
        String[] s = str.split("\\n");
        for (int i = 0; i < s.length; i++) {
            if (tagsFonte) {
                s[i] = s[i].replaceAll("(?i)&lt;([/]?[iub])&gt;", "<$1>");
            }
            if (tagEspacoInicial && s[i].matches("[ ]+[^ ]+.*")) {
                StringBuilder dest = new StringBuilder();
                int p;
                for (p = 0; p < s[i].length() && s[i].charAt(p) == ' '; p++) {
                    dest.append("&nbsp;");
                }
                s[i] = dest + s[i].substring(p);
            }
        }
        StringBuilder ret = new StringBuilder();
        for (int i = 0; i < s.length; i++) {
            if (i > 0) {
                ret.append(tagQuebraLinha ? "<br>" : "\n");
            }
            ret.append(s[i]);
        }
        return ret.toString();
    }

    /**
     * Converte string para html, deixando tags de bold, italic e underscore, bem como traduzindo final de linha e espaços iniciais;
     *
     * @param str String a ser traduzido
     * @return String formatada
     */
    public static String stringToHTML(String str) {
        return stringToHTML(str, false, false, false);
    }

    /**
     * Faz com que tags de bold, italic sejam permitidas (não traduzidas para xml)
     *
     * @param str String a ser convertida para padrão HTML
     * @return String
     */
    public static String stringToHTMLComTag(String str) {
        if (str == null || str.length() == 0) {
            return "";
        }
        str = StringUtil.validateStr(str).replace("ç", "&ccedil;").replace("Ç", "&Ccedil;").replace("ã", "&atilde;").replace("Ã", "&Atilde;").replace("?", "&etilde;").replace("?", "&Etilde;").replace("õ", "&otilde;").replace("Õ", "&Otilde;").replace("á", "&aacute;").replace("Á", "&Aacute;").replace("é", "&eacute;").replace("É", "&Eacute;").replace("í", "&iacute;").replace("Í", "&Iacute;").replace("ó", "&oacute;").replace("Ó", "&Oacute;").replace("ú", "&uacute;").replace("Ú", "&Uacute;").replace("ê", "&ecirc;").replace("Ê", "&Ecirc;").replace("\r", "").replace("'", "");
        String[] s = str.split("\\n");
        StringBuilder ret = new StringBuilder();
        for (int i = 0; i < s.length; i++) {
            if (i > 0) {
                ret.append("<br/>");
            }
            ret.append(s[i]);
        }
        return ret.toString();
    }

    /**
     * Adiciona tags HTML '<a href="..."></a>' em String com //:...
     * @param str String a converter link para <a href="..."></a>
     * @return String
     */
    public static String addHyperlinkToString(String str) {
        StringBuilder result = new StringBuilder();
        if (! StringUtil.isEmptyStr(str)) {
            String[] s = str.trim().split("\\s+");
            for (String value : s) {
                if (value.contains("://") && !value.toLowerCase().contains("href")) {
                    if (value.toLowerCase().contains("<br")) {
                        String[] br = value.toLowerCase().split("<br>|<br/>|<br />");
                        for (String item : br) {
                            if (item.contains("://")) {
                                String temp = item.replace("\n", "");
                                result.append("<a href=\"").append(temp).append("\">").append(temp).append("</a>");
                            } else {
                                result.append("<br>");
                            }
                        }
                    } else {
                        String temp = value.replace("\n", "");
                        result.append("<a href=\"").append(temp).append("\">").append(temp).append("</a>").append(" ");
                    }
                } else {
                    result.append(value).append(" ");
                }
            }
        }
        return result.toString();
    }

    /**
     * Converte string URI (aceito pelo action get)
     *
     * @param str String a ser codificada em padrao URI
     * @return String
     */
    public static String encodeURI(String str) {
        if (str == null) {
            return "";
        }
        try {
            return java.net.URLEncoder.encode(str, "ISO-8859-1");
        } catch (UnsupportedEncodingException ignored) {
        }
        return str;
    }

    /**
     * Converte string URI (aceito pelo action get UTF-8)
     *
     * @param str String a ser codificada em padrao URI UTF-8
     * @return String
     */
    public static String encodeURI_UTF(String str) {
        if (str == null) {
            return "";
        }
        try {
            return java.net.URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException ignored) {
        }
        return str;
    }

    /**
     * Converte URI para string
     * @param str String URI para String java
     * @return String
     */
    public static String decodeURI(String str) throws UnsupportedEncodingException {
        if (str == null) {
            return "";
        }
        return java.net.URLDecoder.decode(str, "ISO-8859-1");
    }

    /**
     * Converte URI para string
     * @param str String URI para String java UTF-8
     * @return String
     */
    public static String decodeURI_UTF(String str) throws UnsupportedEncodingException {
        if (str == null) {
            return "";
        }
        return java.net.URLDecoder.decode(str, "UTF-8");
    }

    /**
     * concatena array de objetos utilizando sep como separador
     *
     * @param a array de strings
     * @param sep separador
     * @return String formatada concatenada 1[0] + sep + a[1] ... Ex.: Ana, Joana, Joao
     */
    public static String concat(Object[] a, String sep) {
        StringBuilder sb = new StringBuilder();
        for (Object o : a) {
            if (o != null) {
                String so = String.valueOf(o);
                if (so.length() > 0) {
                    if (sb.length() > 0) {
                        sb.append(sep);
                    }
                    sb.append(so);
                }
            }
        }
        return sb.toString();
    }

    /**
     * converte em string javascript utilizando aspas duplas e escapeando aspas duplas internas
     *
     * @param s string a ser convertida para formato JavaScript
     * @return String
     */
    public static String toStringJS(String s) {
        StringBuilder sb = new StringBuilder((s != null) ? s : "");
        for (int i = 0; i < sb.length(); i++) {
            if (sb.charAt(i) == '"') {
                sb.insert(i++, '\\');
            }
        }
        return "\"" + sb + "\"";
    }

    /**
     * converte string em uma string de procura de nomes, case insensitive Uso: obj.getNome().matches(toRegexNome("NOME1 NOME2"))
     *
     * @param orig String a ser preparada para pesquisa regex
     * @return String regex
     */
    public static String toRegexNome(String orig) {
        if (orig == null || orig.isEmpty() || "null".equalsIgnoreCase(orig)) {
            return "";
        }
        return "(?i).*" + orig.trim().toUpperCase().replaceAll("[^ A-Z0-9]", ".").replaceAll("[ ]", ".*\\\\s+.*") + ".*";
    }

    /**
     * Gera uma pseudo string codificada para sinonimos foneticos da linguagem pt-BR Baseado no algoritmo BuscaBR
     *
     * @param arg String para bscar em formato fonetico
     * @return String codificada
     */
    public static String stringToFonetic(String arg) {
        if (arg == null || arg.isEmpty() || "null".equalsIgnoreCase(arg)) {
            return "";
        }
        arg = StringUtil.trimToValidChars(StringUtil.filterChars(arg.trim().toUpperCase().replace("Ç", "S")));
        // ajuste especial
        arg = arg.replace("PRATA", "PRAT@"); // para evitar conflito entre preto e prata AMBOS P1T
        arg = arg.replace("TENIS", "TEN@"); // para evitar conflito entre TENIS e TIME AMBOS T1M
        arg = arg.replace("TENIZ", "TEN@"); // para evitar conflito entre TENIS e TIME AMBOS T1M
        arg = arg.replace("TENIX", "TEN@"); // para evitar conflito entre TENIS e TIME AMBOS T1M
        // serie de replaces para portuga "amigavel" ...
        arg = arg.replace("BR", "B").replace("BL", "B")
                .replace("CA", "KA")
                .replace("CE", "SE")
                .replace("CI", "SI")
                .replace("CO", "KO")
                .replace("CU", "KU")
                .replace("CK", "K").replace("CL", "K").replace("CR", "K").replace("SC", "K")
                .replace("CH", "S")
                .replace("GE", "JE").replace("GI", "JI")
                .replace("GM", "M")
                .replace("GL", "G").replace("GR", "G")
                .replace("ND", "D")
                .replace("N", "M").replace("MD", "M")
                .replace("MG", "G")
                .replace("MJ", "J")
                .replace("PH", "F")
                .replace("PR", "P").replace("PL", "P")
                .replace("Q", "K").replace("QUE", "KE").replace("QUI", "KI")
                .replace("RG", "G")
                .replace("RS", "R")
                .replace("RT", "T")
                .replace("RM", "M")
                .replace("RJ", "J")
                .replace("SM", "M")
                .replace("ST", "T").replace("TR", "T").replace("TL", "T")
                .replace("TS", "S")
                .replace("W", "V")
                .replace("X", "S")
                .replace("Y", "I")
                .replace("Z", "S")
                // ultimos preparativos - letras duplas e tratamento fonético das vogais
                .replace("AA", "A").replace("BB", "B").replace("CC", "C").replace("DD", "D").replace("EE", "E").replace("FF", "F").replace("GG", "G").replace("LL", "L").replace("MM", "M").replace("PP", "P").replace("RR", "R").replace("SS", "S").replace("TT", "T") // elimina letras repetida
                .replace("A", "1").replace("E", "1").replace("I", "1")
                .replace("O", "2").replace("U", "2")
                .replace("11", "1").replace("22", "2")
                .replace("H", "");
        // elimina letra de palavras que terminam com S, Z, R, R, M, N para evitar infinitivos e plurais
        if (arg.endsWith("N") || arg.endsWith("M") || arg.endsWith("R") || arg.endsWith("S")) {
            arg = arg.substring(0, arg.length() -1);
        }
        // substitui vogal no fim para 0 para evitar diferencas entre genero ex BRANCO e BRANCA = B1NC0
        if (arg.endsWith("1") || arg.endsWith("2")) {
            arg = arg.substring(0, arg.length() -1) + "0";
        }

        // pronto portuga decompricado pronto :)
        return arg;
    }

    /**
     * Converte texto com tags HTML para texto plano
     * @param strHTML String HTML para String java
     * @return String
     */
    public static String htmlToString(String strHTML) {
        return htmlToString(strHTML, false);
    }

    /**
     * Converte texto com tags HTML para texto plano
     * @param strHTML String HTML para String java
     * @param quebraLinha se TRUE converte <BR> em \r\n
     * @return String
     */
    public static String htmlToString(String strHTML, boolean quebraLinha) {
        if (strHTML == null || strHTML.isEmpty() || "null".equalsIgnoreCase(strHTML)) {
            return "";
        }
        return (quebraLinha ? strHTML.replace("<br>", "\r\n") : strHTML)
                .replace("&aacute;", "á").replace("&Aacute;", "Á")
                .replace("&eacute;", "é").replace("&Eacute;", "É")
                .replace("&iacute;", "í").replace("&Iacute;", "Í")
                .replace("&oacute;", "ó").replace("&Oacute;", "Ó")
                .replace("&uacute;", "ú").replace("&Uacute;", "Ú")
                .replace("&atilde;", "ã").replace("&Atilde;", "Ã")
                .replace("&etilde;", "?").replace("&Etilde;", "?")
                .replace("&otilde;", "õ").replace("&Otilde;", "Õ")
                .replace("&ccedil;", "ç").replace("&Ccedil;", "Ç")
                .replace("&ecirc;", "ê").replace("&Ecirc;", "Ê")
                .replace("&ocirc;", "ô").replace("&Ocirc;", "Ô")
                .replaceAll("<(.|\n)*?>", "")
                .replace("\t", "").replace("&nbsp;", " ");
    }

    public static void main(String[] args) {
        String s = htmlToString("Tênis Adidas confeccionado em <span style='font-style: italic;'>mesh</span>, material específico para aumentar a ventilação e, com isso, permitir que a umidade da pele evapore com mais rapidez.<br>Palmilha: em EVA, para maior conforto. <br>Solado: em borracha, que oferece alta durabilidade em terrenos planos.");

        System.out.println(s);


        String aux  = "cumaá? <!> iué ççç blahh";
        s = escapeXml(aux);
        System.out.println(s);
    }

    public static String textToNFe(String conteudoChave){
        return filterChars(removeISOControlChars(validateStr(conteudoChave))).replace("&", "E").replace("<", " ").replace(">", " ").replace("\"", " ").replace("'", " ").replace(":", " ").replace("=", " ").replace("\r", " ").replace("\n", " ").replace("\t", " ").replace("  ", " ").trim();
    }

    public static String removeISOControlChars(String texto) {
        char[] chars = texto.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (Character.isISOControl(c) && c != 0x09 && c != 0x0A
                    && c != 0x0D) {
                chars[i] = ' ';
            }
        }
        return new String(chars);
    }

    public static void replaceAll(StringBuilder builder, String from, String to) {
        if (isEmptyStr(from)) {
            return;
        }
        int index = builder.indexOf(from);
        while (index != -1) {
            builder.replace(index, index + from.length(), to);
            index += to.length();
            index = builder.indexOf(from, index);
            if (builder.length() == 0) break;
        }
    }
}
