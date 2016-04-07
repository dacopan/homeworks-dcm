/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.dacopancm.socketdcm.view;

import com.sun.jna.NativeLibrary;
import io.dacopancm.socketdcm.helper.HelperUtil;
import io.dacopancm.socketdcm.model.DCMFile;
import io.dacopancm.socketdcm.model.StreamFile;
import io.dacopancm.socketdcm.net.ClientSocket;
import io.dacopancm.socketdcm.net.TCPEchoServerThread;
import io.dacopancm.socketdcm.player.PlayerThread;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;
import uk.co.caprica.vlcj.runtime.x.LibXUtil;

/**
 *
 * @author dtic
 */
public class MainApp extends Application {

    ArrayList<ClientSocket> chatClientThreads = new ArrayList();
    private ObservableList<DCMFile> sendFilesList = FXCollections.observableArrayList();
    private ObservableList<DCMFile> receiveFilesList = FXCollections.observableArrayList();
    private ObservableList<StreamFile> streamFileList = FXCollections.observableArrayList();
    private PlayerThread playerServer;

    //splashscreen
    private Pane splashLayout;
    private ProgressBar loadProgress;
    private Label progressText;

    private static final int SPLASH_WIDTH = 640;
    private static final int SPLASH_HEIGHT = 420;

    @Override
    public void init() {

//        Font.loadFont(MainApp.class.getResource("SegoeUILight.ttf").toExternalForm(), 30.0);
//        Font.loadFont(MainApp.class.getResource("SegoeUI.ttf").toExternalForm(), 10.0);
//        Font.loadFont(MainApp.class.getResource("SegoeUISemibold.ttf").toExternalForm(), 10.0);
//        Font.loadFont(MainApp.class.getResource("SegoeUILight.ttf").toExternalForm(), 10.0);
        ImageView splash = new ImageView(new Image(MainApp.class.getResourceAsStream("splash.png")));

        loadProgress = new ProgressBar();
        loadProgress.setPrefWidth(307);
        StackPane.setMargin(loadProgress, new Insets(112, 0, 0, 68));

        progressText = new Label("Will find friends for peanuts . . .");
        progressText.setAlignment(Pos.CENTER);
        StackPane.setMargin(progressText, new Insets(160, 0, 0, 50));
        progressText.setStyle("-fx-text-fill:#8bdcf8;");

        splashLayout = new StackPane();
        splashLayout.getStylesheets().add(MainApp.class.getResource("splash.css").toExternalForm());
        splashLayout.getChildren().addAll(splash, loadProgress, progressText);
        splashLayout.setStyle("-fx-background-color:transparent;");

        //splashLayout.setEffect(new DropShadow());
    }

    @Override
    public void start(final Stage initStage) throws Exception {
        final Task<ObservableList<String>> friendTask;
        friendTask = new Task<ObservableList<String>>() {
            @Override
            protected ObservableList<String> call() throws InterruptedException {
                ObservableList<String> foundFriends
                        = FXCollections.<String>observableArrayList();
                ObservableList<String> availableFriends
                        = FXCollections.observableArrayList(
                                "Recalibración del motor de motivación",
                                "Hiperactivando sockets",
                                "Reprogramando angeles",
                                "Creando sockets pintorescos",
                                "Calibrando la personalidad matriz",
                                "Todavía hiperactivando sockets",
                                "Generando algoritmo de cotorreo",
                                "Difundiendo rumores de sockets vagos",
                                "Cafeinizando el cuerpo estudiantil",
                                "Cargando algoritmo del espíritu académico",
                                "Dinero agotado"
                        );

                //updateMessage("Finding friends . . .");
                //for (int i = 0; i < availableFriends.size(); i++) {
                for (int i = 0; i < 1; i++) {
                    Thread.sleep(400);
                    updateProgress(i + 1, availableFriends.size());
                    String nextFriend = availableFriends.get(i);
                    foundFriends.add(nextFriend);
                    updateMessage(nextFriend);
                }
                Thread.sleep(400);
                //updateMessage("All friends found.");

                return foundFriends;
            }
        };

        showSplash(
                initStage,
                friendTask,
                () -> showMainStage()
        );
        new Thread(friendTask).start();
    }

    private void showSplash(
            final Stage initStage,
            Task<?> task,
            InitCompletionHandler initCompletionHandler
    ) {
        progressText.textProperty().bind(task.messageProperty());
        loadProgress.progressProperty().bind(task.progressProperty());
        task.stateProperty().addListener((observableValue, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {
                loadProgress.progressProperty().unbind();
                loadProgress.setProgress(1);
                initStage.toFront();
                FadeTransition fadeSplash = new FadeTransition(Duration.seconds(1.2), splashLayout);
                fadeSplash.setFromValue(1.0);
                fadeSplash.setToValue(0.0);
                fadeSplash.setOnFinished(actionEvent -> initStage.hide());
                fadeSplash.play();

                initCompletionHandler.complete();
            } // todo add code to gracefully handle other task states.
        });

        Scene splashScene = new Scene(splashLayout);
        splashScene.setFill(null);
        initStage.initStyle(StageStyle.TRANSPARENT);
        initStage.getIcons().add(
                new Image(MainApp.class.getResourceAsStream("icon.png")));
        final Rectangle2D bounds = Screen.getPrimary().getBounds();
        initStage.setScene(splashScene);
        initStage.setX(bounds.getMinX() + bounds.getWidth() / 2 - SPLASH_WIDTH / 2);
        initStage.setY(bounds.getMinY() + bounds.getHeight() / 2 - SPLASH_HEIGHT / 2);
        initStage.show();
    }

