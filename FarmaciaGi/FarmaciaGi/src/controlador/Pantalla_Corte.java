/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.Corte;
import modelo.Gastos;
import tikect.TikectCorte;
import tikect.TikectCorteConsulta;
import vista.Pantalla_CorteCaja;
import vista.Pantalla_Ventas;
import vista.Pantalla_principal;

/**
 *
 * @author saube
 */
public class Pantalla_Corte {

    Pantalla_CorteCaja pantalla_Corte;
    Controlador_PantallaPrincipal controlador_PantallaPrincipal;
    Pantalla_Ventas pantalla_Ventas;
    Pantalla_principal pantalla_principal;

    TikectCorte tikectCorte;
    TikectCorteConsulta tcc;
    Corte corte;
    String ventaTotal = "0";
    String consultorioTotal = "0";
    String devolucionesTotal = "0";
    String gastosTotal = "0";
    String perfumeriaTotal = "0";
    String abarrotesTotal = "0";
    String retiros ;
    String id;
    int folio;
    String turnoF;
     ArrayList<String> nombresClientes = new ArrayList<String>();

    public Pantalla_Corte(String turno) {
        this.turnoF = turno;
        pantalla_Corte = new Pantalla_CorteCaja();
        pantalla_Corte.setVisible(true);
        pantalla_Corte.setLocationRelativeTo(null);

        pantalla_Corte.jButtonCorte.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (pantalla_Corte.jComboBoxTurno.getSelectedItem().toString().equals(turnoF)) {

                    corte = new Corte(turnoF);
                    boolean next = corte.Corte();

                    if (next) {

                        String turno = pantalla_Corte.jComboBoxTurno.getSelectedItem().toString();
                        corte = new Corte(turno);

                        id = corte.folio();
                        folio = Integer.parseInt(id) + 1;
                        pantalla_Corte.jTextFieldFolio.setText(String.valueOf(folio));

                        ventaTotal = corte.ventaTotal(0);
                        consultorioTotal = corte.consultaTotal(0);
                        devolucionesTotal = corte.devolucionesTotal(0);
                        abarrotesTotal = corte.abarrotesTotal(0);
                        perfumeriaTotal = corte.perfumeriaTotal(0);
                        gastosTotal = corte.gastosTotal(0);
                        nombresClientes = corte.descuentos(0);
                        retiros = corte.retiros(0);
                        String arr [] = corte.totalesC(0);
                        
      
                        double vt = Double.parseDouble(ventaTotal);
                        double ct = Double.parseDouble(consultorioTotal);
                        double dt = Double.parseDouble(devolucionesTotal);
                        double at = Double.parseDouble(abarrotesTotal);
                        double pt = Double.parseDouble(perfumeriaTotal);
                        double gt = Double.parseDouble(gastosTotal);
                        double r = Double.parseDouble(retiros);
                        
                        /*pantalla_Corte.jTextFieldTVenta.setText("$ " + String.format("%.2f", vt));
                        pantalla_Corte.jTextFieldTConsultorio.setText("$ "+String.format("%.2f", ct));
                        pantalla_Corte.jTextFieldTDevoluciones.setText("$ " + String.format("%.2f",dt));
                        pantalla_Corte.jTextFieldTAbarrotes.setText("$ " + String.format("%.2f", at));
                        pantalla_Corte.jTextFieldTPerfumeria.setText("$ " + String.format("%.2f", pt));
                        pantalla_Corte.jTextFieldTGastos.setText("$ " + String.format("%.2f", gt));
                        pantalla_Corte.jTextFieldRetiros.setText("$ " + String.format("%.2f", r)); */

                        double t = vt + ct + at + pt;//total de los tipos de venta
                        double tt = t - dt - gt - r;//total a estregar
                        double tk =tt - ct - r;//el total menos las consultas
                        
                        System.out.println(r);
                        System.out.println(ct);
                        System.out.println(t);
                        System.out.println(tt);
                        System.out.println(tk);
                        

                        pantalla_Corte.jTextFieldTEntregar.setText("$ " +String.format("%.2f", tt) );

                        corte = new Corte(turno, tt);
                        if (true) {
                            corte.registrarCortes();
                            JOptionPane.showMessageDialog(null, "<html><h1 align='center'> El corte se a guardado </h1></html>");
                            tikectCorte = new TikectCorte();
                            tikectCorte.TikecCorte(ventaTotal, consultorioTotal, devolucionesTotal, gastosTotal,abarrotesTotal,perfumeriaTotal, tk, turno,nombresClientes,arr,retiros,0);
                            tcc = new TikectCorteConsulta();
                            tcc.Tikect(ct, turno);
                            JOptionPane.showMessageDialog(null, "<html><h1 align='center'> Turno finalizado </h1></html>" , "Adios" , JOptionPane.INFORMATION_MESSAGE);
                            System.exit(0);

                        } else {
                            JOptionPane.showMessageDialog(null, "<html><h1 align='center'> Error </h1></html>", "ERROR", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "<html><h1 align='center'> El corte ya a sido realizado </h1></html>", "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                } else {

                    JOptionPane.showMessageDialog(null, "<html><h1 align='center'> No puedes hacer el corte distinto a tu turno </h1></html>", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        pantalla_Corte.txtFolioCorte.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e
            ) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (pantalla_Corte.txtFolioCorte.getText() != "") {
                        Clear_Table();
                        int id = Integer.parseInt(pantalla_Corte.txtFolioCorte.getText());
                        pantalla_Corte.tablaCortes.setModel(new Corte(id).buscarCorte(pantalla_Corte.tablaCortes , ""));
                    }
                }
            }
        });
        
        pantalla_Corte.jDateChooserCorte.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                Date fecha = pantalla_Corte.jDateChooserCorte.getDate();
                if (fecha != null) {
                    Clear_Table();
                    SimpleDateFormat Formato = new SimpleDateFormat("yyyy-MM-dd");
                    System.out.println(Formato.format(fecha));
                   pantalla_Corte.tablaCortes.setModel(new Corte().buscarCorte(pantalla_Corte.tablaCortes , Formato.format(fecha)));
                }
            }
        });
        
        pantalla_Corte.btnTickect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int fila;
                try {
                    fila = pantalla_Corte.tablaCortes.getSelectedRow();
                    if (fila == -1) {
                        JOptionPane.showMessageDialog(null, "<html><h1 align='center'> No ha seleccionado ninguna fila. </h1></html>", "ERROR", JOptionPane.ERROR_MESSAGE);
                    } else {
                        String fecha = (String) pantalla_Corte.tablaCortes.getValueAt(fila, 2);
                        String turno = (String) pantalla_Corte.tablaCortes.getValueAt(fila, 3);
                        corte = new Corte(turno,fecha);
                        ventaTotal = corte.ventaTotal(1);
                        consultorioTotal = corte.consultaTotal(1);
                        devolucionesTotal = corte.devolucionesTotal(1);
                        abarrotesTotal = corte.abarrotesTotal(1);
                        perfumeriaTotal = corte.perfumeriaTotal(1);
                        gastosTotal = corte.gastosTotal(1);
                        nombresClientes = corte.descuentos(1);
                        retiros = corte.retiros(1);
                        String arr [] = corte.totalesC(1);
                        
                        double vt = Double.parseDouble(ventaTotal);
                        double ct = Double.parseDouble(consultorioTotal);
                        double dt = Double.parseDouble(devolucionesTotal);
                        double at = Double.parseDouble(abarrotesTotal);
                        double pt = Double.parseDouble(perfumeriaTotal);
                        double gt = Double.parseDouble(gastosTotal);
                        double r = Double.parseDouble(retiros);
                        
                        double t = vt + ct + at + pt;//total de los tipos de venta
                        double tt = t - dt - gt-r;//total a estregar
                        double tk =tt-ct-r;//el total menos las consultas
                        
                        tikectCorte = new TikectCorte();
                        tikectCorte.TikecCorte(ventaTotal, consultorioTotal, devolucionesTotal, gastosTotal,abarrotesTotal,perfumeriaTotal, tk, turno,nombresClientes,arr,retiros,0);
                        tcc = new TikectCorteConsulta();
                        tcc.Tikect(ct, turno);
                    }
                } catch (Exception ex) {
                }
            }
        });
       
    }
    
    private void Clear_Table() {
        DefaultTableModel modelo = (DefaultTableModel) pantalla_Corte.tablaCortes.getModel();
        int filas = pantalla_Corte.tablaCortes.getRowCount();
        for (int i = 0; filas > i; i++) {
            modelo.removeRow(0);
        }
    }

}
