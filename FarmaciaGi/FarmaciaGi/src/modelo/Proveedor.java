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
public class Proveedor {
    private int idproveedor;

    public int getIdproveedor() {
        return idproveedor;
    }

    public void setIdproveedor(int idproveedor) {
        this.idproveedor = idproveedor;
    }
    private String nombre;
    private String telefono;
    private String correo;
    Conexion conexion ;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Proveedor(int idproveedor, String nombre, String telefono, String correo) {
        this.idproveedor = idproveedor;
        this.nombre = nombre;
        this.telefono = telefono;
        this.correo = correo;
    }

    public Proveedor(int idproveedor) {
        this.idproveedor = idproveedor;
    }
    
    public Proveedor(){
        
    }
    
    @Override
    public String toString(){
        return this.nombre;
    }
    
    public boolean registrarProveedor(){
        try {
            Connection connection =new Conexion().getConnection();
           Statement stm= (Statement) connection.createStatement();
           stm.execute("INSERT INTO proveedor VALUES(null,'"+getNombre()+"','"+getTelefono()+"','"+getCorreo()+"')");
          connection.close();
       return  true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
              
        
    }
    
    public boolean ModificarRegristros(){
        try {
            Connection connection =new Conexion().getConnection();
            Statement stm= (Statement) connection.createStatement();
            stm.execute("UPDATE proveedor SET nombre='"+getNombre()+"', telefono='"+getTelefono()+"',correo='"+getCorreo()+"' WHERE id_proveedor="+getIdproveedor());
            connection.close();
            return  true;
        } catch (SQLException ex) {
            System.out.println(ex);
            return false;
        }
    }
    
    public boolean eliminarRegristro(){
        try {
            Connection connection =new Conexion().getConnection();
            Statement stm= (Statement) connection.createStatement();
            stm.execute("DELETE FROM proveedor WHERE id_proveedor="+getIdproveedor());
            connection.close();
            return  true;
        } catch (SQLException ex) {
            System.out.println(ex);
            return  false;
            
            
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
        ArrayList<Proveedor> arrayEgresos = new ArrayList<>();
        try {
            
            String sql = "SELECT * FROM proveedor order by nombre";
            Connection con  =new Conexion().getConnection();
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            ResultSet resultado = pst.executeQuery();
            while (resultado.next()) {
                arrayEgresos.add(new Proveedor(resultado.getInt("id_proveedor"), resultado.getString("nombre"), resultado.getString("telefono"), resultado.getString("correo")));
            }
            for (int i = 0; i < arrayEgresos.size(); i++) {
                modelo.addRow(new Object[]{ arrayEgresos.get(i).getIdproveedor(), arrayEgresos.get(i).getNombre(), arrayEgresos.get(i).getTelefono(),
                    arrayEgresos.get(i).getCorreo(), btnModificar, btnEliminar});
            }
            pst=null;
            pst.close();
            con.close();
        } catch (SQLException ex) {
            //Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, ex);
        }

        return modelo;
    }



}
