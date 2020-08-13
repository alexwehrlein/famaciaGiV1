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
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author saube
 */
public class Devoluciones {

    private int folio;
    private long codigo;
    private String marcaComercial;
    private int piezas;
    private double total;
    private double monto;
    private String idCliente;
    private int id_venta;
    Conexion conexion;
    Connection con;
    int existencias;

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public int getFolio() {
        return folio;
    }

    public void setFolio(int folio) {
        this.folio = folio;
    }

    public long getCodigo() {
        return codigo;
    }

    public void setCodigo(long codigo) {
        this.codigo = codigo;
    }

    public String getMarcaComercial() {
        return marcaComercial;
    }

    public void setMarcaComercial(String marcaComercial) {
        this.marcaComercial = marcaComercial;
    }

    public int getPiezas() {
        return piezas;
    }

    public void setPiezas(int piezas) {
        this.piezas = piezas;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public Devoluciones(int folio, long codigo, String marcaComercial, int piezas, double total, double monto, String idCliente) {
        this.folio = folio;
        this.codigo = codigo;
        this.marcaComercial = marcaComercial;
        this.piezas = piezas;
        this.total = total;
        this.monto = monto;
        this.idCliente = idCliente;
    }

    public Devoluciones() {
    }

    public Devoluciones(long codigo, double monto) {
        this.codigo = codigo;
        this.monto = monto;
    }

    public Devoluciones(long codigo, int piezas) {
        this.codigo = codigo;
        this.piezas = piezas;
    }

    public Devoluciones(int id_venta, long id_producto, int piezas) {
        this.id_venta = id_venta;
        this.codigo = id_producto;
        this.piezas = piezas;
    }

    public boolean registrarDevolucion(String idEmp, String idClient, String turno) {
        try {
            Connection connection = new Conexion().getConnection();
            Statement stm = (Statement) connection.createStatement();
            stm.execute("INSERT INTO ventas (fecha,id_empleado,id_cliente,monto,turno,tipo_venta)"
                    + "VALUES (CURDATE()," + idEmp + "," + idClient + "," + getMonto() + ",'" + turno + "','DEVOLUCION')");
             connection.close();
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }

    }

    public boolean RegresarProducto() {
        int cantidadp = 0, cantidadT = 0;
        String sql = null;
        Connection connection = new Conexion().getConnection();

        try {
            Statement stm = (Statement) connection.createStatement();

            sql = "SELECT cantidad From productos WHERE codigo=" + getCodigo();
            ResultSet resultado = stm.executeQuery(sql);
            if (resultado.next()) {
                cantidadp = resultado.getInt("cantidad");
            }

            cantidadT = cantidadp + getPiezas();

            stm.execute("UPDATE productos SET cantidad=" + cantidadT + " WHERE codigo=" + getCodigo());
            connection.close();
            return true;
        } catch (SQLException ex) {
           System.out.println(ex);
            return false;
        }
    }

    public boolean detalleVenta(double precio) {
        int cantidadp = 0, cantidadT = 0;
        double  precioFinal=0;
        String sql = null;
        Connection connection = new Conexion().getConnection();

        try {
            Statement stm = (Statement) connection.createStatement();

            sql = "SELECT piezas From detalle_venta WHERE id_venta=" + getId_venta() + " AND id_producto = " + getCodigo();
            ResultSet resultado = stm.executeQuery(sql);
            if (resultado.next()) {
                cantidadp = resultado.getInt("piezas");
            }

            cantidadT = cantidadp - getPiezas();
            precioFinal=precio * cantidadT;
            

            stm.execute("UPDATE detalle_venta SET piezas=" + cantidadT + " , total = "+precioFinal+" WHERE id_venta=" + getId_venta() + " AND id_producto = " + getCodigo());
            connection.close();
            return true;
        } catch (SQLException ex) {
            System.out.println(ex);
            return false;
        }
    }

    public DefaultTableModel cargarRegistro(JTable jt, int folio) {

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
        ArrayList<Devoluciones> arrayEgresos = new ArrayList<>();
        try {

            String sql = "SELECT id_venta,productos.codigo,productos.marca_comercial,piezas,total,productos.precio,ventas.id_cliente from detalle_venta \n"
                    + "JOIN productos ON productos.codigo=detalle_venta.id_producto \n"
                    + "JOIN ventas ON ventas.id_ventas=detalle_venta.id_venta\n"
                    + "WHERE id_venta =" + folio;
            Connection con = new Conexion().getConnection();
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            ResultSet resultado = pst.executeQuery();
            while (resultado.next()) {
                arrayEgresos.add(new Devoluciones(resultado.getInt("id_venta"), resultado.getLong("codigo"), resultado.getString("marca_comercial"), resultado.getInt("piezas"), resultado.getDouble("total"), resultado.getDouble("precio"), resultado.getString("id_cliente")));
            }
            for (int i = 0; i < arrayEgresos.size(); i++) {
                modelo.addRow(new Object[]{arrayEgresos.get(i).getFolio(), arrayEgresos.get(i).getCodigo(), arrayEgresos.get(i).getMarcaComercial(),
                    arrayEgresos.get(i).getPiezas(), arrayEgresos.get(i).getMonto(), arrayEgresos.get(i).getTotal(), arrayEgresos.get(i).getIdCliente()});
            }
            con.close();
        } catch (SQLException ex) {
            //Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, ex);
        }

        return modelo;
    }

    public int getId_venta() {
        return id_venta;
    }

    public void setId_venta(int id_venta) {
        this.id_venta = id_venta;
    }

}
