/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.dacopancm.socketdcm.model;

import io.dacopancm.socketdcm.view.ListCellItemController;
import javafx.scene.control.ListCell;

/**
 *
 * @author dtic
 */
public class ListViewCell extends ListCell<DCMFile> {

    @Override
    public void updateItem(DCMFile dat, boolean empty) {
        super.updateItem(dat, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else if (dat != null) {
            ListCellItemController data = new ListCellItemController();
            data.setInfo(dat);
            setGraphic(data.getBox());
        }
    }
}
