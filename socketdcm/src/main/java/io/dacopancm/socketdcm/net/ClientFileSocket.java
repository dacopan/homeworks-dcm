/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.dacopancm.socketdcm.net;

import io.dacopancm.socketdcm.helper.ConectType;
import io.dacopancm.socketdcm.helper.HelperUtil;
import io.dacopancm.socketdcm.model.DCMFile;
import io.dacopancm.socketdcm.view.MainApp;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 *
 * @author dtic
 */
public class ClientFileSocket implements Runnable {

    private Socket sock;
    private String ip;
    private int port;
    private final String file;
    private final Logger logger;
    private final MainApp mainApp;
    private final boolean isSendMetodh;
    private final boolean isCompressed;
    private DCMFile fileView;

    /**
     * constructor de ClientFileSocket si es para envio
     *
     * @param ip ip
     * @param port puerto
     * @param file path archivo a enviar
     * @param mainApp refrencia a mainApp
     */
    public ClientFileSocket(final String ip, String port, String file, MainApp mainApp) {
        this.ip = ip;
        this.port = Integer.parseInt(port);
        this.file = file;
        isSendMetodh = true;
        this.mainApp = mainApp;
        logger = Logger.getLogger("practical");
        this.isCompressed = HelperUtil.getBoolProperty(HelperUtil.CLT_GZIP);
    }

    /**
     * constructor de ClientFileSocket
     *
     * @param sock socket del cliente
     * @param fileView model de vista para archivo a recibir
     * @param mainApp refrencia a mainApp
     * @param isCompresed si usa gzip o no
     */
    public ClientFileSocket(Socket sock, DCMFile fileView, MainApp mainApp, boolean isCompresed) {
        this.isCompressed = isCompresed;
        this.sock = sock;
        this.fileView = fileView;
        this.file = fileView.getPath();
        isSendMetodh = false;
        this.mainApp = mainApp;
        logger = Logger.getLogger("practical");
    }

    @Override
    public void run() {
        handleFile();
    }

    /**
     * determina si es envio o recepcion y guarda o envia segun corresponda
     */
    private void handleFile() {

        try {
            if (!isSendMetodh) {
                //logica para recibir archivo
                //fileView = mainApp.addFileSend(file, ip, port);
                mainApp.addFileReceive(fileView);
                //meta archivo donde se guardara lo recibido                
                InputStream in = null;
                if (isCompressed) {
                    //handleEchoClient(clntSock, logger, filet);
                    in = new GZIPInputStream(sock.getInputStream());
                } else {
                    //handleCompressClient(clntSock, logger, filet);
                    in = sock.getInputStream();
                }

                fileView.setState("descargando");
                //handle
                // Get the input and output I/O streams from socket
                //archivo fisico donde se guardara lo recibido
                File f = new File(fileView.getPath());
                f.createNewFile();

                //stream archivo fisico donde se guardara lo recibido
                FileOutputStream out = new FileOutputStream(f);

                int recvMsgSize; // Size of received message
                int totalBytesEchoed = 0; // Bytes received from client
                byte[] echoBuffer = new byte[HelperUtil.getIntProperty(HelperUtil.CLT_BUFFER)]; // Receive Buffer
                // Receive until client closes connection, indicated by -1
                while ((recvMsgSize = in.read(echoBuffer)) != -1) {
                    out.write(echoBuffer, 0, recvMsgSize);
                    totalBytesEchoed += recvMsgSize;
                }

                out.close();
                in.close();
                fileView.setState("recebido");
                HelperUtil.showInfoB("archivo recibido " + fileView.getName());
                logger.log(Level.INFO, "Client {0}, echoed {1} bytes.", new Object[]{sock.getRemoteSocketAddress(), totalBytesEchoed});

            } else {
                //logica para enviar archivo
                // Create socket connected to server on specified port
                fileView = mainApp.addFileSend(file, ip, port);
                sock = new Socket(ip, port);

                //dcmsocket
                sock.setSoTimeout(HelperUtil.getIntProperty(HelperUtil.CLT_TIMEOUT));
                sock.setReuseAddress(HelperUtil.getBoolProperty(HelperUtil.SRV_REUSEADDRESS));
                sock.setSoLinger(HelperUtil.getBoolProperty(HelperUtil.CLT_LINGERING), 500);
                sock.setTcpNoDelay(!HelperUtil.getBoolProperty(HelperUtil.CLT_DELAY));
                sock.setSendBufferSize(HelperUtil.getIntProperty(HelperUtil.CLT_BUFFER));

                DataOutputStream dOut = new DataOutputStream(sock.getOutputStream());
                DataInputStream dIn = new DataInputStream(sock.getInputStream());

                dOut.writeUTF(ConectType.AUDIO.name());//send audio type
                dOut.writeUTF(isCompressed ? "compressed" : "uncompressed");//send comprimido o no

                dOut.writeUTF(fileView.getName()); //send 
                dOut.writeUTF(fileView.getFormat());
                dOut.flush(); // Send off the data

                HelperUtil.showInfoB(String.format("Enviando %s a %s:%d", fileView.getName() + fileView.getFormat(), ip, port));

                String resp = dIn.readUTF();
                if (resp.equalsIgnoreCase(HelperUtil.ACEPT_CODE)) {

                    OutputStream out = null;
                    FileInputStream fileIn = new FileInputStream(file);
                    if (isCompressed) {
                        out = new GZIPOutputStream(sock.getOutputStream());
                    } else {
                        out = sock.getOutputStream();
                    }
                    int bytesRead;                      // Number of bytes read
                    byte[] buffer = new byte[HelperUtil.getIntProperty(HelperUtil.CLT_BUFFER)];  // Byte buffer
                    while ((bytesRead = fileIn.read(buffer)) != -1) {
                        out.write(buffer, 0, bytesRead);
                    }
                    fileView.setState("enviado");
                    fileIn.close();
                    sock.shutdownOutput();

                } else {
                    fileView.setState("cancelado");
                }
            }
        } catch (IOException ex) {
            HelperUtil.showErrorB("Error en la transmisión de: " + file);
            try {
                if (sock != null) {
                    sock.close();

                }
                if (fileView != null) {
                    fileView.setState("cancelado");
                }
            } catch (IOException e) {
                if (fileView != null) {
                    fileView.setState("cancelado");
                }
            }
        } finally {
            try {
                if (sock != null) {
                    sock.close();
                }
            } catch (IOException e) {
            }
        }
    }
}
