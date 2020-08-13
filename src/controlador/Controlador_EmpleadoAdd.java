/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import vista.Pantalla_EmpleadoAdd;
import vista.Pantalla_Empleados;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import modelo.Empleado;

/**
 *
 * @author saube
 */
public class Controlador_EmpleadoAdd {
    Pantalla_EmpleadoAdd empleadoAdd;
    Empleado empleado;

    public Controlador_EmpleadoAdd() {
        empleadoAdd = new Pantalla_EmpleadoAdd();
        empleadoAdd.setLocationRelativeTo(null);
        empleadoAdd.setVisible(true);
        
        empleadoAdd.registroEmpleados.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                if (empleadoAdd.nombreEmpleados.getText().equals("") || empleadoAdd.telefonoEmpleados.getText().equals("") 
                      ||  empleadoAdd.correoEmpleados.getText().equals("") || empleadoAdd.direccionEmpleados.getText().equals("") ) {
                    
                    JOptionPane.showMessageDialog(null, "<html><h1 align='center'> No deje campos en blaco </h1></html>");
                }else{
                    
                
               
                String nombre = empleadoAdd.nombreEmpleados.getText();
                String telefono = empleadoAdd.telefonoEmpleados.getText();
                String correo = empleadoAdd.correoEmpleados.getText();
                String direccion = empleadoAdd.direccionEmpleados.getText();
                String puesto = empleadoAdd.empleoPuesto.getSelectedItem().toString();
                String turno = empleadoAdd.jComboBoxTurno.getSelectedItem().toString();
                
                empleado = new Empleado(0,nombre, telefono, correo, direccion, puesto,turno);
                if (empleado.registrarEmpleado()) {
                    JOptionPane.showMessageDialog(null, "<html><h1 align='center'> Datos ingresados Correctamente </h1></html>");
                    limpiarCampos();

                } else {
                    JOptionPane.showMessageDialog(null, "<html><h1 align='center'> Error </h1></html>");
                    
                }
                }
                
            }
        });
        
        empleadoAdd.regresarEmpleado.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                empleadoAdd.dispose();
                new Controlador_PantallaEmpleado();
            }
        });
        
        
    }
     public void limpiarCampos() {
        empleadoAdd.nombreEmpleados.setText("");
        empleadoAdd.telefonoEmpleados.setText("");
        empleadoAdd.correoEmpleados.setText("");
        empleadoAdd.direccionEmpleados.setText("");
    }
    
    
    
}
