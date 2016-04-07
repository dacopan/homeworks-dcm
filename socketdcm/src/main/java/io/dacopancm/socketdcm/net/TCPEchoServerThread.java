package io.dacopancm.socketdcm.net;

import io.dacopancm.socketdcm.helper.HelperUtil;
import io.dacopancm.socketdcm.view.MainApp;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;

public class TCPEchoServerThread implements Runnable {

    ServerSocket servSock;
    public boolean isRunning = false;
    static Logger logger;
    MainApp mainApp;

    public TCPEchoServerThread(MainApp mainapp) {
        this.mainApp = mainapp;
    }

    public void initServer() throws IOException {
        logger = Logger.getLogger("practical");
        this.isRunning = true;
        int echoServPort = HelperUtil.getIntProperty(HelperUtil.SRV_PORT); // Server port

        // Create a server socket to accept client connection requests
        servSock = new ServerSocket(echoServPort);

        //servSock.setSoTimeout(HelperUtil.getIntProperty(HelperUtil.SRV_TIMEOUT));
        servSock.setReuseAddress(HelperUtil.getBoolProperty(HelperUtil.SRV_REUSEADDRESS));
        //servSock.set
        //servSock.set(HelperUtil.getBoolProperty(HelperUtil.SRV_REUSEADDRESS));

        logger.log(Level.INFO, "Server init: {0}", servSock.getInetAddress());
//de.jensd.fx.glyphs.fontawesome.FontAwesomeIcons.SERVER;

        // Run forever, accepting and spawning a thread for each connection
        HelperUtil.showInfoB("Servidor iniciado.");
        while (isRunning) {
            try {
                Socket clntSock = servSock.accept(); // Block waiting for connection
                //clntSock.set

                // Spawn thread to handle new connection
                Thread thread = new Thread(new HandleProtocol(clntSock, logger, mainApp));
                thread.start();
                logger.log(Level.INFO, "Created and started Thread {0}", thread.getName());
            } catch (java.net.SocketException sck) {
                //HelperUtil.showErrorB("no se pudo aceptar conexión");
                logger.log(Level.INFO, "Error normal, socket cerrado al finalizar app {0}", sck);
                this.isRunning = false;
            } catch (java.net.SocketTimeoutException sex) {
                //this.isRunning = false;
                //servSock.close();
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        HelperUtil.showError("timeout excedido. esperando nueva conexión.");
                    }
                });

            }
        }
        /* NOT REACHED */
    }

    public void stopServer() throws IOException {
        logger.info("Server stoping");
        isRunning = false;
        if (servSock != null) {
            servSock.close();
        }
    }

    @Deprecated
    private void resetServer() throws IOException {
        logger.info("Server restarting");
        isRunning = false;
        servSock.close();
        isRunning = true;
        initServer();

    }

    @Override
    public void run() {
        try {
            this.isRunning = true;
            initServer();
        } catch (java.net.BindException ex) {
            this.isRunning = false;
            HelperUtil.showErrorB("Servidor no pudo iniciarse. a");
            //Logger.getLogger(TCPEchoServerThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            this.isRunning = false;
            HelperUtil.showErrorB("Servidor no pudo iniciarse. b");
            //Logger.getLogger(TCPEchoServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
