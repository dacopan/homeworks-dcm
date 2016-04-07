package com.dacopancm.dcmqueen.conmponents;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.geom.Ellipse2D;
import org.jdesktop.swingx.JXBusyLabel;
import org.jdesktop.swingx.painter.BusyPainter;

/**
 * Algoritmos-UCE
 *
 * @author dacopanCM
 * @copyright Copyright (C) 2014 dacopanCM. All rights reserved.
 * @license GNU General Public License version 3 or later; see LICENSE.txt
 * @website http://dacopancm.hol.es/blog/about ProgressRing de color vileta con
 * un mensaje de carga
 */
public class DCMBusyLabel extends JXBusyLabel {

    public DCMBusyLabel() {
        super(new Dimension(150, 150));

        BusyPainter painter = getBusyPainter();
        painter.setPointShape(new Ellipse2D.Float(0, 0, 15, 15));

        painter.setTrailLength(8);
        painter.setPoints(15);
        painter.setHighlightColor(new java.awt.Color(102, 0, 102));
        //painter.setBaseColor(new java.awt.Color(240, 240, 240));

        //text apperence
        setFont(new java.awt.Font("Segoe UI", 0, 22)); // NOI18N
        setForeground(Color.WHITE);
        setText("Killing Queens, Wait");
        setToolTipText("Killing Queens... Wait...");
        setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        setBusyPainter(painter);
    }

}
