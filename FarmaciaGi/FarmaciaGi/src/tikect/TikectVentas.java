/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikect;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.Confings;
import vista.Pantalla_Ventas;

/**
 *
 * @author saube
 */
public class TikectVentas {
    Confings confings;

    public void tikectVentas(String folio, String empleada, String cliente, int piezas, String total, String pago, String cambio, String[] prod, String[] prec, String[] cant, String[] impor , String pc) {
        
        confings = new Confings(Integer.parseInt(pc));
        String[] arr = confings.settings();
        
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");

        ServicioImp impServicio = new ServicioImp(); // se crea objeto 
        System.out.println(impServicio.getImpresoras()); // imprime todas las impresoras instaladas
        String auxs = "";

        String impra = arr[0]; // Nombre de la impresora

        // Se llama al metodo para imprimir una cadena
        auxs += "COMPROBANTE DE VENTA\n\n";
        auxs += "FARMACIAS GI\n";
        auxs += arr[1]+"\n";
        auxs += "Iguala de la Independencia\n";
        auxs += "Folio: " + folio + "\n";
        auxs += "Le atendio: " + empleada + "\n";
        auxs += "Fecha: " + dateFormat.format(date) + " Hora: " + hourFormat.format(date) + "\n";
        auxs += "Cliente: " + cliente + "\n";
        auxs += "==========================================\n";
        auxs += "Cant Descripcion       Precio    Importe\n";
        auxs += "==========================================\n";

        for (int i = 0; i < cant.length; i++) // for ejemplo para varios productos
        {
            if (prod[i].length() > 17) { // si la descripcion_producto es mayor a 17 la corta
                prod[i] = prod[i].substring(0, 17);
            }
            // Se formatea la cadena a imprimir con String.format para varios string
            auxs += String.format("%-4s" + " " + "%-17s" + " " + "$%-8s" + " " + "$%-8s", cant[i], prod[i], prec[i], impor[i]);
            auxs += "\n";
        }
        auxs += "\n";
        auxs += String.format("\t                TOTAL:" + " " + "$%-10s", total);

        auxs += "\n";
        auxs += String.format("\t         PAGO CLIENTE:" + " " + "$%-10s", pago);

        auxs += "\n";
        auxs += String.format("\t               CAMBIO:" + " " + "$%-10s", cambio);
        auxs += "\n==========================================\n";
        auxs += "Gracias por su compra\n Expertos en tu salud\n\n\n\n\n";// Varios saltos para no cortar antes

        try {
            impServicio.printCadena(impra, auxs);
            // Cortar el papel ....
            byte[] cutP = new byte[]{0x1d, 'V', 1}; // comado para cortar
            impServicio.printBytes(impra, cutP); // se imprime el bruto 
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "<html><h1 align='center'>El tikect no se pudo imprimir </h1></html>","warning",JOptionPane.WARNING_MESSAGE);
        }

    }

}
