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
import modelo.Bajas;
import vista.Pantalla_bajas;

/**
 *
 * @author alexwehrlein
 */
public class Controlador_Pantalla_Bajas {

    Pantalla_bajas pantalla_bajas;
    Bajas bajas;
    int id_empleado;
    
    public Controlador_Pantalla_Bajas(int id) {
        this.id_empleado = id;
        pantalla_bajas = new Pantalla_bajas();
        pantalla_bajas.setVisible(true);
        pantalla_bajas.setLocationRelativeTo(null);

        pantalla_bajas.tctCodigo.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    String codigo = pantalla_bajas.tctCodigo.getText();
                    bajas = new Bajas(codigo);
                    String existencias = bajas.Producto();
                    pantalla_bajas.txtExistencias.setText(existencias);
                    pantalla_bajas.txtPiezas.requestFocus();
                }
            }
        });

        pantalla_bajas.btnBajas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (pantalla_bajas.txtPiezas.getText().isEmpty()) {
                    pantalla_bajas.txtPiezas.requestFocus();
                    return;
                }
                String codigo = pantalla_bajas.tctCodigo.getText();
                int existencias = Integer.parseInt(pantalla_bajas.txtExistencias.getText());
                int piezas = Integer.parseInt(pantalla_bajas.txtPiezas.getText());
                if (!String.valueOf(piezas).matches("[0-9]*")) {
                    JOptionPane.showMessageDialog(null, "<html><h1 align='center'>Ingrese una cantidad correcta.</h1></html>", "ERROR", JOptionPane.ERROR_MESSAGE);
                    pantalla_bajas.txtPiezas.requestFocus();
                    return;
                }
                if (piezas > existencias) {
                    JOptionPane.showMessageDialog(null, "<html><h1 align='center'>No puede dar de baja mas de lo que existe </h1></html>", "ERROR", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int inventario = existencias - piezas;
                bajas = new Bajas(codigo, piezas, inventario , id_empleado);
                //bajas.actualizarExistencias();
                boolean flag = bajas.insertarBajas();
                if (flag) {
                    pantalla_bajas.tctCodigo.setText("");
                    pantalla_bajas.txtExistencias.setText("");
                    pantalla_bajas.txtPiezas.setText("");
                    JOptionPane.showMessageDialog(null, "<html><h1 align='center'>El piezas se dieron de baja </h1></html>", "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
                }else{
                    JOptionPane.showMessageDialog(null, "<html><h1 align='center'>Error </h1></html>", "ERROR", JOptionPane.ERROR_MESSAGE);
                }

            }
        });
    }
}
