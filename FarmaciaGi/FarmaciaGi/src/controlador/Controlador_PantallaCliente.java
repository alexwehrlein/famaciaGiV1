/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.Cliente;
import modelo.Empleado;
import modelo.Proveedor;
import vista.Pantalla_Clientes;

/**
 *
 * @author saube
 */
public class Controlador_PantallaCliente {

    Pantalla_Clientes pantalla_Clientes;
    Cliente cliente;

    public Controlador_PantallaCliente() {
        pantalla_Clientes = new Pantalla_Clientes();
        pantalla_Clientes.setVisible(true);
        pantalla_Clientes.setLocationRelativeTo(null);
        pantalla_Clientes.tablaClientes.setModel(new Cliente().cargarRegistroEgreso(pantalla_Clientes.tablaClientes));

        pantalla_Clientes.addCliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                pantalla_Clientes.dispose();
                pantalla_Clientes.agregarCliente.setVisible(true);
                pantalla_Clientes.agregarCliente.setResizable(false);
                pantalla_Clientes.agregarCliente.setBounds(449, 154, 503, 365);
                
            }
        });

        pantalla_Clientes.regresarCliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pantalla_Clientes.agregarCliente.dispose();
                new Controlador_PantallaCliente();
            }
        });

        pantalla_Clientes.registroCliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (pantalla_Clientes.nombreCliente.getText().equals("") || pantalla_Clientes.correoCliente.getText().equals("") || pantalla_Clientes.telefonoCliente.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "<html><h1 align='center'> No deje campos en blaco </h1></html>");
                } else {
                    
                    String nombre = pantalla_Clientes.nombreCliente.getText();
                    String telefono = pantalla_Clientes.telefonoCliente.getText();
                    String correo = pantalla_Clientes.correoCliente.getText();
                    String password = PasswordGenerator.getPassword(10);
                     boolean pass = validarFormulario(nombre, telefono, correo);
                     
                      if (pass) {
                         cliente = new Cliente(0, nombre, correo, telefono);
                    if (cliente.registrarCliente()) {
                        JOptionPane.showMessageDialog(null, "<html><h1 align='center'> Datos ingresados Correctamente </h1></html>");
                        limpiarCampos();

                    } else {
                        JOptionPane.showMessageDialog(null, "<html><h1 align='center'> Error </h1></html>", "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                    }
                    
                    

                }
            }
        });
        
        
        pantalla_Clientes.tablaClientes.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent me) {
                int column = pantalla_Clientes.tablaClientes.getColumnModel().getColumnIndexAtX(me.getX());
                int row = me.getY() / pantalla_Clientes.tablaClientes.getRowHeight();
                int filaseleccionada;
                if (row < pantalla_Clientes.tablaClientes.getRowCount() && row >= 0 && column < pantalla_Clientes.tablaClientes.getColumnCount() && column >= 0) {
                    Object value = pantalla_Clientes.tablaClientes.getValueAt(row, column);
                    if (value instanceof JButton) {
                        ((JButton) value).doClick();
                        JButton boton = (JButton) value;

                        if (boton.getName().equals("btnModificar")) {
                            int reply = JOptionPane.showConfirmDialog(null, "<html><h1 align='center'> ¿Modificar Cliente? </h1></html>", "Modificar", JOptionPane.YES_NO_OPTION);
                            if (reply == JOptionPane.YES_OPTION) {

                                filaseleccionada = pantalla_Clientes.tablaClientes.getSelectedRow();
                                int id = (int) pantalla_Clientes.tablaClientes.getValueAt(filaseleccionada, 0);
                                String nombre = pantalla_Clientes.tablaClientes.getValueAt(filaseleccionada, 1).toString();
                                String correo = pantalla_Clientes.tablaClientes.getValueAt(filaseleccionada, 2).toString();
                                String telefono = pantalla_Clientes.tablaClientes.getValueAt(filaseleccionada, 3).toString();
                                

                                cliente = new Cliente(id, nombre, correo, telefono);
                                if (cliente.ModificarRegristros()) {
                                    JOptionPane.showMessageDialog(null, "<html><h1 align='center'> Datos Modificados Correctamente </h1></html>");
                                    Clear_Table();
                                    pantalla_Clientes.tablaClientes.setModel(new Cliente().cargarRegistroEgreso(pantalla_Clientes.tablaClientes));

                                } else {
                                    JOptionPane.showMessageDialog(null, "<html><h1 align='center'> Error </h1></html>", "ERROR", JOptionPane.ERROR_MESSAGE);
                                }
                            } else {

                            }

                        }
                        if (boton.getName().equals("btnEliminar")) {
                            int reply = JOptionPane.showConfirmDialog(null, "<html><h1 align='center'> ¿Eliminar Cliente? </h1></html>", "Eliminarr", JOptionPane.YES_NO_OPTION);
                            if (reply == JOptionPane.YES_OPTION) {
                                filaseleccionada = pantalla_Clientes.tablaClientes.getSelectedRow();
                                int id = (int) pantalla_Clientes.tablaClientes.getValueAt(filaseleccionada, 0);
                                cliente = new Cliente(id);
                                if (cliente.eliminarRegristro()) {
                                    JOptionPane.showMessageDialog(null, "<html><h1 align='center'> Dato Eliminado Correctamente </h1></html>");
                                    Clear_Table();
                                    pantalla_Clientes.tablaClientes.setModel(new Cliente().cargarRegistroEgreso(pantalla_Clientes.tablaClientes));

                                } else {
                                   JOptionPane.showMessageDialog(null, "<html><h1 align='center'> Error </h1></html>", "ERROR", JOptionPane.ERROR_MESSAGE);
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
        DefaultTableModel modelo = (DefaultTableModel) pantalla_Clientes.tablaClientes.getModel();
        int filas = pantalla_Clientes.tablaClientes.getRowCount();
        for (int i = 0; filas > i; i++) {
            modelo.removeRow(0);
        }
    }
    
     public void limpiarCampos() {
        pantalla_Clientes.nombreCliente.setText("");
        pantalla_Clientes.correoCliente.setText("");
        pantalla_Clientes.telefonoCliente.setText("");
    }
    
     public boolean validarFormulario(String nombre, String telefono, String correo) {
        boolean next=false;
        Pattern pat = Pattern.compile("[a-zA-Záéíóú ]*");
        Pattern patCorreo = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Pattern patTelefono = Pattern.compile("[0-9]*");
        Matcher matNombre = pat.matcher(nombre);
        Matcher matTelefono = patTelefono.matcher(telefono);
        Matcher matCorreo = patCorreo.matcher(correo);
        if (matNombre.matches()) {
           
            if (matTelefono.matches()) {
                
                
                    if (matCorreo.matches()) {
                        next = true;
                        
                    }else{
                        JOptionPane.showMessageDialog(null, "<html><h1 align='center'> Escriba una direccion valida de correo </h1></html>");
                         pantalla_Clientes.correoCliente.setBackground(Color.red);
                    }
                
            }else{
                JOptionPane.showMessageDialog(null, "<html><h1 align='center'> Solo Numeros </h1></html>");
                  pantalla_Clientes.telefonoCliente.setBackground(Color.red);
            }
        } else {
           JOptionPane.showMessageDialog(null, "<html><h1 align='center'> Solo Letras En EL Nombre </h1></html>");
             pantalla_Clientes.nombreCliente.setBackground(Color.red);
        }
        
        return next;
    }
}
