/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.dacopancm.socketdcm.helper;

import io.dacopancm.socketdcm.view.AudioRecDialogController;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;

/**
 *
 * @author dtic
 */
public class Cronometer implements Runnable {

    public boolean isRunning;
    AudioRecDialogController controller;
    String ss = "00", mm = "00", hh = "00";

    public Cronometer(AudioRecDialogController controller) {
        this.controller = controller;
    }

    @Override
    public void run() {
        Integer s = 0, m = 0, h = 0;
        isRunning = true;
        Platform.runLater(() -> {
            if (isRunning) {
                controller.setConunterText(hh + ":" + mm + ":" + ss);
            }
        });
        while (isRunning) {
            try {
                Thread.sleep(500);
                if (!isRunning) {
                    return;
                }
                Thread.sleep(500);
                if (!isRunning) {
                    return;
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(AudioRecDialogController.class.getName()).log(Level.SEVERE, null, ex);
            }
            s += 1;
            if (s == 60) {
                s = 0;
                m += 1;
                if (m == 60) {
                    m = 0;
                    h++;
                }
            }
            hh = (h < 10) ? "0" + h : h.toString();
            mm = (m < 10) ? "0" + m : m.toString();
            ss = (s < 10) ? "0" + s : s.toString();
            //Colocamos en la etiqueta la informacion
            Platform.runLater(() -> {
                if (isRunning) {
                    controller.setConunterText(hh + ":" + mm + ":" + ss);
                }
            });

        }

    }

    public void stopCronometer() {
        isRunning = false;
    }

    public void restart() {
    }
}
