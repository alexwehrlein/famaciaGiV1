/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikect;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.Confings;
import vista.Pantalla_Devoluciones;

/**
 *
 * @author saube
 */
public class TikectDevoluciones {
    Pantalla_Devoluciones devoluciones;
     private DefaultTableModel modelo;
    Confings confings;
    
    public void  TikectDevoluciones(String folio ,String nombre, int piezas, double precio, double total , String pc){
        
        confings = new Confings(Integer.parseInt(pc));
        String[] arr = confings.settings();
        
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");
        DateFormat formatofecha = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        ServicioImp impServicio = new ServicioImp(); // se crea objeto 
        System.out.println(impServicio.getImpresoras()); // imprime todas las impresoras instaladas
         String auxs="";
       
        String impra = arr[0]; // Nombre de la impresora

        // Se llama al metodo para imprimir una cadena
        auxs+= "DEVOLUCION\n\n";
        auxs+= "FARMACIAS GI\n";
        auxs+= arr[1]+"\n";
        auxs+= "Iguala de la Independencia\n";
        auxs+= "Folio: " + folio + "\n";
        auxs+= "Fecha: " + formatofecha.format(date) + "\n";
        auxs+= "==========================================\n";
        auxs+= "Cant Descripcion       Precio    Importe\n";
        auxs+= "==========================================\n";

        
    
        
            if (nombre.length() > 17) { // si la descripcion_producto es mayor a 17 la corta
                nombre = nombre.substring(0, 17);
            }
            // Se formatea la cadena a imprimir con String.format para varios string
            auxs+= String.format("%-4s" + " " + "%-17s" + " " + "$%-8s" + " " + "$%-8s", piezas, nombre, precio, total);
            auxs+= "\n";
        
        
        auxs+= "\n==========================================\n";
        auxs+= "Devolucion realizada\nRegrese Pronto\n\n\n\n\n";// Varios saltos para no cortar antes
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
