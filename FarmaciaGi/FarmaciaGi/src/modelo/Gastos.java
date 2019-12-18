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
import java.util.logging.*;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author saube
 */
public class Gastos {

    private int id;
    private String descripcion;
    private String total;
    private String turno;
    private String fecha;
    Conexion conexion;

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getTurno() {
        return turno;
    }

    public Gastos(int id) {
        this.id = id;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public Gastos(String descripcion, String total, String turno) {
        this.descripcion = descripcion;
        this.total = total;
        this.turno = turno;
    }

    public Gastos(int id, String descripcion, String total, String turno, String fecha) {
        this.id = id;
        this.descripcion = descripcion;
        this.total = total;
        this.turno = turno;
        this.fecha = fecha;
    }

    public Gastos(String descripcion, String total) {
        this.descripcion = descripcion;
        this.total = total;
    }
    
    public Gastos(String turno){
        this.turno = turno;
    }

    public Gastos() {

    }

    public ArrayList<Gastos> gastosT(String turno , String fecha , int tipo) {
        String sql = null ;
        if (tipo == 0) {
            sql = "SELECT * FROM gastos WHERE fecha = CURDATE() AND turno = '" +turno + "'";    
        }else{
            sql = "SELECT * FROM gastos WHERE fecha = '"+fecha+"' AND turno = '" +turno + "'";
        }
        
        ArrayList<Gastos> gastos = new ArrayList<>();
        
        try {
            Connection con = new Conexion().getConnection();
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            ResultSet resultado = pst.executeQuery();
             while (resultado.next()) {
                gastos.add(new Gastos(resultado.getString("descripcion"), String.format(Locale.US, "%.2f", resultado.getDouble("total"))));
             }
        } catch (Exception e) {
            Logger.getLogger(Gastos.class.getName()).log(Level.SEVERE, ""+e);
        }
        return gastos;

    }

    public boolean registrarGastos() {
        try {
            Connection connection = new Conexion().getConnection();
            Statement stm = (Statement) connection.createStatement();
            stm.execute("INSERT INTO gastos VALUES(null,'" + getDescripcion() + "',CURDATE()," + getTotal() + " , '" + getTurno() + "')");
            connection.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public DefaultTableModel cargarRegistroEgreso(JTable jt) {
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
        ArrayList<Gastos> arrayEgresos = new ArrayList<>();
        try {

            String sql = "SELECT * FROM gastos WHERE fecha = CURDATE()  order by descripcion";
            Connection con = new Conexion().getConnection();
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            ResultSet resultado = pst.executeQuery();
            while (resultado.next()) {
                arrayEgresos.add(new Gastos(resultado.getInt("id_gastos"), resultado.getString("descripcion"), String.format(Locale.US, "%.2f", resultado.getDouble("total")), resultado.getString("turno"), resultado.getString("fecha")));
            }
            for (int i = 0; i < arrayEgresos.size(); i++) {
                modelo.addRow(new Object[]{arrayEgresos.get(i).getId(), arrayEgresos.get(i).getDescripcion(), arrayEgresos.get(i).getTotal(), arrayEgresos.get(i).getFecha(),
                    arrayEgresos.get(i).getTurno()});
            }
            con.close();
        } catch (SQLException ex) {
            //Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, ex);
        }

        return modelo;
    }

    public DefaultTableModel buscarRegistroEgreso(JTable jt) {
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
        ArrayList<Gastos> arrayEgresos = new ArrayList<>();
        try {

            String sql = "SELECT * FROM gastos WHERE fecha = '" + getFecha() + "' or id_gastos = " + getId() + "  order by descripcion";
            Connection con = new Conexion().getConnection();
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            ResultSet resultado = pst.executeQuery();
            while (resultado.next()) {
                arrayEgresos.add(new Gastos(resultado.getInt("id_gastos"), resultado.getString("descripcion"), String.format(Locale.US, "%.2f", resultado.getDouble("total")), resultado.getString("turno"), resultado.getString("fecha")));
            }
            for (int i = 0; i < arrayEgresos.size(); i++) {
                modelo.addRow(new Object[]{arrayEgresos.get(i).getId(), arrayEgresos.get(i).getDescripcion(), arrayEgresos.get(i).getTotal(), arrayEgresos.get(i).getFecha(),
                    arrayEgresos.get(i).getTurno()});
            }
            con.close();
        } catch (SQLException ex) {
            //Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, ex);
        }

        return modelo;
    }

}
