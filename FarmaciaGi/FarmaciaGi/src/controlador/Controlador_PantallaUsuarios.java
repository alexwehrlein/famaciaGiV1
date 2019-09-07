/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.Empleado;
import modelo.Usuarios;
import vista.Pantalla_Usuarios;

/**
 *
 * @author saube
 */
public class Controlador_PantallaUsuarios {

    Pantalla_Usuarios pantalla_Usuarios;
    Usuarios usuarios;

    public Controlador_PantallaUsuarios() {
        pantalla_Usuarios = new Pantalla_Usuarios();
        pantalla_Usuarios.setVisible(true);
        pantalla_Usuarios.setLocationRelativeTo(null);
        pantalla_Usuarios.jTableUsuarios.setModel(new Usuarios().cargarRegistroEgreso(pantalla_Usuarios.jTableUsuarios));

        pantalla_Usuarios.jButtonRegistrarUsuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (pantalla_Usuarios.jTextFieldUsuario.getText().isEmpty() || pantalla_Usuarios.jTextFieldPassword.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "<html><h1 align='center'>No deje campos vacios</h1></html>");
                } else {
                    String usuario = pantalla_Usuarios.jTextFieldUsuario.getText();
                    String passwork = pantalla_Usuarios.jTextFieldPassword.getText();
                    Empleado empleado = (Empleado) pantalla_Usuarios.jComboBoxEmpleado.getSelectedItem();
                    String pc = pantalla_Usuarios.jComboBoxPc.getSelectedItem().toString();
                    if (pc.equals("Seleccionar un PC")) {
                        JOptionPane.showMessageDialog(null, "Selecciones una Pc.", "ERROR", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    String numPc = "";
                    switch (pc) {
                        case "PC 1":
                            numPc = "1";
                            break;
                        case "PC 2":
                            numPc = "2";
                            break;
                        case "PC 3":
                            numPc = "3";
                            break;
                        case "PC 4":
                            numPc = "4";
                            break;
                        case "PC 5":
                            numPc = "5";
                            break;
                    }
                    usuarios = new Usuarios(0, usuario, passwork, numPc, empleado.getIdEmpleado());
                    if (usuarios.validarUser()) {
                        JOptionPane.showMessageDialog(null, "Ya existe un usuario vinculado con ese empleado", "ERROR", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (usuarios.registrarUsuario()) {
                        JOptionPane.showMessageDialog(null, "<html><h1 align='center'>Datos ingresados Correctamente </h1></html>");
                        limpiarCampos();
                        Clear_Table();
                        pantalla_Usuarios.jComboBoxPc.setSelectedIndex(0);
                        pantalla_Usuarios.jTableUsuarios.setModel(new Usuarios().cargarRegistroEgreso(pantalla_Usuarios.jTableUsuarios));

                    } else {
                        JOptionPane.showMessageDialog(null, "<html><h1 align='center'>Error</h1></html>", "ERROR", JOptionPane.ERROR_MESSAGE);
                    }

                }
            }

        });

        pantalla_Usuarios.jButtonEliminarUsuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (pantalla_Usuarios.jTableUsuarios.getSelectedRow() > 0) {
                    int m = JOptionPane.showConfirmDialog(null, "<html><h1 align='center'>¿DESEA ELIMINAR EL USUARIO ? </h1></html>", "CONFIRMAR", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (m == 0) {

                        int filaSeleccionada = pantalla_Usuarios.jTableUsuarios.getSelectedRow();
                        int idusuario = Integer.parseInt(pantalla_Usuarios.jTableUsuarios.getValueAt(filaSeleccionada, 0).toString());

                        usuarios = new Usuarios(idusuario);

                        if (usuarios.eliminarUsuario()) {
                            JOptionPane.showMessageDialog(null, "<html><h1 align='center'> Usuario Eliminado </h1></html>");
                            Clear_Table();
                            limpiarCampos();
                            pantalla_Usuarios.jTableUsuarios.setModel(new Usuarios().cargarRegistroEgreso(pantalla_Usuarios.jTableUsuarios));
                        } else {
                            JOptionPane.showMessageDialog(null, "<html><h1 align='center'>Error </html></html>", "ERROR", JOptionPane.ERROR_MESSAGE);
                        }

                    } else {
                        JOptionPane.showMessageDialog(null, "<html><h1 align='center'>Debe seleccionar un Usuario</h1></html>", "Usuarios", JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        });

        pantalla_Usuarios.jButtonEditarUsuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int m = JOptionPane.showConfirmDialog(null, "<html><h1 align='center'>¿DESEA MODIFICAR EL USUARIO ? </h1></html> ", "CONFIRMAR", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (m == 0) {
                    int filaSeleccionada = pantalla_Usuarios.jTableUsuarios.getSelectedRow();
                    int idusuario = Integer.parseInt(pantalla_Usuarios.jTableUsuarios.getValueAt(filaSeleccionada, 0).toString());
                    String usuario = pantalla_Usuarios.jTextFieldUsuario.getText();
                    String passwork = pantalla_Usuarios.jTextFieldPassword.getText();
                    Empleado empleado = (Empleado) pantalla_Usuarios.jComboBoxEmpleado.getSelectedItem();
                    String pc = pantalla_Usuarios.jComboBoxPc.getSelectedItem().toString();
                    if (pc.equals("Seleccionar un PC")) {
                        JOptionPane.showMessageDialog(null, "Selecciones una Pc.", "ERROR", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    String numPc = "";
                    switch (pc) {
                        case "PC 1":
                            numPc = "1";
                            break;
                        case "PC 2":
                            numPc = "2";
                            break;
                        case "PC 3":
                            numPc = "3";
                            break;
                        case "PC 4":
                            numPc = "4";
                            break;
                        case "PC 5":
                            numPc = "5";
                            break;
                    }
                    usuarios = new Usuarios(idusuario, usuario, passwork, numPc, empleado.getIdEmpleado());
                    if (usuarios.ModificarRegristros()) {
                        JOptionPane.showMessageDialog(null, "<html><h1 align='center'> Usuario Modificado </h1></html>");
                        Clear_Table();
                        limpiarCampos();
                        pantalla_Usuarios.jComboBoxEmpleado.setEnabled(true);
                        pantalla_Usuarios.jComboBoxPc.setSelectedIndex(0);
                        pantalla_Usuarios.jTableUsuarios.setModel(new Usuarios().cargarRegistroEgreso(pantalla_Usuarios.jTableUsuarios));

                    } else {
                        JOptionPane.showMessageDialog(null, "<html><h1 align='center'>Error </h1></html>", "ERROR", JOptionPane.ERROR_MESSAGE);
                    }

                }
            }
        });

        pantalla_Usuarios.jTableUsuarios.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent dosclick) {
                int fila;
                if (dosclick.getClickCount() == 1) {
                    fila = pantalla_Usuarios.jTableUsuarios.getSelectedRow();
                    int id = (int) pantalla_Usuarios.jTableUsuarios.getValueAt(fila, 0);
                    String usuario = (String) pantalla_Usuarios.jTableUsuarios.getValueAt(fila, 1);
                    String passwork = (String) pantalla_Usuarios.jTableUsuarios.getValueAt(fila, 2);
                    String pc = (String) pantalla_Usuarios.jTableUsuarios.getValueAt(fila, 3);
                    usuarios = new Usuarios(id);
                    String nombre = usuarios.idEmpleado();
                    setSelectedValue(pantalla_Usuarios.jComboBoxEmpleado, nombre);
                    pantalla_Usuarios.jTextFieldUsuario.setText(usuario);
                    pantalla_Usuarios.jTextFieldPassword.setText(passwork);
                    String pcN = pc.substring(3, 4);
                    if (pcN.matches("\\d")) {
                        pantalla_Usuarios.jComboBoxPc.setSelectedIndex(Integer.parseInt(pcN));
                    } else {
                        pantalla_Usuarios.jComboBoxPc.setSelectedIndex(0);
                    }
                    pantalla_Usuarios.jComboBoxEmpleado.setEnabled(false);
                }
            }
        });

    }

    private void setSelectedValue(JComboBox comboBox, String value) {
        Empleado empleado;
        for (int i = 0; i < comboBox.getItemCount(); i++) {
            empleado = (Empleado) comboBox.getItemAt(i);
            if (empleado.getNombre().equalsIgnoreCase(value)) {
                comboBox.setSelectedIndex(i);
                break;
            }
        }
    }

    private void limpiarCampos() {
        pantalla_Usuarios.jTextFieldPassword.setText("");
        pantalla_Usuarios.jTextFieldUsuario.setText("");
    }

    private void Clear_Table() {
        DefaultTableModel modelo = (DefaultTableModel) pantalla_Usuarios.jTableUsuarios.getModel();
        int filas = pantalla_Usuarios.jTableUsuarios.getRowCount();
        for (int i = 0; filas > i; i++) {
            modelo.removeRow(0);
        }
    }
}
