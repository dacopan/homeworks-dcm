/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dacopancm.dcmqueen.conmponents;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * Algoritmos-UCE
 *
 * @author dacopanCM
 * @copyright Copyright (C) 2014 dacopanCM. All rights reserved.
 * @license GNU General Public License version 3 or later; see LICENSE.txt
 * @website http://dacopancm.hol.es/blog/about Reina que será agregada al
 * ChessBoard, contiene un png de una reina
 */
public class Queen extends JLabel {

    public static ImageIcon icon;
    private boolean draggable=true;
    private Point point;

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public boolean isDraggable() {
        return draggable;
    }

    public void setDraggable(boolean enDrag) {
        this.draggable = enDrag;
    }

    @SuppressWarnings("null")
    public Queen(int xy) {
        if (icon == null) {
            icon = new javax.swing.ImageIcon();
            BufferedImage img = null;
            try {
                img = ImageIO.read(getClass().getResourceAsStream("/com/dacopancm/dcmqueen/resources/queen2.png"));
            } catch (IOException e) {
                System.out.println(e);
            }

            Image dimg = null;
            if (dimg == null) {
                dimg = img.getScaledInstance(xy, xy,
                        Image.SCALE_SMOOTH);
            }
            if (dimg != null) {
                icon = new ImageIcon(dimg);
            }
        }
        setIcon(icon);
        setPreferredSize(new Dimension(xy, xy));
        setBounds(0, 0, xy, xy);
        // setBackground(Color.CYAN);
    }

    @Override
    public String toString() {
        return "Queen: x:" + getX() + " y:" + getY() + " w:" + getWidth() + " h:" + getHeight();
    }

}
