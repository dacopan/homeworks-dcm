/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.dacopancm.socketdcm.view;

import de.jensd.fx.glyphs.GlyphIcon;
import de.jensd.fx.glyphs.GlyphsBuilder;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcons;
import io.dacopancm.socketdcm.helper.ConectType;
import io.dacopancm.socketdcm.helper.HelperUtil;
import io.dacopancm.socketdcm.model.ListViewStreamCell;
import io.dacopancm.socketdcm.model.StreamFile;
import io.dacopancm.socketdcm.net.StreamSocket;
import io.dacopancm.styles.jmetro8.NumberTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.concurrent.Worker.State;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

/**
 *
 * @author dtic
 */
public class StreamChooserDialogController implements Initializable {

    private Stage dialogStage;
    MainApp mainApp;

    @FXML
    private TextField ipIn;
    @FXML
    private NumberTextField portIn;
    @FXML
    private Button getBut;

    @FXML
    private Label rtspIn;
    @FXML
    private ListView listStreams;
    @FXML
    private Button connectBut;

    @FXML
    HBox progressxWrap;
    @FXML
    ProgressIndicator progressx;

    public StreamChooserDialogController() {

    }

    @Override
    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Sets the stage of this dialog.
     *
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Called when the user clicks cancel.
     */
    @FXML
    public void handleCancel() {
        dialogStage.close();
    }

    /**
     * Called when the user clicks connect stream after select stream on
     * listview.
     */
    @FXML
    private void handleGet() {
        System.out.println("handle get click");
        //Task for computing the Panels:
        getBut.setDisable(true);
        progressxWrap.getStyleClass().clear();
        progressxWrap.getStyleClass().add("visible");
        progressx.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {

                try {
                    ObservableList<StreamFile> streams = new StreamSocket(ipIn.getText(), portIn.getText(), ConectType.GET_STREAMLIST.name(), "", mainApp).getStreamList();
                    Platform.runLater(() -> {
                        listStreams.setItems(streams);
                    });

                } catch (Exception ex) {
                    System.out.println("error task get streams: " + ex.getMessage());
                    HelperUtil.showErrorB("No se pudo obtener lista de streams");
                }
                return null;
            }
        };

        //stateProperty for Task:
        task.stateProperty().addListener((ObservableValue<? extends State> observable, State oldValue, Worker.State newState) -> {
            if (newState == Worker.State.SUCCEEDED) {
                getBut.setDisable(false);
                progressxWrap.getStyleClass().clear();
                progressxWrap.getStyleClass().add("oculto");
                progressx.setProgress(0.25F);
                return;
            }
            if (newState == Worker.State.FAILED) {

            }
        });

