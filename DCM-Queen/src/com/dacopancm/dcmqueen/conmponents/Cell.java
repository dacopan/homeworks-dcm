/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dacopancm.dcmqueen.conmponents;

import java.awt.Component;
import java.awt.Dimension;
import javax.swing.ImageIcon;

/**
 * Algoritmos-UCE
 *
 * @author dacopanCM
 * @copyright Copyright (C) 2014 dacopanCM. All rights reserved.
 * @license GNU General Public License version 3 or later; see LICENSE.txt
 * @website http://dacopancm.hol.es/blog/about Celda que será agregada al
 * ChessBoard
 */
public class Cell extends javax.swing.JLayeredPane {

    int id = 0;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * Creates new form Cell
     *
     * @param x pos X
     * @param y pos Y
     * @param width width
     * @param heigth height
     * @param i type de celda obscuro/claro [type=ImageIcon]
     */
    public Cell(int x, int y, int width, int heigth, ImageIcon i) {
        initComponents();
        cell.setIcon(null); // NOI18N
        cell.setIcon(i); // NOI18N

        setPreferredSize(new Dimension(width, heigth));
        setBounds(x, y, width, heigth);
        /*setBorder(new EmptyBorder(0, 0, 0, 0));
         GroupLayout gp = new GroupLayout(this);*/

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cell = new javax.swing.JLabel();

        setAlignmentX(0.0F);
        setAlignmentY(0.0F);

        cell.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/dacopancm/dcmqueen/resources/cell1.png"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(cell)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(cell)
        );
        setLayer(cell, javax.swing.JLayeredPane.DEFAULT_LAYER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel cell;
    // End of variables declaration//GEN-END:variables
/**
     * agrega un componente a la celda y lo sobrepone
     *
     * @param c componente a agregar
     */
    void addComp(Component c) {
        //    add(cell, new org.netbeans.lib.awtextra.AbsoluteConstraints(1, 1, -1, -1));
        try {
            add(c);
            this.moveToFront(c);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * remueve un componente de una celda
     *
     * @param c
     */
    void removeCom(Component c) {
        try {
            this.remove(c);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
