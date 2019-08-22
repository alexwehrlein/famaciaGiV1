/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ArchivoLog;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author saube
 */
public class ArchivoLog {
     FileWriter archivo;
    //nuestro archivo log
     
    public void crearLog(SQLException Operacion){
         try {
             //Pregunta el archivo existe, caso contrario crea uno con el nombre log.txt
             if (new File("C:/farmacia/log1.txt").exists() == false) {
                 try {
                     archivo = new FileWriter(new File("C:/farmacia/log1.txt"), false);
                 } catch (IOException ex) {
                     Logger.getLogger(ArchivoLog.class.getName()).log(Level.SEVERE, "Error "+ ex);
                 }
             }
             archivo = new FileWriter(new File("C:/farmacia/log1.txt"), true);
             Calendar fechaActual = Calendar.getInstance(); //Para poder utilizar el paquete calendar
             //Empieza a escribir en el archivo
             archivo.write("[" + (String.valueOf(fechaActual.get(Calendar.DAY_OF_MONTH))
                     + "/" + String.valueOf(fechaActual.get(Calendar.MONTH) + 1)
                     + "/" + String.valueOf(fechaActual.get(Calendar.YEAR))
                     + " " + String.valueOf(fechaActual.get(Calendar.HOUR_OF_DAY))
                     + ":" + String.valueOf(fechaActual.get(Calendar.MINUTE))
                     + ":" + String.valueOf(fechaActual.get(Calendar.SECOND))) + "]" + "[INFO]" + " " + Operacion + "\r\n");
             archivo.close(); //Se cierra el archivo
         } //Fin del metodo InfoLog
         catch (IOException ex) {
             Logger.getLogger(ArchivoLog.class.getName()).log(Level.SEVERE, "Error "+ ex);
         }
    }
    
     public void crearLogException(Exception Operacion){
         try {
             //Pregunta el archivo existe, caso contrario crea uno con el nombre log.txt
             if (new File("C:/farmacia/log2.txt").exists() == false) {
                 try {
                     archivo = new FileWriter(new File("C:/farmacia/log2.txt"), false);
                 } catch (IOException ex) {
                     Logger.getLogger(ArchivoLog.class.getName()).log(Level.SEVERE, "Error "+ ex);
                 }
             }
             archivo = new FileWriter(new File("C:/farmacia/log2.txt"), true);
             Calendar fechaActual = Calendar.getInstance(); //Para poder utilizar el paquete calendar
             //Empieza a escribir en el archivo
             archivo.write("[" + (String.valueOf(fechaActual.get(Calendar.DAY_OF_MONTH))
                     + "/" + String.valueOf(fechaActual.get(Calendar.MONTH) + 1)
                     + "/" + String.valueOf(fechaActual.get(Calendar.YEAR))
                     + " " + String.valueOf(fechaActual.get(Calendar.HOUR_OF_DAY))
                     + ":" + String.valueOf(fechaActual.get(Calendar.MINUTE))
                     + ":" + String.valueOf(fechaActual.get(Calendar.SECOND))) + "]" + "[INFO]" + " " + Operacion + "\r\n");
             archivo.close(); //Se cierra el archivo
         } //Fin del metodo InfoLog
         catch (IOException ex) {
             Logger.getLogger(ArchivoLog.class.getName()).log(Level.SEVERE, "Error "+ ex);
         }
    }

}
