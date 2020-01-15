/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import ArchivoLog.ArchivoLog;
import com.mysql.jdbc.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

    public Bajas(String codigo, int piezas, int existenxias, int id) {
        this.codigo = codigo;
        this.piezas = piezas;
        this.existenxias = existenxias;
        this.id_empleado = id;
    }
    
    public Bajas(String codigo, int piezas, int existenxias) {
        this.codigo = codigo;
        this.piezas = piezas;
        this.existenxias = existenxias;
    }

    public Bajas(String codigo, int piezas) {
        this.codigo = codigo;
        this.piezas = piezas;
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

    public Boolean GuadarListaBajas(DefaultTableModel modelo) {
        String sql = null;
        try {
            for (int i = 0; i < modelo.getRowCount(); i++) {
                con = conn.getConnection();
                sql = "INSERT INTO bajas_temporales (codigo,cantidad) VALUES ( '" + modelo.getValueAt(i, 0).toString() + "' , " + modelo.getValueAt(i, 1).toString() + " )";
                com.mysql.jdbc.PreparedStatement stmt = (com.mysql.jdbc.PreparedStatement) con.prepareStatement(sql);
                stmt.execute(sql);
                stmt.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Bajas.class.getName()).log(Level.SEVERE, "Error " + ex);
            return false;
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(Bajas.class.getName()).log(Level.SEVERE, "" + ex);
            }
        }
        return true;

    }
    
     public boolean insertarBajasIn() {
        String sql = null;
        boolean next = true;
        try {
            con = conn.getConnection();
            con.setAutoCommit(false);
            Statement stm = (Statement) con.createStatement();
            sql = "INSERT INTO bajas (codigo,piezas,id_empleado) VALUES ( " + getCodigo() + " , " + getPiezas()+ " , "+getId_empleado()+")";
            stm.execute(sql);
            
            sql = "UPDATE productos SET cantidad = " + getExistenxias() + " WHERE codigo = " + getCodigo();
            stm.execute(sql);

            stm.close();
            con.commit();
        } catch (SQLException ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(Bajas.class.getName()).log(Level.SEVERE , ""+ex1);
            }
            log.crearLog(ex);
            Logger.getLogger(Bajas.class.getName()).log(Level.SEVERE, "Error " + ex);
            next = false;
        } finally {
            conn.getClose();
        }
        return next;
    }

    public String[] insertarBajas(DefaultTableModel modelo, int id_empleado) {
        String sql = null, mensajeCodigo = "", mensajeExistencias = "", arr[] = {"", "", ""};
        int existencias = 0;

        try {
            con = conn.getConnection();
            con.setAutoCommit(false);
            for (int i = 0; i < modelo.getRowCount(); i++) {

                Statement stm3 = (Statement) con.createStatement();
                sql = "SELECT cantidad FROM productos WHERE codigo=" + modelo.getValueAt(i, 0).toString() + "";
                ResultSet rs = stm3.executeQuery(sql);
                if (rs.next()) {
                    existencias = rs.getInt("cantidad");
                } else {
                    mensajeCodigo += "El codigo: " + modelo.getValueAt(i, 0).toString() + " no existe. \n";
                    continue;
                }

                if (Integer.parseInt(modelo.getValueAt(i, 1).toString()) > existencias) {
                    mensajeExistencias += "El codigo: " + modelo.getValueAt(i, 0).toString() + " no puede dar de baja mas de las existencias. \n";
                    continue;
                }

                int canT = existencias - Integer.parseInt(modelo.getValueAt(i, 1).toString());

                Statement stm2 = (Statement) con.createStatement();
                sql = "UPDATE productos SET cantidad = " + canT + " WHERE codigo = " + modelo.getValueAt(i, 0).toString();
                stm2.execute(sql);

                Statement stm = (Statement) con.createStatement();
                sql = "INSERT INTO bajas (codigo,piezas,id_empleado) VALUES ( " + modelo.getValueAt(i, 0).toString() + " , " + modelo.getValueAt(i, 1).toString() + " , " + id_empleado + ")";
                stm.execute(sql);

                stm.close();
                stm2.close();
                stm3.close();
            }
            arr[0] = "1";
            arr[1] = mensajeCodigo;
            arr[2] = mensajeExistencias;
            con.commit();
            Statement stm = (Statement) con.createStatement();
            sql = "TRUNCATE bajas_temporales";
            stm.execute(sql);
            stm.close();
            stm.close();
        } catch (SQLException ex) {
            try {
                arr[0] = "0";
                arr[1] = mensajeCodigo;
                arr[2] = mensajeExistencias;
                con.rollback();
                log.crearLog(ex);
                Logger.getLogger(Bajas.class.getName()).log(Level.SEVERE, "Error " + ex);
            } catch (SQLException ex1) {
                Logger.getLogger(Bajas.class.getName()).log(Level.SEVERE, "Error " + ex1);
            }

        } finally {
            conn.getClose();
        }
        return arr;
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
        ArrayList<Bajas> arrayEgresos = new ArrayList<>();
        try {

            String sql = "SELECT * FROM bajas_temporales";
            Connection con = new Conexion().getConnection();
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            ResultSet resultado = pst.executeQuery();
            while (resultado.next()) {
                arrayEgresos.add(new Bajas(resultado.getString("codigo"), resultado.getInt("cantidad")));
            }
            for (int i = 0; i < arrayEgresos.size(); i++) {
                modelo.addRow(new Object[]{arrayEgresos.get(i).getCodigo(), arrayEgresos.get(i).getPiezas()});
            }
            pst = null;
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(Bajas.class.getName()).log(Level.SEVERE, "" + ex);
        }
        return modelo;
    }

}
