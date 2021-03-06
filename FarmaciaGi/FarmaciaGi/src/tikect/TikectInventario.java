/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikect;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import mail.Mail;
import modelo.Confings;
import modelo.sucursal;

/**
 *
 * @author saube
 */
public class TikectInventario {
    Confings confings;
    
    public void tikectInventario(String turno,  List<List<String>>productos , String pc ){
        confings = new Confings(Integer.parseInt(pc));
        String[] arr = confings.settings();
        String mensaje = "";
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");
        
        ServicioImp impServicio = new ServicioImp(); // se crea objeto 
        System.out.println(impServicio.getImpresoras()); // imprime todas las impresoras instaladas
        String auxs = "";
        String prod = "";
        String impra = arr[0]; // Nombre de la impresora
        
         // Se llama al metodo para imprimir una cadena
        auxs += "PRODUCTOS AGREGADOS\n\n";
        auxs += "FARMACIAS GI\n";
        auxs += arr[1]+"\n";
        auxs += "Iguala de la Independencia\n";
        auxs += "Turno: " + turno + "\n";
        auxs += "Fecha: " + dateFormat.format(date) + " Hora: " + hourFormat.format(date) + "\n";
        auxs += "==========================================\n";
        auxs += "  Descripcion          piezas\n";
        auxs += "==========================================\n";
        
         for (int i = 0; i <= productos.get(0).size() - 1; i++)  // for ejemplo para varios productos
        {
           
             if (productos.get(0).get(i).length() > 17) { // si la descripcion_producto es mayor a 17 la corta
                 prod = productos.get(0).get(i).substring(0, 17);
            }else{
                prod = productos.get(0).get(i); 
             }
            // Se formatea la cadena a imprimir con String.format para varios string
            auxs += String.format("%-18s" + " " + "%-5s", prod, productos.get(1).get(i) );
            auxs += "\n";
        }
         
         auxs += "\n==========================================\n";
         auxs+= "Ingreso de medicamento  \n Farmacia gi\n\n\n\n\n";// Varios saltos para no cortar antes
        
        sucursal su = new sucursal();
        String datSucursal[] = su.datosSucursal();
         
        Mail mail = new Mail();
        mensaje += "FARMACIAS GI \n";
        mensaje += datSucursal[1].toUpperCase()+" \n";
        mensaje += "Iguala de la Independencia\n";
        mensaje += "Fecha: " + dateFormat.format(date) + " Hora: " + hourFormat.format(date) + "\n";
        mensaje += "Turno:    "+turno+"\n\n";
        mensaje += "==========================================\n";
        mensaje += "  Descripcion                              piezas\n";
        mensaje += "==========================================\n";
         for (int i = 0; i <= productos.get(0).size() - 1; i++)  // for ejemplo para varios productos
        {
           
             if (productos.get(0).get(i).length() > 17) { // si la descripcion_producto es mayor a 17 la corta
                 prod = productos.get(0).get(i).substring(0, 17);
            }else{
                prod = productos.get(0).get(i); 
             }
            // Se formatea la cadena a imprimir con String.format para varios string
            mensaje  += String.format("%-18s" + "           " + "%-22s", prod, productos.get(1).get(i) );
            mensaje  += "\n";
        }
        mail.send_mail("farmaciagi08@gmail.com", mensaje , "INGRESO DE MEDICAMENTO TURNO: "+turno.toUpperCase()); //farmaciagi08@gmail.com
        
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
