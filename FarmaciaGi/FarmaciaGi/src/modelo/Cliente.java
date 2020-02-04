/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import ArchivoLog.ArchivoLog;
import com.mysql.jdbc.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author Jose Abada Nava
 */
public class Cliente {

    private Connection con;
    private int id_cliente;
    private String nombre;
    private String correo;
    private String telefono;
    private String password;
    Conexion conn = new Conexion();
    ArchivoLog log = new ArchivoLog();

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Cliente(int id_cliente, String nombre, String correo, String telefono) {
        this.id_cliente = id_cliente;
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
    }

    public Cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public Cliente() {
        con = new Conexion().getConnection();
        try {
            con.setAutoCommit(false);
        } catch (SQLException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public DefaultTableModel cargarTablaRegistroCliente(JTable tabla) {
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        try {
            String sql = "SELECT id_cliente,nombre FROM cliente WHERE id_cliente > 1;";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet resultado = pst.executeQuery();
            while (resultado.next()) {
                modelo.addRow(new Object[]{resultado.getInt("id_cliente"), resultado.getString("nombre")});
            }
            con.close();
        } catch (SQLException ex) {
            log.crearLog(ex);
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, "Error " + ex);
        }

        return modelo;
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
        ArrayList<Cliente> arrayCliente = new ArrayList<>();
        try {

            String sql = "SELECT * FROM cliente order by nombre";
            //  Connection con  =new Conexion().getConnection();
            com.mysql.jdbc.PreparedStatement pst = (com.mysql.jdbc.PreparedStatement) con.prepareStatement(sql);
            ResultSet resultado = pst.executeQuery();
            while (resultado.next()) {
                arrayCliente.add(new Cliente(resultado.getInt("id_cliente"), resultado.getString("nombre"), resultado.getString("correo"), resultado.getString("telefono")));
            }
            for (int i = 0; i < arrayCliente.size(); i++) {
                modelo.addRow(new Object[]{arrayCliente.get(i).getId_cliente(), arrayCliente.get(i).getNombre(), arrayCliente.get(i).getCorreo(),
                    arrayCliente.get(i).getTelefono(), btnModificar, btnEliminar});
            }
            con.close();
        } catch (SQLException ex) {
            log.crearLog(ex);
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, "Error " + ex);
        }

        return modelo;
    }

    public boolean registrarCliente() {
        try {
            Connection connection = new Conexion().getConnection();
            Statement stm = (Statement) connection.createStatement();
            stm.execute("INSERT INTO cliente VALUES(null,'" + getNombre() + "','" + getCorreo() + "','" + getTelefono() + "')");
            connection.close();
            return true;
        } catch (SQLException ex) {
            log.crearLog(ex);
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, "Error " + ex);
            return false;
        }

    }

    public boolean ModificarRegristros() {
        try {
            Connection connection = new Conexion().getConnection();
            Statement stm = (Statement) connection.createStatement();
            stm.execute("UPDATE cliente SET nombre='" + getNombre() + "', correo='" + getCorreo() + "',telefono='" + getTelefono() + "' WHERE id_cliente=" + getId_cliente());
            connection.close();
            return true;
        } catch (SQLException ex) {
            log.crearLog(ex);
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, "Error " + ex);
            return false;
        }
    }

    public boolean eliminarRegristro() {
        try {
            Connection connection = new Conexion().getConnection();
            Statement stm = (Statement) connection.createStatement();
            stm.execute("DELETE FROM cliente WHERE id_cliente=" + getId_cliente());
            connection.close();
            return true;
        } catch (SQLException ex) {
            log.crearLog(ex);
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, "Error " + ex);
            return false;

        }
    }

    public boolean validarPassword(String clave, String password) {
        boolean next = true;
        try {
            con = conn.getConnection();
            java.sql.Statement stm = (java.sql.Statement) con.createStatement();

            String sql = "SELECT password FROM cliente WHERE password='" + password + "' AND id_cliente=" + clave;
            ResultSet rs = stm.executeQuery(sql);
            if (rs.next()) {
                next = true;
            } else {
                next = false;
            }
            stm.close();
            rs.close();
        } catch (SQLException ex) {
            log.crearLog(ex);
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, "Error " + ex);

        } finally {
            conn.getClose();
        }
        return next;

    }

}
