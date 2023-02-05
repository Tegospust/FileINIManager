package com.ounis.fileinimanager;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

import com.ounis.utils.FramesUtils;
import javax.swing.DefaultListModel;
import com.ounis.fileinistruct.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Collection;
import java.util.Enumeration;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.plaf.basic.BasicTreeUI;

/**
 *
 * @author AndroidDev
 */
public class MainFrame extends javax.swing.JFrame {

    FileINIManager fINIManager;
//    https://stackoverflow.com/questions/17131589/how-to-change-non-editable-generated-code-in-netbeans
    DefaultListModel<FINILine> lmSections;
    DefaultListModel<FINILine> lmItems;
    
/**
 * obsługa zdarzenia kliknięcia na przycisku btnSave
 */
    class btnSaveClick implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showConfirmDialog(null, 
                    "kliknięto", 
                    "akcja", JOptionPane.YES_NO_OPTION, 
                    JOptionPane.INFORMATION_MESSAGE);
        }
    
    }
    
    /**
     * obsługa dwukliku na liście sekcji
     */
    class lstSectionMouseAdapter extends MouseAdapter {
        
        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 2) {
                if (lstSections.getModel().getSize() == 0)
                    return; // pusta lista to nic nie robimy
                FINILine selSect = lmSections.get(lstSections.getSelectedIndex());
                System.out.printf("Line num. in .ini file: #%d\n",selSect.getLineNum());
                if (!lmItems.isEmpty()) {
                    lmItems.clear();
                    clearKeyValueEditPanel(1);
                }
                lmItems.addAll(fINIManager.getItems4Section(selSect.getSection()));
                
            }
        }
    }
    
    /**
     * obsługa dwukliku na liście pozycji sekcji klucz=wartość
     */
    class lstItemsMouseAdapter extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent me) {
            if(me.getClickCount() == 2) {
                clearKeyValueEditPanel(1);
                if (lstItems.getModel().getSize() == 0) 
                    return;
                String sect = getChosenSection(lstSections);
                String item = getChoosenItem(lstItems);
                edKey.setText(item);
                edValue.setText(fINIManager.getValue4SectKey(sect, item));
                
            }
        }
    }
    /**
     * czyszczemoe panelu edycji wartośco klucza
     * @param i 
     */
    private void clearKeyValueEditPanel(int i) {
        this.edKey.setText("");
        this.edValue.setText("");
    }
    
    
    /**
     * 
     * @param aList lista 
     * @return nazwa sekcji zaznaczona na liście sekcji
     */
    private String getValueFromList(JList<FINILine> aList) {
        String result = null;
        if (aList.getModel().getSize() > 0) 
            result = aList.getModel().getElementAt(aList.getSelectedIndex()).getSection();
        return result;
    }
    
    /**
     * zwraca wybraną pozycję z listy sekcji
     * @param aList
     * @return 
     */
    private String getChosenSection(JList<FINILine> aList) {
        return getValueFromList(aList);
    }
    
    /**
     * zwraca wybramą pozycję z listy sekcji
     * @param aList
     * @return 
     */
    private String getChoosenItem(JList<FINILine> aList) {
        return getValueFromList(aList);
    }
    
    
    /**
     * Creates new form MainFrame
     */
    public MainFrame(String aFileName) {
        initComponents();
        FramesUtils.centerWindow(this, -1, -1);
        this.setResizable(false);
        this.setTitle(aFileName);
        
        fINIManager = new FileINIManager(aFileName);
        if (fINIManager.loadFromFile() > -1) {
//          lstSections SETUP
            lmSections = new DefaultListModel<FINILine>();
            lmSections.addAll(fINIManager.getSections());
            
            for(FINILine finil: fINIManager.getSections())
                lmSections.addElement(finil);
            lstSections.setModel(lmSections);
            
//            Enumeration elements = fINIManager.getSections().elements();
//            while(elements.hasMoreElements()) {
//                lmSections.addElement((FINILine)elements.nextElement());
//            }
            lstSections.addMouseListener(new lstSectionMouseAdapter());
            
//            lstItems SETUP
            lmItems = new DefaultListModel<>();
            lstItems.setModel(lmItems);
            lstItems.addMouseListener(new lstItemsMouseAdapter());
            
//            btnSave SETUP
            btnSave.addActionListener(new btnSaveClick());
            
        }
        else
            JOptionPane.showMessageDialog(null, "Problem z wczytaniem pliku ".concat(aFileName), "Błąd!", JOptionPane.ERROR_MESSAGE);
//            JOptionPane.showMessageDialog(null, "Problem z wczytaniem pliku: ".concat(aFileName), JOptionPane.ERROR_MESSAGE);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        lstItems = new javax.swing.JList<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        lstSections = new javax.swing.JList<FINILine>();
        lblSections = new javax.swing.JLabel();
        lblItems = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        btnSave = new javax.swing.JButton();
        lblKey = new javax.swing.JLabel();
        edKey = new javax.swing.JTextField();
        lblValue = new javax.swing.JLabel();
        edValue = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lstItems.setBackground(new java.awt.Color(255, 255, 204));
        lstItems.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jScrollPane1.setViewportView(lstItems);

        lstSections.setBackground(new java.awt.Color(255, 255, 204));
        lstSections.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lstSections.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        lstSections.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                lstSectionsPropertyChange(evt);
            }
        });
        jScrollPane2.setViewportView(lstSections);

        lblSections.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblSections.setText("Sekcje");

        lblItems.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblItems.setText("Klucze i wartości");

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        btnSave.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnSave.setText("Zapisz");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        lblKey.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblKey.setText("Klucz");

        edKey.setEditable(false);
        edKey.setBackground(new java.awt.Color(204, 255, 255));
        edKey.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        edKey.setFocusable(false);

        lblValue.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblValue.setText("Wartość");

        edValue.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnSave))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblKey)
                            .addComponent(edKey, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(edValue)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(lblValue)
                                .addGap(0, 144, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblKey)
                    .addComponent(lblValue))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(edKey, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(edValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addComponent(btnSave)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(lblSections)
                .addGap(267, 267, 267)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 373, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblItems)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(20, 20, 20)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(813, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSections)
                    .addComponent(lblItems))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 392, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(26, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(35, 35, 35)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 392, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(26, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSaveActionPerformed

    private void lstSectionsPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_lstSectionsPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_lstSectionsPropertyChange

    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new MainFrame().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSave;
    private javax.swing.JTextField edKey;
    private javax.swing.JTextField edValue;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblItems;
    private javax.swing.JLabel lblKey;
    private javax.swing.JLabel lblSections;
    private javax.swing.JLabel lblValue;
    private javax.swing.JList<FINILine> lstItems;
    private javax.swing.JList<FINILine> lstSections;
    // End of variables declaration//GEN-END:variables
}