    public interface InitCompletionHandler {

        public void complete();
    }
    //end splashscreen    

    /**
     * Returns la lista de archivos recibidos
     *
     * @return
     */
    public ObservableList<DCMFile> getReceiveFilesList() {
        return receiveFilesList;
    }

    public void addClientThread(ClientSocket socketx) {
        chatClientThreads.add(socketx);
    }

    public void removeClientThread(ClientSocket socketx) {
        chatClientThreads.remove(socketx);
    }

    public void addStreamThread(ClientSocket socketx) {
        //streamThreads.add(socketx);
    }

    public void removeStreamThread(ClientSocket socketx) {
        //streamThreads.remove(socketx);
    }

    private Stage primaryStage;
    public BorderPane rootLayout;

    public TCPEchoServerThread ServerThread;
    MainContainerController controller;

    //@Override
    //public void start(Stage primaryStage) {
    private void showMainStage() {
        try {

            //this.primaryStage = primaryStage;
            this.primaryStage = new Stage(StageStyle.DECORATED);
            this.primaryStage.setTitle("Cerberus Socket");
            this.primaryStage.getIcons().add(
                    new Image(MainApp.class.getResourceAsStream("icon.png")));

            initProperties();

            initRootLayout();

            showMainContainer();

            initServer();

            primaryStage.setOnCloseRequest(
                    (e) -> {
                        try {
                            chatClientThreads.stream().forEach((clientSocket) -> {
                                clientSocket.terminateChat();
                            });
                            ServerThread.stopServer();
                            playerServer.off(getStreamFilesList());
                            HelperUtil.PrintAllThreads();

                        } catch (IOException ex) {
                            logger.info("main on close", ex);
                        }

                    }
            );

            //ServerThread.initServer();
        } catch (Exception ex) {
            logger.info("main showMainStage", ex);
        }
    }

    private void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
        }
    }

    /**
     * Shows the person overview inside the root layout.
     */
    public void showMainContainer() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("MainContainer.fxml"));
            AnchorPane personOverview = (AnchorPane) loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(personOverview);
            controller = loader.getController();
            controller.setMainApp(this);

            //FXMLLoader loader = new FXMLLoader();
        } catch (IOException e) {
        }
    }

    /**
     * Returns the main stage.
     *
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void initProperties() {
        HelperUtil.properties = new Properties();
        HelperUtil.setDefaultPropierties();
        String localFolder = "dcmData";
        HelperUtil.properties.setProperty(HelperUtil.LOCAL_FOLDER, "dcmData");
        HelperUtil.properties.setProperty(HelperUtil.TMP_FOLDER, localFolder + File.separator + "tmp");
        HelperUtil.properties.setProperty(HelperUtil.AUDIO_FOLDER, localFolder + File.separator + "voice");
        HelperUtil.properties.setProperty(HelperUtil.RECEIVE_FOLDER, localFolder + File.separator + "receive");
        HelperUtil.properties.setProperty(HelperUtil.TMP_FILE, "0");
        HelperUtil.properties.setProperty(HelperUtil.AUDIO_FORMAT, ".wav");
        new File(HelperUtil.getProperty(HelperUtil.TMP_FOLDER)).mkdirs();
        new File(HelperUtil.getProperty(HelperUtil.AUDIO_FOLDER)).mkdirs();
        new File(HelperUtil.getProperty(HelperUtil.RECEIVE_FOLDER)).mkdirs();

        //load VLC
//        String arch = HelperUtil.getArch();
//
//        String osname = System.getProperty("os.name");
//
//        String userDir = System.getProperty("user.dir");
//        String spliter = System.getProperty("file.separator");
//        String filePath = "C:\\Users\\dtic\\Documents\\GitHub\\NetBeansProjects\\" + "vlc";
//        if (osname.contains("Windows")) {
//            HelperUtil.VLC_FOLDER = filePath + spliter + "win" + spliter + arch;
//            loadVLC();
//        } else if (osname.contains("linux")) {
//            HelperUtil.VLC_FOLDER = filePath + spliter + "linux" + spliter + arch;
//            loadVLC();
//        } else {
//            HelperUtil.showError("sistema/arch no compatible");
//        }
        loadVLC();
    }

    /**
     * load VLC LIB
     */
    private static final Logger logger = LoggerFactory.getLogger(MainApp.class);
    /**
     * Set to true to dump out native JNA memory structures.
     */
    private static final String DUMP_NATIVE_MEMORY = "false";

    static {
        try /**
         * Static initialisation.
         */
        {
            // Initialise Log4J (this is good enough for testing, vlcj depends on log4j only for testing here)
            BasicConfigurator.configure();

            // Safely try to initialise LibX11 to reduce the opportunity for native
            // crashes - this will silently throw an Error on Windows (and maybe MacOS)
            // that can safely be ignored
            LibXUtil.initialise();

            //load VLC
            String arch = HelperUtil.getArch();

            String osname = System.getProperty("os.name");

            String userDir = System.getProperty("user.dir");
            String spliter = System.getProperty("file.separator");
            String filePath = new File(MainApp.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath())
                    .getParentFile().getParentFile().getParentFile().getAbsolutePath() + spliter + "vlc";

            if (osname.contains("Windows")) {
                HelperUtil.VLC_FOLDER = filePath + spliter + "win" + spliter + arch;
                // loadVLC();

                if (null != HelperUtil.VLC_FOLDER) {
                    logger.info("Explicitly adding JNA native library search path: '{}'", HelperUtil.VLC_FOLDER);
                    NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), HelperUtil.VLC_FOLDER);
                }
                System.setProperty("VLC_PLUGIN_PATH", HelperUtil.VLC_FOLDER + spliter + "plugins");
            } else if (osname.contains("nux")) {
                HelperUtil.VLC_FOLDER = filePath + spliter + "linux";

                boolean found = new NativeDiscovery().discover();
                System.out.println("vlcj for linux:" + found);
                // loadVLC();
            } else {
                //HelperUtil.showError("sistema/arch no compatible");
                System.out.println("your os name: " + osname + " IS NOT COMPATIBLE");
            }
            System.out.println("your vlc file path to file DIALOG: "+HelperUtil.VLC_FOLDER);
            //NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), (HelperUtil.VLC_FOLDER + spliter + "plugins" + spliter + "stream_filter"));
            //Native.loadLibrary("libvlc", MainApp.class);
