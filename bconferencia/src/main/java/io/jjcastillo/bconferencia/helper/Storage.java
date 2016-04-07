/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.jjcastillo.bconferencia.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import io.jjcastillo.bconferencia.model.Auditorio;
import io.jjcastillo.bconferencia.model.Conferencia;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author anunaki
 */
public class Storage {

    public Auditorio auditorio1;
    public Auditorio auditorio2;
    public final String FILE_NAME = "jjcastillo_bconferencia.txt";

    public Storage() {
        auditorio1 = new Auditorio("auditorio 1", 0);
        auditorio2 = new Auditorio("auditorio 2", 0);
    }

    public void load() {
        File f = new File(FILE_NAME);
        if (f.exists()) {
            //load data
            BufferedReader br = null;
            try {
                br = new BufferedReader(new FileReader(FILE_NAME));
                StringBuilder sb = new StringBuilder();
                String line = br.readLine();

                while (line != null) {
                    sb.append(line);
                    sb.append(System.lineSeparator());
                    line = br.readLine();
                }
                String dataString = sb.toString();
                ArrayList<Auditorio> data = new ArrayList();
                data = fromJSON(dataString);
                if (data != null && data.size() > 1) {
                    auditorio1 = data.get(0);
                    auditorio2 = data.get(1);
                } else {
                    System.out.println("no se pudo recuperar datos");
                }

            } catch (IOException ex) {
                Logger.getLogger(Storage.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException ex) {
                        Logger.getLogger(Storage.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

        } else {
            try {
                //primera vez, create data
                f.createNewFile();
                saveAll();

            } catch (IOException ex) {
                Logger.getLogger(Storage.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public static ArrayList<Auditorio> fromJSON(final String jsonPacket) {
        ArrayList<Auditorio> data = null;
        try {
            data = new ObjectMapper().readValue(jsonPacket,
                    TypeFactory.defaultInstance().constructCollectionType(List.class,
                            Auditorio.class));
        } catch (Exception e) {
            // Handle the problem
            System.out.println(e.getMessage());
        }
        return data;
    }

    public static String toJSON(Object[] toArray) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.writeValueAsString(toArray);
        } catch (JsonProcessingException ex) {
            return "[]";
        }
    }

    public void saveAll() {
        File f = new File(FILE_NAME);
        FileOutputStream str = null;
        try {
            str = new FileOutputStream(f, false);

            ArrayList<Auditorio> data = new ArrayList();
            data.add(auditorio1);
            data.add(auditorio2);

            str.write(toJSON(data.toArray()).getBytes());
            str.close();
        } catch (Exception ex) {
            Logger.getLogger(Storage.class.getName()).log(Level.SEVERE, null, ex);
            if (str != null) {
                try {
                    str.close();
                } catch (IOException ex1) {
                    Logger.getLogger(Storage.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }
        }

    }

    public boolean addConferencia(Conferencia conferencia, int auditorio) {

        ArrayList<Conferencia> confs = new ArrayList();
        confs.addAll((1 == auditorio ? auditorio1 : auditorio2).getConferencias());
        // confs.addAll(auditorio2.getConferencias());

        for (Conferencia conf : confs) {
            // if (conf.getNombre().equalsIgnoreCase(conferencia.getNombre())) {
            if (HelperUtil.overlapDate(conf.getFechaInicio(), conf.getFechaFin(), conferencia.getFechaInicio(), conferencia.getFechaFin())) {
                System.out.println("Ya existe una conferencia en es horario");
                return false;
            }
            //}
        }
        (1 == auditorio ? auditorio1 : auditorio2).addConferencia(conferencia);
        saveAll();
        return true;
    }

    public Vector getAllConferenciasVector() {
        Vector v = new Vector();
        for (Conferencia c : auditorio1.getConferencias()) {
            Vector v1 = new Vector();
            v1.add(auditorio1.getNombre());
            v1.add(c.getNombre());
            v1.add(HelperUtil.formatDate(c.getFechaInicio()));
            v1.add(HelperUtil.formatDate(c.getFechaFin()));
            v1.add(HelperUtil.PrettifyDateDiff(c.getFechaFin().getTime() - c.getFechaInicio().getTime()));
            v.add(v1);
        }

        for (Conferencia c : auditorio2.getConferencias()) {
            Vector v1 = new Vector();
            v1.add(auditorio2.getNombre());
            v1.add(c.getNombre());
            v1.add(HelperUtil.formatDate(c.getFechaInicio()));
            v1.add(HelperUtil.formatDate(c.getFechaFin()));
            v1.add(HelperUtil.PrettifyDateDiff(c.getFechaFin().getTime() - c.getFechaInicio().getTime()));
            v.add(v1);
        }

        return v;
    }

    public ArrayList<Conferencia> getAllConferencias() {
        ArrayList a = new ArrayList<>(auditorio1.getConferencias());
        a.addAll(auditorio2.getConferencias());
        return a;

    }

    public boolean hasAsientoDisponible(Conferencia c, int conferenciaIndex) {
        return c.getAsistentes().size() < ((auditorio1.getConferencias().size() - 1) <= conferenciaIndex ? auditorio1 : auditorio2)
                .getAsientos();
    }
}
