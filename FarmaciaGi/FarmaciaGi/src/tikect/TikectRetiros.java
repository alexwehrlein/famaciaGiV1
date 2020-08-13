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
import modelo.Confings;

/**
 *
 * @author alexwehrlein
 */
public class TikectRetiros {

    Confings confings;

    public void TikectRetiros(String arr[]) {

        confings = new Confings();
        String[] arr2 = confings.settings();

        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");
        DateFormat formatofecha = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        ServicioImp impServicio = new ServicioImp(); // se crea objeto 
        System.out.println(impServicio.getImpresoras()); // imprime todas las impresoras instaladas
        String auxs = "";

        String impra = arr[0]; // Nombre de la impresora

        // Se llama al metodo para imprimir una cadena
        auxs += "GASTO\n\n";
        auxs += "FARMACIAS GI\n";
        auxs += arr[1]+"\n";
        auxs += "Iguala de la Independencia\n";
        //impServicio.printCadena(impra, "Folio: " + folio + "\n");
        auxs += "Fecha: " + formatofecha.format(date) +"\n";
        auxs += "==========================================\n";
        auxs += "Folio     Total\n";
        auxs += "==========================================\n";
        // Se formatea la cadena a imprimir con String.format para varios string
        auxs += String.format("%-10s" + " " + "$%-8s", arr[0], arr[1]);
        auxs += "\n";

        auxs += "\n==========================================\n";
        auxs += "    Retiros     \n Farmacia gi\n\n\n\n\n";// Varios saltos para no cortar antes

        try {
            impServicio.printCadena(impra, auxs);
            // Cortar el papel ....
            byte[] cutP = new byte[]{0x1d, 'V', 1}; // comado para cortar
            impServicio.printBytes(impra, cutP); // se imprime el bruto 
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "<html><h1 align='center'>El tikect no se pudo imprimir </h1></html>", "warning", JOptionPane.WARNING_MESSAGE);
        }
    }
}
