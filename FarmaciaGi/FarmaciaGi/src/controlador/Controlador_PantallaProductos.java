/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import vista.Pantalla_Productos;
import vista.Pantalla_ProductosAdd;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.Empleado;
import modelo.Productos;
import modelo.Proveedor;
import tikect.TikectInventario;
import tikect.TikectVentas;

/**
 *
 * @author saube
 */
public class Controlador_PantallaProductos {

    Pantalla_Productos pantalla_Productos;
    Pantalla_ProductosAdd productoAgregar;
    Productos productos;
    TikectInventario tikectInventario;

    public Controlador_PantallaProductos(String rol, String turno) {
        pantalla_Productos = new Pantalla_Productos();
        pantalla_Productos.setVisible(true);
        pantalla_Productos.setLocationRelativeTo(null);
        pantalla_Productos.tablaProductos.setModel(new Productos().cargarRegistroEgreso(pantalla_Productos.tablaProductos));
        pantalla_Productos.existenciasM.setVisible(false);
        pantalla_Productos.codigo.setVisible(false);

        List<List<String>> productosTikect = new ArrayList<List<String>>();
        productosTikect.add(new ArrayList<String>());
        productosTikect.add(new ArrayList<String>());

        pantalla_Productos.agregarInventarioTikect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (productosTikect.get(0).size() > 0 && productosTikect.get(1).size() > 0) {
                    tikectInventario = new TikectInventario();
                    tikectInventario.tikectInventario(turno, productosTikect);

                } else {
                    JOptionPane.showMessageDialog(null, "<html><h1 align='center'> No hay productos agregados </h1></html>", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        pantalla_Productos.productoAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pantalla_Productos.setVisible(false);
                new Controlador_PantallaProductoAdd(rol, turno);
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
                        productos = new Productos(codigo, agregarInventario);
                        if (productos.Modificarexistencias()) {
                            JOptionPane.showMessageDialog(null, "<html><h1 align='center'> SE AGREGARON "+inventario+" PIEZAS A INVENTARIO </h1></html>");
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
                                String precio = (String) pantalla_Productos.tablaProductos.getValueAt(filaseleccionada, 3);
                                if (!precio.matches("^\\d+\\.?\\d?\\d?")) {
                                    JOptionPane.showMessageDialog(null, "<html><h1 align='center'> Ingrese una cantidad correcta. </h1></html>" , "ERROR" , JOptionPane.ERROR_MESSAGE);
                                    return;
                                }
                                double precio2 = Double.valueOf(precio);
                                if (rol.equals("Cajero")) {
                                    System.out.println(rol);
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

        pantalla_Productos.buscarProductos.addKeyListener(
                new KeyAdapter() {

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
    }

    private void Clear_Table() {
        DefaultTableModel modelo = (DefaultTableModel) pantalla_Productos.tablaProductos.getModel();
        int filas = pantalla_Productos.tablaProductos.getRowCount();
        for (int i = 0; filas > i; i++) {
            modelo.removeRow(0);
        }
    }

}
