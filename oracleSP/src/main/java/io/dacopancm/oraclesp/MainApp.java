/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.dacopancm.oraclesp;

import io.dacopancm.oraclesp.controller.ConferenciaJpaController;
import io.dacopancm.oraclesp.model.Conferencia;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author dacopan
 */
public class MainApp {
    
    private final Log log = LogFactory.getLog(this.getClass());
    int idx = 2005;
    
    public static void main(String[] args) {
        new MainApp().Run();
    }
    
    public void Run() {
        //testConnect();
        long start_time = System.currentTimeMillis();
        log.info("oracleSP: start=" + start_time);

        //test1(); //a mano lista simple
        Date dateInicio = Helper.stringToDate("2015-05-01 12:01");
        Date dateFin = Helper.stringToDate("2015-05-01 12:01");
        insertConferencia(idx, dateInicio, dateFin, ("conferencia " + idx));
        
        long end_time = System.currentTimeMillis();
        log.info("oracleSP: end=" + end_time);
        
        log.info("oracleSP: delta=" + (end_time - start_time)/1000+"seconds");
    }
    
    public void insertConferencia(int idx, Date dateInicio, Date dateFin, String name) {
        try {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("io.dacopancm_oracleSP_jar_1.0-SNAPSHOTPU");
            ConferenciaJpaController conferenciaCtl = new ConferenciaJpaController(emf);

            //taremos todas las conferencias
            List<Conferencia> conferencias = conferenciaCtl.findConferenciaEntities();

            //recorremos para probar si esa fecha esta o no en conflicto
            for (Conferencia c : conferencias) {
                if (Helper.overlapDate(c.getCfrInicio(), c.getCfrFin(), dateInicio, dateFin)) {
                    log.error("ya existe una confernecia en esas horas");
                    log.error(c);
                    return;
                }
            }
            Conferencia c = new Conferencia();
            c.setCfrId(new BigDecimal(idx));
            c.setCfrInicio(dateInicio);
            c.setCfrFin(dateFin);
            c.setCfrNombre(name);
            
            conferenciaCtl.create(c);
            //conferenciaCtl.destroy(c.getCfrId());
        } catch (Exception ex) {
            log.error(ex);
        }
    }
    
    public void oracle1() {
        
    }
    
    public void testConnect() {
        
        System.out.println("-------- Oracle JDBC Connection Testing ------");
        
        try {
            
            Class.forName("oracle.jdbc.driver.OracleDriver");
            
        } catch (ClassNotFoundException e) {
            
            System.out.println("Where is your Oracle JDBC Driver?");
            e.printStackTrace();
            return;
            
        }
        
        System.out.println("Oracle JDBC Driver Registered!");
        
        Connection connection = null;
        
        try {
            
            connection = DriverManager.getConnection(
                    "jdbc:oracle:thin:@192.168.10.2:1521:enki", "scott",
                    "DACOPANdacopan1");
            
        } catch (SQLException e) {
            
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return;
            
        }
        
        if (connection != null) {
            System.out.println("You made it, take control your database now!");
        } else {
            System.out.println("Failed to make connection!");
        }
    }
    
}
