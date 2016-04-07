/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.jjcastillo.bconferencia.helper;

import io.jjcastillo.bconferencia.model.Auditorio;
import io.jjcastillo.bconferencia.model.Conferencia;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Alexander
 */
public class StorageTest {

    public StorageTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of load method, of class Storage.
     */
    @Test
    public void testLoad1() {

        System.out.println("Load");
        Storage instance = new Storage();

        File f = new File(instance.FILE_NAME);
        f.delete();

        instance.load();

        int conf = instance.auditorio1.getConferencias().size();
        // TODO review the generated test code and remove the default call to fail.
        assertEquals(0, conf);
    }

    @Test
    public void testLoad2() {
        String path = "jjcastillo_bconferencia.txt";
        System.out.println("saveAll");
        Storage instance = new Storage();

        instance.auditorio1.setNombre("Juan León Mera");
        instance.auditorio1.setAsientos(33);
        instance.auditorio2.setNombre("Antonio Salgado");
        instance.auditorio2.setAsientos(27);
        instance.saveAll();

        instance.auditorio1.setNombre("Jxxxx");
        instance.load();

        assertEquals(instance.auditorio1.getNombre(), "Juan León Mera");
    }

    /**
     * Test of fromJSON method, of class Storage.
     */
    @Test
    public void testFromJSON() {
        System.out.println("fromJSON");
        String dataAudit = "[{\"nombre\":\"Juan León Mera\",\"asientos\":33,\"conferencias\":[]},{\"nombre\":\"Antonio Salgado\",\"asientos\":27,\"conferencias\":[]}]";
        ArrayList<Auditorio> arrayExp = new ArrayList();
        ArrayList<Auditorio> arrayResult = Storage.fromJSON(dataAudit);
        Auditorio dataResult1 = new Auditorio("Juan León Mera", 33);
        Auditorio dataResult2 = new Auditorio("Antonio Salgado", 27);
        arrayExp.add(dataResult1);
        arrayExp.add(dataResult2);
        String expResult = arrayExp.toString();
        String result = arrayResult.toString();
        assertEquals(expResult, result);
    }

    /**
     * Test of toJSON method, of class Storage.
     */
    @Test
    public void testToJSON() {
        System.out.println("toJSON");
        String expResult = "[{\"nombre\":\"Juan León Mera\",\"asientos\":33,\"conferencias\":[]},{\"nombre\":\"Antonio Salgado\",\"asientos\":27,\"conferencias\":[]}]";
        ArrayList<Auditorio> arrayResult = new ArrayList();
        Auditorio dataResult1 = new Auditorio("Juan León Mera", 33);
        Auditorio dataResult2 = new Auditorio("Antonio Salgado", 27);
        arrayResult.add(dataResult1);
        arrayResult.add(dataResult2);
        String result = Storage.toJSON(arrayResult.toArray());
        assertEquals(expResult, result);
    }

