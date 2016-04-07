/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.dacopancm.socketdcm.view;

import io.dacopancm.socketdcm.helper.HelperUtil;
import io.dacopancm.socketdcm.net.ClientFileSocket;
import io.dacopancm.styles.jmetro8.NumberTextField;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author dtic
 */
public class SendFileDialogController implements Initializable {

    private Stage dialogStage;
    private boolean okClicked = false;
    private boolean isRecording = false;
    private boolean isRecorded = false;
    MainApp mainApp;

    @FXML
    private Label filename;
    @FXML
    private TextField ipIn;
    @FXML
    private NumberTextField portIn;
    @FXML
    private Button sendBut;

    public SendFileDialogController() {

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
     * Called when the user clicks cancel.
     */
    @FXML
    public void handleCancel() {
        dialogStage.close();
    }

    @FXML
    public void handleChoseFile() {
        final FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());
        if (file != null) {
            try {
                this.filename.setText(file.getCanonicalPath());
//openFile(file);
            } catch (IOException ex) {
                this.filename.setText("Archivo");
            }
        }

    }

    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleOk() {
        File f = new File(filename.getText());
        if (f.exists()) {
            new Thread(new ClientFileSocket(ipIn.getText(), portIn.getText(), filename.getText(), this.mainApp)).start();
        } else {
        }
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
        filename.textProperty().addListener((observable, oldValue, newValue) -> {
            boolean v = newValue.equalsIgnoreCase("archivo");
            sendBut.setDisable(v);
        });
    }

    void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

}
