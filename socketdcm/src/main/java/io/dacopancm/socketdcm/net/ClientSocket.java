/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.dacopancm.socketdcm.net;

import io.dacopancm.socketdcm.helper.HelperUtil;
import io.dacopancm.socketdcm.view.ChatTabController;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;

/**
 *
 * @author dtic
 */
public class ClientSocket implements Runnable {

    private Socket sock;
    private String ip;
    private int port;
    private ChatTabController controller;
    private boolean isServer;
    boolean isAcepted;
    private String clt_username;
    private final String srv_username;
    private final Logger logger;

    /**
     * constructor si es cliente
     *
     * @param key
     * @param value
     * @param controller
     */
    public ClientSocket(String key, String value, ChatTabController controller) {
        this.ip = key;
        this.port = Integer.parseInt(value);
        this.controller = controller;
        this.srv_username = HelperUtil.getProperty(HelperUtil.SRV_USERNAME);
        logger = Logger.getLogger("practical");
    }

    /**
     * *
     * constructor si es server
     *
     * @param controller
     * @param soc
     * @param isAcepted
     * @param name
     */
    public ClientSocket(ChatTabController controller, Socket soc, boolean isAcepted, String name) {
        this.controller = controller;
        this.isServer = true;
        this.sock = soc;
        this.isAcepted = isAcepted;
        this.srv_username = HelperUtil.getProperty(HelperUtil.SRV_USERNAME);
        this.clt_username = name;
        controller.setUsername(name);
        logger = Logger.getLogger("practical");

    }

    /**
     * maneja lo referente al chat, si es servidor, sino crea wl socket del
     * cliente y envia la peticion al server, luego llama a @see manageChat
     */
    public void handleChat() {
        try {
            if (isServer) {
                DataOutputStream dOut = new DataOutputStream(sock.getOutputStream());
                DataInputStream dIn = new DataInputStream(sock.getInputStream());
                if (isAcepted) {
                    addMessageTS("contectado con " + clt_username);
                    dOut.writeUTF(HelperUtil.ACEPT_CODE);
                    dOut.writeUTF(this.srv_username);
                    dOut.flush();
//                    dOut.close();
//                    dIn.close();
                    manageChat();
                } else {
                    //server no acepto conexion, notifico a cliente y cierro socket
                    dOut.writeUTF(HelperUtil.DENIED_CODE);
                    dOut.flush();
                    dOut.close();
                    dIn.close();
                    sock.close();
                    controller.getMainApp().removeClientThread(this);
                }

            } else {

                // Create socket connected to server on specified port
                sock = new Socket(ip, port);

                DataOutputStream dOut = new DataOutputStream(sock.getOutputStream());
                DataInputStream dIn = new DataInputStream(sock.getInputStream());

                dOut.writeUTF("CHAT");
                dOut.writeUTF("uncompressed");
                dOut.writeUTF(srv_username);
                dOut.writeUTF("dcm");
                dOut.flush(); // Send off the data

                addMessageT("Estableciendo conexión con " + ip + ":" + port);

                String resp = dIn.readUTF();
                if (resp.equalsIgnoreCase(HelperUtil.ACEPT_CODE)) {
                    this.clt_username = dIn.readUTF();
                    Platform.runLater(() -> {
                        controller.setUsername(clt_username);
                        controller.addMessage("Conexión aceptada con: " + ip + ":" + port + "/" + clt_username);
                    });
                    //dOut.flush();
                    //dOut.close();
                    //dIn.close();
                    manageChat();
                } else {
                    addMessageT("Conexion no aceptada.");
                    controller.getMainApp().removeClientThread(this);
                    dOut.close();
                    dIn.close();
                    sock.close();
                }

            }
            //new Tab().setGraphic();
        } catch (IOException ex) {
            HelperUtil.showErrorB("No se pudo establecer conexiónx");
            addMessageT("No se pudo establecer conexión.");
            try {
                if (sock != null) {
                    sock.close();
                }
            } catch (IOException e) {
            }
        }
    }
    ObjectOutputStream out;
    ObjectInputStream in;

    /**
     * maneja los streams del socket recibe los mensajes y los ãnade a la vista
     * llamando a @addMessageT si el mensaje recibido es <code>exit</code>
     * cierra el buffer y el socket
     */
    private void manageChat() {

        try {
            //sock.setSoTimeout(port);
            //ObjectOutputStream out;

            String message;
//3. Entrada salida
            out = new ObjectOutputStream(sock.getOutputStream());
            out.flush();
            in = new ObjectInputStream(sock.getInputStream());

            //4. las dos partes se comunican via entrada salida
            do {

                message = (String) in.readObject();
                addMessageT(message);
                if (message.equals("exit")) {
                    out.writeObject("exit");
                    out.flush();
                }

            } while (!message.equals("exit"));
        } catch (SocketException ex) {
            Logger.getLogger(ClientSocket.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ClientSocket.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ClientSocket.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //Cerrar conexion
            try {
                in.close();
                out.close();
                sock.close();
                controller.getMainApp().removeClientThread(this);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        handleChat();
    }

    public void sendMessagge(String msg) {
        try {
            if (sock.isConnected()) {
                out.writeObject(msg);
                out.flush();
            } else {
                HelperUtil.showErrorB("mensaje no enviado, sin conexión");
            }
        } catch (IOException | NullPointerException ioException) {
            HelperUtil.showErrorB("mensaje no enviado");
        }
    }

    /**
     * ss
     */
    public void terminateChat() {
        if (sock != null && sock.isConnected()) {
            sendMessagge("exit");
        }
    }

    private void addMessageT(String msg) {
        Platform.runLater(() -> {
            controller.addMessage(msg);
        });
    }

    private void addMessageTS(String msg) {
        Platform.runLater(() -> {
            controller.addServerMessage(msg);
        });
    }
}
