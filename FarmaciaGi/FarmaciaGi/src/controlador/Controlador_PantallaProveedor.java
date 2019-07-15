/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import vista.Pantalla_Proveedor;
import vista.Pantalla_ProveedorAdd;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import modelo.Proveedor;
import controlador.Controlador_PandallaProveedorAdd;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author saube
 */
public class Controlador_PantallaProveedor {

    Pantalla_Proveedor pantalla_Proveedor;
    Proveedor proveedor;

    public Controlador_PantallaProveedor() {
        pantalla_Proveedor = new Pantalla_Proveedor();
        pantalla_Proveedor.setVisible(true);
        pantalla_Proveedor.setLocationRelativeTo(null);
        pantalla_Proveedor.tablaProveedores.setModel(new Proveedor().cargarRegistroEgreso(pantalla_Proveedor.tablaProveedores));

        pantalla_Proveedor.agregarProveedor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pantalla_Proveedor.setVisible(false);
                new Controlador_PandallaProveedorAdd();
            }
        });

        pantalla_Proveedor.tablaProveedores.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent me) {
                int column = pantalla_Proveedor.tablaProveedores.getColumnModel().getColumnIndexAtX(me.getX());
                int row = me.getY() / pantalla_Proveedor.tablaProveedores.getRowHeight();
                int filaseleccionada;
                if (row < pantalla_Proveedor.tablaProveedores.getRowCount() && row >= 0 && column < pantalla_Proveedor.tablaProveedores.getColumnCount() && column >= 0) {
                    Object value = pantalla_Proveedor.tablaProveedores.getValueAt(row, column);
                    if (value instanceof JButton) {
                        ((JButton) value).doClick();
                        JButton boton = (JButton) value;

                        if (boton.getName().equals("btnModificar")) {
                            int reply = JOptionPane.showConfirmDialog(null, "<html><h1 align='center'>¿Modificar Proveedor? </h1></html>", "Modificar", JOptionPane.YES_NO_OPTION);
                            if (reply == JOptionPane.YES_OPTION) {

                                filaseleccionada = pantalla_Proveedor.tablaProveedores.getSelectedRow();
                                int id = (int) pantalla_Proveedor.tablaProveedores.getValueAt(filaseleccionada, 0);
                                String nombre = pantalla_Proveedor.tablaProveedores.getValueAt(filaseleccionada, 1).toString();
                                String telefono = pantalla_Proveedor.tablaProveedores.getValueAt(filaseleccionada, 2).toString();
                                String correo = pantalla_Proveedor.tablaProveedores.getValueAt(filaseleccionada, 3).toString();

                                proveedor = new Proveedor(id, nombre, telefono, correo);
                                if (proveedor.ModificarRegristros()) {
                                    JOptionPane.showMessageDialog(null, "<html><h1 align='center'>Datos Modificados Correctamente </h1></html>");
                                    Clear_Table();
                                    pantalla_Proveedor.tablaProveedores.setModel(new Proveedor().cargarRegistroEgreso(pantalla_Proveedor.tablaProveedores));

                                } else {
                                   JOptionPane.showMessageDialog(null, "<html><h1 align='center'>Error </h1></html>", "ERROR", JOptionPane.ERROR_MESSAGE);
                                }
                            } else {
                                
                            }

                        }
                        if (boton.getName().equals("btnEliminar")) {
                            int reply = JOptionPane.showConfirmDialog(null, "<html><h1 align='center'>¿Eliminar Proveedor? </h1></html>", "Modificar", JOptionPane.YES_NO_OPTION);
                            if (reply == JOptionPane.YES_OPTION) {
                            filaseleccionada = pantalla_Proveedor.tablaProveedores.getSelectedRow();
                            int id = (int) pantalla_Proveedor.tablaProveedores.getValueAt(filaseleccionada, 0);
                            proveedor = new Proveedor(id);
                            if (proveedor.eliminarRegristro()) {
                                JOptionPane.showMessageDialog(null, "<html><h1 align='center'>Dato Eliminado Correctamente </h1></html>");
                                Clear_Table();
                                pantalla_Proveedor.tablaProveedores.setModel(new Proveedor().cargarRegistroEgreso(pantalla_Proveedor.tablaProveedores));

                            } else {
                               JOptionPane.showMessageDialog(null, "<html><h1 align='center'>Error </h1></html>", "ERROR", JOptionPane.ERROR_MESSAGE);
                            }
                            }
                        }

                    }

                }

            }

            @Override
            public void mousePressed(MouseEvent me) {

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

    }

    private void Clear_Table() {
        DefaultTableModel modelo = (DefaultTableModel) pantalla_Proveedor.tablaProveedores.getModel();
        int filas = pantalla_Proveedor.tablaProveedores.getRowCount();
        for (int i = 0; filas > i; i++) {
            modelo.removeRow(0);
        }
    }

    
}
