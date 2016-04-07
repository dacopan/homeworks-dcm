/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dacopancm.dcmqueen.helpers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

/**
 * Algoritmos-UCE
 *
 * @author dacopanCM
 * @copyright Copyright (C) 2014 dacopanCM. All rights reserved.
 * @license GNU General Public License version 3 or later; see LICENSE.txt
 * @website http://dacopancm.hol.es/blog/about Aquí va la magia, muestra la
 * WARNING Sal, de aquí, tu no deberías estar viendo esto.. esto es una ilusión,
 * no existe, esta clase java no esta....
 */
public class Egg extends javax.swing.JFrame {

    /**
     * Creates new form Egg
     */
    public Egg() {
        setUndecorated(true);
        initComponents();
        String txt = "IDAYhPfP+PFUjiZQxAJGls3PP0p3TlcYPIDr2O2TtzREN4Y72snpDXX0xAR1RHLnK4FzAO+5GJhr\n"
                + "0QN3FQAddEHEz2HZTOvFZyEOMWX1QNhWx4RBBny0Z2DxWslIn86hmUM9/FKwAZiLdS+toK9C3ma6\n"
                + "USfvZqm1CzZSyCaU5UgUURviLH78g0azH1o/YKtlf39RiMijIB2sXlE1vPTTX7ZQme4cQb7hyQXV\n"
                + "ephwbgfxBDkOI572GKnUPfvDHACv1BWK1J9iAfW2ZK3puu3JSIt1KBFoosuXj3TvXOV0y0aTrKTf\n"
                + "sD7Nfk1yueKkWuHcdLl53jlA7gMcNia6IY3YwohFBAFwJmYmXJvcPsI4Qn4GTosvlui3a5CHFIVd\n"
                + "HYGSUQ6t7tuSMBMMSL4UgmMMWKxekBbpvaemKTSlnmdDrbjtzAmKHMbaBEu+DD8ZLUpPP8qnKFqH\n"
                + "DYiaZerfYm9ef4gaywuVsPtqyGZpB3ciwPA/zkdz8Zy+rnGRstszaJWYY91ECNlvpfEoPl/ckoxW\n"
                + "WbF7L3CYH3gAF4v6ldVNNpwZy2kXxkf7ZtlfOrSynieh";
        String x = new com.dacopancm.dcmqueen.helpers.EasterEggs("lFj46wEL0WN1DfMQ9PNaAVCzWKnB3W0rAtAu2ZuXOmk=").getEgg(txt);

        String[] l = x.split("_");
        t1.setText(l[0]);
        t2.setText(l[1]);
        t3.setText(l[2]);
        t4.setText(l[3]);
        t5.setText(l[4]);
        t6.setText(l[5]);
        t7.setText(l[6]);
        t8.setText(l[7]);
        setLocationRelativeTo(null);

        jPanel1.registerKeyboardAction(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        }, KeyStroke.getKeyStroke("control W"), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     *
     */
    @SuppressWarnings("unchecked")
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        t8 = new javax.swing.JLabel();
        t1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        t7 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        t6 = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        t3 = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        t4 = new javax.swing.JTextArea();
        jScrollPane4 = new javax.swing.JScrollPane();
        t5 = new javax.swing.JTextArea();
        t2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Easter Egg DCM Queen");
        setBackground(new java.awt.Color(255, 255, 255));
        

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 4, false));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        t8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/dacopancm/dcmqueen/resources/robot.png"))); // NOI18N
        t8.setText("t8");
        t8.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        t8.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanel1.add(t8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        t1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        t1.setText("Sal, de aquí, tu no deberías estar viendo esto");
        jPanel1.add(t1, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 20, -1, -1));
        jPanel1.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 52, 300, 10));

        t7.setText("t7");
        jPanel1.add(t7, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 220, -1, -1));

        jScrollPane1.setBorder(null);
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        t6.setColumns(20);
        t6.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        t6.setLineWrap(true);
        t6.setRows(5);
        t6.setText("t6");
        t6.setBorder(null);
        t6.setMaximumSize(new java.awt.Dimension(604, 94));
        t6.setMinimumSize(new java.awt.Dimension(604, 94));
        t6.setName(""); // NOI18N
        jScrollPane1.setViewportView(t6);
        t6.getAccessibleContext().setAccessibleParent(jPanel1);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 190, 480, 40));

        jScrollPane2.setBorder(null);
        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        t3.setColumns(20);
        t3.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        t3.setLineWrap(true);
        t3.setRows(5);
        t3.setText("t3\n");
        t3.setBorder(null);
        t3.setMaximumSize(new java.awt.Dimension(604, 94));
        t3.setMinimumSize(new java.awt.Dimension(604, 94));
        t3.setName(""); // NOI18N
        jScrollPane2.setViewportView(t3);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 90, 480, 40));

        jScrollPane3.setBorder(null);
        jScrollPane3.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane3.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        t4.setColumns(20);
        t4.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        t4.setLineWrap(true);
        t4.setRows(5);
        t4.setText("t4");
        t4.setBorder(null);
        t4.setMaximumSize(new java.awt.Dimension(604, 94));
        t4.setMinimumSize(new java.awt.Dimension(604, 94));
        t4.setName(""); // NOI18N
        jScrollPane3.setViewportView(t4);

        jPanel1.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 130, 480, 30));

        jScrollPane4.setBorder(null);
        jScrollPane4.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane4.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        t5.setColumns(20);
        t5.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        t5.setLineWrap(true);
        t5.setRows(5);
        t5.setText("t5");
        t5.setBorder(null);
        t5.setMaximumSize(new java.awt.Dimension(604, 94));
        t5.setMinimumSize(new java.awt.Dimension(604, 94));
        t5.setName(""); // NOI18N
        jScrollPane4.setViewportView(t5);

        jPanel1.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 160, 480, 40));

        t2.setText("esto es una ilusión, no existe, esta clase java no esta....");
        jPanel1.add(t2, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 60, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 634, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 307, Short.MAX_VALUE)
        );

        pack();
    }
    public void Runx() {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Egg.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                Egg egg = new Egg();

                egg.setVisible(true);
            }
        });
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Egg.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                Egg egg = new Egg();

                egg.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify                     
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel t1;
    private javax.swing.JLabel t2;
    private javax.swing.JTextArea t3;
    private javax.swing.JTextArea t4;
    private javax.swing.JTextArea t5;
    private javax.swing.JTextArea t6;
    private javax.swing.JLabel t7;
    private javax.swing.JLabel t8;
    // End of variables declaration                   
}
