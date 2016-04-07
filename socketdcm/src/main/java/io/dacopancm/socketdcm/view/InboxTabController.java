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
import io.dacopancm.socketdcm.helper.DesktopApi;
import io.dacopancm.socketdcm.helper.HelperUtil;
import io.dacopancm.socketdcm.model.DCMFile;
import io.dacopancm.socketdcm.model.ListViewCell;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author dtic
 */
public class InboxTabController implements Initializable {

    MainApp mainApp;
    @FXML
    ListView filesList;
    @FXML
    Label title;
    @FXML
    Label folderx;
    @FXML
    Button openfolder;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

    }

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     * @param isInbox
     */
    public void setMainApp(MainApp mainApp, boolean isInbox) {
        this.mainApp = mainApp;
        folderx.setVisible(isInbox);
        openfolder.setVisible(isInbox);
        try {
            String path = new File(HelperUtil.getProperty(HelperUtil.RECEIVE_FOLDER)).getAbsolutePath();
            folderx.setText(isInbox ? path : "");
            folderx.setWrapText(true);
        } catch (Exception ex) {
            System.out.println("error al determinar ruta de carpeta de recibidos");
        }
        title.setText(isInbox ? "Archivos recibidos" : "Archivos enviados");
        filesList.setItems(isInbox ? mainApp.getReceiveFilesList() : mainApp.getSendFilesList());
        filesList.setCellFactory(new Callback<ListView<DCMFile>, javafx.scene.control.ListCell<DCMFile>>() {
            @Override
            public ListCell<DCMFile> call(ListView<DCMFile> param) {
                return new ListViewCell();
            }
        });
        String ms = "esto esta vacío, aprecerán aquí los archivos que " + (isInbox ? "recibas" : "Envies");
        Label l = new Label(ms);
        l.getStyleClass().add("header");
        l.setStyle("-fx-font-size:28px;-fx-text-fill:#c1c1c1;");
        l.setWrapText(true);

        GlyphIcon iServer = GlyphsBuilder.create(FontAwesomeIcon.class).glyph(FontAwesomeIcons.TINT).build();
        iServer.setSize("30px");
        iServer.setFill(Paint.valueOf("white"));

        l.setGraphic(iServer);

        filesList.setPlaceholder(l);

        filesList.setOnMouseClicked((MouseEvent event) -> {
            DCMFile item = (DCMFile) filesList.getSelectionModel().getSelectedItem();
            String path = item.getPath();
            
            Platform.runLater(() -> {
                try {
                    DesktopApi.open(new File(path));
                } catch (Exception ex) {
                    HelperUtil.showErrorB("su sistema no soporta la apertura de archivos, vaya a la carpeta de archivos");
                }
            });
        });

        // Add observable list data to the table
    }

    @FXML
    public void showFolder() {
        Platform.runLater(() -> {
            try {

                DesktopApi.open(new File(HelperUtil.getProperty(HelperUtil.RECEIVE_FOLDER)));
            } catch (Exception ex) {
                HelperUtil.showErrorB("su sistema no soporta la apertura de archivos, vaya a la carpeta de archivos");
            }
        });
    }

}
