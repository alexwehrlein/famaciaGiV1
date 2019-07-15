/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import modelo.sucursal;
import vista.Pantalla_sucursal;
import java.security.SecureRandom;
import java.math.BigInteger;

/**
 *
 * @author alexwehrlein
 */
public class Controlador_Pantalla_Informacion {

    Pantalla_sucursal pantalla_sucursal;
    sucursal s;

    public Controlador_Pantalla_Informacion() {
        pantalla_sucursal = new Pantalla_sucursal();
        pantalla_sucursal.setVisible(true);
        pantalla_sucursal.setLocationRelativeTo(null);
        SecureRandom random = new SecureRandom();
        String text = new BigInteger(50, random).toString(10);
        s = new sucursal(text);
        String[] idSucursal = s.registrarSucursal();
        pantalla_sucursal.textIdSucursal.setText(idSucursal[0]);
        pantalla_sucursal.textDireccion.setText(idSucursal[1]);
        pantalla_sucursal.textTelefono.setText(idSucursal[2]);

        pantalla_sucursal.btnEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pantalla_sucursal.textDireccion.setEditable(true);
                pantalla_sucursal.textTelefono.setEditable(true);
                pantalla_sucursal.textDireccion.requestFocus();
            }
        });

        pantalla_sucursal.btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = pantalla_sucursal.textIdSucursal.getText();
                String direccion = pantalla_sucursal.textDireccion.getText();
                String telefono = pantalla_sucursal.textTelefono.getText();
                
                if (!telefono.matches("^\\d+$")) {
                        JOptionPane.showMessageDialog(null, "<html><h1 align='center'> TELEFONO INCORRECTO </h1></html>", "ERROR..", JOptionPane.ERROR_MESSAGE);
                        pantalla_sucursal.textTelefono.setText("");
                        pantalla_sucursal.textTelefono.requestFocus();
                        return;
                    }

                s = new sucursal(id, direccion, telefono);
                boolean next = s.updateSucursal();
                if (next) {
                    JOptionPane.showMessageDialog(null, "I<html><h1 align='center'> nformacion actualizada </h1></html>", "EXITO", JOptionPane.INFORMATION_MESSAGE);
                    pantalla_sucursal.textDireccion.setEditable(false);
                    pantalla_sucursal.textTelefono.setEditable(false);
                } else {
                    JOptionPane.showMessageDialog(null, "<html><h1 align='center'> Error al actualizada Informacion</h1></html>", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

}
