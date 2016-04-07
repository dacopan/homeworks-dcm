/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.dacopancm.oraclesp;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author dacopan
 */
public class Helper {

    private static final Log log = LogFactory.getLog(Helper.class);

    /**
     *
     * @param line yyyy-MM-dd HH:mm
     * @return
     */
    public static Date stringToDate(String line) {

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm",
                Locale.getDefault());

        Date date = null;
        try {
            date = format.parse(line);
        } catch (ParseException e) {
            log.error("Lo sentimos, eso no es válido. Por favor reintente.");
        }

        return date;
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
