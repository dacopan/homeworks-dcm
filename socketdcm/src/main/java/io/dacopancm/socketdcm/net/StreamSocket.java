/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.dacopancm.socketdcm.net;

import com.fasterxml.jackson.core.type.TypeReference;
import io.dacopancm.socketdcm.helper.ConectType;
import io.dacopancm.socketdcm.helper.HelperUtil;
import io.dacopancm.socketdcm.model.StreamFile;
import io.dacopancm.socketdcm.player.PlayerThread;
import io.dacopancm.socketdcm.view.MainApp;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author dtic
 */
public class StreamSocket implements Runnable {

    private final MainApp mainApp;
    private Socket sock;
    private String ip;
    private int port;
    private final String method;
    private final String streamid;
    private final Logger logger;

    //to client
    public StreamSocket(String ip, String port, String method, String streamid, MainApp app) {
        this.mainApp = app;
        this.ip = ip;
        this.port = Integer.parseInt(port);
        this.method = method;
        this.streamid = streamid;
        logger = Logger.getLogger("practical");
    }

//si es server
    public StreamSocket(Socket soc, String method, String streamid, MainApp app) {
        this.mainApp = app;
        this.method = method;
        this.sock = soc;
        this.streamid = streamid;
        logger = Logger.getLogger("practical");
    }

    public void handle() {
        try {

            if (ConectType.GET_STREAMLIST.name().equalsIgnoreCase(method)) {
                DataOutputStream dOut = new DataOutputStream(sock.getOutputStream());
                DataInputStream dIn = new DataInputStream(sock.getInputStream());

                String files = HelperUtil.toJSON(mainApp.getStreamFilesList().toArray());

                dOut.writeUTF(files);
                dOut.flush(); // Send off the data
                dOut.close();
            } else if (ConectType.GET_STREAM.name().equalsIgnoreCase(method)) {
                DataOutputStream dOut = new DataOutputStream(sock.getOutputStream());
                DataInputStream dIn = new DataInputStream(sock.getInputStream());

                boolean player = mainApp.playStream(streamid);

                dOut.writeUTF("" + (player ? HelperUtil.VLC_SRV_PORT : player));
                dOut.flush(); // Send off the data
                dOut.close();

            }
        } catch (IOException ex) {
            HelperUtil.showErrorB("No se pudo establecer conexión");
            try {
                if (sock != null) {
                    sock.close();
                }
            } catch (IOException e) {
            }
        }
    }

    @Override
    public void run() {
        handle();
    }

    public ObservableList<StreamFile> getStreamList() {
        ObservableList<StreamFile> list = FXCollections.observableArrayList();
        try {

            if (ConectType.GET_STREAMLIST.name().equalsIgnoreCase(method)) {
                logger.log(Level.INFO, "get stream list call");
                sock = new Socket(ip, port);

                DataOutputStream dOut = new DataOutputStream(sock.getOutputStream());
                DataInputStream dIn = new DataInputStream(sock.getInputStream());

                dOut.writeUTF(ConectType.STREAM.name());//send stream type
                dOut.writeUTF("uncompressed");//send comprimido o no

                dOut.writeUTF(ConectType.GET_STREAMLIST.toString()); //send type
                dOut.writeUTF("mayra");
                dOut.flush(); // Send off the data
                System.out.println("Request list send");
                String resp = dIn.readUTF();
                dOut.close();
                logger.log(Level.INFO, "resp get streamlist: {0}", resp);
                List<StreamFile> files = HelperUtil.fromJSON(resp);
                list.addAll(files);

            }
        } catch (IOException ex) {
            HelperUtil.showErrorB("No se pudo establecer conexión");
            logger.log(Level.INFO, "get streamlist error no connect:{0}", ex.getMessage());
            try {
                if (sock != null) {
                    sock.close();
                }
            } catch (IOException e) {
            }

        }
        return list;
    }

    public String getStreamFile() {
        String list = "false";
        try {

            if (ConectType.GET_STREAM.name().equalsIgnoreCase(method)) {
                logger.log(Level.INFO, "get stream socket call");
                sock = new Socket(ip, port);

                DataOutputStream dOut = new DataOutputStream(sock.getOutputStream());
                DataInputStream dIn = new DataInputStream(sock.getInputStream());

                dOut.writeUTF(ConectType.STREAM.name());//send stream type
                dOut.writeUTF("uncompressed");//send comprimido o no

                dOut.writeUTF(ConectType.GET_STREAM.toString()); //send type
                dOut.writeUTF(streamid);
                dOut.flush(); // Send off the data

                String rtspId = dIn.readUTF();
                dOut.close();
                logger.log(Level.INFO, "resp get stream file: {0}", rtspId);
                list = rtspId;

            }
        } catch (IOException ex) {
            HelperUtil.showErrorB("No se pudo establecer conexión");
            logger.log(Level.INFO, "get stream file error no connect:{0}", ex.getMessage());
            try {
                if (sock != null) {
                    sock.close();
                }
            } catch (IOException e) {
            }

        }
        return list;

    }
}
