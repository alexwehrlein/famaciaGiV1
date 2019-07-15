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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import modelo.Devoluciones;
import modelo.Proveedor;
import tikect.TikectDevoluciones;
import vista.Pantalla_Devoluciones;

/**
 *
 * @author saube
 */
public class Controlador_PantallaDevoluciones {

    Pantalla_Devoluciones devoluciones;
    Devoluciones devoluciones1;
    String idEmpleado, turno;
    TikectDevoluciones tikectDevoluciones;
    int cantidadExistencias;

    public Controlador_PantallaDevoluciones(String idEmpleado, String nombreEmpleado, String turnoEmpleado) {
        devoluciones = new Pantalla_Devoluciones();
        devoluciones.setLocationRelativeTo(null);
        devoluciones.setVisible(true);
        this.turno = turnoEmpleado;
        this.idEmpleado = idEmpleado;

        devoluciones.jTextFieldFolioDevoluciones.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (devoluciones.jTextFieldFolioDevoluciones.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "<html><h1 align='center'> Ingrese un folio de venta </h1></html>", "Devoluciones", JOptionPane.WARNING_MESSAGE);
                    } else {
                        Clear_Table();
                        int folio = Integer.parseInt(devoluciones.jTextFieldFolioDevoluciones.getText());

                        devoluciones.jTableDeboluciones.setModel(new Devoluciones().cargarRegistro(devoluciones.jTableDeboluciones, folio));
                        devoluciones.jTextFieldFolioDevoluciones.setText("");

                    }

                }

            }
        });

        devoluciones.jTableDeboluciones.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent me) {

                System.out.println(cantidadExistencias);
            }

            @Override
            public void mousePressed(MouseEvent me) {
                System.out.println("press");
            }

            @Override
            public void mouseReleased(MouseEvent me) {

            }

            @Override
            public void mouseEntered(MouseEvent me) {

            }

            @Override
            public void mouseExited(MouseEvent me) {

            }
        });

        devoluciones.jButtonDevolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (devoluciones.jTableDeboluciones.getSelectedRow() >= 0) {
                    int filaSeleccionada = devoluciones.jTableDeboluciones.getSelectedRow();

                    int m = JOptionPane.showConfirmDialog(null, "<html><h1 align='center'> DESEA DEVOLVER EL PRODUCTO </h1></html>", "CONFIRMAR", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (m == 0) {
                        int filaseleccionada = devoluciones.jTableDeboluciones.getSelectedRow();
                        int piezas = Integer.parseInt(devoluciones.jTableDeboluciones.getValueAt(filaSeleccionada, 3).toString());
                        cantidadExistencias = piezas;
                        long codigo = Long.parseLong(devoluciones.jTableDeboluciones.getValueAt(filaSeleccionada, 1).toString());
                        double precio = Double.valueOf(devoluciones.jTableDeboluciones.getValueAt(filaSeleccionada, 4).toString());
                        String idCliente = devoluciones.jTableDeboluciones.getValueAt(filaSeleccionada, 6).toString();
                        String nombreP = devoluciones.jTableDeboluciones.getValueAt(filaSeleccionada, 2).toString();
                        String folio = devoluciones.jTableDeboluciones.getValueAt(filaSeleccionada, 0).toString();
                        int IntFolio = Integer.parseInt(devoluciones.jTableDeboluciones.getValueAt(filaSeleccionada, 0).toString());

                        double total = piezas * precio;

                        if (cantidadExistencias < piezas) {
                            JOptionPane.showMessageDialog(null, "<html><h1 align='center'> No puedes devolver mas de lo vendido </h1></html>  " + cantidadExistencias + " piezas", "ERROR", JOptionPane.ERROR_MESSAGE);
                            Clear_Table();
                            devoluciones.jTableDeboluciones.setModel(new Devoluciones().cargarRegistro(devoluciones.jTableDeboluciones, IntFolio));
                        } else if (cantidadExistencias == 0) {
                            JOptionPane.showMessageDialog(null, "<html><h1 align='center'>Ya de devolvieron todos los productos </h1></html> ", "ERROR", JOptionPane.ERROR_MESSAGE);
                            Clear_Table();
                            devoluciones.jTableDeboluciones.setModel(new Devoluciones().cargarRegistro(devoluciones.jTableDeboluciones, IntFolio));
                        } else {

                            devoluciones1 = new Devoluciones(codigo, total);

                            if (devoluciones1.registrarDevolucion(idEmpleado, idCliente, turno)) {
                                JOptionPane.showMessageDialog(null, "<html><h1 align='center'> Devolucion Registrada Correctamente </h1></html>");
                                devoluciones1 = new Devoluciones(codigo, piezas);
                                tikectDevoluciones = new TikectDevoluciones();
                                tikectDevoluciones.TikectDevoluciones(folio, nombreP, piezas, precio, total);
                                if (devoluciones1.RegresarProducto()) {
                                    JOptionPane.showMessageDialog(null, "<html><h1 align='center'> Medicamento Regresado al almacen </h1></html>");
                                    devoluciones1 = new Devoluciones(IntFolio, codigo, piezas);
                                    boolean next = devoluciones1.detalleVenta(precio);
                                    Clear_Table();
                                    devoluciones.jTableDeboluciones.setModel(new Devoluciones().cargarRegistro(devoluciones.jTableDeboluciones, IntFolio));
                                    int n = JOptionPane.showConfirmDialog(null, "<html><h1 align='center'>DESEA DEVOLVER OTRO PRODUCTO</h1></html> ", "CONFIRMAR", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                                    if (n == 0) {

                                    } else {
                                        Clear_Table();
                                    }
                                } else {
                                    JOptionPane.showMessageDialog(null, "<html><h1 align='center'>Error </h1></html>", "ERROR", JOptionPane.ERROR_MESSAGE);
                                }

                            } else {
                                JOptionPane.showMessageDialog(null, "<html><h1 align='center'>Error </h1></html>", "ERROR", JOptionPane.ERROR_MESSAGE);
                            }
                        }

                    }

                } else {
                    JOptionPane.showMessageDialog(null, "<html><h1 align='center'> Debe seleccionar un Producto </h1></html>", "Devoluciones", JOptionPane.WARNING_MESSAGE);
                }

            }
        });

    }

    private void Clear_Table() {
        DefaultTableModel modelo = (DefaultTableModel) devoluciones.jTableDeboluciones.getModel();
        int filas = devoluciones.jTableDeboluciones.getRowCount();
        for (int i = 0; filas > i; i++) {
            modelo.removeRow(0);
        }
    }

}
