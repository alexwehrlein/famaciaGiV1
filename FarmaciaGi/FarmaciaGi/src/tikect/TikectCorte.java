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
import modelo.Gastos;
import modelo.sucursal;

/**
 *
 * @author saube
 */
public class TikectCorte {

    Confings confings;

    public void TikecCorte(String ventas, String consultorio, String devoluciones, String gastos, String abarrotes, String perfumeria, double total, String turno, ArrayList<String> clientes, String[] consultas, String retiros, int clave, String pc, ArrayList<Gastos> gastosT, String vR, String dR , String totalTabla ) {
        String mensaje = "";
        String prod = "";
        float totalVentas= Float.valueOf(ventas);
        float totalEntregar=totalVentas-Float.valueOf(gastos)-Float.valueOf(retiros);
        confings = new Confings(Integer.parseInt(pc));
        String[] arr = confings.settings();
        float diferencia = Float.parseFloat(totalTabla) - Float.parseFloat(ventas);
        float diferencia2 = totalEntregar - Float.parseFloat(totalTabla);
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");

        ServicioImp impServicio = new ServicioImp(); // se crea objeto 
        System.out.println(impServicio.getImpresoras()); // imprime todas las impresoras instaladas
        int clientesNum = clientes.size();
        String impra = arr[0]; // Nombre de la impresora

        sucursal su = new sucursal();
        String datSucursal[] = su.datosSucursal();
        Mail mail = new Mail();
        mensaje += "         *****CORTE DE CAJA*****\n\n";
        mensaje += "\n";
        mensaje +=  "             **FARMACIAS GI**\n";
        mensaje += "\n";
        mensaje += "SUCURSAL: "+datSucursal[1].toUpperCase() + " \n";
        mensaje += "Iguala de la Independencia\n";
        mensaje += "\n";
        mensaje += "Fecha: " + dateFormat.format(date) + " Hora: " + hourFormat.format(date) + "\n";
        mensaje += "Turno:    " + turno + "\n\n";
        mensaje += "===============INGRESOS===============\n";
        mensaje += "VENTAS FARMACIA:        $ " + ventas + "\n";
        mensaje += "VENTAS PERFUMERIA:      $ " + perfumeria + "\n";
        mensaje += "VENTAS ABARROTES:       $ " + abarrotes + "\n\n";
        //mensaje += "TOTAL VENTAS:           $ " + totalVentas + "\n";
        //mensaje += "DEVOLUCIONES:            $ " + devoluciones + "\n";
        mensaje += "\n";
        mensaje += "===============GASTOS===============\n";
        mensaje += "GASTOS TOTALES::                   $ " + gastos + "\n";
        for (int i = 0; i < gastosT.size(); i++) {
            if (gastosT.get(i).getDescripcion().length() > 17) { // si la descripcion_producto es mayor a 17 la corta
                prod = gastosT.get(i).getDescripcion().substring(0, 17);
            } else {
                prod = gastosT.get(i).getDescripcion();
            }
            // Se formatea la cadena a imprimir con String.format para varios string
            mensaje += String.format("%-18s" + " " + "%-5s", prod, gastosT.get(i).getTotal());
            mensaje += "\n";
        }
        mensaje += "===============DESCUENTOS===============\n";
        mensaje += "Cantidad de descuentos:   " + clientesNum + "\n";
        mensaje += "Clientes con descuento:   Des. Patente  Des. Generico \n";
        for (String string : clientes) {

            mensaje += string + ". \n";
        }
        mensaje += "===============RECARGAS===============\n";
        mensaje += "RECARGAS INICIALES:            $ " + dR + "\n";
        mensaje += "RECARGAS FINALES:              $ " + vR + "\n";
        mensaje += "TOTAL VENTAS RECARGAS:         $ " + (Double.parseDouble(dR) - Double.parseDouble(vR))  + "\n";
        if (Double.parseDouble(dR) <= 800) {
            mensaje += "QUEDA EN RECARGAS $ "+(Double.parseDouble(dR) - Double.parseDouble(vR))+" Y SE DEBE DE DEPOSITAR URGENTEMENTE" + "\n";
        }
        mensaje += "\n";
        mensaje += "===============RETIROS===============\n";
        if (clave == 0) {
            mensaje += "RETIROS:                $ " + retiros + "\n";
        }
        mensaje += "\n";
        mensaje += "************TOTAL DE VENTAS**************\n\n";
        mensaje += "VENTAS TOTALES:          $ " + totalVentas + "\n";
        mensaje += "GASTOS TOTALES:          $ " + gastos + "\n";
        if (clave == 0) {
        mensaje += "RETIROS:                 $ " + retiros + "\n";
        }
        mensaje += "****************************************\n";
        mensaje += "TOTAL A ENTREGAR:        $ "+ totalEntregar + "\n";
        mensaje += "****************************************\n";
        mensaje += "ENTREGAN CAJEROS:             $ "+ totalTabla + "\n";
        mensaje += "INGRESO ADICIONAL DE VENTAS:  $ " + diferencia2 + "\n";
        mensaje += "_______________________________________\n\n\n\n\n";

        try {
            impServicio.printCadena(impra, mensaje );
            // Cortar el papel ....
            byte[] cutP = new byte[]{0x1d, 'V', 1}; // comado para cortar
            impServicio.printBytes(impra, cutP); // se imprime el bruto 
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "<html><h1 align='center'>El tikect no se pudo imprimir </h1></html>", "warning", JOptionPane.WARNING_MESSAGE);
        }
        mail.send_mail("igualafarmaciagi@gmail.com", mensaje, "CORTE DE CAJA TURNO: " + turno.toUpperCase()); //farmaciagi08@gmail.com
    }

}
