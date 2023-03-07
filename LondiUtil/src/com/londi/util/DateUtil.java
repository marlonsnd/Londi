/*
 * DateUtil.java
 * Copyright (c) MACLE Sistemas LTDA.
 * Criado em 07 de fevereiro de 2023 - 12:51:28
 * Projeto ProjetosLondi
 * @author Márlon Schoenardie
 */
package com.londi.util;

import org.apache.commons.lang3.time.FastDateFormat;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.text.ParseException;
import java.time.ZoneId;
import java.util.*;

/**
 * Classe utilitaria para manipulação de datas
 */
public abstract class DateUtil {

    private static final java.util.Date DEFAULT_DATE = new GregorianCalendar(1900, 0, 1).getTime();
    private static final java.util.Date DEFAULT_DATE_DELPHI = new GregorianCalendar(1899, 11, 30).getTime();
    private static final FastDateFormat FMT_DATA = FastDateFormat.getInstance("dd/MM/yyyy");
    private static final FastDateFormat FMT_HOUR = FastDateFormat.getInstance("HH:mm:ss");
    private static final ZoneId ZONE = ZoneId.systemDefault();

    /**
     * DAY_OF_WEEK - constante que representa o conjunto dos dias da semana
     */
    private static final int DAY_OF_WEEK = Calendar.DAY_OF_WEEK;
    /**
     * DATE - constante do dia do mes 1 para primeiro dia
     */
    private static final int DATE = Calendar.DATE;
    /**
     * MONTH - constante do mes 1 para janeiro
     */
    private static final int MONTH = Calendar.MONTH;
    /**
     * YEAR - constante do ano
     */
    private static final int YEAR = Calendar.YEAR;
    /**
     * WEEK_OF_YEAR - constante da semana do ano
     */
    private static final int WEEK_OF_YEAR = Calendar.WEEK_OF_YEAR;
    /**
     * HOUR_OF_DAY - constante da hora
     */
    private static final int HOUR_OF_DAY = Calendar.HOUR_OF_DAY;
    /**
     * MINUTOS - minutos da hora
     */
    private static final int MINUTO = Calendar.MINUTE;    // constantes por extenso
    /**
     * SEGUNDO - segundos da hora
     */
    private static final int SEGUNDO = Calendar.SECOND;

    private static final String[] dayOfWeek = {"domingo", "segunda-feira", "terca-feira", "quarta-feira", "quinta-feira", "sexta-feira", "sabado"};
    private static final String[] months = {"janeiro", "fevereiro", "março", "abril", "maio", "junho", "julho", "agosto", "setembro", "outubro", "novembro", "dezembro"};
    /**
     * DOMINGO - constante do dia da semana domingo
     */
    public static final int DOMINGO = 1;
    /**
     * SEGUNDA - constante do dia da semana segunda
     */
    public static final int SEGUNDA = 2;
    /**
     * TERCA - constante do dia da semana terca
     */
    public static final int TERCA = 3;
    /**
     * QUARTA - constante do dia da semana quarta
     */
    public static final int QUARTA = 4;
    /**
     * QUINTA - constante do dia da semana quinta
     */
    public static final int QUINTA = 5;
    /**
     * SEXTA - constante do dia da semana sexta
     */
    public static final int SEXTA = 6;
    /**
     * SABADO - constante do dia da semana sabado
     */
    public static final int SABADO = 7;
    public final static long SECOND_MILLIS = 1000;
    public final static long MINUTE_MILLIS = SECOND_MILLIS * 60;
    public final static long HOUR_MILLIS = MINUTE_MILLIS * 60;

    public static Date incSemana(Date data, int semanas) {
        return incDay(data, semanas * 7);
    }

    /**
     * Retorna lista de semanas do periodo
     * @param dataINI
     * @param dataFIM
     * @return double
     */
    public static List<Integer> getListSemanas(Date dataINI, Date dataFIM) {
        List<Integer> result = new ArrayList<>();
        final int semanaIni = DateUtil.getWeekOfYear(dataINI);
        result.add(semanaIni);
        final int semanaFim = DateUtil.getWeekOfYear(dataFIM);
        Date aux = dataINI;
        int semanaAux = -1;
        do {
            aux = DateUtil.incDay(aux, 1);
            semanaAux = DateUtil.getWeekOfYear(aux);
            if (! result.contains(semanaAux)) {
                result.add(semanaAux);
            }
        } while (semanaAux != semanaFim || DateUtil.getNumDayByInterval(aux, dataFIM) > 0);
        return result;
    }

