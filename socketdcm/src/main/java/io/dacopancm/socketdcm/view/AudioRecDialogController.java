/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.dacopancm.socketdcm.view;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcons;
import io.dacopancm.socketdcm.helper.Cronometer;
import io.dacopancm.socketdcm.helper.HelperUtil;
import io.dacopancm.socketdcm.helper.SimpleAudioRecorder;
import io.dacopancm.socketdcm.net.ClientFileSocket;
import io.dacopancm.styles.jmetro8.NumberTextField;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author dtic
 */
public class AudioRecDialogController implements Initializable {

    private Stage dialogStage;
    private boolean okClicked = false;
    private boolean isRecording = false;
    private boolean isRecorded = false;
    MainApp mainApp;

    private final String filename;
    @FXML
    private Label counter;
    @FXML
    private Button recButton;
    @FXML
    private TextField ipIn;
    @FXML
    private NumberTextField portIn;
    @FXML
    private Button sendBut;

    public AudioRecDialogController() {
        cronometer = new Cronometer(this);
        int tmpnum = HelperUtil.getIntProperty(HelperUtil.TMP_FILE);
        HelperUtil.properties.setProperty(HelperUtil.TMP_FILE, tmpnum++ + "");
        filename = HelperUtil.getProperty(HelperUtil.AUDIO_FOLDER) + File.separator + "dcm_audio_msg_" + tmpnum + HelperUtil.getProperty(HelperUtil.AUDIO_FORMAT);
        recorder = new SimpleAudioRecorder(this, filename);
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
     * Returns true if the user clicked OK, false otherwise.
     *
     * @return
     */
    public boolean isOkClicked() {
        return okClicked;
    }

    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleRec() {
        FontAwesomeIcon icon = (FontAwesomeIcon) recButton.getGraphic();

        if (icon.getGlyphName().equalsIgnoreCase(FontAwesomeIcons.MICROPHONE.name())) {
            icon.setIcon(FontAwesomeIcons.STOP);
            System.out.println("iniciando grabación");
            if (cronometer.isRunning) {
                cronometer.stopCronometer();
            }
            new Thread(cronometer).start();
            if (recorder.isRuning) {
                recorder.stopRecording();
            }
            recorder = new SimpleAudioRecorder(this, filename);
            recorder.init();
            //recorder.start();
        } else if (icon.getGlyphName().equalsIgnoreCase(FontAwesomeIcons.STOP.name())) {
            icon.setIcon(FontAwesomeIcons.REFRESH);
            System.out.println("interrupir grabación");
            if (cronometer.isRunning) {
                cronometer.stopCronometer();
            }
            recorder.stopRecording();
        } else {
            icon.setIcon(FontAwesomeIcons.STOP);
            System.out.println("reiniciar grabación");
            if (cronometer.isRunning) {
                cronometer.stopCronometer();
            }
            new Thread(cronometer).start();
            if (recorder.isRuning) {
                recorder.stopRecording();
            }
            recorder = new SimpleAudioRecorder(this, filename);
            recorder.init();
        }
    }

    /**
     * Called when the user clicks cancel.
     */
    @FXML
    public void handleCancel() {
        if (cronometer.isRunning) {
            cronometer.stopCronometer();
        }
        recorder.stopRecording();
        File f = new File(filename);
        if (f.exists()) {
            f.delete();
        }
        dialogStage.close();
    }

    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleOk() {
        if (cronometer.isRunning) {
            cronometer.stopCronometer();
        }
        FontAwesomeIcon icon = (FontAwesomeIcon) recButton.getGraphic();

        if (icon.getGlyphName().equalsIgnoreCase(FontAwesomeIcons.MICROPHONE.name())) {
            HelperUtil.showInfo("Grabe audio para enviar.");
            return;
        }
        if (icon.getGlyphName().equalsIgnoreCase(FontAwesomeIcons.STOP.name())) {
            if (recorder.isRuning) {
                recorder.stopRecording();
            }
        }

        new Thread(new ClientFileSocket(ipIn.getText(), portIn.getText(), filename, this.mainApp)).start();
        dialogStage.close();
    }

    public void initx() {
        ipIn.textProperty().addListener((observable, oldValue, newValue) -> {
            boolean v = HelperUtil.isValidIP(newValue) && HelperUtil.isValidPort(portIn.getText());
            sendBut.setDisable(!v);
        });
        portIn.textProperty().addListener((observable, oldValue, newValue) -> {
            boolean v = HelperUtil.isValidPort(newValue) && HelperUtil.isValidIP(ipIn.getText());
            sendBut.setDisable(!v);
        });
        sendBut.setDisable(true);
    }
    SimpleAudioRecorder recorder;
    Cronometer cronometer;

    public void setConunterText(String string) {
        this.counter.setText(string);
    }

    void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

}
