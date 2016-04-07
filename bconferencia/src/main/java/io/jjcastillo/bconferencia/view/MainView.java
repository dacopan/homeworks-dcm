/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.jjcastillo.bconferencia.view;

import dnl.utils.text.table.TextTable;
import io.jjcastillo.bconferencia.helper.HelperUtil;
import io.jjcastillo.bconferencia.helper.ReaderHelper;
import io.jjcastillo.bconferencia.helper.Storage;
import io.jjcastillo.bconferencia.model.Asistente;
import io.jjcastillo.bconferencia.model.Auditorio;
import io.jjcastillo.bconferencia.model.Conferencia;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author anunaki
 */
public class MainView {

    Storage st;

    public static void main(String[] args) {
        new MainView().run();
    }

    private void run() {
        st = new Storage();

        st.load();

        String r = "si";
        while (r.equalsIgnoreCase("si") || r.equalsIgnoreCase("Sí")) {
            int opt = printMenu();
            switch (opt) {
                case 1:
                    printMenuAuditorio();
                    break;
                case 2:
                    printMenuConferencia();
                    break;
                case 3:
                    printMenuAsistentes();
                    break;
                case 4:
                    printMenuBuscar();
                    break;
                case 5:
                    Salir();
                    break;
                default:
                    System.out.println("opcion inválida");
                    break;
            }
        }
    }

    private int printMenu() {
        // imprimo menu
        System.out.println("");
        System.out.println("╔═════════════════════════════╗");
        System.out.println("║  MENU BOLETOS CONFERENCIA   ║");
        System.out.println("╠═════════════════════════════╣");
        System.out.println("║ Opción:                     ║");
        System.out.println("║        1. Auditorio         ║");
        System.out.println("║        2. Conferencias      ║");
        System.out.println("║        3. Asistentes        ║");
        System.out.println("║        4. Buscar            ║");
        System.out.println("║        5. Salir             ║");
        System.out.println("╚═════════════════════════════╝");
        System.out.println("");
        //leo opcion del usuario
        return new ReaderHelper().readOpcion("Seleccione opción", 1, 5);

    }

///<editor-fold defaultstate="collapsed" desc="auditorio">
    private void printMenuAuditorio() {
        // imprimo menu
        System.out.println("");
        System.out.println("╔═════════════════════════════╗");
        System.out.println("║   MENU AUDITORIO            ║");
        System.out.println("╠═════════════════════════════╣");
        System.out.println(" Opción:                     ");
        System.out.println("        1. " + st.auditorio1.getNombre());
        System.out.println("        2. " + st.auditorio2.getNombre());
        System.out.println("        3. Regresar          ║");
        System.out.println("");
        //leo opcion del usuario
        int opt = new ReaderHelper().readOpcion("Seleccione opción", 1, 3);

        if (opt != 3) {
            editarAuditorio(opt == 1 ? st.auditorio1 : st.auditorio2);
        }

    }

    private void editarAuditorio(Auditorio auditorio) {
        String nombre = new ReaderHelper().readString("Nombre del auditorio");
        int capacidad = new ReaderHelper().readIntPositivo("Capacidad del auditorio");
        auditorio.setNombre(nombre);
        auditorio.setAsientos(capacidad);
        st.saveAll();
    }
///</editor-fold>

///<editor-fold defaultstate="collapsed" desc="conferencias">
    private void printMenuConferencia() {
        // imprimo menu
        System.out.println("");
        System.out.println("╔═════════════════════════════╗");
        System.out.println("║   MENU Conferencia          ║");
        System.out.println("╠═════════════════════════════╣");
        System.out.println("║ Opción:                     ║");
        System.out.println("║        1. Añadir            ║");
        System.out.println("║        2. Ver               ║");
        System.out.println("║        3. Regresar          ║");
        System.out.println("╚═════════════════════════════╝");
        System.out.println("");
        //leo opcion del usuario
        int opt = new ReaderHelper().readOpcion("Seleccione opción", 1, 3);

        if (opt == 1) {
            añadirConferencia();

        } else if (opt == 2) {
            printAllConferencias();
        }

    }

