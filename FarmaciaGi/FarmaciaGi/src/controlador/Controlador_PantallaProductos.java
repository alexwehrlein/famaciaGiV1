/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.pdf.PdfPTable;
import java.awt.Color;
//import java.awt.Font;
import vista.Pantalla_Productos;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import mail.Mail;
import modelo.Empleado;
import modelo.Productos;
import modelo.Proveedor;
import modelo.Ventas;
import modelo.sucursal;
import tikect.TikectInventario;
import tikect.TikectProducto;
import utilerias.Utilerias;

public class Controlador_PantallaProductos {

    Pantalla_Productos pantalla_Productos;
    Productos productos;
    Empleado empleado;
    TikectInventario tikectInventario;
    TikectProducto tikectProducto;
    int idEmpleado;
    String cargo;

    public Controlador_PantallaProductos(String rol, String turno, int idEmpleado, String pc) {
        pantalla_Productos = new Pantalla_Productos();
        pantalla_Productos.setVisible(true);
        pantalla_Productos.setLocationRelativeTo(null);
        empleado = new Empleado();
        empleado.Empleado(String.valueOf(idEmpleado));
        if (empleado.getPuesto().equals("Administrador")) {

            pantalla_Productos.tablaProductos.setModel(new javax.swing.table.DefaultTableModel(
                    new Object[][]{},
                    new String[]{
                        "Codigo", "Marca Comercial", "Sustancia", "Precio", "Tipo de Medicamento", "Laboratorio", "Modificar"
                    }
            ) {
                boolean[] canEdit = new boolean[]{
                    false, true, false, true, true, false, false, false
                };

                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return canEdit[columnIndex];
                }
            });
        }
        pantalla_Productos.tablaProductos.setModel(new Productos().cargarRegistroEgreso(pantalla_Productos.tablaProductos));
        pantalla_Productos.existenciasM.setVisible(false);
        pantalla_Productos.codigo.setVisible(false);
        this.idEmpleado = idEmpleado;

        List<List<String>> productosTikect = new ArrayList<List<String>>();
        productosTikect.add(new ArrayList<String>());
        productosTikect.add(new ArrayList<String>());

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

