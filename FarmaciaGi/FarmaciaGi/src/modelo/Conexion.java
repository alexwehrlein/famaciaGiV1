/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author saube
 */
public class Conexion {
    private static String bd = "farmaciagijava";
    private static String login = "root";
    private static String password = "";
    //private static String password = "1234";
 //   private static String url = "jdbc:mysql://192.168.1.11:3306/" + bd;
    private static String url ="jdbc:mysql://127.0.0.1/"+bd;
    Connection conn = null;
    public Connection getConnection() {
        
        try {
            //obtenemos el driver de para mysql
            Class.forName("com.mysql.jdbc.Driver");
            //obtenemos la conexión
            conn = (Connection) DriverManager.getConnection(url, login, password);
            if (conn != null) {
              //  System.out.println("Base de datos  " + bd + " conectado");
                //
              // JOptionPane.showMessageDialog(null, "Base de datos  " + bd + " conectado");
            }
            //System.out.println("sii");
        } catch (SQLException e) {
            //System.out.println(e);
            JOptionPane.showMessageDialog(null,e.getMessage());
            //System.out.println("nnn");
        } catch (ClassNotFoundException e) {
            //System.out.println(e);
            JOptionPane.showMessageDialog(null,e.getMessage());
        }finally{
             return conn;
        }
       
    }
    
    public void getClose(){
        try {
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public static void main(String[] args) {
        Conexion v=new Conexion();
        v.getConnection();
    }
    
}