    /**
     * Retorna o primeiro dia da semana informada
     *
     * @param semana
     * @param ano
     * @return Date
     */
    public static Date fisrtDayOfWeek(int semana, int ano) {
        Date result = getInstance(1, 1, ano);
        if (semana == 1) {
            Date aux = result;
            while (getWeekOfYear(aux) != semana) {
                aux = incDay(aux, 1);
                result = aux;
            }
            return result;
        } else {
            result = incSemana(result, semana - 1);
            if (getWeekOfYear(result) < semana) {
                result = incSemana(result, 1);
            }
            Date aux = result;
            // decrementa aux até o primeiro dia da semana
            while (getWeekOfYear(aux) == semana) {
                result = aux;
                aux = incDay(aux, -1);
            }
            return result;
        }
    }

    /**
     * Retorna o ultimo dia da semana informada
     *
     * @param semana
     * @param ano
     * @return Date
     */
    public static Date lastDayOfWeek(int semana, int ano) {
        Date result = getInstance(1, 1, ano);
        if (semana == 1) {
            result = fisrtDayOfWeek(semana, ano);
        } else {
            result = incSemana(result, semana - 1);
        }
        Date aux = result;
        int currSemana = getWeekOfYear(aux);
        // incrementa aux até o primeiro dia da semana
        while ((currSemana < semana && extractYear(aux) == ano) || currSemana == semana) {
            result = aux;
            aux = incDay(aux, 1);
            currSemana = getWeekOfYear(aux);
        }
        return result;
    }

    public static String dayOfWeek(Date data) {
        return dayOfWeek[getDayOfWeek(data) - 1];
    }

    /**
     * Retorna data atual com a HORA zerada 00:00:00
     *
     * @return Retorna data atual
     */
    public static java.util.Date getDate() {
        java.util.Date now = new Date();
        return getInstance(extractDay(now), extractMonth(now), extractYear(now));
    }

    /**
     * Retorna data java.util.date conf
     *
     * @param dia dia do mes de 1 a 31
     * @param mes mes de 1 a 12
     * @param ano .
     * @return Date
     */
    public static java.sql.Date getInstance(final int dia, final int mes, final int ano) {
        GregorianCalendar grec = new GregorianCalendar(ano, mes - 1, dia);
        return new java.sql.Date(grec.getTime().getTime());
    }

    /**
     * retorna data default(1900-1-1)
     *
     * @return Date
     */
    public static java.util.Date getDefaultDate() {
        return DEFAULT_DATE;
    }

    /**
     * retorna data informada ou data default se a data informada for null
     *
     * @param dt data
     * @return Date
     */
    public static java.util.Date getDefaultDate(final java.util.Date dt) {
        return (dt != null) ? dt : DEFAULT_DATE;
    }

    /**
     * Retorna o numero de dias do intervalo entre as duas datas
     *
     * @param dataIni data inicial
     * @param dataFim data final
     * @return numero de dias entre as datas, zero se alguma data for nula
     */
    public static int getNumDayByInterval(final java.util.Date dataIni, final java.util.Date dataFim) {
        return getNumDayByInterval(dataIni, dataFim, false);
    }

    /**
     * Retorna o numero de dias do intervalo entre as duas datas
     *
     * @param dataIni data inicial
     * @param dataFim data final
     * @param zeroSeDefaultDate se true, retorna zero caso alguma data seja
     * DEFALT_DATE
     * @return numero de dias entre as datas, zero se alguma data for nula ou
     * conforme flag zeroSeDefaultDate
     */
    public static int getNumDayByInterval(final java.util.Date dataIni, final java.util.Date dataFim, final boolean zeroSeDefaultDate) {
        if (dataIni == null || dataFim == null) {
            return 0;
        }
        final long d1 = truncDate(dataIni).getTime();
        final long d2 = truncDate(dataFim).getTime();
        final long d3 = truncDate(DEFAULT_DATE).getTime();
        if (zeroSeDefaultDate && (d1 == d3 || d2 == d3)) {
            return 0;
        }
        final double aux = NumberUtil.round((double) (d2 - d1) / 1000 / 60 / 60 / 24);
        long n = (long) aux;
        return (int) n;
    }

    /**
     * Retorna se duas datas sao iguais desconsiderando o horario apenas
     * verifica dia, mes e ano.
     *
     * @param data1 data1
     * @param data2 data2
     * @return boolean
     */
    public static boolean isEquals(final Date data1, final Date data2) {
        if (data1 == null || data2 == null) {
            return (data1 == null && data2 == null);
        }
        if (data1.getTime() == DEFAULT_DATE.getTime() || data2.getTime() == DEFAULT_DATE.getTime()) {
            return data1.getTime() == DEFAULT_DATE.getTime() && data2.getTime() == DEFAULT_DATE.getTime();
        }
        final long d1 = truncDate(data1).getTime();
        final long d2 = truncDate(data2).getTime();
        final double aux = ((double) (d2 - d1) / 1000 / 60 / 60 / 24) + 0.05;
        final long n = NumberUtil.trunc(aux);
        return n == 0;
    }

