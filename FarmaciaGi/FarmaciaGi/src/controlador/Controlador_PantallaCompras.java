/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.event.AncestorListener;
import javax.swing.table.DefaultTableModel;
import modelo.Compras;
import vista.Pantalla_Compra;

/**
 *
 * @author saube
 */
public class Controlador_PantallaCompras {
    Pantalla_Compra pantalla_Compra;
    Compras compras;
    public Controlador_PantallaCompras(){
        pantalla_Compra = new Pantalla_Compra();
        pantalla_Compra.setVisible(true);
        pantalla_Compra.setLocationRelativeTo(null);
        
        
         pantalla_Compra.btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               Date fechaIni = pantalla_Compra.fechaIni.getDatoFecha();
               Date fechaFin = pantalla_Compra.fechaFin.getDatoFecha();
                if (fechaIni != null && fechaFin != null) {
                    SimpleDateFormat Formato = new SimpleDateFormat("yyyy-MM-dd");
                    Clear_Table();
                    pantalla_Compra.tablaCompras.setModel(new Compras(Formato.format(fechaIni) , Formato.format(fechaFin)).compras(pantalla_Compra.tablaCompras));
                }else{
                    JOptionPane.showMessageDialog(null, "<html><h1> Ingrese el rango de fechas. </h1></html>" , "Error" , JOptionPane.ERROR_MESSAGE);
                }
            }
         });
        
    }
    
     private void Clear_Table() {
        DefaultTableModel modelo = (DefaultTableModel) pantalla_Compra.tablaCompras.getModel();
        int filas = pantalla_Compra.tablaCompras.getRowCount();
        for (int i = 0; filas > i; i++) {
            modelo.removeRow(0);
        }
    }
    
}
