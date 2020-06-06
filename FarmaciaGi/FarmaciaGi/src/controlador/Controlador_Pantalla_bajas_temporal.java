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
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.Bajas;
import modelo.Gastos;
import modelo.Ventas;
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

        pantalla_bjas_temporal.btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (pantalla_bjas_temporal.txtCodigo.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "<html><h1> No dejar el campo vacio.</h1></html>", "ERROR", JOptionPane.ERROR_MESSAGE);
                    pantalla_bjas_temporal.txtCodigo.requestFocus();
                    return;
                }

                if (!new Ventas().existeRegistroProducto(pantalla_bjas_temporal.txtCodigo.getText())) {
                    JOptionPane.showMessageDialog(null, "<html><h1 align='center'>EL PRODUCTO NO EXISTE </h1></html>", "ERROR..", JOptionPane.ERROR_MESSAGE);
                    pantalla_bjas_temporal.txtCantidad.setText("");
                    pantalla_bjas_temporal.txtCodigo.setText("");
                    pantalla_bjas_temporal.txtCodigo.requestFocus();
                    return;
                }

                if (pantalla_bjas_temporal.txtCantidad.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "<html><h1> No dejar el campo vacio.</h1></html>", "ERROR", JOptionPane.ERROR_MESSAGE);
                    pantalla_bjas_temporal.txtCantidad.requestFocus();
                    return;
                }
                
                 if (pantalla_bjas_temporal.txtMotivo.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "<html><h1> No dejar el campo vacio.</h1></html>", "ERROR", JOptionPane.ERROR_MESSAGE);
                    pantalla_bjas_temporal.txtMotivo.requestFocus();
                    return;
                }
                
                Date fecha = pantalla_bjas_temporal.txtFechaC.getDate();
                if (fecha == null) {
                     JOptionPane.showMessageDialog(null, "<html><h1> Ingresar fecha de caducidad.</h1></html>", "ERROR", JOptionPane.ERROR_MESSAGE);
                    //SimpleDateFormat Formato = new SimpleDateFormat("yyyy-MM-dd");
                    //gastosFarmacia.jTableGastos.setModel(new Gastos(Formato.format(fecha)).buscarRegistroEgreso(gastosFarmacia.jTableGastos));
                }
                SimpleDateFormat Formato = new SimpleDateFormat("yyyy-MM-dd");
                DefaultTableModel model = (DefaultTableModel) pantalla_bjas_temporal.jTableBjasT.getModel();
                model.addRow(new Object[]{pantalla_bjas_temporal.txtCodigo.getText(), pantalla_bjas_temporal.txtCantidad.getText(),pantalla_bjas_temporal.txtMotivo.getText(),Formato.format(fecha) });
                pantalla_bjas_temporal.txtCantidad.setText("");
                pantalla_bjas_temporal.txtCodigo.setText("");
                pantalla_bjas_temporal.txtMotivo.setText("");
                pantalla_bjas_temporal.txtCodigo.requestFocus();
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
}
