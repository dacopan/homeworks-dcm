/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.jjcastillo.arboleq.helper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Scanner;

/**
 *
 * @author anunaki
 */
public class ReaderHelper {

    Scanner sc;

    public ReaderHelper() {
        //sc = new Scanner(System.in);
    }

    public String readString(String title) {
        System.out.println(title);
        return readString();
    }

    public String readString() {
        sc = new Scanner(System.in);
        return sc.nextLine();
    }

    public int readInt(String title) {
        System.out.println(title);
        boolean read;
        do {
            try {
                sc = new Scanner(System.in);
                int t = Integer.parseInt(sc.nextLine());
                //sc.close();
                return t;
            } catch (Exception ex) {
                System.out.println("Ingrese un entero válido");
                read = true;

            }
        } while (read);
        return -1;
    }

    public int readOpcion(String title, int min, int max) {
        System.out.println(title);
        boolean read = true;
        do {
            try {
                Scanner sc = new Scanner(System.in);
                String k = sc.nextLine();
                int t = Integer.parseInt(k);

                if (t >= min && t <= max) {
                    //sc.close();
                    return t;
                }
            } catch (Exception ex) {
                System.out.println("Ingrese una opción valida");
                //sc.close();
            }
        } while (read);
        return -1;
    }

    public int readIntPositivo(String title) {
        System.out.println(title);
        boolean read = true;
        do {
            try {
                sc = new Scanner(System.in);
                int t = Integer.parseInt(sc.nextLine());
                if (t >= 0) {
                    //sc.close();
                    return t;
                }
            } catch (Exception ex) {
                //sc.close();
                System.out.println("Ingrese un entero positivo");
            }
        } while (read);
        return -1;
    }

    public Date readFecha(String title) {
        System.out.println(title);
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm",
                Locale.getDefault());
        System.out.println("Ingrese la fecha y hora en el formato yyyy-MM-dd HH:mm");
        System.out.println("Por ejemplo, así  " + format.format(new Date()));
        Date date = null;
        while (date == null) {
            sc = new Scanner(System.in);
            String line = sc.nextLine();
            //sc.close();
            try {
                date = format.parse(line);
            } catch (ParseException e) {
                System.out.println("Lo sentimos, eso no es válido. Por favor reintente.");
            }
        }
        return date;
    }

    public Date readDuracion(String title, Date _fechaInicio) {
        System.out.println(title);
        DateFormat format = new SimpleDateFormat("HH:mm",
                Locale.getDefault());
        System.out.println("Ingrese la hora en el formato HH:mm");
        System.out.println("Por ejemplo, así  " + format.format(new Date()));
        Date date = null;
        while (date == null) {
            sc = new Scanner(System.in);
            String line = sc.nextLine();
            //sc.close();
            try {
                date = format.parse(line);
            } catch (ParseException e) {
                System.out.println("Lo sentimos, eso no es válido. Por favor reintente.");
            }
        }

        GregorianCalendar fechaInicio = new GregorianCalendar();
        fechaInicio.setTime(_fechaInicio);

        GregorianCalendar _fechaFin = new GregorianCalendar();
        _fechaFin.setTime(date);

        fechaInicio.add(Calendar.HOUR, _fechaFin.get(Calendar.HOUR));
        fechaInicio.add(Calendar.MINUTE, _fechaFin.get(Calendar.MINUTE));
        return fechaInicio.getTime();

    }

    public Date readHora(String title) {
        System.out.println(title);
        DateFormat format = new SimpleDateFormat("HH:mm",
                Locale.getDefault());
        System.out.println("Ingrese la hora en el formato HH:mm");
        System.out.println("Por ejemplo, así  " + format.format(new Date()));
        Date date = null;
        while (date == null) {
            sc = new Scanner(System.in);
            String line = sc.nextLine();
            //sc.close();
            try {
                date = format.parse(line);
            } catch (ParseException e) {
                System.out.println("Lo sentimos, eso no es válido. Por favor reintente.");
            }
        }

        return date;
    }

    public Double readDoublePositivo(String title) {
        System.out.println(title);
        boolean read = true;
        do {
            try {
                sc = new Scanner(System.in);
                Double t = Double.parseDouble(sc.nextLine());
                if (t >= 0) {
                    //sc.close();
                    return t;
                }
            } catch (Exception ex) {
                //sc.close();
                System.out.println("Ingrese un entero positivo");
            }
        } while (read);
        return -1.0;
    }
}