    /**
     * Retorna se duas datas sao iguais considera a hora verifica hora, dia, mes
     * e ano.
     *
     * @param data1 data1
     * @param data2 data2
     * @return boolean TRUE iguais com tolerancia de uma hora
     */
    public static boolean isEqualsHour(final Date data1, final Date data2) {
        if (data1 == null || data2 == null) {
            return (data1 == null && data2 == null);
        }
        final long d1 = truncDate(data1).getTime();
        final long d2 = truncDate(data2).getTime();
        final long n = (d2 - d1) / 1000 / 60 / 60;
        return n == 0;
    }

    /**
     * Retorna se é a data padrão 01/01/1900 ou *** NULL
     *
     ***
     * @param data a ser testada
     * @return boolean retorna true se for igual a data padrao
     */
    public static boolean isDefaultDate(final Date data) {
        return data == null || isEquals(getDefaultDate(), data) || isEquals(DEFAULT_DATE_DELPHI, data);
    }

    /**
     * incrementa n dias na data
     *
     * @param date .
     * @param days dias a serem incrementados
     * @return Date com n dias incrementados
     */
    public static java.util.Date incDay(final Date date, final int days) {
        if (date == null) {
            return null;
        }
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);
        return cal.getTime();
    }

    /**
     * incrementa n horas na data
     *
     * @param date .
     * @param hours horas a serem incrementados
     * @return Date com n horas incrementados
     */
    public static java.util.Date incHour(final Date date, final int hours) {
        if (date == null) {
            return null;
        }
        GregorianCalendar grec = new GregorianCalendar();
        grec.setTime(date);
        grec.add(GregorianCalendar.HOUR_OF_DAY, hours);
        return grec.getTime();
    }

    /**
     * Incrementa número de minutos na data
     * @param date
     * @param minutes
     * @return
     */
    public static java.util.Date incMinute(final Date date, final int minutes) {
        if (date == null) {
            return null;
        }
        GregorianCalendar grec = new GregorianCalendar();
        grec.setTime(date);
        grec.add(GregorianCalendar.MINUTE, minutes);
        return grec.getTime();
    }

    /**
     * Incrementa o mes
     *
     * @param date data a ser incrementada
     * @param months numero de meses a incrementar
     * @param forceDay para forcar o dia da data 0 para manter o atual
     * @return Retorna a data com mes incrementado
     */
    public static java.util.Date incMonth(final Date date, final int months, final int forceDay) {
        int dia = forceDay;
        int mes = 0;
        int ano = 0;
        if (date != null) {
            if (forceDay <= 0 || forceDay > 31) {
                dia = extractDay(date);
            }
            // pega dia, mes e ano da data atual
            mes = extractMonth(date);
            ano = extractYear(date);

            // incrementa o mes
            if (months == 0) {
                mes++;
            } else {
                mes += months;
            }

            if (mes > 12) {
                int numInc = NumberUtil.trunc((mes - 1) / 12);
                ano += numInc;
                mes += -(numInc * 12);
            } else if (mes < 1) {
                int numInc = NumberUtil.trunc((mes) / 12);
                ano += numInc - 1;
                mes = 12 - (Math.abs(mes) - (Math.abs(numInc) > 0 ? (Math.abs(numInc) * 12) : 0));
            }
            // valida o dia
            if (dia == 0) {
                dia = 1;
            }
            if (dia > 31) {
                dia = 31;            // trata fevereiro
            }
            if (mes == 2) {
                if (dia > 28) {
                    dia = 28;
                }
                // trata meses com 30 dias
            } else if (dia >= 31 && (mes == 4 || mes == 6 || mes == 9 || mes == 11)) {
                dia = 30;
            }
            return getInstance(dia, mes, ano);
        } else {
            return getDate();
        }
    }

    /**
     * Retorna se time é igual a 00:00:00, hora zero (meia noite)
     *
     * @param time
     * @return TRUE para meia noite
     */
    public static boolean isZeroHour(final Date time) {
        if (time == null) {
            return true;
        }
        return "00:00:00".equals(FMT_HOUR.format(time));
    }

    /**
     * Trunca a hora da data
     *
     * @param data .
     * @return retorna a data com a hora zerada 00:00:00
     */
    public static java.util.Date truncDate(final java.util.Date data) {
        return DateUtil.getInstance(
                extractDay(data),
                extractMonth(data),
                extractYear(data));
    }

    /**
     * Retorna a data default 01/01/1990 com a hora corrente do deteTime
     *
     * @param dateTime
     * @return Date
     */
    public static java.util.Date truncTime(final java.util.Date dateTime) {
        GregorianCalendar grec = new GregorianCalendar();
        grec.setTime(dateTime);
        int hora = grec.get(GregorianCalendar.HOUR_OF_DAY);
        int minuto = grec.get(GregorianCalendar.MINUTE);
        int segundo = grec.get(GregorianCalendar.SECOND);

        String sd = "01/01/1990 " + Parser.LFill(hora, 2, true) + ":" + Parser.LFill(minuto, 2, true) + ":" + Parser.LFill(segundo, 2, true);
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        try {
            return sdf.parse(sd);
        } catch (ParseException ex) {
            return dateTime;
        }
    }

    /**
     * Retorna a maior hora do dia (23:59:59)
     *
     * @param data .
     * @return Date
     */
    public static java.util.Date getDateMaxTime(final java.util.Date data) {
        String sd = Parser.dateToStr(data, "MM/dd/yyyy");
        sd += " 23:59:59";
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        try {
            return sdf.parse(sd);
        } catch (ParseException ex) {
            return data;
        }
    }

    /**
     * Extrai o dia da data
     *
     * @param data .
     * @return int
     */
    public static int extractDay(final Date data) {
        if (data == null) {
            return 1;
        }
        return extractPart(DATE, data);
    }

    /**
     * Extrai o mês da mês
     *
     * @param data .
     * @return int
     */
    public static int extractMonth(final Date data) {
        if (data == null) {
            return 1;
        }
        return extractPart(MONTH, data);
    }

    /**
     * Extrai o ano da data
     *
     * @param data .
     * @return int
     */
    public static int extractYear(final Date data) {
        if (data == null) {
            return 0;
        }
        return extractPart(YEAR, data);
    }

    /**
     * Extrai a hora da data
     *
     * @param data .
     * @return int
     */
    public static int extractHour(final Date data) {
        if (data == null) {
            return 0;
        }
        return extractPart(HOUR_OF_DAY, data);
    }

    /**
     * Extrai os minutos da hora da data
     *
     * @param data .
     * @return int
     */
    public static int extractMinute(final Date data) {
        if (data == null) {
            return 0;
        }
        return extractPart(MINUTO, data);
    }

    /**
     * Extrai os segundos da hora da data
     *
     * @param data .
     * @return int
     */
    public static int extractSecond(final Date data) {
        if (data == null) {
            return 0;
        }
        return extractPart(SEGUNDO, data);
    }

    /**
     * Extrai a semana do ano
     *
     * @param data .
     * @return int
     */
    public static int getWeekOfYear(final Date data) {
        if (data == null) {
            return 0;
        }
        return extractPart(WEEK_OF_YEAR, data);
    }

    /**
     * Retorna o dia da semana onde o 1 é o domingo, 2 a segunda, .. e 7 o
     * sabado
     *
     * @param data .
     * @return int com dia da semana
     */
    public static int getDayOfWeek(final Date data) {
        if (data == null) {
            return 0;
        }
        return extractPart(DAY_OF_WEEK, data);
    }

    private static int extractPart(final int parte, final Date data) {
        FastDateFormat sdf;
        if (parte == DATE) {
            sdf = FastDateFormat.getInstance("dd");
        } else if (parte == MONTH) {
            sdf = FastDateFormat.getInstance("MM");
        } else if (parte == YEAR) {
            sdf = FastDateFormat.getInstance("yyyy");
        } else if (parte == HOUR_OF_DAY) {
            sdf = FastDateFormat.getInstance("HH");
        } else if (parte == MINUTO) {
            sdf = FastDateFormat.getInstance("mm");
        } else if (parte == SEGUNDO) {
            sdf = FastDateFormat.getInstance("ss");
        } else if (parte == DAY_OF_WEEK) {
            GregorianCalendar grec = new GregorianCalendar();
            grec.setTime(data);
            return grec.get(GregorianCalendar.DAY_OF_WEEK);
        } else if (parte == WEEK_OF_YEAR) {
            GregorianCalendar grec = new GregorianCalendar();
            grec.setFirstDayOfWeek(GregorianCalendar.SUNDAY);
            grec.setMinimalDaysInFirstWeek(3);
            grec.setTime(data);
            return grec.get(GregorianCalendar.WEEK_OF_YEAR);
        } else {
            return 0;
        }
        return Parser.strToInt(sdf.format(data));
    }

    /**
     * @param data data a ser convertida
     * @return Retorna data com dia setado para o PRIMEIRO dia do mes
     */
    public static Date getDateInicPeriodo(Date data) {
        return getDateInicPeriodo(
                extractYear(data),
                extractMonth(data));
    }

    /**
     * @param ano
     * @param mes
     * @return Retorna data com dia setado para o PRIMEIRO dia do mes
     */
    public static Date getDateInicPeriodo(final int ano, final int mes) {
        return getInstance(1, mes, ano);
    }

    /**
     * @param data
     * @return Retorna data com dia setado para o ULTIMO dia do mes
     */
    public static Date getDateFimPeriodo(final Date data) {
        if (data != null) {
            return getDateFimPeriodo(
                    extractYear(data),
                    extractMonth(data));
        } else {
            return getDefaultDate();
        }
    }

    /**
     * @param ano
     * @param mes
     * @return Retorna data com dia setado para o ULTIMO dia do mes
     */
    public static Date getDateFimPeriodo(final int ano, final int mes) {
        Date d = getInstance(1, mes + 1, ano);
        d = incDay(d, -1);
        return d;
    }

    /**
     * Retorna a data informada por extenso ex 29/03/2006 = quarta-feira, 29 de
     * março de 2006
     *
     * @param data .
     * @return Retorna String com a data por extenso
     */
    public static String getDataExtenso(final Date data) {
        return dayOfWeek[getDayOfWeek(data) - 1] + ", " + extractDay(data) + " de " + months[extractMonth(data) - 1] + " de " + extractYear(data);
    }

    /**
     * Retorna o mes por extenso
     *
     * @param month
     * @parma mes int com o numero do mes de 1 a 12
     * @return String
     */
    public static String getMonthExtenso(final int month) {
        if (month > 0 && month < 13) {
            return months[month - 1];
        } else {
            return "";
        }
    }

    /**
     * formata um período para visualização reduzida quando possível ex: mesmo
     * mês/ano: 01-31/01/2006 - mesmo ano: 01/01-31/12/2006
     *
     * @param dtIni
     * @param dtFim
     * @return
     */
    public static String displayPeriodo(final Date dtIni, final Date dtFim) {
        boolean hasIni = dtIni != null && !isDefaultDate(dtIni);
        boolean hasFim = dtFim != null && !isDefaultDate(dtFim);
        if (!hasIni && !hasFim) {
            return "-";
        }
        if (!hasFim) {
            return FMT_DATA.format(dtIni) + " - ";
        }
        if (!hasIni) {
            return " - " + FMT_DATA.format(dtFim);
        }
        if (extractYear(dtIni) == extractYear(dtFim)) {
            int sub = 5;
            if (extractMonth(dtIni) == extractMonth(dtFim)) {
                if (extractDay(dtIni) == extractDay(dtFim)) {
                    return getDataRelativa(DateUtil.getDate(), dtIni);
                }
                sub = 2;
            }
            return FMT_DATA.format(dtIni).substring(0, sub) + "-" + FMT_DATA.format(dtFim);
        }
        return FMT_DATA.format(dtIni) + " - " + FMT_DATA.format(dtFim);
    }

    /**
     * Retorna o número de dias entre datas para fins de evento (ex:
     * aniversário)
     *
     * @param dataEvento data do evento
     * @param dataRef data de referência (ex: hoje)
     * @param permiteNegativo se true, permite que seja retornado valores
     * negativos para eventos que recém passaram
     * @return retorna o número de dias para que o evento aconteça, ou negativo
     * se permitido e o evento recém aconteceu
     */
    public static int getDiasEvento(final Date dataEvento, final Date dataRef, final boolean permiteNegativo) {
        if (isDefaultDate(dataEvento) || isDefaultDate(dataRef)) {
            return 0;
        }
        int dif1 = getNumDayByInterval(dataRef, getInstance(extractDay(dataEvento), extractMonth(dataEvento), extractYear(dataRef)));
        int dif2;

        if (dif1 < 0) {
            dif2 = dif1;
            dif1 = getNumDayByInterval(dataRef, getInstance(extractDay(dataEvento), extractMonth(dataEvento), extractYear(dataRef) + 1));
        } else {
            dif2 = getNumDayByInterval(dataRef, getInstance(extractDay(dataEvento), extractMonth(dataEvento), extractYear(dataRef) - 1));
        }
        if (permiteNegativo) {
            return (Math.abs(dif1) <= Math.abs(dif2)) ? dif1 : dif2;
        } else {
            return dif1;
        }
    }

    /**
     * retorna a data do primeiro dia do mês
     *
     * @param dt
     * @return
     */
    public static Date primeiroDiaMes(final Date dt) {
        final int day = extractDay(dt);
        return (day == 1) ? dt : incDay(dt, 1 - day);
    }

    /**
     * retorna a data do último dia do mês
     *
     * @param dt
     * @return
     */
    public static Date ultDiaMes(final Date dt) {
        return incDay(incMonth(dt, 1, 1), -1);
    }

    /**
     * Calcula a Idade baseado em java.util.Date
     *
     * @param dataNasc
     * @return
     */
    public static int calculaIdade(java.util.Date dataNasc) {
        if (isDefaultDate(dataNasc)) {
            return 0;
        }
        Calendar dateOfBirth = new GregorianCalendar();
        dateOfBirth.setTime(dataNasc);

        // Cria um objeto calendar com a data atual
        Calendar today = Calendar.getInstance();

        // Obtém a idade baseado no ano
        int age = today.get(Calendar.YEAR) - dateOfBirth.get(Calendar.YEAR);

        dateOfBirth.add(Calendar.YEAR, age);

        //se a data de hoje é antes da data de Nascimento, então diminui 1(um)
        if (today.before(dateOfBirth)) {
            age--;
        }
        return age;
    }

    /**
     * Retorna a diferença, em segundos, entre dois horários
     *
     * @param earlierDate
     * @param laterDate
     * @return
     */
    public static int diferencaEmSegundos(Date earlierDate, Date laterDate) {
        if (earlierDate == null || laterDate == null) {
            return 0;
        }

        return (int) ((laterDate.getTime() / SECOND_MILLIS) - (earlierDate.getTime() / SECOND_MILLIS));
    }

    /**
     * Retorna a diferença, em minutos, entre dois horários
     *
     * @param earlierDate
     * @param laterDate
     * @return
     */
    public static int diferencaEmMinutos(Date earlierDate, Date laterDate) {
        if (earlierDate == null || laterDate == null) {
            return 0;
        }

        return (int) ((laterDate.getTime() / MINUTE_MILLIS) - (earlierDate.getTime() / MINUTE_MILLIS));
    }

    /**
     * Retorna a diferença, em horas, entre dois horários
     *
     * @param horaInicial
     * @param horaFinal
     * @return
     */
    public static int diferencaEmHoras(Date horaInicial, Date horaFinal) {
        if (horaInicial == null || horaFinal == null) {
            return 0;
        }

        return (int) ((horaFinal.getTime() / HOUR_MILLIS) - (horaInicial.getTime() / HOUR_MILLIS));
    }

    /**
     * Apenas calcula os dias que faltam levando em conta o ano atual. Este
     * métod0o não calcula quantos dias faltam para o aniversário do ano que vem
     *
     * @param dataAniversario do cliente
     * @return inteiro positivo caso o dia do anoversário ainda não ocorreu no
     * ano. Inteiro negativo caso tenha passado.
     */
    public static int diasParaAniversario(Date dataAniversario) {

        Calendar calendar = Calendar.getInstance();
        int atual = calendar.get(Calendar.DAY_OF_YEAR);
        int ano = calendar.get(Calendar.YEAR);

        calendar.setTime(dataAniversario);
        calendar.set(Calendar.YEAR, ano);
        int aniversario = calendar.get(Calendar.DAY_OF_YEAR);

        return aniversario - atual;
    }

    /**
     * Retorna a data como Hoje, Ontem ou a data no formato padrão xx/xx/xxxx
     * caso a diferença de dias seja maior que 1
     *
     * @param dataAtual data de referencia
     * @param dataComparar data do registro que sera relativada
     * @return c.getData()
     */
    public static String getDataRelativa(Date dataAtual, Date dataComparar) {
        String result = "";
        int numDays = getNumDayByInterval(getDefaultDate(dataAtual), getDefaultDate(dataComparar));

        if (numDays == 0) {
            result = "Hoje";
        } else if (numDays == 1) {
            result = "Amanhã";
        } else if (numDays == -1) {
            result = "Ontem";
        } else if (numDays > -7 && numDays < -1) {
            result = dayOfWeek[getDayOfWeek(dataComparar) - 1];
        } else if (numDays > 1 && numDays < 7) {
            result = dayOfWeek[getDayOfWeek(dataComparar) - 1];
        }
        return result.equals("") ? (isDefaultDate(dataComparar) ? Parser.dateToStr(new Date()) : Parser.dateToStr(getDefaultDate(dataComparar))) : result;
    }

    /**
     * Dia da semana por extenso
     * @param argDayOfWeek
     * @return String domingo, segunda, terça, ...
     */
    public static String getDayOfWeekExtenso(int argDayOfWeek) {
        return argDayOfWeek > 0 && argDayOfWeek < 8 ? dayOfWeek[argDayOfWeek - 1] : "";
    }

    /**
     * Dia da semana por extenso
     * @param data
     * @return String domingo, segunda, terça, ...
     */
    public static String getDayOfWeekExtenso(Date data) {
        return getDayOfWeekExtenso(getDayOfWeek(data));
    }

    /**
     * Retorna um Date completo juntando Data (sem hora) e uma hora
     * (possivelmente vindo do banco), normalmente para cálculo de tempo entre
     * duas datas
     *
     * @param data
     * @param dataHora
     * @return
     */
    public static Date mergeDataHora(Date data, Date dataHora) {
        int dia = extractDay(data);
        int mes = extractMonth(data);
        int ano = extractYear(data);
        int hora = extractHour(dataHora);
        int minuto = extractMinute(dataHora);
        int segundo = extractSecond(dataHora);

        GregorianCalendar gc = new GregorianCalendar(ano, mes - 1, dia, hora, minuto, segundo);

        return gc.getTime();
    }

    /**
     * Retorna true se a data de consulta for igual ou estiver entre as datas de
     * início e fim
     *
     * @param dataConsulta
     * @param dataInicial
     * @param dataFinal
     * @return
     */
    public static boolean entreDatas(Date dataConsulta, Date dataInicial, Date dataFinal) {

        if (dataConsulta.equals(dataInicial) || dataConsulta.after(dataInicial)) {
            if (dataConsulta.equals(dataFinal) || dataConsulta.before(dataFinal)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retorna true se o mes e ano da data1 for igual o mes e ano da data2
     *
     * @param data1
     * @param data2
     * @return
     */
    public static boolean comparaMesAno(Date data1, Date data2) {
        int mesData1 = DateUtil.extractMonth(data1);
        int mesData2 = DateUtil.extractMonth(data2);
        int anoData1 = DateUtil.extractYear(data1);
        int anoData2 = DateUtil.extractYear(data2);
        return mesData1 == mesData2 && anoData1 == anoData2;
    }

    /**
     * Recebe a diferença de minutos entre duas datas (utilizando o método
     * diferencaEmMinutos) e retorna a resposta na forma de tempo relativo da
     * seguinte forma: - minutos exatos quando diferença < 60 min - mais de X
     * horas quando diferença < 24 horas - mais de X dias quando diferença < 1 semana
     * - mais de X meses ou mês quando diferença >= 30 dias - mais de X anos ou
     * ano quando diferença >= 365 dias
     *
     * @param minutos
     * @return
     */
    public static String getTempoRelativoExtenso(int minutos) {

        if (minutos > 525600) {
            return "mais de " + NumberUtil.trunc(minutos / 525600) + " ano(s)!";
        } else if (minutos > 43200) {
            return "mais de " + NumberUtil.trunc(minutos / 43200) + " mês(meses)!";
        } else if (minutos > 10080) {
            return "mais de " + NumberUtil.trunc(minutos / 10080) + " semana(s)!";
        } else if (minutos > 1440) {
            return "mais de " + NumberUtil.trunc(minutos / 1440) + " dia(s)!";
        } else if (minutos > 60) {
            return "mais de " + NumberUtil.trunc(minutos / 60) + " hora(s)!";
        } else {
            return minutos + " minuto(s)!";
        }
    }

    public static String getAnoMesNFe(Date dataNFe) {

        String result = "";
        //Extrai ano e mês da data da NFe
        String ano = extractYear(dataNFe) + "";
        String mes = extractMonth(dataNFe) + "";

        //Pega apenas os dois últimos dígitos do ano
        ano = ano.substring(2, 4);

        //Se o mês tiver apenas um caracter, adiciona um 0 antes do número
        if (mes.length() < 2) {
            mes = Parser.LFill(mes, 2, '0', true);
        }

        result = ano.concat(mes);
        return result;
    }

    public static String getAnoNFe(Date dataNFe) {
        String ano = "";
        ano = extractYear(dataNFe) + "";
        //Pega apenas os dois últimos dígitos do ano
        ano = ano.substring(2, 4);

        return ano;
    }

    /**
     * Retorna a data no formato requisitado pela NFe (AAAA-MM-DDThh:mm:ss.TZD)
     * onde TZD seria o TimeZone, no caso da maior parte do Brasil, -03:00 (GMT)
     * Exemplo de retorno de data: 2013-08-08T16:18:25-03:00
     *
     * @param dataNFe
     * @param hora
     * @return
     */
    public static String geraDataHoraPadraoNFe(Date dataNFe, Date hora) {
        try {
            GregorianCalendar calendar = new GregorianCalendar();
            calendar.setTime(mergeDataHora(dataNFe, hora));
            XMLGregorianCalendar xmlCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
            xmlCalendar.setMillisecond(DatatypeConstants.FIELD_UNDEFINED);
            return xmlCalendar.toString();
        } catch (DatatypeConfigurationException ex) {
            ex.getMessage();
        }

        return "";

//        String sdata = Parser.dateToStr(dataNFe, "yyyy-MM-dd");
//        String shora = Parser.dateToStr(hora, "HH:mm:ss");
//
//        //Verifica se está no horário de verão
//        Calendar c = Calendar.getInstance(TimeZone.getDefault());
//        c.setTime(new Date());
//        int offset = c.get(Calendar.DST_OFFSET);
//
//        if (offset > 0){
//            return sdata + "T" + shora + "-02:00";
//        } else {
//            return sdata + "T" + shora + "-03:00";
//        }
    }

    /**
     * Recebe uma data no formato AAAA-MM-DDThh:mm:ss.TZD
     * (2013-08-08T16:18:25-03:00) e devolve data e hora no formato dd/MM/yyyy
     * hh:mm:ss (08/08/2013 16:18:25)
     *
     * @param dataRecebto
     * @return
     * @throws ParseException
     */
    public static Date getDateNFeDHRecebto(String dataRecebto) throws ParseException {
        if (! StringUtil.isEmptyStr(dataRecebto) && dataRecebto.length() > 10) {
            Date data = Parser.strToDate(dataRecebto.substring(0, 10), "yyyy-MM-dd");
            String dataC = Parser.dateToStr(data);
            String hora = dataRecebto.substring(11, dataRecebto.length()).replace("-", "");
            hora = hora.substring(0, hora.length() - 5);
            return Parser.strToDateTime(Parser.dateTimeToStr(mergeDataHora(Parser.strToDate(dataC), Parser.strToTime(hora))));
        } else return getDefaultDate();
    }

    /**
     * Recebe uma data no formato AAAA-MM-DDThh:mm:ss
     * (2013-08-08T16:18:25) e devolve data e hora no formato dd/MM/yyyy
     * hh:mm:ss (08/08/2013 16:18:25)
     *
     * @param dataRecebto
     * @return
     * @throws ParseException
     */
    public static Date getDateNFeSemTZD(String dataRecebto) throws ParseException {
        if (! StringUtil.isEmptyStr(dataRecebto) && dataRecebto.length() > 10) {
            Date data = Parser.strToDate(dataRecebto.substring(0, 10), "yyyy-MM-dd");
            String dataC = Parser.dateToStr(data);
            String hora = dataRecebto.substring(11, dataRecebto.length());
            return Parser.strToDateTime(Parser.dateTimeToStr(mergeDataHora(Parser.strToDate(dataC), Parser.strToTime(hora))));
        } else return getDefaultDate();
    }

    /**
     * USE APENAS PARA FAZER MANUTENCAO DA DATA E HORA DE RECEBIMENTO DA NFE NO
     * FORMATO Formata como vem do site da receita federal (manualmente)
     * 11/09/2014 às 12:30:54-03:00 PARA O FORMATO AAAA-MM-DDThh:mm:ss.TZD
     * (2014-03-21T08:05:29-03:00)
     *
     * @param dataHora
     * @return
     * @throws java.text.ParseException
     */
    public static String formataDataRecbtoManutencaoNFe(String dataHora) throws ParseException {

        //Formata a data recebida (CTRL+C / CTRL+V do SITE DA RECEITA)
        //Pega o offset da data (-02:00 ou -03:00)
        String offset = dataHora.substring(22, dataHora.length());
        //Remove o offset da data
        dataHora = dataHora.substring(0, 22);
        //Substitui o às por espaço em branco
        dataHora = dataHora.replace("às ", "");
        //Formata a data + hora no formato padrão
        Date dataRecbto = Parser.strToDateTime(dataHora);

        //Formata a data e a hora para o formato da NFe
        String sdata = Parser.dateToStr(dataRecbto, "yyyy-MM-dd");
        String shora = Parser.dateToStr(dataRecbto, "HH:mm:ss");

        //Retorna a data formatada para NFe com offset de timezone
        return sdata.concat("T").concat(shora).concat(offset);
    }

    /**
     * Retorna data em formato que o google, para feed.xml
     * @param data
     * @return
     */
    public static String dateTimeGoogleToString(Date data) {
        StringBuilder sb = new StringBuilder();
        Calendar dateTime = new GregorianCalendar(TimeZone.getTimeZone("GMT"));
        long localTime = data.getTime();
        dateTime.setTimeInMillis(localTime);
        sb.append(Parser.LFill(dateTime.get(Calendar.YEAR), 4, true));
        sb.append('-');
        sb.append(Parser.LFill(dateTime.get(Calendar.MONTH) + 1, 2, true));
        sb.append('-');
        sb.append(Parser.LFill(dateTime.get(Calendar.DAY_OF_MONTH), 2, true));
        sb.append('T');
        sb.append(Parser.LFill(dateTime.get(Calendar.HOUR_OF_DAY), 2, true));
        sb.append(':');
        sb.append(Parser.LFill(dateTime.get(Calendar.MINUTE), 2, true));
        sb.append(':');
        sb.append(Parser.LFill(dateTime.get(Calendar.SECOND), 2, true));
        return sb.toString();
    }

    public static String diferencaHorasMinutosSegundos(Date dataInicio, Date dataFim){

        long diferenca = dataFim.getTime() - dataInicio.getTime();

        long segundosMilli = 1000;
        long minutosMilli = segundosMilli * 60;
        long hourasMilli = minutosMilli * 60;

        long horasPassadas = diferenca / hourasMilli;
        diferenca = diferenca % hourasMilli;

        long minutosPassados = diferenca / minutosMilli;
        diferenca = diferenca % minutosMilli;

        long segundosPassados = diferenca / segundosMilli;

        if (horasPassadas > 0) {
            return horasPassadas + ":" + minutosPassados + ":" + segundosPassados;
        }

        return (minutosPassados < 10 ? ("0" + minutosPassados) : minutosPassados) + ":" + (segundosPassados < 10 ? "0" + segundosPassados : segundosPassados);
    }
}

