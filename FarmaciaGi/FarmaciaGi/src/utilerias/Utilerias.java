/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilerias;

import java.util.regex.Pattern;

/**
 *
 * @author carlosguzman
 */
public class Utilerias {

    //CONEXION FARMACIAS GI 
    //private static String bd = "farmaciagi";
    //private static String login = "charlie";
    //private static String password = "carlos$%&jose78";
    //private static String password = "1234";
    
    //Conexion a DB 
    public final static String IP = "192.168.1.8:3306";
    public final static String DB = "farmaciagi";
    public final static String USER = "charlie";
    public final static String PASS = "carlos$%&jose78";

    //Correos de Corte de Caja
    public final static String MAIL_CORTES = "cortesfarmaciagi@gmail.com";

    //Correos de pedidos de Farmacia
    public final static String MAIL_PEDIDOS = "pedidosgisucursal@gmail.com";

    //Correos de Reporte Ventas, Reporte de Piezas agotadas
    public final static String MAIL_REPORTES = "cortesfarmaciagi@gmail.com";
 
    //Correos de Ingreso de Medicamentos
    public final static String MAIL_PRINCIPAL="farmaciagi08@gmail.com";
    
    
    //VALIDACIONES
    public static boolean isMail(String mail) {
        return Pattern.matches("/^[a-zA-Z0-9._\\-]+\\@[a-zA-Z0-9._\\-]+(\\.[a-zA-Z]+)+$/", mail);
    }

    public static boolean isTelefono(String telefono) {
        return Pattern.matches("/^([0-9\\.\\(\\)\\- ]+)([\\. ]*[ext]*[\\. ]*[0-9]+)*$/", telefono);
    }

    public static boolean isNumber(String numero) {
        return Pattern.matches("/^[0-9]+$/", numero);
    }

    public static boolean isDecimal(String numero) {
        return Pattern.matches("/^[0-9]+[\\.]{0,1}[0-9]*$/", numero);
    }

}