    private void añadirConferencia() {
        System.out.println("Auditorio para la conferencia");
        System.out.println(" Opción:                     ");
        System.out.println("        1. " + st.auditorio1.getNombre());
        System.out.println("        2. " + st.auditorio2.getNombre());

        int auditorio = new ReaderHelper().readOpcion("Seleccione opción", 1, 2);

        String nombre = new ReaderHelper().readString("Nombre conferencia");
        Date fechaInicio = new ReaderHelper().readFecha("Fecha y hora de la conferencia");
        Date fechaFin = new ReaderHelper().readDuracion("Duración conferencia (hh:mm)", fechaInicio);

        if (st.addConferencia(new Conferencia(nombre, fechaInicio, fechaFin), auditorio)) {
            System.out.println("Conferencia añadida.");
        } else {
            System.out.println("Conferencia no añadida.");
        }

    }

    private void printAllConferencias() {
        Vector columnas = new Vector();
        columnas.add("Auditorio");
        columnas.add("Nombre");
        columnas.add("Inicio");
        columnas.add("Fin");
        columnas.add("Duración");

        System.out.println("╔═════════════════════════════╗");
        System.out.println("║   Conferencias              ║");
        System.out.println("╚═════════════════════════════╝");

//        System.out.println("");
//        System.out.println("******************************************");
//        System.out.println(st.auditorio1.getNombre());
//        System.out.println("******************************************");
//        System.out.println("");
        TextTable tt = new TextTable(new DefaultTableModel(st.getAllConferenciasVector(), columnas));
        tt.setAddRowNumbering(true);
        tt.printTable();
        System.out.println("");

//        System.out.println("");
//        System.out.println("******************************************");
//        System.out.println(st.auditorio2.getNombre());
//        System.out.println("******************************************");
//        System.out.println("");
//        TextTable tt2 = new TextTable(new DefaultTableModel(st.auditorio2.getConferenciasVector(), columnas));
//        tt2.setAddRowNumbering(true);
//        tt2.printTable();
//        System.out.println("");
    }
///</editor-fold>

///<editor-fold defaultstate="collapsed" desc="asistentes">
    private void printMenuAsistentes() {
        // imprimo menu
        System.out.println("");
        System.out.println("╔═════════════════════════════╗");
        System.out.println("║   MENU asistentes           ║");
        System.out.println("╠═════════════════════════════╣");
        System.out.println("║ Opción:                     ║");
        System.out.println("║        1. Añadir            ║");
        System.out.println("║        2. Regresar          ║");
        System.out.println("╚═════════════════════════════╝");
        System.out.println("");
        //leo opcion del usuario
        int opt = new ReaderHelper().readOpcion("Seleccione opción", 1, 2);

        if (opt == 1) {
            añadirAsistente();

        }

    }

    private void añadirAsistente() {
        System.out.println("\n\n");
        System.out.println("╔═════════════════════════════╗");
        System.out.println("║   Registar asistente        ║");
        System.out.println("╚═════════════════════════════╝");

        String nombre = new ReaderHelper().readString("Nombre asistene");
        String apellido = new ReaderHelper().readString("Apellido asistene");
        String identificacion = new ReaderHelper().readString("Identificacion");

        System.out.println("╔═════════════════════════════╗");
        System.out.println("║   Conferencias disponibles  ║");
        System.out.println("╚═════════════════════════════╝");
        printAllConferencias();

        int conferencia = new ReaderHelper().readOpcion("Seleccione opción", 1, st.getAllConferencias().size());
        Conferencia c = st.getAllConferencias().get(conferencia - 1);
        if (!st.hasAsientoDisponible(c, conferencia - 1)) {
            System.out.println("No existen cupos disponibles para esta conferencia.");
            System.out.println("asistente no añadido");
            return;
        }
        c.addAsistente(new Asistente(nombre, apellido, identificacion));
        System.out.println("asistente añadido");

        st.saveAll();
    }
///</editor-fold>

///<editor-fold defaultstate="collapsed" desc="buscar">
    private void printMenuBuscar() {
        // imprimo menu
        System.out.println("");
        System.out.println("╔══════════════════════════════════╗");
        System.out.println("║   MENU Buscar                    ║");
        System.out.println("╠══════════════════════════════════╣");
        System.out.println("║ Opción:                          ║");
        System.out.println("║        1. Asistente por Nombre   ║");
        System.out.println("║        2. Evento por nombre      ║");
        System.out.println("║        3. Evento por hora        ║");
        System.out.println("║        4. Regresar               ║");
        System.out.println("╚══════════════════════════════════╝");
        System.out.println("");
        //leo opcion del usuario
        int opt = new ReaderHelper().readOpcion("Seleccione opción", 1, 4);

        switch (opt) {
            case 1:
                buscarAsistentePorNombre();
                break;
            case 2:
                buscarEventoPorNombre();
                break;
            case 3:
                buscarEventoPorHora();
                break;
            default:
                break;
        }

    }
///</editor-fold>

