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
 * @author saube
 */
public class TikectTurno {

    Confings confings;

    public void TikectTurno(int turno , String nombre, String pc) {

        confings = new Confings(Integer.parseInt(pc));
        String[] arr = confings.settings();

        Date date = new Date();
        DateFormat formatofecha = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        ServicioImp impServicio = new ServicioImp(); // se crea objeto 
        System.out.println(impServicio.getImpresoras()); // imprime todas las impresoras instaladas
        String auxs = "";

        String impra = arr[0]; // Nombre de la impresora

        auxs += "              TURNO\n\n";
        auxs += "            FARMACIAS GI\n";
        auxs += "\n";
        auxs += arr[1] + "\n";
        auxs += "Iguala de la Independencia\n";
        auxs += "TURNO: " + turno + "\n";
        auxs += "TIPO:   " + nombre + "\n";
        auxs += "Fecha: " + formatofecha.format(date) + "\n";
        auxs += "==========================================\n";
        auxs += "          ¡Gracias por su compra!\n          ¡Expertos en tu salud!\n\n\n";// Varios saltos para no cortar antes
        
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
