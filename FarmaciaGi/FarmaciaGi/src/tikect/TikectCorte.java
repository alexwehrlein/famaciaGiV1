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
import javax.swing.JOptionPane;
import mail.Mail;
import modelo.Confings;
import modelo.sucursal;

/**
 *
 * @author saube
 */
public class TikectCorte {
    Confings confings;
    
    public void TikecCorte(String ventas, String consultorio, String devoluciones, String gastos,String abarrotes, String perfumeria, double total,String turno, ArrayList<String>clientes , String [] consultas , String retiros, int clave , String pc){
        String mensaje = "";
        confings = new Confings(Integer.parseInt(pc));
        String[] arr = confings.settings();
        
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");

        ServicioImp impServicio = new ServicioImp(); // se crea objeto 
        System.out.println(impServicio.getImpresoras()); // imprime todas las impresoras instaladas
        int clientesNum = clientes.size();
        String auxs="";
        int num = 0 , num2 = 1 , num3 = 2;
        String impra = arr[0]; // Nombre de la impresora

        // Se llama al metodo para imprimir una cadena
        auxs+= "CORTE DE CAJA\n\n";
        auxs+= "==========================================\n";
        auxs+= "FARMACIAS GI\n";
        auxs+= arr[1]+"\n";
        auxs+= "Iguala de la Independencia\n";
        auxs+= "Fecha: " + dateFormat.format(date) + " Hora: " + hourFormat.format(date) + "\n";
        auxs+= "Turno:    "+turno+"\n\n";
        
        auxs+= "VENTAS FARMACIA:      $ "+ventas+"\n";
        auxs+= "VENTAS PERFUMERIA:    $ "+perfumeria+"\n";
        auxs+= "VENTAS ABARROTES:     $ "+abarrotes+"\n";
        auxs+= "==========================================\n";
        auxs+= "DEVOLUCIONES:         $ "+devoluciones+"\n";
        auxs+= "GASTOS:               $ "+gastos+"\n";
        if (clave == 0) {
            auxs+= "RETIROS:               $ "+retiros+"\n";
        }
        auxs+= "==========================================\n";
        auxs+= "Cantidad de descuentos:   "+clientesNum+"\n";
        auxs+= "Clientes con descuento:   Des. Patente  Des. Generico \n";
        for (String string : clientes) {
            
             auxs+= string+". \n";
        }
//        auxs+= "==========================================\n";
//         for (int i = 0; i < consultas.length / 3; i++) {
//        auxs+= ""+consultas[num]+":           "+consultas[num2]+"    Total: $ "+consultas[num3]+"\n";
//        num+=3; num2+=3; num3+=3;
//         }
        auxs+= "==========================================\n";
        auxs+= "TOTAL VENTAS:     $ "+String.format("%.2f", total)+"\n";
        auxs+= "_________________________________________\n";
        
        auxs+= "==========================================\n\n\n\n\n";
        
        sucursal su = new sucursal();
        String datSucursal[] = su.datosSucursal();
        Mail mail = new Mail();
        mensaje += "FARMACIAS GI \n";
        mensaje += datSucursal[1].toUpperCase()+" \n";
        mensaje += "Iguala de la Independencia\n";
        mensaje += "Fecha: " + dateFormat.format(date) + " Hora: " + hourFormat.format(date) + "\n";
        mensaje += "Turno:    "+turno+"\n\n";
        mensaje += "VENTAS FARMACIA:        $ "+ventas+"\n";
        mensaje += "VENTAS PERFUMERIA:    $ "+perfumeria+"\n";
        mensaje += "VENTAS ABARROTES:      $ "+abarrotes+"\n";
        mensaje += "==========================================\n";
        mensaje += "DEVOLUCIONES:            $ "+devoluciones+"\n";
        mensaje += "GASTOS:                   $ "+gastos+"\n";
        if (clave == 0) {
            mensaje += "RETIROS:                $ "+retiros+"\n";
        }
        mensaje += "==========================================\n";
        mensaje += "Cantidad de descuentos:   "+clientesNum+"\n";
        mensaje += "Clientes con descuento:   Des. Patente  Des. Generico \n";
        for (String string : clientes) {
            
            mensaje += string+". \n";
        }
        mensaje += "==========================================\n";
        mensaje += "TOTAL VENTAS:     $ "+String.format("%.2f", total)+"\n";
        mensaje += "_________________________________________\n";
        
        mail.send_mail("farmaciagi08@gmail.com", mensaje , "CORTE DE CAJA TURNO: "+turno.toUpperCase()); //farmaciagi08@gmail.com
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
