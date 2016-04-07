/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.dacopancm.socketdcm.view;

import de.jensd.fx.glyphs.GlyphIcon;
import de.jensd.fx.glyphs.GlyphsBuilder;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcons;
import io.dacopancm.socketdcm.helper.HelperUtil;
import io.dacopancm.socketdcm.net.ClientSocket;
import io.dacopancm.styles.jmetro8.NumberTextField;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Pair;

/**
 * FXML Controller class
 *
 * @author dtic
 */
public class MainContainerController implements Initializable {

    MainApp mainApp;
    @FXML
    private Tab settingsServerTab;
    @FXML
    private Tab settingsClientTab;
    @FXML
    private Tab inboxTab;
    @FXML
    private Tab outboxTab;
    @FXML
    private Tab streamTab;
    @FXML
    private TabPane tabPaneChat;
    @FXML
    private HBox rebeld;

    /**
     * Initializes the controller class.
     */
    @Override

    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void showRecAudioDialog() {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("AudioRecDialog.fxml"));
            StackPane page = (StackPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            //dialogStage.setTitle("Edit Person");
            //dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.initOwner(mainApp.getPrimaryStage());
            dialogStage.initStyle(StageStyle.UNDECORATED);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            dialogStage.setWidth(mainApp.getPrimaryStage().getWidth());
            dialogStage.setHeight(350);
            Stage stage = mainApp.getPrimaryStage();
            dialogStage.setX(stage.getX() + stage.getWidth() / 2 - dialogStage.getWidth() / 2);
            dialogStage.setY(stage.getY() + stage.getHeight() / 2 - dialogStage.getHeight() / 2);

            double y = stage.getY();
            // Set the person into the controller.
            AudioRecDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setMainApp(mainApp);
            controller.initx();
            //mainApp.rootLayout.setDisable(true);
            //controller.setPerson(person);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            boolean resp = controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    @FXML
    private void showFileDialog() {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("SendFileDialog.fxml"));
            StackPane page = (StackPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            //dialogStage.setTitle("Edit Person");
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.initOwner(mainApp.getPrimaryStage());
            dialogStage.initStyle(StageStyle.UNDECORATED);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            dialogStage.setWidth(mainApp.getPrimaryStage().getWidth());
            dialogStage.setHeight(350);
            Stage stage = mainApp.getPrimaryStage();
            dialogStage.setX(stage.getX() + stage.getWidth() / 2 - dialogStage.getWidth() / 2);
            dialogStage.setY(stage.getY() + stage.getHeight() / 2 - dialogStage.getHeight() / 2);

            double y = stage.getY();
            // Set the person into the controller.
            SendFileDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setMainApp(mainApp);
            controller.initx();
            //mainApp.rootLayout.setDisable(true);
            //controller.setPerson(person);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            boolean resp = controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    @FXML
    private void showChatDialog() {
        Optional<Pair<String, String>> result = showInputConectionDialog("Chat", "Iniciar chat",
                GlyphsDude.createIcon(FontAwesomeIcons.COMMENTS, "40px")
        );

        result.ifPresent(conectparam -> {
            //System.out.println("Username=" + usernamePassword.getKey() + ", Password=" + usernamePassword.getValue());
            try {
                // Load person overview.
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(MainApp.class.getResource("ChatTab.fxml"));
                AnchorPane chatTab = (AnchorPane) loader.load();

                Tab t = new Tab();
                t.setContent(chatTab);
                t.setText(conectparam.getKey());
                tabPaneChat.getTabs().add(t);

                ChatTabController controller = loader.getController();
                controller.setMainApp(mainApp);
                ClientSocket socketx = new ClientSocket(conectparam.getKey(), conectparam.getValue(), controller);
                controller.setClientSocket(socketx);
                Thread tt = new Thread(socketx);
                tt.start();
                mainApp.addClientThread(socketx);
                //on close tab close socket
                t.setOnClosed((e) -> {
                    socketx.terminateChat();

                });

                //FXMLLoader loader = new FXMLLoader();
            } catch (IOException e) {
                e.printStackTrace();
            }

        });

    }

    public void requestChatDialog(String name, Socket clntSock) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Sesión de chat");
        alert.setHeaderText("Quiere inisiar una sesión de chat?");
        alert.setContentText(name + " Quiere iniciar un chat con usted.");

        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("ChatTab.fxml"));
            AnchorPane chatTab = (AnchorPane) loader.load();

            //pregunto si quiere aceptar chat
            Optional<ButtonType> result = alert.showAndWait();
            boolean isAcepted = result.get() == ButtonType.OK;

            Tab t = new Tab();
            t.setText(name);
            t.setContent(chatTab);
            if (isAcepted) {
                tabPaneChat.getTabs().add(t);
                //on close tab close socket
            }
            ChatTabController controller = loader.getController();
            controller.setMainApp(mainApp);
            ClientSocket socketx = new ClientSocket(controller, clntSock, isAcepted, name);
            controller.setClientSocket(socketx);
            new Thread(socketx).start();
            mainApp.addClientThread(socketx);

            t.setOnClosed((e) -> {
                socketx.terminateChat();

            });
            //FXMLLoader loader = new FXMLLoader();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void showPlayerDialog() {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("StreamChooserDialog.fxml"));
            StackPane page = (StackPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            //dialogStage.setTitle("Edit Person");
            //dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.initOwner(mainApp.getPrimaryStage());
            dialogStage.initStyle(StageStyle.UNDECORATED);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            dialogStage.setWidth(mainApp.getPrimaryStage().getWidth());
            dialogStage.setHeight(500);
            Stage stage = mainApp.getPrimaryStage();
            dialogStage.setX(stage.getX() + stage.getWidth() / 2 - dialogStage.getWidth() / 2);
            dialogStage.setY(stage.getY() + stage.getHeight() / 2 - dialogStage.getHeight() / 2);

            double y = stage.getY();
            // Set the person into the controller.
            StreamChooserDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setMainApp(mainApp);
            controller.initx();

            dialogStage.showAndWait();

//            boolean resp = controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    private Optional<Pair<String, String>> showInputConectionDialog(String title, String text, Node grph) {
        // Create the custom dialog.
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle(title);
        dialog.setHeaderText(text);

// Set the icon (must be included in the project).
        dialog.setGraphic(
                //new ImageView(this.getClass().getResource("login.png").toString())
                grph
        );

// Set the button types.
        ButtonType loginButtonType = new ButtonType("Aceptar", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

// Create the username and password labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField username = new TextField();
        username.setPromptText("IP");
        NumberTextField password = new NumberTextField();
        password.setPromptText("Puerto");

        grid.add(new Label("IP"), 0, 0);
        grid.add(username, 1, 0);
        grid.add(new Label("Puerto"), 0, 1);
        grid.add(password, 1, 1);

// Enable/Disable login button depending on whether a username was entered.
        Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
        loginButton.setDisable(true);

// Do some validation (using the Java 8 lambda syntax).
        username.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(!HelperUtil.isValidIP(newValue));
        });

        password.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(!HelperUtil.isValidPort(newValue));
        });

        dialog.getDialogPane().setContent(grid);