    private void Salir() {
        String r = new ReaderHelper().readString("Seguro desea salir?");
        if (r.equalsIgnoreCase("si") || r.equalsIgnoreCase("Sí")) {
            System.out.println("Gracias por utilizar nuestros servicios...");
            System.exit(0);
        }
    }

    private void buscarAsistentePorNombre() {
        System.out.println("═════════════════════════════════════════════════════════════════");
        Vector columnas = new Vector();
        columnas.add("Asistente");
        columnas.add("Evento");
        columnas.add("Hora inicio");
        columnas.add("Auditorio");

        String nombre = new ReaderHelper().readString("Nombre y/o apellido a buscar");
        int confCount = 0;
        int auditConfSize = st.auditorio1.getConferencias().size();
        Vector resultado = new Vector();

        ArrayList<Conferencia> confs = st.getAllConferencias();
        for (Conferencia conf : confs) {
            confCount++;
            for (Asistente a : conf.getAsistentes()) {
                if (a.getFullName().contains(nombre)) {
                    Vector v = new Vector();
                    v.add(a.getFullName());
                    v.add(conf.getNombre());
                    v.add(HelperUtil.formatDate(conf.getFechaFin()));
                    v.add(confCount <= auditConfSize ? st.auditorio1.getNombre() : st.auditorio2.getNombre());
                    resultado.add(v);
                }
            }
        }

        System.out.println("");
        TextTable tt = new TextTable(new DefaultTableModel(resultado, columnas));
        tt.setAddRowNumbering(true);
        tt.printTable();
        System.out.println("");
    }

    private void buscarEventoPorNombre() {

        Vector columnas = new Vector();
        columnas.add("Asistente");

        String nombre = new ReaderHelper().readString("Nombre conferencia a buscar");
        ArrayList<Conferencia> confs = st.getAllConferencias();
        int confCount = 0;

        for (Conferencia conf : confs) {
            confCount++;
            if (conf.getNombre().contains(nombre)) {
                Vector resultado = new Vector();

                //recorro los asistentes
                for (Asistente a : conf.getAsistentes()) {
                    Vector v = new Vector();
                    v.add(a.getFullName());
                    resultado.add(v);
                }

                System.out.println("═════════════════════════════════════════════════════════════════");
                System.out.println("conferencia: " + conf.getNombre() + "\thora: " + HelperUtil.formatDate(conf.getFechaInicio())
                        + "\tasistentes: " + resultado.size());
                System.out.println("═════════════════════════════════════════════════════════════════");
                //por cada conferencia q coincida con el nombre imprimo los asistentes

                System.out.println("");
                TextTable tt = new TextTable(new DefaultTableModel(resultado, columnas));
                tt.setAddRowNumbering(true);
                tt.printTable();
                System.out.println("");

            }

        }

    }

    /**
     * busca evento por hora, si la hora ingresada esta dentro del intervalo de
     * duración de una conferencia, esta será mostrada por ejemplo, si el evento
     * a es de 11h a 12h, y se solicita buscar eventos a las 11h30, el evento a
     * será mostrado.
     */
    private void buscarEventoPorHora() {
        System.out.println("═════════════════════════════════════════════════════════════════");
        Vector columnas = new Vector();
        columnas.add("Evento");
        columnas.add("Auditorio");

        Date horax = new ReaderHelper().readFecha("Fecha y hora de conferencia a buscar formato (hh:mm)");

        ArrayList<Conferencia> confs = st.getAllConferencias();
        int confCount = 0;
        int auditConfSize = st.auditorio1.getConferencias().size();
        Vector resultado = new Vector();

        for (Conferencia conf : confs) {
            confCount++;
            if (horax.compareTo(conf.getFechaInicio()) >= 0 && horax.compareTo(conf.getFechaFin()) <= 0) {

                Vector v = new Vector();
                v.add(conf.getNombre());
                v.add(confCount <= auditConfSize ? st.auditorio1.getNombre() : st.auditorio2.getNombre());
                resultado.add(v);
            }

        }
        System.out.println("");
        TextTable tt = new TextTable(new DefaultTableModel(resultado, columnas));
        tt.setAddRowNumbering(true);
        tt.printTable();
        System.out.println("");

    }

}
