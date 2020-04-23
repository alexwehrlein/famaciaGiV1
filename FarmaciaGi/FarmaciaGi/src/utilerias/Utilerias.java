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
    
    public static String SUCURSALE = "Farmacia 6";
    
    public static boolean isMail(String mail) {
        return Pattern.matches("/^[a-zA-Z0-9._\\-]+\\@[a-zA-Z0-9._\\-]+(\\.[a-zA-Z]+)+$/", mail);
    }
    
    public static boolean isTelefono(String telefono) {
        return Pattern.matches("/^([0-9\\.\\(\\)\\- ]+)([\\. ]*[ext]*[\\. ]*[0-9]+)*$/",telefono);
    }
    
    public static boolean isNumber(String numero) {
        return Pattern.matches("/^[0-9]+$/",numero);
    }

    public static boolean isDecimal(String numero) {
         return Pattern.matches("/^[0-9]+[\\.]{0,1}[0-9]*$/", numero);
    }
    
    
}
