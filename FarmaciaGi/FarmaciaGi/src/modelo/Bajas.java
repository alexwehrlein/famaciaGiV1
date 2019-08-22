/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import ArchivoLog.ArchivoLog;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alexwehrlein
 */
public class Bajas {

    private Connection con;
    private String codigo;
    private int piezas;
    private int existenxias;
    private int id_empleado;
    ArchivoLog log = new ArchivoLog();
    Conexion conn = new Conexion();

    public int getId_empleado() {
        return id_empleado;
    }

    public void setId_empleado(int id_empleado) {
        this.id_empleado = id_empleado;
    }
    
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public int getPiezas() {
        return piezas;
    }

    public void setPiezas(int piezas) {
        this.piezas = piezas;
    }

    public int getExistenxias() {
        return existenxias;
    }

    public void setExistenxias(int existenxias) {
        this.existenxias = existenxias;
    }

    public Bajas(String codigo) {
        this.codigo = codigo;
    }

    public Bajas(String codigo, int piezas, int existenxias , int id) {
        this.codigo = codigo;
        this.piezas = piezas;
        this.existenxias = existenxias;
        this.id_empleado = id;
    }

    public Bajas() {
    }

    public String Producto() {
        String sql = null, existencias = "";
        try {
            con = conn.getConnection();
            Statement stm = (Statement) con.createStatement();

            sql = "SELECT cantidad FROM productos WHERE codigo=" + getCodigo() + "";
            ResultSet rs = stm.executeQuery(sql);
            if (rs.next()) {
                existencias = rs.getString("cantidad");

            }
            stm.close();
            rs.close();

        } catch (SQLException ex) {
            log.crearLog(ex);
            Logger.getLogger(Bajas.class.getName()).log(Level.SEVERE, "Error " + ex);

        } finally {
            conn.getClose();
        }

        return existencias;
    }

    public void actualizarExistencias() {
        String sql = null;

        try {
            con = conn.getConnection();
            Statement stm = (Statement) con.createStatement();
            sql = "UPDATE productos SET cantidad = " + getExistenxias() + " WHERE codigo = " + getCodigo();
            stm.execute(sql);

            stm.close();
        } catch (SQLException ex) {
            log.crearLog(ex);
            Logger.getLogger(Bajas.class.getName()).log(Level.SEVERE, "Error " + ex);

        } finally {
            conn.getClose();
        }

    }

    public boolean insertarBajas() {
        String sql = null;
        boolean flag = true;
        
        try {
            con = conn.getConnection();
            con.setAutoCommit(false);
            
            Statement stm = (Statement) con.createStatement();
            sql = "INSERT INTO bajas (codigo,piezas,id_empleado) VALUES ( " + getCodigo() + " , " + getExistenxias() + " , "+getId_empleado()+")";
            stm.execute(sql);
            
            Statement stm2 = (Statement) con.createStatement();
            sql = "UPDATE productos SET cantidad = " + getExistenxias() + " WHERE codigo = " + getCodigo();
            stm2.execute(sql);
            
            con.commit();
            stm.close();
            stm.close();
        } catch (SQLException ex) {
            try {
                flag = false;
                con.rollback();
                log.crearLog(ex);
                Logger.getLogger(Bajas.class.getName()).log(Level.SEVERE, "Error " + ex);
            } catch (SQLException ex1) {
                Logger.getLogger(Bajas.class.getName()).log(Level.SEVERE, "Error "+ ex1);
            }

        } finally {
            conn.getClose();
        }
        return flag;

    }

}
