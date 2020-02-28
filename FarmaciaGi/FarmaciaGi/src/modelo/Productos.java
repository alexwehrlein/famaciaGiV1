/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import ArchivoLog.ArchivoLog;
import java.awt.Color;
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
import vista.Pantalla_Productos;

/**
 *
 * @author saube
 */
public class Productos {

    private int num;
    private String codigo;
    private String marcaComercial;
    private String sustancias;
    private double precio;
    private String tipoMedicamento;
    private String laboratorio;
    private int proveedor;
    private String nombreProveedor;
    private int cantidad;
    private Connection con;
    private int id_empleado;
    private int piezasAdd;
    Conexion conn = new Conexion();
    Conexion conexion;
    Pantalla_Productos pantalla_Productos;
    ArchivoLog log;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public Productos(String codigo, int cantidad, int id_empleado, int piezasAdd) {
        this.codigo = codigo;
        this.cantidad = cantidad;
        this.id_empleado = id_empleado;
        this.piezasAdd = piezasAdd;
    }

    public int getId_empleado() {
        return id_empleado;
    }

    public void setId_empleado(int id_empleado) {
        this.id_empleado = id_empleado;
    }

    public int getPiezasAdd() {
        return piezasAdd;
    }

    public void setPiezasAdd(int piezasAdd) {
        this.piezasAdd = piezasAdd;
    }

    public String getNombreProveedor() {
        return nombreProveedor;
    }

    public void setNombreProveedor(String nombreProveedor) {
        this.nombreProveedor = nombreProveedor;
    }

    public Productos(String codigo, String marcaComercial, String sustancias, double precio, String tipoMedicamento, String laboratorio, String nombreProveedor, int cantidad) {
        this.codigo = codigo;
        this.marcaComercial = marcaComercial;
        this.sustancias = sustancias;
        this.precio = precio;
        this.tipoMedicamento = tipoMedicamento;
        this.laboratorio = laboratorio;
        this.nombreProveedor = nombreProveedor;
        this.cantidad = cantidad;
    }

    public Productos(String codigo, String marcaComercial, String sustancias, double precio, String tipoMedicamento, String laboratorio, int proveedor, int cantidad) {
        this.codigo = codigo;
        this.marcaComercial = marcaComercial;
        this.sustancias = sustancias;
        this.precio = precio;
        this.tipoMedicamento = tipoMedicamento;
        this.laboratorio = laboratorio;
        this.proveedor = proveedor;
        this.cantidad = cantidad;
    }

    public Productos(String codigo, String marcaComercial, String sustancias, double precio, String tipoMedicamento, String laboratorio, String nombreProveedor) {
        this.codigo = codigo;
        this.marcaComercial = marcaComercial;
        this.sustancias = sustancias;
        this.precio = precio;
        this.tipoMedicamento = tipoMedicamento;
        this.laboratorio = laboratorio;
        this.nombreProveedor = nombreProveedor;
    }

