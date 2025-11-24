/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

/**
 * Clase principal que inicia la aplicación Megaferia.
 * @author Alexander
 */
import com.formdev.flatlaf.FlatDarkLaf;
import javax.swing.UIManager;

public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                MegaferiaFrame vista = new MegaferiaFrame();
                vista.setVisible(true);
            }
        });
    }
}