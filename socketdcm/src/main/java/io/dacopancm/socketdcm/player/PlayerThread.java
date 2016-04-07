/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.dacopancm.socketdcm.player;

import io.dacopancm.socketdcm.helper.HelperUtil;
import io.dacopancm.socketdcm.model.StreamFile;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import uk.co.caprica.vlcj.binding.internal.libvlc_log_level_e;
import uk.co.caprica.vlcj.log.LogEventListener;
import uk.co.caprica.vlcj.log.NativeLog;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.manager.MediaManager;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

/**
 *
 * @author dtic
 */
public class PlayerThread implements Runnable {

    boolean running;
    String playerId;
    String ip;
    String port;

    Logger logger;
    MediaManager manager;
    MediaPlayerFactory factory;
    NativeLog log;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public PlayerThread(String ip, String port) {
        logger = Logger.getLogger("practical");
        this.port = port;
        this.ip = ip;
    }

    boolean server;

    public boolean isServer() {
        return server;
    }

    public void setServer(boolean server) {
        this.server = server;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public boolean isRunning() {
        return running;
    }

    public void endPlayer() {
        running = false;
    }

    @Override
    public void run() {
        initPlayer();
    }

    private void initPlayer() {
        running = true;
        if (isServer()) {

            List<String> vlcArgs = new ArrayList<String>();

            vlcArgs.add("--rtsp-host=" + ip);
            vlcArgs.add("--rtsp-port=" + port);
            vlcArgs.add("-vvv");
            vlcArgs.add("--no-snapshot-preview");
            vlcArgs.add("--quiet");
            vlcArgs.add("--quiet-synchro");
            vlcArgs.add("--intf");
            vlcArgs.add("dummy");

            // Special case to help out users on Windows (supposedly this is not actually needed)...
            if (RuntimeUtil.isWindows()) {
                vlcArgs.add("--plugin-path=" + HelperUtil.VLC_FOLDER + "\\plugins");
            }
            // The host and port options are REQUIRED for video-on-demand
            factory = new MediaPlayerFactory(vlcArgs.toArray(new String[vlcArgs.size()]));

            manager = factory.newMediaManager();

            // This latch is used simply to cleanly exit the application when the
            // "finished" event is raised
            final CountDownLatch latch = new CountDownLatch(1);

            log = factory.newLog();
            if (log == null) {
                System.out.println("Native log not available on this platform");
                System.exit(1);
            }

            log.setLevel(libvlc_log_level_e.DEBUG);
            LogEventListener levl = new LogEventListener() {
                @Override
                public void log(libvlc_log_level_e level, String module, String file, Integer line, String name, String header, Integer id, String message) {
                    System.out.printf("a [%-20s] (%-20s) %7s: %s\n", module, name, level, message);
                }
            };
            log.addLogListener(levl);

//            String vodMux = "mp2t";
////        String mux = "ts";
//
//            // Client MRL: rtsp://@127.0.0.1:5004/Movie1
//            manager.addVideoOnDemand("Movie1", "/movies/Movie1.iso", true, vodMux);
//
//            // Client MRL: rtsp://@127.0.0.1:5004/Movie2
//            manager.addVideoOnDemand("Movie2", "/movies/Movie2.iso", true, vodMux);
//
//            System.out.println("Movie1: " + manager.show("Movie1"));
//            System.out.println("Movie2: " + manager.show("Movie2"));
            try {
                while (running) {
                    Thread.sleep(500);
                }

                log.setLevel(libvlc_log_level_e.ERROR);
                log.release();

                manager.release();
                factory.release();
                //log.release();
                // Must release the components to exit (otherwise threads are left running)

            } catch (InterruptedException ex) {
                Logger.getLogger(PlayerThread.class.getName()).log(Level.SEVERE, null, ex);
            }
            logger.log(Level.INFO, "server player end");
            log.removeLogListener(levl);

        } else {
        }
    }

    public boolean addVideo(StreamFile file) {
        String vodMux = "mp2t";
        //boolean resp = manager.addVideoOnDemand(file.getIdx(), file.getPath(), true, vodMux,":sout=#transcode{vcodec=h264,acodec=mpga,ab=128,channels=2,samplerate=44100}");

        boolean resp = manager.addVideoOnDemand(file.getIdx(), file.getPath(), true, null);

        //boolean resp = true;
        //System.out.println(file.getIdx() + " " + manager.show(file.getIdx()));
        return resp;
    }

    public boolean removeVideo() {
        return false;
    }

    public void off(ObservableList<StreamFile> streamFileList) {
        //running = false;        
        for (StreamFile str : streamFileList) {
            if (!manager.removeMedia(str.getIdx())) {
                //HelperUtil.showErrorB("Error eliminando media");
            }
        }

        running = false;
    }
}