        pantalla_Productos.btnExistencias.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //pantalla_Productos.setVisible(false);
                //new Controlador_PantallaProductoAdd(rol, turno);
                pantalla_Productos.jDialogInventario.setBounds(249, 100, 670, 583);
                pantalla_Productos.jDialogInventario.setResizable(false);
                pantalla_Productos.jDialogInventario.setVisible(true);
                pantalla_Productos.txtInvetarioCodigo.requestFocus();
                pantalla_Productos.txtInvetarioCodigo.setText("");
                pantalla_Productos.txtInvetarioNombre.setText("");
                pantalla_Productos.txtInvetarioPiezas.setText("");
                Clear_Table(pantalla_Productos.tableInvetario);
            }
        });

        pantalla_Productos.actuslizartabla.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Clear_Table(pantalla_Productos.tablaProductos);
                pantalla_Productos.tablaProductos.setModel(new Productos().cargarRegistroEgreso(pantalla_Productos.tablaProductos));
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
                                String codi = pantalla_Productos.tablaProductos.getValueAt(filaseleccionada, 0).toString();
                                String nombreM = (String) pantalla_Productos.tablaProductos.getValueAt(filaseleccionada, 1);
                                String precio = (String) pantalla_Productos.tablaProductos.getValueAt(filaseleccionada, 3).toString();
                                String tipoMedicamento = pantalla_Productos.tablaProductos.getValueAt(filaseleccionada, 4).toString();
                                if (!precio.matches("^\\d+\\.?\\d?\\d?")) {
                                    JOptionPane.showMessageDialog(null, "<html><h1 align='center'> Ingrese una cantidad correcta. </h1></html>", "ERROR", JOptionPane.ERROR_MESSAGE);
                                    return;
                                }
                                double precio2 = Double.valueOf(precio);
                                if (empleado.getPuesto().equals("Cajero")) {
                                    productos = new Productos(codi);
                                    double precioActual = productos.PrrcioProducto();
                                    if (precio2 >= precioActual) {
                                        productos = new Productos(codi, precio2, nombreM, tipoMedicamento);
                                        if (productos.ModificarRegristros()) {
                                            JOptionPane.showMessageDialog(null, "<html><h1 align='center'>Datos Modificados Correctamente </h1></html>");
                                            Clear_Table(pantalla_Productos.tablaProductos);
                                            pantalla_Productos.tablaProductos.setModel(new Productos().cargarRegistroEgreso(pantalla_Productos.tablaProductos));

                                        } else {
                                            JOptionPane.showMessageDialog(null, "<html><h1 align='center'>Error </h1></html>", "ERROR", JOptionPane.ERROR_MESSAGE);
                                            Clear_Table(pantalla_Productos.tablaProductos);
                                            pantalla_Productos.tablaProductos.setModel(new Productos().cargarRegistroEgreso(pantalla_Productos.tablaProductos));
                                        }
                                    } else {
                                        JOptionPane.showMessageDialog(null, "<html><h1 align='center'>No puede modificar el precio a uno menor contacte al administrador </h1></html>", "ERROR", JOptionPane.ERROR_MESSAGE);
                                        Clear_Table(pantalla_Productos.tablaProductos);
                                        pantalla_Productos.tablaProductos.setModel(new Productos().cargarRegistroEgreso(pantalla_Productos.tablaProductos));
                                    }
                                } else {
                                    productos = new Productos(codi, precio2, nombreM, tipoMedicamento);
                                    if (productos.ModificarRegristros()) {
                                        JOptionPane.showMessageDialog(null, "<html><h1 align='center'>Datos Modificados Correctamente </h1></html>");
                                        Clear_Table(pantalla_Productos.tablaProductos);
                                        pantalla_Productos.tablaProductos.setModel(new Productos().cargarRegistroEgreso(pantalla_Productos.tablaProductos));

                                    } else {
                                        JOptionPane.showMessageDialog(null, "<html><h1 align='center'>Error </h1></html>", "ERROR", JOptionPane.ERROR_MESSAGE);
                                        Clear_Table(pantalla_Productos.tablaProductos);
                                        pantalla_Productos.tablaProductos.setModel(new Productos().cargarRegistroEgreso(pantalla_Productos.tablaProductos));
                                    }
                                }

                            }
                        }

                        if (boton.getName().equals("btnEliminar")) {
                            filaseleccionada = pantalla_Productos.tablaProductos.getSelectedRow();
                            int reply = JOptionPane.showConfirmDialog(null, "<html><h1 align='center'>¿Eliminar El Medicamento? </h1></html>", "Eliminar", JOptionPane.YES_NO_OPTION);
                            if (reply == JOptionPane.YES_OPTION) {
                                String codi = pantalla_Productos.tablaProductos.getValueAt(filaseleccionada, 0).toString();
                                productos = new Productos(codi);

                                if (productos.eliminarMedicamento()) {
                                    JOptionPane.showMessageDialog(null, "<html><h1 align='center'>Medicamento Eliminado Correctamente </h1></html>");
                                    Clear_Table(pantalla_Productos.tablaProductos);
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
                            Clear_Table(pantalla_Productos.tablaProductos);
                            String medicamento = pantalla_Productos.buscarProductos.getText();
                            long codigoBarra = Long.valueOf(medicamento);
                            pantalla_Productos.tablaProductos.setModel(new Productos().BuscarRegistroEgreso(pantalla_Productos.tablaProductos, codigoBarra));

                        }
                    }
                }
                if (pantalla_Productos.busquedaNombre.isSelected()) {
                    String valor = pantalla_Productos.buscarProductos.getText();
                    Clear_Table(pantalla_Productos.tablaProductos);
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
                        if (!pantalla_Productos.altaMedicamentoPrecioCompra.getText().matches("\\d+\\.?\\d?\\d?")) {
                            JOptionPane.showMessageDialog(null, "<html><h1>Ingrese un precio correcta.</html></h1>", "ERROR", JOptionPane.ERROR_MESSAGE);
                            pantalla_Productos.altaMedicamentoPrecioCompra.requestFocus();
                            return;
                        }
                        if (pantalla_Productos.nombreEmpleado.getText().isEmpty()) {
                            JOptionPane.showMessageDialog(null, "<html><h1>Ingrese el nombre de quien esta dando de alta el articulo.</html></h1>", "ERROR", JOptionPane.ERROR_MESSAGE);
                            pantalla_Productos.nombreEmpleado.requestFocus();
                            return;
                        }

                            
                        String nombreEmpleado = pantalla_Productos.nombreEmpleado.getText();
                        String codigo = pantalla_Productos.altaMedicamentoCodigo.getText();
                        String marcaComercia = pantalla_Productos.altaMedicamentoMarcaComercial.getText();
                        String sustancia = pantalla_Productos.altaMedicamentoSustancia.getText();
                        double precio = Double.parseDouble(pantalla_Productos.altaMedicamentoPrecio.getText());
                        double precioCompra = Double.parseDouble(pantalla_Productos.altaMedicamentoPrecioCompra.getText());
                        String tipoMedicamento = pantalla_Productos.altaMedicamentoTipoMedicamento.getSelectedItem().toString();
                        String laboratorio = pantalla_Productos.altaMedicamentoLavoratorio.getSelectedItem().toString();
                        Proveedor proveedor = (Proveedor) pantalla_Productos.altaMedicamentoProveedor.getSelectedItem();
                        int cantidad = Integer.parseInt(pantalla_Productos.altaMedicamentoCantidad.getText());

                        productos = new Productos(codigo, marcaComercia.toUpperCase(), sustancia.toUpperCase(), precio, precioCompra, tipoMedicamento, laboratorio, proveedor.getIdproveedor(), cantidad);

                        if (productos.registrarProducto()) {
                            JOptionPane.showMessageDialog(null, "<html><h1>EL PRODUCTO SE HA DADO DE ALTA EN LA BASE DE DATOS</h1></html>", "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
                            limpiarCampos();
                            tikectProducto = new TikectProducto();
                            tikectProducto.tikectProducto(turno, marcaComercia, cantidad, pc , nombreEmpleado, tipoMedicamento );
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
                    String codigo = pantalla_Productos.altaMedicamentoCodigo.getText();
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

        pantalla_Productos.txtInvetarioCodigo.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (pantalla_Productos.txtInvetarioCodigo.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "<html><h1> No dejar el campo vacio.</h1></html>", "ERROR", JOptionPane.ERROR_MESSAGE);
                        pantalla_Productos.txtInvetarioCodigo.requestFocus();
                        return;
                    }

                    if (!new Ventas().existeRegistroProducto(pantalla_Productos.txtInvetarioCodigo.getText())) {
                        JOptionPane.showMessageDialog(null, "<html><h1 align='center'>EL PRODUCTO NO EXISTE </h1></html>", "ERROR..", JOptionPane.ERROR_MESSAGE);
                        pantalla_Productos.txtInvetarioCodigo.requestFocus();
                        return;
                    }

                    //productos = new Productos(Long.parseLong(pantalla_Productos.txtInvetarioCodigo.getText()));
                    Productos productoInfo = new Productos();
                    productoInfo.producto(pantalla_Productos.txtInvetarioCodigo.getText());
                    pantalla_Productos.txtInvetarioNombre.setText(productoInfo.getMarcaComercial());
                    pantalla_Productos.btnAgregarProducto.setEnabled(true);
                    pantalla_Productos.txtInvetarioPiezas.requestFocus();
                }
            }
        });

        pantalla_Productos.btnAgregarProducto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (pantalla_Productos.txtInvetarioPiezas.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "<html><h1> No dejar el campo vacio.</h1></html>", "ERROR", JOptionPane.ERROR_MESSAGE);
                    pantalla_Productos.txtInvetarioPiezas.requestFocus();
                    return;
                }
                if (!pantalla_Productos.txtInvetarioPiezas.getText().matches("^\\d+\\.?\\d?\\d?")) {
                    JOptionPane.showMessageDialog(null, "<html><h1> Ingrese un numero.</h1></html>", "ERROR", JOptionPane.ERROR_MESSAGE);
                    pantalla_Productos.txtInvetarioPiezas.requestFocus();
                    return;
                }
                DefaultTableModel model = (DefaultTableModel) pantalla_Productos.tableInvetario.getModel();
                for (int i = 0; i < pantalla_Productos.tableInvetario.getRowCount(); i++) {
                    String codigo = pantalla_Productos.tableInvetario.getValueAt(i, 0).toString();
                    if (codigo.equals(pantalla_Productos.txtInvetarioCodigo.getText())) {

                        model.setValueAt(Integer.parseInt(pantalla_Productos.tableInvetario.getValueAt(i, 2).toString()) + Integer.parseInt(pantalla_Productos.txtInvetarioPiezas.getText()), i, 2);
                        pantalla_Productos.txtInvetarioCodigo.setText("");
                        pantalla_Productos.txtInvetarioNombre.setText("");
                        pantalla_Productos.txtInvetarioPiezas.setText("");
                        pantalla_Productos.txtInvetarioCodigo.requestFocus();
                        return;
                    }
                }

                model.addRow(new Object[]{pantalla_Productos.txtInvetarioCodigo.getText(), pantalla_Productos.txtInvetarioNombre.getText(), pantalla_Productos.txtInvetarioPiezas.getText()});
                pantalla_Productos.txtInvetarioCodigo.setText("");
                pantalla_Productos.txtInvetarioNombre.setText("");
                pantalla_Productos.txtInvetarioPiezas.setText("");
                pantalla_Productos.btnAgregarProducto.setEnabled(false);
                pantalla_Productos.txtInvetarioCodigo.requestFocus();
            }
        });

        pantalla_Productos.btnInventarioGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel model = (DefaultTableModel) pantalla_Productos.tableInvetario.getModel();
                if (model.getRowCount() <= 0) {
                    JOptionPane.showMessageDialog(null, "<html><h1> no hay datos en la tabla</h1></html>", "ERROR", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                productos = new Productos();
                boolean next = productos.GuadarListaAltas(model, idEmpleado);
                if (next) {
                    tikectInventario = new TikectInventario();
                    tikectInventario.tikectInventario(turno, model, pc);
                    Clear_Table(pantalla_Productos.tableInvetario);
                    JOptionPane.showMessageDialog(null, "<html><h1> Exito</h1></html>", "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "<html><h1> ERROR</h1></html>", "ERROR", JOptionPane.ERROR_MESSAGE);
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

        pantalla_Productos.btnInventariar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pantalla_Productos.jDialogPDF.setBounds(249, 154, 456, 204);
                pantalla_Productos.jDialogPDF.setResizable(false);
                pantalla_Productos.jDialogPDF.setVisible(true);
            }
        });

        pantalla_Productos.btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser dlg = new JFileChooser();
                int option = dlg.showSaveDialog(dlg);
                if (option == JFileChooser.APPROVE_OPTION) {
                    File f = dlg.getSelectedFile();
                    pantalla_Productos.txtUrl.setText(f.toString());
                }
            }
        });

        pantalla_Productos.btnGuardarPDF.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String url = pantalla_Productos.txtUrl.getText();
                if (url.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "<html><h1 align='center'> Eliga la ruta de guardado. </h1></html>", "ERROR", JOptionPane.ERROR_MESSAGE);
                    return;
                }
           
                Date date = new Date();
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                productos = new Productos();
                ArrayList<Productos> datos = productos.inventario();
                try {
                    FileOutputStream ficheroPdf = null;
                    // Se crea el documento
                    Document documento = new Document();
// Se crea el OutputStream para el fichero donde queremos dejar el pdf.
                    ficheroPdf = new FileOutputStream(url+".pdf");
                    // Se asocia el documento al OutputStream y se indica que el espaciado entre
// lineas sera de 20. Esta llamada debe hacerse antes de abrir el documento
                    PdfWriter.getInstance(documento, ficheroPdf);
// Se abre el documento.
                    documento.open();
                    Paragraph titulo = new Paragraph();
                    titulo.setAlignment(Paragraph.ALIGN_CENTER);
                    titulo.setFont(FontFactory.getFont("Times New Roman", 20, Font.BOLD, BaseColor.RED));
                    titulo.add("***Inventario***");
                    documento.add(titulo);
                    
                    Paragraph titulo2 = new Paragraph();
                    titulo2.setAlignment(Paragraph.ALIGN_RIGHT);
                    titulo2.setFont(FontFactory.getFont("Times New Roman", 14, BaseColor.BLACK));
                    DateFormat formatofecha = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    titulo2.add(formatofecha.format(date));
                  
                  
                    Paragraph titulo3 = new Paragraph();
                    titulo3.setAlignment(Paragraph.ALIGN_RIGHT);
                    titulo3.setFont(FontFactory.getFont("Times New Roman", 14, BaseColor.BLACK));
                    sucursal su = new sucursal();
                    String datSucursal[] = su.datosSucursal();
                    titulo3.add(datSucursal[1].toUpperCase());
                    documento.add(titulo3);
                    
                    Paragraph saltolinea12 = new Paragraph();
                    saltolinea12.add("\n\n");
                    documento.add(saltolinea12);
                    
                    float[] columnWidths = {2, 3, 2,1,1};
                    PdfPTable tabla = new PdfPTable(columnWidths);
                    tabla.setWidthPercentage(100);
                    //Añadimos los títulos a la tabla. 
                    Paragraph columna1 = new Paragraph("Código");
                    columna1.getFont().setStyle(Font.BOLD);
                    columna1.getFont().setSize(10);
                    tabla.addCell(columna1);

                    Paragraph columna2 = new Paragraph("Marca");
                    columna2.getFont().setStyle(Font.BOLD);
                    columna2.getFont().setSize(10);
                    tabla.addCell(columna2);

                    Paragraph columna3 = new Paragraph("Tipo");
                    columna3.getFont().setStyle(Font.BOLD);
                    columna3.getFont().setSize(10);
                    tabla.addCell(columna3);
                    
                    Paragraph columna4 = new Paragraph("Piezas");
                    columna4.getFont().setStyle(Font.BOLD);
                    columna4.getFont().setSize(10);
                    tabla.addCell(columna4);
                    
                    Paragraph columna5 = new Paragraph("Estatus");
                    columna5.getFont().setStyle(Font.BOLD);
                    columna5.getFont().setSize(10);
                    tabla.addCell(columna5);
                    
                    for (Productos r : datos) {
                        tabla.addCell(r.getCodigo());
                        tabla.addCell(r.getMarcaComercial());
                        tabla.addCell(r.getTipoMedicamento());
                        tabla.addCell(String.valueOf(r.getCantidad()));
                        tabla.addCell("");
                    }
                    documento.add(tabla);

                    documento.close();
                    pantalla_Productos.txtUrl.setText("");
                    JOptionPane.showMessageDialog(null, "<html><h1> Exito</h1></html>", "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    Logger.getLogger(Controlador_PantallaProductos.class.getName()).log(Level.SEVERE, " " + ex);
                    JOptionPane.showMessageDialog(null, ex, "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        pantalla_Productos.btnPiezas0.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                productos = new Productos();
                ArrayList<Productos> datos = productos.inventarioPZ0();
                Date date = new Date();
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    FileOutputStream ficheroPdf = null;
                    // Se crea el documento
                    Document documento = new Document();
// Se crea el OutputStream para el fichero donde queremos dejar el pdf.
                    ficheroPdf = new FileOutputStream("C:/farmacia/inventario.pdf");
                    // Se asocia el documento al OutputStream y se indica que el espaciado entre
// lineas sera de 20. Esta llamada debe hacerse antes de abrir el documento
                    PdfWriter.getInstance(documento, ficheroPdf);
// Se abre el documento.
                    documento.open();
                    Paragraph titulo = new Paragraph();
                    titulo.setAlignment(Paragraph.ALIGN_CENTER);
                    titulo.setFont(FontFactory.getFont("Times New Roman", 20, Font.BOLD, BaseColor.RED));
                    titulo.add("***Inventario***");
                    documento.add(titulo);
                    
                    Paragraph titulo2 = new Paragraph();
                    titulo2.setAlignment(Paragraph.ALIGN_RIGHT);
                    titulo2.setFont(FontFactory.getFont("Times New Roman", 14, BaseColor.BLACK));
                    DateFormat formatofecha = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    titulo2.add(formatofecha.format(date));
                    documento.add(titulo2);
                    
                    Paragraph titulo3 = new Paragraph();
                    titulo3.setAlignment(Paragraph.ALIGN_RIGHT);
                    titulo3.setFont(FontFactory.getFont("Times New Roman", 14, BaseColor.BLACK));
                    sucursal su = new sucursal();
                    String datSucursal[] = su.datosSucursal();
                    titulo3.add(datSucursal[1].toUpperCase());
                    documento.add(titulo3);
                    
                    Paragraph saltolinea12 = new Paragraph();
                    saltolinea12.add("\n\n");
                    documento.add(saltolinea12);
                    
                    float[] columnWidths = {2, 3, 2,1};
                    PdfPTable tabla = new PdfPTable(columnWidths);
                    tabla.setWidthPercentage(100);
                    //Añadimos los títulos a la tabla. 
                    Paragraph columna1 = new Paragraph("Código");
                    columna1.getFont().setStyle(Font.BOLD);
                    columna1.getFont().setSize(10);
                    tabla.addCell(columna1);

                    Paragraph columna2 = new Paragraph("Marca");
                    columna2.getFont().setStyle(Font.BOLD);
                    columna2.getFont().setSize(10);
                    tabla.addCell(columna2);

                    Paragraph columna3 = new Paragraph("Tipo");
                    columna3.getFont().setStyle(Font.BOLD);
                    columna3.getFont().setSize(10);
                    tabla.addCell(columna3);
                    
                    Paragraph columna4 = new Paragraph("Piezas");
                    columna4.getFont().setStyle(Font.BOLD);
                    columna4.getFont().setSize(10);
                    tabla.addCell(columna4);
                   
                    
                    for (Productos r : datos) {
                        tabla.addCell(r.getCodigo());
                        tabla.addCell(r.getMarcaComercial());
                        tabla.addCell(r.getTipoMedicamento());
                        tabla.addCell(String.valueOf(r.getCantidad()));
                    }
                    documento.add(tabla);

                    documento.close();
                    Mail mail = new Mail();
                    Utilerias util = new Utilerias();
                    
                    mail.send_mail( Utilerias.MAIL_PRINCIPAL, "PDF", "inventario Piezas 0" , 1); //farmaciagi08@gmail.com
                    JOptionPane.showMessageDialog(null, "<html><h1> Exito</h1></html>", "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    Logger.getLogger(Controlador_PantallaProductos.class.getName()).log(Level.SEVERE, " " + ex);
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
        //pantalla_Productos.altaMedicamentoProveedor.setSelectedIndex(1);

    }

    private void Clear_Table(JTable jt) {
        DefaultTableModel modelo = (DefaultTableModel) jt.getModel();
        int filas = jt.getRowCount();
        for (int i = 0; filas > i; i++) {
            modelo.removeRow(0);
        }
    }

}
