/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import ArchivoLog.ArchivoLog;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import vista.Pantalla_Ventas;

/**
 *
 * @author Jose Abada Nava
 */
public class Ventas {

    private Connection con;
    private int id;
    private int descuento;
    private int ventas;
    private long codigo;
    private int piezas;
    private String turno;
    private String marca;
    private String existencias;

    public String getExistencias() {
        return existencias;
    }

    public void setExistencias(String existencias) {
        this.existencias = existencias;
    }
    private float venta;
    private float total;
    private float monto;
    Pantalla_Ventas pv;
    Conexion conn = new Conexion();

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public float getVenta() {
        return venta;
    }

    public void setVenta(float venta) {
        this.venta = venta;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVentas() {
        return ventas;
    }

    public void setVentas(int ventas) {
        this.ventas = ventas;
    }

    public int getDescuento() {
        return descuento;
    }

    public void setDescuento(int descuento) {
        this.descuento = descuento;
    }

    public Ventas(long codigo, int piezas) {
        this.codigo = codigo;
        this.piezas = piezas;
    }

    public Ventas(int descuento, int piezas) {
        this.descuento = descuento;
        this.piezas = piezas;
    }

    public long getCodigo() {
        return codigo;
    }

    public void setCodigo(long codigo) {
        this.codigo = codigo;
    }

    public int getPiezas() {
        return piezas;
    }

    public void setPiezas(int piezas) {
        this.piezas = piezas;
    }

    public Ventas(int piezas) {
        this.piezas = piezas;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public Ventas(String turno) {
        this.turno = turno;
    }

    public float getMonto() {
        return monto;
    }

    public void setMonto(float monto) {
        this.monto = monto;
    }

    public Ventas(String turno, float monto) {
        this.turno = turno;
        this.monto = monto;
    }

    public Ventas(long codigo) {
        this.codigo = codigo;
    }

    public Ventas() {

    }

    public Ventas(long codigo, int piezas, String marca, float venta, float total) {
        this.codigo = codigo;
        this.piezas = piezas;
        this.marca = marca;
        this.venta = venta;
        this.total = total;
    }

    public ArrayList<Ventas> ventasList(String turno) {
        ArrayList<Ventas> arrayRegistros = new ArrayList<>();

        try {
            String sql = "SELECT p.codigo , p.marca_comercial, p.precio , d.piezas , d.total, p.cantidad FROM detalle_venta d INNER JOIN ventas v ON v.id_ventas = d.id_venta INNER JOIN productos p ON p.codigo = d.id_producto WHERE DATE(v.fecha) = CURDATE() AND v.turno = '" + turno + "'";
            con = new Conexion().getConnection();
            Statement stm = (Statement) con.createStatement();
            ResultSet resultado = stm.executeQuery(sql);
            while (resultado.next()) {
                Ventas r = new Ventas();
                r.setCodigo(resultado.getLong("codigo"));
                r.setMarca(resultado.getString("marca_comercial"));
                r.setVenta(resultado.getFloat("precio"));
                r.setPiezas(resultado.getInt("piezas"));
                r.setTotal(resultado.getFloat("total"));
                r.setExistencias(resultado.getString("cantidad"));

                arrayRegistros.add(r);
            }
            stm.close();
            resultado.close();
        } catch (SQLException ex) {
            Logger.getLogger(Productos.class.getName()).log(Level.SEVERE, "Error" + ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(Productos.class.getName()).log(Level.SEVERE, "Error" + ex);
            }
        }

        return arrayRegistros;
    }

   
    public ArrayList<Ventas> ventaPausada(String id) {
        ArrayList<Ventas> arrayRegistros = new ArrayList<>();

        try {
            String sql = "SELECT * FROM pausar_venta WHERE id=" + id;
            con = conn.getConnection();
            com.mysql.jdbc.PreparedStatement pst = (com.mysql.jdbc.PreparedStatement) con.prepareStatement(sql);
            ResultSet resultado = pst.executeQuery();
            while (resultado.next()) {
                Ventas r = new Ventas();
                r.setCodigo(resultado.getLong("codigo"));
                r.setPiezas(resultado.getInt("piezas"));
                arrayRegistros.add(r);
            }

            pst.close();
            resultado.close();

        } catch (SQLException ex) {
            Logger.getLogger(Ventas.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            conn.getClose();
        }

        return arrayRegistros;
    }

    public String precioProducto() {
        String sql = null, precio = "";
        try {
            con = new Conexion().getConnection();
            Statement stm = (Statement) con.createStatement();

            sql = "SELECT precio FROM productos WHERE codigo=" + getCodigo() + "";
            ResultSet rs = stm.executeQuery(sql);
            if (rs.next()) {
                precio = rs.getString("precio");

            }
            stm.close();
            rs.close();

        } catch (SQLException ex) {
            Logger.getLogger(Ventas.class.getName()).log(Level.SEVERE, null, ex);

        } finally {
            try {
                con.close();

            } catch (SQLException ex) {
                Logger.getLogger(Ventas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return precio;
    }

    public int productoCero(String codigo) {
        String sql = null;
        int cantidad = 0;
        try {
            con = new Conexion().getConnection();
            Statement stm = (Statement) con.createStatement();

            sql = "SELECT cantidad FROM productos WHERE codigo=" + codigo + "";
            ResultSet rs = stm.executeQuery(sql);
            if (rs.next()) {
                cantidad = rs.getInt("cantidad");

            }
            stm.close();
            rs.close();

        } catch (SQLException ex) {
            Logger.getLogger(Ventas.class.getName()).log(Level.SEVERE, null, ex);

        } finally {
            try {
                con.close();

            } catch (SQLException ex) {
                Logger.getLogger(Ventas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return cantidad;

    }

    public String OctenerCodigo(String sustancia) {
        String sql = null;
        String codigo = null;

        try {
            con = conn.getConnection();
            Statement stm = (Statement) con.createStatement();

            sql = "SELECT codigo FROM productos WHERE sustancia='" + sustancia + "'";
            ResultSet rs = stm.executeQuery(sql);
            if (rs.next()) {
                codigo = rs.getString("codigo");

            }
            stm.close();
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(Ventas.class.getName()).log(Level.SEVERE, null, ex);

        } finally {
            conn.getClose();
        }
        return codigo;

    }

    public void eliminarVentaPausada() {
        String sql = null;

        try {
            con = conn.getConnection();
            Statement stm = (Statement) con.createStatement();
            sql = "TRUNCATE pausar_venta";
            stm.execute(sql);

            stm.close();
        } catch (SQLException ex) {
            Logger.getLogger(Ventas.class.getName()).log(Level.SEVERE, null, ex);

        } finally {
            conn.getClose();
        }

    }

    public boolean insertAqueo() {
        String sql = null;

        try {
            con = conn.getConnection();
            Statement stm = (Statement) con.createStatement();
            sql = "INSERT INTO arqueos VALUES(" + getMonto() + ", '" + getTurno() + "')";
            stm.execute(sql);

            stm.close();
        } catch (SQLException ex) {
            Logger.getLogger(Ventas.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            conn.getClose();
        }
        return true;
    }

    public boolean existeRegistroProducto(String cod) {
        try {

            String sql = "SELECT * FROM productos WHERE codigo = " + cod;
            con = conn.getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet resultado = pst.executeQuery();

            return resultado.next();

        } catch (SQLException ex) {

        } finally {
            conn.getClose();
        }
        return false;
    }

    public void eliminarTurnos() {
        String sql = null;

        try {
            con = conn.getConnection();
            Statement stm = (Statement) con.createStatement();
            sql = "TRUNCATE turno";
            stm.execute(sql);

            stm.close();
        } catch (SQLException ex) {
            Logger.getLogger(Ventas.class.getName()).log(Level.SEVERE, null, ex);

        } finally {
            conn.getClose();
        }

    }

    public int turnoConsulta(String turno, String codigo) {
        int turnoConsulta = 0;
        String sql = null;
        try {

            con = conn.getConnection();
            Statement stm = (Statement) con.createStatement();
            sql = "INSERT INTO turno (codigo,turno) VALUES(" + codigo + ", '" + turno + "')";
            stm.execute(sql);

            sql = "SELECT COUNT(*) AS turno FROM turno t WHERE DATE(t.fecha) = CURDATE() AND t.codigo = " + codigo + " AND t.turno = '" + turno + "' ";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet resultado = pst.executeQuery();
            if (resultado.next()) {
                turnoConsulta = resultado.getInt("turno");
            }
            //System.out.println(turnoConsulta);
            stm.close();
            pst.close();
            resultado.close();
        } catch (Exception e) {
            Logger.getLogger(Ventas.class.getName()).log(Level.SEVERE, "" + e);
        } finally {
            conn.getClose();
        }
        return turnoConsulta;
    }

    public int numArqueos() {
        int arqueos = 0;
        try {

            String sql = "SELECT COUNT(id_arqueo) as arqueo FROM arqueos";
            con = conn.getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet resultado = pst.executeQuery();
            if (resultado.next()) {
                arqueos = resultado.getInt("arqueo");
            }

        } catch (SQLException ex) {

        } finally {
            conn.getClose();
        }
        return arqueos;
    }

    public String[] arqueo() {
        String[] arr = {"", "", "", ""};
        try {

            String sql = "select COUNT(id_ventas),\n"
                    + "       (SELECT IFNULL(SUM(monto),0) FROM ventas WHERE fecha = CURDATE() AND tipo_venta = 'Venta' AND turno = '" + getTurno() + "') as ventas ,\n"
                    + "       (SELECT IFNULL(SUM(monto),0) FROM ventas WHERE fecha = CURDATE() AND tipo_venta = 'DEVOLUCION' AND turno = '" + getTurno() + "') as devoluciones ,\n"
                    + "       (SELECT IFNULL(SUM(total),0) FROM gastos WHERE fecha = CURDATE() AND turno = '" + getTurno() + "') as gastos\n"
                    + "  from ventas";
            con = conn.getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet resultado = pst.executeQuery();
            if (resultado.next()) {
                arr[0] = resultado.getString("ventas");
                arr[1] = resultado.getString("devoluciones");
                arr[2] = resultado.getString("gastos");

            }
        } catch (SQLException ex) {

        } finally {
            conn.getClose();
        }
        return arr;
    }

    public DefaultTableModel obtenerDatosProducto(String cod, JTable jt, String piezas) {
        con = conn.getConnection();
        jt.setDefaultRenderer(Object.class, new Render());
        JButton btnEliminar = new JButton("Eliminar");
        ImageIcon ie = new ImageIcon(getClass().getResource("/imagenes/eli.png"));
        btnEliminar.setIcon(ie);
        jt.setRowHeight(25);
        DefaultTableModel modelo = (DefaultTableModel) jt.getModel();
        String[] arr = null;
        try {
            String sql = "SELECT * FROM productos WHERE codigo = " + cod;
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet resultado = pst.executeQuery();

            while (resultado.next()) {
                float precio = 0;
                float precioU = 0;
                if (resultado.getString("tipo_medicamento").equals("PROMOCIÓN")) {
                    Ventas producto = new Ventas();
                    producto.descuentoProducto(resultado.getString("codigo"));
                    System.out.println(getId());
                    if (producto.getId() == 0) {
                        JOptionPane.showMessageDialog(null, "<html><h1 align='center'>No hay una promociòn activa para ese producto </h1></html>");
                        continue;
                    }
                    int restantes = (producto.getPiezas() - producto.getVentas());
                    float descuento = (Float.parseFloat(resultado.getString("precio")) * producto.getDescuento()) / 100;
                    precio = Float.parseFloat(resultado.getString("precio")) - descuento;
                    precio = precio * Integer.parseInt(piezas);
                    precioU = Float.parseFloat(resultado.getString("precio")) - descuento;
                    if (producto.getVentas() >= producto.getPiezas()) {
                        JOptionPane.showMessageDialog(null, "<html><h1 align='center'>El producto esta agotado para esta promoción </h1></html>");
                        continue;
                    }
                    if (Integer.parseInt(piezas) > restantes) {
                        JOptionPane.showMessageDialog(null, "<html><h1 align='center'>El producto de esta promoción solo es de: " + restantes + " piezas </h1></html>");
                        continue;
                    }
                } else {
                    precio = Integer.parseInt(piezas) * Float.parseFloat(resultado.getString("precio"));
                    precioU = Float.parseFloat(resultado.getString("precio"));
                }

                arr = new String[]{resultado.getString("codigo"), resultado.getString("marca_comercial"), resultado.getString("sustancia"), resultado.getString("tipo_medicamento"), piezas, String.format(Locale.US, "%.2f", precioU), String.format(Locale.US, "%.2f", precio)};
            }
            pst.close();
            resultado.close();
            modelo.addRow(new Object[]{arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], btnEliminar});

        } catch (SQLException ex) {
            Logger.getLogger(Ventas.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(Ventas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return modelo;
    }

    public boolean pausarVenta(DefaultTableModel modelo, int id) {

        try {
            con = conn.getConnection();
            Statement stm = (Statement) con.createStatement();
            for (int i = 0; i < modelo.getRowCount(); i++) {
                stm.execute("INSERT INTO pausar_venta VALUES(" + id + ", " + modelo.getValueAt(i, 0).toString() + "," + modelo.getValueAt(i, 4).toString() + ")");
            }

            stm.close();
        } catch (Exception e) {
            System.out.println(e);
            return false;
        } finally {
            conn.getClose();
        }
        return true;
    }

    public String[] registrarVenta(String idEmp, String idClient, String cantidad, String total, DefaultTableModel modelo, String turno, String tipoVenta, int des_p, int des_g, String pagaCon, String cambio) {
        String idv = null, sql = null;
        String[] arr = {"", ""};
        pv = new Pantalla_Ventas();
        double totalV = Double.valueOf(total);

        try {
            con = conn.getConnection();
            Statement stm = (Statement) con.createStatement();

            for (int i = 0; i < modelo.getRowCount(); i++) {
//                el de abajo es codigo de eder
//                if (modelo.getValueAt(i, 3).toString().equals("ABARROTES") || modelo.getValueAt(i, 3).toString().equals("PERFUMERIA") || modelo.getValueAt(i, 3).toString().equals("CONSULTA")) {
                                   if ( modelo.getValueAt(i, 3).toString().equals("CONSULTA")) {

                                totalV -= Double.valueOf(modelo.getValueAt(i, 6).toString());
                }
            }

            sql = "INSERT INTO ventas (id_empleado,id_cliente,monto,turno,tipo_venta,des_p,des_g,pago,cambio)"
                    + "VALUES (" + idEmp + "," + idClient + "," + totalV + ",'" + turno + "','Venta', " + des_p + " , " + des_g + " , " + pagaCon + " , " + cambio + ")";

            stm.execute(sql);

            sql = "SELECT last_insert_id() AS last_id FROM ventas";
            ResultSet resultado = stm.executeQuery(sql);
            if (resultado.next()) {
                idv = resultado.getString("last_id");
                arr[0] = idv;
            }
            pv.folioTikect.setText(idv);
            for (int i = 0; i < modelo.getRowCount(); i++) {
                int piezas = Integer.parseInt(modelo.getValueAt(i, 4).toString());
                double totalProducto = Double.parseDouble(modelo.getValueAt(i, 6).toString());
                String tipo = modelo.getValueAt(i, 3).toString();
                sql = "INSERT INTO detalle_venta VALUES (null," + idv + "," + modelo.getValueAt(i, 0).toString() + "," + piezas + "," + totalProducto + ", '" + modelo.getValueAt(i, 1).toString() + "', CURDATE() , '" + turno + "', '" + tipo + "' )";
                stm.execute(sql);

                if (!tipo.equals("CONSULTA")) {
                    long codigoProducto = Long.parseLong(modelo.getValueAt(i, 0).toString());
                    piezasDescontar(codigoProducto, piezas);
                    faltantes(modelo.getValueAt(i, 0).toString());
                }

                if (tipo.equals("PROMOCIÓN")) {
                    String codigoProducto = modelo.getValueAt(i, 0).toString();
                    Ventas obj = new Ventas();
                    obj.descuentoProducto(codigoProducto);
                    int ventas = obj.getVentas() + Integer.parseInt(modelo.getValueAt(i, 4).toString());
                    sql = "UPDATE promociones SET vendidos = " + ventas + " where status = 'SI' AND codigoPrododucto =" + codigoProducto;
                    stm.execute(sql);
                }

            }
            arr[1] = "0";
            stm.close();
            resultado.close();

        } catch (SQLException ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(Ventas.class.getName()).log(Level.SEVERE, "" + ex1);
            }
            Logger.getLogger(Ventas.class.getName()).log(Level.SEVERE, "" + ex);
            arr[1] = "1";
            return arr;
        } finally {
            conn.getClose();
        }
        return arr;
    }

    public void descuentoProducto(String codigo) {
        try {
            con = conn.getConnection();
            Statement stm = (Statement) con.createStatement();
            String sql = "SELECT * FROM promociones WHERE status = 'SI' AND codigoPrododucto =" + codigo;
            ResultSet resultado = stm.executeQuery(sql);
            if (resultado.next()) {
                setId(resultado.getInt("id"));
                setDescuento(resultado.getInt("descuento"));
                setPiezas(resultado.getInt("cantidad"));
                setVentas(resultado.getInt("vendidos"));
            }
            stm.close();
            resultado.close();
        } catch (SQLException ex) {
            Logger.getLogger(Ventas.class.getName()).log(Level.SEVERE, null, ex);

        } finally {
            conn.getClose();
        }
    }

    public void faltantes(String codigoProducto) {
        int cantidadExistencias = 0;
        String nombre = null;
        try {
            con = conn.getConnection();
            Statement stm = (Statement) con.createStatement();
            String sql = "SELECT cantidad,marca_comercial FROM productos WHERE codigo=" + codigoProducto;
            ResultSet resultado = stm.executeQuery(sql);
            if (resultado.next()) {
                cantidadExistencias = resultado.getInt("cantidad");
                nombre = resultado.getString("marca_comercial");
            }
            if (cantidadExistencias <= 4) {
                JOptionPane.showMessageDialog(null, "<html><h1 align='center'> Quedan: " + cantidadExistencias + " De: " + nombre + "</h1></html>");
            }

            stm.close();
            resultado.close();
        } catch (SQLException ex) {
            Logger.getLogger(Ventas.class.getName()).log(Level.SEVERE, null, ex);

        } finally {
            conn.getClose();
            System.out.println("cerro2");
        }

    }

    public void piezasDescontar(long codigoProducto, int piezas) {
        int cantidadExistencias = 0, cantidadActual;
        try {
            con = conn.getConnection();
            Statement stm = (Statement) con.createStatement();
            String sql = "SELECT cantidad FROM productos WHERE codigo=" + codigoProducto;
            ResultSet resultado = stm.executeQuery(sql);
            if (resultado.next()) {
                cantidadExistencias = resultado.getInt("cantidad");
            }
            cantidadActual = cantidadExistencias - piezas;
            sql = "UPDATE productos SET cantidad='" + cantidadActual + "' WHERE codigo=" + codigoProducto + "";
            stm.execute(sql);

            stm.close();
            resultado.close();
        } catch (SQLException ex) {
            Logger.getLogger(Ventas.class.getName()).log(Level.SEVERE, null, ex);

        } finally {
            conn.getClose();
            System.out.println("cerro");
        }

    }

    public String folio() {
        String sql = null, folio = "0";
        try {
            con = conn.getConnection();
            Statement stm = (Statement) con.createStatement();

            sql = "SELECT MAX(id_ventas) AS folio FROM ventas";
            ResultSet resultado = stm.executeQuery(sql);
            if (resultado.next()) {
                folio = resultado.getString("folio");

                if (folio != null) {

                } else {
                    folio = "0";
                }

            }
            stm.close();
            resultado.close();
        } catch (SQLException ex) {
            Logger.getLogger(Ventas.class.getName()).log(Level.SEVERE, null, ex);

        } finally {
            conn.getClose();
        }
        return folio;

    }

    public String[] infoTikect(String folio) {
        String[] arr = {"", "", "", "", "", "", ""};
        try {
            con = conn.getConnection();
            Statement stm = (Statement) con.createStatement();

            String sql = "SELECT * , cliente.nombre as cliente , empleado.nombre as empleado FROM ventas\n"
                    + "INNER JOIN empleado ON empleado.id_empleado = ventas.id_empleado\n"
                    + "INNER JOIN cliente on cliente.id_cliente = ventas.id_cliente\n"
                    + "WHERE id_ventas =" + folio;
            ResultSet resultado = stm.executeQuery(sql);
            if (resultado.next()) {
                arr[0] = String.valueOf(resultado.getInt("id_ventas"));
                arr[1] = resultado.getString("fecha");
                arr[2] = resultado.getString("cliente");
                arr[3] = resultado.getString("empleado");
                arr[4] = String.valueOf(resultado.getDouble("monto"));
                arr[5] = String.valueOf(resultado.getDouble("pago"));
                arr[6] = String.valueOf(resultado.getDouble("cambio"));
            }
            stm.close();
            resultado.close();
        } catch (SQLException ex) {
            Logger.getLogger(Ventas.class.getName()).log(Level.SEVERE, null, ex);

        } finally {
            conn.getClose();
        }
        return arr;
    }

    public List<List<String>> infoTikectProductos(String folio) {
        List<List<String>> productos = new ArrayList<List<String>>();
        productos.add(new ArrayList<String>());
        productos.add(new ArrayList<String>());
        productos.add(new ArrayList<String>());
        productos.add(new ArrayList<String>());

        try {
            con = conn.getConnection();
            Statement stm = (Statement) con.createStatement();

            String sql = "SELECT * FROM detalle_venta\n"
                    + "INNER JOIN productos ON productos.codigo = detalle_venta.id_producto\n"
                    + "WHERE id_venta =" + folio;
            ResultSet resultado = stm.executeQuery(sql);
            while (resultado.next()) {
                productos.get(0).add(String.valueOf(resultado.getInt("piezas")));
                productos.get(1).add(resultado.getString("marca_comercial"));
                productos.get(2).add(String.valueOf(resultado.getInt("precio")));
                productos.get(3).add(String.valueOf(resultado.getInt("total")));
            }
            stm.close();
            resultado.close();
        } catch (SQLException ex) {
            Logger.getLogger(Ventas.class.getName()).log(Level.SEVERE, null, ex);

        } finally {
            conn.getClose();
        }
        return productos;

    }

}
