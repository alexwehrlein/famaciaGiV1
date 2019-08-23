/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import com.mysql.jdbc.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author saube
 */
public class Usuarios {

    private int idLogin;
    private String pc;
    private String usuario;
    private String passwork;
    private int idEmpleado;
    private String nombre;
    private Connection con;
    Conexion conn = new Conexion();

    public String getPc() {
        return pc;
    }

    public void setPc(String pc) {
        this.pc = pc;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getIdLogin() {
        return idLogin;
    }

    public void setIdLogin(int idLogin) {
        this.idLogin = idLogin;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPasswork() {
        return passwork;
    }

    public void setPasswork(String passwork) {
        this.passwork = passwork;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public Usuarios(int idLogin, String usuario, String passwork, String pc, String nombre) {
        this.idLogin = idLogin;
        this.usuario = usuario;
        this.passwork = passwork;
        this.pc = pc;
        this.nombre = nombre;
    }

    public Usuarios(int idLogin, String usuario, String passwork, String pc, int idEmpleado) {
        this.idLogin = idLogin;
        this.usuario = usuario;
        this.passwork = passwork;
        this.pc = pc;
        this.idEmpleado = idEmpleado;
    }

    public Usuarios(int idLogin) {
        this.idLogin = idLogin;
    }

    public Usuarios() {

    }

    public boolean registrarUsuario() {
        try {
            con = conn.getConnection();
            Statement stm = (Statement) con.createStatement();
            stm.execute("INSERT INTO login VALUES(null,'" + getUsuario() + "','" + getPasswork() + "', "+getPc()+" , " + getIdEmpleado() + ")");
            stm.close();
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        } finally {
            conn.getClose();
        }

    }

    public boolean ModificarRegristros() {
        try {
            con = conn.getConnection();
            Statement stm = (Statement) con.createStatement();
            stm.execute("UPDATE login SET usuario='" + getUsuario() + "', contrasena='" + getPasswork() + "',id_empleado=" + getIdEmpleado() + " , PC = "+getPc()+" WHERE id_login=" + getIdLogin());
            stm.close();
            return true;
        } catch (SQLException ex) {
            System.out.println(ex);
            return false;
        } finally {
            conn.getClose();
        }
    }

    public boolean eliminarUsuario() {
        try {
            con = conn.getConnection();
            Statement stm = (Statement) con.createStatement();
            stm.execute("DELETE FROM login WHERE id_login=" + getIdLogin());
            stm.close();
            return true;
        } catch (SQLException ex) {
            System.out.println(ex);
            return false;
        } finally {
            conn.getClose();
        }
    }

    public ArrayList<Empleado> octenerEmpleados() {
        ArrayList<Empleado> listaEmpleados = new ArrayList<Empleado>();
        try {
            String sql = "SELECT nombre,id_empleado FROM empleado";
            con = conn.getConnection();
            Statement stm = (Statement) con.createStatement();
            ResultSet rs = stm.executeQuery(sql);

            while (rs.next()) {

                String nombre = rs.getString("nombre");
                int id = rs.getInt("id_empleado");

                Empleado empleado = new Empleado(id, nombre);
                listaEmpleados.add(empleado);
            }
            stm.close();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            conn.getClose();
        }
        return listaEmpleados;
    }

    public String idEmpleado() {
        String sql = null, idEmpleado = null;
        try {
            con = conn.getConnection();
            sql = "SELECT empleado.nombre AS nombre FROM login INNER JOIN empleado on empleado.id_empleado = login.id_empleado WHERE id_login = " + getIdLogin() + "";
            Statement stm = (Statement) con.createStatement();
            ResultSet rs = stm.executeQuery(sql);

            if (rs.next()) {
                idEmpleado = rs.getString("nombre");
            }
            stm.close();
            rs.close();

        } catch (SQLException ex) {
            Logger.getLogger(Ventas.class.getName()).log(Level.SEVERE, "ERROR " + ex);

        } finally {
            conn.getClose();
        }

        return idEmpleado;
    }

    public DefaultTableModel cargarRegistroEgreso(JTable jt) {

        jt.setRowHeight(25);

        DefaultTableModel modelo = (DefaultTableModel) jt.getModel();
        ArrayList<Usuarios> arrayEgresos = new ArrayList<>();
        try {

            String sql = "SELECT id_login , usuario, contrasena, empleado.nombre , PC  from empleado JOIN login ON empleado.id_empleado=login.id_empleado order by usuario";
            con = conn.getConnection();
            Statement stm = (Statement) con.createStatement();
            ResultSet resultado = stm.executeQuery(sql);
            while (resultado.next()) {
                int pc = resultado.getInt("PC");
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
                    default:
                        nomPc = "PC no Seleccionada.";
                        break;
                }
                arrayEgresos.add(new Usuarios(resultado.getInt("id_login"), resultado.getString("usuario"), resultado.getString("contrasena"), nomPc, resultado.getString("nombre")));
            }
            for (int i = 0; i < arrayEgresos.size(); i++) {
                modelo.addRow(new Object[]{arrayEgresos.get(i).getIdLogin(), arrayEgresos.get(i).getUsuario(), arrayEgresos.get(i).getPasswork(),
                    arrayEgresos.get(i).getPc(), arrayEgresos.get(i).getNombre()});
            }
            stm.close();
            con.close();
        } catch (SQLException ex) {
            //Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, ex);
        }

        return modelo;
    }

}
