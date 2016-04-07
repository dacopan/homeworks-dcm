/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.dacopancm.socketdcm.model;

import io.dacopancm.socketdcm.view.ListStreamCellItemController;
import javafx.scene.control.ListCell;

/**
 *
 * @author dtic
 */
public class ListViewStreamCell extends ListCell<StreamFile> {

    @Override
    public void updateItem(StreamFile dat, boolean empty) {
        super.updateItem(dat, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else if (dat != null) {
            ListStreamCellItemController data = new ListStreamCellItemController();
            data.setInfo(dat);
            setGraphic(data.getBox());
        }
    }
}
