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
public class Empleado {
    private int idEmpleado;
    private String nombre;
    private String telefono;
    private String correo;
    private String direccion;
    private String puesto;
    private String turno;
    private Connection con;

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }
 

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public Empleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    
    

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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

     public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public Empleado(String nombre) {
        this.nombre = nombre;
    }

     @Override
    public String toString(){
        return this.nombre;
    }
   

    public Empleado(int idEmpleado, String nombre, String telefono, String correo, String direccion, String puesto, String turno) {
        this.idEmpleado = idEmpleado;
        this.nombre = nombre;
        this.telefono = telefono;
        this.correo = correo;
        this.direccion = direccion;
        this.puesto = puesto;
        this.turno = turno;
        
          con = new Conexion().getConnection();
        try {
            con.setAutoCommit(false);
        } catch (SQLException ex) {
            Logger.getLogger(Empleado.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }

    public Empleado(int idEmpleado, String nombre) {
        this.idEmpleado = idEmpleado;
        this.nombre = nombre;
    }
    
    
    
    public Empleado(){
         con = new Conexion().getConnection();
        try {
            con.setAutoCommit(false);
        } catch (SQLException ex) {
            Logger.getLogger(Empleado.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public boolean registrarEmpleado() {
        try {
          Connection connection = new Conexion().getConnection();
            Statement stm = (Statement) con.createStatement();
           stm.execute("INSERT INTO empleado VALUES(null,'" + getNombre() + "','" + getTelefono() + "','" + getCorreo() + "','"+getDireccion()+"' , '"+getPuesto()+"' , '"+getTurno()+"')");
            con.setAutoCommit(true);
            con.close();
            return true;
        } catch (Exception e) {
            
            return false;
        }

    }
    
    public boolean eliminarRegristro(){
        try {
            Connection connection =new Conexion().getConnection();
            Statement stm= (Statement) con.createStatement();
            stm.execute("DELETE FROM empleado WHERE id_empleado="+getIdEmpleado());
            con.setAutoCommit(true);
            con.close();
            return  true;
        } catch (SQLException ex) {
           
            return  false;
            
            
        }
    }
    
    public boolean ModificarRegristros(){
        try {
            Connection connection =new Conexion().getConnection();
            Statement stm= (Statement) con.createStatement();
           stm.execute("UPDATE empleado SET nombre='"+getNombre()+"', telefono='"+getTelefono()+"',correo='"+getCorreo()+"' , direccion='"+getDireccion()+"' , puesto='"+getPuesto()+"' , turno='"+getTurno()+"' WHERE id_empleado="+getIdEmpleado());
           con.setAutoCommit(true);
           con.close();
            return  true;
        } catch (SQLException ex) {
            
            return false;
        }
    }
    
    public DefaultTableModel cargarRegistroEgreso(JTable jt) {
        jt.setDefaultRenderer(Object.class, new Render());
        JButton btnModificar = new JButton("Modificar");
        JButton btnEliminar = new JButton("Eliminar");
        JComboBox tipo;
        TableColumn col = jt.getColumnModel().getColumn(6);
        String op[] = {"mañana", "tarde"};
        tipo = new JComboBox(op);
        col.setCellEditor(new DefaultCellEditor(tipo));
        btnModificar.setName("btnModificar");
        btnEliminar.setName("btnEliminar");
       ImageIcon im = new ImageIcon(getClass().getResource("/imagenes/mo.png"));
       btnModificar.setIcon(im);
       ImageIcon ie = new ImageIcon(getClass().getResource("/imagenes/eli.png"));
       btnEliminar.setIcon(ie);
        jt.setRowHeight(25);

        DefaultTableModel modelo = (DefaultTableModel) jt.getModel();
        ArrayList<Empleado> arrayEmpleados = new ArrayList<>();
        try {
            
            String sql = "SELECT * FROM empleado order by nombre";
          //  Connection con  =new Conexion().getConnection();
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            ResultSet resultado = pst.executeQuery();
            while (resultado.next()) {
                arrayEmpleados.add(new Empleado(resultado.getInt("id_empleado"), resultado.getString("nombre"), resultado.getString("telefono"), resultado.getString("correo"), resultado.getString("direccion"),resultado.getString("puesto"),resultado.getString("turno")));
            }
            for (int i = 0; i < arrayEmpleados.size(); i++) {
                modelo.addRow(new Object[]{arrayEmpleados.get(i).getIdEmpleado(), arrayEmpleados.get(i).getNombre(), arrayEmpleados.get(i).getTelefono(),
                    arrayEmpleados.get(i).getCorreo(),arrayEmpleados.get(i).getDireccion(),arrayEmpleados.get(i).getPuesto(),arrayEmpleados.get(i).getTurno(), btnModificar, btnEliminar});
            }
            con.close();
        } catch (SQLException ex) {
            //Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, ex);
        }

        return modelo;
    }
    
   
    public String[] obtenerContraUsuario(String usuario){
        String []arr = {"","","","","",""};
        try {
            String sql = "select contrasena,login.id_empleado,empleado.puesto,empleado.nombre,empleado.turno,pc from login join empleado on login.id_empleado=empleado.id_empleado where usuario= '" + usuario + "'";
            java.sql.Statement sta = con.createStatement();
            ResultSet resultado = sta.executeQuery(sql);
            if(resultado.next()){
                arr[0]=resultado.getString("contrasena");
                arr[1]=String.valueOf(resultado.getInt("id_empleado"));
                arr[2]=resultado.getString("puesto");
                arr[3]=resultado.getString("nombre");
                arr[4]=resultado.getString("turno");
                arr[5]=resultado.getString("PC");
            }
            con.close();
        } catch (SQLException ex) {
        }
        return arr;
    }
}