/*            System.out.println(LibVlc.INSTANCE.libvlc_get_version());
            System.out.println(LibVlc.INSTANCE.libvlc_get_changeset());
            System.out.println(LibVlc.INSTANCE.libvlc_get_compiler());*/

            System.setProperty("jna.dump_memory", DUMP_NATIVE_MEMORY);
        } catch (URISyntaxException ex) {
            java.util.logging.Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadVLC() {
        try {
            //NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), HelperUtil.VLC_FOLDER);

            playerServer = new PlayerThread(HelperUtil.VLC_SRV_IP, HelperUtil.VLC_SRV_PORT);
            playerServer.setServer(true);
            new Thread(playerServer).start();

        } catch (Exception ex) {
            System.out.println("error cargando VLC: " + ex.getMessage());
        }
    }

    public void initServer() {
        ServerThread = new TCPEchoServerThread(this);
        new Thread(ServerThread).start();
    }

    public MainContainerController getMainController() {
        return this.controller;
    }

    public DCMFile addFileSend(String file, String ip, int port) {
        File f = new File(file);

        String filename = f.getName();
        int pos = filename.lastIndexOf(".");
        String justName = pos > 0 ? filename.substring(0, pos) : filename;
        //String folder = pos > 0 ? f.getParent().substring(0, pos) : filename;

        DCMFile df = new DCMFile(justName, f.getParent(), filename.replace(justName, "").replace(".", ""));
        df.setDate(LocalDateTime.now());
        df.setState("enviando");
        this.sendFilesList.add(df);
        return df;
    }

    public void addFileReceive(DCMFile df) {
        Platform.runLater(() -> {
            try {
                this.receiveFilesList.add(df);
            } catch (Exception ex) {
            }
        });
    }

    boolean addStreamFile(StreamFile sf) {
        for (StreamFile sfx : streamFileList) {
            if (sfx.getIdx().equalsIgnoreCase(sf.getIdx())) {
                return false;
            }
        }
        this.streamFileList.add(sf);

        return playerServer.addVideo(sf);
    }

    /**
     * Returns la lista de archivos enviados
     *
     * @return
     */
    public ObservableList<DCMFile> getSendFilesList() {
        return sendFilesList;
    }

    /**
     *
     * @return la lista de archivos disponibles para stream on demand
     */
    public ObservableList getStreamFilesList() {
        return this.streamFileList;
    }

    public boolean playStream(String streamid) {
        for (StreamFile sfx : streamFileList) {
            if (sfx.getIdx().equalsIgnoreCase(streamid)) {

                Platform.runLater(() -> {
                    sfx.stepUpEmisiones();
                    sfx.stepUpView();

                });

                return true;
            }
        }

        return false;
    }
}
