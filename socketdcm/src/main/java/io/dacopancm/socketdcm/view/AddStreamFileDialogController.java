/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.dacopancm.socketdcm.view;

import io.dacopancm.socketdcm.helper.HelperUtil;
import io.dacopancm.socketdcm.model.StreamFile;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
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
public class AddStreamFileDialogController implements Initializable {

    private Stage dialogStage;
    private boolean okClicked = false;
    MainApp mainApp;

    @FXML
    private Label filename;
    @FXML
    private TextField idxIn;
    @FXML
    private TextField nameIn;
    @FXML
    private Button sendBut;

    public AddStreamFileDialogController() {

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
        ArrayList<String> files = new ArrayList();
        String filestype = "*.ps,*.ts,*.mpg,*.ogg,*.asf,*.mp4,*.mov,*.mp3,*.flv,*.wmv";
        String[] a = filestype.split(",");
        files.addAll(Arrays.asList(a));

        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Multimedia (" + filestype + ")", files));
        fileChooser.setInitialDirectory(new File(HelperUtil.VLC_FOLDER));
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
        if (filename.getText().equalsIgnoreCase("archivo")) {
            HelperUtil.showError("Seleccione un archivo para continuar");
            return;
        }

        StreamFile sf = new StreamFile();
        sf.setNombre(nameIn.getText());
        sf.setPath(filename.getText());
        sf.setIdx(idxIn.getText());

        boolean isAdded = mainApp.addStreamFile(sf);
        if (isAdded) {
            //HelperUtil.showInfo(sf.getNombre(), "Disponible para stream bajo demanda.");

            dialogStage.close();
        } else {
            HelperUtil.showError("Ya existe un stream con esta id.");
        }

    }

    public void initx() {
        idxIn.textProperty().addListener((observable, oldValue, newValue) -> {
            boolean v = HelperUtil.isValidStreamId(idxIn.getText());
            sendBut.setDisable(!v);
        });
        nameIn.textProperty().addListener((observable, oldValue, newValue) -> {
            boolean v = idxIn.getText().equalsIgnoreCase("");
            sendBut.setDisable(v);
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
