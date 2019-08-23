/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import com.mysql.jdbc.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author saube
 */
public class Confings {

    private int id;
    private String impresora;
    private String direccion;
    private int pc;
    private Connection con;
    Conexion conn = new Conexion();

    public int getPc() {
        return pc;
    }

    public void setPc(int pc) {
        this.pc = pc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public Confings(int id, String impresora, String direccion, int pc) {
        this.impresora = impresora;
        this.direccion = direccion;
        this.id = id;
        this.pc = pc;
    }

    public Confings(String impresora, String direccion, int pc) {
        this.impresora = impresora;
        this.direccion = direccion;
        this.pc = pc;
    }

    public Confings(int pc) {
        this.pc = pc;
    }

    public boolean settingsSave() {
        String sql = null;

        try {
            con = conn.getConnection();
            Statement stm = (Statement) con.createStatement();
            sql = "INSERT INTO  configuraciones (impresora,direccion,PC) VALUES ('" + getImpresora() + "' , '" + getDireccion() + "' , " + getPc() + ")";
            stm.execute(sql);
            stm.close();
        } catch (SQLException ex) {
            Logger.getLogger(Confings.class.getName()).log(Level.SEVERE, "Error " + ex);
            return false;
        } finally {
            conn.getClose();
        }
        return true;
    }

    public boolean settingsEdit() {
        String sql = null;

        try {
            con = conn.getConnection();
            Statement stm = (Statement) con.createStatement();
            sql = "UPDATE configuraciones SET impresora = '" + getImpresora() + "' , direccion = '" + getDireccion() + "' WHERE PC = " + getPc();
            stm.execute(sql);
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
        String[] arr = {"", ""};
        try {

            con = conn.getConnection();
            Statement stm = (Statement) con.createStatement();

            sql = "SELECT * FROM configuraciones WHERE PC = "+getPc();
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

    public DefaultTableModel cargarImpresoras(JTable jt) {
        jt.setRowHeight(25);
        DefaultTableModel modelo = (DefaultTableModel) jt.getModel();
        ArrayList<Confings> arrayConfigs = new ArrayList<>();
        try {

            String sql = "SELECT * FROM configuraciones order by id";
            con = conn.getConnection();
            Statement stm = (Statement) con.createStatement();
            ResultSet resultado = stm.executeQuery(sql);
            while (resultado.next()) {
                arrayConfigs.add(new Confings(resultado.getInt("id"), resultado.getString("impresora"), resultado.getString("direccion"), resultado.getInt("PC")));
            }
            for (int i = 0; i < arrayConfigs.size(); i++) {
                int pc = arrayConfigs.get(i).getPc();
                String nomPc = "";
                switch (pc) {
                    case 1:
                        nomPc = "PC 1";
                        break;
                    case 2:
                        nomPc = "PC 2";
                        break;
                    case 3:
                        nomPc = "PC 3";
                        break;
                    case 4:
                        nomPc = "PC 4";
                        break;
                    case 5:
                        nomPc = "PC 5";
                        break;
                }
                modelo.addRow(new Object[]{arrayConfigs.get(i).getId(), arrayConfigs.get(i).getImpresora(), arrayConfigs.get(i).getDireccion(), nomPc});
            }
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(Confings.class.getName()).log(Level.SEVERE, "ERROR " + ex);
        }

        return modelo;
    }

}
