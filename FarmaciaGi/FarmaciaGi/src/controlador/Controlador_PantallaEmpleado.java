/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import vista.Pantalla_Empleados;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.Empleado;
import modelo.Proveedor;
import vista.Pantalla_EmpleadosModificar;

/**
 *
 * @author saube
 */
public class Controlador_PantallaEmpleado {

    Pantalla_Empleados pantalla_Empleados;
    Pantalla_EmpleadosModificar empleadosModificar;
    Empleado empleado;

    public Controlador_PantallaEmpleado() {
        pantalla_Empleados = new Pantalla_Empleados();
        pantalla_Empleados.setVisible(true);
        pantalla_Empleados.setLocationRelativeTo(null);
        pantalla_Empleados.tablaEmpleados.setModel(new Empleado().cargarRegistroEgreso(pantalla_Empleados.tablaEmpleados));

        pantalla_Empleados.agregarEmpleado.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pantalla_Empleados.setVisible(false);
                new Controlador_EmpleadoAdd();
            }
        });

        pantalla_Empleados.tablaEmpleados.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent me) {
                int column = pantalla_Empleados.tablaEmpleados.getColumnModel().getColumnIndexAtX(me.getX());
                int row = me.getY() / pantalla_Empleados.tablaEmpleados.getRowHeight();
                int filaseleccionada;
                if (row < pantalla_Empleados.tablaEmpleados.getRowCount() && row >= 0 && column < pantalla_Empleados.tablaEmpleados.getColumnCount() && column >= 0) {
                    Object value = pantalla_Empleados.tablaEmpleados.getValueAt(row, column);
                    if (value instanceof JButton) {
                        ((JButton) value).doClick();
                        JButton boton = (JButton) value;

                        if (boton.getName().equals("btnModificar")) {
                            int reply = JOptionPane.showConfirmDialog(null, "<html><h1 align='center'>¿Modificar Empleado?</h1></html>", "Modificar", JOptionPane.YES_NO_OPTION);
                            if (reply == JOptionPane.YES_OPTION) {

                                filaseleccionada = pantalla_Empleados.tablaEmpleados.getSelectedRow();
                                int id = (int) pantalla_Empleados.tablaEmpleados.getValueAt(filaseleccionada, 0);
                                String nombre = pantalla_Empleados.tablaEmpleados.getValueAt(filaseleccionada, 1).toString();
                                String telefono = pantalla_Empleados.tablaEmpleados.getValueAt(filaseleccionada, 2).toString();
                                String correo = pantalla_Empleados.tablaEmpleados.getValueAt(filaseleccionada, 3).toString();
                                String direccion = pantalla_Empleados.tablaEmpleados.getValueAt(filaseleccionada, 4).toString();
                                String puesto = pantalla_Empleados.tablaEmpleados.getValueAt(filaseleccionada, 5).toString();
                                String turno = pantalla_Empleados.tablaEmpleados.getValueAt(filaseleccionada, 6).toString();
                                
                                String Id = String.valueOf(id);
                                
                                //new Controlador_PantallaEmpleadoModificar(Id,nombre,telefono,correo,direccion,puesto,turno);
                               empleadosModificar = new Pantalla_EmpleadosModificar(Id, nombre, telefono, correo, direccion, puesto, turno);
                               empleadosModificar.setVisible(true);
                               pantalla_Empleados.dispose();

                                       
                               

                              /*  empleado = new Empleado(id, nombre, telefono, correo, direccion,puesto,turno);
                                if (empleado.ModificarRegristros()) {
                                    JOptionPane.showMessageDialog(null, "Datos Modificados Correctamente");
                                    Clear_Table();
                                    pantalla_Empleados.tablaEmpleados.setModel(new Empleado().cargarRegistroEgreso(pantalla_Empleados.tablaEmpleados));

                                } else {
                                    JOptionPane.showMessageDialog(null, "Error");
                                }*/
                            } else {

                            }

                        }
                        if (boton.getName().equals("btnEliminar")) {
                            int reply = JOptionPane.showConfirmDialog(null, "<html><h1 align='center'>¿Eliminar Empleado?</h1></html>", "Eliminar", JOptionPane.YES_NO_OPTION);
                            if (reply == JOptionPane.YES_OPTION) {
                                filaseleccionada = pantalla_Empleados.tablaEmpleados.getSelectedRow();
                                int id = (int) pantalla_Empleados.tablaEmpleados.getValueAt(filaseleccionada, 0);
                                empleado = new Empleado(id);
                                if (empleado.eliminarRegristro()) {
                                    JOptionPane.showMessageDialog(null, "<html><h1 align='center'>Dato Eliminado Correctamente </h1></html>");
                                    Clear_Table();
                                    pantalla_Empleados.tablaEmpleados.setModel(new Empleado().cargarRegistroEgreso(pantalla_Empleados.tablaEmpleados));

                                } else {
                                     JOptionPane.showMessageDialog(null, "<html><h1 align='center'>Error </h1></html>", "ERROR", JOptionPane.ERROR_MESSAGE);
                                }
                            } else {

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
        DefaultTableModel modelo = (DefaultTableModel) pantalla_Empleados.tablaEmpleados.getModel();
        int filas = pantalla_Empleados.tablaEmpleados.getRowCount();
        for (int i = 0; filas > i; i++) {
            modelo.removeRow(0);
        }
    }

    

}
