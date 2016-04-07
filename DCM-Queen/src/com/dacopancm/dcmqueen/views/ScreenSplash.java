/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dacopancm.dcmqueen.views;

import java.awt.*;
import java.awt.SplashScreen;

/**
 * Algoritmos-UCE
 *
 * @author dacopanCM
 * @copyright Copyright (C) 2014 dacopanCM. All rights reserved.
 * @license GNU General Public License version 3 or later; see LICENSE.txt
 * @website http://dacopancm.hol.es/blog/about Se encarga de animar el
 * splashCreen, y mostrar los mensajes de carga
 */
public final class ScreenSplash {

    final boolean enabled = true;
    final SplashScreen splash;
    //texto que se muestra a medida que se va cargando el screensplah
    final String[] texto = {"Recalibración del motor de motivación", "Hiperactivando reinas", "Reprogramación de los mamíferos",
        "Creando reinas pintorescas ", "Calibrando la personalidad matriz", "Todavía Hiperactivando reinas",
        "Generando Algoritmo de cotorreo", "Difundiendo rumores de Algoritmos I", "Cafeinizando el cuerpo estudiantil",
        "Cargando algoritmo del espíritu académico."};

    /**
     * obtiene el splashScreen de la app
     */
    public ScreenSplash() {
        splash = SplashScreen.getSplashScreen();
    }

    /**
     * anima el SplashCreen
     */
    public void animar() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                MainView.main(null);
                if (splash != null && enabled) {
                    Graphics2D g = splash.createGraphics();
                    for (int i = 1; i < texto.length; i++) {
                        //se pinta texto del array
                        g.setColor(new Color(0, 33, 75));//color de fondo
                        g.fillRect(227, 333, 260, 12);//para tapar texto anterior
                        g.setColor(new Color(139, 220, 248));//color de texto 
                        g.drawString(texto[i - 1] + "...", 235, 343);

                        g.setColor(Color.blue);//color de barra de progeso
                        g.fillRect(204, 260, (i * 307 / texto.length), 12);//la barra de progreso
                        //se pinta una linea segmentada encima de la barra verde
                        float dash1[] = {2.0f};
//                        BasicStroke dashed = new BasicStroke(9.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 5.0f, dash1, 0.0f);
//                        g.setStroke(dashed);
//                        g.setColor(Color.BLUE);//color de barra de progeso
//                        g.setColor(new Color(4, 52, 101));
//                        g.drawLine(205, 270, 510, 270);
                        //se actualiza todo
                        splash.update();
                        try {
                            Thread.sleep(321);
                        } catch (InterruptedException e) {
                        }
                    }
                    splash.close();
                }
                //una vez terminada la animación se muestra la aplicación principal
                try {
                    // MainView.main(null);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

            }
        }).start();
    }

}
