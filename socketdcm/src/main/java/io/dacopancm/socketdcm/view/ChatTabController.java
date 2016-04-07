/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.dacopancm.socketdcm.view;

import io.dacopancm.socketdcm.net.ClientSocket;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author dtic
 */
public class ChatTabController implements Initializable {

    MainApp mainApp;
    ClientSocket socketx;
    @FXML
    VBox chatWrap;
    @FXML
    TextField inMessage;
    @FXML
    Label username;

    public ChatTabController() {
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

    }

    public MainApp getMainApp() {
        return mainApp;

    }

    void setClientSocket(ClientSocket socketx) {
        this.socketx = socketx;
    }

    /**
     * anade mensaje como server
     *
     * @param m
     */
    public void addMessage(String m) {
        addBubble(false, m);
    }

    public void addServerMessage(String m) {
        addBubble(true, m);
    }

    @FXML
    public void handleSendButton() {
        //VBox.
        socketx.sendMessagge(inMessage.getText());
        addBubble(true, inMessage.getText());
    }

    private void addBubble(boolean isServer, String txt) {
        VBox b = new VBox();
        b.setMinWidth(240);
        b.setAlignment(isServer ? Pos.CENTER_RIGHT : Pos.CENTER_LEFT);
        Label msg = new Label(txt);
        msg.setWrapText(true);

        msg.getStyleClass().add(isServer ? "chat-bubble-b" : "chat-bubble-t");
        b.getChildren().add(msg);
        chatWrap.getChildren().add(b);
    }

    public void setUsername(String name) {
        this.username.setText(name);
    }
}
