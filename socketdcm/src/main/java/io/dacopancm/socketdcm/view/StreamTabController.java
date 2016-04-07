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
import io.dacopancm.socketdcm.model.ListViewStreamCell;
import io.dacopancm.socketdcm.model.StreamFile;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

/**
 *
 * @author dtic
 */
public class StreamTabController implements Initializable {

    MainApp mainApp;
    @FXML
    ListView filesList;

    public StreamTabController() {
    }

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        filesList.setItems(mainApp.getStreamFilesList());
        filesList.setCellFactory(new Callback<ListView<StreamFile>, javafx.scene.control.ListCell<StreamFile>>() {
            @Override
            public ListCell<StreamFile> call(ListView<StreamFile> param) {
                return new ListViewStreamCell();
            }
        });
        String ms = "esto esta vacío, aprecerán aquí los archivos que publiques para emitir en stream.";
        Label l = new Label(ms);
        l.getStyleClass().add("header");
        l.setStyle("-fx-font-size:28px;-fx-text-fill:#c1c1c1;");
        l.setWrapText(true);

        GlyphIcon iServer = GlyphsBuilder.create(FontAwesomeIcon.class).glyph(FontAwesomeIcons.QQ).build();
        iServer.setSize("30px");
        iServer.setFill(Paint.valueOf("white"));

        l.setGraphic(iServer);

        filesList.setPlaceholder(l);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void refreshList() {
        filesList.setItems(mainApp.getStreamFilesList());
    }

    @FXML
    private void showAddStream() {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("AddStreamFileDialog.fxml"));
            StackPane page = (StackPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();

            dialogStage.initOwner(mainApp.getPrimaryStage());
            dialogStage.initStyle(StageStyle.UNDECORATED);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            dialogStage.setWidth(mainApp.getPrimaryStage().getWidth());
            dialogStage.setHeight(350);
            Stage stage = mainApp.getPrimaryStage();
            dialogStage.setX(stage.getX() + stage.getWidth() / 2 - dialogStage.getWidth() / 2);
            dialogStage.setY(stage.getY() + stage.getHeight() / 2 - dialogStage.getHeight() / 2);

            // Set the person into the controller.
            AddStreamFileDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setMainApp(mainApp);
            controller.initx();

            dialogStage.showAndWait();

            boolean resp = controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }
}
