/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.dacopancm.socketdcm.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import de.jensd.fx.glyphs.GlyphIcon;
import de.jensd.fx.glyphs.GlyphsBuilder;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcons;
import io.dacopancm.socketdcm.model.StreamFile;
import io.dacopancm.styles.jmetro8.Notifications;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;
import javafx.application.Platform;
import static sun.misc.ThreadGroupUtils.getRootThreadGroup;

/**
 *
 * @author dtic
 */
public class HelperUtil {

    public static final String LOCAL_FOLDER = "dcm.folder";
    public static final String TMP_FOLDER = "dcm.folder.tmp";
    public static final String AUDIO_FOLDER = "dcm.folder.audio";
    public static final String RECEIVE_FOLDER = "dcm.folder.receive";
    public static final String TMP_FILE = "dcm.file.tmp";
    public static final String AUDIO_FORMAT = "dcm.audio.format";

    public static final String ACEPT_CODE = "202";
    public static final String ERROR_CODE = "404";
    public static final String DENIED_CODE = "401";

    public static final String CLT_KEEPALIVE = "dcm.clt.keepalive";
    public static final String CLT_BUFFER = "dcm.clt.buffer";
    public static final String CLT_TIMEOUT = "dcm.clt.timeout";
    public static final String CLT_REUSEADDRESS = "dcm.clt.reuseaddress";
    public static final String CLT_DELAY = "dcm.clt.delay";
    public static final String CLT_URGEN = "dcm.clt.urgen";
    public static final String CLT_LINGERING = "dcm.clt.lingering";
    public static final String CLT_BROADCAST = "dcm.clt.broadcast";
    public static final String CLT_GZIP = "dcm.clt.gzip";
    public static final String CLT_USERNAME = "dcm.clt.name";

    public static final String SRV_BUFFER = "dcm.srv.buffer";
    public static final String SRV_TIMEOUT = "dcm.srv.timeout";
    public static final String SRV_REUSEADDRESS = "dcm.srv.reuseaddress";
    public static final String SRV_PORT = "dcm.srv.port";
    public static final String SRV_USERNAME = "dcm.srv.name";

    public static String VLC_FOLDER = "VLC";
    public static String VLC_SRV_PORT = "5004";
    public static String VLC_SRV_IP = "0.0.0.0";

    /*
     * Determines if a byte array is compressed. The java.util.zip GZip
     * implementaiton does not expose the GZip header so it is difficult to determine
     * if a string is compressed.
     * 
     * @param bytes an array of bytes
     * @return true if the array is compressed or false otherwise
     * @throws java.io.IOException if the byte array couldn't be read
     */
    public static boolean isCompressed(byte[] bytes) throws IOException {
        if ((bytes == null) || (bytes.length < 2)) {
            return false;
        } else {
            return ((bytes[0] == (byte) (GZIPInputStream.GZIP_MAGIC)) && (bytes[1] == (byte) (GZIPInputStream.GZIP_MAGIC >> 8)));
        }
    }

    public static boolean isValidFormat(String format) throws IOException {
        return true;
    }

    public static boolean isValidType(String type) throws IOException {
        return true;
    }
    public static Properties properties;

    public static boolean getBoolProperty(String key) {
        return Boolean.parseBoolean(properties.getProperty(key));
    }

