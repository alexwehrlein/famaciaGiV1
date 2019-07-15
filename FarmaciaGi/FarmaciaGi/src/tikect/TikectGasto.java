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

/**
 *
 * @author wehrlein
 */
public class TikectGasto {
    
    public void TikectGasto(String descripcion, String total){
       
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");
        
        
        
        ServicioImp impServicio = new ServicioImp(); // se crea objeto 
        System.out.println(impServicio.getImpresoras()); // imprime todas las impresoras instaladas
        String auxs="";
       
        String impra = "MP-4200 TH"; // Nombre de la impresora

        // Se llama al metodo para imprimir una cadena
         auxs+= "GASTO\n\n";
         auxs+= "FARMACIAS GI\n";
         auxs+= "Altamirano No 8-B\n";
         auxs+= "Iguala de la Independencia\n";
        //impServicio.printCadena(impra, "Folio: " + folio + "\n");
         auxs+= "Fecha: " + dateFormat.format(date) + " Hora: " + hourFormat.format(date) + "\n";
         auxs+= "==========================================\n";
         auxs+= "Descripcion          Total\n";
         auxs+= "==========================================\n";

        
    
        
            if (descripcion.length() > 20) { // si la descripcion_producto es mayor a 17 la corta
                descripcion = descripcion.substring(0, 17);
            }
            // Se formatea la cadena a imprimir con String.format para varios string
             auxs+= String.format("%-20s" + " " + "$%-8s", descripcion, total);
             auxs+= "\n";
        
        
         auxs+= "\n==========================================\n";
         auxs+= "    Gasto      \n Farmacia gi\n\n\n\n\n";// Varios saltos para no cortar antes
         
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