    /**
     * Test of saveAll method, of class Storage.
     */
    @Test
    public void testSaveAll() {
        String path = "jjcastillo_bconferencia.txt";
        System.out.println("saveAll");
        Storage instance = new Storage();
        String prueba = "[{\"nombre\":\"Juan León Mera\",\"asientos\":33,\"conferencias\":[]},{\"nombre\":\"Antonio Salgado\",\"asientos\":27,\"conferencias\":[]}]";
        String expResult = prueba.trim();

        instance.auditorio1.setNombre("Juan León Mera");
        instance.auditorio1.setAsientos(33);
        instance.auditorio2.setNombre("Antonio Salgado");
        instance.auditorio2.setAsientos(27);
        instance.saveAll();

        File f = new File(path);
        BufferedReader br = null;
        String result = "";
        try {
            br = new BufferedReader(new FileReader(path));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            result = sb.toString().trim();
        } catch (IOException ex) {
            Logger.getLogger(Storage.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Resultado: " + result);
        System.out.println("Resuktado: " + expResult);
        assertEquals(expResult, result);

    }

    /**
     * Test of addConferencia method, of class Storage.
     */
    @Test
    public void testAddConferencia() {
        Storage instance = new Storage();
        System.out.println("addConferencia");
        ReaderHelper metodos = new ReaderHelper();
        Date dateIn = metodos.readFechaAction("2015-06-16 16:30");
        Date dateFn = metodos.readDuracionAction(dateIn, "03:30");
        Conferencia test = new Conferencia("Cableado Estructurado", dateIn, dateFn);
        Date dateIn2 = metodos.readFechaAction("2015-06-16 16:30");
        Date dateFn2 = metodos.readDuracionAction(dateIn, "03:30");
        Conferencia test2 = new Conferencia("Redes", dateIn, dateFn);
        boolean expResult = false;
        instance.addConferencia(test, 1);
        boolean result = instance.addConferencia(test2, 1);
        assertEquals(expResult, result);
    }

    /**
     * Test of getAllConferenciasVector method, of class Storage.
     */
    @Test
    public void testGetAllConferenciasVector() {
        System.out.println("getAllConferenciasVector");
        Storage instance = new Storage();
        ReaderHelper metodos = new ReaderHelper();

        Date dateIn1 = metodos.readFechaAction("2015-06-16 16:30");
        Date dateFn1 = metodos.readDuracionAction(dateIn1, "03:30");
        Conferencia test1 = new Conferencia("Cableado Estructurado", dateIn1, dateFn1);
        Date dateIn2 = metodos.readFechaAction("2014-01-11 11:40");
        Date dateFn2 = metodos.readDuracionAction(dateIn1, "01:20");
        Conferencia test2 = new Conferencia("Redes", dateIn2, dateFn2);
        Date dateIn3 = metodos.readFechaAction("2015-09-19 13:30");
        Date dateFn3 = metodos.readDuracionAction(dateIn1, "01:00");
        Conferencia test3 = new Conferencia("POO", dateIn3, dateFn3);
        Date dateIn4 = metodos.readFechaAction("2012-04-07 19:40");
        Date dateFn4 = metodos.readDuracionAction(dateIn4, "00:30");
        Conferencia test4 = new Conferencia("Análisis Matemático", dateIn4, dateFn4);
        instance.auditorio1.addConferencia(test1);
        instance.auditorio1.addConferencia(test3);
        instance.auditorio2.addConferencia(test2);
        instance.auditorio2.addConferencia(test4);

        Vector result = instance.getAllConferenciasVector();
        Vector expResult = new Vector();
        expResult.add("[auditorio 1, Cableado Estructurado, 2015-06-16 16:30, 2015-06-16 16:30, 23907660 minutos.]");
        expResult.add("[auditorio 1, POO, 2015-09-19 13:30, 2015-09-19 13:30, 24044430 minutos.]");
        expResult.add("[auditorio 2, Redes, 2014-01-11 11:40, 2014-01-11 11:40, 23157260 minutos.]");
        expResult.add("[auditorio 2, Análisis Matemático, 2012-04-07 19:40, 2012-04-07 19:40, 22230430 minutos.]");
        String resultS = result.toString();
        String expResultS = expResult.toString();
        assertEquals(expResultS, resultS);

    }

    /**
     * Test of getAllConferencias method, of class Storage.
     */
    @Test
    public void testGetAllConferencias() {
        System.out.println("getAllConferencias");

        Storage instance = new Storage();
        ReaderHelper metodos = new ReaderHelper();

        Date dateIn1 = metodos.readFechaAction("2015-06-16 16:30");
        Date dateFn1 = metodos.readDuracionAction(dateIn1, "03:30");
        Conferencia test1 = new Conferencia("Cableado Estructurado", dateIn1, dateFn1);
        Date dateIn2 = metodos.readFechaAction("2014-01-11 11:40");
        Date dateFn2 = metodos.readDuracionAction(dateIn1, "01:20");
        Conferencia test2 = new Conferencia("Redes", dateIn2, dateFn2);
        Date dateIn3 = metodos.readFechaAction("2015-09-19 13:30");
        Date dateFn3 = metodos.readDuracionAction(dateIn1, "01:00");
        Conferencia test3 = new Conferencia("POO", dateIn3, dateFn3);
        Date dateIn4 = metodos.readFechaAction("2012-04-07 19:40");
        Date dateFn4 = metodos.readDuracionAction(dateIn1, "00:30");
        Conferencia test4 = new Conferencia("Análisis Matemático", dateIn4, dateFn4);
        instance.auditorio1.addConferencia(test1);
        instance.auditorio1.addConferencia(test3);
        instance.auditorio2.addConferencia(test2);
        instance.auditorio2.addConferencia(test4);

        ArrayList<Conferencia> resultArray = instance.getAllConferencias();
        ArrayList<Conferencia> expResultArray = new ArrayList();
        expResultArray.add(test1);
        expResultArray.add(test3);
        expResultArray.add(test2);
        expResultArray.add(test4);
        String result = resultArray.toString();
        String expResult = expResultArray.toString();
        assertEquals(expResult, result);
    }

    /**
     * Test of hasAsientoDisponible method, of class Storage. Verifica
     * disponibilidad de asientos
     */
    @Test
    public void testHasAsientoDisponible() {
        System.out.println("hasAsientoDisponible");
        Storage instance = new Storage();
        //ArrayList<Conferencia> a = new ArrayList(instance.auditorio1.getConferencias());
        ArrayList<Conferencia> a = new ArrayList();
        ReaderHelper metodos = new ReaderHelper();
        Date dateIn1 = metodos.readFechaAction("2015-06-16 16:30");
        Date dateFn1 = metodos.readDuracionAction(dateIn1, "03:30");
        Conferencia test1 = new Conferencia("Cableado Estructurado", dateIn1, dateFn1);
        Date dateIn2 = metodos.readFechaAction("2014-01-11 11:40");
        Date dateFn2 = metodos.readDuracionAction(dateIn1, "01:20");
        Conferencia test2 = new Conferencia("Redes", dateIn2, dateFn2);
        a.add(test1);
        a.add(test2);
        instance.auditorio1.setAsientos(30);
        instance.auditorio1.addConferencia(test1);
        instance.auditorio1.addConferencia(test2);
        // a.addAll(instance.auditorio2.getConferencias());
        int select = 0;
        Conferencia c = a.get(select);
        boolean result = instance.hasAsientoDisponible(c, 1);
        boolean expResult = true;
        assertEquals(expResult, result);
    }

}
