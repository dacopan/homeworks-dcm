/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.dacopancm.socketdcm.view;

import com.sun.jna.Memory;
import io.dacopancm.socketdcm.helper.HelperUtil;
import io.dacopancm.socketdcm.helper.LoggingMediaPlayerEventAdapter;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.image.WritablePixelFormat;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import static javafx.util.Duration.millis;
import uk.co.caprica.vlcj.binding.internal.libvlc_log_level_e;
import uk.co.caprica.vlcj.component.DirectMediaPlayerComponent;
import uk.co.caprica.vlcj.log.LogEventListener;
import uk.co.caprica.vlcj.log.NativeLog;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.direct.BufferFormat;
import uk.co.caprica.vlcj.player.direct.BufferFormatCallback;
import uk.co.caprica.vlcj.player.direct.DirectMediaPlayer;
import uk.co.caprica.vlcj.player.direct.format.RV32BufferFormat;

/**
 *
 * @author dtic
 */
public class PlayerDialogController implements Initializable {

    private Stage dialogStage;
    MainApp mainApp;
    NativeLog log;
    static Logger logger;

    @FXML
    HBox progressxWrap;
    @FXML
    ProgressIndicator progressx;
    @FXML
    Label counter;
    @FXML
    Label timerx;
    @FXML
    Label state;
    @FXML
    HBox audioWrap;

    @FXML
    private Pane playerHolder;
    private String PATH_TO_VIDEO;
    private boolean isAudio;
    private ImageView imageView;
    private DirectMediaPlayerComponent mediaPlayerComponent;
    private WritableImage writableImage;
    private WritablePixelFormat<ByteBuffer> pixelFormat;
    private FloatProperty videoSourceRatioProperty;

    public PlayerDialogController() {

    }

    @Override
    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Sets the stage of this dialog.
     *
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Called when the user clicks cancel.
     */
    @FXML
    public void handleCancel() {
        dialogStage.close();
        log.release();
        mediaPlayerComponent.getMediaPlayer().stop();
        mediaPlayerComponent.getMediaPlayer().release();
        mediaPlayerComponent.release();

    }

    void setMainApp(MainApp mainApp, String mrl, boolean isAudio) {
        this.mainApp = mainApp;
        this.PATH_TO_VIDEO = mrl;
        this.isAudio = isAudio;
        //this.PATH_TO_VIDEO = new File("C:\\Users\\dtic\\Documents\\GitHub\\NetBeansProjects\\vlc\\media\\audio.mp3").getAbsolutePath();
    }

    public void initx() {

        logger = Logger.getLogger("practical");
        progressxWrap.getStyleClass().add("oculto");

        //if (!isAudio) {
        mediaPlayerComponent = new CanvasPlayerComponent();
        //}

        log = mediaPlayerComponent.getMediaPlayerFactory().newLog();
        if (log == null) {
            System.out.println("Native log not available on this platform");
            System.exit(1);
        }

        log.setLevel(libvlc_log_level_e.DEBUG);
        log.addLogListener(new LogEventListener() {
            @Override
            public void log(libvlc_log_level_e level, String module, String file, Integer line, String name, String header, Integer id, String message) {
                System.out.printf("xx [%-20s] (%-20s) %7s: %s\n", module, name, level, message);
            }
        });

        // if (!isAudio) {
        videoSourceRatioProperty = new SimpleFloatProperty(0.4f);
        pixelFormat = PixelFormat.getByteBgraPreInstance();
        initializeImageView();
        if (!isAudio) {
            audioWrap.getStyleClass().add("oculto");

        }
        // } else {
        //audioWrap.getStyleClass().add("oculto");
        // }

        mediaPlayerComponent.getMediaPlayer().addMediaPlayerEventListener(new LoggingMediaPlayerEventAdapter());
        mediaPlayerComponent.getMediaPlayer().addMediaPlayerEventListener(new MediaPlayerEventAdapter() {
            @Override
            public void error(MediaPlayer mediaPlayer) {
                logger.log(Level.INFO, "dcmplayer: error al reproducir");
                progressUI(false);
                HelperUtil.showErrorB("Error al reproducir");
            }

            @Override
            public void opening(MediaPlayer mediaPlayer) {
                logger.log(Level.INFO, "dcmplayer: opening");
                Platform.runLater(() -> {
                    state.setText("intentando reproducir");
                });
            }

            @Override
            public void playing(MediaPlayer mediaPlayer) {
                logger.log(Level.INFO, "dcmplayer: inicio reproducir");
                Platform.runLater(() -> {
                    state.setText("Reproduciendo");
                });
            }

            @Override
            public void mediaDurationChanged(MediaPlayer mediaPlayer, long newDuration) {
                logger.log(Level.INFO, "dcmplayer: duracion a reproducir: {0}", newDuration);
                long minutes = TimeUnit.MILLISECONDS.toMinutes(newDuration);
                String format = String.format("%d:%d",
                        minutes,
                        TimeUnit.MILLISECONDS.toSeconds(newDuration)
                        - TimeUnit.MINUTES.toSeconds(minutes)
                );
                Platform.runLater(() -> {
                    counter.setText(format);
                });
            }

            @Override
            public void timeChanged(MediaPlayer mediaPlayer, long newTime) {
                logger.log(Level.INFO, "dcmplayer: duracion a reproducir: {0}", newTime);
                long minutes = TimeUnit.MILLISECONDS.toMinutes(newTime);
                String format = String.format("%d:%d",
                        minutes,
                        TimeUnit.MILLISECONDS.toSeconds(newTime)
                        - TimeUnit.MINUTES.toSeconds(minutes)
                );
                Platform.runLater(() -> {
                    timerx.setText(format);
                });
            }

            @Override
            public void finished(MediaPlayer mediaPlayer) {
                Platform.runLater(() -> {
                    state.setText(timerx.getText().contains("-") ? "error al reproducir" : "finalizado");
                    timerx.setText(counter.getText());
                });
            }

        });
        mediaPlayerComponent.getMediaPlayer().prepareMedia(PATH_TO_VIDEO);
        mediaPlayerComponent.getMediaPlayer().start();

//        
        //https://github.com/caprica/vlcj/blob/master/src/test/java/uk/co/caprica/vlcj/test/basic/PlayerControlsPanel.java
    }

