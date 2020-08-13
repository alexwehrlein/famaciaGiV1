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
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.Retiros;
import tikect.TikectRetiros;
import vista.Pantalla_Retiro;

/**
 *
 * @author alexwehrlein
 */
public class Controlador_Pantalla_Retiros {

    Pantalla_Retiro pantalla_Retiro;
    Retiros retiros;
    TikectRetiros tr;
    String turno;

    public Controlador_Pantalla_Retiros(String turno) {
        this.turno = turno;
        pantalla_Retiro = new Pantalla_Retiro();
        pantalla_Retiro.setVisible(true);
        pantalla_Retiro.setLocationRelativeTo(null);
        pantalla_Retiro.jTableRetiros.setModel(new Retiros().cargarRetiros(pantalla_Retiro.jTableRetiros));

        pantalla_Retiro.jTextFieldRetiro.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (pantalla_Retiro.jTextFieldRetiro.getText().isEmpty()) {
                        //JOptionPane.showMessageDialog(null, "CAMPO VACIO..", "ERROR", JOptionPane.ERROR_MESSAGE);
                        pantalla_Retiro.jTextFieldRetiro.requestFocus();
                        return;
                    }

                    if (!pantalla_Retiro.jTextFieldRetiro.getText().matches("^[0-9]+([.])?([0-9]+)?$")) {
                        JOptionPane.showMessageDialog(null, "<html><h1 align='center'>INGERSE SOLO NUMEROS </h1></html>", "ERROR..", JOptionPane.ERROR_MESSAGE);
                        pantalla_Retiro.jTextFieldRetiro.setText("");
                        return;
                    }
                    float cantidad = Float.parseFloat(pantalla_Retiro.jTextFieldRetiro.getText());
                    retiros = new Retiros(cantidad, turno);
                    String[] next = retiros.registrarRetiro();
                    if (next[0] == "0") {
                        JOptionPane.showMessageDialog(null, "<html><h1 align='center'> Exito al guardar retiro </h1></html>", "success", JOptionPane.INFORMATION_MESSAGE);
                        retiros = new Retiros(Integer.parseInt(next[1]));
                        String arr[] = retiros.tikect();
                        tr = new TikectRetiros();
                        tr.TikectRetiros(arr);
                        pantalla_Retiro.jTextFieldRetiro.setText("");
                        Clear_Table();
                        pantalla_Retiro.jTableRetiros.setModel(new Retiros().cargarRetiros(pantalla_Retiro.jTableRetiros));
                    } else {
                        JOptionPane.showMessageDialog(null, "<html><h1 align='center'> Error </h1></html>", "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        pantalla_Retiro.txtFolio.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e
            ) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (pantalla_Retiro.txtFolio.getText() != "") {
                        Clear_Table();
                        int id = Integer.parseInt(pantalla_Retiro.txtFolio.getText());
                        pantalla_Retiro.jTableRetiros.setModel(new Retiros(id).buscarRetiro(pantalla_Retiro.jTableRetiros));
                    }
                }
            }
        });

        pantalla_Retiro.jDateChooserFechaRetiro.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                Date fecha = pantalla_Retiro.jDateChooserFechaRetiro.getDate();
                if (fecha != null) {
                    Clear_Table();
                    SimpleDateFormat Formato = new SimpleDateFormat("yyyy-MM-dd");
                    pantalla_Retiro.jTableRetiros.setModel(new Retiros(Formato.format(fecha)).buscarRetiro(pantalla_Retiro.jTableRetiros));
                }
            }
        });

        pantalla_Retiro.btnTikect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int fila;
                String arr[] = {"", "", "", ""};
                try {
                    fila = pantalla_Retiro.jTableRetiros.getSelectedRow();
                    if (fila == -1) {
                        JOptionPane.showMessageDialog(null, "<html><h1 align='center'>No ha seleccionado ninguna fila. </h1></html>", "ERROR", JOptionPane.ERROR_MESSAGE);
                    } else {
                        String id =  String.valueOf(pantalla_Retiro.jTableRetiros.getValueAt(fila, 0));
                        String retiro = String.valueOf(pantalla_Retiro.jTableRetiros.getValueAt(fila, 1));
                        arr[0] = id; arr[1] = retiro;
                        tr = new TikectRetiros();
                        tr.TikectRetiros(arr);
                    }
                } catch (Exception ex) {
                    System.out.println(ex);
                }
            }
        });

    }

    private void Clear_Table() {
        DefaultTableModel modelo = (DefaultTableModel) pantalla_Retiro.jTableRetiros.getModel();
        int filas = pantalla_Retiro.jTableRetiros.getRowCount();
        for (int i = 0; filas > i; i++) {
            modelo.removeRow(0);
        }
    }

}