    public static int getIntProperty(String key) {
        String v = properties.getProperty(key);
        return Integer.parseInt(v);
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    public static void setDefaultPropierties() {
        setServerDefaultPropierties();
        setClientDefaultPropierties();
    }

    public static void setServerDefaultPropierties() {
        properties.setProperty(HelperUtil.SRV_BUFFER, "32");
        properties.setProperty(HelperUtil.SRV_TIMEOUT, "0");
        properties.setProperty(HelperUtil.SRV_REUSEADDRESS, "true");
        properties.setProperty(HelperUtil.SRV_PORT, "40666");
        properties.setProperty(HelperUtil.SRV_USERNAME, "uriel");
    }

    public static void setClientDefaultPropierties() {
        properties.setProperty(HelperUtil.CLT_KEEPALIVE, "true");
        properties.setProperty(HelperUtil.CLT_BUFFER, "32");
        properties.setProperty(HelperUtil.CLT_TIMEOUT, "0");
        properties.setProperty(HelperUtil.CLT_REUSEADDRESS, "true");
        properties.setProperty(HelperUtil.CLT_DELAY, "false");
        properties.setProperty(HelperUtil.CLT_URGEN, "false");
        properties.setProperty(HelperUtil.CLT_LINGERING, "false");
        properties.setProperty(HelperUtil.CLT_BROADCAST, "false");
        properties.setProperty(HelperUtil.CLT_GZIP, "false");
    }

    public static void showErrorB(String text) {
        Platform.runLater(() -> {
            showError(text);
        });
    }

    public static void showInfoB(String text) {
        Platform.runLater(() -> {
            showInfo(text);
        });
    }

    public static void showError(String text) {
        GlyphIcon i = GlyphsBuilder.create(FontAwesomeIcon.class).glyph(FontAwesomeIcons.WARNING)
                .size("45px").styleClass("noti-icon").build();
        Notifications.create().graphic(i)
                .title("Error")
                .text(text)
                .show();
    }

    public static void showInfo(String t) {
        GlyphIcon i = GlyphsBuilder.create(FontAwesomeIcon.class).glyph(FontAwesomeIcons.INFO_CIRCLE)
                .size("45px").styleClass("noti-icon").build();
        Notifications.create()
                .title("Info").graphic(i)
                .text(t)
                .show();
    }

    public static void showInfo(String t, String c) {
        GlyphIcon i = GlyphsBuilder.create(FontAwesomeIcon.class).glyph(FontAwesomeIcons.INFO_CIRCLE)
                .size("45px").styleClass("noti-icon").build();
        Notifications.create()
                .title(t).graphic(i)
                .text(c)
                .show();
    }

    public static boolean isValidIP(String newValue) {
        String regex = "\\b((25[0–5]|2[0–4]\\d|[01]?\\d\\d?)(\\.)){3}(25[0–5]|2[0–4]\\d|[01]?\\d\\d?)\\b";
        return Pattern.matches(regex, newValue);
    }

    public static boolean isValidPort(String newValue) {
        try {
            if (Integer.parseInt(newValue) < 65536) {
                return true;
            }
        } catch (NumberFormatException ex) {
        }
        return false;
    }

    // return true if the input field is valid, based on the parameter passed
    private static final String STREAMID_REGEX = "^[a-zA-Z0-9_]*$";

    public static boolean isValidStreamId(String t) {
        return isValidText(t, STREAMID_REGEX);
    }

    public static boolean isValidText(String text, String regex) {
        // text required and editText is blank, so return false
        if (text.equalsIgnoreCase("")) {
            return false;
        }
        return Pattern.matches(regex, text);
    }

    public static String formatRtspStream(String serverAddress, int serverPort, String id) {
        StringBuilder sb = new StringBuilder(60);
        sb.append(":sout=#rtp{sdp=rtsp://@");
        sb.append(serverAddress);
        sb.append(':');
        sb.append(serverPort);
        sb.append('/');
        sb.append(id);
        sb.append("}");
        return sb.toString();
    }

    public static String formatRtspStreamClient(String serverAddress, String serverPort, String id) {
        StringBuilder sb = new StringBuilder(60);
        sb.append("rtsp://@");
        sb.append(serverAddress);
        sb.append(':');
        sb.append(serverPort);
        sb.append('/');
        sb.append(id);
        // sb.append("}");
        return sb.toString();
    }

    public static String getArch() {
//        boolean is64bit;
//        if (System.getProperty("os.name").contains("Windows")) {
//            is64bit = (System.getenv("ProgramFiles(x86)") != null);
//        } else {
//            is64bit = (System.getProperty("os.arch").contains("64"));
//        }
        boolean is64bit = System.getProperty("sun.arch.data.model").contains("64");
        return is64bit ? "64" : "32";
    }

    public static List<StreamFile> fromJSON(final String jsonPacket) {
        List<StreamFile> data = null;
        try {
            data = new ObjectMapper().readValue(jsonPacket,
                    TypeFactory.defaultInstance().constructCollectionType(List.class,
                            StreamFile.class));
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

    public static Thread[] getAllThreads() {
        final ThreadGroup root = getRootThreadGroup();
        final ThreadMXBean thbean = ManagementFactory.getThreadMXBean();
        int nAlloc = thbean.getThreadCount();
        int n = 0;
        Thread[] threads;
        do {
            nAlloc *= 2;
            threads = new Thread[nAlloc];
            n = root.enumerate(threads, true);
        } while (n == nAlloc);
        return java.util.Arrays.copyOf(threads, n);
    }

    public static void PrintAllThreads() {
        Logger logger = Logger.getLogger("practical");
        Thread[] ths = HelperUtil.getAllThreads();
        logger.log(Level.WARNING, "Thread activos {0}", ths.length);
        for (Thread th : ths) {
            //logger.log(Level.INFO, "Thread activo {0},{1}", new Object[]{th.getId(), th.getName()});
        }
    }
}
