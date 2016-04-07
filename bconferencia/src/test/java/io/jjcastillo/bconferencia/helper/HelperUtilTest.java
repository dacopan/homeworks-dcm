/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.jjcastillo.bconferencia.helper;

import java.util.Date;
import java.util.GregorianCalendar;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import static org.junit.Assert.*;

/**
 *
 * @author anunaki
 */
public class HelperUtilTest {

    public HelperUtilTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

///<editor-fold defaultstate="collapsed" desc="testPrettifyDateDiff">    
    /**
     * Test of PrettifyDateDiff method, of class HelperUtil.
     */
    @org.junit.Test
    public void testPrettifyDateDiff1() {
        System.out.println("PrettifyDateDiff");
        long dateDiff = 1000 * 60 * (5 + 60 * 8);
        String expResult = "8 horas 5 minutos.";
        String result = HelperUtil.PrettifyDateDiff(dateDiff);
        assertEquals(expResult, result);
        System.out.println("result: " + result);

    }

    /**
     * Test of PrettifyDateDiff method, of class HelperUtil.
     */
    @org.junit.Test
    public void testPrettifyDateDiff2() {
        System.out.println("PrettifyDateDiff");
        long dateDiff = 1000 * 60 * 5;
        String expResult = "5 minutos.";
        String result = HelperUtil.PrettifyDateDiff(dateDiff);
        assertEquals(expResult, result);
        System.out.println("result: " + result);

    }

    /**
     * Test of PrettifyDateDiff method, of class HelperUtil.
     */
    @org.junit.Test
    public void testPrettifyDateDiff3() {
        System.out.println("PrettifyDateDiff");
        long dateDiff = 1000 * 60 * (5 + 60 * 25);
        String expResult = "1 horas 5 minutos.";
        String result = HelperUtil.PrettifyDateDiff(dateDiff);
        assertEquals(expResult, result);
        System.out.println("result: " + result);

    }
///</editor-fold>

///<editor-fold defaultstate="collapsed" desc="testFormatDate">
    /**
     * Test of formatDate method, of class HelperUtil.
     */
    @org.junit.Test
    public void testFormatDate() {
        System.out.println("formatDate");
        Date fechaInicio = new GregorianCalendar(2015, 11, 25, 15, 58).getTime();
        String expResult = "2015-12-25 15:58";
        String result = HelperUtil.formatDate(fechaInicio);
        assertEquals(expResult, result);

        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
///</editor-fold>

///<editor-fold defaultstate="collapsed" desc="test overaldate">
    /**
     * Test of overlapDate method, of class HelperUtil.
     */
    @org.junit.Test
    public void testOverlapDate1() {
        System.out.println("overlapDate");
        Date startA = new GregorianCalendar(2015, 1, 1, 9, 0).getTime();
        Date endA = new GregorianCalendar(2015, 1, 1, 10, 0).getTime();
        Date startB = new GregorianCalendar(2015, 1, 1, 11, 00).getTime();
        Date endB = new GregorianCalendar(2015, 1, 1, 12, 00).getTime();
        boolean expResult = false;
        boolean result = HelperUtil.overlapDate(startA, endA, startB, endB);
        assertEquals(expResult, result);
    }

    /**
     * Test of overlapDate method, of class HelperUtil.
     */
    @org.junit.Test
    public void testOverlapDate2() {
        System.out.println("overlapDate");
        //case 2
        Date startA = new GregorianCalendar(2015, 1, 1, 9, 0).getTime();
        Date endA = new GregorianCalendar(2015, 1, 1, 10, 0).getTime();
        Date startB = new GregorianCalendar(2015, 1, 1, 9, 55).getTime();
        Date endB = new GregorianCalendar(2015, 1, 1, 12, 00).getTime();
        boolean expResult = true;
        boolean result = HelperUtil.overlapDate(startA, endA, startB, endB);
        assertEquals(expResult, result);
    }

    /**
     * Test of overlapDate method, of class HelperUtil.
     */
    @org.junit.Test
    public void testOverlapDate3() {
        System.out.println("overlapDate");
        //case 2
        Date startA = new GregorianCalendar(2015, 1, 1, 9, 00).getTime();
        Date endA = new GregorianCalendar(2015, 1, 1, 10, 0).getTime();
        Date startB = new GregorianCalendar(2015, 1, 1, 8, 0).getTime();
        Date endB = new GregorianCalendar(2015, 1, 1, 9, 5).getTime();
        boolean expResult = true;
        boolean result = HelperUtil.overlapDate(startA, endA, startB, endB);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of overlapDate method, of class HelperUtil.
     */
    @org.junit.Test
    public void testOverlapDate4() {
        System.out.println("overlapDate");
        //case 2
        Date startA = new GregorianCalendar(2015, 1, 1, 12, 00).getTime();
        Date endA = new GregorianCalendar(2015, 1, 1, 13, 0).getTime();
        Date startB = new GregorianCalendar(2015, 1, 1, 8, 0).getTime();
        Date endB = new GregorianCalendar(2015, 1, 1, 9, 5).getTime();
        boolean expResult = false;
        boolean result = HelperUtil.overlapDate(startA, endA, startB, endB);
        assertEquals(expResult, result);
    }
        
///</editor-fold>
}
