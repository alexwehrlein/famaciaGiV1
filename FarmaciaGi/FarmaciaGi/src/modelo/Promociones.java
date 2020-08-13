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
 * @author alexwehrlein
 */
public class Promociones {

    private int id;
    private long codigo;
    private String nombre;
    private int descuento;
    private String tipo;
    private Connection con;
    private Conexion conn = new Conexion();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getCodigo() {
        return codigo;
    }

    public void setCodigo(long codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getDescuento() {
        return descuento;
    }

    public void setDescuento(int descuento) {
        this.descuento = descuento;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Promociones() {

    }

    public Promociones(long codigo, int descuento) {
        this.codigo = codigo;
        this.descuento = descuento;
    }
    
    
    public Promociones(long codigo) {
        this.codigo = codigo;
    }

    public Promociones(int id, long codigo, String nombre, int descuento, String tipo) {
        this.id = id;
        this.codigo = codigo;
        this.nombre = nombre;
        this.descuento = descuento;
        this.tipo = tipo;
    }

    public Promociones(int id, int descuento, String tipo) {
        this.id = id;
        this.descuento = descuento;
        this.tipo = tipo;
    }
    
    public boolean guardesPromocion(){
         String sql = null;
        try {
            con = conn.getConnection();
            Statement stm = (Statement) con.createStatement();
            sql = "INSERT INTO promociones (codigoPrododucto , descuento) VALUES(" + getCodigo()+ ", " + getDescuento()+ ")";
            stm.execute(sql);
            stm.close();
        } catch (SQLException ex) {
            Logger.getLogger(Ventas.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
            return false;
        } finally {
            conn.getClose();
        }
        return true;
    }
    
    public String[] buscarProducto() {
        String[] arr = {"", "", ""};
        try {
            con = conn.getConnection();
            Statement stm = (Statement) con.createStatement();

            String sql = "SELECT * FROM productos WHERE codigo=" + getCodigo() + "";
            ResultSet rs = stm.executeQuery(sql);
            if (rs.next()) {
                arr[0] = rs.getString("marca_comercial");
                arr[1] = rs.getString("precio");
            }else{
                arr[2] = "No se encontró el producto.";
            }
            stm.close();
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(Ventas.class.getName()).log(Level.SEVERE, null, ex);

        } finally {
            conn.getClose();
        }
        return arr;
    }
    
    public boolean modificarPromocion() {
        String sql = null;
        try {
            con = conn.getConnection();
            Statement stm = (Statement) con.createStatement();
            sql = "UPDATE promociones SET descuento = '" + getDescuento() + "' , status = '" + getTipo() + "' WHERE id =" + getId();
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

    public DefaultTableModel cargarRegistroPromociones(JTable jt, String codigo) {
        String sql = "";
        jt.setDefaultRenderer(Object.class, new Render());
        JButton btnModificar = new JButton("Modificar");
        JButton btnEliminar = new JButton("Eliminar");
        JComboBox tipoJ;
        TableColumn col = jt.getColumnModel().getColumn(4);
        String op[] = {"SI", "NO"};
        tipoJ = new JComboBox(op);
        col.setCellEditor(new DefaultCellEditor(tipoJ));
        btnModificar.setName("btnModificar");
        btnEliminar.setName("btnEliminar");
        ImageIcon im = new ImageIcon(getClass().getResource("/imagenes/mo.png"));
        btnModificar.setIcon(im);
        ImageIcon ie = new ImageIcon(getClass().getResource("/imagenes/eli.png"));
        btnEliminar.setIcon(ie);
        jt.setRowHeight(25);

        DefaultTableModel modelo = (DefaultTableModel) jt.getModel();
        ArrayList<Promociones> arrayPromociones = new ArrayList<>();
        try {
            if (!codigo.isEmpty()) {
                sql = "SELECT promociones.* , productos.marca_comercial AS nombre FROM promociones INNER JOIN productos on productos.codigo = promociones.codigoPrododucto WHERE codigoPrododucto = " + codigo;
            } else {
                sql = "SELECT promociones.* , productos.marca_comercial AS nombre FROM promociones INNER JOIN productos on productos.codigo = promociones.codigoPrododucto";
            }

            Connection con = new Conexion().getConnection();
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            ResultSet resultado = pst.executeQuery();
            while (resultado.next()) {
                arrayPromociones.add(new Promociones(resultado.getInt("id"), resultado.getLong("codigoPrododucto"), resultado.getString("nombre"), resultado.getInt("descuento"), resultado.getString("status")));
            }
            for (int i = 0; i < arrayPromociones.size(); i++) {
                modelo.addRow(new Object[]{arrayPromociones.get(i).getId(), arrayPromociones.get(i).getCodigo(),
                    arrayPromociones.get(i).getNombre(), arrayPromociones.get(i).getDescuento(), arrayPromociones.get(i).getTipo(),
                    btnModificar});
            }
            pst.close();
            pst = null;
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(Promociones.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
        }

        return modelo;
    }

}
