/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.swing.JOptionPane;
import modelo.Confings;
import vista.Pantallas_Configuraciones;

/**
 *
 * @author saube
 */
public class Controlador_PantallaConfing {

    Pantallas_Configuraciones pc;
    Confings confings;

    public Controlador_PantallaConfing() {
        pc = new Pantallas_Configuraciones();
        pc.setVisible(true);
        pc.setLocationRelativeTo(null);
        impresoraPredeterminada();
        settings();
        
        pc.comboImpresoras.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String impresora = pc.comboImpresoras.getSelectedItem().toString();
                if (!impresora.equals("Seleccionar impresoras")) {
                    pc.txtImpressora.setText(impresora);
                }
            }
        });

        pc.btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String impresora = pc.txtImpressora.getText();
                String direccion = pc.txtDireccion.getText();
                if (impresora.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "<html><h1 align='center'>Selecciones una impresora.</h1></html>", "ERROR", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (direccion.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "<html><h1 align='center'>Ingrese una direccion.</h1></html>", "ERROR", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                confings = new Confings(impresora,direccion);
                boolean next = confings.settingsEdit();
                if (next) {
                    JOptionPane.showMessageDialog(null, "<html><h1 align='center'>Los datos se actualizaron.</h1></html>", "ERROR", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "<html><h1 align='center'>ERROR.</h1></html>", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void impresoraPredeterminada() {
        PrintService service = PrintServiceLookup.lookupDefaultPrintService();
        if (service != null) {
            String printServiceName = service.getName();
            pc.comboImpresoras.setSelectedItem(printServiceName);
        } else {
            pc.comboImpresoras.setSelectedItem("Seleccionar impresoras");
        }
    }
    
    private void settings(){
        confings = new Confings();
        String[] arr = confings.settings();
        pc.txtImpressora.setText(arr[0]);
        pc.txtDireccion.setText(arr[1]);
    }

}
