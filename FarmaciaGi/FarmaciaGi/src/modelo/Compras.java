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
public class Compras {
    
    private String fechaIni;
    private String fechaFin;
    private String nomProduc;
    private String nomEmpleado;
    private String fecha;
    private int piezas;
    
    private Connection con;
    Conexion conn = new Conexion();

    public String getFechaIni() {
        return fechaIni;
    }

    public void setFechaIni(String fechaIni) {
        this.fechaIni = fechaIni;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getNomProduc() {
        return nomProduc;
    }

    public void setNomProduc(String nomProduc) {
        this.nomProduc = nomProduc;
    }

    public String getNomEmpleado() {
        return nomEmpleado;
    }

    public void setNomEmpleado(String nomEmpleado) {
        this.nomEmpleado = nomEmpleado;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getPiezas() {
        return piezas;
    }

    public void setPiezas(int piezas) {
        this.piezas = piezas;
    }

    public Compras(String nomProduc, String nomEmpleado, String fecha, int piezas) {
        this.nomProduc = nomProduc;
        this.nomEmpleado = nomEmpleado;
        this.fecha = fecha;
        this.piezas = piezas;
    }
    
    public Compras(){
        
    }
    
    public Compras(String fechaIni, String fechaFin) {
        this.fechaIni = fechaIni;
        this.fechaFin = fechaFin;
    }
    
    public DefaultTableModel compras(JTable jt){
        
        jt.setDefaultRenderer(Object.class, new Render());
        jt.setRowHeight(25);
        DefaultTableModel modelo = (DefaultTableModel) jt.getModel();
        ArrayList<Compras> arrayCompras = new ArrayList<>();
        try {
            
            String sql = "SELECT productos.marca_comercial as producto , empleado.nombre as nombre , compraproductos.piezas , compraproductos.fecha "
                            + " FROM compraproductos " +
                            " INNER JOIN empleado on empleado.id_empleado = compraproductos.id_empleado " +
                            " INNER JOIN productos on productos.codigo = compraproductos.codigo " +
                            " WHERE compraproductos.fecha BETWEEN '"+getFechaIni()+"' AND '"+getFechaFin()+"' " +
                            " ORDER BY id";
            con = conn.getConnection();
            Statement stm = (Statement) con.createStatement();
            ResultSet resultado = stm.executeQuery(sql);
            while (resultado.next()) {
                arrayCompras.add(new Compras(resultado.getString("producto") , resultado.getString("nombre") , resultado.getString("fecha") , resultado.getInt("piezas")));
            }
            for (int i = 0; i < arrayCompras.size(); i++) {
                modelo.addRow(new Object[]{arrayCompras.get(i).getNomProduc(), arrayCompras.get(i).getNomEmpleado(), arrayCompras.get(i).getPiezas(),
                    arrayCompras.get(i).getFecha()});
            }
            stm.close();
        } catch (SQLException ex) {
            Logger.getLogger(Compras.class.getName()).log(Level.SEVERE, "Error "+ ex);
        }finally{
            conn.getClose();
        }
        System.out.println(arrayCompras.size());
        return modelo;
    }
    
    
}
