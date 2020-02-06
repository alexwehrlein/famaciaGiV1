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
import modelo.Corte;

/**
 *
 * @author saube
 */
public class TikectCorteConsulta {
    Confings confings;
    
    public void Tikect(double consultas,String turno, String pc ,  ArrayList<Corte> consultorio, String pagoDoctores){
        
        confings = new Confings(Integer.parseInt(pc));
        String[] arr = confings.settings();
        
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");
       
        ServicioImp impServicio = new ServicioImp(); // se crea objeto 
        System.out.println(impServicio.getImpresoras()); // imprime todas las impresoras instaladas
        String auxs="";
       
        String impra = arr[0]; // Nombre de la impresora
        Mail mail = new Mail();
        // Se llama al metodo para imprimir una cadena
        auxs+= "CORTE DE CAJA\n\n";
        auxs+= "==========================================\n";
        auxs+= "FARMACIAS GI\n";
        auxs+= arr[1]+"\n";
        auxs+= "Iguala de la Independencia\n";
        auxs+= "Fecha: " + dateFormat.format(date) + " Hora: " + hourFormat.format(date) + "\n";
        auxs+= "Turno:    "+turno+"\n\n";
        auxs+= "=====VENTAS CONSULTORIO===================\n";
       // auxs+= "Descripcion    cant     Total";
        //for(Corte consulta: consultorio){
        //auxs+= " "+consulta.getNombreMedicamento()+"  "+consulta.getCantidadMedicamento()+"   "+consulta.getSumPrecioMedicamento()+" \n\n";
       // }
        //auxs+= "==========================================\n";
        auxs+= "PAGO A DOCTORES:      $ "+pagoDoctores+"\n";
        auxs+= "VENTAS CONSULTAS:     $ "+consultas+"\n";
        auxs+= "TOTAL:                $ "+(consultas - Double.parseDouble(pagoDoctores)) +"\n";
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
        mail.send_mail("cortesfarmaciagi@gmail.com", auxs, "CORTE DE CAJA CONSULTORIO TURNO: " + turno.toUpperCase()); //farmaciagi08@gmail.com
    }
    
}
