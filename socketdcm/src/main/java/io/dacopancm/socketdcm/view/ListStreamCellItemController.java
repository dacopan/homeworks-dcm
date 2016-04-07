/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.dacopancm.socketdcm.view;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import io.dacopancm.socketdcm.model.StreamFile;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 *
 * @author dtic
 */
public class ListStreamCellItemController {

    @FXML
    Label namex;
    @FXML
    Label pathx;
    @FXML
    Label idx;
    @FXML
    Label viewsx;
    @FXML
    Label emitex;
    @FXML
    FontAwesomeIcon icox;
    @FXML
    HBox hbox;

    public ListStreamCellItemController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ListStreamCellItem.fxml"));
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setInfo(StreamFile dcfile) {
        namex.setText(dcfile.getNombre());
        pathx.setText(dcfile.getPath());
        viewsx.setText(dcfile.getViews() + "");
        emitex.setText(dcfile.getEmisiones() + "");
        idx.setText(dcfile.getIdx());
    }

    public Node getBox() {
        return hbox;
    }
}
