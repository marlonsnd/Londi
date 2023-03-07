/*
 * NumberUtil.java
 * Copyright (c) MACLE Sistemas LTDA.
 * Criado em 07 de fevereiro de 2023 - 12:52:30
 * Projeto ProjetosLondi
 * @author Márlon Schoenardie
 */
package com.londi.util;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

/**
 * Classe utilitaria para formatação de números
 */
public class NumberUtil {

    /** numero de casas decimais */
    public static final int CURRENCY_DECIMALS = 2;

    /**
     * trunca o valor apos as caas decimais
     * @param valor - numero a ser truncado
     * @param decimal - numero de casas decimais
     */
    public static double trunc(double valor, int decimal) {
        if (decimal > 0) {
            double result = ((valor + 0.00001) * Math.pow(10, decimal));
            return Math.floor(result) / Math.pow(10, decimal);
        } else {
            return Math.floor(valor);
        }
    }

    public static int trunc(double valor) {
        return (int) Math.floor(valor + 0.001);
    }

    /**
     * arredonda o valor apos as caas decimais
     * @param valor - numero a ser arredondado
     * @param decimal - numero de casas decimais
     */
    public static double round(double valor, int decimal) {
        if (decimal > 0) {
            double result = (valor * Math.pow(10, decimal));
            return Math.round(result) / Math.pow(10, decimal);
        } else {
            return Math.round(valor);
        }
    }

    public static long round(double valor) {
        return Math.round(valor);
    }

    /**
     * Valida se uma string contem dados numericos
     * @param number - string com o numero pattern "0..9-,."
     * @return boolean
     */
    public static boolean isNumeric(String number) {
        String pattern = "0123456789-,.";
        boolean result = true;
        if (StringUtil.isEmptyStr(number)) {
            return false;
        }
        // testa e ve se pattern esta correto
        for (int i = 0; i < number.length(); i++) {
            if (pattern.indexOf(number.charAt(i)) == -1) {
                result = false;
                break;
            }
        }
        int iSig = number.indexOf("-");
        if (iSig != -1 && iSig != 0) {
            result = false;
        }
        return result;
    }

    /**
     * Converte uma sequencia de numeros para extenso
     * @param numero
     * @return String
     */
    public static String getNumeroExtenso(double numero) {
        return new Extenso(new BigDecimal(numero)).toString().toUpperCase();
    }

    /**
     * Converte uma sequência de números para extenso
     * @param numero
     * @return String
     */
    public static String getApenasNumeroPorExtenso(double numero) {
        return new Extenso(new BigDecimal(numero)).converteString().toUpperCase();
    }

    /**
     * Valida se uma string um integer válido
     * @param number - string com o numero pattern "0..9-,."
     * @return boolean
     */
    public static boolean isInteger(String number) {
        String pattern = "0123456789";
        if (number == null || "".equals(number) || number.length() > 10) {
            return false;
        }
        // testa e ve se pattern esta correto
        for (int i = 0; i < number.length(); i++) {
            if (pattern.indexOf(number.charAt(i)) == -1) {
                return false;
            }
        }
        return true;
    }

    /**
     * Retorna se o numero informado é par (divisivel por 2)
     * @param numero
     * @return true se é par
     */
    public static boolean isNumeroPar(int numero) {
        return (numero % 2) == 0;
    }

    /**
     * Compara dois numeros com uma determinada tolerancia e retorna verdadeiro
     * se ambos valores estiverem entre a tolerancia/faixa
     * Ex.: 12 e 12.5 com uma tolerancia de 0.5 ou maior retorna verdadeiro
     * @param vlr1 valor a ser comparado
     * @param vlr2 valor a ser comparado
     * @param tolerancia dos valores tanto para + como para -
     * @return boolean
     */
    public static boolean equalsWithToler(double vlr1, double vlr2, double tolerancia) {
        return (vlr1 + tolerancia) >= vlr2 && (vlr1 - tolerancia) <= vlr2;
    }

    /**
     * compara valores double/float
     * @param vlr1
     * @param vlr2
     * @param numDec número de casas decimais
     * @return Negativo se vlr1 for MENOR vlr2 -  ZERO se valores forem IGUAIS -  POSITIVO se vlr1 for MAIOR vlr2
     */
    public static int compareFloat(double vlr1, double vlr2, int numDec) {
        long l1 = Math.round(vlr1 * Math.pow(10, numDec));
        long l2 = Math.round(vlr2 * Math.pow(10, numDec));
        return (l1 < l2 ? -1 : (l1 == l2 ? 0 : 1));
    }

    public static String getDuracaoExtenso(long segundos) {
        if(segundos < 0) {
            return "";
        }
        long dias = TimeUnit.SECONDS.toDays(segundos);
        segundos -= TimeUnit.DAYS.toMillis(dias);
        long horas = TimeUnit.SECONDS.toHours(segundos);
        segundos -= TimeUnit.HOURS.toMillis(horas);
        long minutos = TimeUnit.SECONDS.toMinutes(segundos);
        StringBuilder sb = new StringBuilder();
        if (dias > 0) {
            sb.append(dias);
            sb.append(" Dia");
            if(dias > 1) {
                sb.append("s ");
            } else {
                sb.append(" ");
            }
        }
        if (horas > 0)  {
            sb.append(horas);
            sb.append(" Hora");
            if (horas > 1) {
                sb.append("s ");
            } else {
                sb.append(" ");
            }
        }
        if (minutos > 0) {
            sb.append(minutos);
            sb.append(" Minuto");
            if (minutos > 1) {
                sb.append("s ");
            } else {
                sb.append(" ");
            }
        }
        return(sb.toString());
    }
}