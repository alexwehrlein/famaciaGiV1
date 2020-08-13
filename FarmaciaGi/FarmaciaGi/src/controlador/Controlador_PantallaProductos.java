/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.Color;
import vista.Pantalla_Productos;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import modelo.Productos;
import modelo.Proveedor;
import tikect.TikectInventario;

/**
 *
 * @author saube
 */
public class Controlador_PantallaProductos {

    Pantalla_Productos pantalla_Productos;
    Productos productos;
    TikectInventario tikectInventario;
    int idEmpleado;

    public Controlador_PantallaProductos(String rol, String turno , int idEmpleado , String pc) {
        pantalla_Productos = new Pantalla_Productos();
        pantalla_Productos.setVisible(true);
        pantalla_Productos.setLocationRelativeTo(null);
        pantalla_Productos.tablaProductos.setModel(new Productos().cargarRegistroEgreso(pantalla_Productos.tablaProductos));
        pantalla_Productos.existenciasM.setVisible(false);
        pantalla_Productos.codigo.setVisible(false);
        this.idEmpleado = idEmpleado;
        
        List<List<String>> productosTikect = new ArrayList<List<String>>();
        productosTikect.add(new ArrayList<String>());
        productosTikect.add(new ArrayList<String>());

        pantalla_Productos.agregarInventarioTikect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (productosTikect.get(0).size() > 0 && productosTikect.get(1).size() > 0) {
                    tikectInventario = new TikectInventario();
                    tikectInventario.tikectInventario(turno, productosTikect,pc);
                    for (int i = 0; i <= productosTikect.get(0).size() - 1; i++) {
                        productosTikect.get(0).clear();
                        productosTikect.get(1).clear();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "<html><h1 align='center'> No hay productos agregados </h1></html>", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        pantalla_Productos.productoAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //pantalla_Productos.setVisible(false);
                //new Controlador_PantallaProductoAdd(rol, turno);
                pantalla_Productos.jDialogAddProducto.setBounds(249, 154, 764, 550);
                pantalla_Productos.jDialogAddProducto.setResizable(false);
                pantalla_Productos.jDialogAddProducto.setVisible(true);
                pantalla_Productos.altaMedicamentoCodigo.requestFocus();
            }
        });

