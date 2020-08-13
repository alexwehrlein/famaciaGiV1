/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author alexwehrlein
 */
public class Retiros {

    private int id;
    private float precio;
    private String turno;
    private String fecha;
    private Connection con;
    Conexion conn = new Conexion();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Retiros() {
    }

    public Retiros(int id, float precio, String turno, String fecha) {
        this.id = id;
        this.precio = precio;
        this.turno = turno;
        this.fecha = fecha;
    }

    public Retiros(int id) {
        this.id = id;
    }

    public Retiros(float precio, String turno) {
        this.precio = precio;
        this.turno = turno;
    }

    public Retiros(String fecha) {
        this.fecha = fecha;
    }
    
    

    public String[] registrarRetiro() {
        String arr[] = {"", ""};
        try {
            con = conn.getConnection();
            Statement stm = (Statement) con.createStatement();
            stm.execute("INSERT INTO retiros VALUES(null," + getPrecio() + ",'" + getTurno() + "' , CURDATE() )");

            String sql = "SELECT last_insert_id() AS last_id FROM retiros";
            ResultSet resultado = stm.executeQuery(sql);
            if (resultado.next()) {
                arr[1] = resultado.getString("last_id");
            }

            stm.close();
        } catch (SQLException ex) {
            Logger.getLogger(Ventas.class.getName()).log(Level.SEVERE, null, ex);
            arr[0] = "1";
            return arr;
        } finally {
            conn.getClose();
        }
        arr[0] = "0";
        return arr;
    }

    public String[] tikect() {
        String arr[] = {"", "","",""};
        try {
            con = conn.getConnection();
            String sql = "SELECT * FROM retiros WHERE id ="+getId();
            Statement stm = (Statement) con.createStatement();
            ResultSet resultado = stm.executeQuery(sql);
            if (resultado.next()) {
                arr[0] = resultado.getString("id");
                arr[1] = resultado.getString("precio");
                arr[2] = resultado.getString("turno");
                arr[3] = resultado.getString("fecha");
            }
            stm.close();
        } catch (SQLException ex) {
            Logger.getLogger(Ventas.class.getName()).log(Level.SEVERE, null, ex);
            return arr;
        } finally {
            conn.getClose();
        }
        return arr;
    }

    public DefaultTableModel cargarRetiros(JTable jt) {
        jt.setDefaultRenderer(Object.class, new Render());
        JButton btnModificar = new JButton("Modificar");
        JButton btnEliminar = new JButton("Eliminar");
        //JComboBox tipo;
        TableColumn col = jt.getColumnModel().getColumn(1);
        //String op[] = {"Luz", "Agua", "Gas", "Producto"};
        //tipo = new JComboBox(op);
        // col.setCellEditor(new DefaultCellEditor(tipo));
        btnModificar.setName("btnModificar");
        btnEliminar.setName("btnEliminar");
        ImageIcon im = new ImageIcon(getClass().getResource("/imagenes/mo.png"));
        btnModificar.setIcon(im);
        ImageIcon ie = new ImageIcon(getClass().getResource("/imagenes/eli.png"));
        btnEliminar.setIcon(ie);
        jt.setRowHeight(25);

        DefaultTableModel modelo = (DefaultTableModel) jt.getModel();
        ArrayList<Retiros> arrayRetiros = new ArrayList<>();
        try {

            String sql = "SELECT * FROM retiros WHERE fecha = CURDATE()  order by id";
            Connection con = new Conexion().getConnection();
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            ResultSet resultado = pst.executeQuery();
            while (resultado.next()) {
                arrayRetiros.add(new Retiros(resultado.getInt("id"), resultado.getFloat("precio"), resultado.getString("turno"), resultado.getString("fecha")));
            }
            for (int i = 0; i < arrayRetiros.size(); i++) {
                modelo.addRow(new Object[]{arrayRetiros.get(i).getId(), arrayRetiros.get(i).getPrecio(), arrayRetiros.get(i).getTurno(), arrayRetiros.get(i).getFecha()});
            }
            con.close();
        } catch (SQLException ex) {
            //Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, ex);
        }

        return modelo;
    }
    
    public DefaultTableModel buscarRetiro(JTable jt) {
        jt.setDefaultRenderer(Object.class, new Render());
        JButton btnModificar = new JButton("Modificar");
        JButton btnEliminar = new JButton("Eliminar");
        //JComboBox tipo;
        TableColumn col = jt.getColumnModel().getColumn(1);
        //String op[] = {"Luz", "Agua", "Gas", "Producto"};
        //tipo = new JComboBox(op);
       // col.setCellEditor(new DefaultCellEditor(tipo));
        btnModificar.setName("btnModificar");
        btnEliminar.setName("btnEliminar");
       ImageIcon im = new ImageIcon(getClass().getResource("/imagenes/mo.png"));
       btnModificar.setIcon(im);
       ImageIcon ie = new ImageIcon(getClass().getResource("/imagenes/eli.png"));
       btnEliminar.setIcon(ie);
        jt.setRowHeight(25);

        DefaultTableModel modelo = (DefaultTableModel) jt.getModel();
        ArrayList<Retiros> arrayEgresos = new ArrayList<>();
        try {
            
            String sql = "SELECT * FROM retiros WHERE fecha = '"+getFecha()+"' or id = "+getId()+"  order by id";
            Connection con  =new Conexion().getConnection();
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            ResultSet resultado = pst.executeQuery();
            while (resultado.next()) {
                arrayEgresos.add(new Retiros(resultado.getInt("id"), resultado.getFloat("precio"), resultado.getString("turno"), resultado.getString("fecha")));
            }
            for (int i = 0; i < arrayEgresos.size(); i++) {
                modelo.addRow(new Object[]{arrayEgresos.get(i).getId(), arrayEgresos.get(i).getPrecio(), arrayEgresos.get(i).getTurno(), arrayEgresos.get(i).getFecha(),
                    arrayEgresos.get(i).getTurno()});
            }
            con.close();
        } catch (SQLException ex) {
            //Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, ex);
        }

        return modelo;
    }
    
}
