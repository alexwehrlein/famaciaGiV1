/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.table.DefaultTableModel;
import modelo.Bajas;
import modelo.Empleado;
import vista.Pantalla_bajas;

/**
 *
 * @author alexwehrlein
 */
public class Controlador_Pantalla_Bajas {

    Pantalla_bajas pantalla_bajas;
    Bajas bajas;
    int id_empleado;

    public Controlador_Pantalla_Bajas(int id) {
        this.id_empleado = id;
        pantalla_bajas = new Pantalla_bajas();
        pantalla_bajas.setVisible(true);
        pantalla_bajas.setLocationRelativeTo(null);
        pantalla_bajas.jtableBjas.setModel(new Bajas().cargarRegistroEgreso(pantalla_bajas.jtableBjas));

        pantalla_bajas.btnBajas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel model = (DefaultTableModel) pantalla_bajas.jtableBjas.getModel();
                if (model.getRowCount() <= 0) {
                    JOptionPane.showMessageDialog(null, "<html><h1> no hay datos en la tabla</h1></html>", "ERROR", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String name = JOptionPane.showInputDialog(null, "Ingresa tu usurio");
                JPasswordField contraseña = new JPasswordField();
                JOptionPane.showConfirmDialog(null, contraseña, "Ingrese contraseña", JOptionPane.OK_CANCEL_OPTION);
                String dialogpass = new String(contraseña.getPassword());
                //System.out.println(name + " "+dialogpass);
                Empleado obj = new Empleado();
                String arr[] = obj.obtenerContraUsuario(name);
                if (!arr[0].equals("")) {
                    if (arr[0].equals(dialogpass) && arr[2].equals("Administrador")) {
                        bajas = new Bajas();
                        String arreglo[] = bajas.insertarBajas(model, id_empleado);
                        if (arreglo[0] == "1") {
                            Clear_Table();
                            pantalla_bajas.jtableBjas.setModel(new Bajas().cargarRegistroEgreso(pantalla_bajas.jtableBjas));
                            JOptionPane.showMessageDialog(null, "<html><h1> Exito </h1></html>", "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
                            if (arreglo[1] != "" || arreglo[2] != "") {
                                JOptionPane.showMessageDialog(null, "<html><h1> " + arreglo[1] + arreglo[2] + " ", "ERROR", JOptionPane.INFORMATION_MESSAGE);
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "<html><h1> ERROR</h1></html>", "ERROR", JOptionPane.ERROR_MESSAGE);
                            if (arreglo[1] != "" || arreglo[2] != "") {
                                JOptionPane.showMessageDialog(null, "<html><h1> " + arreglo[1] + arreglo[2] + " </h1></html>", "ERROR", JOptionPane.INFORMATION_MESSAGE);
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "<html><h1 align='center'> Contraseña Incorrecta </h1></html>");
                        return;
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "<html><h1 align='center'> Usuario Incorrecto </h1></html>");
                    return;
                }
            }
        });
    }

    private void Clear_Table() {
        DefaultTableModel modelo1 = (DefaultTableModel) pantalla_bajas.jtableBjas.getModel();
        int filas = pantalla_bajas.jtableBjas.getRowCount();
        for (int i = 0; filas > i; i++) {
            modelo1.removeRow(0);
        }
    }
}
