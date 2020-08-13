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
import mail.Mail;
import modelo.Confings;
import modelo.sucursal;
import utilerias.Utilerias;

/**
 *
 * @author saube
 */
public class TikectInventario {

    Confings confings;

    public void tikectInventario(String turno, DefaultTableModel modelo, String pc) {
        confings = new Confings(Integer.parseInt(pc));
        String[] arr = confings.settings();
        String mensaje = "";
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");
        DateFormat formatofecha = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");


        ServicioImp impServicio = new ServicioImp(); // se crea objeto 
        System.out.println(impServicio.getImpresoras()); // imprime todas las impresoras instaladas
        String auxs = "";
        String prod = "";
        String impra = arr[0]; // Nombre de la impresora

        // Se llama al metodo para imprimir una cadena
        auxs += "PRODUCTOS AGREGADOS\n\n";
        auxs += "FARMACIAS GI\n";
        auxs += arr[1] + "\n";
        auxs += "Iguala de la Independencia\n";
        auxs += "Turno: " + turno + "\n";
        auxs += "Fecha: " + formatofecha.format(date) +"\n";
        auxs += "==========================================\n";
        auxs += "Descripcion          piezas\n";
        auxs += "==========================================\n";

        for (int i = 0; i < modelo.getRowCount(); i++) // for ejemplo para varios productos
        {

            if (modelo.getValueAt(i, 1).toString().length() > 17) { // si la descripcion_producto es mayor a 17 la corta
                prod = modelo.getValueAt(i, 1).toString().substring(0, 17);
            } else {
                prod = modelo.getValueAt(i, 1).toString();
            }
            // Se formatea la cadena a imprimir con String.format para varios string
            auxs += String.format("%-18s" + "           " + "%-5s", prod, modelo.getValueAt(i, 2));
            auxs += "\n";
        }

        auxs += "\n==========================================\n";
        auxs += "Ingreso de medicamento  \n Farmacia gi\n\n\n\n\n";// Varios saltos para no cortar antes

        sucursal su = new sucursal();
        String datSucursal[] = su.datosSucursal();

        Mail mail = new Mail();

        try {
            impServicio.printCadena(impra, auxs);
            // Cortar el papel ....
            byte[] cutP = new byte[]{0x1d, 'V', 1}; // comado para cortar
            impServicio.printBytes(impra, cutP); // se imprime el bruto 
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "<html><h1 align='center'>El tikect no se pudo imprimir </h1></html>", "warning", JOptionPane.WARNING_MESSAGE);
         Utilerias util = new Utilerias();
            mail.send_mail(Utilerias.MAIL_PRINCIPAL, auxs, "INGRESO DE MEDICAMENTO TURNO: " + turno.toUpperCase(), 0); //farmaciagi08@gmail.com
        }

    }

}