    private void progressUI(boolean b) {
        if (!b) {
            progressxWrap.getStyleClass().clear();
            progressxWrap.getStyleClass().add("oculto");
            progressx.setProgress(0.25F);
        } else {
            progressxWrap.getStyleClass().clear();
            progressxWrap.getStyleClass().add("visible");
            progressx.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
        }
    }

    private void initializeImageView() {
        Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
        writableImage = new WritableImage((int) visualBounds.getWidth(), (int) visualBounds.getHeight());

        imageView = new ImageView(writableImage);
        playerHolder.getChildren().add(imageView);

        playerHolder.widthProperty().addListener((observable, oldValue, newValue) -> {
            fitImageViewSize(newValue.floatValue(), (float) playerHolder.getHeight());
        });

        playerHolder.heightProperty().addListener((observable, oldValue, newValue) -> {
            fitImageViewSize((float) playerHolder.getWidth(), newValue.floatValue());
        });

        videoSourceRatioProperty.addListener((observable, oldValue, newValue) -> {
            fitImageViewSize((float) playerHolder.getWidth(), (float) playerHolder.getHeight());
        });
    }

    private void fitImageViewSize(float width, float height) {
        Platform.runLater(() -> {
            float fitHeight = videoSourceRatioProperty.get() * width;
            if (fitHeight > height) {
                imageView.setFitHeight(height);
                double fitWidth = height / videoSourceRatioProperty.get();
                imageView.setFitWidth(fitWidth);
                imageView.setX((width - fitWidth) / 2);
                imageView.setY(0);
            } else {
                imageView.setFitWidth(width);
                imageView.setFitHeight(fitHeight);
                imageView.setY((height - fitHeight) / 2);
                imageView.setX(0);
            }
        });
    }

    private class CanvasPlayerComponent extends DirectMediaPlayerComponent {

        public CanvasPlayerComponent() {
            super(new CanvasBufferFormatCallback());
        }

        PixelWriter pixelWriter = null;

        private PixelWriter getPW() {
            if (pixelWriter == null) {
                pixelWriter = writableImage.getPixelWriter();
            }
            return pixelWriter;
        }

        @Override
        public void display(DirectMediaPlayer mediaPlayer, Memory[] nativeBuffers, BufferFormat bufferFormat) {
            if (writableImage == null) {
                return;
            }
            Platform.runLater(() -> {
                Memory nativeBuffer = mediaPlayer.lock()[0];
                try {
                    ByteBuffer byteBuffer = nativeBuffer.getByteBuffer(0, nativeBuffer.size());
                    getPW().setPixels(0, 0, bufferFormat.getWidth(), bufferFormat.getHeight(), pixelFormat, byteBuffer, bufferFormat.getPitches()[0]);
                } finally {
                    mediaPlayer.unlock();
                }
            });
        }
    }

    private class CanvasBufferFormatCallback implements BufferFormatCallback {

        @Override
        public BufferFormat getBufferFormat(int sourceWidth, int sourceHeight) {
            Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
            Platform.runLater(() -> videoSourceRatioProperty.set((float) sourceHeight / (float) sourceWidth));
            return new RV32BufferFormat((int) visualBounds.getWidth(), (int) visualBounds.getHeight());
        }
    }
}
