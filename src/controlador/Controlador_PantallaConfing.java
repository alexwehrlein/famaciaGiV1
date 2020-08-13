/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.Confings;
import vista.Pantallas_Configuraciones;

/**
 *
 * @author saube
 */
public class Controlador_PantallaConfing {

    Pantallas_Configuraciones pc;
    Confings confings;
    String pcN;
    int idImpresora;

    public Controlador_PantallaConfing(String pcN) {
        this.pcN = pcN;
        pc = new Pantallas_Configuraciones();
        pc.setVisible(true);
        pc.setLocationRelativeTo(null);
        impresoraPredeterminada();
        pc.tablaImpresora.setModel(new Confings().cargarImpresoras(pc.tablaImpresora));

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

                confings = new Confings(impresora, direccion, Integer.parseInt(pcN));
                boolean next = confings.settingsSave();
                if (next) {
                    Clear_Table();
                    pc.txtImpressora.setText("");
                    pc.txtDireccion.setText("");
                    pc.tablaImpresora.setModel(new Confings().cargarImpresoras(pc.tablaImpresora));
                    JOptionPane.showMessageDialog(null, "<html><h1 align='center'>Los datos se ingresaron correctamente.</h1></html>", "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "<html><h1 align='center'>ERROR.</h1></html>", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        pc.btnEditar.addActionListener(new ActionListener() {
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
                confings = new Confings(impresora, direccion, Integer.parseInt(pcN));
                boolean next = confings.settingsEdit();
                if (next) {
                    Clear_Table();
                    pc.txtImpressora.setText("");
                    pc.txtDireccion.setText("");
                    pc.tablaImpresora.setModel(new Confings().cargarImpresoras(pc.tablaImpresora));
                    JOptionPane.showMessageDialog(null, "<html><h1 align='center'>Los datos se actualizaron.</h1></html>", "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "<html><h1 align='center'>ERROR.</h1></html>", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        pc.tablaImpresora.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent dosclick) {
                int fila;
                if (dosclick.getClickCount() == 1) {
                    fila = pc.tablaImpresora.getSelectedRow();
                    idImpresora = (int) pc.tablaImpresora.getValueAt(fila, 0);
                    String impresora = pc.tablaImpresora.getValueAt(fila, 1).toString();
                    String direccion = pc.tablaImpresora.getValueAt(fila, 2).toString();

                    pc.txtImpressora.setText(impresora);
                    pc.txtDireccion.setText(direccion);
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

    private void Clear_Table() {
        DefaultTableModel modelo = (DefaultTableModel) pc.tablaImpresora.getModel();
        int filas = pc.tablaImpresora.getRowCount();
        for (int i = 0; filas > i; i++) {
            modelo.removeRow(0);
        }
    }

    private void settings() {
        confings = new Confings();
        String[] arr = confings.settings();
        pc.txtImpressora.setText(arr[0]);
        pc.txtDireccion.setText(arr[1]);
    }

}
