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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import modelo.Proveedor;

/**
 *
 * @author saube
 */
public class Controlador_PandallaProveedorAdd {

    Pantalla_ProveedorAdd pantalla_Proveedoradd;
    Proveedor proveedor;

    public Controlador_PandallaProveedorAdd() {
        pantalla_Proveedoradd = new Pantalla_ProveedorAdd();
        pantalla_Proveedoradd.setLocationRelativeTo(null);
        pantalla_Proveedoradd.setVisible(true);
        
        

        pantalla_Proveedoradd.guardarProveedor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (pantalla_Proveedoradd.nombreProveedores.getText().equals("") || pantalla_Proveedoradd.telefonoProveedores.getText().equals("")
                        || pantalla_Proveedoradd.correoProveedores.getText().equals("")) {

                    JOptionPane.showMessageDialog(null, "<html><h1 align='center'> No deje campos en blaco </h1></html>");
                } else {
                    

                    String nombre = pantalla_Proveedoradd.nombreProveedores.getText();
                    String telefono = pantalla_Proveedoradd.telefonoProveedores.getText();
                    String correo = pantalla_Proveedoradd.correoProveedores.getText();
                    
                boolean pass = validarFormulario(nombre, telefono, correo);
                
                    if (pass) {
                         proveedor = new Proveedor(0, nombre, telefono, correo);
                    if (proveedor.registrarProveedor()) {
                        JOptionPane.showMessageDialog(null, "<html><h1 align='center'> Datos ingresados Correctamente </h1></html>");
                        limpiarCampos();
                        pantalla_Proveedoradd.nombreProveedores.requestFocus();

                    } else {
                        JOptionPane.showMessageDialog(null, "<html><h1 align='center'> Error </h1></html>");
                    }
                    }

                   
                }
            }
        });

        pantalla_Proveedoradd.regresarProveedor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pantalla_Proveedoradd.dispose();
                new Controlador_PantallaProveedor();
            }
        });

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
                        System.out.println("Escriba una direccion valida de correo");
                    }
                
            }else{
                System.out.println("solo numeros");
            }
        } else {
            System.out.println("Solo letras en el nombre");
        }
        
        return next;
    }

    public void limpiarCampos() {
        pantalla_Proveedoradd.nombreProveedores.setText("");
        pantalla_Proveedoradd.telefonoProveedores.setText("");
        pantalla_Proveedoradd.correoProveedores.setText("");
    }

}