        //start Task
        new Thread(task).start();
    }

    /**
     * Called when the user clicks connect stream after select stream on
     * listview.
     */
    @FXML
    private void handleConnect() {
        if (rtspIn.getText().equalsIgnoreCase("RTSP")) {
            HelperUtil.showError("Seleccione un archivo para continuar");
            return;
        }

        System.out.println("handle connect click");
        //Task for computing the Panels:
        connectBut.setDisable(true);
        progressxWrap.getStyleClass().clear();
        progressxWrap.getStyleClass().add("visible");
        progressx.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {

                try {
                    String ip = ipIn.getText(), port = portIn.getText();
                    String resp = new StreamSocket(ip, port, ConectType.GET_STREAM.name(), streamid, mainApp).getStreamFile();

                    if (!resp.contains("false")) {
                        Platform.runLater(() -> {
                            //load player dialog
                            try {
                                // Load the fxml file and create a new stage for the popup dialog.
                                FXMLLoader loader = new FXMLLoader();
                                loader.setLocation(MainApp.class.getResource("PlayerDialog.fxml"));
                                StackPane page = (StackPane) loader.load();

                                // Create the dialog Stage.
                                Stage dialogStage = new Stage();
                                //dialogStage.setTitle("Edit Person");
                                dialogStage.initModality(Modality.APPLICATION_MODAL);
                                dialogStage.initOwner(mainApp.getPrimaryStage());
                                dialogStage.initStyle(StageStyle.UNDECORATED);
                                Scene scene = new Scene(page);
                                dialogStage.setScene(scene);
                                dialogStage.setWidth(mainApp.getPrimaryStage().getWidth());
                                dialogStage.setHeight(500);
                                Stage stage = mainApp.getPrimaryStage();
                                dialogStage.setX(stage.getX() + stage.getWidth() / 2 - dialogStage.getWidth() / 2);
                                dialogStage.setY(stage.getY() + stage.getHeight() / 2 - dialogStage.getHeight() / 2);

                                double y = stage.getY();
                                // Set the person into the controller.
                                PlayerDialogController controller = loader.getController();
                                controller.setDialogStage(dialogStage);
                                String mrl = HelperUtil.formatRtspStreamClient(ip, resp, streamid);
                                controller.setMainApp(mainApp, mrl,isAudio);
                                controller.initx();

                                // Show the dialog and wait until the user closes it
                                dialogStage.showAndWait();

                            } catch (IOException e) {
                                e.printStackTrace();
                                HelperUtil.showErrorB("Error al abrir el stream");
                            }

                        });
                    } else {
                        HelperUtil.showErrorB("No se pudo obtener el stream");
                    }
                } catch (Exception ex) {
                    System.out.println("error task get streams: " + ex.getMessage());
                    HelperUtil.showErrorB("No se pudo recibir el stream");
                }
                return null;
            }
        };

        //stateProperty for Task:
        task.stateProperty().addListener((ObservableValue<? extends State> observable, State oldValue, Worker.State newState) -> {
            if (newState == Worker.State.SUCCEEDED) {
                connectBut.setDisable(false);
                progressxWrap.getStyleClass().clear();
                progressxWrap.getStyleClass().add("oculto");
                progressx.setProgress(0.25F);
                dialogStage.close();

                return;
            }
            if (newState == Worker.State.FAILED) {

            }
        });

        //start Task
        new Thread(task).start();
    }

    public void initx() {
        ipIn.textProperty().addListener((observable, oldValue, newValue) -> {
            boolean v = HelperUtil.isValidIP(newValue) && HelperUtil.isValidPort(portIn.getText());
            getBut.setDisable(!v);
        });
        portIn.textProperty().addListener((observable, oldValue, newValue) -> {
            boolean v = HelperUtil.isValidPort(newValue) && HelperUtil.isValidIP(ipIn.getText());
            getBut.setDisable(!v);
        });
        getBut.setDisable(true);
        connectBut.setDisable(true);

        rtspIn.textProperty().addListener((observable, oldValue, newValue) -> {
            boolean v = rtspIn.getText().equalsIgnoreCase("RTSP");
            connectBut.setDisable(v);
        });

        String ms = "esto esta vacío, Ningún stream disponible";
        Label l = new Label(ms);
        l.getStyleClass().add("header");
        l.setStyle("-fx-font-size:28px;-fx-text-fill:#c1c1c1;");
        l.setWrapText(true);

        GlyphIcon iServer = GlyphsBuilder.create(FontAwesomeIcon.class).glyph(FontAwesomeIcons.TINT).build();
        iServer.setSize("30px");
        iServer.setFill(Paint.valueOf("white"));

        l.setGraphic(iServer);
        this.listStreams.setPlaceholder(l);

        listStreams.setCellFactory(new Callback<ListView<StreamFile>, javafx.scene.control.ListCell<StreamFile>>() {
            @Override
            public ListCell<StreamFile> call(ListView<StreamFile> param) {
                return new ListViewStreamCell();
            }
        });

        listStreams.setOnMouseClicked((MouseEvent event) -> {
            StreamFile item = (StreamFile) listStreams.getSelectionModel().getSelectedItem();

            this.rtspIn.setText(HelperUtil.formatRtspStreamClient(ipIn.getText(), HelperUtil.VLC_SRV_PORT, item.getIdx()));
            streamid = item.getIdx();
            isAudio = item.getPath().contains("mp3");

        });
        progressxWrap.getStyleClass().add("oculto");
    }

    String streamid;
    boolean isAudio;

    void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

}
