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
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import mail.MailBug;
import modelo.Corte;
import modelo.Gastos;
import modelo.Usuarios;
import tikect.TikectCorte;
import tikect.TikectCorteConsulta;
import utilerias.Utilerias;
import vista.Pantalla_CorteCaja;
import vista.Pantalla_Ventas;
import vista.Pantalla_principal;

/**
 *
 * @author saube
 */
public class Pantalla_Corte {

    MailBug mailbug = new MailBug();
    Pantalla_CorteCaja pantalla_Corte;
    Controlador_PantallaPrincipal controlador_PantallaPrincipal;
    Pantalla_Ventas pantalla_Ventas;
    Pantalla_principal pantalla_principal;
    private DefaultTableModel modelo;
    TikectCorte tikectCorte;
    TikectCorteConsulta tcc;
    Corte corte;
    Gastos gastos;
    String ventaTotal = "0";
    String consultorioTotal = "0";
    String devolucionesTotal = "0";
    String gastosTotal = "0";
    String perfumeriaTotal = "0";
    String abarrotesTotal = "0";
    String retiros;
    String id;
    int folio;
    String turnoF;
    ArrayList<String> nombresClientes = new ArrayList<String>();

    public Pantalla_Corte(String turno, String pc) {
        this.turnoF = turno;
        pantalla_Corte = new Pantalla_CorteCaja();
        pantalla_Corte.setVisible(true);
        pantalla_Corte.setLocationRelativeTo(null);
        pantalla_Corte.txtTurno.setText(turno.toUpperCase());
        pantalla_Corte.jButtonCorte.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modelo = (DefaultTableModel) pantalla_Corte.jTableDatosExtras.getModel();
                pantalla_Corte.txtRecargas.setText("");
                pantalla_Corte.txtPagoDoctores.setText("");
                modelo.setValueAt("", 0, 1);
                modelo.setValueAt("", 1, 1);
                modelo.setValueAt("", 2, 1);
                modelo.setValueAt("", 3, 1);
                modelo.setValueAt("", 4, 1);
                modelo.setValueAt("", 0, 3);
                modelo.setValueAt("", 1, 3);
                modelo.setValueAt("", 2, 3);
                modelo.setValueAt("", 3, 3);
                modelo.setValueAt("", 4, 3);
                modelo.setValueAt("", 5, 3);
                modelo.setValueAt("", 6, 1);
                modelo.setValueAt("", 6, 3);
                pantalla_Corte.jDialogDatalles.setBounds(249, 105, 767, 520);
                pantalla_Corte.jDialogDatalles.setResizable(false);
                pantalla_Corte.jDialogDatalles.setVisible(true);

            }
        });

        pantalla_Corte.txtFolioCorte.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e
            ) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (pantalla_Corte.txtFolioCorte.getText() != "") {
                        Clear_Table();
                        int id = Integer.parseInt(pantalla_Corte.txtFolioCorte.getText());
                        pantalla_Corte.tablaCortes.setModel(new Corte(id).buscarCorte(pantalla_Corte.tablaCortes, ""));
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
                    pantalla_Corte.tablaCortes.setModel(new Corte().buscarCorte(pantalla_Corte.tablaCortes, Formato.format(fecha)));
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
                        corte = new Corte(turno, fecha);
                        gastos = new Gastos();
                        ventaTotal = corte.ventaTotal(1);
                        consultorioTotal = corte.consultaTotal(1);
                        devolucionesTotal = corte.devolucionesTotal(1);
                        abarrotesTotal = corte.abarrotesTotal(1);
                        perfumeriaTotal = corte.perfumeriaTotal(1);
                        gastosTotal = corte.gastosTotal(1);
                        nombresClientes = corte.descuentos(1);
                        retiros = corte.retiros(1);
                        String arr[] = corte.totalesC(1);
                        ArrayList<Corte> consultas = corte.consultorioSelect(1);
                        ArrayList<Gastos> gastosT = gastos.gastosT(turno, fecha, 1);

                        double vt = Double.parseDouble(ventaTotal);
                        double ct = Double.parseDouble(consultorioTotal);
                        double dt = Double.parseDouble(devolucionesTotal);
                        double at = Double.parseDouble(abarrotesTotal);
                        double pt = Double.parseDouble(perfumeriaTotal);
                        double gt = Double.parseDouble(gastosTotal);
                        double r = Double.parseDouble(retiros);

                        double t = vt + ct + at + pt;//total de los tipos de venta
                        double tt = t - dt - gt - r;//total a estregar
                        double tk = tt - ct - r;//el total menos las consultas

                        tikectCorte = new TikectCorte();
                        tikectCorte.TikecCorte(ventaTotal, consultorioTotal, devolucionesTotal, gastosTotal, abarrotesTotal, perfumeriaTotal, tk, turno, nombresClientes, arr, retiros, 0, pc, gastosT, "0", "0","0");
                        tcc = new TikectCorteConsulta();
                        tcc.Tikect(ct, turno, pc,consultas,"0");
                    }
                } catch (Exception ex) {
                }
            }
        });

        pantalla_Corte.jTableDatosExtras.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    modelo = (DefaultTableModel) pantalla_Corte.jTableDatosExtras.getModel();
                    float Total = 0, terminal = 0;
                    for (int i = 0; i < modelo.getRowCount() - 1; i++) {
                        int moneda = 0;
                        int billete = 0;
                        int clave1 = 0;
                        int clave3 = 0;

                        if (modelo.getValueAt(i, 3).toString().equals("")) {
                            clave3 = 1;
                        }

                        if (modelo.getValueAt(i, 1).toString().equals("")) {
                            clave1 = 1;
                        }

                        if (clave1 == 0) {
                            if (!validarCantidad(modelo.getValueAt(i, 1).toString())) {
                                modelo.setValueAt("", i, 1);
                                JOptionPane.showMessageDialog(null, "<html><h1 align='center'>Ingrese una cantidad valida.</h1></html>", "ERROR", JOptionPane.ERROR_MESSAGE);
                                mailbug.send_mail("guzmangaleanacarlos@gmail.com", " NO INGRESAN CANTIDAD CORRECTA AL REAL " + Utilerias.SUCURSALE, "CORTE DE CAJA");

                                return;
                            }
                            moneda = Integer.parseInt(modelo.getValueAt(i, 1).toString());
                        }
                        if (clave3 == 0) {
                            if (!validarCantidad(modelo.getValueAt(i, 3).toString())) {
                                modelo.setValueAt("", i, 3);
                                JOptionPane.showMessageDialog(null, "<html><h1 align='center'> Ingrese una cantidad valida .</h1></html>", "ERROR", JOptionPane.ERROR_MESSAGE);
                                mailbug.send_mail("guzmangaleanacarlos@gmail.com", " NO INGRESAN CANTIDAD CORRECTA " + Utilerias.SUCURSALE, "CORTE DE CAJA");

                                return;
                            }
                            billete = Integer.parseInt(modelo.getValueAt(i, 3).toString());
                        }

                        if (i == 5) {
                            Total = Total + (billete * Float.parseFloat(modelo.getValueAt(i, 2).toString()));
                        } else {
                            Total = Total + (moneda * Float.parseFloat(modelo.getValueAt(i, 0).toString())) + (billete * Integer.parseInt(modelo.getValueAt(i, 2).toString()));
                        }

                    }
                    int clave6 = 0;
                    
                    if (modelo.getValueAt(6, 1).toString().equals("")) {
                            clave6 = 1;
                    }

                    if (clave6 == 0) {
                        terminal = Integer.parseInt(modelo.getValueAt(6, 1).toString());
                    }

                    modelo.setValueAt(Total + terminal, 6, 3);

                }
            }

        });

        pantalla_Corte.btnGuadarDatosExtras.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String recargas = pantalla_Corte.txtRecargas.getText();
                String pagoDoctores = pantalla_Corte.txtPagoDoctores.getText();
                modelo = (DefaultTableModel) pantalla_Corte.jTableDatosExtras.getModel();

                if (recargas.isEmpty() || validarCantidad(recargas) == false) {
                    JOptionPane.showMessageDialog(null, "<html><h1 align='center'> Ingrese un cantidad correcta. </h1></html>", "ERROR", JOptionPane.ERROR_MESSAGE);
                    mailbug.send_mail("guzmangaleanacarlos@gmail.com", " NO INGRESAN CANTIDAD CORRECTA " + Utilerias.SUCURSALE, "CORTE DE CAJA");

                    pantalla_Corte.txtRecargas.requestFocus();
                    return;
                }
                if (pagoDoctores.isEmpty() || validarCantidad(pagoDoctores) == false) {
                    JOptionPane.showMessageDialog(null, "<html><h1 align='center'> Ingrese un cantidad correcta. </h1></html>", "ERROR", JOptionPane.ERROR_MESSAGE);
                    mailbug.send_mail("guzmangaleanacarlos@gmail.com", " NO INGRESAN CANTIDAD CORRECTA " + Utilerias.SUCURSALE, "CORTE DE CAJA");

                    pantalla_Corte.txtRecargas.requestFocus();
                    return;
                }
                if (modelo.getValueAt(0, 1) == null || modelo.getValueAt(1, 1) == null || modelo.getValueAt(2, 1) == null
                        || modelo.getValueAt(3, 1) == null || modelo.getValueAt(4, 1) == null || modelo.getValueAt(6, 1) == null
                        || modelo.getValueAt(0, 3) == null || modelo.getValueAt(1, 3) == null || modelo.getValueAt(2, 3) == null
                        || modelo.getValueAt(3, 3) == null || modelo.getValueAt(4, 3) == null || modelo.getValueAt(5, 3) == null) {
                    JOptionPane.showMessageDialog(null, "Ingresar todas las denominaciones", "ERROR", JOptionPane.ERROR_MESSAGE);
                    mailbug.send_mail("guzmangaleanacarlos@gmail.com", " NO INGRESAN LAS DENOMINACIONES CORRECTAS AL REALIZAR CORTE " + Utilerias.SUCURSALE, "CORTE DE CAJA");

                    return;
                }
                Usuarios obj = new Usuarios();
                String recargasF = obj.recargaTurno(turno);
                List<String> extras = new ArrayList<String>();
                extras.add(pantalla_Corte.txtRecargas.getText());
                extras.add(recargasF);
                extras.add(modelo.getValueAt(0, 1).toString());
                extras.add(modelo.getValueAt(1, 1).toString());
                extras.add(modelo.getValueAt(2, 1).toString());
                extras.add(modelo.getValueAt(3, 1).toString());
                extras.add(modelo.getValueAt(4, 1).toString());
                extras.add(modelo.getValueAt(0, 3).toString());
                extras.add(modelo.getValueAt(1, 3).toString());
                extras.add(modelo.getValueAt(2, 3).toString());
                extras.add(modelo.getValueAt(3, 3).toString());
                extras.add(modelo.getValueAt(4, 3).toString());
                extras.add(modelo.getValueAt(5, 3).toString());
                extras.add(modelo.getValueAt(6, 1).toString());
                extras.add(modelo.getValueAt(6, 3).toString());
                extras.add(pagoDoctores);
                String total = modelo.getValueAt(6, 3).toString();

                corte = new Corte(turnoF);
                boolean next = corte.Corte();

                if (next) {

                    corte = new Corte(turno);
                    gastos = new Gastos();
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
                    String arr[] = corte.totalesC(0);
                    ArrayList<Corte> consultas = corte.consultorioSelect(1);
                    ArrayList<Gastos> gastosT = gastos.gastosT(turno, "", 0);

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
                    double tk = tt - ct ;//el total menos las consultas

                    System.out.println(r);//retiros
                    System.out.println(ct);//consultas
                    System.out.println(t);//total
                    System.out.println(tt);//total final
                    System.out.println(tk);//total menos las consultas

                    pantalla_Corte.jTextFieldTEntregar.setText("$ " + String.format("%.2f", tt));

                    corte = new Corte(turno, tt);
                    if (corte.registrarCortes(extras)) {
                        pantalla_Corte.txtRecargas.setText("");
                        pantalla_Corte.txtPagoDoctores.setText("");
                        modelo.setValueAt("", 0, 1);
                        modelo.setValueAt("", 1, 1);
                        modelo.setValueAt("", 2, 1);
                        modelo.setValueAt("", 3, 1);
                        modelo.setValueAt("", 4, 1);
                        modelo.setValueAt("", 0, 3);
                        modelo.setValueAt("", 1, 3);
                        modelo.setValueAt("", 2, 3);
                        modelo.setValueAt("", 3, 3);
                        modelo.setValueAt("", 4, 3);
                        modelo.setValueAt("", 5, 3);
                        modelo.setValueAt("", 6, 1);
                        pantalla_Corte.jDialogDatalles.setVisible(false);
                        JOptionPane.showMessageDialog(null, "<html><h1 align='center'> El corte se a guardado </h1></html>");
                        tikectCorte = new TikectCorte();
                        tikectCorte.TikecCorte(ventaTotal, consultorioTotal, devolucionesTotal, gastosTotal, abarrotesTotal, perfumeriaTotal, tk, turno, nombresClientes, arr, retiros, 0, pc, gastosT,recargas , recargasF , total);
                        tcc = new TikectCorteConsulta();
                        tcc.Tikect(ct, turno, pc, consultas,pagoDoctores);
                        JOptionPane.showMessageDialog(null, "<html><h1 align='center'> Turno finalizado </h1></html>", "Adios", JOptionPane.INFORMATION_MESSAGE);
                        System.exit(0);

                    } else {
                        JOptionPane.showMessageDialog(null, "<html><h1 align='center'> Error </h1></html>", "ERROR", JOptionPane.ERROR_MESSAGE);
                        mailbug.send_mail("guzmangaleanacarlos@gmail.com", " ERROR AL REALIZAR EL CORTE  " + Utilerias.SUCURSALE, "CORTE DE CAJA");

                    }
                } else {
                    JOptionPane.showMessageDialog(null, "<html><h1 align='center'> El corte ya a sido realizado </h1></html>", "ERROR", JOptionPane.ERROR_MESSAGE);

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

    private boolean validarCantidad(String cantidad) {
        boolean next = true;
        String cadena = "^\\d+\\.?\\d?\\d?";
        Pattern pat = Pattern.compile(cadena);
        Matcher mat = pat.matcher(cantidad);
        if (!mat.matches()) {
            next = false;
            System.out.println("no");
        }
        return next;
    }

}
