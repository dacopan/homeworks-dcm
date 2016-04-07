/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.dacopancm.socketdcm.view;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import io.dacopancm.socketdcm.helper.DateUtil;
import io.dacopancm.socketdcm.model.DCMFile;
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
public class ListCellItemController {
    
    @FXML
    Label namex;
    @FXML
    Label pathx;
    @FXML
    Label datex;
    @FXML
    FontAwesomeIcon icox;
    @FXML
    HBox hbox;
    @FXML
    Label statex;
    
    public ListCellItemController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ListCellItem.fxml"));
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    public void setInfo(DCMFile dcfile) {
        namex.setText(dcfile.getName() + "." + dcfile.getFormat());
        pathx.setText(dcfile.getPath());
        datex.setText(DateUtil.format(dcfile.getDate()));
        statex.setText(dcfile.getState());
//        label1.setText(string);
//        label2.setText(string);
    }
    
    public Node getBox() {
        return hbox;
    }
}
