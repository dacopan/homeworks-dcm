package io.dacopancm.socketdcm.net;

import io.dacopancm.socketdcm.helper.ConectType;
import io.dacopancm.socketdcm.helper.DateUtil;
import io.dacopancm.socketdcm.helper.HelperUtil;
import static io.dacopancm.socketdcm.helper.HelperUtil.*;
import io.dacopancm.socketdcm.model.DCMFile;
import io.dacopancm.socketdcm.view.MainApp;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;

/**
 *
 * @author dtic
 */
public class HandleProtocol implements Runnable {

    private final int BUFSIZE;// Size (in bytes) of I/O buffer
    private final Socket clntSock;               // Socket connect to client
    private final Logger logger;                 // Server logger
    MainApp mainApp;

    public HandleProtocol(Socket clntSock, Logger logger, MainApp mainApp) {
        this.mainApp = mainApp;
        this.clntSock = clntSock;
        this.logger = logger;
        BUFSIZE = HelperUtil.getIntProperty(HelperUtil.SRV_BUFFER);
    }

    private void handleProtocol(Socket clntSock, Logger logger) {

        try {
            // Get the input and output I/O streams from socket           
            DataInputStream dIn = new DataInputStream(clntSock.getInputStream());
            DataOutputStream dOut = new DataOutputStream(clntSock.getOutputStream());
            //leer tipo de envio de dato            
            String type = dIn.readUTF(); //tipo si es chat o archivo o stream
            String compressed = dIn.readUTF(); //conexion usa gzip
            String name = dIn.readUTF(); // nombre archivo /type is chat: nombre usuario/si type is stream: stream method(get_list,send_list,get_stream)
            String format = dIn.readUTF(); // otro dato /si type is stream: streamid         

            if (isValidFormat(format) && isValidType(type)) {
                //detectamos que es lo que vamos a recibir
                if (ConectType.STREAM.name().equalsIgnoreCase(type)) {
                    logger.log(Level.INFO, "server accept stream get list");
                    new StreamSocket(clntSock, name, format, mainApp).handle();
                } else if (ConectType.CHAT.name().equalsIgnoreCase(type)) {
                    //handle chat
                    Platform.runLater(() -> {
                        mainApp.getMainController().requestChatDialog(name, clntSock);
                    });
                } else {
                    dOut.writeUTF(HelperUtil.ACEPT_CODE);
                    dOut.flush(); // Send off the data
                    DCMFile dcmfile = new DCMFile(name, HelperUtil.getProperty(HelperUtil.RECEIVE_FOLDER), format);
                    dcmfile.setDate(LocalDateTime.now());
                    dcmfile.setFolder(HelperUtil.getProperty(HelperUtil.RECEIVE_FOLDER));
                    File f = new File(dcmfile.getPath());
                    if (f.exists()) {
                        dcmfile.setName(dcmfile.getName() + "_" + DateUtil.nowDateJoined());
                    }

                    //dcmsocket
                    clntSock.setSoTimeout(HelperUtil.getIntProperty(HelperUtil.SRV_TIMEOUT));
                    clntSock.setReuseAddress(HelperUtil.getBoolProperty(HelperUtil.SRV_REUSEADDRESS));
                    clntSock.setSoLinger(HelperUtil.getBoolProperty(HelperUtil.CLT_LINGERING), 500);
                    clntSock.setTcpNoDelay(!HelperUtil.getBoolProperty(HelperUtil.CLT_DELAY));
                    clntSock.setReceiveBufferSize(clntSock.getReceiveBufferSize());

                    new Thread(new ClientFileSocket(clntSock, dcmfile, mainApp, compressed.equalsIgnoreCase("compressed"))).start();
                }
                //end handle
            } else {
            }

        } catch (IOException ex) {
            logger.log(Level.WARNING, "Exception in echo protocol", ex);
            try {
                clntSock.close();
            } catch (IOException e) {
            }
        } finally {

        }

    }

    @Override
    public void run() {
        handleProtocol(clntSock, logger);
    }

}
