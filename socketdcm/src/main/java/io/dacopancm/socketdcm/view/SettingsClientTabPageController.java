/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.dacopancm.socketdcm.view;

import io.dacopancm.styles.jmetro8.ToggleSwitch;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import static io.dacopancm.socketdcm.helper.HelperUtil.properties;
import io.dacopancm.socketdcm.helper.HelperUtil;
import static io.dacopancm.socketdcm.helper.HelperUtil.properties;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author dtic
 */
public class SettingsClientTabPageController implements Initializable {

    MainApp mainApp;
    @FXML
    private ToggleSwitch clt_keppalive;
    @FXML
    private Slider clt_buffer;
    @FXML
    private Slider clt_timeout;
    @FXML
    private CheckBox clt_reuseaddress;
    @FXML
    private CheckBox clt_delay;
    @FXML
    private CheckBox clt_urgen;
    @FXML
    private CheckBox clt_lingering;
    @FXML
    private CheckBox clt_broadcast;
    @FXML
    private CheckBox clt_gzip;

    @FXML
    private Label clt_buffer_val;
    @FXML
    private Label clt_timeout_val;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        clt_buffer_val.textProperty().bind(clt_buffer.valueProperty().asString());
        clt_timeout_val.textProperty().bind(clt_timeout.valueProperty().asString());
        // TODO
    }

    public void setDefaultSettings() {

        clt_keppalive.setSelected(HelperUtil.getBoolProperty(HelperUtil.CLT_KEEPALIVE));
        clt_buffer.setValue(HelperUtil.getIntProperty(HelperUtil.CLT_BUFFER));
        clt_timeout.setValue(HelperUtil.getIntProperty(HelperUtil.CLT_TIMEOUT));
        clt_reuseaddress.setSelected(HelperUtil.getBoolProperty(HelperUtil.CLT_REUSEADDRESS));
        clt_delay.setSelected(HelperUtil.getBoolProperty(HelperUtil.CLT_DELAY));
        clt_urgen.setSelected(HelperUtil.getBoolProperty(HelperUtil.CLT_URGEN));
        clt_lingering.setSelected(HelperUtil.getBoolProperty(HelperUtil.CLT_LINGERING));
        clt_broadcast.setSelected(HelperUtil.getBoolProperty(HelperUtil.CLT_BROADCAST));
        clt_gzip.setSelected(HelperUtil.getBoolProperty(HelperUtil.CLT_GZIP));
    }

    @FXML
    private void handleSave() {
        properties.setProperty(HelperUtil.CLT_KEEPALIVE, String.valueOf(clt_keppalive.isSelected()));
        properties.setProperty(HelperUtil.SRV_BUFFER, String.valueOf(new Double(clt_buffer.getValue()).intValue()));
        properties.setProperty(HelperUtil.SRV_TIMEOUT, String.valueOf(new Double(clt_timeout.getValue()).intValue()));
        properties.setProperty(HelperUtil.SRV_REUSEADDRESS, String.valueOf(clt_reuseaddress.isSelected()));
        properties.setProperty(HelperUtil.CLT_DELAY, String.valueOf(clt_delay.isSelected()));
        properties.setProperty(HelperUtil.CLT_URGEN, String.valueOf(clt_urgen.isSelected()));
        properties.setProperty(HelperUtil.CLT_LINGERING, String.valueOf(clt_lingering.isSelected()));
        properties.setProperty(HelperUtil.CLT_BROADCAST, String.valueOf(clt_broadcast.isSelected()));
        properties.setProperty(HelperUtil.CLT_GZIP, String.valueOf(clt_gzip.isSelected()));

        HelperUtil.showInfo("Configuaciones aplicadas.");
    }

    @FXML
    private void handleReset() {
        setDefaultSettings();
        HelperUtil.showInfo("Configuraciones anteriores.");
    }

    @FXML
    private void handleDefect() {
        HelperUtil.setClientDefaultPropierties();
        setDefaultSettings();
        HelperUtil.showInfo("Configuaciones por defecto.");
    }

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        setDefaultSettings();

    }
}
