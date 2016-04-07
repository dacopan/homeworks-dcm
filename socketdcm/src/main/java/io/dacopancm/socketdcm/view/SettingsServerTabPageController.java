/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.dacopancm.socketdcm.view;

import io.dacopancm.socketdcm.helper.HelperUtil;
import static io.dacopancm.socketdcm.helper.HelperUtil.properties;
import io.dacopancm.styles.jmetro8.NumberTextField;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author dtic
 */
public class SettingsServerTabPageController implements Initializable {

    MainApp mainApp;
    @FXML
    private Slider srv_buffer;
    @FXML
    private Slider srv_timeout;
    @FXML
    private CheckBox srv_reuseaddress;
    @FXML
    private NumberTextField srv_port;
    @FXML
    private Label srv_url;
    @FXML
    private Label srv_buffer_val;
    @FXML
    private Label srv_timeout_val;
    @FXML
    private TextField srv_username;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        srv_buffer_val.textProperty().bind(srv_buffer.valueProperty().asString());
        srv_timeout_val.textProperty().bind(srv_timeout.valueProperty().asString());
    }

    public void setDefaultSettings() {

        srv_buffer.setValue(HelperUtil.getIntProperty(HelperUtil.SRV_BUFFER));
        srv_timeout.setValue(HelperUtil.getIntProperty(HelperUtil.SRV_TIMEOUT));
        srv_reuseaddress.setSelected(HelperUtil.getBoolProperty(HelperUtil.SRV_REUSEADDRESS));
        srv_port.setText(HelperUtil.getProperty(HelperUtil.SRV_PORT));
        srv_username.setText(HelperUtil.getProperty(HelperUtil.SRV_USERNAME));

    }

    @FXML
    private void handleSave() {
        try {
            properties.setProperty(HelperUtil.SRV_BUFFER, String.valueOf(new Double(srv_buffer.getValue()).intValue()));
            properties.setProperty(HelperUtil.SRV_TIMEOUT, String.valueOf(new Double(srv_timeout.getValue()).intValue()));
            properties.setProperty(HelperUtil.SRV_REUSEADDRESS, String.valueOf(srv_reuseaddress.isSelected()));
            properties.setProperty(HelperUtil.SRV_PORT, String.valueOf(srv_port.getText()));
            properties.setProperty(HelperUtil.SRV_USERNAME, String.valueOf(srv_username.getText()));
            HelperUtil.showInfo("Configuaciones aplicadas.");
            mainApp.ServerThread.stopServer();
            mainApp.initServer();
            HelperUtil.showInfo("Servidor reiniciado.");
        } catch (IOException ex) {
            HelperUtil.showError("Configuaciones no aplicadas. o servidor no reiniciado");
            Logger.getLogger(SettingsServerTabPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void handleReset() {
        setDefaultSettings();
        HelperUtil.showInfo("Configuraciones anteriores.");
    }

    @FXML
    private void handleDefect() {
        HelperUtil.setServerDefaultPropierties();
        setDefaultSettings();
        HelperUtil.showInfo("Configuaciones por defecto.");
    }

    @FXML
    private void handleOn() {
        if (mainApp.ServerThread.isRunning) {
            //ya esta encendido
            HelperUtil.showInfo("Servidor ya está encendido.");
        } else {
            mainApp.initServer();            
        }
    }

    @FXML
    private void handleOff() {
        try {
            if (mainApp.ServerThread.isRunning) {
                mainApp.ServerThread.stopServer();
                HelperUtil.showInfo("Servidor apagado.");
            } else {
                //ya esta apagado
                HelperUtil.showInfo("Servidor ya está apagado.");
            }
        } catch (IOException ex) {
            Logger.getLogger(SettingsServerTabPageController.class.getName()).log(Level.SEVERE, null, ex);
            HelperUtil.showError("Servidor no pudo ser apagado.");
        }
    }

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;        
        try {
            srv_url.setText(InetAddress.getLocalHost().getHostAddress());            
        } catch (UnknownHostException ex) {            
        }
        setDefaultSettings();
    }
}
