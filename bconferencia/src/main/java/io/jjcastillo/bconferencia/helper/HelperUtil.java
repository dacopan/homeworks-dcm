/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.jjcastillo.bconferencia.helper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 *
 * @author anunaki
 */
public final class HelperUtil {

    public static String PrettifyDateDiff(long dateDiff) {
        /* Constantes utilizadas para facilitar
         * la lectura de la aplicación
         */
        long second = 1000;
        long minute = second * 60;
        long hour = minute * 60;
        long day = hour * 24;

        // Dividimos los milisegundos entre su equivalente de
        // las constantes de arriba para obtener el valor en la
        // escala de tiempo correspondiente.
        long minutes = dateDiff / minute;
        long hours = dateDiff / hour;
        long days = dateDiff / day;

        String prettyDateString;

        if (minutes > 60) {
            prettyDateString = minutes - (hours * 60) + " minutos.";

            if (hours > 24) {
                prettyDateString = hours - (days * 24) + " horas " + prettyDateString;

            } else {
                prettyDateString = hours + " horas " + prettyDateString;
            }

        } else {
            prettyDateString = minutes + " minutos.";
        }

        return prettyDateString;
    }

    public static String formatDate(Date fechaInicio) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm",
                Locale.getDefault());
        return format.format(fechaInicio);
    }

    /**
     *
     * @param startA
     * @param endA
     * @param startB
     * @param endB
     * @return return true si las fechas se sobreponen, intersecan
     */
    public static boolean overlapDate(Date startA, Date endA, Date startB, Date endB) {
        return (startA.compareTo(endB) < 0)// startA menor q endB
                && (endA.compareTo(startB) > 0); //end A mayor q startB
    }
}