        pantalla_Productos.actuslizartabla.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Clear_Table();
                pantalla_Productos.tablaProductos.setModel(new Productos().cargarRegistroEgreso(pantalla_Productos.tablaProductos));
            }
        });

        pantalla_Productos.agregarInventario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = pantalla_Productos.tablaProductos.getSelectedRow();
                if (row < 0) {
                    JOptionPane.showMessageDialog(null, "<html><h1 align='center'>Selecciona una fila </h1></html>", "ERROR", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (pantalla_Productos.campoAgregarExistencia.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "<html><h1 align='center'> No deje el  campo en blanco </h1></html>");
                } else {
                    int inventario = Integer.parseInt(pantalla_Productos.campoAgregarExistencia.getText());
                    if (inventario > 0) {
                        long codigo = Long.valueOf(pantalla_Productos.codigo.getText());
                        productos = new Productos(codigo);
                        int existencias = productos.productoCero();
                        int agregarInventario = inventario + existencias;
                        String producto = pantalla_Productos.nombre.getText();
                        productos = new Productos(codigo, agregarInventario , idEmpleado ,inventario );
                        if (productos.Modificarexistencias()) {
                            JOptionPane.showMessageDialog(null, "<html><h1 align='center'> SE AGREGARON " + inventario + " PIEZAS A INVENTARIO </h1></html>");
                            productosTikect.get(0).add(producto);
                            productosTikect.get(1).add(String.valueOf(inventario));
                            Clear_Table();
                            pantalla_Productos.tablaProductos.setModel(new Productos().cargarRegistroEgreso(pantalla_Productos.tablaProductos));
                            pantalla_Productos.codigo.setText("");
                            pantalla_Productos.nombre.setText("");
                            pantalla_Productos.existenciasM.setText("");
                            pantalla_Productos.campoAgregarExistencia.setText("");
                            pantalla_Productos.buscarProductos.setText("");

                        } else {
                            JOptionPane.showMessageDialog(null, "<html><h1 align='center'>Error </h1></html>", "ERROR", JOptionPane.ERROR_MESSAGE);
                        }

                    } else {
                        JOptionPane.showMessageDialog(null, "<html><h1 align='center'> Tiene que ser mayor a 0 </h1></html>");
                    }
                }
            }
        });

        pantalla_Productos.tablaProductos.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent me) {

                int column = pantalla_Productos.tablaProductos.getColumnModel().getColumnIndexAtX(me.getX());
                int row = me.getY() / pantalla_Productos.tablaProductos.getRowHeight();
                int filaseleccionada;
                if (row < pantalla_Productos.tablaProductos.getRowCount() && row >= 0 && column < pantalla_Productos.tablaProductos.getColumnCount() && column >= 0) {
                    Object value = pantalla_Productos.tablaProductos.getValueAt(row, column);
                    if (value instanceof JButton) {
                        ((JButton) value).doClick();
                        JButton boton = (JButton) value;

                        if (boton.getName().equals("btnModificar")) {
                            int reply = JOptionPane.showConfirmDialog(null, "<html><h1 align='center'> Modificar Medicamento? </h1></html>", "Modificar", JOptionPane.YES_NO_OPTION);
                            if (reply == JOptionPane.YES_OPTION) {
                                filaseleccionada = pantalla_Productos.tablaProductos.getSelectedRow();
                                long codi = (long) pantalla_Productos.tablaProductos.getValueAt(filaseleccionada, 0);
                                String nombreM = (String) pantalla_Productos.tablaProductos.getValueAt(filaseleccionada, 1);
                                String precio = (String) pantalla_Productos.tablaProductos.getValueAt(filaseleccionada, 3).toString();
                                if (!precio.matches("^\\d+\\.?\\d?\\d?")) {
                                    JOptionPane.showMessageDialog(null, "<html><h1 align='center'> Ingrese una cantidad correcta. </h1></html>", "ERROR", JOptionPane.ERROR_MESSAGE);
                                    return;
                                }
                                double precio2 = Double.valueOf(precio);
                                if (rol.equals("Cajero")) {
                                    productos = new Productos(codi);
                                    double precioActual = productos.PrrcioProducto();
                                    if (precio2 >= precioActual) {
                                        productos = new Productos(codi, precio2, nombreM);
                                        if (productos.ModificarRegristros()) {
                                            JOptionPane.showMessageDialog(null, "<html><h1 align='center'>Datos Modificados Correctamente </h1></html>");
                                            Clear_Table();
                                            pantalla_Productos.tablaProductos.setModel(new Productos().cargarRegistroEgreso(pantalla_Productos.tablaProductos));

                                        } else {
                                            JOptionPane.showMessageDialog(null, "<html><h1 align='center'>Error </h1></html>", "ERROR", JOptionPane.ERROR_MESSAGE);
                                            Clear_Table();
                                            pantalla_Productos.tablaProductos.setModel(new Productos().cargarRegistroEgreso(pantalla_Productos.tablaProductos));
                                        }
                                    } else {
                                        JOptionPane.showMessageDialog(null, "<html><h1 align='center'>No puede modificar el precio a uno menor contacte al administrador </h1></html>", "ERROR", JOptionPane.ERROR_MESSAGE);
                                        Clear_Table();
                                        pantalla_Productos.tablaProductos.setModel(new Productos().cargarRegistroEgreso(pantalla_Productos.tablaProductos));
                                    }
                                } else {
                                    productos = new Productos(codi, precio2, nombreM);
                                    if (productos.ModificarRegristros()) {
                                        JOptionPane.showMessageDialog(null, "<html><h1 align='center'>Datos Modificados Correctamente </h1></html>");
                                        Clear_Table();
                                        pantalla_Productos.tablaProductos.setModel(new Productos().cargarRegistroEgreso(pantalla_Productos.tablaProductos));

                                    } else {
                                        JOptionPane.showMessageDialog(null, "<html><h1 align='center'>Error </h1></html>", "ERROR", JOptionPane.ERROR_MESSAGE);
                                        Clear_Table();
                                        pantalla_Productos.tablaProductos.setModel(new Productos().cargarRegistroEgreso(pantalla_Productos.tablaProductos));
                                    }
                                }

                            }
                        }

                        if (boton.getName().equals("btnEliminar")) {
                            filaseleccionada = pantalla_Productos.tablaProductos.getSelectedRow();
                            int reply = JOptionPane.showConfirmDialog(null, "<html><h1 align='center'>¿Eliminar El Medicamento? </h1></html>", "Eliminar", JOptionPane.YES_NO_OPTION);
                            if (reply == JOptionPane.YES_OPTION) {
                                long codi = (long) pantalla_Productos.tablaProductos.getValueAt(filaseleccionada, 0);
                                productos = new Productos(codi);

                                if (productos.eliminarMedicamento()) {
                                    JOptionPane.showMessageDialog(null, "<html><h1 align='center'>Medicamento Eliminado Correctamente </h1></html>");
                                    Clear_Table();
                                    pantalla_Productos.tablaProductos.setModel(new Productos().cargarRegistroEgreso(pantalla_Productos.tablaProductos));

                                } else {
                                    JOptionPane.showMessageDialog(null, "<html><h1 align='center'>Error </h1></html>", "ERROR", JOptionPane.ERROR_MESSAGE);
                                }

                            }
                        }

                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent me) {

            }

            @Override
            public void mouseReleased(MouseEvent me) {

            }

            @Override
            public void mouseEntered(MouseEvent me) {

            }

            @Override
            public void mouseExited(MouseEvent me) {

            }
        });

        pantalla_Productos.buscarProductos.addKeyListener(new KeyAdapter() {

            @Override
            public void keyReleased(KeyEvent e
            ) {

                if (pantalla_Productos.busquedaCodigo.isSelected()) {
                    String valor = pantalla_Productos.buscarProductos.getText();
                    if (valor.isEmpty()) {

                    } else {
                        if (!valor.matches("[0-9]*")) {
                            JOptionPane.showMessageDialog(null, "Solo Numeros");
                        } else {
                            Clear_Table();
                            String medicamento = pantalla_Productos.buscarProductos.getText();
                            long codigoBarra = Long.valueOf(medicamento);
                            pantalla_Productos.tablaProductos.setModel(new Productos().BuscarRegistroEgreso(pantalla_Productos.tablaProductos, codigoBarra));

                        }
                    }
                }
                if (pantalla_Productos.busquedaNombre.isSelected()) {
                    String valor = pantalla_Productos.buscarProductos.getText();
                    Clear_Table();
                    pantalla_Productos.tablaProductos.setModel(new Productos().Buscar2RegistroEgreso(pantalla_Productos.tablaProductos, valor));
                }

            }

        }
        );

        pantalla_Productos.altaMedicamentoGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (pantalla_Productos.altaMedicamentoCodigo.getText().equals("") || pantalla_Productos.altaMedicamentoMarcaComercial.getText().equals("")
                        || pantalla_Productos.altaMedicamentoSustancia.getText().equals("") || pantalla_Productos.altaMedicamentoPrecio.getText().equals("")
                        || pantalla_Productos.altaMedicamentoCantidad.getText().equals("")) {

                    JOptionPane.showMessageDialog(null, "<html><h1 align='center'>No deje campos en blaco</h1></html>");

                } else {

                    boolean next = validarFormulario(pantalla_Productos.altaMedicamentoPrecio.getText(), pantalla_Productos.altaMedicamentoCantidad.getText());
                    if (next) {
                        if (!pantalla_Productos.altaMedicamentoCodigo.getText().matches("[0-9]*")) {
                            JOptionPane.showMessageDialog(null, "<html><h1>Ingrese un codigo correcto.</html></h1>", "ERROR", JOptionPane.ERROR_MESSAGE);
                            pantalla_Productos.altaMedicamentoCodigo.requestFocus();
                            return;
                        }
                        if (!pantalla_Productos.altaMedicamentoCantidad.getText().matches("[0-9]*")) {
                            JOptionPane.showMessageDialog(null, "<html><h1>Ingrese un cantidad correcta.</html></h1>", "ERROR", JOptionPane.ERROR_MESSAGE);
                            pantalla_Productos.altaMedicamentoCantidad.requestFocus();
                            return;
                        }
                        if (!pantalla_Productos.altaMedicamentoPrecio.getText().matches("\\d+\\.?\\d?\\d?")) {
                            JOptionPane.showMessageDialog(null, "<html><h1>Ingrese un precio correcta.</html></h1>", "ERROR", JOptionPane.ERROR_MESSAGE);
                            pantalla_Productos.altaMedicamentoPrecio.requestFocus();
                            return;
                        }

                        long codigo = Long.valueOf(pantalla_Productos.altaMedicamentoCodigo.getText());
                        String marcaComercia = pantalla_Productos.altaMedicamentoMarcaComercial.getText();
                        String sustancia = pantalla_Productos.altaMedicamentoSustancia.getText();
                        double precio = Double.parseDouble(pantalla_Productos.altaMedicamentoPrecio.getText());
                        String tipoMedicamento = pantalla_Productos.altaMedicamentoTipoMedicamento.getSelectedItem().toString();
                        String laboratorio = pantalla_Productos.altaMedicamentoLavoratorio.getSelectedItem().toString();
                        Proveedor proveedor = (Proveedor) pantalla_Productos.altaMedicamentoProveedor.getSelectedItem();
                        int cantidad = Integer.parseInt(pantalla_Productos.altaMedicamentoCantidad.getText());

                        productos = new Productos(codigo, marcaComercia.toUpperCase(), sustancia.toUpperCase(), precio, tipoMedicamento, laboratorio, proveedor.getIdproveedor(), cantidad);

                        if (productos.registrarProducto()) {
                            JOptionPane.showMessageDialog(null, "<html><h1>EL PRODUCTO SE HA DADO DE ALTA EN LA BASE DE DATOS</h1></html>", "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
                            limpiarCampos();
                            pantalla_Productos.altaMedicamentoCodigo.requestFocus();
                            pantalla_Productos.altaMedicamentoCodigo.setBackground(Color.WHITE);

                        } else {
                            JOptionPane.showMessageDialog(null, "<html><h1 align='center'>Error</h1></html>", "ERROR", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }

            }
        });

        pantalla_Productos.altaMedicamentoCodigo.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    long codigo = Long.parseLong(pantalla_Productos.altaMedicamentoCodigo.getText());
                    productos = new Productos(codigo);
                    boolean next = productos.verificarCodigo();

                    if (next) {
                        pantalla_Productos.altaMedicamentoCodigo.setBackground(Color.RED);
                        pantalla_Productos.altaMedicamentoCodigo.setText("");
                        JOptionPane.showMessageDialog(null, "<html><h1 align='center'> El codigo ya a sido registrado </h1></html>");

                    } else {
                        pantalla_Productos.altaMedicamentoCodigo.setBackground(Color.GREEN);
                        pantalla_Productos.altaMedicamentoMarcaComercial.requestFocus();

                    }

                }

            }
        });

        pantalla_Productos.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        pantalla_Productos.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent ev) {
                if (productosTikect.get(0).size() > 0 && productosTikect.get(1).size() > 0) {
                    JOptionPane.showMessageDialog(null, "<html><h1 align='center'> ANTES DE CERRAR LA VENTANA IMPRIMA EL TIKECT </h1></html>", "ERROR", JOptionPane.ERROR_MESSAGE);
                } else {
                    pantalla_Productos.dispose();
                }
            }
        });
    }

    private boolean validarFormulario(String precio, String cantidad) {
        boolean next = false;
        Pattern patPrecio = Pattern.compile("^[0-9]+([.])?([0-9]+)?$");
        Pattern patCantidad = Pattern.compile("[0-9]*");
        Matcher matPrecio = patPrecio.matcher(precio);
        Matcher matCantidad = patCantidad.matcher(cantidad);

        if (matPrecio.matches()) {
            pantalla_Productos.altaMedicamentoPrecio.setBackground(Color.WHITE);

            if (matCantidad.matches()) {
                pantalla_Productos.altaMedicamentoCantidad.setBackground(Color.WHITE);
                next = true;
            } else {
                JOptionPane.showMessageDialog(null, "<html><h1 align='center'> Solo Numeros </h1></html>");
                pantalla_Productos.altaMedicamentoCantidad.setBackground(Color.red);
            }
        } else {
            JOptionPane.showMessageDialog(null, "<html><h1 align='center'> Solo Numeros </h1></html>");
            pantalla_Productos.altaMedicamentoPrecio.setBackground(Color.red);
        }

        return next;
    }

    private void limpiarCampos() {
        pantalla_Productos.altaMedicamentoCantidad.setText("");
        pantalla_Productos.altaMedicamentoCodigo.setText("");
        pantalla_Productos.altaMedicamentoMarcaComercial.setText("");
        pantalla_Productos.altaMedicamentoPrecio.setText("");
        pantalla_Productos.altaMedicamentoSustancia.setText("");
        pantalla_Productos.altaMedicamentoLavoratorio.setSelectedIndex(1);
        pantalla_Productos.altaMedicamentoTipoMedicamento.setSelectedIndex(1);
        pantalla_Productos.altaMedicamentoProveedor.setSelectedIndex(1);

    }

    private void Clear_Table() {
        DefaultTableModel modelo = (DefaultTableModel) pantalla_Productos.tablaProductos.getModel();
        int filas = pantalla_Productos.tablaProductos.getRowCount();
        for (int i = 0; filas > i; i++) {
            modelo.removeRow(0);
        }
    }

}
