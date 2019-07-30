/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author saube
 */
public class Confings {

    private String impresora;
    private String direccion;
    private Connection con;
    Conexion conn = new Conexion();

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    
    public String getImpresora() {
        return impresora;
    }

    public void setImpresora(String impresora) {
        this.impresora = impresora;
    }

    public Confings() {
    }

    public Confings(String impresora, String direccion) {
        this.impresora = impresora;
        this.direccion = direccion;
    }
    
    public boolean settingsEdit() {
        String sql = null;

        try {
            con = conn.getConnection();
            Statement stm = (Statement) con.createStatement();
            sql = "SELECT * FROM configuraciones";
            ResultSet rs = stm.executeQuery(sql);
            if (rs.next()) {
                sql = "UPDATE configuraciones SET impresora = '" + getImpresora() + "' , direccion = '"+getDireccion()+"'";
                stm.execute(sql);
            } else {
                sql = "INSERT INTO  configuraciones (impresora,direccion) VALUES ('"+getImpresora()+"' , '"+getDireccion()+"')";
                stm.execute(sql);
            }
            rs.close();
            stm.close();
        } catch (SQLException ex) {
            Logger.getLogger(Confings.class.getName()).log(Level.SEVERE, "Error " + ex);
            return false;
        } finally {
            conn.getClose();
        }
        return true;
    }

    public String[] settings() {
        String sql = null;
        String[] arr = {"",""};
        try {

            con = conn.getConnection();
            Statement stm = (Statement) con.createStatement();

            sql = "SELECT * FROM configuraciones";
            ResultSet rs = stm.executeQuery(sql);
            if (rs.next()) {
                arr[0] = rs.getString("impresora");
                arr[1] = rs.getString("direccion");
            }
            stm.close();
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(Ventas.class.getName()).log(Level.SEVERE, "Error " + ex);

        } finally {
            conn.getClose();
        }

        return arr;
    }

}
