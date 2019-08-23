/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.AWTEventMulticaster;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
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

    public Controlador_PantallaGstos(String turnoE , String pc) {
        this.turno = turnoE;
        gastosFarmacia = new Pantalla_GastosFarmacia();
        gastosFarmacia.setVisible(true);
        gastosFarmacia.setLocationRelativeTo(null);
        gastosFarmacia.jTableGastos.setModel(new Gastos().cargarRegistroEgreso(gastosFarmacia.jTableGastos));

        gastosFarmacia.jButtonRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (gastosFarmacia.jTextFieldDescripcionGastos.getText().isEmpty() || gastosFarmacia.jTextFieldTotalGastos.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "<html><h1 align='center'>No dejar campos en blanco</h1></html>");

                } else {
                    boolean pass = validarFormulario(gastosFarmacia.jTextFieldTotalGastos.getText());

                    if (pass) {
                        String descripcion = gastosFarmacia.jTextFieldDescripcionGastos.getText();
                        String monto = gastosFarmacia.jTextFieldTotalGastos.getText();

                        gastos = new Gastos(descripcion, monto, turno);

                        if (gastos.registrarGastos()) {
                            JOptionPane.showMessageDialog(null, "<html><h1 align='center'>Gastos Registrados</h1></html>");
                            limpiarCampos();
                            Clear_Table();
                            gastosFarmacia.jTableGastos.setModel(new Gastos().cargarRegistroEgreso(gastosFarmacia.jTableGastos));
                            gastosFarmacia.jTextFieldTotalGastos.setBackground(Color.WHITE);
                            tikectGastos = new TikectGasto();
                            tikectGastos.TikectGasto(descripcion, monto,pc);

                        } else {

                            JOptionPane.showMessageDialog(null, "<html><h1 align='center'>Error </h1></html>", "ERROR", JOptionPane.ERROR_MESSAGE);
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

        gastosFarmacia.jTextFieldTotalGastosId.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e
            ) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (gastosFarmacia.jTextFieldTotalGastosId.getText() != "") {
                        Clear_Table();
                        int id = Integer.parseInt(gastosFarmacia.jTextFieldTotalGastosId.getText());
                        gastosFarmacia.jTableGastos.setModel(new Gastos(id).buscarRegistroEgreso(gastosFarmacia.jTableGastos));
                    }
                }
            }
        });

        gastosFarmacia.jDateChooserFecha.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                Date fecha = gastosFarmacia.jDateChooserFecha.getDate();
                if (fecha != null) {
                    Clear_Table();
                    SimpleDateFormat Formato = new SimpleDateFormat("yyyy-MM-dd");
                    gastosFarmacia.jTableGastos.setModel(new Gastos(Formato.format(fecha)).buscarRegistroEgreso(gastosFarmacia.jTableGastos));
                }
            }
        });

        gastosFarmacia.jButtonRegistrarTikect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int fila;
                try {
                    fila = gastosFarmacia.jTableGastos.getSelectedRow();
                    if (fila == -1) {
                        JOptionPane.showMessageDialog(null, "<html><h1 align='center'>No ha seleccionado ninguna fila. </h1></html>", "ERROR", JOptionPane.ERROR_MESSAGE);
                    } else {
                        String descripcion = (String) gastosFarmacia.jTableGastos.getValueAt(fila, 1);
                        String monto = (String) gastosFarmacia.jTableGastos.getValueAt(fila, 2);
                        tikectGastos = new TikectGasto();
                        tikectGastos.TikectGasto(descripcion, monto,pc);
                    }
                } catch (Exception ex) {
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
                        JOptionPane.showMessageDialog(null, "<html><h1 align='center'>No dejar campos en blanco.</h1></html>");

                    } else {
                        boolean pass = validarFormulario(gastosFarmacia.jTextFieldTotalGastos.getText());

                        if (pass) {
                            String descripcion = gastosFarmacia.jTextFieldDescripcionGastos.getText();
                            String monto = String.format(Locale.US, "%.2f", gastosFarmacia.jTextFieldTotalGastos.getText());
                            gastos = new Gastos(descripcion, monto, turno);

                            if (gastos.registrarGastos()) {
                                JOptionPane.showMessageDialog(null, "<html><h1 align='center'>Gastos Registrados.</h1></html>");
                                limpiarCampos();
                                Clear_Table();
                                gastosFarmacia.jTableGastos.setModel(new Gastos().cargarRegistroEgreso(gastosFarmacia.jTableGastos));
                                gastosFarmacia.jTextFieldTotalGastos.setBackground(Color.WHITE);
                                gastosFarmacia.jTextFieldDescripcionGastos.requestFocus();

                            } else {

                                JOptionPane.showMessageDialog(null, "<html><h1 align='center'>Error </h1></html>", "ERROR", JOptionPane.ERROR_MESSAGE);
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
