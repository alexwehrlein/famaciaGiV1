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
import utilerias.Utilerias;

/**
 *
 * @author saube
 */
public class TikectCorteConsulta {
    Confings confings;
    
    public void Tikect(double consultas,String turno, String pc ,  ArrayList<Corte> consultorio, String pagoDoctores , String[] consulta){
        confings = new Confings(Integer.parseInt(pc));
        String[] arr = confings.settings();
        float total = (Integer.parseInt(consulta[0]) * 35) + (Integer.parseInt(consulta[1]) * 15) + (Integer.parseInt(consulta[2]) * 15) + (Integer.parseInt(consulta[3]) * 15);
        Date date = new Date();
       
	 SimpleDateFormat formate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

       
        ServicioImp impServicio = new ServicioImp(); // se crea objeto 
        System.out.println(impServicio.getImpresoras()); // imprime todas las impresoras instaladas
        String auxs="";
       
        String impra = arr[0]; // Nombre de la impresora
        Mail mail = new Mail();
        // Se llama al metodo para imprimir una cadena
        auxs+= "CORTE DE CAJA \n\n";
        auxs+= "****************************************\n";
        auxs+= "FARMACIAS GI\n";
        auxs+= arr[1]+"\n";
        auxs+= "Iguala de la Independencia\n";
        auxs+= "Fecha: " + formate.format(date) + "\n";
        auxs+= "Turno:    "+turno+"\n\n";
        auxs+= " ********* VENTAS CONSULTORIO ********* \n";
        auxs+= "* TOTAL VENTAS CONSULTORIO:  $ "+consultas+"\n";
        auxs+= "* PAGO A DOCTORES:     $ "+pagoDoctores+"\n\n";
        auxs+= "*   VENTAS  : --->> $ "+(consultas - Double.parseDouble(pagoDoctores)) +"\n\n";
        auxs+= " *********** DESCRIPCION ***************\n";
        auxs+= "* CONSULTAS: "+ Integer.parseInt(consulta[0])+ " ---->  $ "+Integer.parseInt(consulta[0]) * 35+"\n";
        auxs+= "* APLICACIONES: "+Integer.parseInt(consulta[1])+ " ---->  $ "+Integer.parseInt(consulta[1]) * 15+"\n";
        auxs+= "* T/A:      "+Integer.parseInt(consulta[2])+ "  ---->   $ "+Integer.parseInt(consulta[2]) * 15+"\n";
        auxs+= "* GLUCOSAS:   "+Integer.parseInt(consulta[3])+" ---->  $ "+Integer.parseInt(consulta[3]) * 15+"\n\n";
        auxs+= "*   DEBEN PAGAR A MÉDICO : ---> $ "+total+"\n\n";
      
        auxs+= "_________________________________________\n";
       
        auxs+= "====================================\n\n\n\n\n";
        try {
            impServicio.printCadena(impra, auxs);
            // Cortar el papel ....
            byte[] cutP = new byte[]{0x1d, 'V', 1}; // comado para cortar
            impServicio.printBytes(impra, cutP); // se imprime el bruto 
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "<html><h1 align='center'>El tikect no se pudo imprimir</h1></html>","warning",JOptionPane.WARNING_MESSAGE);
        }
        Utilerias util = new Utilerias();
        mail.send_mail(Utilerias.MAIL_CORTES, auxs, "CORTE DE CAJA CONSULTORIO TURNO: " + turno.toUpperCase(),0); //farmaciagi08@gmail.com
    }
    
}
