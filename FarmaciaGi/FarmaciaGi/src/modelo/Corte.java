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
import java.util.List;
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
public class Corte {

    Connection con;
    private String turno;
    private String fecha;
    private String fecha2;
    private double total;
    private int id;
    boolean yes;
    String NombreMedicamento;
    int cantidadMedicamento;
    float SumPrecioMedicamento;
    Conexion conn = new Conexion();

    public String getNombreMedicamento() {
        return NombreMedicamento;
    }

    public void setNombreMedicamento(String NombreMedicamento) {
        this.NombreMedicamento = NombreMedicamento;
    }

    public int getCantidadMedicamento() {
        return cantidadMedicamento;
    }

    public void setCantidadMedicamento(int cantidadMedicamento) {
        this.cantidadMedicamento = cantidadMedicamento;
    }

    public float getSumPrecioMedicamento() {
        return SumPrecioMedicamento;
    }

    public void setSumPrecioMedicamento(float SumPrecioMedicamento) {
        this.SumPrecioMedicamento = SumPrecioMedicamento;
    }

    public Corte(String NombreMedicamento, int cantidadMedicamento, float SumPrecioMedicamento) {
        this.NombreMedicamento = NombreMedicamento;
        this.cantidadMedicamento = cantidadMedicamento;
        this.SumPrecioMedicamento = SumPrecioMedicamento;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Corte(String turno) {
        this.turno = turno;
    }

    public String getTurno() {
        return turno;
    }

    public Corte() {
    }

    public Corte(int id) {
        this.id = id;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public Corte(String turno, double total) {
        this.turno = turno;
        this.total = total;
    }

    public Corte(int id, double total, String fecha, String turno) {
        this.turno = turno;
        this.fecha = fecha;
        this.total = total;
        this.id = id;
    }

    public Corte(String turno, String fecha) {
        this.turno = turno;
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "Corte{" + "NombreMedicamento=" + NombreMedicamento + ", cantidadMedicamento=" + cantidadMedicamento + ", SumPrecioMedicamento=" + SumPrecioMedicamento + '}';
    }
    

    public String devolucionesTotal(int num) {
        String sql = null, devolucionesTotal = "0";
        try {
            con = new Conexion().getConnection();
            Statement stm = (Statement) con.createStatement();

            if (num == 0) {
                sql = "SELECT SUM(monto) FROM ventas WHERE fecha = CURDATE() AND tipo_venta = 'DEVOLUCION' AND turno='" + getTurno() + "'";
            } else {
                sql = "SELECT SUM(monto) FROM ventas WHERE fecha = '" + getFecha() + "' AND tipo_venta = 'DEVOLUCION' AND turno='" + getTurno() + "'";
            }
            ResultSet resultado = stm.executeQuery(sql);
            if (resultado.next()) {
                devolucionesTotal = resultado.getString("SUM(monto)");

                if (devolucionesTotal != null) {

                } else {
                    devolucionesTotal = "0";
                }

            }
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(Ventas.class.getName()).log(Level.SEVERE, null, ex);

        }
        return devolucionesTotal;

    }

    public String ventaTotal(int num) {
        String sql = null, ventaTotal = "0";
        double total = 0;
        try {
            con = new Conexion().getConnection();
            Statement stm = (Statement) con.createStatement();
            Statement stm2 = (Statement) con.createStatement();

            if (num == 0) {
                sql = "SELECT SUM(monto) FROM ventas WHERE fecha = CURDATE()   AND tipo_venta = 'Venta' AND turno='" + getTurno() + "'";
            } else {
                sql = "SELECT SUM(monto) FROM ventas WHERE fecha = '" + getFecha() + "'   AND tipo_venta = 'Venta' AND turno='" + getTurno() + "'";
            }

            ResultSet resultado = stm.executeQuery(sql);
            if (resultado.next()) {
                ventaTotal = resultado.getString("SUM(monto)");

                if (ventaTotal != null) {

                } else {
                    ventaTotal = "0";
                }

            }

            con.close();

        } catch (SQLException ex) {
            Logger.getLogger(Ventas.class.getName()).log(Level.SEVERE, null, ex);

        }
        return ventaTotal;

    }

    public String consultaTotal(int num) {
        String sql = null, ventaTotal = "0";
        try {
            con = new Conexion().getConnection();
            Statement stm = (Statement) con.createStatement();

            if (num == 0) {
                sql = "SELECT SUM(total) FROM detalle_venta WHERE fecha = CURDATE()  AND tipo_venta = 'CONSULTA' AND turno='" + getTurno() + "'";
            } else {
                sql = "SELECT SUM(total) FROM detalle_venta WHERE fecha = '" + getFecha() + "'  AND tipo_venta = 'CONSULTA' AND turno='" + getTurno() + "'";
            }
            ResultSet resultado = stm.executeQuery(sql);
            if (resultado.next()) {
                ventaTotal = resultado.getString("SUM(total)");

                if (ventaTotal != null) {

                } else {
                    ventaTotal = "0";
                }

            }
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(Ventas.class.getName()).log(Level.SEVERE, null, ex);

        }
        return ventaTotal;

    }

    public String gastosTotal(int num) {
        String sql = null, gastosTotal = "0";
        try {
            con = new Conexion().getConnection();
            Statement stm = (Statement) con.createStatement();

            if (num == 0) {
                sql = "SELECT SUM(total) FROM gastos WHERE fecha = CURDATE()  AND turno='" + getTurno() + "'";
            } else {
                sql = "SELECT SUM(total) FROM gastos WHERE fecha = '" + getFecha() + "'  AND turno='" + getTurno() + "'";
            }
            ResultSet resultado = stm.executeQuery(sql);
            if (resultado.next()) {
                gastosTotal = resultado.getString("SUM(total)");

                if (gastosTotal != null) {

                } else {
                    gastosTotal = "0";
                }

            }
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(Ventas.class.getName()).log(Level.SEVERE, null, ex);

        }
        return gastosTotal;

    }

    public String abarrotesTotal(int num) {
        String sql = null, ventaTotal = "0";
        try {
            con = new Conexion().getConnection();
            Statement stm = (Statement) con.createStatement();

            if (num == 0) {
                sql = "SELECT SUM(total) FROM detalle_venta WHERE fecha = CURDATE()  AND tipo_venta = 'ABARROTES' AND turno='" + getTurno() + "'";
            } else {
                sql = "SELECT SUM(total) FROM detalle_venta WHERE fecha = '" + getFecha() + "'  AND tipo_venta = 'ABARROTES' AND turno='" + getTurno() + "'";
            }
            ResultSet resultado = stm.executeQuery(sql);
            if (resultado.next()) {
                ventaTotal = resultado.getString("SUM(total)");

                if (ventaTotal != null) {

                } else {
                    ventaTotal = "0";
                }

            }
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(Ventas.class.getName()).log(Level.SEVERE, null, ex);

        }
        return ventaTotal;

    }

    public String perfumeriaTotal(int num) {
        String sql = null, ventaTotal = "0";
        try {
            con = new Conexion().getConnection();
            Statement stm = (Statement) con.createStatement();

            if (num == 0) {
                sql = "SELECT SUM(total) FROM detalle_venta WHERE fecha = CURDATE()  AND tipo_venta = 'PERFUMERIA' AND turno='" + getTurno() + "'";
            } else {
                sql = "SELECT SUM(total) FROM detalle_venta WHERE fecha = '" + getFecha() + "'  AND tipo_venta = 'PERFUMERIA' AND turno='" + getTurno() + "'";
            }
            ResultSet resultado = stm.executeQuery(sql);
            if (resultado.next()) {
                ventaTotal = resultado.getString("SUM(total)");

                if (ventaTotal != null) {

                } else {
                    ventaTotal = "0";
                }

            }
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(Ventas.class.getName()).log(Level.SEVERE, null, ex);

        }
        return ventaTotal;

    }

    public String folio() {
        String sql = null, folio = "0";
        try {
            con = new Conexion().getConnection();
            Statement stm = (Statement) con.createStatement();

            sql = "SELECT MAX(id_corte) AS folio FROM cortes";
            ResultSet resultado = stm.executeQuery(sql);
            if (resultado.next()) {
                folio = resultado.getString("folio");

                if (folio != null) {

                } else {
                    folio = "0";
                }

            }
            con.close();
            return folio;
        } catch (SQLException ex) {
            Logger.getLogger(Ventas.class.getName()).log(Level.SEVERE, null, ex);

        }
        return folio;

    }

    public boolean Corte() {
        String sql = null, id_corte = null;

        try {
            con = new Conexion().getConnection();
            Statement stm = (Statement) con.createStatement();

            sql = "SELECT id_corte From cortes WHERE fecha = CURDATE() AND turno = '" + getTurno() + "'";
            ResultSet resultado = stm.executeQuery(sql);
            if (resultado.next()) {
                id_corte = resultado.getString("id_corte");

                if (id_corte != null) {
                    yes = false;

                }

            } else {
                yes = true;
            }
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(Ventas.class.getName()).log(Level.SEVERE, null, ex);

        }
        return yes;

    }

    public boolean registrarCortes(List<String> list) {
        Connection connection = new Conexion().getConnection();
        String idv = null;
        try {
            connection.setAutoCommit(false);
            com.mysql.jdbc.Statement stm = (com.mysql.jdbc.Statement) connection.createStatement();
            stm.execute("INSERT INTO cortes VALUES(null,CURDATE()," + getTotal() + " , '" + getTurno() + "')");
            String sql = "SELECT last_insert_id() AS last_id FROM cortes";
            ResultSet resultado = stm.executeQuery(sql);
            if (resultado.next()) {
                idv = resultado.getString("last_id");
            }
            com.mysql.jdbc.Statement stm2 = (com.mysql.jdbc.Statement) connection.createStatement();
            stm2.execute("INSERT corte_datos_extras  VALUES(null , " + idv + " , " + list.get(0) + "," + list.get(1) + "," + list.get(2) + "," + list.get(3) + "," + list.get(4) + "," + list.get(5) + "," + list.get(6) + "," + list.get(7) + "," + list.get(8) + "," + list.get(9) + "," + list.get(10) + "," + list.get(11) + "," + list.get(12) + "," + list.get(13) + "," + list.get(14) + "," + list.get(15) + ")");
            connection.commit();
            return true;
        } catch (Exception e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                Logger.getLogger(Corte.class.getName()).log(Level.SEVERE, "" + ex);
            }
            Logger.getLogger(Ventas.class.getName()).log(Level.SEVERE, "" + e);
            return false;
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(Corte.class.getName()).log(Level.SEVERE, "" + ex);
            }
        }

    }

