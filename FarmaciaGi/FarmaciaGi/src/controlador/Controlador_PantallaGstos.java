/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.Conexion;
import modelo.Empleado;
import modelo.Gastos;
import tikect.TikectGasto;
import vista.Pantalla_GastosFarmacia;

/**
 *
 * @author saube
 */
public class Controlador_PantallaGstos {

    Pantalla_GastosFarmacia gastosFarmacia;
    Gastos gastos;
    String turno;
    TikectGasto tikectGastos;

    public Controlador_PantallaGstos(String turnoE) {
        this.turno = turnoE;
        gastosFarmacia = new Pantalla_GastosFarmacia();
        gastosFarmacia.setVisible(true);
        gastosFarmacia.setLocationRelativeTo(null);
        gastosFarmacia.jTableGastos.setModel(new Gastos().cargarRegistroEgreso(gastosFarmacia.jTableGastos));

        gastosFarmacia.jButtonRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (gastosFarmacia.jTextFieldDescripcionGastos.getText().isEmpty() || gastosFarmacia.jTextFieldTotalGastos.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "No dejar campos en blanco");

                } else {
                    boolean pass = validarFormulario(gastosFarmacia.jTextFieldTotalGastos.getText());

                    if (pass) {
                        String descripcion = gastosFarmacia.jTextFieldDescripcionGastos.getText();
                        double monto = Double.valueOf(gastosFarmacia.jTextFieldTotalGastos.getText());

                        gastos = new Gastos(descripcion, monto, turno);

                        if (gastos.registrarGastos()) {
                            JOptionPane.showMessageDialog(null, "Gastos Registrados");
                            limpiarCampos();
                            Clear_Table();
                            gastosFarmacia.jTableGastos.setModel(new Gastos().cargarRegistroEgreso(gastosFarmacia.jTableGastos));
                            gastosFarmacia.jTextFieldTotalGastos.setBackground(Color.WHITE);
                            tikectGastos = new TikectGasto();
                            tikectGastos.TikectGasto(descripcion, monto);
                            
                            

                        } else {

                            JOptionPane.showMessageDialog(null, "error", "ERROR", JOptionPane.ERROR_MESSAGE);
                        }
                    }

                }
            }
        });

        gastosFarmacia.jTextFieldDescripcionGastos.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e
            ) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    gastosFarmacia.jTextFieldTotalGastos.requestFocus();
                }
            }
        });

        gastosFarmacia.jTextFieldTotalGastos.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e
            ) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    gastosFarmacia.jButtonRegistrar.requestFocus();
                }
            }
        });

        gastosFarmacia.jButtonRegistrar.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e
            ) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (gastosFarmacia.jTextFieldDescripcionGastos.getText().isEmpty() || gastosFarmacia.jTextFieldTotalGastos.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "No dejar campos en blanco");

                    } else {
                        boolean pass = validarFormulario(gastosFarmacia.jTextFieldTotalGastos.getText());

                        if (pass) {
                            String descripcion = gastosFarmacia.jTextFieldDescripcionGastos.getText();
                            double monto = Double.valueOf(gastosFarmacia.jTextFieldTotalGastos.getText());

                            gastos = new Gastos(descripcion, monto, turno);

                            if (gastos.registrarGastos()) {
                                JOptionPane.showMessageDialog(null, "Gastos Registrados");
                                limpiarCampos();
                                Clear_Table();
                                gastosFarmacia.jTableGastos.setModel(new Gastos().cargarRegistroEgreso(gastosFarmacia.jTableGastos));
                                gastosFarmacia.jTextFieldTotalGastos.setBackground(Color.WHITE);
                                gastosFarmacia.jTextFieldDescripcionGastos.requestFocus();

                            } else {

                                JOptionPane.showMessageDialog(null, "error", "ERROR", JOptionPane.ERROR_MESSAGE);
                            }
                        }

                    }
                }
            }
        });

    }

    public boolean validarFormulario(String gastos) {
        boolean next = false;
        Pattern patGastos = Pattern.compile("^[0-9]+([.])?([0-9]+)?$");
        Matcher matGastos = patGastos.matcher(gastos);

        if (matGastos.matches()) {
            next = true;

        } else {
            JOptionPane.showMessageDialog(null, "Solo Numeros");
            gastosFarmacia.jTextFieldTotalGastos.setBackground(Color.red);
        }

        return next;
    }

    public void limpiarCampos() {
        gastosFarmacia.jTextFieldDescripcionGastos.setText("");
        gastosFarmacia.jTextFieldTotalGastos.setText("");
    }

    private void Clear_Table() {
        DefaultTableModel modelo = (DefaultTableModel) gastosFarmacia.jTableGastos.getModel();
        int filas = gastosFarmacia.jTableGastos.getRowCount();
        for (int i = 0; filas > i; i++) {
            modelo.removeRow(0);
        }
    }
}
