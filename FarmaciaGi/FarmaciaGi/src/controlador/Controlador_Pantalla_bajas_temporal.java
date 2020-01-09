/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.Bajas; 
import vista.Pantalla_bjas_temporal;

/**
 *
 * @author saube
 */
public class Controlador_Pantalla_bajas_temporal {

    Pantalla_bjas_temporal pantalla_bjas_temporal;
    Bajas bajas;

    public Controlador_Pantalla_bajas_temporal() {
        pantalla_bjas_temporal = new Pantalla_bjas_temporal();
        pantalla_bjas_temporal.setVisible(true);
        pantalla_bjas_temporal.setLocationRelativeTo(null);

        pantalla_bjas_temporal.txtCantidad.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (pantalla_bjas_temporal.txtCodigo.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "<html><h1> No dejar el campo vacio.</h1></html>", "ERROR", JOptionPane.ERROR_MESSAGE);
                        pantalla_bjas_temporal.txtCodigo.requestFocus();
                        return;
                    }

                    if (pantalla_bjas_temporal.txtCantidad.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "<html><h1> No dejar el campo vacio.</h1></html>", "ERROR", JOptionPane.ERROR_MESSAGE);
                        pantalla_bjas_temporal.txtCantidad.requestFocus();
                        return;
                    }

                    if (!isNumeric(pantalla_bjas_temporal.txtCodigo.getText())) {
                        JOptionPane.showMessageDialog(null, "<html><h1> Ingrese un codigo correcto.</h1></html>", "ERROR", JOptionPane.ERROR_MESSAGE);
                        pantalla_bjas_temporal.txtCodigo.requestFocus();
                        return;
                    }

                    if (!isNumeric(pantalla_bjas_temporal.txtCantidad.getText())) {
                        JOptionPane.showMessageDialog(null, "<html><h1> Ingrese un codigo correcto.</h1></html>", "ERROR", JOptionPane.ERROR_MESSAGE);
                        pantalla_bjas_temporal.txtCantidad.requestFocus();
                        return;
                    }

                    DefaultTableModel model = (DefaultTableModel) pantalla_bjas_temporal.jTableBjasT.getModel();
                    model.addRow(new Object[]{pantalla_bjas_temporal.txtCodigo.getText(), pantalla_bjas_temporal.txtCantidad.getText()});
                    pantalla_bjas_temporal.txtCantidad.setText("");
                    pantalla_bjas_temporal.txtCodigo.setText("");
                    pantalla_bjas_temporal.txtCodigo.requestFocus();
                }
            }
        });

        pantalla_bjas_temporal.btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel model = (DefaultTableModel) pantalla_bjas_temporal.jTableBjasT.getModel();
                if (model.getRowCount() <= 0) {
                    JOptionPane.showMessageDialog(null, "<html><h1> no hay datos en la tabla</h1></html>", "ERROR", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                bajas = new Bajas();
                boolean next = bajas.GuadarListaBajas(model);
                if (next) {
                    Clear_Table();
                    JOptionPane.showMessageDialog(null, "<html><h1> Exito</h1></html>", "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "<html><h1> ERROR</h1></html>", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void Clear_Table() {
        DefaultTableModel modelo1 = (DefaultTableModel) pantalla_bjas_temporal.jTableBjasT.getModel();
        int filas = pantalla_bjas_temporal.jTableBjasT.getRowCount();
        for (int i = 0; filas > i; i++) {
            modelo1.removeRow(0);
        }
    }

    private static boolean isNumeric(String cadena) {
        boolean resultado;
        try {
            Integer.parseInt(cadena);
            resultado = true;
        } catch (NumberFormatException excepcion) {
            resultado = false;
        }
        return resultado;
    }

}