// Request focus on the username field by default.
        Platform.runLater(() -> username.requestFocus());

// Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new Pair<>(username.getText(), password.getText());
            }
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();

        return result;
    }

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        try {
            long startx = System.currentTimeMillis();
            System.out.println(" start load tabs: " + startx);
            // Load tab1
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("SettingsServerTabPage.fxml"));
            AnchorPane SettingsServerTabPage = (AnchorPane) loader.load();
            settingsServerTab.setContent(SettingsServerTabPage);
            SettingsServerTabPageController controller = loader.getController();
            controller.setMainApp(mainApp);

            //loader client tab
            FXMLLoader loader2 = new FXMLLoader();
            loader2.setLocation(MainApp.class.getResource("SettingsClientTabPage.fxml"));
            AnchorPane SettingsClientTabPage = (AnchorPane) loader2.load();
            settingsClientTab.setContent(SettingsClientTabPage);
            SettingsClientTabPageController controller2 = loader2.getController();
            controller2.setMainApp(mainApp);
            //load inbox tab
            FXMLLoader loader3 = new FXMLLoader();
            loader3.setLocation(MainApp.class.getResource("InboxTab.fxml"));
            AnchorPane InboxTabPage = (AnchorPane) loader3.load();
            inboxTab.setContent(InboxTabPage);
            InboxTabController controller3 = loader3.getController();
            controller3.setMainApp(mainApp, true);
            //load outbox
            FXMLLoader loader4 = new FXMLLoader();
            loader4.setLocation(MainApp.class.getResource("InboxTab.fxml"));
            AnchorPane OutboxTabPage = (AnchorPane) loader4.load();
            outboxTab.setContent(OutboxTabPage);
            InboxTabController controller4 = loader4.getController();
            controller4.setMainApp(mainApp, false);
            //load stream tab
            FXMLLoader loader5 = new FXMLLoader();
            loader5.setLocation(MainApp.class.getResource("StreamTab.fxml"));
            AnchorPane StreamTabPage = (AnchorPane) loader5.load();
            streamTab.setContent(StreamTabPage);
            StreamTabController controller5 = loader5.getController();
            controller5.setMainApp(mainApp);

            GlyphIcon iServer = GlyphsBuilder.create(FontAwesomeIcon.class).glyph(FontAwesomeIcons.SERVER).build();
            GlyphIcon iClient = GlyphsBuilder.create(FontAwesomeIcon.class).glyph(FontAwesomeIcons.LAPTOP).build();
            GlyphIcon iOutbox = GlyphsBuilder.create(FontAwesomeIcon.class).glyph(FontAwesomeIcons.UPLOAD).build();
            GlyphIcon iInbox = GlyphsBuilder.create(FontAwesomeIcon.class).glyph(FontAwesomeIcons.DOWNLOAD).build();
            settingsClientTab.setText(iClient.getText());
            settingsServerTab.setText(iServer.getText());
            inboxTab.setText(iInbox.getText());
            outboxTab.setText(iOutbox.getText());
            streamTab.setText(GlyphsBuilder.create(FontAwesomeIcon.class).glyph(FontAwesomeIcons.SOUNDCLOUD).build().getText());

            //p = tabPaneChat.getTabs().get(0);
            //p.setText("dacopanCM");
            this.tabPaneChat.getTabs().addListener(new ListChangeListener() {

                @Override
                public void onChanged(ListChangeListener.Change c) {
                    rebeld.getStyleClass().clear();
                    if (c.getList().size() < 1) {
                        // tabPaneChat.getTabs().add(p);
                        rebeld.getStyleClass().add("visible");
                    } else {
                        rebeld.getStyleClass().add("oculto");
                    }
                }
            });
            this.tabPaneChat.getTabs().clear();

            long finx = System.currentTimeMillis();
            System.out.println(" fin load tabs: " + finx + "total: " + (finx - startx) / 1000);
            //FXMLLoader loader = new FXMLLoader();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Add observable list data to the table
        //mainApp.getServer();
    }
    Tab p;

    @FXML
    private void handleLogPrint() {
        Logger logger = Logger.getLogger("practical");
        Thread[] ths = HelperUtil.getAllThreads();
        logger.log(Level.INFO, "Thread activos {0}", ths.length);
        for (Thread th : ths) {
            logger.log(Level.INFO, "Thread activo {0},{1}", new Object[]{th.getId(), th.getName()});
        }
    }
}