    public Productos(int num, String codigo, int cantidad) {
        this.num = num;
        this.codigo = codigo;
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return "Productos{" + "num=" + num + ", codigo=" + codigo + ", cantidad=" + cantidad + '}';
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getMarcaComercial() {
        return marcaComercial;
    }

    public void setMarcaComercial(String marcaComercial) {
        this.marcaComercial = marcaComercial;
    }

    public String getSustancias() {
        return sustancias;
    }

    public void setSustancias(String sustancias) {
        this.sustancias = sustancias;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getTipoMedicamento() {
        return tipoMedicamento;
    }

    public void setTipoMedicamento(String tipoMedicamento) {
        this.tipoMedicamento = tipoMedicamento;
    }

    public String getLaboratorio() {
        return laboratorio;
    }

    public void setLaboratorio(String laboratorio) {
        this.laboratorio = laboratorio;
    }

    public int getProveedor() {
        return proveedor;
    }

    public void setProveedor(int proveedor) {
        this.proveedor = proveedor;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Productos() {

    }

    public Productos(String codigo) {
        this.codigo = codigo;
    }

    public Productos(String codigo, int cantidad) {
        this.codigo = codigo;
        this.cantidad = cantidad;
    }

    public Productos(String codigo, double precio, String nombre, String tipoMedicamento) {
        this.codigo = codigo;
        this.precio = precio;
        this.marcaComercial = nombre;
        this.tipoMedicamento = tipoMedicamento;
    }

    public double PrrcioProducto() {
        double precio = 0;

        try {
            String sql = "SELECT precio FROM productos WHERE codigo = " + codigo;
            con = new Conexion().getConnection();
            Statement stm = (Statement) con.createStatement();
            ResultSet resultado = stm.executeQuery(sql);
            while (resultado.next()) {
                precio = resultado.getDouble("precio");
            }
            stm.close();
            resultado.close();
        } catch (SQLException ex) {
            log = new ArchivoLog();
            log.crearLog(ex);
            Logger.getLogger(Productos.class.getName()).log(Level.SEVERE, " Error ", ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                log = new ArchivoLog();
                log.crearLog(ex);
                Logger.getLogger(Productos.class.getName()).log(Level.SEVERE, "Error " + ex);
            }
        }
        return precio;
    }

    public void producto(String codigo) {

        try {
            String sql = "SELECT * FROM productos WHERE codigo = " + codigo;
            con = new Conexion().getConnection();
            Statement stm = (Statement) con.createStatement();
            ResultSet resultado = stm.executeQuery(sql);
            if (resultado.next()) {
                setCodigo(resultado.getString("codigo"));
                setMarcaComercial(resultado.getString("marca_comercial"));
                setPrecio(resultado.getDouble("precio"));
                setCantidad(resultado.getInt("cantidad"));
            }
            stm.close();
            resultado.close();
        } catch (SQLException ex) {
            log = new ArchivoLog();
            log.crearLog(ex);
            Logger.getLogger(Productos.class.getName()).log(Level.SEVERE, " Error ", ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                log = new ArchivoLog();
                log.crearLog(ex);
                Logger.getLogger(Productos.class.getName()).log(Level.SEVERE, "Error " + ex);
            }
        }
    }

    public int productoCero() {
        String sql = null;
        int cantidad = 0;
        try {
            con = new Conexion().getConnection();
            Statement stm = (Statement) con.createStatement();

            sql = "SELECT cantidad FROM productos WHERE codigo=" + getCodigo() + "";
            ResultSet rs = stm.executeQuery(sql);
            if (rs.next()) {
                cantidad = rs.getInt("cantidad");

            }
            stm.close();
            rs.close();

        } catch (SQLException ex) {
            log = new ArchivoLog();
            log.crearLog(ex);
            Logger.getLogger(Productos.class.getName()).log(Level.SEVERE, "Error " + ex);

        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                log = new ArchivoLog();
                log.crearLog(ex);
                Logger.getLogger(Productos.class.getName()).log(Level.SEVERE, "Error" + ex);
            }
        }

        return cantidad;

    }

    public boolean ModificarRegristros() {
        try {
            con = new Conexion().getConnection();
            Statement stm = (Statement) con.createStatement();
            stm.execute("UPDATE productos SET marca_comercial='" + getMarcaComercial() + "' , precio=" + getPrecio() + " , tipo_medicamento = '" + getTipoMedicamento() + "' WHERE codigo=" + getCodigo());
            con.setAutoCommit(true);
            return true;
        } catch (SQLException ex) {
            log = new ArchivoLog();
            log.crearLog(ex);
            Logger.getLogger(Productos.class.getName()).log(Level.SEVERE, "Error" + ex);
            return false;
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                log = new ArchivoLog();
                log.crearLog(ex);
                Logger.getLogger(Productos.class.getName()).log(Level.SEVERE, "Error" + ex);
            }
        }
    }

    public boolean verificarCodigo() {
        String sql = null, resultadoSql;
        boolean veCo = false;
        try {
            con = new Conexion().getConnection();
            Statement stm = (Statement) con.createStatement();

            sql = "SELECT marca_comercial FROM productos WHERE codigo=" + getCodigo();
            ResultSet resultado = stm.executeQuery(sql);
            if (resultado.next()) {
                resultadoSql = resultado.getString("marca_comercial");
                veCo = true;
            }
            stm.close();
            resultado.close();
        } catch (SQLException ex) {
            log = new ArchivoLog();
            log.crearLog(ex);
            Logger.getLogger(Productos.class.getName()).log(Level.SEVERE, "Error" + ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                log = new ArchivoLog();
                log.crearLog(ex);
                Logger.getLogger(Productos.class.getName()).log(Level.SEVERE, "Error" + ex);
            }
        }
        return veCo;

    }

    public ArrayList<Productos> Sustancia(String sustancia) {
        ArrayList<Productos> arrayRegistros = new ArrayList<>();

        try {
            String sql = "SELECT * FROM productos WHERE sustancia like  '%" + sustancia + "%'";
            con = new Conexion().getConnection();
            Statement stm = (Statement) con.createStatement();
            ResultSet resultado = stm.executeQuery(sql);
            while (resultado.next()) {
                Productos r = new Productos();
                r.setSustancias(resultado.getString("sustancia"));
                arrayRegistros.add(r);
            }
            stm.close();
            resultado.close();
        } catch (SQLException ex) {
            log = new ArchivoLog();
            log.crearLog(ex);
            Logger.getLogger(Productos.class.getName()).log(Level.SEVERE, "Error" + ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                log = new ArchivoLog();
                log.crearLog(ex);
                Logger.getLogger(Productos.class.getName()).log(Level.SEVERE, "Error" + ex);
            }
        }

        return arrayRegistros;
    }

    public ArrayList<Proveedor> octenerProveedores() {
        ArrayList<Proveedor> listaProveedor = new ArrayList<Proveedor>();
        try {
            String sql = "SELECT * FROM proveedor";
            con = new Conexion().getConnection();
            Statement stm = (Statement) con.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt("id_proveedor");
                String nombre = rs.getString("nombre");
                String telefono = rs.getString("telefono");
                String correo = rs.getString("correo");

                Proveedor proveedor = new Proveedor(id, nombre, telefono, correo);
                listaProveedor.add(proveedor);
            }
            stm.close();
            rs.close();
        } catch (SQLException ex) {
            log = new ArchivoLog();
            log.crearLog(ex);
            Logger.getLogger(Productos.class.getName()).log(Level.SEVERE, "Error" + ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                log = new ArchivoLog();
                log.crearLog(ex);
                Logger.getLogger(Productos.class.getName()).log(Level.SEVERE, "Error" + ex);
            }
        }
        return listaProveedor;
    }

