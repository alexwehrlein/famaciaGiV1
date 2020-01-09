
package modelo;

import ArchivoLog.ArchivoLog;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.StyledEditorKit;


public class sucursal {

    Conexion conn = new Conexion();
    private String id;
    private String direccion;
    private String telefono;
    private Connection con;
    ArchivoLog log;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public sucursal() {
    }

    public sucursal(String id) {
        this.id = id;
    }

    public sucursal(String id, String direccion, String telefono) {
        this.id = id;
        this.direccion = direccion;
        this.telefono = telefono;
    }

    public String[] registrarSucursal() {
        String[] num = {"", "", ""};
        String sql = "";
        int row;
        ResultSet resultado;
        try {
            con = conn.getConnection();
            Statement stm = (Statement) con.createStatement();

            sql = "SELECT  * FROM sucursal LIMIT 1";
            resultado = stm.executeQuery(sql);
            resultado.next();
            if (resultado.getRow() > 0) {
                num[0] = resultado.getString("id_sucursal");
                num[1] = resultado.getString("direccion");
                num[2] = resultado.getString("telefono");
            } else {
                stm.execute("INSERT INTO sucursal (id_sucursal) VALUES ('FGI" + getId() + "') ");
                sql = "SELECT  * FROM sucursal ";
                resultado = stm.executeQuery(sql);
                resultado.next();
                num[0] = resultado.getString("id_sucursal");
                num[1] = resultado.getString("direccion");
                num[2] = resultado.getString("telefono");
            }
            stm.close();
            resultado.close();
        } catch (SQLException ex) {
            log = new ArchivoLog();
            log.crearLog(ex);
            Logger.getLogger(Ventas.class.getName()).log(Level.SEVERE, "ERROR " + ex);
        } finally {
            conn.getClose();
        }
        return num;

    }

    public String[] datosSucursal() {
        String[] num = {"", "", ""};
        String sql = "";
        ResultSet resultado;
        try {
            con = conn.getConnection();
            Statement stm = (Statement) con.createStatement();

            sql = "SELECT  * FROM sucursal LIMIT 1";
            resultado = stm.executeQuery(sql);
            if (resultado.next()) {;
                num[0] = resultado.getString("id_sucursal");
                num[1] = resultado.getString("direccion");
                num[2] = resultado.getString("telefono");
            }
            stm.close();
            resultado.close();
        } catch (SQLException ex) {
            log = new ArchivoLog();
            log.crearLog(ex);
            Logger.getLogger(Ventas.class.getName()).log(Level.SEVERE, "ERROR " + ex);
        } finally {
            conn.getClose();
        }
        return num;
    }

    public boolean updateSucursal() {

        String sql = null, folio = "0";
        try {
            con = conn.getConnection();
            Statement stm = (Statement) con.createStatement();
            stm.execute("UPDATE sucursal SET direccion='" + getDireccion() + "', telefono='" + getTelefono() + "' WHERE id_sucursal='" + getId() + "'");
            stm.close();
        } catch (SQLException ex) {
            log = new ArchivoLog();
            log.crearLog(ex);
            Logger.getLogger(Ventas.class.getName()).log(Level.SEVERE, "ERROR " + ex);
            return false;
        } finally {
            conn.getClose();
        }

        return true;

    }
}
