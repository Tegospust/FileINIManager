package com.ounis.fileinimanager;


import javax.security.auth.callback.ConfirmationCallback;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author AndroidDev
 */
public class FINIManApp {
    
    public static void main(String... args) {
        System.out.print("\nSURFACE!!!\n");
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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>        
        //
        //   spolszczenie przycisków okien dialogowych
        //
        //  http://www.java2s.com/Tutorial/Java/0240__Swing/SettingJOptionPanebuttonlabelstoFrench.htm
        UIManager.put("OptionPane.noButtonText", "Nie");
        UIManager.put("OptionPane.yesButtonText", "Tak");
        UIManager.put("OptionPane.cancelButtonText", "Anuluj");
        UIManager.put("OptionPane.okButtonText", "OK");
        
        /* Create and display the form */
        if (args.length > 0) {
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    System.out.println("w mordeczke..");

                    new MainFrame(args[0]).setVisible(true);
                }
            });        
        }
        else 
            JOptionPane.showMessageDialog(null, 
                    "Brak parametru z nazwą pliku!\nUżycie: FileINIManager.jar <plik.ini>", 
                    "Błąd!", JOptionPane.ERROR_MESSAGE);
    }
}
