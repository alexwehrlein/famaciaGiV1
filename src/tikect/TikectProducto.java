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
import mail.Mail;
import modelo.Confings;
import utilerias.Utilerias;

/**
 *
 * @author saube
 */
public class TikectProducto {

    Confings confings;

    public void tikectProducto(String turno, String nombre, int piezas, String pc, String nombreEmpleado, String tipo) {
        confings = new Confings(Integer.parseInt(pc));
        String[] arr = confings.settings();
        String mensaje = "";
        Date date = new Date();

        DateFormat formatofecha = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        ServicioImp impServicio = new ServicioImp(); // se crea objeto 
        System.out.println(impServicio.getImpresoras()); // imprime todas las impresoras instaladas
        String auxs = "";
        String prod = "";

        String impra = arr[0]; // Nombre de la impresora

        // Se llama al metodo para imprimir una cadena
        auxs += "PRODUCTO AGREGADO\n\n";
        auxs += "FARMACIAS GI\n";
        auxs += arr[1] + "\n";
        auxs += "Iguala de la Independencia\n";
        auxs += "Nombre del empleado: " + nombreEmpleado + "\n";
        auxs += "Turno: " + turno + "\n";
        auxs += "Fecha: " + formatofecha.format(date) + "\n";
        auxs += "==========================================\n";
        auxs += "Descripcion       piezas    tipo \n";
        auxs += "==========================================\n";

        if (nombre.length() > 17) { // si la descripcion_producto es mayor a 17 la corta
            prod = nombre.substring(0, 17);
        } else {
            prod = nombre;
        }
        // Se formatea la cadena a imprimir con String.format para varios string
        auxs += String.format("%-18s" + "           " + "%-5s", prod, piezas, tipo);
        auxs += "\n";

        auxs += "\n==========================================\n";
        auxs += "Alta de medicamento  \n Farmacia gi\n\n\n\n\n";// Varios saltos para no cortar antes

        Mail mail = new Mail();

        try {
            impServicio.printCadena(impra, auxs);
            // Cortar el papel ....
            byte[] cutP = new byte[]{0x1d, 'V', 1}; // comado para cortar
            impServicio.printBytes(impra, cutP); // se imprime el bruto 
        } catch (Exception e) {
            Utilerias util = new Utilerias();
            JOptionPane.showMessageDialog(null, "<html><h1 align='center'>El tikect no se pudo imprimir </h1></html>", "warning", JOptionPane.WARNING_MESSAGE);
            mail.send_mail(Utilerias.MAIL_PRINCIPAL, auxs, "INGRESO DE MEDICAMENTO TURNO: " + turno.toUpperCase(), 0); //farmaciagi08@gmail.com
        }
    }

}