    public boolean registrarProducto() {
        try {
            con = conn.getConnection();
            Statement stm = (Statement) con.createStatement();
            stm.execute("INSERT INTO productos VALUES(" + getCodigo() + ",'" + getMarcaComercial() + "','" + getSustancias() + "'," + getPrecio() + ",'"
                    + getTipoMedicamento() + "','" + getLaboratorio() + "'," + getProveedor() + "," + getCantidad() + ")");
            return true;
        } catch (SQLException ex) {
            log = new ArchivoLog();
            log.crearLog(ex);
            Logger.getLogger(Productos.class.getName()).log(Level.SEVERE, "Error" + ex);
            return false;
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                log = new ArchivoLog();
                log.crearLog(ex);
                Logger.getLogger(Productos.class.getName()).log(Level.SEVERE, "Error" + ex);
            }
        }

    }

    public boolean Modificarexistencias() {
        try {

            con = conn.getConnection();
            con.setAutoCommit(false);

            Statement stm = (Statement) con.createStatement();
            stm.execute("UPDATE productos SET cantidad='" + getCantidad() + "' WHERE codigo=" + getCodigo());

            Statement stm2 = (Statement) con.createStatement();
            stm2.execute("INSERT INTO compraproductos(codigo, id_empleado, piezas) VALUES (" + getCodigo() + " , " + getId_empleado() + " , " + getPiezasAdd() + ")");
            con.commit();
            stm.close();
            stm2.close();
            return true;
        } catch (SQLException ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                log = new ArchivoLog();
                log.crearLog(ex);
                Logger.getLogger(Productos.class.getName()).log(Level.SEVERE, "Error" + ex1);
            }
            log = new ArchivoLog();
            log.crearLog(ex);
            Logger.getLogger(Productos.class.getName()).log(Level.SEVERE, "Error" + ex);
            return false;
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                log = new ArchivoLog();
                log.crearLog(ex);
                Logger.getLogger(Productos.class.getName()).log(Level.SEVERE, "Error" + ex);
            }
        }
    }

    public boolean eliminarMedicamento() {
        try {
            con = conn.getConnection();
            Statement stm = (Statement) con.createStatement();
            stm.execute("DELETE FROM productos WHERE codigo=" + getCodigo());
            return true;
        } catch (SQLException ex) {
            log = new ArchivoLog();
            log.crearLog(ex);
            Logger.getLogger(Productos.class.getName()).log(Level.SEVERE, "Error" + ex);
            return false;

        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                log = new ArchivoLog();
                log.crearLog(ex);
                Logger.getLogger(Productos.class.getName()).log(Level.SEVERE, "Error" + ex);
            }
        }
    }

    public DefaultTableModel cargarRegistroEgreso(JTable jt) {
        jt.setDefaultRenderer(Object.class, new Render());
        JButton btnModificar = new JButton("Modificar");
        JButton btnEliminar = new JButton("Eliminar");
        JComboBox tipo;
        TableColumn col = jt.getColumnModel().getColumn(4);
        String op[] = {"PATENTE", "CONSULTA", "GENÉRICO", "ABARROTES", "PERFUMERIA", "PROMOCIÓN"};
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
        ArrayList<Productos> arrayProductos = new ArrayList<>();
        try {

            String sql = "SELECT codigo,marca_comercial,sustancia,precio,tipo_medicamento,laboratorio,"
                    + "proveedor.nombre,cantidad FROM productos JOIN proveedor "
                    + "on productos.proveedor=proveedor.id_proveedor order by marca_comercial";
            con = conn.getConnection();
            Statement stm = (Statement) con.createStatement();
            ResultSet resultado = stm.executeQuery(sql);
            while (resultado.next()) {
                arrayProductos.add(new Productos(resultado.getString("codigo"), resultado.getString("marca_comercial"), resultado.getString("sustancia"), resultado.getDouble("precio"),
                        resultado.getString("tipo_medicamento"), resultado.getString("laboratorio"), resultado.getString("nombre")));
            }
            for (int i = 0; i < arrayProductos.size(); i++) {
                modelo.addRow(new Object[]{arrayProductos.get(i).getCodigo(), arrayProductos.get(i).getMarcaComercial(),
                    arrayProductos.get(i).getSustancias(), arrayProductos.get(i).getPrecio(), arrayProductos.get(i).getTipoMedicamento(), arrayProductos.get(i).getLaboratorio(),
                    btnModificar});
            }
            stm.close();
            resultado.close();
        } catch (SQLException ex) {
            log = new ArchivoLog();
            log.crearLog(ex);
            Logger.getLogger(Productos.class.getName()).log(Level.SEVERE, "Error" + ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                log = new ArchivoLog();
                log.crearLog(ex);
                Logger.getLogger(Productos.class.getName()).log(Level.SEVERE, "Error" + ex);
            }
        }

        return modelo;
    }

    public DefaultTableModel BuscarRegistroEgreso(JTable jt, long codigo) {
        jt.setDefaultRenderer(Object.class, new Render());
        JButton btnModificar = new JButton("Modificar");
        JButton btnEliminar = new JButton("Eliminar");
        JComboBox tipo;
        TableColumn col = jt.getColumnModel().getColumn(4);
        String op[] = {"PATENTE", "CONSULTA", "GENÉRICO", "ABARROTES", "PERFUMERIA", "PROMOCIÓN"};
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
        ArrayList<Productos> arrayProductos = new ArrayList<>();
        try {

            String sql = "SELECT codigo,marca_comercial,sustancia,precio,tipo_medicamento,laboratorio,"
                    + "proveedor.nombre,cantidad FROM productos JOIN proveedor "
                    + "on productos.proveedor=proveedor.id_proveedor  WHERE codigo=" + codigo;
            con = conn.getConnection();
            Statement stm = (Statement) con.createStatement();
            ResultSet resultado = stm.executeQuery(sql);
            while (resultado.next()) {
                arrayProductos.add(new Productos(resultado.getString("codigo"), resultado.getString("marca_comercial"), resultado.getString("sustancia"), resultado.getDouble("precio"),
                        resultado.getString("tipo_medicamento"), resultado.getString("laboratorio"), resultado.getString("nombre")));
            }
            int num = arrayProductos.size();

            for (int i = 0; i < arrayProductos.size(); i++) {
                modelo.addRow(new Object[]{arrayProductos.get(i).getCodigo(), arrayProductos.get(i).getMarcaComercial(),
                    arrayProductos.get(i).getSustancias(), arrayProductos.get(i).getPrecio(), arrayProductos.get(i).getTipoMedicamento(), arrayProductos.get(i).getLaboratorio(),
                    btnModificar});
            }
            System.out.println(num);
            if (num < 1) {
                System.out.println("entro");
                pantalla_Productos = new Pantalla_Productos();
                pantalla_Productos.codigo.setBackground(Color.RED);
            } else {

            }
            stm.close();
            resultado.close();
        } catch (SQLException ex) {
            log = new ArchivoLog();
            log.crearLog(ex);
            Logger.getLogger(Ventas.class.getName()).log(Level.SEVERE, "Error" + ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                log = new ArchivoLog();
                log.crearLog(ex);
                Logger.getLogger(Ventas.class.getName()).log(Level.SEVERE, "Error" + ex);
            }
        }

        return modelo;
    }

    public DefaultTableModel Buscar2RegistroEgreso(JTable jt, String codigo) {
        jt.setDefaultRenderer(Object.class, new Render());
        JButton btnModificar = new JButton("Modificar");
        JButton btnEliminar = new JButton("Eliminar");
        JComboBox tipo;
        TableColumn col = jt.getColumnModel().getColumn(4);
        String op[] = {"PATENTE", "CONSULTA", "GENÉRICO", "ABARROTES", "PERFUMERIA", "PROMOCIÓN"};
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
        ArrayList<Productos> arrayProductos = new ArrayList<>();
        try {

            String sql = "SELECT codigo,marca_comercial,sustancia,precio,tipo_medicamento,laboratorio,"
                    + "proveedor.nombre,cantidad FROM productos JOIN proveedor "
                    + "on productos.proveedor=proveedor.id_proveedor  WHERE marca_comercial like '%" + codigo + "%'";
            con = conn.getConnection();
            Statement stm = (Statement) con.createStatement();
            ResultSet resultado = stm.executeQuery(sql);
            while (resultado.next()) {
                arrayProductos.add(new Productos(resultado.getString("codigo"), resultado.getString("marca_comercial"), resultado.getString("sustancia"), resultado.getDouble("precio"),
                        resultado.getString("tipo_medicamento"), resultado.getString("laboratorio"), resultado.getString("nombre")));
            }
            for (int i = 0; i < arrayProductos.size(); i++) {
                modelo.addRow(new Object[]{arrayProductos.get(i).getCodigo(), arrayProductos.get(i).getMarcaComercial(),
                    arrayProductos.get(i).getSustancias(), arrayProductos.get(i).getPrecio(), arrayProductos.get(i).getTipoMedicamento(), arrayProductos.get(i).getLaboratorio(),
                    btnModificar});
            }
            stm.close();
            resultado.close();
        } catch (SQLException ex) {
            log = new ArchivoLog();
            log.crearLog(ex);
            Logger.getLogger(Ventas.class.getName()).log(Level.SEVERE, "Error" + ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                log = new ArchivoLog();
                log.crearLog(ex);
                Logger.getLogger(Ventas.class.getName()).log(Level.SEVERE, "Error" + ex);
            }
        }

        return modelo;
    }

    public Boolean GuadarListaAltas(DefaultTableModel modelo, int id_empleado) {
        String sql = null;
        boolean next = true;
        int piezasFinales = 0;
        try {
            con = conn.getConnection();
            con.setAutoCommit(false);
            for (int i = 0; i < modelo.getRowCount(); i++) {
                Statement stm = (Statement) con.createStatement();
                sql = "INSERT INTO compraproductos (codigo,id_empleado,piezas) VALUES ( '" + modelo.getValueAt(i, 0).toString() + "' , " + id_empleado + " ," + modelo.getValueAt(i, 2).toString() + " )";
                stm.execute(sql);
                Productos p = new Productos();
                p.producto(modelo.getValueAt(i, 0).toString());
                piezasFinales = p.getCantidad() + Integer.parseInt(modelo.getValueAt(i, 2).toString());
                sql = "UPDATE productos SET cantidad = " + piezasFinales + " WHERE codigo = " + modelo.getValueAt(i, 0).toString();
                stm.execute(sql);
                stm.close();
            }
            con.commit();
        } catch (SQLException ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(Bajas.class.getName()).log(Level.SEVERE, "" + ex1);
            }
            log.crearLog(ex);
            Logger.getLogger(Bajas.class.getName()).log(Level.SEVERE, "Error " + ex);
            next = false;
        } finally {
            conn.getClose();
        }
        return next;
    }

}