    public ArrayList<String> descuentos(int num) {
        ArrayList<String> nombresClientes = new ArrayList<String>();
        String sql = "";
        try {
            con = conn.getConnection();
            java.sql.Statement stm = (java.sql.Statement) con.createStatement();

            if (num == 0) {
                sql = "SELECT  concat_ws(' DESCUENTO %  ', cliente.nombre, des_p, des_g) as clientes From ventas INNER JOIN cliente on ventas.id_cliente=cliente.id_cliente WHERE ventas.id_cliente != 1 AND fecha = CURDATE()  AND turno = '" + getTurno() + "' ";
            } else {
                sql = "SELECT  concat_ws(' DESCUENTO %  ', cliente.nombre, des_p, des_g) as clientes From ventas INNER JOIN cliente on ventas.id_cliente=cliente.id_cliente WHERE ventas.id_cliente != 1 AND fecha = '" + getFecha() + "'  AND turno = '" + getTurno() + "' ";
            }
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                nombresClientes.add(rs.getString("clientes"));
            }
            stm.close();
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(Ventas.class.getName()).log(Level.SEVERE, null, ex);

        } finally {
            conn.getClose();
        }
        return nombresClientes;
    }

    public ArrayList<Corte> consultorioSelect(int tipo) {
        ArrayList<Corte> consultas = new ArrayList<>();

        con = conn.getConnection();
        try {
            Statement stm = (Statement) con.createStatement();
            String sql = "SELECT * FROM productos WHERE tipo_medicamento = 'CONSULTA'";
           PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
           ResultSet resultado = pst.executeQuery();
            while (resultado.next()) {
                String query = "SELECT productos.marca_comercial AS nombre , IFNULL(total,0) AS sum, COUNT(id_detalle) AS cantidad FROM detalle_venta INNER JOIN productos ON productos.codigo = detalle_venta.id_producto WHERE turno = 'Tarde' AND id_producto = " + resultado.getString("codigo");
                if (tipo == 0) {
                    query += " AND fecha = CURDATE() ";
                } else {
                    query += " AND fecha =  " + getFecha();
                }
                PreparedStatement pst2 = (PreparedStatement) con.prepareStatement(query);
                ResultSet resultado2 = pst2.executeQuery();
                if (resultado2.next()) {
                    consultas.add(new Corte(resultado2.getString("nombre"),resultado2.getInt("cantidad"),resultado2.getFloat("sum")));
                }else{
                    Logger.getLogger(Corte.class.getName()).log(Level.WARNING,"No hay datos");
                }
                resultado2.close();
            }
            stm.close();
            resultado.close();
        } catch (SQLException ex) {
            Logger.getLogger(Corte.class.getName()).log(Level.SEVERE, null, ex);
        }

        return consultas;
    }

    public String[] consultaD() {
        String[] arr = {"0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"};
        String box;
        try {
            String sql = "SELECT SUM(total) AS consulta, COUNT(id_detalle) AS consulata2 FROM detalle_venta WHERE descripcion = 'CONSULTA'AND fecha = CURDATE() AND turno = '" + getTurno() + "' ";
            String sql2 = "SELECT SUM(total) AS aplicacion, COUNT(id_detalle) AS aplicacion2 FROM detalle_venta WHERE descripcion = 'APLICACION' AND fecha = CURDATE() AND turno = '" + getTurno() + "' ";
            String sql3 = "SELECT SUM(total) AS suero, COUNT(id_detalle) AS suero2 FROM detalle_venta WHERE descripcion = 'SUERO VITAMINADO' AND fecha = CURDATE() AND turno = '" + getTurno() + "'";
            String sql4 = "SELECT SUM(total) AS glucosa, COUNT(id_detalle) AS glucosa2 FROM detalle_venta WHERE descripcion = 'GLUSOCA' AND fecha = CURDATE() AND turno = '" + getTurno() + "'";
            String sql5 = "SELECT SUM(total) AS certificado, COUNT(id_detalle) AS ccertificado FROM detalle_venta WHERE descripcion = 'CERTIFICADO MEDICO' AND fecha = CURDATE() AND turno = '" + getTurno() + "'";
            String sql6 = "SELECT SUM(total) AS presion, COUNT(id_detalle) AS presion2 FROM detalle_venta WHERE descripcion = 'TOMA DE PRESION' AND fecha = CURDATE() AND turno = '" + getTurno() + "'";
            String sql7 = "SELECT SUM(total) AS oido, COUNT(id_detalle) AS oido2 FROM detalle_venta WHERE descripcion = 'LAVADO DE OIDO' AND fecha = CURDATE() AND turno = '" + getTurno() + "'";
            String sql8 = "SELECT SUM(total) AS hc, COUNT(id_detalle) AS hc2 FROM detalle_venta WHERE descripcion = 'HISTORIAL CLINICO' AND fecha = CURDATE() AND turno = '" + getTurno() + "'";
            String sql9 = "SELECT SUM(total) AS tg, COUNT(id_detalle) AS tg2 FROM detalle_venta WHERE descripcion = 'TOMA DE GLUCOSA' AND fecha = CURDATE() AND turno = '" + getTurno() + "'";
            con = conn.getConnection();
            java.sql.Statement stm = (java.sql.Statement) con.createStatement();
            ResultSet resultado = stm.executeQuery(sql);
            if (resultado.next()) {
                box = resultado.getString("consulta");
                arr[0] = resultado.getString("consulta2");
                if (box != null) {
                    arr[1] = box;
                } else {

                }

            }
            resultado = stm.executeQuery(sql2);
            if (resultado.next()) {
                box = resultado.getString("aplicacion");
                arr[2] = resultado.getString("aplicacion2");
                if (box != null) {
                    arr[3] = box;
                } else {

                }
            }
            resultado = stm.executeQuery(sql3);
            if (resultado.next()) {
                box = resultado.getString("suero");
                arr[4] = resultado.getString("suero2");
                if (box != null) {
                    arr[5] = box;
                } else {

                }
            }
            resultado = stm.executeQuery(sql4);
            if (resultado.next()) {
                box = resultado.getString("glucosa");
                arr[6] = resultado.getString("glucosa2");
                if (box != null) {
                    arr[7] = box;
                } else {

                }
            }
            resultado = stm.executeQuery(sql5);
            if (resultado.next()) {
                box = resultado.getString("certificado");
                arr[8] = resultado.getString("certificado2");
                if (box != null) {
                    arr[9] = box;
                } else {

                }
            }
            resultado = stm.executeQuery(sql6);
            if (resultado.next()) {
                box = resultado.getString("presion");
                arr[10] = resultado.getString("presion2");
                if (box != null) {
                    arr[11] = box;
                } else {

                }
            }
            resultado = stm.executeQuery(sql7);
            if (resultado.next()) {
                box = resultado.getString("oido");
                arr[12] = resultado.getString("oido2");
                if (box != null) {
                    arr[13] = box;
                } else {

                }
            }
            resultado = stm.executeQuery(sql8);
            if (resultado.next()) {
                box = resultado.getString("hc");
                arr[14] = resultado.getString("hc2");
                if (box != null) {
                    arr[15] = box;
                } else {

                }
            }
            resultado = stm.executeQuery(sql9);
            if (resultado.next()) {
                box = resultado.getString("tg");
                arr[16] = resultado.getString("tg2");
                if (box != null) {
                    arr[17] = box;
                } else {

                }
            }

            stm.close();
            resultado.close();
        } catch (SQLException ex) {
        } finally {
            conn.getClose();
        }
        return arr;
    }

    public String[] totalesC(int num2) {
        String c[] = null, nom[] = null, turno = "noche";
        String sql = "";
        int num = 0, contador = 0;
        int x = 0, y = 1, z = 2;
        try {
            con = conn.getConnection();
            Statement stm = (Statement) con.createStatement();

            sql = "SELECT COUNT(codigo) AS num FROM productos WHERE tipo_medicamento = 'CONSULTA'";

            ResultSet resultado = stm.executeQuery(sql);
            if (resultado.next()) {
                num = resultado.getInt("num");
                c = new String[num * 3];
                nom = new String[num];
            }

            sql = "SELECT * FROM productos WHERE tipo_medicamento = 'CONSULTA'";
            resultado = stm.executeQuery(sql);

            while (resultado.next()) {
                nom[contador] = resultado.getString("marca_comercial");
                contador++;
            }

            for (int i = 0; i < nom.length; i++) {
                if (num2 == 0) {
                    sql = "SELECT IFNULL(descripcion , '" + nom[i] + "') AS des , IFNULL(SUM(total),0) AS sum , IFNULL(SUM(piezas) , 0 ) AS con FROM detalle_venta WHERE descripcion ='" + nom[i] + "' AND fecha = CURDATE() AND turno = '" + turno + "'";
                } else {
                    sql = "SELECT IFNULL(descripcion , '" + nom[i] + "') AS des , IFNULL(SUM(total),0) AS sum , IFNULL(SUM(piezas) , 0 ) AS con FROM detalle_venta WHERE descripcion ='" + nom[i] + "' AND fecha = '" + getFecha() + "' AND turno = '" + turno + "'";
                }
                resultado = stm.executeQuery(sql);
                if (resultado.next()) {
                    c[x] = resultado.getString("des");
                    c[y] = String.valueOf(resultado.getInt("con"));
                    c[z] = String.valueOf(resultado.getDouble("sum"));
                }
                x += 3;
                y += 3;
                z += 3;

            }

            stm.close();
            resultado.close();
        } catch (SQLException ex) {
            Logger.getLogger(Ventas.class.getName()).log(Level.SEVERE, null, ex);

        } finally {
            conn.getClose();
        }
        return c;

    }
    
     public String[] totalesConsultas(String turno) {
        String[] arr = {"", "", "", ""};
        int contador = 0;
        String sql = "SELECT COUNT(d.id_detalle) AS consultas  FROM detalle_venta d " +
                    " WHERE d.id_producto = 2 AND d.fecha = CURDATE() AND d.turno = '"+turno+"' " +
                    " UNION all " +
                    " SELECT COUNT(d.id_detalle) AS aplicaciones FROM detalle_venta d " +
                    " WHERE d.id_producto = 3 AND d.fecha =  CURDATE() AND d.turno = '"+turno+"'  " +
                    " UNION all " +
                    " SELECT COUNT(d.id_detalle) AS tp FROM detalle_venta d " +
                    " WHERE d.id_producto = 23 AND d.fecha = CURDATE() AND d.turno = '"+turno+"' " +
                    " UNION all" +
                    " SELECT COUNT(d.id_detalle) AS glucosa FROM detalle_venta d " +
                    " WHERE d.id_producto = 10 AND d.fecha =  CURDATE() AND d.turno = '"+turno+"' ";
        
        try {
            con = conn.getConnection();
            Statement stm = (Statement) con.createStatement();

            ResultSet resultado = stm.executeQuery(sql);

            while (resultado.next()) {
                arr[contador] = resultado.getString("consultas");
                contador++;
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

    public String retiros(int num) {
        String sql = null, precio = "";
        try {
            con = new Conexion().getConnection();
            Statement stm = (Statement) con.createStatement();

            if (num == 0) {
                sql = "SELECT IFNULL(SUM(precio),0) as total FROM retiros WHERE fecha = CURDATE() AND turno='" + getTurno() + "'";
            } else {
                sql = "SELECT IFNULL(SUM(precio),0) as total FROM retiros WHERE fecha = '" + getFecha() + "' AND turno='" + getTurno() + "'";
            }
            ResultSet rs = stm.executeQuery(sql);
            if (rs.next()) {
                precio = rs.getString("total");

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

    public DefaultTableModel buscarCorte(JTable jt, String fecha) {
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
        ArrayList<Corte> arrayEgresos = new ArrayList<>();
        try {

            String sql = "SELECT * FROM cortes WHERE fecha = '" + fecha + "' or id_corte = " + getId() + "  order by fecha";
            Connection con = new Conexion().getConnection();
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            ResultSet resultado = pst.executeQuery();
            while (resultado.next()) {
                arrayEgresos.add(new Corte(resultado.getInt("id_corte"), resultado.getDouble("monto"), resultado.getString("fecha"), resultado.getString("turno")));
            }
            for (int i = 0; i < arrayEgresos.size(); i++) {
                modelo.addRow(new Object[]{arrayEgresos.get(i).getId(), arrayEgresos.get(i).getTotal(), arrayEgresos.get(i).getFecha(),
                    arrayEgresos.get(i).getTurno()});
            }
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(Corte.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
        }

        return modelo;

    }
}
