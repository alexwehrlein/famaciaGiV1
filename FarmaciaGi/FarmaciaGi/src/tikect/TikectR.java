/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikect;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.Confings;

/**
 *
 * @author saube
 */
public class TikectR {

    Confings confings;

    public void tikectR(String[] infoT, List<List<String>> productos, String pc) {
        confings = new Confings(Integer.parseInt(pc));
        String[] arr = confings.settings();

        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");

        ServicioImp impServicio = new ServicioImp(); // se crea objeto 
        System.out.println(impServicio.getImpresoras()); // imprime todas las impresoras instaladas
        String auxs = "";
        String prod = "";
        float total = 0;
        String impra = arr[0]; // Nombre de la impresora

        // Se llama al metodo para imprimir una cadena
        auxs += "COMPROBANTE DE VENTA\n\n";
        auxs += "FARMACIAS GI\n";
        auxs += arr[1] + "\n";
        auxs += "Iguala de la Independencia\n";
        auxs += "Folio: " + infoT[0] + "\n";
        auxs += "Le atendio: " + infoT[3] + "\n";
        auxs += "Fecha: " + infoT[1] + "\n";
        auxs += "Cliente: " + infoT[2] + "\n";
        auxs += "==========================================\n";
        auxs += "Cant Descripcion       Precio    Importe\n";
        auxs += "==========================================\n";
        for (int i = 0; i <= productos.get(0).size() - 1; i++) // for ejemplo para varios productos
        {

            if (productos.get(1).get(i).length() > 17) { // si la descripcion_producto es mayor a 17 la corta
                prod = productos.get(1).get(i).substring(0, 17);
            } else {
                prod = productos.get(1).get(i);
            }
            // Se formatea la cadena a imprimir con String.format para varios string
            auxs += String.format("%-4s" + " " + "%-17s" + " " + "$%-8s" + " " + "$%-8s", productos.get(0).get(i), prod, productos.get(2).get(i), productos.get(3).get(i));
            auxs += "\n";

            total += Integer.parseInt(productos.get(3).get(i));
        }

        auxs += "\n";
        auxs += String.format("\t                TOTAL:" + " " + "$%-10s", total);
        auxs += "\n";
        auxs += String.format("\t         PAGO CLIENTE:" + " " + "$%-10s", infoT[5]);

        auxs += "\n";
        auxs += String.format("\t               CAMBIO:" + " " + "$%-10s", infoT[6]);
        auxs += "\n==========================================\n";
        auxs += "\n==========================================\n";
        auxs += "Gracias por su compra\n Expertos en tu salud\n\n\n\n\n";// Varios saltos para no cortar antes

        try {
            impServicio.printCadena(impra, auxs);
            // Cortar el papel ....
            byte[] cutP = new byte[]{0x1d, 'V', 1}; // comado para cortar
            impServicio.printBytes(impra, cutP); // se imprime el bruto 
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "<html><h1 align='center'>El tikect no se pudo imprimir</h1></html>", "warning", JOptionPane.WARNING_MESSAGE);
        }

    }
}
