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
 * @author saube
 */
public class TikectCorteConsulta {
    public void Tikect(double consultas,String turno){
        
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");
        
        
        
        ServicioImp impServicio = new ServicioImp(); // se crea objeto 
        System.out.println(impServicio.getImpresoras()); // imprime todas las impresoras instaladas
        String auxs="";
       
        String impra = "MP-4200 TH"; // Nombre de la impresora

        // Se llama al metodo para imprimir una cadena
        auxs+= "CORTE DE CAJA\n\n";
        auxs+= "==========================================\n";
        auxs+= "FARMACIAS GI\n";
        auxs+= "Altamirano #6-AS";
        auxs+= "Iguala de la Independencia\n";
        auxs+= "Fecha: " + dateFormat.format(date) + " Hora: " + hourFormat.format(date) + "\n";
        auxs+= "Turno:    "+turno+"\n\n";
        
       
        
        auxs+= "==========================================\n";
        auxs+= "VENTAS CONSULTAS:     $ "+consultas+"\n";
        auxs+= "_________________________________________\n";
       
        auxs+= "==========================================\n\n\n\n\n";
        
        try {
            impServicio.printCadena(impra, auxs);
            // Cortar el papel ....
            byte[] cutP = new byte[]{0x1d, 'V', 1}; // comado para cortar
            impServicio.printBytes(impra, cutP); // se imprime el bruto 
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "<html><h1 align='center'>El tikect no se pudo imprimir</h1></html>","warning",JOptionPane.WARNING_MESSAGE);
        }
        
    }
    
}
