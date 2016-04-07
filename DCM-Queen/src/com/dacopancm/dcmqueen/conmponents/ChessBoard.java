/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dacopancm.dcmqueen.conmponents;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.jdesktop.swingx.JXPanel;

/**
 * Algoritmos-UCE
 *
 * @author dacopanCM
 * @copyright Copyright (C) 2014 dacopanCM. All rights reserved.
 * @license GNU General Public License version 3 or later; see LICENSE.txt
 * @website http://dacopancm.hol.es/blog/about Componente ChessBoard que puede
 * ser agregado en un Jpanel
 */
public class ChessBoard extends javax.swing.JLayeredPane {

    /**
     * Creates new form ChessBoard
     */
    public static final int SIZE = 502;
    public final int COUNT = 8;
    public final int TILE_SIZE = SIZE / COUNT;
    public final Color RED = Color.RED;
    public final Color BLUE = Color.BLUE;

    public static Cell[][] tiles;

    /**
     * prueba del chessboard
     *
     * @param args
     */
    public static void main(String[] args) {
        JFrame f = new JFrame();
        ChessBoard c = new ChessBoard();

        f.setTitle("Chessboard Tile Click");
        f.setContentPane(c);
        f.pack();
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);

        // c.createChessBoard();
        //c.addAtCell(new Point(0, 0), new Queen(c.TILE_SIZE));
        c.addCom(0, 0, new Queen(c.TILE_SIZE));
    }

    /**
     * constructor
     */
    public ChessBoard() {
        initComponents();
        wrap.setPreferredSize(new Dimension(496, 496));
        System.out.println(TILE_SIZE);
        System.out.println(62 * 8);
//        wrap.addMouseListener(new MouseAdapter() {
//            public void mouseClicked(MouseEvent e) {
//                Point p = e.getPoint();
//
//                for (int i = 0; i < COUNT; i++) {
//                    for (int j = 0; j < COUNT; j++) {
//                        Cell tile = tiles[i][j];
//
//                        if (tile.contains(p)) {
//                            JOptionPane.showMessageDialog(null,
//                                    String.format("Clicked Tile: %s", tile));
//                            break;
//                        }
//                    }
//                }
//            }
//        });
        createChessBoard();
        progressRing = new com.dacopancm.dcmqueen.conmponents.DCMBusyLabel();
    }

    /**
     * agrega un componente en la celda x y
     *
     * @param x row
     * @param y col
     * @param c component
     */
    public void addCom(int x, int y, Queen c) {
        // c.setBounds(0, 0, TILE_SIZE, TILE_SIZE);
        tiles[x][y].add(c);
        tiles[x][y].moveToFront(c);

    }

    /**
     * remueve un componente en la celda x y
     *
     * @param x row
     * @param y col
     * @param c component
     */
    public void delCom(int x, int y, Component c) {
        tiles[x][y].removeCom(c);
    }

    /**
     * Limapia el tablero de reinas
     */
    public void removeAllQueens() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                for (Component c : tiles[i][j].getComponents()) {
                    if (c instanceof Queen) {
                        tiles[i][j].remove(c);
                    }
                }
            }
        }
    }

    /**
     * Elimina la reina y UserCell en el componente indicado
     *
     * @param cc container donde remover
     */
    public void cleenCell(Container cc) {
        for (Component c : cc.getComponents()) {
            if (c instanceof Queen || c instanceof UserCell) {
                cc.remove(c);
            }
        }
    }

    /**
     * agrega un componente en el Punto p indicado
     *
     * @param p punto donde agregar
     * @param cl componente a agregar
     * @return true si se agregó
     */
    public boolean addAtCell(Point p, Queen cl) {
        if (((Cell) wrap.getComponentAt(p)) != null) {
            ((Cell) wrap.getComponentAt(p)).add(cl);
            ((Cell) wrap.getComponentAt(p)).moveToFront(cl);
            this.p = p;
            return true;
        } else {
            return false;
        }
    }
    Point p;

    /**
     * obtiene el punto donde el usuario coloco la reina
     *
     * @return punto (x,y) de la reina
     */
    public Point getUserSelectPoint() {

        if (((Cell) wrap.getComponentAt(p)) != null) {
            Cell c = ((Cell) wrap.getComponentAt(p));
            UserCell resalt = new UserCell(c.getWidth());
            c.add(resalt);
            c.moveToFront(resalt);

            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (tiles[i][j].equals(c)) {
                        System.out.println("punto seleccionado: (" + i + "," + j + ")");
                        return new Point(i, j);
                    }
                }
            }
        }
        return new Point(0, 0);
    }

    /**
     * crea las celdas del ChessBoard
     */
    private void createChessBoard() {

        tiles = new Cell[COUNT][COUNT];
        ImageIcon i1 = new javax.swing.ImageIcon(getClass().getResource("/com/dacopancm/dcmqueen/resources/cell1.png"));
        ImageIcon i2 = new javax.swing.ImageIcon(getClass().getResource("/com/dacopancm/dcmqueen/resources/cell2.png"));
        int u = 0;
        boolean flip = false;
        for (int j = 0; j < COUNT; j++) {
            for (int i = 0; i < COUNT; i++) {

                tiles[i][j] = new Cell(i * TILE_SIZE, j * TILE_SIZE,
                        TILE_SIZE, TILE_SIZE, ((flip = !flip) ? i1 : i2));
                tiles[i][j].setId(i + j);
                wrap.add(tiles[i][j]);
                //tiles[i][j].setVisible(true);
            }
            flip = !flip;
        }

        this.repaint();
        //this.list();
        JLabel j = new JLabel("mayra");
        j.setBounds(0, 0, 20, 20);
        wrap.add(j);//*/ 
    }

    /**
     * guarda una captura del tablero (.jpg)
     *
     * @param filePath archivo donde se gaurdará la captura
     * @return
     */
    public boolean getImg(String filePath) {
        BufferedImage bufImage = new BufferedImage(printer.getSize().width, printer.getSize().height, BufferedImage.TYPE_INT_RGB);
        printer.paint(bufImage.createGraphics());
        File imageFile = new File(filePath);
        try {
            imageFile.createNewFile();
            ImageIO.write(bufImage, "jpeg", imageFile);
        } catch (IOException ex) {
            System.out.println("Captura no guardada: " + ex.getMessage());
        }
        return true;
    }

    /**
     * muestra/oculta el progressring con overlay sobre el tablero
     *
     * @param b true muestra progressring
     * @param text
     */
    public void toggleProgres(boolean b, String text) {

        if (b) {
            int progDIM = 200;
            int x = this.getWidth();
            int y = this.getHeight();

            if (overlay == null) {
                overlay = new JXPanel();
                overlay.setBackground(new java.awt.Color(0, 0, 0, 100));
                overlay.setBounds(0, 0, this.getWidth(), this.getHeight());

                overlay.setInheritAlpha(false);
                overlay.setOpaque(true);
                overlay.setAlpha(.6f);
                JPanel j = new JPanel();
                j.setBackground(new java.awt.Color(0, 0, 0));
                j.setBounds(0, 0, x, y);
                j.setPreferredSize(new Dimension(x, y));
                overlay.add(j);
            }

            System.out.println(overlay.getEffectiveAlpha());
            //overlay.

            this.remove(overlay);
            this.remove(progressRing);

            this.add(overlay);
            this.moveToFront(overlay);

            progressRing = new com.dacopancm.dcmqueen.conmponents.DCMBusyLabel();
            progressRing.setBounds((x - progDIM) / 2, (y - progDIM) / 2, progDIM, progDIM);
            progressRing.setEnabled(b);
            progressRing.setBusy(b);
            progressRing.setText(text);

            this.add(progressRing);
            this.moveToFront(progressRing);

            //progressRing.setText("killing queens... wait.");
        } else {
            this.remove(progressRing);
            this.remove(overlay);
        }
        overlay.repaint();
        this.repaint();
    }

    public void gameEnd(String txt) {
       // progressRing.setBusy(true);
        toggleProgres(true, txt);
    
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        printer = new javax.swing.JPanel();
        wrap = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(560, 540));
        setLayout(new java.awt.BorderLayout());

        printer.setBackground(new java.awt.Color(255, 255, 102));
        printer.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        wrap.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
        wrap.setPreferredSize(new java.awt.Dimension(500, 500));

        javax.swing.GroupLayout wrapLayout = new javax.swing.GroupLayout(wrap);
        wrap.setLayout(wrapLayout);
        wrapLayout.setHorizontalGroup(
            wrapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 490, Short.MAX_VALUE)
        );
        wrapLayout.setVerticalGroup(
            wrapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 490, Short.MAX_VALUE)
        );

        printer.add(wrap, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 496, 496));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/dacopancm/dcmqueen/resources/bg2.png"))); // NOI18N
        jLabel1.setText("jLabel1");
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel1.setMaximumSize(new java.awt.Dimension(650, 650));
        jLabel1.setMinimumSize(new java.awt.Dimension(650, 650));
        jLabel1.setPreferredSize(new java.awt.Dimension(650, 650));
        printer.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 560, 540));

        add(printer, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel printer;
    private javax.swing.JPanel wrap;
    // End of variables declaration//GEN-END:variables
    com.dacopancm.dcmqueen.conmponents.DCMBusyLabel progressRing;
    org.jdesktop.swingx.JXPanel overlay;
}
