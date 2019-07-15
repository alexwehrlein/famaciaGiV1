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

/**
 *
 * @author saube
 */
public class TikectCorte {
    
    public void TikecCorte(String ventas, String consultorio, String devoluciones, String gastos,String abarrotes, String perfumeria, double total,String turno, ArrayList<String>clientes , String [] consultas , String retiros, int clave){
        
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");
        
        
        
        ServicioImp impServicio = new ServicioImp(); // se crea objeto 
        System.out.println(impServicio.getImpresoras()); // imprime todas las impresoras instaladas
        int clientesNum = clientes.size();
        String auxs="";
        int num = 0 , num2 = 1 , num3 = 2;
        String impra = "MP-4200 TH"; // Nombre de la impresora

        // Se llama al metodo para imprimir una cadena
        auxs+= "CORTE DE CAJA\n\n";
        auxs+= "==========================================\n";
        auxs+= "FARMACIAS GI\n";
        auxs+= "Altamirano #6-A\n";
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
